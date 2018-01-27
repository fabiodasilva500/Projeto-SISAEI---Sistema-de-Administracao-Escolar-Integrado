package ManagedBeans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.ProfessorDao;
import Dao.ProfessorDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.Professor;
import Pojo.Turma;

@ManagedBean (name="duvidaMB")
@ViewScoped

public class DuvidaMB implements Serializable {
private int selecaoCurso;
private int selecaoModulo;
private int selecaoTurma;
private int selecaoDisciplina;
private int selecaoProfessor;
private int selecaoAno;
private int selecaoSemestre;

private String assunto;
private String conteudo;
private List<Professor> listaProfessores;
private List<Curso> listaCursos;
private List<Disciplina> listaDisciplinas;
private List<Turma> listaTurmas;
private List<Integer> anos;
private Integer ano;


private boolean desabilitaModulo;
private boolean desabilitaCurso;
private boolean desabilitaTurma;
private boolean desabilitaDisciplina;
private boolean desabilitaAluno;
private boolean desabilitaData;
private boolean desabilitaTurno;
private boolean desabilitaAssunto;
private boolean desabilitaConteudo;


private CursoDao cursoDao;
private DisciplinaDao disciplinaDao;
private ProfessorDao professorDao;
private TurmaDao turmaDao;
private UploadedFile arquivo;  
private boolean habilitaGridArquivo;
private String caminho;



public DuvidaMB(){
listaProfessores=new ArrayList<Professor>();	
listaCursos = new ArrayList<Curso>();
listaTurmas = new ArrayList<Turma>();
anos=new ArrayList<Integer>();
}


public void enviarEmail(String emailAluno) throws AddressException, MessagingException {

if(habilitaGridArquivo){
enviarComAnexo(emailAluno);
System.out.println("Com anexo");
}
else{
enviarSemAnexo(emailAluno);
System.out.println("Com anexo");
}
}


public void enviarSemAnexo(String emailAluno) throws AddressException, MessagingException{
String destinatario = localizaEmailProfessor() + ","+emailAluno;

FacesContext context = FacesContext.getCurrentInstance();

	
if(selecaoCurso>0 && selecaoModulo>0 && selecaoProfessor>0 && 
!assunto.equalsIgnoreCase("") && !conteudo.equalsIgnoreCase("")){

String host = null;
String port = null;
final String email = "eteczl2000@gmail.com";
final String senha = "eteczonaleste2000";


String[] attachFiles = new String[1];

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


MimeMessage message = new MimeMessage(session);
message.setSubject(assunto);
message.setContent(conteudo, "text/plain");
message.setFrom(new InternetAddress(email));
message.addRecipients(Message.RecipientType.TO, destinatario);
Transport.send(message);

context.addMessage("formDuvidaAluno:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Email enviado com sucesso.",""+""));
limpaCampo();
}

else{
context.addMessage("formDuvidaAluno:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
}
}


public void enviarComAnexo(String emailAluno) throws AddressException, MessagingException{
	String destinatario = localizaEmailProfessor();
	FacesContext context = FacesContext.getCurrentInstance();  
	
	
	System.out.println("Enviando com anexo");
	
	String host = null;
	String port = null;
	boolean enviado=false;


	final String email = "eteczl2000@gmail.com";
	final String senha = "eteczonaleste2000";
	
	String[] attachFiles = new String[1];
	attachFiles[0] = caminho;

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
	msg.setSubject(assunto);
	msg.setSentDate(new Date());
	
	MimeBodyPart messageBodyPart = new MimeBodyPart();
	messageBodyPart.setContent(conteudo, "text/html");

	Multipart multipart = new MimeMultipart();
	multipart.addBodyPart(messageBodyPart);

	MimeMessage message = new MimeMessage(session);
	message.setSubject(assunto);
	message.setFrom(new InternetAddress(email));
	message.addRecipients(Message.RecipientType.TO, destinatario);


	if (attachFiles != null && attachFiles.length > 0) {
		for (String filePath : attachFiles) {
			MimeBodyPart attachPart = new MimeBodyPart();

			try {
				attachPart.attachFile(filePath);
				enviado=true;

	
			} catch (IOException ex) {
				enviado=false;
				ex.printStackTrace();
			}

			multipart.addBodyPart(attachPart);
		}


		msg.setContent(multipart);
		Transport.send(msg);
	}
	
	if(enviado){
    enviarCopiaAluno(emailAluno);
    limpaCampo();
	}

	else{
	context.addMessage("formDuvidaAluno:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
	}
	}
	
	

public void enviarCopiaAluno(String emailAluno) throws AddressException, MessagingException{
	String destinatario = emailAluno;
	FacesContext context = FacesContext.getCurrentInstance();  
	
	
	System.out.println("Enviando com anexo");
	
	String host = null;
	String port = null;
	boolean enviado=false;


	final String email = "eteczl2000@gmail.com";
	final String senha = "eteczonaleste2000";
	
	String[] attachFiles = new String[1];
	attachFiles[0] = caminho;

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
	msg.setSubject(assunto);
	msg.setSentDate(new Date());
	
	MimeBodyPart messageBodyPart = new MimeBodyPart();
	messageBodyPart.setContent(conteudo, "text/html");

	Multipart multipart = new MimeMultipart();
	multipart.addBodyPart(messageBodyPart);

	MimeMessage message = new MimeMessage(session);
	message.setSubject(assunto);
	message.setContent(conteudo, "text/plain");
	message.setFrom(new InternetAddress(email));
	message.addRecipients(Message.RecipientType.TO, destinatario);


	if (attachFiles != null && attachFiles.length > 0) {
		for (String filePath : attachFiles) {
			MimeBodyPart attachPart = new MimeBodyPart();

			try {
				attachPart.attachFile(filePath);
				enviado=true;

	
			} catch (IOException ex) {
				enviado=false;
				ex.printStackTrace();
			}

			multipart.addBodyPart(attachPart);
		}


		msg.setContent(multipart);
		Transport.send(msg);
	}
	
	if(enviado){
    context.addMessage("formDuvidaAluno:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Email enviado com sucesso.",""+""));
    limpaCampo();
	}

	else{
	context.addMessage("formDuvidaAluno:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
	}
	}
	



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
    caminho = scontext.getRealPath("/WEB-INF/LIB/EnviaAnexo/" + nomeArquivo); 
    System.out.println("Caminho do arquivo:"+caminho);
         
    FileOutputStream fos = new FileOutputStream(caminho);  
    fos.write(conteudo);  
    fos.close();  
   }



public void limpaCampo(){
selecaoSemestre=1;
selecaoCurso = 0;
selecaoModulo = 0;
selecaoTurma=0;
selecaoDisciplina = 0;
selecaoAno = 2014;
selecaoProfessor=0;
assunto = "";
conteudo = "";
}



public String localizaEmailProfessor(){
professorDao=new ProfessorDaoImplementation();
String destinatario = professorDao.localizaEmailProfessor(selecaoProfessor);
return destinatario;
}



public List<Curso> listaCursos(){
cursoDao = new CursoDaoImplementation();
listaCursos = cursoDao.listaCursos();
listaProfessoresTurma();

return listaCursos;
}


public List<Disciplina> listaDisciplinas(){
disciplinaDao=new DisciplinaDaoImplementation();
if(selecaoModulo==0){
listaDisciplinas = disciplinaDao.localizaDisciplinasCurso(selecaoCurso);
}
else{
listaDisciplinas = disciplinaDao.localizaDisciplinasCursoModulo(selecaoCurso, selecaoModulo);
}

return listaDisciplinas;
}


public List<Turma> listaTurmas(){
turmaDao=new TurmaDaoImplementation();
if(selecaoAno>0 && selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo ==0){
listaTurmas = turmaDao.listaTurmaCurso(selecaoCurso, selecaoSemestre, selecaoAno);
listaProfessoresTurma();
}
else
if(selecaoAno>0 && selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo > 0){
listaTurmas = turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno);
listaProfessoresTurma();
}
return listaTurmas;
}


