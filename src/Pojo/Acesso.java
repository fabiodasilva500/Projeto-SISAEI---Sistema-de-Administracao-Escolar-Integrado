package Pojo;

import java.util.Date;

public class Acesso {
private String identificacao;
private String senha;
private String nome;
private String rg;
private String tipoAcesso;
private String email;
private int IDProfessor;
private int senhaProfessor;
private String cep;
private String senhaAnterior;
private String novoEmail;
private String novaSenha;


public String getCep() {
	return cep;
}
public void setCep(String cep) {
	this.cep = cep;
}
public String getSenha() {
	return senha;
}
public void setSenha(String senha) {
	this.senha = senha;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getRg() {
	return rg;
}
public void setRg(String rg) {
	this.rg = rg;
}
public String getTipoAcesso() {
	return tipoAcesso;
}
public void setTipoAcesso(String tipoAcesso) {
	this.tipoAcesso = tipoAcesso;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getIdentificacao() {
	return identificacao;
}
public void setIdentificacao(String identificacao) {
	this.identificacao = identificacao;
}
public int getIDProfessor() {
	return IDProfessor;
}
public void setIDProfessor(int iDProfessor) {
	IDProfessor = iDProfessor;
}
public int getSenhaProfessor() {
	return senhaProfessor;
}
public void setSenhaProfessor(int senhaProfessor) {
	this.senhaProfessor = senhaProfessor;
}
public String getSenhaAnterior() {
	return senhaAnterior;
}
public void setSenhaAnterior(String senhaAnterior) {
	this.senhaAnterior = senhaAnterior;
}
public String getNovaSenha() {
	return novaSenha;
}
public void setNovaSenha(String novaSenha) {
	this.novaSenha = novaSenha;
}
public String getNovoEmail() {
	return novoEmail;
}
public void setNovoEmail(String novoEmail) {
	this.novoEmail = novoEmail;
}


}
