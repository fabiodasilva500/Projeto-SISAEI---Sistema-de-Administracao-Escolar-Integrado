package Pojo;

import java.io.Serializable;

public class Disciplina implements Serializable {
	private int IDDisciplina;
	private String nome;
	private String sigla;
	private String descricao;
	private String ata;
	private int curso;
	private int modulo;
	private double cargaHoraria;
	private int IDAreaConcentracao;
	
	
	private int preparaIDDisciplina;


	public int getIDDisciplina() {
		return IDDisciplina;
	}
	public void setIDDisciplina(int iDDisciplina) {
		IDDisciplina = iDDisciplina;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAta() {
		return ata;
	}
	public void setAta(String ata) {
		this.ata = ata;
	}
	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}


	public int getPreparaIDDisciplina() {
		return preparaIDDisciplina;
	}
	public void setPreparaIDDisciplina(int preparaIDDisciplina) {
		this.preparaIDDisciplina = preparaIDDisciplina;
	}
	public int getModulo() {
		return modulo;
	}
	public void setModulo(int modulo) {
		this.modulo = modulo;
	}
	public double getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(double cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	public int getIDAreaConcentracao() {
		return IDAreaConcentracao;
	}
	public void setIDAreaConcentracao(int iDAreaConcentracao) {
		IDAreaConcentracao = iDAreaConcentracao;
	}


}
