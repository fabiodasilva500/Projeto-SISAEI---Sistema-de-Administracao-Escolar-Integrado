package ManagedBeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;

import org.apache.catalina.Session;




import CorreioEletronico.EnvioDeEmail;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.EmailDaoImplementation;
import Pojo.Disciplina;
import Pojo.Email;

import java.io.Serializable;  
import java.util.ArrayList;  
import java.util.List;  
import org.primefaces.event.FileUploadEvent;  
import org.primefaces.model.UploadedFile;  

@ManagedBean(name="emailMB")

@RequestScoped

public class EmailMB implements Serializable{
	/**
	 * 
	 */

	private Email emailAtual;
	private EnvioDeEmail enviaEmail;
	private String provedor;
	private String diretorioSelecionado;

	private EmailDaoImplementation eDAO;


	public EmailMB() throws AddressException, MessagingException{
		setEmailAtual(new Email());
		enviaEmail=new EnvioDeEmail();
		eDAO=new EmailDaoImplementation(); 
	}



	public void handleFileUpload(FileUploadEvent event) {  
		FacesMessage msg = new FacesMessage("O arquivo", event.getFile().getFileName() + " foi carregado com sucesso.");  
		FacesContext.getCurrentInstance().addMessage(null, msg); 
		emailAtual.setCaminhoArquivo(event.getFile().getFileName());
		eDAO.Inserir(emailAtual.getCaminhoArquivo());

	}  



	public String enviar() throws AddressException, MessagingException{
		FacesContext context = FacesContext.getCurrentInstance();



		if ("Gmail".equals(provedor)) { 
			emailAtual.setProvedor("Gmail");
		}
		else
			if("Hotmail".equals(provedor)){
				emailAtual.setProvedor("Hotmail");
			}

   
		emailAtual.setCaminhoArquivo(emailAtual.getDiretorio()+""+eDAO.pesquisar());
		
		

		boolean enviado = enviaEmail.EnviarArquivo(emailAtual.getCaminhoArquivo(), emailAtual.getProvedor(), emailAtual.getEmailDigitado(), emailAtual.getSenhaDigitada(), emailAtual.getDestinatario(), emailAtual.getAssunto(), emailAtual.getConteudo());
		if(enviado){
			context.addMessage("formEmail:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso", ""+""));
			limpaCampo();
		}
		else{
			context.addMessage("formEmail:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do disciplina", ""+""));
		}

		return "";
	}


	public Email getEmailAtual() {
		return emailAtual;
	}

	public void setEmailAtual(Email emailAtual) {
		this.emailAtual = emailAtual;
	}

	public EnvioDeEmail getEnviaEmail() {
		return enviaEmail;
	}

	public void setEnviaEmail(EnvioDeEmail enviaEmail) {
		this.enviaEmail = enviaEmail;
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}



	public String getDiretorioSelecionado() {
		return diretorioSelecionado;
	}



	public void setDiretorioSelecionado(String diretorioSelecionado) {
		this.diretorioSelecionado = diretorioSelecionado;
	}


	public void limpaCampo(){
		emailAtual.setProvedor("");
		emailAtual.setEmailDigitado("");
		emailAtual.setSenhaDigitada("");
		emailAtual.setDestinatario("");
		emailAtual.setAssunto("");
		emailAtual.setConteudo("");
		emailAtual.setCaminhoArquivo("");
		emailAtual.setDiretorio("");
	}




}
