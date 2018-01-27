package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojo.AreaConcentracao;

public class AreaConcentracaoDaoImplementation implements AreaConcentracaoDao {

	@Override
	public boolean inserir(int IDCurso, AreaConcentracao area) {
		boolean inserido = false;
		try{
			Connection con = BD.getConnection();
			String sql = "Insert Into AreaConcentracao values (?,?,?,?)";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, IDCurso);
			stmt.setString(2, area.getNome());
			stmt.setString(3, area.getDescricao());
			stmt.setString(4, area.getResultadosEsperados());
			stmt.executeUpdate();
			inserido=true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return inserido;
	}

	@Override
	public boolean alterar(int IDCurso, AreaConcentracao area) {
		boolean alterado = false;
		try{
			Connection con = BD.getConnection();
			String sql = "Update AreaConcentracao set IDCurso = ?, descricao = ?, resultadosEsperados = ? " +
					"where nome = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, IDCurso);
			stmt.setString(2, area.getDescricao());
			stmt.setString(3, area.getResultadosEsperados());
			stmt.setString(4, area.getNome());
			stmt.executeUpdate();
			alterado = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return alterado;
	}

	@Override
	public boolean excluir(String nome) {
		boolean excluido = false; 
		try{
			Connection con = BD.getConnection();
			String sql = "Delete AreaConcentracao where nome = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1,nome);
			stmt.executeUpdate();
			excluido = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return excluido;
	}

	@Override
	public AreaConcentracao pesquisar(int IDCurso, String nome) {
	 AreaConcentracao areaConcentracao=new AreaConcentracao();
     try{
    Connection con = BD.getConnection();
    String sql = "Select nome, descricao, resultadosEsperados from AreaConcentracao where nome = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, nome);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    areaConcentracao.setNome(rs.getString("nome"));
    areaConcentracao.setDescricao(rs.getString("descricao"));
    areaConcentracao.setResultadosEsperados(rs.getString("resultadosEsperados"));
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return areaConcentracao;
	}
	
	public List<AreaConcentracao> listaAreas(int selecaoCurso){
	List<AreaConcentracao> listaAreas=new ArrayList<AreaConcentracao>();
	try{
	Connection con = BD.getConnection();
	String sql = "Select IDArea, nome from AreaConcentracao where IDCurso = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, selecaoCurso);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	AreaConcentracao area=new AreaConcentracao();
	area.setIDArea(rs.getInt("IDArea"));
	area.setNome(rs.getString("nome"));
	listaAreas.add(area);
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaAreas;
	}

}
