package Pojo;

import java.io.Serializable;

public class ProfessorDisciplinaTurma implements Serializable {
private int IDDoProfessor;
private int IDDaTurma;
private int IDDaDisciplina;
private String nomeProfessor;
private String nomeTurma;

public int getIDDoProfessor() {
	return IDDoProfessor;
}
public void setIDDoProfessor(int iDDoProfessor) {
	IDDoProfessor = iDDoProfessor;
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
public String getNomeProfessor() {
	return nomeProfessor;
}
public void setNomeProfessor(String nomeProfessor) {
	this.nomeProfessor = nomeProfessor;
}
public String getNomeTurma() {
	return nomeTurma;
}
public void setNomeTurma(String nomeTurma) {
	this.nomeTurma = nomeTurma;
}

}
