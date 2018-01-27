package Dao;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import Pojo.ProfessorDisciplinaTurma;
import Pojo.Turma;

public class TurmaDaoImplementation  implements TurmaDao, Serializable{

	@Override
	public boolean inserir(Turma t) {
		boolean inserido = false;

		if(!t.getNome().equalsIgnoreCase("")){
		try{
			Connection con = BD.getConnection();
			String sql = "INSERT Into turma values (?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt=con.prepareStatement(sql);
			if(t.getIDTurma()<=0){
			t.setIDTurma(1);
			}
			stmt.setInt(1, t.getIDTurma());
			stmt.setString(2, t.getNome());
			stmt.setInt(3, t.getAno());
			stmt.setInt(4, t.getSemestre());
			stmt.setString(5, t.getPeriodo());
			stmt.setInt(6, t.getModulo());
			stmt.setDouble(7, t.getAulas_totais());
			stmt.setInt(8, t.getCurso());
			stmt.setString(9, t.getSala());
			stmt.setString(10, t.getDescricao());
			stmt.setDate(11, new java.sql.Date(t.getData_conselho().getTime()));
			stmt.setString(12, t.getPerfil());
			stmt.executeUpdate();
			inserido=true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		}
		return inserido;	
	}

	@Override
	public boolean atualizar(int IDTurma, Turma t) {
		boolean atualizado = false; 
		try{
			Connection con = BD.getConnection();
			String sql = "UPDATE turma set nome = ?, ano = ?, semestre = ?, periodo = ?, modulo = ?, aulas_totais = ?," +
					"curso = ?, sala = ?, descricao = ?, data_conselho = ?, perfil = ?" +
					"Where IDTurma = ?";


			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, t.getNome());
			stmt.setInt(2, t.getAno());
			stmt.setInt(3, t.getSemestre());
			stmt.setString(4, t.getPeriodo());
			stmt.setInt(5, t.getModulo());
			stmt.setDouble(6, t.getAulas_totais());
			stmt.setInt(7, t.getCurso());
			stmt.setString(8, t.getSala());
			stmt.setString(9, t.getDescricao());
			stmt.setDate(10, new java.sql.Date(t.getData_conselho().getTime()));
			stmt.setString(11, t.getPerfil());
			stmt.setInt(12, t.getIDTurma());
			stmt.executeUpdate();
			atualizado=true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return atualizado;

	}

	@Override
	public boolean remover(int IDTurma) {
		boolean removido=false;
		try{
			Connection con = BD.getConnection();
			String sql = "DELETE turma Where IDTurma = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, IDTurma);
			stmt.executeUpdate();
			removido = true;

		}
		catch(SQLException e){
			e.printStackTrace();
			removido = false;
		}
		return removido;
	}

	@Override
	public Turma pesquisar(Turma turma) {

		Turma t=new Turma();
		
		
		try{
			Connection con = BD.getConnection();
			String sql = "SELECT IDTurma, nome, ano, semestre, periodo, modulo, aulas_totais, curso," +
					"sala, descricao, data_conselho, perfil from turma Where nome = ? and semestre = ? and ano = ? and periodo = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, turma.getNome());
			stmt.setInt(2, turma.getSemestre());
			stmt.setInt(3, turma.getAno());
			stmt.setString(4, turma.getPeriodo());
	
			
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){
				t.setIDTurma(rs.getInt("IDTurma"));
				t.setNome(rs.getString("nome"));
				t.setAno(rs.getInt("ano"));
				t.setSemestre(rs.getInt("semestre"));
				t.setPeriodo(rs.getString("periodo"));
				t.setModulo(rs.getInt("modulo"));
				t.setAulas_totais(rs.getDouble("aulas_totais"));
				t.setCurso(rs.getInt("curso"));
				t.setSala(rs.getString("sala"));
				t.setDescricao(rs.getString("descricao"));
				t.setData_conselho(rs.getDate("data_conselho"));
				t.setPerfil(rs.getString("perfil"));

			}
		}
		catch(SQLException e){
			e.printStackTrace();	
		}

		return t;
	}



