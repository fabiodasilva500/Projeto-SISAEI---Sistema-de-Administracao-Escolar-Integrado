package Pojo;

import java.io.Serializable;

public class Curso implements Serializable{

	private int id;
	private String nome;
	private String sigla;
	private String descricao;
	private String tipo;
	private String eixoTecnologico;
	private String corArea;
	private String corCurso;
	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEixoTecnologico() {
		return eixoTecnologico;
	}
	public void setEixoTecnologico(String eixoTecnologico) {
		this.eixoTecnologico = eixoTecnologico;
	}
	public String getCorArea() {
		return corArea;
	}
	public void setCorArea(String corArea) {
		this.corArea = corArea;
	}
	public String getCorCurso() {
		return corCurso;
	}
	public void setCorCurso(String corCurso) {
		this.corCurso = corCurso;
	}
}
