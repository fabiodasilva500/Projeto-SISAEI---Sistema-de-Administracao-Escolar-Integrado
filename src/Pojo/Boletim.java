package Pojo;

import java.io.Serializable;

public class Boletim implements Serializable{
	private int numeroDoConceito;
	private String identificacaoAluno;
	private int IDDaTurma;
	private int IDDaDisciplina;
	private int modulo;
	private double aulasDadasParciais;
	private double aulasDadasFinais;
	
	private String conceitoParcial;
	private String conceitoFinal;
	private double faltasParciais;
	private double faltasFinais;
    private double frequenciaParcial;
    private double frequenciaFinal;
	private String status;
	private int curso;
	private String nomeCurso;
	private String nomeTurma;
	private String nomeDisciplina;

	public int getNumeroDoConceito() {
		return numeroDoConceito;
	}
	public void setNumeroDoConceito(int numeroDoConceito) {
		this.numeroDoConceito = numeroDoConceito;
	}
	public String getIdentificacaoAluno() {
		return identificacaoAluno;
	}
	public void setIdentificacaoAluno(String identificacaoAluno) {
		this.identificacaoAluno = identificacaoAluno;
	}
	public int getIDDaTurma() {
		return IDDaTurma;
	}
	public void setIDDaTurma(int iDDaTurma) {
		IDDaTurma = iDDaTurma;
	}
	public int getIDDaDisciplina() {
		return IDDaDisciplina;
	}
	public void setIDDaDisciplina(int iDDaDisciplina) {
		IDDaDisciplina = iDDaDisciplina;
	}
	public int getModulo() {
		return modulo;
	}
	public void setModulo(int modulo) {
		this.modulo = modulo;
	}
	public String getConceitoParcial() {
		return conceitoParcial;
	}
	public void setConceitoParcial(String conceitoParcial) {
		this.conceitoParcial = conceitoParcial;
	}
	public String getConceitoFinal() {
		return conceitoFinal;
	}
	public void setConceitoFinal(String conceitoFinal) {
		this.conceitoFinal = conceitoFinal;
	}
	
	

	public void setFaltasParciais(double faltasParciais) {
		this.faltasParciais = faltasParciais;
	}
	
	public double getFaltasParciais() {
		return faltasParciais;
	}
	

	
	public double getFaltasFinais() {
		return faltasFinais;
	}
	public void setFaltasFinais(double faltasFinais) {
		this.faltasFinais = faltasFinais;
	}
	public double getFrequenciaParcial() {
		return frequenciaParcial;
	}
	public void setFrequenciaParcial(double frequenciaParcial) {
		this.frequenciaParcial = frequenciaParcial;
	}
	public double getFrequenciaFinal() {
		return frequenciaFinal;
	}
	public void setFrequenciaFinal(double frequenciaFinal) {
		this.frequenciaFinal = frequenciaFinal;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public String getNomeTurma() {
		return nomeTurma;
	}
	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}
	public String getNomeDisciplina() {
		return nomeDisciplina;
	}
	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}
	public double getAulasDadasParciais() {
		return aulasDadasParciais;
	}
	public void setAulasDadasParciais(double aulasDadasParciais) {
		this.aulasDadasParciais = aulasDadasParciais;
	}
	public double getAulasDadasFinais() {
		return aulasDadasFinais;
	}
	public void setAulasDadasFinais(double aulasDadasFinais) {
		this.aulasDadasFinais = aulasDadasFinais;
	}

}
