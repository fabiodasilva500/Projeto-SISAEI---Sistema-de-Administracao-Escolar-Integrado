package Dao;

import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import Pojo.Professor;

public interface ProfessorDao {
	public boolean inserir(Professor p);
	public boolean alterar (int IDProfessor, Professor p);
	public List<Professor> pesquisarPorNome(String nome);
	public Professor pesquisarIdentificacao(int IDProfessor);
	public boolean excluir (int IDProfessor);
	public String localizaNomeProfessor(int IDProfessor);
    public List<Professor> listaProfessores();
    public String localizaNomeProfessor(String identificacaoAluno);
	public boolean validaNomeRG(int identificacaoConvertida, String rg, String nome);
	public boolean validaCEP(int identificacaoConvertida, String rg);  //método retirado do managed bean; porém pode retornar
	public List<Professor> listaProfessorTurma (int IDTurma);
	public List<Professor> listaProfessorTurmaDisciplina (int IDTurma, int IDDisciplina);
	public String localizaEmailProfessor (int IDProfessor);
	public JasperPrint gerarPDF() throws JRException;
}

