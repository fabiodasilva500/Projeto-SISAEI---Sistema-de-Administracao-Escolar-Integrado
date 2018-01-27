package Dao;

import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import Pojo.Boletim;

public interface BoletimDao {
	public boolean inserir(Boletim boletim, int selecaoCurso);
	public boolean incrementaStatus(Boletim boletim);
	public Boletim consultar(Boletim boletim, int selecaoConceito);
	public boolean alterar(Boletim boletim);
	public boolean atualizaStatus(Boletim boletim, String localizado);
	public boolean atualizaStatusNota(Boletim boletim, int IDCurso, String localizado);
	public boolean remover(Boletim boletim, int selecaoCurso, int conceito);
	public boolean verificaMencaoParcial(String identificacaoAluno, int selecaoTurma, int selecaoModulo, int IDCurso);
	public boolean verificaMencaoFinal(String identificacaoAluno, int selecaoTurma, int selecaoModulo, int IDCurso);
	public JasperPrint gerarPDF(int selecaoTurma, int selecaoDisciplina) throws JRException;
	public JasperPrint gerarBoletimParcial(String identificacao, int selecaoTurma,int selecaoModulo) throws JRException;
	public JasperPrint gerarBoletimFinal(String identificacao, int selecaoTurma, int selecaoModulo1) throws JRException;
	public JasperPrint acompanhamentoGeralDoEstudante(String identificacao,
			int selecaoTurma) throws JRException;
	public double consultaFaltas(int IDCurso, int IDTurma, int IDDisciplina, String identificacaoAluno);
	public boolean atualizarFrequenciaParcial(String identificacaoAluno, int IDDaTurma, int modulo, int curso, int conceito);
	public boolean atualizaStatusFinal (String identificacaoAluno, int IDDaTurma, int modulo, int curso, int conceito);
	public boolean atualizaFrequenciaFinal (Boletim boletim, int selecaoCurso, int conceito, String statusFinal);
	public boolean atualizaNumeroDeDependenciasFalta(int contadorFalta, int contadorNota, String identificacaoAluno, int IDDaTurma,
			String statusFinal);
	public boolean atualizaNumeroDeDependenciasNota(String identificacaoAluno, int IDDaTurma);
	public boolean verificaRetidoNotas(Boletim boletim, int IDCurso);
	public int subtraiRetidoNota(String identificacaoAluno, int IDDaTurma);   
	public boolean atualizaStatusCursoAluno(Boletim boletim);
	public boolean verificaRetencaoFalta(Boletim boletim);
	public double calculaTotalAulas(int IDCurso, int IDTurma, int IDDisciplina);
	public double localizaCargaHoraria(int IDDisciplina);
}
