package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pojo.Trancamento;

public class TrancamentoDaoImplementation implements TrancamentoDao{

	@Override
	public boolean inserir(Trancamento trancamento) {
	boolean efetuado=false;
	try{
	Connection con = BD.getConnection();
	String sql = "Insert into Trancamento values (?,?,?,?)";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, trancamento.getIdentificacao());
	stmt.setInt(2, trancamento.getCurso());
	stmt.setInt(3, trancamento.getSemestre());
    stmt.setInt(4, trancamento.getAno());
	stmt.executeUpdate();
    efetuado=true;
	}
	catch(SQLException e){
	e.printStackTrace();
	}
		return efetuado;
	}


	@Override
	public boolean excluir(String identificacaoAluno) {
    boolean efetuado = false;
    try{
    Connection con = BD.getConnection();
    String sql = "Delete Trancamento where identificacaoAluno = ?";
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

	@Override
	public Trancamento consultar(Trancamento trancamento) {
	Trancamento trancado=new Trancamento();
	try{
	Connection con = BD.getConnection();
	String sql = "Select curso, semestre, ano from Trancamento where identificacaoAluno = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, trancamento.getIdentificacao());
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	trancamento.setCurso(rs.getInt("curso"));
	trancamento.setSemestre(rs.getInt("semestre"));
	trancamento.setAno(rs.getInt("ano"));
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
		return trancamento;
	}

}
