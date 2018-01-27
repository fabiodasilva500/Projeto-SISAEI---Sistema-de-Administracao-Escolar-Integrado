package Dao;

import java.sql.SQLException;
import java.util.List;

import Pojo.Aluno;
import Pojo.Curso;

public interface CursoDao {
	public boolean inserir(Curso a) throws SQLException;
	Curso pesquisarPorNome(String nome) throws SQLException;
	public boolean remover (int identificacao) throws SQLException;
	public boolean atualizar (int identificacao, Curso a) throws SQLException;
	public List<Curso> listaCursos();
    public Curso localizaCursoAluno(String identificacao, int IDDaTurma);
	public int localizaIDCurso();
    
}
