package Dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pojo.Coordenador;

public class CoordenadorDaoImplementation implements CoordenadorDao, Serializable {

	@Override
	public boolean inserir(Coordenador p) {
    boolean inserido=false;
	try{
	Connection con = BD.getConnection();
    String sql = "INSERT INTO Coordenador values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1,p.getCpf());
    stmt.setString(2, p.getNome());
    stmt.setString(3, p.getRg());
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
    stmt.setInt(16, p.getCurso());
    
    stmt.executeUpdate();
    inserido=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
   
	return inserido;
	}

	@Override
	public boolean alterar(String cpf, Coordenador p) {
	boolean atualizado = false;
	try{
	Connection con = BD.getConnection();
	String sql = "UPDATE Coordenador set nome = ?, rg = ?, cep = ?, endereco = ?, numero = ?," +
	"bairro = ?, cidade = ?, estado = ?, telefone = ?, celular = ?, dataNascimento = ?,"+
	"nacionalidade = ?, naturalidade = ?, email = ?, curso = ? " +
	"Where cpf  = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, p.getNome());
    stmt.setString(2, p.getRg());
    stmt.setString(3, p.getCep());
    stmt.setString(4, p.getEndereco());
    stmt.setInt(5, p.getNumero());
    stmt.setString(6, p.getBairro());
    stmt.setString(7, p.getCidade());
    stmt.setString(8, p.getEstado());
    stmt.setString(9, p.getTelefone());
    stmt.setString(10, p.getCelular());
    stmt.setDate(11, new java.sql.Date(p.getDataNascimento().getTime()));
    stmt.setString(12, p.getNacionalidade());
    stmt.setString(13, p.getNaturalidade());
    stmt.setString(14, p.getEmail());
    stmt.setInt(15, p.getCurso());
    stmt.setString(16, p.getCpf());
    
    
    stmt.executeUpdate();
    atualizado = true;
    }
	catch(SQLException e){
	e.printStackTrace();
	}
	
		return atualizado;
	}

	@Override
	public Coordenador pesquisarPorNome(String nome) {
	  Coordenador coordenador =new Coordenador();

	try{
    Connection con = BD.getConnection();
    String sql = "SELECT nome, rg, cpf, cep, endereco, numero, bairro, cidade," +
    	"estado, telefone, celular, dataNascimento, nacionalidade, naturalidade," +
        "email, curso from Coordenador Where nome like ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, "%"+nome+"%");
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    coordenador .setNome(rs.getString("nome"));
    coordenador.setRg(rs.getString("rg"));
    coordenador.setCpf(rs.getString("cpf"));
    coordenador.setCep(rs.getString("cep"));
    coordenador.setEndereco(rs.getString("endereco"));
    coordenador.setNumero(rs.getInt("numero"));
    coordenador.setBairro(rs.getString("bairro"));
    coordenador.setCidade(rs.getString("cidade"));
    coordenador.setEstado(rs.getString("estado"));
    coordenador.setTelefone(rs.getString("telefone"));
    coordenador.setCelular(rs.getString("celular"));
    coordenador.setDataNascimento(rs.getDate("dataNascimento"));
    coordenador.setNacionalidade(rs.getString("nacionalidade"));
    coordenador.setNaturalidade(rs.getString("naturalidade"));
    coordenador.setEmail(rs.getString("email"));
    coordenador.setCurso(rs.getInt("curso"));
    
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		
		return coordenador;
	}

	@Override
	public boolean excluir(String cpf) {
    boolean excluido = false;
	try{
    Connection con = BD.getConnection();
    String sql = "DELETE Coordenador Where cpf = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, cpf);
    stmt.executeUpdate();
    excluido = true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		
		return excluido;
	}

	@Override
	public String localizaNomeCoordenador(String identificacao) {
		 String nome = "";
		    System.out.println("Estou novamente aqui");
			try{
		    Connection con = BD.getConnection();
		    String sql = "Select nome from Coordenador Where cpf = ?";
		    PreparedStatement stmt=con.prepareStatement(sql);
		    stmt.setString(1, identificacao);
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
			
	@Override
	public boolean validaNomeRG(String identificacao, String rg, String nome) {
		String rgLocalizado="";
		String nomeLocalizado="";
		boolean validado=false;
	    try{
		Connection con = BD.getConnection();
		String sql = "Select rg, nome from Coordenador Where cpf = ?";
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
		

	public boolean validaCEP(String identificacao, String cep)	  {
	    String cepLocalizado="";
		boolean validado=false;
	    try{
	    Connection con = BD.getConnection();
	    String sql = "Select cep from Coordenador Where cpf = ?";
	    PreparedStatement stmt=con.prepareStatement(sql);
	    stmt.setString(1, identificacao);
	    ResultSet rs=stmt.executeQuery();
	    if(rs.next()){
	    cepLocalizado =rs.getString("cep");
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

	
}