public List<Professor> listaProfessoresTurma(){
if(selecaoTurma>0 && selecaoModulo>0 && selecaoDisciplina==0){
professorDao = new ProfessorDaoImplementation();
listaProfessores=professorDao.listaProfessorTurma(selecaoTurma);
System.out.println("Tamanho Turma:"+listaProfessores.size());
System.out.println("ID:"+selecaoTurma);

for (Professor d: listaProfessores){
System.out.println(d.getNome());
}

}
else
if(selecaoTurma>0 && selecaoModulo>0){
professorDao = new ProfessorDaoImplementation();
listaProfessores=professorDao.listaProfessorTurmaDisciplina(selecaoTurma, selecaoDisciplina);
System.out.println("Tamanho Módulo:"+listaProfessores.size());
System.out.println("ID:"+selecaoTurma);
System.out.println("Disc:"+selecaoDisciplina);
System.out.println("Tamanho da lista de disciplinas:"+listaDisciplinas.size());

for (Professor d: listaProfessores){
System.out.println(d.getNome());
}
}
else{
listaProfessores.clear();


}

return listaProfessores;	
}


public List<Integer> listaAnos(){
	  Calendar cal = GregorianCalendar.getInstance(); 
	 

	  anos.clear();
	  
	 
	  ano = cal.get(Calendar.YEAR); 

      
	    if(selecaoSemestre==2){

	    for (int i=0;i<=2;i++){
	    anos.add(ano);
	    ano=ano-1;
	    }
	    }
	    else{
	    ano=cal.get(Calendar.YEAR);
	    anos.add(ano);
	    anos.add(ano-1);
	    }
	    
	  listaTurmas();
	
  return anos;
	}



