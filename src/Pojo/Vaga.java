package Pojo;

import java.util.Date;


public class Vaga {
private int IDOportunidade;
private int curso;
private String nomeEmpresa;
private String cargo;
private String descricao;
private String requisitos;
private String diferenciais;
private String email;
private Date dataInicial;
private Date dataFinal;
private String preparaDataInicial;
private String preparaDataFinal;




public int getIDOportunidade() {
	return IDOportunidade;
}
public void setIDOportunidade(int iDOportunidade) {
	IDOportunidade = iDOportunidade;
}
public int getCurso() {
	return curso;
}
public void setCurso(int curso) {
	this.curso = curso;
}
public String getNomeEmpresa() {
	return nomeEmpresa;
}
public void setNomeEmpresa(String nomeEmpresa) {
	this.nomeEmpresa = nomeEmpresa;
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}
public String getRequisitos() {
	return requisitos;
}
public void setRequisitos(String requisitos) {
	this.requisitos = requisitos;
}
public String getDiferenciais() {
	return diferenciais;
}
public void setDiferenciais(String diferenciais) {
	this.diferenciais = diferenciais;
}
public Date getDataInicial() {
	return dataInicial;
}
public void setDataInicial(Date dataInicial) {
	this.dataInicial = dataInicial;
}
public Date getDataFinal() {
	return dataFinal;
}
public void setDataFinal(Date dataFinal) {
	this.dataFinal = dataFinal;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPreparaDataInicial() {
	return preparaDataInicial;
}
public void setPreparaDataInicial(String preparaDataInicial) {
	this.preparaDataInicial = preparaDataInicial;
}
public String getPreparaDataFinal() {
	return preparaDataFinal;
}
public void setPreparaDataFinal(String preparaDataFinal) {
	this.preparaDataFinal = preparaDataFinal;
}
public String getCargo() {
	return cargo;
}
public void setCargo(String cargo) {
	this.cargo = cargo;
}



}
