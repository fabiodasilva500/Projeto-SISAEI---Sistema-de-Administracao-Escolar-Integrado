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
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import Pojo.Matricula;
import Pojo.Turma;

public class AlunoTurmaDaoImplementation implements AlunoTurmaDao, Serializable{



	@Override
	public boolean inserir(Matricula at) throws SQLException {
		
		boolean inserido=false;
		try{
			
			int curso = localizaCurso(at);
 			int dependencias = verificaDependencias(at, curso);
            String situacaoCurso = "";			
			
			System.out.println("Dependencias localizadas:"+dependencias);
		
			int moduloInformadoLocalizado = 0;
			int moduloCadastradoLocalizado = 0;
						
			Connection con = BD.getConnection();
			String consultaModulo = "Select modulo from Turma Where IDTurma = ?";
			PreparedStatement consultaModuloTurma=con.prepareStatement(consultaModulo);
			consultaModuloTurma.setInt(1, at.getIDTurma());
			ResultSet rs=consultaModuloTurma.executeQuery();
			if(rs.next()){
			moduloInformadoLocalizado=rs.getInt("modulo");
			}
			
			String consultaModuloCadastrado = "Select modulo, situacaoCurso from statusAluno Where identificacao = ? and IDCurso = ?";
			PreparedStatement consultaModuloAtual=con.prepareStatement(consultaModuloCadastrado);
			consultaModuloAtual.setString(1, at.getIdentificacao());
			consultaModuloAtual.setInt(2, curso);
			ResultSet localiza=consultaModuloAtual.executeQuery();
			if(localiza.next()){
		    moduloCadastradoLocalizado=localiza.getInt("modulo");
		    situacaoCurso = localiza.getString("situacaoCurso");
			}
			
			if(moduloCadastradoLocalizado==0 && moduloInformadoLocalizado==1){
			moduloCadastradoLocalizado=1;
			System.out.println("Modulo Cadastrado:"+moduloCadastradoLocalizado);
			}
			
			
			int validado = verificaCursoModulo(at, dependencias);
			
			if((moduloInformadoLocalizado==moduloCadastradoLocalizado || dependencias<=2) && validado==1){
			if(!situacaoCurso.equalsIgnoreCase("concluído") && !situacaoCurso.equalsIgnoreCase("trancado")&&
			!situacaoCurso.equalsIgnoreCase("desistente")){	
				String sql = "INSERT INTO AlunoTurma Values (?,?,?)";
				PreparedStatement stmt=con.prepareStatement(sql);
				stmt.setString(1, at.getIdentificacao());
				stmt.setInt(2, curso);
				stmt.setInt(3, at.getIDTurma());
				stmt.executeUpdate();
				inserido=true;
				insereStatus(at);
				System.out.println("Válido");
			
			}
			}
			
			else{
			System.out.println("Invalido");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return inserido;
	}

	@Override
	public List<Matricula> pesquisar(String identificacao) throws SQLException {
		List<Matricula> listaAt=new ArrayList<Matricula>();
		try
		{
			Connection con = BD.getConnection();
			String sql = "Select IdentificacaoAluno, IDDaTurma from AlunoTurma Where IdentificacaoAluno = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, identificacao);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Matricula at=new Matricula();

				at.setIdentificacao(rs.getString("IdentificacaoAluno"));
				at.setIDTurma(rs.getInt("IDDaTurma"));
				listaAt.add(at);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return listaAt;
	}

	@Override
	public boolean remover(String identificacao, int IDTurma, int selecaoCurso) throws SQLException {
		boolean removido = false;
		int modulo=0;
		try{
			Connection con = BD.getConnection();
			int verifica = verificaIDTurma(identificacao, IDTurma, selecaoCurso);
			if(verifica==1){
			String sql = "DELETE AlunoTurma Where IdentificacaoAluno = ? and IDDaTurma = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, identificacao);
			stmt.setInt(2, IDTurma);
			stmt.executeUpdate();
			removido = true;
			
			String localizaModulo = "Select modulo from StatusAluno Where identificacao = ? and IDCurso = ? ";
			stmt=con.prepareStatement(localizaModulo);
			stmt.setString(1, identificacao);
			stmt.setInt(2, selecaoCurso);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){
			modulo=rs.getInt("modulo");
			}
			
			if(modulo==1){
			removeStatus(identificacao, IDTurma);
			atualizaTurma(identificacao, IDTurma, selecaoCurso);
			System.out.println("Modulo 1");
			}
			else{
			atualizaTurma(identificacao, IDTurma, selecaoCurso);
		    System.out.println("Atualizando modulo");
			}
		}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return removido;
	}


	private int verificaIDTurma(String identificacao, int IDTurma,
			int selecaoCurso) {
      int retorno = 0;
      int IDTurmaLocalizado=0;
      int moduloLocalizado=0;
      int moduloAtual=0;
      try{
      Connection con = BD.getConnection();
      String sql = "Select IDTurma, modulo from StatusAluno Where identificacao = ? and IDCurso = ? ";
      PreparedStatement stmt=con.prepareStatement(sql);
      stmt.setString(1, identificacao);
      stmt.setInt(2, selecaoCurso);
      ResultSet rs=stmt.executeQuery();
      if(rs.next()){
      IDTurmaLocalizado = rs.getInt("IDTurma");
      moduloLocalizado = rs.getInt("modulo");
      if(IDTurma>=IDTurmaLocalizado){
      retorno = 1;
      System.out.println("Sim");
      }
      else{
       retorno = 0;
       System.out.println("Não");
      }
      }
      
      String modulo = "Select modulo from Turma Where IDTurma = ? ";
      stmt=con.prepareStatement(modulo);
      stmt.setInt(1, IDTurma);
      rs=stmt.executeQuery();
      if(rs.next()){
      moduloAtual = rs.getInt("modulo");
    	  
      }
      if(moduloAtual>moduloLocalizado){
      retorno = 0;
      }
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
      
	return retorno;
	}

	public void preparaPDF(int idTurma) throws JRException{
		gerarPDF(idTurma);
	}




	public JasperPrint gerarPDF(int idTurma) throws JRException{
        JasperPrint rel = null;
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

		try {
			Connection con = BD.getConnection();
			Map map = new HashMap();
			map.put("IDTurma", idTurma);
			String arquivoJasper = caminho+File.separator+"ListaDeAlunosDeTurmas.jasper";
			rel = JasperFillManager.fillReport(arquivoJasper, map, con);
		    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=Lista De Alunos da turma : "+idTurma+" .pdf");
	 
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
	

	
	public void insereStatus(Matricula at){
    try{
    boolean localizaAluno=false;
    Connection con = BD.getConnection(); 
    String localizaIdentificacao = "Select identificacao from statusAluno where identificacao = ? and IDCurso = ? ";
    PreparedStatement localizaIdentificacaoAluno=con.prepareStatement(localizaIdentificacao);
    localizaIdentificacaoAluno.setString(1, at.getIdentificacao());
    localizaIdentificacaoAluno.setInt(2, at.getIDCurso());
    ResultSet localizaIDAluno=localizaIdentificacaoAluno.executeQuery();
    if(localizaIDAluno.next()){
    if(!localizaIDAluno.getString("identificacao").equals(null)){
    localizaAluno=true; 
    }
    }
    
    String localizaDados = "Select curso, modulo, semestre, ano from turma Where IDTurma = ?";
    PreparedStatement localiza=con.prepareStatement(localizaDados);
    localiza.setInt(1, at.getIDTurma());
    
    Turma turma=new Turma();
    ResultSet rs=localiza.executeQuery();
    if(rs.next()){
    turma.setCurso(rs.getInt("curso"));
    turma.setModulo(rs.getInt("modulo"));
    turma.setSemestre(rs.getInt("semestre"));
    turma.setAno(rs.getInt("ano"));
    }
    
 
    if(localizaAluno){
    String sql = "UPDATE statusAluno set IDTurma = ?, modulo = ?, semestre = ?, ano = ?, situacaoDeMatricula = ? Where identificacao = ? and IDCurso = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, at.getIDTurma());
    stmt.setInt(2, turma.getModulo());
    stmt.setInt(3, turma.getSemestre());
    stmt.setInt(4, turma.getAno());
    stmt.setString(5, "Matriculado no módulo:"+turma.getModulo());
    stmt.setString(6, at.getIdentificacao());
    stmt.setInt(7, turma.getCurso());
    stmt.executeUpdate();
    }
    else{
     
    String sql = "INSERT INTO statusAluno values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, at.getIdentificacao());
    stmt.setInt(2, turma.getCurso());
    stmt.setInt(3, at.getIDTurma());
    stmt.setDouble(4, at.getDependencias());
    stmt.setInt(5, at.getTotalDependencias());
    stmt.setInt(6, turma.getModulo());
    stmt.setInt(7, turma.getSemestre());
    stmt.setInt(8, turma.getAno());
    stmt.setString(9, "Matriculado no:"+turma.getModulo()+" módulo");
    stmt.setString(10, "");
    stmt.setString(11, "Aluno recém cadastrado");
    stmt.setString(12, at.getInstituicao());
    stmt.setString(13, at.getRazao());
    stmt.executeUpdate();
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
	}

	
	public void removeStatus(String identificacao, int IDTurma){

	Connection con = BD.getConnection();	
	try{
	String sql = "Delete statusAluno Where identificacao = ? and IDTurma = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, identificacao);
	stmt.setInt(2, IDTurma);
	stmt.executeUpdate();
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	}
	
	public void atualizaTurma (String identificacao, int IDTurma, int curso){
    int IDTurmaEncontrado  = 0;
    int moduloLocalizado=0;
    int cursoLocalizado=0;
	
    try{
    Connection con = BD.getConnection();   
    String localizaModuloCurso = "Select curso, modulo from Turma Where IDTurma = ?";
    PreparedStatement stmt=con.prepareStatement(localizaModuloCurso);
    stmt.setInt(1, IDTurma);
    ResultSet localizaCurso=stmt.executeQuery();
    if(localizaCurso.next()){
    cursoLocalizado=localizaCurso.getInt("curso");
    moduloLocalizado=localizaCurso.getInt("modulo");
    }
     
    String localizaTurmaAnterior="Select IDDaTurma from AlunoTurma Where identificacaoAluno = ? and IDCurso = ? ";
    stmt=con.prepareStatement(localizaTurmaAnterior);
    stmt.setString(1, identificacao);
    stmt.setInt(2, curso);
    ResultSet localizaTurma=stmt.executeQuery();
    while(localizaTurma.next()){
    IDTurmaEncontrado=localizaTurma.getInt("IDDaTurma");
    if(IDTurmaEncontrado>IDTurma){
    IDTurmaEncontrado=IDTurma;
    }
    System.out.println("Turma Encontrada:"+IDTurmaEncontrado);
    }
    
    if(IDTurmaEncontrado>0){
    String atualizaTurma="UPDATE statusAluno set IDTurma = ?, modulo = modulo-1 Where identificacao = ? and IDCurso = ?";
    stmt=con.prepareStatement(atualizaTurma);
    stmt.setInt(1, IDTurmaEncontrado);
    stmt.setString(2, identificacao);
    stmt.setInt(3, cursoLocalizado);
    stmt.executeUpdate();
    }
    
    if(IDTurmaEncontrado==0){
    	String sql = "Delete statusAluno Where identificacao = ? and IDTurma = ?";
    	stmt=con.prepareStatement(sql);
    	stmt.setString(1, identificacao);
    	stmt.setInt(2, IDTurma);
    	stmt.executeUpdate();
    }
    }
    
    
	catch(SQLException e){
	e.printStackTrace();
	}
	}
	
	
	public int verificaDependencias(Matricula at, int curso){
	int dependencias=0;
	try{
	Connection con = BD.getConnection();
	String sql = "Select dependencias from statusAluno Where identificacao = ? and IDCurso = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, at.getIdentificacao());
	stmt.setInt(2, curso);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	dependencias=rs.getInt("dependencias");	
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return dependencias;
	}
	
	
	public int localizaCurso(Matricula at){
	int curso = 0;
	try{
	Connection con = BD.getConnection();
	String sql = "Select curso from Turma Where IDTurma = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, at.getIDTurma());
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	curso = rs.getInt("curso");
	}
	System.out.println("Curso:"+curso);
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	
	return curso;
	}
	
	
	public int verificaCursoModulo(Matricula at, int dependencias){
	int encontrado = 0;
	List<Integer> listaCursos = new ArrayList<Integer>();
	List<Integer> listaModulos = new ArrayList<Integer>();
	int cursoLocalizado=0;
	int moduloLocalizado=0;
	try{
	Connection con = BD.getConnection();

	
	String localizaIdentificacao = "Select IDCurso, modulo from statusAluno Where identificacao = ? and IDCurso = ? ";
	PreparedStatement stmt=con.prepareStatement(localizaIdentificacao);
	stmt.setString(1, at.getIdentificacao());
	stmt.setInt(2, at.getIDCurso());
	ResultSet identificacao=stmt.executeQuery();
	while(identificacao.next()){
	listaCursos.add(identificacao.getInt("IDCurso"));
	listaModulos.add(identificacao.getInt("modulo"));
	}
	
	System.out.println("Tamanho:"+listaModulos.size());
	
	String localizaCursoModulo = "Select curso, modulo from Turma Where IDTurma = ?";
	stmt=con.prepareStatement(localizaCursoModulo);
	stmt.setInt(1, at.getIDTurma());
	ResultSet localizaInfoTurma=stmt.executeQuery();
	if(localizaInfoTurma.next()){
	cursoLocalizado=localizaInfoTurma.getInt("curso");
	moduloLocalizado=localizaInfoTurma.getInt("modulo");
	}
	
	for(int i=0;i<listaCursos.size();i++){
	if((listaCursos.get(i)==cursoLocalizado) && ((listaModulos.get(i)+1)==moduloLocalizado && dependencias <=2)){
	encontrado=1;
	}
	if((listaCursos.get(i)==cursoLocalizado) && listaModulos.get(i)==moduloLocalizado && dependencias >=3){
	encontrado=1;
	}
	}
	

	if(listaCursos.size()==0 && listaModulos.size()==0 && moduloLocalizado==1){
	System.out.println("AQUI!!!");
    String localizaCurso = "Select curso from Aluno Where identificacao = ?";
    stmt=con.prepareStatement(localizaCurso);
    stmt.setString(1, at.getIdentificacao());
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    int cursoEstudante=rs.getInt("curso");
    if(cursoEstudante==cursoLocalizado){
    encontrado=1;
    }
    }
    }
	}
	catch(SQLException e){
    e.printStackTrace();
	}
  return encontrado;
	
}
	
	
	public int localizaMatriculados(int semestreInicial, int anoInicial, int curso){
	int matriculados=0;
	try{
	Connection con = BD.getConnection();
	String sql = "select Count (identificacaoAluno) as 'identificacao' from AlunoTurma "+
"inner join turma "+
"on AlunoTurma.IDDaTurma = turma.IDTurma "+
"where turma.semestre = ? and turma.ano = ? and turma.curso = ? and turma.modulo = 1";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, semestreInicial);
	stmt.setInt(2, anoInicial);
	stmt.setInt(3, curso);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	matriculados=rs.getInt("identificacao");
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return matriculados;
	}
	
	public List<String> localizaIdentificacoes(int semestreInicial, int anoInicial, int curso){
	List<String> identificacoes= new ArrayList<String>();
	try{
	Connection con = BD.getConnection();
	String sql = "select  identificacaoALuno from AlunoTurma "+
"inner join Turma "+
"on AlunoTurma.IDDaTurma=turma.IDTurma "+
"where  turma.semestre= ? and turma.ano = ? and turma.curso = ? and  turma.modulo=1 ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, semestreInicial);
	stmt.setInt(2, anoInicial);
	stmt.setInt(3, curso);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	identificacoes.add(rs.getString("identificacaoAluno"));
	}
	}
	catch(SQLException e){
    e.printStackTrace();
	}
	return identificacoes;
	}
	
	
	   public int localizaTrancamento(String identificacao){
		    int localizado=0;
		    try{
		    Connection con = BD.getConnection();
		    String sql = "Select identificacaoAluno from Trancamento where identificacaoAluno = ?";
		    PreparedStatement stmt=con.prepareStatement(sql);
		    stmt.setString(1, identificacao);
		    ResultSet rs=stmt.executeQuery();
		    if(rs.next()){
		    localizado=1;
		    }
		    }
		    catch(SQLException e){
		    e.printStackTrace();
		    }
		    return localizado;
		    }
		    
	
	
}
