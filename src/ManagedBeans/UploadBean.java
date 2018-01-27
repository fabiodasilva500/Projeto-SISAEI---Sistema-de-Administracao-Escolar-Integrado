package ManagedBeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

  
@ManagedBean  
@SessionScoped
public class UploadBean implements java.io.Serializable {  
  
    private UploadedFile arquivo;  
    private String caminho;
  
    /* Construtor */  
    public UploadBean() {  
    }  
  
    /* Get e Set */  
    public UploadedFile getArquivo() {  
        return arquivo;  
    }  
  
    public void setArquivo(UploadedFile arquivo) {  
        this.arquivo = arquivo;  
    }  
  
    /* Fim Get e Set */  
    /** 
     * Trata o evento de upload e guarda uma referencia para o 
     * arquivo no bean 
     * @param evento  
     * @throws IOException 
     */  
    public void uploadListener(FileUploadEvent evento) throws IOException {  
        this.arquivo = evento.getFile();  
  
        FacesContext ctx = FacesContext.getCurrentInstance();  
        FacesMessage msg = new FacesMessage();  
  
        msg.setSummary("Arquivo anexado com sucesso.");  
        msg.setSeverity(FacesMessage.SEVERITY_INFO);  
  
        ctx.addMessage("mensagens", msg);  
          
        String nomeArquivo = arquivo.getFileName(); //Nome do arquivo enviado  
        byte[] conteudo = evento.getFile().getContents(); //Conteudo a ser gravado no arquivo  
        
        System.out.println(conteudo);

        FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
         caminho = scontext.getRealPath("/WEB-INF/LIB/Envio/" + nomeArquivo); 
  
        System.out.println(caminho);
        
                
        FileOutputStream fos = new FileOutputStream(caminho);  
        fos.write(conteudo);  
        fos.close();  
       }  
  
    /** 
     * Salvar o arquivo 
     * @return  
     * @throws IOException 
     */  
    public String salvar() throws IOException {  
    	try {
			EnviarArquivo(caminho); 
	        FacesContext facesContext = FacesContext.getCurrentInstance();  
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
		    caminho = scontext.getRealPath("/WEB-INF/LIB/Envio/"); 
			File folder = new File(caminho);  
			if (folder.isDirectory()) {  
			    File[] sun = folder.listFiles();  
			    for (File excluiItens : sun) {  
			        excluiItens.delete();  
			    }
			}
				
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    }
    
  
    
    public  void EnviarArquivo(String caminhoArquivo)
			throws AddressException, MessagingException {
    	
		String host = null;
		String port = null;


		String[] attachFiles = new String[1];
		attachFiles[0] = caminhoArquivo;

		Properties dados = new Properties();

			host = "smtp.gmail.com";
			port = "587";
			dados.put("mail.smtp.host", host);
			dados.put("mail.smtp.port", port);
			dados.put("mail.smtp.auth", "true");
			dados.put("mail.smtp.starttls.enable", "true");
			dados.put("mail.user", "fabiodasilva500@gmail.com");
			dados.put("mail.password", "silva20112014");
		

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("fabiodasilva500@gmail.com", "silva20112014");
			}
		};
		Session session = Session.getInstance(dados, auth);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress("fabiodasilva500@gmail.com"));
		InternetAddress[] toAddresses = { new InternetAddress("fabiodasilva500@gmail.com") };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject("email");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent("arquivo", "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
					


				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}



			msg.setContent(multipart);

			Transport.send(msg);

		}
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
    
    

}