package Dao;

import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import Pojo.Aluno;
import Pojo.Diario;
import Pojo.Disciplina;



public interface DiarioDao {
	public boolean inserirDiario(Diario a, int idDiario);
	public boolean consultarDiario();
	public boolean alterarDiario();
	public boolean excluirDiario(int idTurma, int idDisciplina, int idProfessor, String dataAula);
	public List<Disciplina> listaDisciplinaProf(int idProfessor);
	public List<Aluno> listaAlunos(int idTurma);
	public boolean inserirPresenca(Aluno a, int id);
	public boolean excluirPresenca(Aluno a, int id);
	public int ultimoDiario();
	public JasperPrint gerarPDF(int idTurma, int idDisciplina, int idProfessor,
			Date dataAula) throws JRException;

	public JasperPrint verificarAlunosAusentesTurma(int IDTurma,
			Date dataInicial, Date dataFinal);
	public JasperPrint verificarAlunosAusentesDisciplina(int IDTurma,
			int IDDisciplina, Date dataInicial, Date dataFinal);

	public List<Integer> graficoChamada(int IDTurmal, Date dataInicial, Date dataFinal);
	public List<Integer> graficoChamadaDisciplina(int IDTurma, int IDDisciplina, Date dataInicial, Date dataFinal);

	public boolean inserirAtraso(Aluno a, Diario d);
	public boolean excluirAtraso(Aluno a,  Diario d);
	
	
}
