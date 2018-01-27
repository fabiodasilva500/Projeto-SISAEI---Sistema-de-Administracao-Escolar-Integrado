package Pojo;

import java.io.Serializable;

public class Dependencia implements Serializable {
private String identificacaoAluno;
private int IDDaDisciplina;
private int modulo;
private String atividade;
private String mencao;


public String getIdentificacaoAluno() {
	return identificacaoAluno;
}
public void setIdentificacaoAluno(String identificacaoAluno) {
	this.identificacaoAluno = identificacaoAluno;
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
public String getAtividade() {
	return atividade;
}
public void setAtividade(String atividade) {
	this.atividade = atividade;
}
public String getMencao() {
	return mencao;
}
public void setMencao(String mencao) {
	this.mencao = mencao;
}
}
