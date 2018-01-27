package Pojo;

import java.io.Serializable;
import java.util.Date;

 
public class TurmaCurso  implements Serializable {
	private int IDTurma;
	private String nomeTurma;
	private int ano;
	private String periodo;
	private String sala;
	private Date data_conselho;
	private String nomeCurso;



	public int getIDTurma() {
		return IDTurma;
	}
	public void setIDTurma(int iDTurma) {
		IDTurma = iDTurma;
	}
	public String getNomeTurma() {
		return nomeTurma;
	}
	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
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
	public String getSala() {
		return sala;
	}
	public void setSala(String sala) {
		this.sala = sala;
	}
	public Date getData_conselho() {
		return data_conselho;
	}
	public void setData_conselho(Date data_conselho) {
		this.data_conselho = data_conselho;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}




}
