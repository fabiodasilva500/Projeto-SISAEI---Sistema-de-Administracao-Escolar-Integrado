package Pojo;

import java.io.Serializable;
import java.util.Date;

public class Diario implements Serializable {
private int idDiario;
private int idDisciplina;
private int idProfessor;
private int idTurma;
private int idCurso;
private String tipoChamada;
private Date dataAula;
private String turno;
private double quantidadeAusencia;




public int getIdCurso() {
	return idCurso;
}
public void setIdCurso(int idCurso) {
	this.idCurso = idCurso;
}


public int getIdDisciplina() {
	return idDisciplina;
}
public void setIdDisciplina(int idDisciplina) {
	this.idDisciplina = idDisciplina;
}
public int getIdProfessor() {
	return idProfessor;
}
public void setIdProfessor(int idProfessor) {
	this.idProfessor = idProfessor;
}
public int getIdTurma() {
	return idTurma;
}
public void setIdTurma(int idTurma) {
	this.idTurma = idTurma;
}
public Date getDataAula() {
	return dataAula;
}
public void setDataAula(Date dataAula) {
	this.dataAula = dataAula;
}
public int getIdDiario() {
	return idDiario;
}
public void setIdDiario(int idDiario) {
	this.idDiario = idDiario;
}



public String getTurno() {
	return turno;
}
public void setTurno(String turno) {
	this.turno = turno;
}
public String getTipoChamada() {
	return tipoChamada;
}
public void setTipoChamada(String tipoChamada) {
	this.tipoChamada = tipoChamada;
}
public double getQuantidadeAusencia() {
	return quantidadeAusencia;
}
public void setQuantidadeAusencia(double quantidadeAusencia) {
	this.quantidadeAusencia = quantidadeAusencia;
}


}
