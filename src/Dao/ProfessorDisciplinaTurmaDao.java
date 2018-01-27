package Dao;

import java.util.List;

import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.ProfessorDisciplinaTurma;
import Pojo.Turma;

public interface ProfessorDisciplinaTurmaDao {
public boolean inserir(ProfessorDisciplinaTurma pt);
public boolean excluir(int  IDDoProfessor, int IDDaTurma, int IDDaDisciplina);
public List<ProfessorDisciplinaTurma> pesquisar(int IDDaTurma);
public List<Curso> listaCursos(int IDDoProfessor, int ano, int semestre);
public List<Turma> localizaTurmaModulo(int IDCurso, int IDDoProfessor, int ano, int modulo, int semestre);
public List<Turma> localizaTurmaCurso(int selecaoCurso, int selecaoProfessor,
		int ano, int semestre);
public List<Disciplina> listaDisciplinas(int curso, int ano, int semestre, int modulo, int IDDoProfessor);
}
