package Dao;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojo.Disciplina;

public class DisciplinaDaoImplementation  implements DisciplinaDao, Serializable{

	@Override
	public boolean inserir(Disciplina d) {
		boolean inserido = false;
		if(!d.getNome().equalsIgnoreCase("")){
		try{
			Connection con = BD.getConnection();
			String sql = "INSERT INTO disciplina values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, d.getIDDisciplina());
			stmt.setString(2, d.getNome());
			stmt.setString(3, d.getSigla());
			stmt.setString(4, d.getDescricao());
			stmt.setString(5, d.getAta());
			stmt.setDouble(6, d.getCargaHoraria());
			stmt.setInt(7, d.getCurso());
            stmt.setInt(8, d.getModulo());
            stmt.setInt(9, d.getIDAreaConcentracao());
			stmt.executeUpdate();
			inserido = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		}

		return inserido;
	}

	@Override
	public boolean atualizar(int IDDisciplina, Disciplina d) {
		System.out.println("Area:"+d.getIDAreaConcentracao());
		
		boolean atualizado = false;
		try{
			Connection con=BD.getConnection();
			String sql = "UPDATE disciplina set nome = ?, sigla = ?, descricao = ?,"+
					"ata = ?, cargaHoraria = ?, curso = ?, modulo = ?, areaConcentracao = ? Where IDDisciplina = ?";		
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, d.getNome());
			stmt.setString(2, d.getSigla());
			stmt.setString(3, d.getDescricao());
			stmt.setString(4, d.getAta());
			
			System.out.println(d.getIDDisciplina());
            System.out.println(d.getCargaHoraria());
			stmt.setDouble(5, d.getCargaHoraria());
			stmt.setInt(6, d.getCurso());
			stmt.setInt(7, d.getModulo());
			stmt.setInt(8, d.getIDAreaConcentracao());
			stmt.setInt(9, d.getIDDisciplina());
			stmt.executeUpdate();
			atualizado = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return atualizado;
	}

	@Override
	public boolean remover(int IDDisciplina) {
		boolean removido = false;
		try{
			Connection con = BD.getConnection();
			String sql = "DELETE disciplina Where IDDisciplina = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, IDDisciplina);
			stmt.executeUpdate();
			removido=true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return removido;
	}

	@Override
	public Disciplina pesquisar(Disciplina disciplina) {
		Disciplina d=new Disciplina();

		try{
			Connection con = BD.getConnection();
			String sql = "SELECT IDDisciplina, nome, sigla, descricao, ata, cargaHoraria, curso, modulo, areaConcentracao from Disciplina Where nome = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, disciplina.getNome());
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){
				d.setIDDisciplina(rs.getInt("IDDisciplina"));
				d.setNome(rs.getString("nome"));
				d.setSigla(rs.getString("sigla"));
				d.setDescricao(rs.getString("descricao"));
				d.setAta(rs.getString("ata"));
				d.setCargaHoraria(rs.getDouble("cargaHoraria"));
				d.setCurso(rs.getInt("curso"));
				d.setModulo(rs.getInt("modulo"));
			    d.setIDAreaConcentracao(rs.getInt("areaConcentracao"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return d;
	}
	
	
	public List<Disciplina> listaDisciplina(int curso, int modulo){
	List<Disciplina> listaDisciplinas=new ArrayList<Disciplina>();
	try{
	Connection con = BD.getConnection();
	String sql = "Select IDDisciplina, nome from Disciplina Where curso = ? and modulo = ? order by nome ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, curso);
	stmt.setInt(2, modulo);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	Disciplina disciplina=new Disciplina();
	disciplina.setIDDisciplina(rs.getInt("IDDisciplina"));
	disciplina.setNome(rs.getString("nome"));
	listaDisciplinas.add(disciplina);
    }
	}
    catch(SQLException e){
    e.printStackTrace();
    }
	return listaDisciplinas;
	}

	@Override
	public String localizaNome(int IDDisciplina) {
    String nomeLocalizado=null;
    try{
    Connection con = BD.getConnection();
    String sql = "Select nome from Disciplina Where IDDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, IDDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    nomeLocalizado=rs.getString("nome");
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return nomeLocalizado;
		
	}

	@Override
	public int localizaIDDisciplina() {
	int localizado=0;
    try{
    Connection con = BD.getConnection();
    String sql = "Select IDDisciplina from Disciplina";
    PreparedStatement stmt=con.prepareStatement(sql);
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    localizado=rs.getInt("IDDisciplina");
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return localizado;
	}
	


	public int cargaHoraria (int IDDisciplina){
	int cargaHoraria=0;
	try{
	Connection con = BD.getConnection();
	String sql = "Select cargaHoraria from Disciplina Where IDDisciplina = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1, IDDisciplina);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    cargaHoraria = rs.getInt("cargaHoraria");
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return cargaHoraria;
	}
	
	public List<String> localizaDisciplinas(int curso, int modulo){
		System.out.println("Curso:"+curso);
		System.out.println("Módulo:"+modulo);
		
	List<String> listaDisciplinas=new ArrayList<String>();
	try{
	Connection con = BD.getConnection();
	String sql  = "Select nome from Disciplina Where curso = ? and modulo = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, curso);
	stmt.setInt(2, modulo);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	listaDisciplinas.add(rs.getString("nome"));
    }
	System.out.println("Chegou:"+listaDisciplinas.size());
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaDisciplinas;
	}
	
	
	
	public List<String> localizaDisciplinaCurso(int curso){		
	List<String> listaDisciplinas=new ArrayList<String>();
	try{
	Connection con = BD.getConnection();
	String sql  = "Select nome from Disciplina Where curso = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, curso);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	listaDisciplinas.add(rs.getString("nome"));
    }
	System.out.println("Chegou:"+listaDisciplinas.size());
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaDisciplinas;
	}
	
	
	
	public List<Disciplina> localizaDisciplinasCurso(int curso){		
	List<Disciplina> listaDisciplinas=new ArrayList<Disciplina>();
	try{
	Connection con = BD.getConnection();
	String sql  = "Select IDDisciplina, nome from Disciplina Where curso = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, curso);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	Disciplina d=new Disciplina();
	d.setIDDisciplina(rs.getInt("IDDisciplina"));
	d.setNome(rs.getString("nome"));
	listaDisciplinas.add(d);
	}
	System.out.println("Chegou:"+listaDisciplinas.size());
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaDisciplinas;
	}
	
	
	

	public List<Disciplina> localizaDisciplinasCursoModulo(int curso, int modulo){		
	List<Disciplina> listaDisciplinas=new ArrayList<Disciplina>();
	try{
	Connection con = BD.getConnection();
	String sql  = "Select IDDisciplina, nome from Disciplina Where curso = ? and modulo = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, curso);
	stmt.setInt(2, modulo);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	Disciplina d=new Disciplina();
	d.setIDDisciplina(rs.getInt("IDDisciplina"));
	d.setNome(rs.getString("nome"));
	listaDisciplinas.add(d);
	}
	System.out.println("Chegou:"+listaDisciplinas.size());
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return listaDisciplinas;
	}
	
	public int localizaDisciplinas(int curso){
	int disciplinas=0;
	try{
	Connection con = BD.getConnection();
	String sql = "select Count (nome) as 'nome' from disciplina where curso = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, curso);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	disciplinas=rs.getInt("nome");
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return disciplinas;
	}
	
	
}	
