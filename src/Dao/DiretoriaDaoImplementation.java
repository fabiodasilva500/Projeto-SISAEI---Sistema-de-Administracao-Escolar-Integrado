package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pojo.Diretoria;
import Pojo.Secretaria;

public class DiretoriaDaoImplementation implements DiretoriaDao {

	@Override
	public boolean inserir(Diretoria p) {
    boolean inserido=false;
	try{
	Connection con = BD.getConnection();
    String sql = "INSERT INTO diretoria values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
    stmt.executeUpdate();
    inserido=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
   
	return inserido;
	}

	@Override
	public boolean alterar(String cpf, Diretoria p) {
	boolean atualizado = false;
	try{
	Connection con = BD.getConnection();
	String sql = "UPDATE diretoria set nome = ?, rg = ?, cep = ?, endereco = ?, numero = ?," +
	"bairro = ?, cidade = ?, estado = ?, telefone = ?, celular = ?, dataNascimento = ?,"+
	"nacionalidade = ?, naturalidade = ?, email = ? " +
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
    stmt.setString(15, p.getCpf());

    stmt.executeUpdate();
    atualizado = true;
    }
	catch(SQLException e){
	e.printStackTrace();
	}
	
		return atualizado;
	}

	@Override
	public Diretoria pesquisarPorNome(String nome) {
	  Diretoria diretoria =new Diretoria();

	try{
    Connection con = BD.getConnection();
    String sql = "SELECT nome, rg, cpf, cep, endereco, numero, bairro, cidade," +
    	"estado, telefone, celular, dataNascimento, nacionalidade, naturalidade," +
        "email from diretoria Where nome like ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, "%"+nome+"%");
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    diretoria .setNome(rs.getString("nome"));
    diretoria.setRg(rs.getString("rg"));
    diretoria.setCpf(rs.getString("cpf"));
    diretoria.setCep(rs.getString("cep"));
    diretoria.setEndereco(rs.getString("endereco"));
    diretoria.setNumero(rs.getInt("numero"));
    diretoria.setBairro(rs.getString("bairro"));
    diretoria.setCidade(rs.getString("cidade"));
    diretoria.setEstado(rs.getString("estado"));
    diretoria.setTelefone(rs.getString("telefone"));
    diretoria.setCelular(rs.getString("celular"));
    diretoria.setDataNascimento(rs.getDate("dataNascimento"));
    diretoria.setNacionalidade(rs.getString("nacionalidade"));
    diretoria.setNaturalidade(rs.getString("naturalidade"));
    diretoria.setEmail(rs.getString("email"));
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		
		return diretoria;
	}

	@Override
	public boolean excluir(String cpf) {
    boolean excluido = false;
    
    System.out.println("CPF:"+cpf);
    
	try{
    Connection con = BD.getConnection();
    String sql = "DELETE diretoria Where cpf = ?";
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
	public String localizaNomeDiretoria(String identificacao) {
		 String nome = "";
		    System.out.println("Estou novamente aqui");
			try{
		    Connection con = BD.getConnection();
		    String sql = "Select nome from diretoria Where cpf = ?";
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
		String sql = "Select rg, nome from diretoria Where cpf = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setString(1, identificacao);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
		rgLocalizado = rs.getString("rg");
		nomeLocalizado = rs.getString("nome");
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

	@Override
	public boolean validaCEP(String identificacao, String cep) {
	    String cepLocalizado="";
		  boolean validado=false;
	      try{
	      Connection con = BD.getConnection();
	      String sql = "Select cep from Diretoria Where cpf = ?";
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
		
	
	

