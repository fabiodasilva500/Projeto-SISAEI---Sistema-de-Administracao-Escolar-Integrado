package Dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


import Pojo.Curso;
import Pojo.Turma;

public class CursoDaoImplementation implements CursoDao, Serializable{

	@Override
	public boolean inserir(Curso a) throws SQLException {
    boolean inserido = false;

    if(!a.getNome().equalsIgnoreCase("")){
       try{
       Connection con = BD.getConnection();			

		String sql = "insert into curso values (?,?,?,?,?,?,?,?)";

		PreparedStatement ps;

		ps = con.prepareStatement(sql);
		ps.setInt(1, a.getId());
		ps.setString(2, a.getNome());
		ps.setString(3, a.getDescricao());
		ps.setString(4, a.getSigla());
		ps.setString(5, a.getTipo());
		ps.setString(6, a.getEixoTecnologico());
		ps.setString(7, a.getCorArea());
		ps.setString(8, a.getCorCurso());
		ps.execute();
		ps.close();
		inserido = true;
	}
       catch(SQLException e){
    	  e.printStackTrace();
       }
    }
       return inserido;
	}


	@Override
	public Curso pesquisarPorNome(String nome) throws SQLException {


		Curso c=new Curso();

		try{
			Connection con = BD.getConnection();
			String sql = "SELECT * from curso Where nome like ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, "%"+nome+"%");
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setDescricao(rs.getString("descricao"));				
				c.setSigla(rs.getString("sigla"));
				c.setTipo(rs.getString("tipo"));
				c.setEixoTecnologico(rs.getString("eixoTecnologico"));
                c.setCorArea(rs.getString("corArea"));
				c.setCorCurso(rs.getString("corCurso"));
				}
			
		}
		catch(SQLException e){
			e.printStackTrace();	
		}


		return c;
	}

	@Override
	public boolean remover(int identificacao) throws SQLException {
    boolean removido = false;
	try{
    Connection con = BD.getConnection();
    String sql = "DELETE curso Where id = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setInt(1,identificacao);
    stmt.executeUpdate();
    removido = true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
	return removido;
	}

	@Override
	public boolean atualizar(int identificacao, Curso a) throws SQLException {
    boolean atualizado = false;
	try{
    Connection con = BD.getConnection();
    String sql = "UPDATE Curso set nome = ?, descricao = ?, sigla = ?, tipo = ?," +
    		"eixoTecnologico = ?, corArea = ?, corCurso = ? Where id = ?";
    
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, a.getNome());
    stmt.setString(2, a.getDescricao());
    stmt.setString(3, a.getSigla());
    stmt.setString(4, a.getTipo());
    stmt.setString(5, a.getEixoTecnologico());
    stmt.setString(6, a.getCorArea());
    stmt.setString(7, a.getCorCurso());
    stmt.setInt(8, identificacao);
    stmt.executeUpdate();
    atualizado = true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return atualizado;		
	}
	
	@Override
	public List<Curso> listaCursos() {
		List<Curso> boletins=new ArrayList<Curso>();
		try{
		Connection con = BD.getConnection();
		String sql = "Select id, nome from curso order by nome";
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		Curso b=new Curso();
		b.setId(rs.getInt("id"));
		b.setNome(rs.getString("nome"));
		boletins.add(b);
		}
	    }
		catch(SQLException e){
		e.printStackTrace();
		}
		return boletins;	
	}


	@Override
	public Curso localizaCursoAluno(String identificacaoAluno, int IDDaTurma) {
    Curso curso=new Curso();
	try{
    Connection con = BD.getConnection();
    String sql = "Select Curso.id as 'id' from curso "+
    "inner join Turma on Turma.curso = Curso.ID "+ 
    "inner join AlunoTurma on Turma.IDTurma = AlunoTurma.IDDaTurma "+
    "Where AlunoTurma.IdentificacaoAluno = ? and AlunoTurma.IDDaTurma = ?";
    
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    stmt.setInt(2, IDDaTurma);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    curso.setId(rs.getInt("id"));
    }
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		return curso;
	}


	@Override
	public int localizaIDCurso() {
		int localizado=0;
	    try{
	    Connection con = BD.getConnection();
	    String sql = "Select id from Curso";
	    PreparedStatement stmt=con.prepareStatement(sql);
	    ResultSet rs=stmt.executeQuery();
	    while(rs.next()){
	    localizado=rs.getInt("id");
	    }
	    }
	    catch(SQLException e){
	    e.printStackTrace();
	    }
			return localizado;
		}
	

}
