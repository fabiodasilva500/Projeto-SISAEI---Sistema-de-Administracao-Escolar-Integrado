package Dao;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import Pojo.Aluno;
import Pojo.Diario;
import Pojo.Disciplina;


public class DiarioDaoImplementation implements DiarioDao, Serializable{

	@Override
	public boolean inserirDiario(Diario a, int idDiario) {
		boolean inserido = false;
		
	       try{
	       Connection con = BD.getConnection();			

			String sql = "insert into diario values (?,?,?,?,?,?,?)";

			PreparedStatement ps;

			ps = con.prepareStatement(sql);
			
			ps.setInt(1, idDiario);
			ps.setInt(2, a.getIdDisciplina());
			ps.setInt(3, a.getIdProfessor());
			ps.setInt(4, a.getIdTurma());
			ps.setInt(5, a.getIdCurso());
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ps.setString(6, sdf.format(a.getDataAula()));
			ps.setString(7, a.getTurno());
			
			ps.execute();
			ps.close();
			inserido = true;
		}
	       catch(SQLException e){
	    	  e.printStackTrace();
	       }
	       return inserido;
	}

	@Override
	public boolean consultarDiario() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterarDiario() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirDiario(int idTurma, int idDisciplina, int idProfessor, String dataAula) {
	
	System.out.println("Chegou:"+idTurma);
	System.out.println("Chegou:"+idDisciplina);
	System.out.println("Chegou:"+idProfessor);
	System.out.println("Chegou:"+dataAula);
	
    int idDiario=0;
	boolean efetuado=false;
    try{
    Connection con = BD.getConnection();
    String sql = "Select idDiario from Diario Where idTurma = ? and idDisciplina = ? and idProfessor = ? and dataAula = ? ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, idTurma);
    stmt.setInt(2, idDisciplina);
    stmt.setInt(3, idProfessor);
    stmt.setString(4, dataAula);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    idDiario = rs.getInt("idDiario");
    }
    System.out.println("Diário:"+idDiario);
    if(idDiario>0){
    String removePresencas="Delete presenca Where idDiario = ? ";
    stmt=con.prepareStatement(removePresencas);
    stmt.setInt(1, idDiario);
    stmt.execute();
    
    String removeChamada = "Delete diario Where idDiario = ?";
    stmt=con.prepareStatement(removeChamada);
    stmt.setInt(1, idDiario);
    stmt.executeUpdate();
    efetuado=true;
    
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return efetuado;
	}

	@Override
	public List<Disciplina> listaDisciplinaProf(int idProfessor) {
		
		List<Disciplina> listaDisciplinas=new ArrayList<Disciplina>();
		try{
			Connection con = BD.getConnection();
			String sql = "select Professor.IDProfessor, Professor.nome nomeProfessor, disciplina.IDDisciplina as IDDisciplina, disciplina.nome as nomeDisciplina "+
"							from ProfessorDisciplinaTurma "+
"							inner join Professor on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
"							inner join disciplina on  disciplina.IDDisciplina = ProfessorDisciplinaTurma.IDDaDisciplina "+ 
"							where Professor.IDProfessor = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, idProfessor);
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Disciplina disciplina=new Disciplina();
				disciplina.setIDDisciplina(rs.getInt("IDDisciplina"));
				disciplina.setNome(rs.getString("nomeDisciplina"));
				listaDisciplinas.add(disciplina);
				System.out.println("Nome turma: "+ disciplina.getNome());
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return listaDisciplinas;
		
	}

	@Override
	public List<Aluno> listaAlunos(int idTurma) {
		List<Aluno> listaAlunos=new ArrayList<Aluno>();
		try{
			Connection con = BD.getConnection();
			String sql = "select Aluno.identificacao as rm, Aluno.nome as nome, Aluno.email as email, Aluno.curso " +
					"from Aluno " +
					"inner Join AlunoTurma " +
					"on Aluno.identificacao=AlunoTurma.identificacaoAluno " +
					"inner join Turma "+
					"on Turma.IDTurma=AlunoTurma.IDDaTurma "+
					"where AlunoTurma.IDDaTurma =  ? " +
					"order by Aluno.nome";
			
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, idTurma);
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Aluno aluno=new Aluno();
				aluno.setIdentificacao(rs.getString("rm"));
				aluno.setNome(rs.getString("nome"));
				aluno.setEmail(rs.getString("email"));
				aluno.setCurso(rs.getInt("curso"));
				aluno.setPresencaAtual(true);
				listaAlunos.add(aluno);
				System.out.println("Nome Aluno: "+ aluno.getNome() +  " Presenca: " + aluno.isPresencaAtual());
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return listaAlunos;
	}

	@Override
	public boolean inserirPresenca(Aluno a, int id) {
		boolean inserido = false;
		
	       try{
	       Connection con = BD.getConnection();			

			String sql = "insert into presenca values (?,?,?,?)";

			PreparedStatement ps;

			ps = con.prepareStatement(sql);
			
			System.out.println("Curso:"+a.getCurso());
			
			ps.setString(1, a.getIdentificacao());
			ps.setInt(2, a.getCurso());
			ps.setInt(3, id);
			ps.setBoolean(4, a.isPresencaAtual());
			
			System.out.println("ID do Diário:"+id);
			
			ps.execute();
			ps.close();
			inserido = true;
		}
	       catch(SQLException e){
	    	  e.printStackTrace();
	       }
	       return inserido;
	}

	@Override
	public int ultimoDiario() {
		int idDiario = 0;
		try{
			Connection con = BD.getConnection();
			String sql ="select top 1 idDiario " + 
						"from diario " +
						"order by idDiario desc";
			
			PreparedStatement stmt=con.prepareStatement(sql);
			
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				idDiario = (rs.getInt("idDiario"));
				System.out.println("ultimo diario: " + idDiario);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return idDiario;
	}

	@Override
	public boolean excluirPresenca(Aluno a, int id) {
		boolean inserido = false;

		try{
			Connection con = BD.getConnection();			

			String sql = "delete from presenca where idDiario = ? and identificacao = ?";

			PreparedStatement ps;

			ps = con.prepareStatement(sql);

			
			ps.setInt(1, id);
			ps.setString(2, a.getIdentificacao());

			ps.execute();
			ps.close();
			inserido = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return inserido;
	}
	
	
	
	

	public JasperPrint gerarPDF(int idTurma, int idDisciplina, int idProfessor,
			java.util.Date dataAula) throws JRException {
		   JasperPrint rel = null;
		   
		   System.out.println("Chegou Turma"+idTurma+" Disciplina:"+idDisciplina+" Professor:"+idProfessor+" Data:"+dataAula);
					   
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
			String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

			try {
				Connection con = BD.getConnection();
				Map map = new HashMap();
				map.put("IDTurma", idTurma);
				map.put("IDDisciplina", idDisciplina);
				map.put("IDProfessor", idProfessor);
				map.put("dataAula", dataAula);
				
				String arquivoJasper = caminho+File.separator+"ListaDeAlunosPresentes.jasper";

				rel = JasperFillManager.fillReport(arquivoJasper, map, con);
			    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
		        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		        response.setContentType("Relatorio/pdf");
		        response.addHeader("Content-disposition", "attachment; filename=Lista De Alunos Presentes da disciplina : "+idDisciplina+" .pdf");
		 
		           // Exporta o relatório
		            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
		            // Salva o estado da aplicação no contexto do JSF
		            fc.getApplication().getStateManager().saveView(fc);
		            // Fecha o stream do response
		            fc.responseComplete();
		        } catch (Exception e) {
		        }
		
			
			return rel;
	
	}


	
	@Override
	public JasperPrint verificarAlunosAusentesTurma(int IDTurma,
			Date dataInicial, Date dataFinal) {
		
		System.out.println("Turma:"+IDTurma);
		System.out.println("Data Inicial:"+dataInicial);
		System.out.println("Data Final:"+dataFinal);
		
		   JasperPrint rel = null;
					FacesContext fc = FacesContext.getCurrentInstance();
					ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
					String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

					try {
						Connection con = BD.getConnection();
						Map map = new HashMap();
						map.put("IDTurma",IDTurma);
						map.put("dataInicial", dataInicial);
						map.put("dataFinal", dataFinal);
						String arquivoJasper = caminho+File.separator+"ListaDeAlunosAusentesTurma.jasper";
						rel = JasperFillManager.fillReport(arquivoJasper, map, con);
					    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
				        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
				        response.setContentType("Relatorio/pdf");
				        response.addHeader("Content-disposition", "attachment; filename=Lista de Alunos Ausentes da Turma : "+IDTurma+" .pdf");
				 
				           // Exporta o relatório
				            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
				            // Salva o estado da aplicação no contexto do JSF
				            fc.getApplication().getStateManager().saveView(fc);
				            // Fecha o stream do response
				            fc.responseComplete();
				        } catch (Exception e) {
				        }
					
					return rel;
	}

	@Override
	public JasperPrint verificarAlunosAusentesDisciplina(int IDTurma,
			int IDDisciplina, Date dataInicial, Date dataFinal) {
		System.out.println("Turma:"+IDTurma);
		System.out.println("Data Inicial:"+dataInicial);
		System.out.println("Data Final:"+dataFinal);
		
		   JasperPrint rel = null;
					FacesContext fc = FacesContext.getCurrentInstance();
					ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
					String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

					try {
						Connection con = BD.getConnection();
						Map map = new HashMap();
						map.put("IDTurma",IDTurma);
						map.put("IDDisciplina",IDDisciplina);
						map.put("dataInicial", dataInicial);
						map.put("dataFinal", dataFinal);
						String arquivoJasper = caminho+File.separator+"ListaDeAlunosAusentesDisciplina.jasper";
						rel = JasperFillManager.fillReport(arquivoJasper, map, con);
					    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
				        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
				        response.setContentType("Relatorio/pdf");
				        response.addHeader("Content-disposition", "attachment; filename=Lista de Alunos Ausentes da Disciplina : "+IDDisciplina+" .pdf");
				 
				           // Exporta o relatório
				            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
				            // Salva o estado da aplicação no contexto do JSF
				            fc.getApplication().getStateManager().saveView(fc);
				            // Fecha o stream do response
				            fc.responseComplete();
				        } catch (Exception e) {
				        }
					
					return rel;
	}

	@Override
	public List<Integer> graficoChamada(int IDTurma, Date dataInicial, Date dataFinal) {
	
	List<Integer> listaChamada = new ArrayList<Integer>();
	try{	
	Connection con = BD.getConnection();
	String presentes = "Select Count (distinct presenca.identificacao) as 'quantidadePresente' from Presenca  " +
	"inner join Diario " +
	"on presenca.idDiario = Diario.idDiario "+
	"where diario.idTurma = ? and diario.dataAula >= ? and diario.dataAula <= ? ";
	PreparedStatement stmt=con.prepareStatement(presentes);
    stmt.setInt(1, IDTurma);
    stmt.setDate(2, new java.sql.Date(dataInicial.getTime()));  
    stmt.setDate(3, new java.sql.Date(dataFinal.getTime()));

    ResultSet alunosPresentes=stmt.executeQuery();
    if(alunosPresentes.next()){
    listaChamada.add(alunosPresentes.getInt("quantidadePresente"));
    }
    
	String ausentes = " select  Count (distinct aluno.identificacao) as 'quantidadeAusente' " +
	 "from Aluno  " + 
    "inner join AlunoTurma "+
    "on Aluno.identificacao = AlunoTurma.identificacaoAluno "+
    "where Aluno.identificacao not in "+
    "(select identificacao from presenca "+ 
    "inner join diario "+
    "on presenca.IDDiario = Diario.IDDiario  "+
    "Where Diario.dataAula > = ? and Diario.dataAula <= ? ) "+ 
    "and AlunoTurma.IDDaTurma = ? ";

    stmt=con.prepareStatement(ausentes);
    stmt.setDate(1, new java.sql.Date(dataInicial.getTime()));  
    stmt.setDate(2, new java.sql.Date(dataFinal.getTime()));
	stmt.setInt(3, IDTurma);

    ResultSet alunosAusentes = stmt.executeQuery();
    if(alunosAusentes.next()){
    listaChamada.add(alunosAusentes.getInt("quantidadeAusente"));
    }
 
    
	}
	catch(SQLException e){
	e.printStackTrace();
	}
    return listaChamada;
    }
	
	
	
	
	@Override
	public List<Integer> graficoChamadaDisciplina(int IDTurma, int IDDisciplina, Date dataInicial, Date dataFinal) {
	
	List<Integer> listaChamada = new ArrayList<Integer>();
	try{	
	Connection con = BD.getConnection();
	String presentes = "Select Count (distinct presenca.identificacao) as 'quantidadePresente' from Presenca  " +
	"inner join Diario " +
	"on presenca.idDiario = Diario.idDiario "+
	"where diario.idTurma = ? and diario.dataAula >= ? and diario.dataAula <= ? and diario.idDisciplina = ?";
	PreparedStatement stmt=con.prepareStatement(presentes);
    stmt.setInt(1, IDTurma);
    stmt.setDate(2, new java.sql.Date(dataInicial.getTime()));  
    stmt.setDate(3, new java.sql.Date(dataFinal.getTime()));
    stmt.setInt(4, IDDisciplina);
    
    ResultSet alunosPresentes=stmt.executeQuery();
    if(alunosPresentes.next()){
    listaChamada.add(alunosPresentes.getInt("quantidadePresente"));
    }
    
	String ausentes = " select  Count (distinct aluno.identificacao) as 'quantidadeAusente' " +
	 "from Aluno  " + 
    "inner join AlunoTurma "+
    "on Aluno.identificacao = AlunoTurma.identificacaoAluno "+
    "where Aluno.identificacao not in "+
    "(select identificacao from presenca "+ 
    "inner join diario "+
    "on presenca.IDDiario = Diario.IDDiario  "+
    "Where Diario.IDDisciplina = ? and Diario.dataAula > = ? and Diario.dataAula <= ? ) "+ 
    "and AlunoTurma.IDDaTurma = ? ";
	
	stmt=con.prepareStatement(ausentes);
	stmt.setInt(1, IDDisciplina);
    stmt.setDate(2, new java.sql.Date(dataInicial.getTime()));  
    stmt.setDate(3, new java.sql.Date(dataFinal.getTime()));
    stmt.setInt(4, IDTurma);

    
    ResultSet alunosAusentes = stmt.executeQuery();
    if(alunosAusentes.next()){
    listaChamada.add(alunosAusentes.getInt("quantidadeAusente"));
    }
 
    
	}
	catch(SQLException e){
	e.printStackTrace();
	}
    return listaChamada;
    }
	
	
	
	
	public boolean inserirAtraso (Aluno a, Diario d){
	boolean inserido = false;
	try{
	Connection con = BD.getConnection();
	String sql = "INSERT INTO atraso Values (?,?,?,?,?,?,?)";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, d.getIdDiario());
	stmt.setString(2, a.getIdentificacao());
	stmt.setInt(3, d.getIdCurso());
	stmt.setInt(4, d.getIdTurma());
	stmt.setInt(5, d.getIdDisciplina());
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	stmt.setString(6, sdf.format(d.getDataAula()));
	stmt.setDouble(7, d.getQuantidadeAusencia());
	stmt.executeUpdate();
	inserido=true;
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return inserido;
	}
	
	
	public boolean excluirAtraso(Aluno a, Diario diario){
	boolean excluido=false;
	try{
	Connection con = BD.getConnection();
	String sql = "DELETE atraso Where IDDiario = ? and identificacao = ? and IDDisciplina = ? and IDTurma = ? and dataAula = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, diario.getIdDiario());
	stmt.setString(2, a.getIdentificacao());
	stmt.setInt(3, diario.getIdDisciplina());
	stmt.setInt(4, diario.getIdTurma());
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	stmt.setString(5, sdf.format(diario.getDataAula()));	
    stmt.executeUpdate();
    excluido=true;
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return excluido;
	}


	
}
