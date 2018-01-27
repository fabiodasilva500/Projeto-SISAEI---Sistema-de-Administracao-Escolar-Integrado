package Pojo;

import java.io.Serializable;

public class Matricula  implements Serializable{
private String identificacao;
private int IDCurso;
private int IDTurma;
private int dependencias;
private int totalDependencias;
private String instituicao;
private String razao;


public String getIdentificacao() {
	return identificacao;
}
public void setIdentificacao(String identificacao) {
	this.identificacao = identificacao;
}
public int getIDTurma() {
	return IDTurma;
}
public void setIDTurma(int iDTurma) {
	IDTurma = iDTurma;
}

public int getDependencias() {
	return dependencias;
}
public void setDependencias(int dependencias) {
	this.dependencias = dependencias;
}
public int getTotalDependencias() {
	return totalDependencias;
}
public void setTotalDependencias(int totalDependencias) {
	this.totalDependencias = totalDependencias;
}
public String getInstituicao() {
	return instituicao;
}
public void setInstituicao(String instituicao) {
	this.instituicao = instituicao;
}
public int getIDCurso() {
	return IDCurso;
}
public void setIDCurso(int iDCurso) {
	IDCurso = iDCurso;
}
public String getRazao() {
	return razao;
}
public void setRazao(String razao) {
	this.razao = razao;
}



}
