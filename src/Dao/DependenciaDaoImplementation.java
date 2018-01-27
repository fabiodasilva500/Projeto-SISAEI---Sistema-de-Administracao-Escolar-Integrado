package Dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojo.Aluno;
import Pojo.Dependencia;

public class DependenciaDaoImplementation implements DependenciaDao, Serializable {

	@Override
	public boolean inserir(String identificacaoAluno, int IDCurso, int IDDaDisciplina) {
    String identificacao=null;
    boolean inserido=false;
	try{
    Connection con = BD.getConnection();
    String localiza = "Select identificacaoAluno from dependencia Where identificacaoAluno = ? and IDDaDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(localiza);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    identificacao=rs.getString("identificacaoAluno");
    }
    if(identificacao==null){
    String insere = "INSERT INTO dependencia values (?,?,?)";
    stmt=con.prepareStatement(insere);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDCurso);
    stmt.setInt(3, IDDaDisciplina);
    stmt.executeUpdate();
    inserido=true;
    }
    else{
    stmt.close();
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		return inserido;
	}

	@Override
	public boolean remover(String identificacaoAluno, int IDDaDisciplina) {
    boolean removido=false;
	try{
    Connection con = BD.getConnection();
    String sql = "Delete dependencia Where identificacaoAluno = ? and IDDaDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    stmt.executeUpdate();
    removido=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return removido;
	}
	
	
	public boolean inserirQuitacaoDependencia(String identificacaoAluno, int IDCurso, int IDDaDisciplina, String atividade, String mencao){
	boolean efetuado=false;
	String identificacaoLocalizada="";
	try{
    Connection con = BD.getConnection();
    
    String localiza = "Select identificacaoAluno from Dependencia Where identificacaoAluno = ? and IDDaDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(localiza);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    identificacaoLocalizada=rs.getString("identificacaoAluno");
    }
    
    if(!identificacaoLocalizada.equalsIgnoreCase("") && !identificacaoLocalizada.equalsIgnoreCase(null) &&
    !mencao.equalsIgnoreCase("I")){
    System.out.println("Quitando");
    String sql = "INSERT INTO dependenciaQuitada Values (?,?,?,?,?)";
    stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDCurso);
    stmt.setInt(3, IDDaDisciplina);
    stmt.setString(4, atividade);
    stmt.setString(5, mencao);
    stmt.executeUpdate();
	remover(identificacaoAluno, IDDaDisciplina);
	decrementaDependencia(identificacaoAluno);
    efetuado=true;
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	
    return efetuado;
}

    public boolean removerQuitacaoDependencia(String identificacaoAluno, int IDCurso, int IDDaDisciplina){
    boolean efetuado=false;
    String identificacaoLocalizada="";
    try{
    Connection con = BD.getConnection();
    
    String localiza = "Select identificacaoAluno from dependenciaQuitada Where identificacaoAluno = ? and IDDaDisciplina = ? ";
    PreparedStatement stmt=con.prepareStatement(localiza);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    identificacaoLocalizada=rs.getString("identificacaoAluno");
    }
    
    if(!identificacaoLocalizada.equalsIgnoreCase(null) && !identificacaoLocalizada.equalsIgnoreCase("")){
    //inserir(identificacaoAluno, IDDaDisciplina);
    incrementaDependencia(identificacaoAluno);
    
    String sql = "Delete dependenciaQuitada Where identificacaoAluno = ? and IDDaDisciplina = ?";
    stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    stmt.execute();
    efetuado=true;
    }
    
    String retornaDependencia = "INSERT INTO Dependencia values (?,?,?) ";
    stmt=con.prepareStatement(retornaDependencia);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDCurso);
    stmt.setInt(3, IDDaDisciplina);
    stmt.executeUpdate();
    
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return efetuado;
    }
    
    
    public boolean decrementaDependencia(String identificacaoAluno){
    boolean efetuado=false;
    try{
    Connection con = BD.getConnection();
    String sql = "UPDATE statusAluno set dependencias = dependencias - 1 Where identificacao = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.executeUpdate();
    efetuado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return efetuado;
    }
    
    
    public boolean incrementaDependencia(String identificacaoAluno){
    boolean efetuado=false;
    
    try{
    Connection con = BD.getConnection();
    String sql = "UPDATE statusAluno set dependencias = dependencias + 1 Where identificacao = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.executeUpdate();
    efetuado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return efetuado;
    }
    
    
    
    public Dependencia localizaDependenciaCadastrada(String identificacaoAluno, int IDDaDisciplina){
    Dependencia dependencia=new Dependencia();
    System.out.println("Chegou:"+identificacaoAluno);
    System.out.println("Chegou:"+IDDaDisciplina);
    try{
    Connection con = BD.getConnection();
    String sql = "Select identificacaoAluno, IDDaDisciplina from Dependencia Where identificacaoAluno = ? and IDDaDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    dependencia.setIdentificacaoAluno(rs.getString("identificacaoAluno"));
    dependencia.setIDDaDisciplina(rs.getInt("IDDaDisciplina"));
    dependencia.setMencao("I");
    dependencia.setAtividade("Dependência ainda não realizada pelo estudante");   
    }
    System.out.println("Identificação localizada:"+dependencia.getIdentificacaoAluno());
    System.out.println("Disciplina localizada:"+dependencia.getIDDaDisciplina());
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return dependencia;
    }
    
    
    
    public Dependencia localizaNovaNotaInformada(String identificacaoAluno, int IDDaDisciplina){
    Dependencia dependencia=new Dependencia();
    System.out.println("Chegou:"+identificacaoAluno);
    System.out.println("Chegou:"+IDDaDisciplina);
    try{
    Connection con = BD.getConnection();
    String sql = "Select identificacaoAluno, IDDaDisciplina, mencao, atividade from dependenciaQuitada Where identificacaoAluno = ? and IDDaDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    dependencia.setIdentificacaoAluno(rs.getString("identificacaoAluno"));
    dependencia.setIDDaDisciplina(rs.getInt("IDDaDisciplina"));
    dependencia.setAtividade(rs.getString("atividade"));
    dependencia.setMencao(rs.getString("mencao"));
    }
    System.out.println("Identificação localizada:"+dependencia.getIdentificacaoAluno());
    System.out.println("Disciplina localizada:"+dependencia.getIDDaDisciplina());
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return dependencia;
    }

	@Override
	public List<Aluno> consultarDependencia(int selecaoDisciplina) {
    List<Aluno> alunos=new ArrayList<Aluno>();
	try{
    Connection con = BD.getConnection();
    String sql = "Select identificacao, nome  from Aluno "+
"inner join dependencia "+
"on Aluno.identificacao = dependencia.identificacaoAluno "+
"Where IDDaDisciplina = ? ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, selecaoDisciplina);
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    Aluno a=new Aluno();
    a.setIdentificacao(rs.getString("identificacao"));
    a.setNome(rs.getString("nome"));
    alunos.add(a);
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return alunos;
	}
    
    
    
	
	public List<Aluno> consultarDependenciaQuitada(int selecaoDisciplina) {
	    List<Aluno> alunos=new ArrayList<Aluno>();
		try{
	    Connection con = BD.getConnection();
	    String sql = "Select identificacao, nome  from Aluno "+
	"inner join dependenciaQuitada "+
	"on Aluno.identificacao = dependenciaQuitada.identificacaoAluno "+
	"Where IDDaDisciplina = ? ";
	    PreparedStatement stmt=con.prepareStatement(sql);
	    stmt.setInt(1, selecaoDisciplina);
	    ResultSet rs=stmt.executeQuery();
	    while(rs.next()){
	    Aluno a=new Aluno();
	    a.setIdentificacao(rs.getString("identificacao"));
	    a.setNome(rs.getString("nome"));
	    alunos.add(a);
	    }
	    }
	    catch(SQLException e){
	    e.printStackTrace();
	    }
			return alunos;
		}
    
    
}