	public void preparaPDF(){
		gerarPDF();
	}


	public JasperPrint gerarPDF(){
		JasperPrint rel = null;
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");
		
		try {
			Connection con = BD.getConnection();
			HashMap map = new HashMap();
			String arquivoJasper = caminho+File.separator+"relatorioTurma.jasper";
			rel = JasperFillManager.fillReport(arquivoJasper, map, con);
			//	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=Relatório de Turmas.pdf");
	 
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
	
	

	public static void main(String[]args){
		new TurmaDaoImplementation();
	}
	
	@Override
	public List<Turma> listaTurmaCurso(int id, int selecaoSemestre, int selecaoAno) {
		List<Turma> boletins=new ArrayList<Turma>();
		try{
		Connection con = BD.getConnection();
		String sql = "Select IDTurma, nome from turma where curso = ? and semestre = ? and ano = ? order by nome ";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.setInt(2, selecaoSemestre);
		stmt.setInt(3, selecaoAno);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		Turma b=new Turma();
		b.setIDTurma(rs.getInt("IDTurma"));
		b.setNome(rs.getString("nome"));
		boletins.add(b);
		}
	    }
		catch(SQLException e){
		e.printStackTrace();
		}
		return boletins;		
	}
	
	
	@Override
	public List<Turma> listaTurmaModulo(int idCurso, int modulo, int selecaoSemestre, int selecaoAno) {
		List<Turma> boletins=new ArrayList<Turma>();
		try{
		Connection con = BD.getConnection();
		String sql = "Select IDTurma, nome from turma where curso = ? and modulo = ? and semestre = ? and ano = ? order by nome ";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, idCurso);
		stmt.setInt(2, modulo);
		stmt.setInt(3, selecaoSemestre);
		stmt.setInt(4, selecaoAno);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		Turma b=new Turma();
		b.setIDTurma(rs.getInt("IDTurma"));
		b.setNome(rs.getString("nome"));
		boletins.add(b);
		}
		
	    }
		catch(SQLException e){
		e.printStackTrace();
		}
		return boletins;		
	}
	
	
	     public String localizaNome(int IDTurma){
	     String nomeLocalizado=null;
	     try{
	     Connection con = BD.getConnection();
	     String sql = "Select nome from Turma Where IDTurma = ?";
	     PreparedStatement stmt=con.prepareStatement(sql);
	     stmt.setInt(1, IDTurma);
	     ResultSet rs=stmt.executeQuery();
	     if(rs.next()){
	     nomeLocalizado=rs.getString("nome");
	     }
	     }
	     catch(SQLException e){
	     e.printStackTrace();
	     }
	     return nomeLocalizado;
	     }

		@Override
		public Turma localizaTurmaAluno(String identificacao) {
        Turma turma=new Turma();
		try{
        Connection con = BD.getConnection();
        String sql = "Select Turma.IDTurma, Turma.nome, Turma.modulo, Turma.curso from Turma "+
        "inner join AlunoTurma "+
        "on Turma.IDTurma = AlunoTurma.IDDaTurma " +
        "Where AlunoTurma.IdentificacaoAluno = ? order by Turma.nome";
        
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1, identificacao);
        System.out.println("Chegou:"+identificacao);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
        turma.setIDTurma(rs.getInt("IDTurma"));
        turma.setNome(rs.getString("nome"));
        turma.setModulo(rs.getInt("modulo"));
        turma.setCurso(rs.getInt("curso"));
        }
        System.out.println("Retornou:"+turma.getIDTurma());
        }
		catch(SQLException e){
		e.printStackTrace();
		}	
		return turma;
		}

		
		@Override
		public Turma localizaDadosTurma(int IDTurma) {
        Turma turma=new Turma();
		try{
        Connection con = BD.getConnection();
        String sql = "Select IDTurma, nome, modulo, curso from Turma where IDTurma = ? ";
        
        
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setInt(1, IDTurma);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
        turma.setIDTurma(rs.getInt("IDTurma"));
        turma.setNome(rs.getString("nome"));
        turma.setModulo(rs.getInt("modulo"));
        turma.setCurso(rs.getInt("curso"));
        }
        }
		catch(SQLException e){
		e.printStackTrace();
		}	
		return turma;
		}

		
		
		public List<Turma> listaTurmasAnoCurso(int semestre, int ano, int curso){
		List<Turma> listaTurmas=new ArrayList<Turma>();
		try{
		Connection con = BD.getConnection();
		String sql = "Select IDTurma, nome from Turma Where semestre = ? and ano = ? and curso = ? order by nome ";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, semestre);
		stmt.setInt(2, ano);
		stmt.setInt(3, curso);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		Turma t=new Turma();
		t.setIDTurma(rs.getInt("IDTurma"));
		t.setNome(rs.getString("nome"));
		listaTurmas.add(t);
		}
		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return listaTurmas;
		}

		
		public List<Turma> listaTurmasAnoCursoHist(int semestre, int ano, int curso){
		List<Turma> listaTurmas=new ArrayList<Turma>();
		try{
		Connection con = BD.getConnection();
		String sql = "Select IDTurma, nome from Turma Where semestre = ? and ano = ? and curso = ? and modulo = 3 order by nome ";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, semestre);
		stmt.setInt(2, ano);
		stmt.setInt(3, curso);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		Turma t=new Turma();
		t.setIDTurma(rs.getInt("IDTurma"));
		t.setNome(rs.getString("nome"));
		listaTurmas.add(t);
		}
		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return listaTurmas;
		}

		public List<Turma> listaTurmasAnoCursoModulo(int semestre, int ano, int curso, int modulo){
			List<Turma> listaTurmas=new ArrayList<Turma>();
			try{
			Connection con = BD.getConnection();
			String sql = "Select IDTurma, nome from Turma Where semestre = ? and ano = ? and curso = ? and modulo = ? order by nome";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, semestre);
			stmt.setInt(2, ano);
			stmt.setInt(3, curso);
			stmt.setInt(4, modulo);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
			Turma t=new Turma();
			t.setIDTurma(rs.getInt("IDTurma"));
			t.setNome(rs.getString("nome"));
			listaTurmas.add(t);
			}
			}
			catch(SQLException e){
			e.printStackTrace();
			}
			return listaTurmas;
			}
		
		
		@Override
		public List<ProfessorDisciplinaTurma> listaTurmaProfessor(int idProfessor) {
			List<ProfessorDisciplinaTurma> boletins=new ArrayList<ProfessorDisciplinaTurma>();
			try{
				Connection con = BD.getConnection();
				String sql = "select Professor.IDProfessor as 'idProf' , Professor.nome as nomeProf, turma.IDTurma as idTurma, turma.nome as nomeTurma " +
"from ProfessorDisciplinaTurma "+
"inner join Professor on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
"inner join turma on turma.IDTurma = ProfessorDisciplinaTurma.IDDaTurma "+
"where Professor.IDProfessor = ? ";
				PreparedStatement stmt=con.prepareStatement(sql);
				stmt.setInt(1, idProfessor);
				
				ResultSet rs=stmt.executeQuery();
				while(rs.next()){
					ProfessorDisciplinaTurma b=new ProfessorDisciplinaTurma();
					b.setIDDoProfessor(rs.getInt("idProf"));
					b.setNomeProfessor(rs.getString("nomeProf"));
					b.setIDDaTurma(rs.getInt("idTurma"));
					b.setNomeTurma(rs.getString("nomeTurma"));
					boletins.add(b);
					System.out.println(b.getNomeTurma());
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return boletins;
		}

		
		
		
		public int localizaIDTurma(){
		int localizado=0;
		try{
		Connection con = BD.getConnection();
		String sql = "Select IDTurma from Turma";
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		localizado=rs.getInt("IDTurma");
		}
		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return localizado;
		}
    }



   