public String retornar(){
return "./indexAluno.jsf";
}

public int getSelecaoCurso() {
	return selecaoCurso;
}


public void setSelecaoCurso(int selecaoCurso) {
	this.selecaoCurso = selecaoCurso;
}


public int getSelecaoModulo() {
	return selecaoModulo;
}


public void setSelecaoModulo(int selecaoModulo) {
	this.selecaoModulo = selecaoModulo;
}


public int getSelecaoProfessor() {
	return selecaoProfessor;
}


public void setSelecaoProfessor(int selecaoProfessor) {
	this.selecaoProfessor = selecaoProfessor;
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


public List<Professor> getListaProfessores() {
	return listaProfessores;
}


public void setListaProfessores(List<Professor> listaProfessores) {
	this.listaProfessores = listaProfessores;
}

public List<Curso> getListaCursos() {
	return listaCursos;
}



public void setListaCursos(List<Curso> listaCursos) {
	this.listaCursos = listaCursos;
}


public List<Disciplina> getListaDisciplinas() {
	return listaDisciplinas;
}


public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
	this.listaDisciplinas = listaDisciplinas;
}


public int getSelecaoTurma() {
	return selecaoTurma;
}


public void setSelecaoTurma(int selecaoTurma) {
	this.selecaoTurma = selecaoTurma;
}


public int getSelecaoDisciplina() {
	return selecaoDisciplina;
}


public void setSelecaoDisciplina(int selecaoDisciplina) {
	this.selecaoDisciplina = selecaoDisciplina;
}


public List<Turma> getListaTurmas() {
	return listaTurmas;
}


public void setListaTurmas(List<Turma> listaTurmas) {
	this.listaTurmas = listaTurmas;
}


public int getSelecaoAno() {
	return selecaoAno;
}


public void setSelecaoAno(int selecaoAno) {
	this.selecaoAno = selecaoAno;
}


public int getSelecaoSemestre() {
	return selecaoSemestre;
}


public void setSelecaoSemestre(int selecaoSemestre) {
	this.selecaoSemestre = selecaoSemestre;
}


public List<Integer> getAnos() {
	return anos;
}


public void setAnos(List<Integer> anos) {
	this.anos = anos;
}


public UploadedFile getArquivo() {
	return arquivo;
}


public void setArquivo(UploadedFile arquivo) {
	this.arquivo = arquivo;
}


public boolean isHabilitaGridArquivo() {
	return habilitaGridArquivo;
}


public void setHabilitaGridArquivo(boolean habilitaGridArquivo) {
this.habilitaGridArquivo=habilitaGridArquivo;
}


public String getCaminho() {
	return caminho;
}


public void setCaminho(String caminho) {
	this.caminho = caminho;
}

}
