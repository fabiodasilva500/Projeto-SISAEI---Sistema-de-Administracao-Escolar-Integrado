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
import Pojo.Professor;

public class ProfessorDaoImplementation implements ProfessorDao, Serializable {

	@Override
	public boolean inserir(Professor p) {
    boolean inserido=false;
	try{
	Connection con = BD.getConnection();
    String sql = "INSERT INTO Professor values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, p.getIDProfessor());
    stmt.setString(2, p.getNome());
    stmt.setString(3,p.getRg());
    stmt.setString(4, p.getCpf());
    stmt.setString(5, p.getCep());
    stmt.setString(6, p.getEndereco());
    stmt.setInt(7, p.getNumero());
    stmt.setString(8, p.getBairro());
    stmt.setString(9, p.getCidade());
    stmt.setString(10, p.getEstado());
    stmt.setString(11, p.getTelefone());
    stmt.setString(12, p.getCelular());
    stmt.setDate(13, new java.sql.Date(p.getDataNascimento().getTime()));
    stmt.setString(14, p.getNacionalidade());
    stmt.setString(15, p.getNaturalidade());
    stmt.setString(16, p.getEmail());
    stmt.setString(17, p.getStatusAtual());
    stmt.executeUpdate();
    inserido=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
   
	return inserido;
	}

	@Override
	public boolean alterar(int IDProfessor, Professor p) {
	boolean atualizado = false;
	try{
	Connection con = BD.getConnection();
	String sql = "UPDATE Professor set nome = ?, rg = ?, cpf = ?, cep = ?, endereco = ?, numero = ?," +
	"bairro = ?, cidade = ?, estado = ?, telefone = ?, celular = ?, dataNascimento = ?,"+
	"nacionalidade = ?, naturalidade = ?, email = ?, statusAtual = ?" +
	"Where IDProfessor = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, p.getNome());
    stmt.setString(2, p.getRg());
    stmt.setString(3, p.getCpf());
    stmt.setString(4, p.getCep());
    stmt.setString(5, p.getEndereco());
    stmt.setInt(6, p.getNumero());
    stmt.setString(7, p.getBairro());
    stmt.setString(8, p.getCidade());
    stmt.setString(9, p.getEstado());
    stmt.setString(10, p.getTelefone());
    stmt.setString(11, p.getCelular());
    stmt.setDate(12, new java.sql.Date(p.getDataNascimento().getTime()));
    stmt.setString(13, p.getNacionalidade());
    stmt.setString(14, p.getNaturalidade());
    stmt.setString(15, p.getEmail());
    stmt.setString(16, p.getStatusAtual());
	stmt.setInt(17, p.getIDProfessor());
    stmt.executeUpdate();
    atualizado = true;
    }
	catch(SQLException e){
	e.printStackTrace();
	}
	
		return atualizado;
	}

	@Override
	public List<Professor> pesquisarPorNome(String nome) {
    List<Professor> professores=new ArrayList<Professor>();
    
	try{
    Connection con = BD.getConnection();
    String sql = "SELECT IDProfessor, nome, rg, cpf, cep, endereco, numero, bairro, cidade," +
    	"estado, telefone, celular, dataNascimento, nacionalidade, naturalidade," +
        "email, statusAtual from Professor Where nome like ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, "%"+nome+"%");
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    Professor p =new Professor();
    p.setIDProfessor(rs.getInt("IDProfessor"));
    p.setNome(rs.getString("nome"));
    p.setRg(rs.getString("rg"));
    p.setCpf(rs.getString("cpf"));
    p.setCep(rs.getString("cep"));
    p.setEndereco(rs.getString("endereco"));
    p.setNumero(rs.getInt("numero"));
    p.setBairro(rs.getString("bairro"));
    p.setCidade(rs.getString("cidade"));
    p.setEstado(rs.getString("estado"));
    p.setTelefone(rs.getString("telefone"));
    p.setCelular(rs.getString("celular"));
    p.setDataNascimento(rs.getDate("dataNascimento"));
    p.setNacionalidade(rs.getString("nacionalidade"));
    p.setNaturalidade(rs.getString("naturalidade"));
    p.setEmail(rs.getString("email"));
    p.setStatusAtual(rs.getString("statusAtual"));
    professores.add(p);
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		
		return professores;
	}

	@Override
	public boolean excluir(int IDProfessor) {
    boolean excluido = false;
	try{
    Connection con = BD.getConnection();
    String sql = "DELETE Professor Where IDProfessor = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, IDProfessor);
    stmt.executeUpdate();
    excluido = true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		
		return excluido;
	}
	
	
	
	@Override
	public Professor pesquisarIdentificacao(int IDProfessor) {
	Professor p =new Professor();

	try{
    Connection con = BD.getConnection();
    String sql = "SELECT IDProfessor, nome, rg, cpf, cep, endereco, numero, bairro, cidade," +
    	"estado, telefone, celular, dataNascimento, nacionalidade, naturalidade," +
        "email, statusAtual from Professor Where  IDProfessor = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, IDProfessor);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    p.setIDProfessor(rs.getInt("IDProfessor"));
    p.setNome(rs.getString("nome"));
    p.setRg(rs.getString("rg"));
    p.setCpf(rs.getString("cpf"));
    p.setCep(rs.getString("cep"));
    p.setEndereco(rs.getString("endereco"));
    p.setNumero(rs.getInt("numero"));
    p.setBairro(rs.getString("bairro"));
    p.setCidade(rs.getString("cidade"));
    p.setEstado(rs.getString("estado"));
    p.setTelefone(rs.getString("telefone"));
    p.setCelular(rs.getString("celular"));
    p.setDataNascimento(rs.getDate("dataNascimento"));
    p.setNacionalidade(rs.getString("nacionalidade"));
    p.setNaturalidade(rs.getString("naturalidade"));
    p.setEmail(rs.getString("email"));
    p.setStatusAtual(rs.getString("statusAtual"));
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		
		return p;
	}

	@Override
	public String localizaNomeProfessor(int IDProfessor) {
    String nomeLocalizado = null; 
	 try{
     Connection con = BD.getConnection();
     String sql = "Select nome from Professor Where IDProfessor = ?";
     PreparedStatement stmt=con.prepareStatement(sql);
     stmt.setInt(1, IDProfessor);
     ResultSet rs=stmt.executeQuery();
     if(rs.next()){
     nomeLocalizado = rs.getString("nome");
     }
     }
	 catch(SQLException e){
     e.printStackTrace();
	 }
		
		return nomeLocalizado;
	}

	
	public List<Professor> listaProfessores(){
	List<Professor> listaProfessores=new ArrayList<Professor>();
	try{
	Connection con = BD.getConnection();
	String sql = "Select IDProfessor, nome from professor Where statusAtual = 'Ativado'  order by professor.nome ";
	PreparedStatement stmt=con.prepareStatement(sql);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	Professor professor=new Professor();
	professor.setIDProfessor(rs.getInt("IDProfessor"));
	professor.setNome(rs.getString("nome"));
	listaProfessores.add(professor);
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaProfessores;
	}
	
	
	public List<Professor> listaProfessorTurma (int IDTurma){
	List<Professor> listaProfessores=new ArrayList<Professor>();
	try{
	Connection con = BD.getConnection();
	String sql = "Select Professor.IDProfessor, Professor.nome, Professor.email " +
	"from Professor " +
	"inner Join ProfessorDisciplinaTurma " +
	"on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
	"inner join Turma "+
	"on Turma.IDTurma = ProfessorDisciplinaTurma.IDDaTurma "+
	"Where Turma.IDTurma = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDTurma);
	ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    Professor professor = new Professor();
    professor.setIDProfessor(rs.getInt("IDProfessor"));
    professor.setNome(rs.getString("nome"));
    professor.setEmail(rs.getString("email"));
    listaProfessores.add(professor);
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaProfessores;
	}
	
	
	
	public String localizaEmailProfessor (int IDProfessor){
			
	String localizado="";
	Connection con = BD.getConnection();
	try{
	String sql = "Select email from Professor Where IDProfessor = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDProfessor);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	localizado=rs.getString("email");
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return localizado;
	}
	

	
	public List<Professor> listaProfessorTurmaDisciplina (int IDTurma, int IDDisciplina){
	List<Professor> listaProfessores=new ArrayList<Professor>();
	try{
	Connection con = BD.getConnection();
	String sql = "Select Professor.IDProfessor, Professor.nome, Professor.email " +
	"from Professor " +
	"inner Join ProfessorDisciplinaTurma " +
	"on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
	"inner join Turma "+
	"on Turma.IDTurma = ProfessorDisciplinaTurma.IDDaTurma "+
	"inner join Disciplina "+
	"on Disciplina.IDDisciplina = ProfessorDisciplinaTurma.IDDaDisciplina "+
	"Where Turma.IDTurma = ? and Disciplina.IDDisciplina = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDTurma);
	stmt.setInt(2, IDDisciplina);
	ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    Professor professor = new Professor();
    professor.setIDProfessor(rs.getInt("IDProfessor"));
    professor.setNome(rs.getString("nome"));
    professor.setEmail(rs.getString("email"));
    listaProfessores.add(professor);
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaProfessores;
	}



	@Override
	public String localizaNomeProfessor(String identificacaoAluno) {
    String nome = "";
    System.out.println("Estou novamente aqui");
	try{
    Connection con = BD.getConnection();
    String sql = "Select nome from Professor Where IDProfessor = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    nome=rs.getString("nome");
    System.out.println(nome);
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
	return nome;	
	}
	
	
	public boolean validaNomeRG(int identificacaoConvertida, String rg, String nome){
	String rgLocalizado="";
	String nomeLocalizado="";
	boolean validado=false;
    try{
	Connection con = BD.getConnection();
	String sql = "Select rg, nome from Professor Where IDProfessor = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, identificacaoConvertida);
	ResultSet rs = stmt.executeQuery();
	if(rs.next()){
	rgLocalizado = rs.getString("rg");
	nomeLocalizado=rs.getString("nome");
	}
	if(rgLocalizado.equals(rg) && nomeLocalizado.equalsIgnoreCase(nome)){
	validado=true;
	}
	}
    catch(SQLException e){
    e.printStackTrace();
    }
    return validado;
	}
	
	

	public boolean validaCEP(int identificacaoConvertida, String cep)	  {
    String cepLocalizado="";
	boolean validado=false;
    try{
    Connection con = BD.getConnection();
    String sql = "Select cep from Professor Where IDProfessor = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, identificacaoConvertida);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    cepLocalizado =rs.getString("cep");
    System.out.println("CEP Informado:"+cep);
    System.out.println("CEP Localizado:"+cepLocalizado);
    if(cep.equals(cepLocalizado)){
    validado=true;
    }
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return validado;
	  }
	
	
	
	public JasperPrint gerarPDF() throws JRException{
        JasperPrint rel = null;
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

		try {
			Connection con = BD.getConnection();
			Map map = new HashMap();
			String arquivoJasper = caminho+File.separator+"ManualDoProfessor.pdf";
			rel = JasperFillManager.fillReport(arquivoJasper, map, con);
		    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=Manual do Professor.pdf");
	 
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
	
	
}
