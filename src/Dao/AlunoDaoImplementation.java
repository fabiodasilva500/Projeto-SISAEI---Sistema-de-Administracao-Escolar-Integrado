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

import Pojo.Acesso;
import Pojo.Aluno;

public class AlunoDaoImplementation implements AlunoDao, Serializable {

	@Override
	public boolean inserir(Aluno a) throws SQLException {
		boolean inserido = false;
		try{
			Connection con = BD.getConnection();
			String sql = "INSERT Into aluno values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, a.getIdentificacao());
			stmt.setString(2, a.getNome());
			stmt.setString(3, a.getCep());			
			stmt.setString(4, a.getEndereco());
			stmt.setInt(5, a.getNumero());
			stmt.setString(6, a.getBairro());
			stmt.setString(7, a.getCidade());
			stmt.setString(8, a.getEstado());
			stmt.setString(9, a.getTelefone());
			stmt.setString(10, a.getCelular());
			stmt.setString(11, a.getRg());
			stmt.setString(12, a.getOrgao_expeditor());
			stmt.setDate(13, new java.sql.Date(a.getDataNascimento().getTime()));
			stmt.setString(14, a.getCidadeNascimento());
			stmt.setString(15, a.getNacionalidade());
			stmt.setString(16, a.getNaturalidade());
			stmt.setString(17, a.getSexo());
			stmt.setInt(18, a.getIdade());
			stmt.setString(19, a.getEmail());
			stmt.setInt(20, a.getCurso());
			stmt.executeUpdate();
			inserido = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return inserido;
	}


	@Override
	public Aluno pesquisarPorId(String identificacao) throws SQLException {
		Aluno a=new Aluno();

		try{
			Connection con = BD.getConnection();
			String sql = "SELECT identificacao, nome, cep, endereco, numero, bairro," +
					"cidade, estado, telefone, celular, rg, orgao_expeditor, dataNascimento, cidadeNascimento," +
					"nacionalidade, naturalidade, sexo, idade, email, curso from aluno Where identificacao like ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, identificacao);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){
				a.setIdentificacao(rs.getString("identificacao"));
				a.setNome(rs.getString("nome"));
				a.setCep(rs.getString("cep"));
				a.setEndereco(rs.getString("endereco"));
				a.setNumero(rs.getInt("numero"));
				a.setBairro(rs.getString("bairro"));
				a.setCidade(rs.getString("cidade"));
				a.setEstado(rs.getString("estado"));
				a.setTelefone(rs.getString("telefone"));
				a.setCelular(rs.getString("celular"));
				a.setRg(rs.getString("rg"));
				a.setOrgao_expeditor(rs.getString("orgao_expeditor"));
				a.setDataNascimento(rs.getDate("dataNascimento"));
				a.setCidadeNascimento(rs.getString("cidadeNascimento"));
				a.setNacionalidade(rs.getString("nacionalidade"));
				a.setNaturalidade(rs.getString("naturalidade"));
				a.setSexo(rs.getString("sexo"));
				a.setIdade(rs.getInt("idade"));
				a.setEmail(rs.getString("email"));
				a.setCurso(rs.getInt("curso"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();	
		}

		return a;
	}




	@Override
	public List<Aluno> pesquisarPorNome(String nome) throws SQLException {
		List<Aluno> alunos=new ArrayList<Aluno>();

		try{
			Connection con = BD.getConnection();
			String sql = "SELECT identificacao, nome, cep, endereco, numero, bairro," +
					"cidade, estado, telefone, celular, rg, orgao_expeditor, dataNascimento, cidadeNascimento," +
					"nacionalidade, naturalidade, sexo, idade, email, curso from aluno Where nome like ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, "%"+nome+"%");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Aluno a=new Aluno();
				a.setIdentificacao(rs.getString("identificacao"));
				a.setNome(rs.getString("nome"));
				a.setCep(rs.getString("cep"));
				a.setEndereco(rs.getString("endereco"));
				a.setNumero(rs.getInt("numero"));
				a.setBairro(rs.getString("bairro"));
				a.setCidade(rs.getString("cidade"));
				a.setEstado(rs.getString("estado"));
				a.setTelefone(rs.getString("telefone"));
				a.setCelular(rs.getString("celular"));
				a.setRg(rs.getString("rg"));
				a.setOrgao_expeditor(rs.getString("orgao_expeditor"));
				a.setDataNascimento(rs.getDate("dataNascimento"));
				a.setCidadeNascimento(rs.getString("cidadeNascimento"));
				a.setNacionalidade(rs.getString("nacionalidade"));
				a.setNaturalidade(rs.getString("naturalidade"));
				a.setSexo(rs.getString("sexo"));
				a.setIdade(rs.getInt("idade"));
				a.setEmail(rs.getString("email"));
				a.setCurso(rs.getInt("curso"));
				alunos.add(a);
			}
		}
		catch(SQLException e){
			e.printStackTrace();	
		}

		return alunos;
	}

	@Override
	public boolean remover(String identificacao) throws SQLException {
		boolean removido = false;
		try{
			Connection con=BD.getConnection();
			String sql = "DELETE aluno where identificacao = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, identificacao);
			stmt.executeUpdate();
			removido = true;
		}

		catch(SQLException e){
			e.printStackTrace();
		}
		return removido;
	}

	@Override
	public boolean atualizar(String identificacao, Aluno a) throws SQLException {
		boolean atualizado = false;
		try{
			Connection con = BD.getConnection();
			String sql = "UPDATE aluno set nome = ?, cep = ?, endereco = ?, numero = ?," +
					"bairro = ?, cidade = ?, estado = ?, telefone = ?, celular = ?, rg = ?, orgao_expeditor = ?," +
					"dataNascimento = ?, cidadeNascimento = ?, nacionalidade = ?, naturalidade = ?," +
					"sexo = ?, idade = ?, email = ?, curso = ? Where identificacao like ?";


			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, a.getNome());
			stmt.setString(2, a.getCep());
			stmt.setString(3, a.getEndereco());
			stmt.setInt(4, a.getNumero());
			stmt.setString(5, a.getBairro());
			stmt.setString(6, a.getCidade());
			stmt.setString(7, a.getEstado());
			stmt.setString(8, a.getTelefone());
			stmt.setString(9, a.getCelular());
			stmt.setString(10, a.getRg());
			stmt.setString(11, a.getOrgao_expeditor());
			stmt.setDate(12, new java.sql.Date(a.getDataNascimento().getTime()));
			stmt.setString(13, a.getCidadeNascimento());
			stmt.setString(14, a.getNacionalidade());
			stmt.setString(15, a.getNaturalidade());
			stmt.setString(16, a.getSexo());
			stmt.setInt(17, a.getIdade());
			stmt.setString(18, a.getEmail());
			stmt.setInt(19, a.getCurso());
			stmt.setString(20, identificacao);
			stmt.executeUpdate();
			atualizado = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return atualizado;
	}
	
	
	public List<Aluno> consultaAluno(int IDTurma){
		List<Aluno> listaAlunos= new ArrayList<Aluno>();

		String sql = "select Aluno.identificacao, Aluno.nome as 'nomeAluno' "+
				"from Aluno "+
				"inner Join AlunoTurma "+ 
				"on Aluno.identificacao=AlunoTurma.identificacaoAluno "+ 
				"inner join Turma "+
				"on Turma.IDTurma=AlunoTurma.IDDaTurma "+
				"where AlunoTurma.IDDaTurma = ? "+
				"and Aluno.identificacao not in "+
				"(select trancamento.identificacaoAluno from trancamento "+
				"inner join AlunoTurma "+
				"on trancamento.identificacaoAluno = AlunoTurma.identificacaoAluno "+ 
				"Where AlunoTurma.IDDaTurma=?) "+
				"order by Aluno.nome";
		
		try{
	        Connection con = BD.getConnection();
			PreparedStatement stmt= con.prepareStatement(sql);
			stmt.setInt(1, IDTurma);
			stmt.setInt(2, IDTurma);
			ResultSet rs=stmt.executeQuery();
			 
			while(rs.next()){

				Aluno aluno = new Aluno();
				aluno.setIdentificacao(rs.getString("identificacao"));
				aluno.setNome(rs.getString("nomeAluno"));
                listaAlunos.add(aluno);
    		}
		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return listaAlunos;

}


	@Override
	public String localizaNomeAluno(String identificacaoAluno) {
    String nome = "";
    System.out.println("Estou novamente aqui");
	try{
    Connection con = BD.getConnection();
    String sql = "Select nome from Aluno Where identificacao = ?";
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
	
	
	public boolean validaNomeRG(String identificacao, String rg, String nome){
	String rgLocalizado="";
	String nomeLocalizado="";
	boolean validado=false;
    try{
	Connection con = BD.getConnection();
	String sql = "Select rg, nome from Aluno Where identificacao = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, identificacao);
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
	
	
	public JasperPrint gerarPDF() throws JRException{
        JasperPrint rel = null;

		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

		System.out.println("Aqui");
		try {
			System.out.println("Tentando abrir");

			Connection con = BD.getConnection();
			java.awt.Desktop.getDesktop().open( new File(caminho+File.separator+"ManualDoAluno.pdf" ));  

		    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=ManualDoAluno.pdf");
	 
	           // Exporta o relatório
	            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
	            // Salva o estado da aplicação no contexto do JSF
	            fc.getApplication().getStateManager().saveView(fc);
	            // Fecha o stream do response
	            fc.responseComplete();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		
		return rel;
	}
	

}

