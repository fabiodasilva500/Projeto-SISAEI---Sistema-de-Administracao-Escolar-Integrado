package Pojo;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.DefaultStreamedContent;

public class Aluno implements Serializable {
	private String identificacao;
	private String nome;
	private String cep;
	private String endereco;
	private int numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String telefone;
	private String celular;
	private String rg;
	private String orgao_expeditor;
	private Date dataNascimento;
	private String cidadeNascimento;
	private String nacionalidade;
	private String naturalidade;
	private String sexo;
	private int idade;
	private String email;
	private int curso;
	private String preparaData;
	private boolean presencaAtual;

	
	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getAux() {
		return aux;
	}
	public void setAux(String aux) {
		this.aux = aux;
	}
	private String aux;

	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
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

	
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getOrgao_expeditor() {
		return orgao_expeditor;
	}
	public void setOrgao_expeditor(String orgao_expeditor) {
		this.orgao_expeditor = orgao_expeditor;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCidadeNascimento() {
		return cidadeNascimento;
	}
	public void setCidadeNascimento(String cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}



	public String getPreparaData() {
		return preparaData;
	}
	public void setPreparaData(String preparaData) {
		this.preparaData = preparaData;
	}
	public boolean isPresencaAtual() {
		return presencaAtual;
	}
	public void setPresencaAtual(boolean presencaAtual) {
		this.presencaAtual = presencaAtual;
	}

	

}
