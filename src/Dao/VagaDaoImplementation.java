package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Pojo.Vaga;

public class VagaDaoImplementation implements VagaDao {

	@Override
	public boolean inserir(Vaga vaga) {
	boolean inserido = false;
	Connection con = BD.getConnection();	     
	try{
    String sql = "INSERT INTO VagaEmprego values (?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, vaga.getIDOportunidade());
    stmt.setInt(2, vaga.getCurso());
    stmt.setString(3, vaga.getNomeEmpresa());
    stmt.setString(4, vaga.getCargo());
    stmt.setString(5, vaga.getDescricao());
    stmt.setString(6, vaga.getRequisitos());
    stmt.setString(7, vaga.getDiferenciais());
    stmt.setString(8, vaga.getEmail());
    stmt.setDate(9, new java.sql.Date(vaga.getDataInicial().getTime()));
    stmt.setDate(10, new java.sql.Date(vaga.getDataFinal().getTime()));
    stmt.executeUpdate();
    inserido=true;
	}
     catch(SQLException e){
    	e.printStackTrace();
     }
		return inserido;
	}

	@Override
	public List<Vaga> pesquisarPorEmpresa(String nomeEmpresa, Date dataInicial, Date dataFinal) {
	List<Vaga> listaVagas=new ArrayList<Vaga>();

    try{
    Connection con = BD.getConnection();
    String sql = "Select IDVaga, IDCurso, empresa, cargo, descricao, requisitos, diferenciais, email, " +
    "dataInicial, dataExpiracao from VagaEmprego where empresa = ? and dataInicial >= ? and dataExpiracao <= ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, nomeEmpresa);
    stmt.setDate(2, new java.sql.Date(dataInicial.getTime()));  
    stmt.setDate(3, new java.sql.Date(dataFinal.getTime()));
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	Vaga v=new Vaga();
	v.setIDOportunidade(rs.getInt("IDVaga"));
	v.setCurso(rs.getInt("IDCurso"));
	v.setNomeEmpresa(rs.getString("empresa"));
	v.setCargo(rs.getString("cargo"));
	v.setDescricao(rs.getString("descricao"));
	v.setRequisitos(rs.getString("requisitos"));
	v.setDiferenciais(rs.getString("diferenciais"));
	v.setEmail(rs.getString("email"));
	v.setDataInicial(new java.util.Date(rs.getDate("dataInicial").getTime()));
	v.setDataFinal(new java.util.Date(rs.getDate("dataExpiracao").getTime()));
	listaVagas.add(v);
	}
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return listaVagas;
	}
	
	
	@Override
	public List<Vaga> pesquisarPorData(Date dataInicial, Date dataFinal) {
	    List<Vaga> listaVagas=new ArrayList<Vaga>();
	    try{
	    Connection con = BD.getConnection();
	    String sql = "Select IDVaga, IDCurso, empresa, cargo, descricao, requisitos, diferenciais, email, " +
	    "dataInicial, dataExpiracao from VagaEmprego where dataInicial >= ? and dataExpiracao <= ?";
	    PreparedStatement stmt=con.prepareStatement(sql);
	    stmt.setDate(1, new java.sql.Date(dataInicial.getTime()));  
	    stmt.setDate(2, new java.sql.Date(dataFinal.getTime()));
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		Vaga v=new Vaga();
		v.setIDOportunidade(rs.getInt("IDVaga"));
		v.setCurso(rs.getInt("IDCurso"));
		v.setNomeEmpresa(rs.getString("empresa"));
		v.setCargo(rs.getString("cargo"));
		v.setDescricao(rs.getString("descricao"));
		v.setRequisitos(rs.getString("requisitos"));
		v.setDiferenciais(rs.getString("diferenciais"));
		v.setEmail(rs.getString("email"));
		v.setDataInicial(new java.util.Date(rs.getDate("dataInicial").getTime()));
		v.setDataFinal(new java.util.Date(rs.getDate("dataExpiracao").getTime()));
		listaVagas.add(v);
		}
	    }
	    catch(SQLException e){
	    e.printStackTrace();
	    }
	    return listaVagas;
	}
	
	

	@Override
	public boolean atualizar(Vaga vaga) {
    boolean atualizado= false;
    try{
    Connection con = BD.getConnection();
    String sql = "UPDATE VagaEmprego set requisitos = ?, diferenciais = ?, email = ?" +
    		"Where IDCurso = ? and empresa = ? and cargo = ? and descricao = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, vaga.getRequisitos());
    stmt.setString(2, vaga.getDiferenciais());
    stmt.setString(3, vaga.getEmail());
    stmt.setInt(4, vaga.getCurso());
    stmt.setString(5, vaga.getNomeEmpresa());
    stmt.setString(6, vaga.getCargo());
    stmt.setString(7, vaga.getDescricao());
    stmt.executeUpdate();
    atualizado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return atualizado;
	}

	@Override
	public boolean remover(int IDVaga) {
     boolean excluido=false;
     try{
     Connection con = BD.getConnection();
     String sql = "Delete VagaEmprego where IDVaga = ?";
     PreparedStatement stmt=con.prepareStatement(sql);
     stmt.setInt(1, IDVaga);
     stmt.executeUpdate();
     excluido = true;
     }
     catch(SQLException e){
    	e.printStackTrace();
     }
		return excluido;
	}

	@Override
	public int localizaIDVaga() {
		int localizado=0;
	    try{
	    Connection con = BD.getConnection();
	    String sql = "Select IDVaga from VagaEmprego";
	    PreparedStatement stmt=con.prepareStatement(sql);
	    ResultSet rs=stmt.executeQuery();
	    while(rs.next()){
	    localizado=rs.getInt("IDVaga");
	    }
	    }
	    catch(SQLException e){
	    e.printStackTrace();
	    }
			return localizado;
	
	}

}
