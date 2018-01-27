package ManagedBeans;
 
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
import javax.mail.util.ByteArrayDataSource;
 
import org.primefaces.event.FileUploadEvent;

import Pojo.Acesso;
import Pojo.Aluno;
  
@ManagedBean(name="fileUploadController")
public class FileUploadController {
   private String destination="D:\\tmp\\";
 
    public void upload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            enviarAlunoUnicoComAnexo(event, event.getFile().getInputstream());
           
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }  
 
    public void copyFile(String fileName, InputStream in) {
           try {
              
              
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
              
                int read = 0;
                byte[] bytes = new byte[1024];
              
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
              
                in.close();
                out.flush();
                out.close();
              
                System.out.println("New file created!");
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
    
    
    
    public void enviarAlunoUnicoComAnexo(FileUploadEvent event, InputStream in) throws AddressException, MessagingException, IOException{
    	FacesContext context = FacesContext.getCurrentInstance();  
		
    	String host = null;
		String port = null;
		boolean enviado=false;
	

		final String email = "eteczl2000@gmail.com";
		final String senha = "eteczonaleste2000";
		
		String destinatario = "fabiodasilva500@gmail.com";

		String[] attachFiles = new String[1];
		attachFiles[0] = event.getFile().getFileName();
		System.out.println("Arquivo:"+event.getFile().getFileName());

		Properties dados = new Properties();

			host = "smtp.gmail.com";
			port = "587";
			dados.put("mail.smtp.host", host);
			dados.put("mail.smtp.port", port);
			dados.put("mail.smtp.auth", "true");
			dados.put("mail.smtp.starttls.enable", "true");
			dados.put("mail.user", email);
			dados.put("mail.password", senha);
		

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, senha);
			}
		};
		Session session = Session.getInstance(dados, auth);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(email));
		InternetAddress[] toAddresses = { new InternetAddress(destinatario) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject("a");
		msg.setSentDate(new Date());
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent("b", "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
	
		MimeMessage message = new MimeMessage(session);
		message.setSubject("b");
		message.setFrom(new InternetAddress(email));
		message.addRecipients(Message.RecipientType.TO, destinatario);
		

		  if (in != null) {
		        // create the second message part with the attachment from a OutputStrean
		        MimeBodyPart attachment= new MimeBodyPart();
		        ByteArrayDataSource ds = new ByteArrayDataSource(in, ""); 
		        attachment.setDataHandler(new DataHandler(ds));
		        attachment.setFileName(event.getFile().getFileName());
		        multipart.addBodyPart(attachment);
		    	msg.setContent(multipart);
				Transport.send(msg);
		    }
		
		if(enviado){
	    context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Email enviado com sucesso.",""+""));
		}

		else{
		context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
		}
		
		}
		

 
    
    
    
    
    
    


  
    
}