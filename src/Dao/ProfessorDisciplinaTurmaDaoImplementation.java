package Dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.ProfessorDisciplinaTurma;
import Pojo.Turma;

public class ProfessorDisciplinaTurmaDaoImplementation implements ProfessorDisciplinaTurmaDao, Serializable {

	@Override
	public boolean inserir(ProfessorDisciplinaTurma pt) {
		boolean inserido = false; 
		try{
			Connection con = BD.getConnection();
			String sql = "INSERT INTO ProfessorDisciplinaTurma values (?,?,?)";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, pt.getIDDoProfessor());
			stmt.setInt(2, pt.getIDDaTurma());
			stmt.setInt(3, pt.getIDDaDisciplina());
			stmt.executeUpdate();
			inserido = true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return inserido;
	}

	@Override
	public boolean excluir(int IDDoProfessor, int IDDaTurma, int IDDaDisciplina) {
		boolean excluido = false; 
		try{
			Connection con = BD.getConnection();
			String sql = "DELETE ProfessorDisciplinaTurma Where IDDoProfessor = ? and IDDaTurma = ? and IDDaDisciplina = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, IDDoProfessor);
			stmt.setInt(2, IDDaTurma);
			stmt.setInt(3, IDDaDisciplina);
			stmt.executeUpdate();
			excluido=true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return excluido;
	}

	@Override
	public List<ProfessorDisciplinaTurma> pesquisar(int IDDaTurma) {
		List<ProfessorDisciplinaTurma> professoresDaTurma=new ArrayList<ProfessorDisciplinaTurma>();
		try{
			Connection con = BD.getConnection();
			String sql = "Select IDDoProfessor, IDDaTurma, IDDaDisciplina from ProfessorDisciplinaTurma Where IDDoProfessor = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, IDDaTurma);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				ProfessorDisciplinaTurma p = new ProfessorDisciplinaTurma();
				p.setIDDoProfessor(rs.getInt("IDDoProfessor"));
				p.setIDDaTurma(rs.getInt("IDDaTurma"));
		        p.setIDDaDisciplina(rs.getInt("IDDaDisciplina"));
				professoresDaTurma.add(p);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return professoresDaTurma;

	}
	
	
	
public List<Curso> listaCursos(int IDDoProfessor, int ano, int semestre){
List<Curso> cursos=new ArrayList<Curso>();
try{
Connection con = BD.getConnection();
String sql = "select distinct curso.id, curso.nome  "+
"from curso "+
"inner join turma "+
"on curso.id=turma.curso "+
"inner join ProfessorDisciplinaTurma "+
"on turma.IDTurma= ProfessorDisciplinaTurma.IDDaTurma "+
"inner join Professor "+
"on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
"Where ProfessorDisciplinaTurma.IDDoProfessor = ? and Turma.ano = ? and Turma.semestre = ? order by curso.nome ";
PreparedStatement stmt=con.prepareStatement(sql);
stmt.setInt(1, IDDoProfessor);
stmt.setInt(2, ano);
stmt.setInt(3, semestre);
ResultSet rs=stmt.executeQuery();
while(rs.next()){
Curso c=new Curso();
c.setId(rs.getInt("id"));
c.setNome(rs.getString("nome"));

System.out.println("ID Localizado:"+c.getId());

cursos.add(c);
}
}
catch(SQLException e){
e.printStackTrace();
}
return cursos;	
}


public List<Turma> localizaTurmaCurso(int IDCurso, int IDDoProfessor, int ano, int semestre){
List<Turma> turmas=new ArrayList<Turma>();
try{
Connection con = BD.getConnection();
String sql = "select distinct turma.IDTurma , turma.nome "+
"from turma "+
"inner join curso "+ 
"on turma.curso = curso.id "+
"inner join ProfessorDisciplinaTurma "+
"on turma.IDTurma= ProfessorDisciplinaTurma.IDDaTurma "+
"inner join Professor "+
"on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
"Where  curso.id = ?  and ProfessorDisciplinaTurma.IDDoProfessor = ? and Turma.ano = ? and Turma.semestre = ? order by turma.nome ";
PreparedStatement stmt=con.prepareStatement(sql);
stmt.setInt(1, IDCurso);
stmt.setInt(2, IDDoProfessor);
stmt.setInt(3, ano);
stmt.setInt(4, semestre);
ResultSet rs=stmt.executeQuery();
while(rs.next()){
Turma t=new Turma();
t.setIDTurma(rs.getInt("IDTurma"));
t.setNome(rs.getString("nome"));
turmas.add(t);
}
}
catch(SQLException e){
e.printStackTrace();
}
return turmas;
}



public List<Turma> localizaTurmaModulo(int IDCurso, int IDDoProfessor, int ano, int modulo, int semestre){
List<Turma> turmas=new ArrayList<Turma>();
try{
Connection con = BD.getConnection();
String sql = "select distinct turma.IDTurma , turma.nome "+
"from turma "+
"inner join curso "+ 
"on turma.curso = curso.id "+
"inner join ProfessorDisciplinaTurma "+
"on turma.IDTurma= ProfessorDisciplinaTurma.IDDaTurma "+
"inner join Professor "+
"on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
"Where  curso.id = ? and ProfessorDisciplinaTurma.IDDoProfessor = ? and Turma.ano = ? and Turma.modulo = ? and Turma.semestre = ? order by turma.nome ";
PreparedStatement stmt=con.prepareStatement(sql);
stmt.setInt(1, IDCurso);
stmt.setInt(2, IDDoProfessor);
stmt.setInt(3, ano);
stmt.setInt(4, modulo);
stmt.setInt(5, semestre);
ResultSet rs=stmt.executeQuery();
while(rs.next()){
Turma t=new Turma();
t.setIDTurma(rs.getInt("IDTurma"));
t.setNome(rs.getString("nome"));
turmas.add(t);
}
}
catch(SQLException e){
e.printStackTrace();
}
return turmas;
}




public List<Disciplina> listaDisciplinas(int curso, int ano, int semestre, int modulo, int IDDoProfessor){
List<Disciplina> disciplinas=new ArrayList<Disciplina>();
try{
Connection con = BD.getConnection();
String sql = "select  distinct disciplina.IDDisciplina, disciplina.nome  "+
"from curso "+
"inner join turma "+
"on curso.id=turma.curso "+
"inner join ProfessorDisciplinaTurma "+
"on turma.IDTurma= ProfessorDisciplinaTurma.IDDaTurma "+
"inner join Professor "+
"on Professor.IDProfessor = ProfessorDisciplinaTurma.IDDoProfessor "+
"inner join Disciplina "+
"on disciplina.IDDisciplina=ProfessorDisciplinaTurma.IDDaDisciplina "+
"Where curso.id= ? and Turma.ano = ? and Turma.semestre = ? and Turma.modulo = ? and ProfessorDisciplinaTurma.IDDoProfessor = ? order by disciplina.nome ";
PreparedStatement stmt=con.prepareStatement(sql);
stmt.setInt(1, curso);
stmt.setInt(2, ano);
stmt.setInt(3, semestre);
stmt.setInt(4, modulo);
stmt.setInt(5, IDDoProfessor);
ResultSet rs=stmt.executeQuery();
while(rs.next()){
Disciplina d=new Disciplina();
d.setIDDisciplina(rs.getInt("IDDisciplina"));
d.setNome(rs.getString("nome"));
disciplinas.add(d);
}
}
catch(SQLException e){
e.printStackTrace();
}
return disciplinas;
}
}
