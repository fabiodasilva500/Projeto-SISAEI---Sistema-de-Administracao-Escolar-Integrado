package Pojo;

import java.io.Serializable;
import java.util.Date;

public class Turma  implements Serializable {
	private int IDTurma;
	private String nome;
	private int ano;
	private int semestre;
	private String periodo;
	private double aulas_totais;
	private int curso;
	private String sala;
	private String descricao;
	private Date data_conselho;
	private String perfil;
	private int preparaIDTurma;
	private String preparaData;
	private int modulo;
	
	
	public int getIDTurma() {
		return IDTurma;
	}
	public void setIDTurma(int iDTurma) {
		IDTurma = iDTurma;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
	public int getModulo() {
		return modulo;
	}
	public void setModulo(int modulo) {
		this.modulo = modulo;
	}

	public double getAulas_totais() {
		return aulas_totais;
	}
	
	
	
	public void setAulas_totais(double aulas_totais) {
		this.aulas_totais = aulas_totais;
	}
	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}
	public String getSala() {
		return sala;
	}
	public void setSala(String sala) {
		this.sala = sala;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getData_conselho() {
		return data_conselho;
	}
	public void setData_conselho(Date data_conselho) {
		this.data_conselho = data_conselho;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	

	public int getPreparaIDTurma() {
		return preparaIDTurma;
	}
	public void setPreparaIDTurma(int preparaIDTurma) {
		this.preparaIDTurma = preparaIDTurma;
	}
	public String getPreparaData() {
		return preparaData;
	}
	public void setPreparaData(String preparaData) {
		this.preparaData = preparaData;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

}
