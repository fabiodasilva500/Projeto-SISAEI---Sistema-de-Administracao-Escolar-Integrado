package Dao;

import java.util.List;

import Pojo.Estatistica;
import Pojo.Turma;

public interface EstatisticaDao {

	public List<Integer> estatisticaTurmaParcial(int selecaoTurma);
	public List<Integer> estatisticaTurmaFinal(int selecaoTurma);
	public List<Integer> estatisticaDisciplinaParcial(int selecaoTurma,
			int selecaoDisciplina);
	public List<Integer> estatisticaDisciplinaFinal(int selecaoTurma,
			int selecaoDisciplina);
	public List<Integer> localizaDadosComparativos(int selecaoCurso, int selecaoModulo, int selecaoAno, int selecaoSemestre);
	public List<Integer> localizaDadosAluno(int IDDaTurma, String identificacao, int selecaoModulo);
	public List<Turma> localizaTurmaAluno(String identificacaoAluno, int selecaoCurso);
	public List<Integer> estatisticaAreaConcentracao(int selecaoArea, int IDTurma);
    public List<Integer> localizaDadosAreaTurma(int selecaoCurso, int selecaoModulo, int selecaoAno, int selecaoSemestre, int selecaoArea);
	public List<Integer> localizaDadosAreaAluno(int IDDaTurma, String identificacao, int selecaoModulo, int selecaoArea);
    public List<Integer>  estatisticaConcluintes (List<String>listaIdentificoes, int curso, int disciplinasDoCurso, int matriculados);
    public int localizaAluno(String identificacao);
}


