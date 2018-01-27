package CorreioEletronico;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;



import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import com.sun.net.httpserver.HttpExchange;


import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  



public class EnvioDeEmail {



	public static void main(String[]args) throws AddressException, MessagingException{
		new EnvioDeEmail();
	}



	public EnvioDeEmail() throws AddressException, MessagingException{
		//EnviarArquivo("d:\\relatorioTurma.jasper", "gmail", "fabiodasilva500@gmail.com", "eusoucruzeiro", "fabioportuguesa@gmail.com", "teste", "pdf");
	}

	public  boolean EnviarArquivo(String caminhoArquivo, String provedor, String emailDigitado, String senhaDigitada,
			String destinatario, String assunto, String conteudo)
					throws AddressException, MessagingException {

		boolean enviado=false;

		String host = null;
		String port = null;

		final String email = emailDigitado;
		final String senha = senhaDigitada;


		String[] attachFiles = new String[1];
		attachFiles[0] = caminhoArquivo;

		Properties dados = new Properties();


		if(provedor.equalsIgnoreCase("gmail")){



			host = "smtp.gmail.com";
			port = "587";
			dados.put("mail.smtp.host", host);
			dados.put("mail.smtp.port", port);
			dados.put("mail.smtp.auth", "true");
			dados.put("mail.smtp.starttls.enable", "true");
			dados.put("mail.user", email);
			dados.put("mail.password", senha);
		}
		else
			if(provedor.equalsIgnoreCase("hotmail")){
				host = "smtp.live.com";
				port = "587";

				dados.put("mail.transport.protocol", "smtp");
				dados.put("mail.smtp.host", host);
				dados.put("mail.smtp.socketFactory.port", "587");
				dados.put("mail.smtp.socketFactory.fallback", "false");
				dados.put("mail.smtp.starttls.enable", "true");
				dados.put("mail.smtp.auth", "true");
				dados.put("mail.smtp.port", port);
			}

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
		msg.setSubject(assunto);
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(conteudo, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
					enviado=true;

				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}



			msg.setContent(multipart);

			Transport.send(msg);

		}
		return enviado;
	}
}
