package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Pojo.Questionario;

public class QuestionarioDaoImplementation implements QuestionarioDao{

	@Override
	public boolean inserir(Questionario q) {
    boolean inserido=false;
	try{
    Connection con = BD.getConnection();
    String sql = "INSERT INTO Questionario Values (?,?,?,?)";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, q.getIdentificacaoAluno());
    stmt.setInt(2, q.getIDTurma());
    stmt.setString(3, q.getQuestao());
    stmt.setString(4, q.getResposta());
    
    stmt.executeUpdate();
    inserido=true;
   
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		return inserido;
	}

	@Override
	public boolean excluir(Questionario q) {
    boolean excluido=false;
    try{
    Connection con= BD.getConnection();
    String sql = "DELETE Questionario Where identificacaoAluno = ? and IDTurma = ? and questao = ? and resposta = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, q.getIdentificacaoAluno());
    stmt.setInt(2, q.getIDTurma());
    stmt.setString(3, q.getQuestao());
    stmt.setString(4, q.getResposta());

    stmt.executeUpdate();
    excluido=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
		return excluido;
	}

	@Override
	public boolean atualizar(Questionario q) {
	boolean atualizado=false;
	try{
	Connection con = BD.getConnection();
	String sql = "UPDATE Questionario set resposta = ? Where questao = ? and identificacaoAluno = ? and IDDTurma = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, q.getQuestao());
	stmt.setString(2, q.getResposta());
	stmt.setString(3, q.getIdentificacaoAluno());
	stmt.setInt(4, q.getIDTurma());
    stmt.executeUpdate();
    atualizado=true;
	}
	catch(SQLException e){
    e.printStackTrace();
	}
		return atualizado;
	}

}
