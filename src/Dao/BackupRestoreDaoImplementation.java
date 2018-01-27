package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class BackupRestoreDaoImplementation implements BackupRestoreDao{

	
	public boolean efetuarBackupSimples (String database, String diretorio){ 


	boolean efetuado=false;
	try{
	Connection con = BD.getConnection();
	String sql = "{call backup_database_selecionada(?,?)}";
	CallableStatement cs=con.prepareCall(sql);
	cs.setString(1, database);
	cs.setString(2, diretorio);
	cs.execute();
	cs.close();	
	efetuado=true;
    }
	catch(Exception e){
	e.printStackTrace();
	}
	return efetuado;
	}
	
	
	public boolean efetuarRestoreSimples(String database, String diretorio){    
	boolean efetuado=false;
	Connection con =null;
	try{
    con = BD.getConnection();
	con.close();
	System.out.println("Fechando a conexao");
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	
	try{
    con = BD.getConnectionMaster();
	String sql = "{call restore_database (?,?)}";
	CallableStatement cs=con.prepareCall(sql);
	cs.setString(1, database);

	cs.setString(2, diretorio);
	cs.execute();
	cs.close();
	efetuado=true;
	BD.close(con);
	}
	catch(Exception e){
	e.printStackTrace();
   }
	return efetuado;
	}

	
	public boolean verificaExistencia (String database, String diretorio, String sobrepor){
	int databaseExistente=0;
	sobrepor="Sim";
	
	System.out.println("Database:"+database);
	System.out.println("Diretório:"+diretorio);
	
	boolean efetuado = false;
	try{
	Connection con = BD.getConnection();
	String sql = "Select dbo.verifica_existencia(?,?)";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, database);
	stmt.setString(2, diretorio);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	databaseExistente=(rs.getInt(1));
	if(databaseExistente==0){
    efetuado=efetuarRestoreSimples(database, diretorio);
	}
	
	}
	stmt.close();
	}
	catch(Exception e){
	e.printStackTrace();
    }
	
	return efetuado;
	}
	
}