package Pojo;

import java.io.Serializable;
import java.util.Date;

public class Coordenador  implements Serializable {
private String rg;
private String nome;
private String cpf;
private String cep;
private String endereco;
private String bairro;
private int numero;
private String cidade;
private String estado;
private String telefone;
private String celular;
private String nacionalidade;
private Date dataNascimento;
private String naturalidade;
private String email;
private int curso;



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
public String getCpf() {
	return cpf;
}
public void setCpf(String cpf) {
	this.cpf = cpf;
}


public String getCep() {
	return cep;
}
public void setCep(String cep) {
	this.cep = cep;
}
public String getEndereco() {
	return endereco;
}
public void setEndereco(String endereco) {
	this.endereco = endereco;
}
public String getBairro() {
	return bairro;
}
public void setBairro(String bairro) {
	this.bairro = bairro;
}
public int getNumero() {
	return numero;
}
public void setNumero(int numero) {
	this.numero = numero;
}
public String getCidade() {
	return cidade;
}
public void setCidade(String cidade) {
	this.cidade = cidade;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
public String getTelefone() {
	return telefone;
}
public void setTelefone(String telefone) {
	this.telefone = telefone;
}
public String getCelular() {
	return celular;
}
public void setCelular(String celular) {
	this.celular = celular;
}
public String getNacionalidade() {
	return nacionalidade;
}
public void setNacionalidade(String nacionalidade) {
	this.nacionalidade = nacionalidade;
}
public Date getDataNascimento() {
	return dataNascimento;
}
public void setDataNascimento(Date dataNascimento) {
	this.dataNascimento = dataNascimento;
}
public String getNaturalidade() {
	return naturalidade;
}
public void setNaturalidade(String naturalidade) {
	this.naturalidade = naturalidade;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getCurso() {
	return curso;
}
public void setCurso(int curso) {
	this.curso = curso;
}

}
