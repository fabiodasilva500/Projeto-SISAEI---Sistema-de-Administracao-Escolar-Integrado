package Dao;

import java.sql.SQLException;
import java.util.List;

import net.sf.jasperreports.engine.JRException;

import Pojo.Matricula;

public interface AlunoTurmaDao {
	public boolean inserir(Matricula at) throws SQLException;
	public List<Matricula> pesquisar(String identificacao) throws SQLException;
	public boolean remover (String identificacao, int IDTurma, int selecaoCurso) throws SQLException;
	public void preparaPDF(int idTurma) throws JRException;
	public void atualizaTurma (String identificacao, int IDTurma, int curso);
	public int localizaMatriculados(int semestreInicial, int anoInicial, int curso);
	public List<String> localizaIdentificacoes(int semestreInicial, int anoInicial, int curso);
	public int localizaTrancamento(String identificacao);
	   
}

