package Dao;

import Pojo.Aluno;

import java.util.List;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface AlunoDao {
	public boolean inserir(Aluno a) throws SQLException;
	public Aluno pesquisarPorId(String identificacao) throws SQLException;
	List<Aluno> pesquisarPorNome(String nome) throws SQLException;
	public boolean remover (String identificacao) throws SQLException;
	public boolean atualizar (String identificacao, Aluno a) throws SQLException;
	public List<Aluno> consultaAluno(int IDTurma);
	public String localizaNomeAluno(String identificacaoAluno);
	public boolean validaNomeRG(String identificacao, String rg, String nome);
	public JasperPrint gerarPDF() throws JRException;
} 
