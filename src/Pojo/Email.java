package Pojo;

import java.io.Serializable;

public class Email implements Serializable {
	private String provedor; 
	private String emailDigitado;
	private String senhaDigitada;
	private String destinatario;
	private String assunto;
	private String conteudo;
	private String caminhoArquivo;
	private String diretorio;




	public String getProvedor() {
		return provedor;
	}
	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}
	public String getEmailDigitado() {
		return emailDigitado;
	}
	public void setEmailDigitado(String emailDigitado) {
		this.emailDigitado = emailDigitado;
	}
	public String getSenhaDigitada() {
		return senhaDigitada;
	}
	public void setSenhaDigitada(String senhaDigitada) {
		this.senhaDigitada = senhaDigitada;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}


	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}


	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	public String getDiretorio() {
		return diretorio;
	}
	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

}


