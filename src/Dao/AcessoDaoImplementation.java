package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojo.Acesso;


public class AcessoDaoImplementation implements AcessoDao{

	@Override
	public boolean inserirAcesso(Acesso a) {
	boolean efetuado=false;
    try{
    Connection con = BD.getConnection();
    String sql = "{call insereAcesso (?,?,?,?,?,?)}";
    CallableStatement cs=con.prepareCall(sql);
    cs.setString(1, a.getIdentificacao());
    cs.setString(2, a.getNome());
    cs.setString(3, a.getRg());
    cs.setString(4, a.getEmail());
    cs.setString(5, a.getSenha());
    cs.setString(6, a.getTipoAcesso());
    cs.execute();
    cs.close();
    efetuado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		
		return efetuado;
	}

	@Override
	public Acesso consultarAcessoAluno(String email, String senha) {
    Acesso acesso = new Acesso();
	try{
    Connection con = BD.getConnection();
    String sql = "Select email, senha_acesso, tipoAcesso, identificacao, nome, rg  from Acesso Where email = ? and senha_acesso = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, email);
    stmt.setString(2, senha);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    acesso.setEmail(rs.getString("email"));
    acesso.setSenha(rs.getString("senha_acesso"));
    acesso.setTipoAcesso(rs.getString("tipoAcesso"));
    acesso.setIdentificacao(rs.getString("identificacao"));
    System.out.println("Localizado:"+acesso.getIdentificacao());
    acesso.setNome(rs.getString("nome"));
    acesso.setRg(rs.getString("rg"));
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
	
		return acesso;
	}

	@Override
	public boolean alterarAcesso() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean validaDados(String identificacao, String nome, String rg,
			String email) {
    boolean encontrado=false;
    Acesso a = new Acesso();
    a.setIdentificacao("");
    a.setNome("");
    a.setRg("");
    a.setEmail("");
    a.setSenha("");
    
    try{
    Connection con = BD.getConnection();
    String sql = "Select identificacao, nome, rg, email, senha_acesso from Acesso Where identificacao = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacao);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    a.setNome(rs.getString("nome"));
    a.setRg(rs.getString("rg"));
    a.setEmail(rs.getString("email"));
    a.setSenha(rs.getString("senha_acesso"));
    }
    
    
    if(a.getNome().equals(nome) && a.getRg().equals(rg) && a.getEmail().equals(email)){
    encontrado=true;
    }
    if(a.getNome().equals("")){
    encontrado=false;
    }
    }
	
    catch(SQLException e){
    e.printStackTrace();
    }
		return encontrado;
	}

	@Override
	public List<String> localizaLoginSenha(String identificacao) {
    List<String> localizaLoginSenha=new ArrayList<String>();
    try{
    Connection con = BD.getConnection();
    String sql = "Select email, senha_acesso from Acesso Where identificacao = ? ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacao);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    localizaLoginSenha.add(rs.getString("email"));
    localizaLoginSenha.add(rs.getString("senha_acesso"));
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    
		return localizaLoginSenha;
	}
	
	
	public String localizaEmailUsuario (String email, String rg){
	String emailLocalizado="";
    try{
	Connection con = BD.getConnection();
	String sql = "Select email from acesso where email = ? and rg = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, email);
	stmt.setString(2, rg);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	emailLocalizado = rs.getString("email");	
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
    return emailLocalizado;
	}
	
	public boolean atualizaEmailStatus (String email, String novaSenha, String rg){
	boolean atualizado = false;
	try{
	Connection con = BD.getConnection();
	String sql = "UPDATE acesso set senha_acesso = ?, primeiroAcesso = 'Sim' where email = ? and rg = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, novaSenha);
	stmt.setString(2, email);
	stmt.setString(3, rg);
	stmt.executeUpdate();
	atualizado=true;
    }
	catch(SQLException e){
	e.printStackTrace();
	}
	return atualizado;
	}

	@Override
	public boolean excluirAcesso(String identificacao, String nome,
			String rg, String email, String tipoAcesso) {
	    boolean encontrado=false;
	    Acesso a = new Acesso();
	    a.setNome("");
	    a.setRg("");
	    a.setEmail("");
	    a.setTipoAcesso("");
	    try{
	    Connection con = BD.getConnection();
	    String sql = "Select identificacao, nome, rg, email, senha_acesso, tipoAcesso from Acesso Where identificacao = ?";
	    PreparedStatement stmt=con.prepareStatement(sql);
	    stmt.setString(1, identificacao);
	    ResultSet rs=stmt.executeQuery();
	    while(rs.next()){
	    a.setNome(rs.getString("nome"));
	    System.out.println("Verificando:"+a.getNome());
	    a.setRg(rs.getString("rg"));
	    a.setEmail(rs.getString("email"));
	    a.setSenha(rs.getString("senha_acesso"));
	    a.setTipoAcesso(rs.getString("tipoAcesso"));
	    }
	    
	    if(a.getNome().equals("")){
		encontrado=false;
	    System.out.println("Nome vázio");
		  }
	    else
	    
	    if(a.getNome().equals(nome) && a.getRg().equals(rg) && a.getEmail().equals(email) && a.getTipoAcesso().equals(tipoAcesso)){
	    encontrado=true;
	    }
	    
	    
	  
	   
	    if(encontrado){
	    String exclui = "Delete Acesso Where identificacao = ?";
	    stmt=con.prepareStatement(exclui);
	    stmt.setString(1, identificacao);
	    stmt.executeUpdate();
	    }
	    
	    }
	    catch(SQLException e){
	    e.printStackTrace();
	    }
			return encontrado;
		}

	@Override
	public String verificarPrimeiroAcesso(String email, String senha) {
    String localizado="";
	try{
    Connection con = BD.getConnection();
    String verifica="Select primeiroAcesso from Acesso Where email = ? and senha_acesso = ?";
    PreparedStatement stmt=con.prepareStatement(verifica);
    stmt.setString(1, email);
    stmt.setString(2, senha);
    ResultSet acessoLocalizado=stmt.executeQuery();
    if(acessoLocalizado.next()){
    localizado=acessoLocalizado.getString("primeiroAcesso");
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		return localizado;
	}
	
	
	public boolean atualizaPrimeiroAcesso(String email, String senha){
	boolean atualizado=false;
	try{
    Connection con = BD.getConnection();
    String sql = "UPDATE acesso set primeiroAcesso = 'Não' Where email like ? and senha_acesso = ? ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, email);
    stmt.setString(2, senha);
    stmt.executeUpdate();
    atualizado=true;
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return atualizado;

}

	@Override
	public boolean atualizaNovaSenha(String email, String novaSenha) {
    boolean atualizado=false;
    System.out.println("Chegou:"+email);
	try{
    Connection con = BD.getConnection();
    String sql = "UPDATE Acesso set senha_acesso = ? Where email like ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, novaSenha);
    stmt.setString(2, email);
    stmt.executeUpdate();
    atualizado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
	return atualizado;
	}

	@Override
	public boolean atualizaEmailSenha(String identificacao, String novoEmail,
			String novaSenha) {
	boolean efetuado=false;
   try{
   Connection con = BD.getConnection();
   String sql = "UPDATE Acesso set email = ?, senha_acesso = ? Where identificacao = ? ";
   PreparedStatement stmt=con.prepareStatement(sql);
   stmt.setString(1, novoEmail);
   stmt.setString(2, novaSenha);
   stmt.setString(3, identificacao);
   

   stmt.executeUpdate();
   efetuado=true; 
   }
   catch(SQLException e){
	 e.printStackTrace();
   }
		return false;
	}
	
	
	public boolean validaAcessoSecretaria(String identificacao){
	boolean validado=false;
    String encontrado = "";
	try{
    Connection con = BD.getConnection();
    String sql = "Select identificacao from Acesso Where identificacao = ? and tipoAcesso = 'Secretaria' or tipoAcesso = 'Direcao' ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacao);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    encontrado=rs.getString("identificacao");
    }
    if(encontrado.equalsIgnoreCase(identificacao)){
    validado=true;
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return validado;
	
}
	
	
	public String localizaEmail(String identificacao){
	String emailLocalizado="";
	try{
    Connection con = BD.getConnection();
    String sql = "Select email from Acesso where identificacao = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacao);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    emailLocalizado=rs.getString("email");
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return emailLocalizado;
	}
}


          
	
	
	
	



