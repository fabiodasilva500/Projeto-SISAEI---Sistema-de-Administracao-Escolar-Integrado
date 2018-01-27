package Dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/projetoLabEng";
	private static String JDBC_USER = "root";
	private static String JDBC_PASSWORD = "pipeline";
	private static Driver driver=null;

	public static synchronized Connection getConnection() throws SQLException{
		if(driver==null){
			try{
				Class jdbcDriverClass = Class.forName( JDBC_DRIVER );
				driver = (Driver) jdbcDriverClass.newInstance();
				DriverManager.registerDriver(driver);
			}
			catch(Exception e){
				System.out.println("Falha na inicialização do driver");
				e.printStackTrace();
			}
		}
		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	}
	
	
	public static synchronized Connection getConnectionMaster() throws SQLException{
		if(driver==null){
			try{
				Class jdbcDriverClass = Class.forName( JDBC_DRIVER );
				driver = (Driver) jdbcDriverClass.newInstance();
				DriverManager.registerDriver(driver);
			}
			catch(Exception e){
				System.out.println("Falha na inicialização do driver");
				e.printStackTrace();
			}
		}
		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	}


	public static void close(Connection conn){
		try{
			if(conn!=null)
				conn.close();
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
	}
}



