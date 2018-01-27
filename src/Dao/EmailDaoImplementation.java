package Dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pojo.Email;

public class EmailDaoImplementation  implements EmailDao, Serializable{
	private String caminhoArquivo;


	public void Inserir(String caminhoArquivo){
		try{
			remover();
			Connection con = BD.getConnection();
			String sql = "{call insereDados(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, caminhoArquivo);
			cs.execute();
			cs.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}


	public String pesquisar(){
		String em = null;
		try{
			Connection con = BD.getConnection();
			String sql = "Select caminhoArquivo from caminho";
			PreparedStatement stmt=con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				em=rs.getString("caminhoArquivo");
				remover();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return em;
	}


	public void remover(){
		try{
			Connection con = BD.getConnection();
			String sql = "{call deletaDados}";
			CallableStatement cs = con.prepareCall(sql);
			cs.execute();
			cs.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
