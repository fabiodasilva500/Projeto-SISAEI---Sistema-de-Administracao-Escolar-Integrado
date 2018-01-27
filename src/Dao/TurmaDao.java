package Dao;

import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import Pojo.ProfessorDisciplinaTurma;
import Pojo.Turma;

public interface TurmaDao {
	public boolean inserir(Turma t);
	public boolean atualizar (int IDTurma, Turma t);
	public boolean remover (int IDTurma);
	public Turma pesquisar(Turma turma);
	public void preparaPDF() throws JRException;
	public JasperPrint gerarPDF() throws JRException;
	public List<Turma> listaTurmaCurso(int id, int selecaoSemestre, int selecaoAno);
	public List<Turma> listaTurmaModulo(int idCurso, int modulo, int selecaoSemestre, int selecaoAno);
	public String localizaNome(int IDTurma);
	public Turma localizaTurmaAluno(String identificacao);
	public List<Turma> listaTurmasAnoCurso(int semestre, int ano, int curso);
	public List<Turma> listaTurmasAnoCursoHist(int semestre, int ano, int curso);
	public List<Turma> listaTurmasAnoCursoModulo(int semestre, int ano, int curso, int modulo);
	public List<ProfessorDisciplinaTurma> listaTurmaProfessor(int idProfessor);
	public int localizaIDTurma();
	public Turma localizaDadosTurma(int iDTurma);
}
