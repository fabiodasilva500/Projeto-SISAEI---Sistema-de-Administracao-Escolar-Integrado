package ManagedBeans;



import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
* Classe para enviar email em anexo usando o Gmail.
*
* @author jabes.cunha
*
*/
public class EnviarEmail {
	
	public static void main(String[] args) {
		new EnviarEmail().enviar();
	}

	public void enviar() {
		try {

			//usuario e senha do seu gmail
			final String usuario = "fabiodasilva500@gmail.com";
			final String senha = "filomena20082010";

			//config. do gmail
			Properties mailProps = new Properties();
			mailProps.put("mail.transport.protocol", "smtp");
			mailProps.put("mail.smtp.starttls.enable","true");
			mailProps.put("mail.smtp.host", "smtp.gmail.com");
			mailProps.put("mail.smtp.auth", "true");
			mailProps.put("mail.smtp.user", usuario);
		  	mailProps.put("mail.debug", "true");
			mailProps.put("mail.smtp.port", "465");
			mailProps.put("mail.smtp.socketFactory.port", "465");
			mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			mailProps.put("mail.smtp.socketFactory.fallback", "false");

			//eh necessario autenticar
			Session mailSession = Session.getInstance(mailProps, new Authenticator() {					
				public PasswordAuthentication getPasswordAuthentication(){				
					return new PasswordAuthentication(usuario, senha);
				}
			});
			mailSession.setDebug(false);

			//config. da mensagem
			Message mailMessage = new MimeMessage(mailSession);

			//remetente
			mailMessage.setFrom(new InternetAddress(usuario));

			//destinatario
			mailMessage.setRecipients(Message.RecipientType.TO, 			InternetAddress.parse(usuario));		

			//mensagem que vai no corpo do email
			MimeBodyPart mbpMensagem = new MimeBodyPart();
			mbpMensagem.setText("Segue em anexo, imagem do novo focus hath.");

			//partes do email
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbpMensagem);
			
			//imagem que sera enviada em anexo, ta no mesmo diretorio da classe.
			String imagem = "focusHath2011.jpg";
			InputStream is = getClass().getResourceAsStream(imagem);

			//setando o anexo
			MimeBodyPart mbpAnexo = new MimeBodyPart();
			mbpAnexo.setDataHandler(new DataHandler(new ByteArrayDataSource(is, "application/image")));		
			mbpAnexo.setFileName(imagem);
			mp.addBodyPart(mbpAnexo);

			//assunto do email
			mailMessage.setSubject("Novo Focus Hath 2011");
			
			//seleciona o conteudo
			mailMessage.setContent(mp);

			//envia o email
			Transport.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}