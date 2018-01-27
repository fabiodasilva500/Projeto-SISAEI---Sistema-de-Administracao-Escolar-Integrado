package ManagedBeans;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import Dao.AcessoDao;
import Dao.AcessoDaoImplementation;
import Dao.AlunoDao;
import Dao.DiarioDao;
import Dao.DiarioDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.ProfessorDao;
import Dao.ProfessorDaoImplementation;
import Dao.ProfessorDisciplinaTurmaDao;
import Dao.ProfessorDisciplinaTurmaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Acesso;
import Pojo.Aluno;
import Pojo.Curso;
import Pojo.Diario;
import Pojo.Disciplina;
import Pojo.Professor;
import Pojo.ProfessorDisciplinaTurma;
import Pojo.Turma;



@ManagedBean (name="contatoProfessorMB")
@ViewScoped
public class ContatoProfessorMB  implements Serializable{
	
	private Diario diario;
	private int selecaoTurno;
	private int selecaoTurma;
	private int selecaoProf;
	private int selecaoDisciplina;
	private int selecaoCurso;
	private int selecaoSemestre;
	private int selecaoAno;
	private int selecaoModulo;
	private String selecaoAluno;
	private boolean selecaoEmail;
	private boolean habilitaGridAluno;
	private List<Professor> listaProfessor;
	private List<Turma> listaTurmas;
	private List<ProfessorDisciplinaTurma> listaTurma;
	private List<Disciplina> listaDisciplina;
	private List<Aluno> listaAlunos;
	private List<Curso> listaCursos;
	private List<Boolean> presenca;
	private List<Aluno> presencaAlunos;
	private List<Integer> anos;
	
	private boolean desabilitaModulo;
	private boolean desabilitaCurso;
	private boolean desabilitaTurma;
	private boolean desabilitaDisciplina;
	private boolean desabilitaAluno;
	private boolean desabilitaData;
	private boolean desabilitaTurno;
	private boolean desabilitaAssunto;
	private boolean desabilitaConteudo;
	
	private boolean pre;
	private boolean value1;
	private List<Aluno> alunosPresenca;

	private AlunoDao alunoDao;
	private AcessoDao aDao;

	private ProfessorDao pDao;
	private String prof;
	private TurmaDao tDao;
	private DiarioDao dDao;
	

	private Integer ano;
	private ProfessorDisciplinaTurmaDao pdtDao;
    private AcessoMB acessoAtual;
    private int selecaoProfessor;
    private boolean habilitaAusenciaParcial;
    private double faltas;
    private boolean desabilitaFaltas;
    
    private List<Double> opcoes;
    private DisciplinaDao disciplinaDao;
    
    private int ade;
    private String assunto;
    private String conteudo;
    
    private UploadedFile arquivo;  
    private String caminho;
    private boolean habilitaGridArquivo;
    private FileUploadEvent arquivoSelecionado;
   
    

    
    
	//Construtor da Classe
	public ContatoProfessorMB(){
		setDiario(new Diario());
		setAcesso(new AcessoMB());
		pDao = new ProfessorDaoImplementation();
		tDao = new TurmaDaoImplementation();
		dDao = new DiarioDaoImplementation();
		pdtDao=new ProfessorDisciplinaTurmaDaoImplementation();
		aDao=new AcessoDaoImplementation();
		disciplinaDao = new DisciplinaDaoImplementation();
		setListaProfessor(pDao.listaProfessores());
		setListaTurma(new ArrayList<ProfessorDisciplinaTurma>());
		setListaDisciplina(new ArrayList<Disciplina>());
		setListaAlunos(new ArrayList<Aluno>());
		setPresenca(new ArrayList<Boolean>());
		setAlunosPresenca(new ArrayList<Aluno>());
		setPresencaAlunos(new ArrayList<Aluno>());
		setListaTurmas(new ArrayList<Turma>());
		diario.setIdDiario(dDao.ultimoDiario() + 1);
		setAnos(new ArrayList<Integer>());
		desabilitaCurso=true;
		desabilitaModulo=true;
		desabilitaTurma=true;
		desabilitaDisciplina=true;
		desabilitaAluno=true;
		desabilitaData=true;
		desabilitaTurno=true;
		selecaoEmail=true;
		
		setListaCursos(new ArrayList<Curso>());
		pdtDao=new ProfessorDisciplinaTurmaDaoImplementation();
        faltas=5;		
        desabilitaFaltas=true;
        opcoes=new ArrayList<Double>();

	}
	

	public List<Integer> listaAnos(){
		  Calendar cal = GregorianCalendar.getInstance(); 
		  
		  if(selecaoAno==0)
		  {
		  selecaoCurso=0;
		  }

		  anos.clear();
		  if(selecaoSemestre>0){
			  desabilitaCurso=false;
			  }
		  if(selecaoSemestre==0){
		  desabilitaCurso=true;
		  desabilitaModulo=true;
		  desabilitaTurma=true;
		  desabilitaDisciplina=true;
		  desabilitaAluno=true;
		  desabilitaData=true;
		  desabilitaTurno=true;
		  desabilitaAluno=true;
		  selecaoAno=cal.get(Calendar.YEAR); 
	      selecaoCurso=0;
		  }

	    ano = cal.get(Calendar.YEAR); 
	    anos.add(ano);
	    
	    
	    return anos;
		}



	
	public List<Curso> listaCursos(Acesso acesso){
    Calendar cal = GregorianCalendar.getInstance(); 
    ano = cal.get(Calendar.YEAR); 


	System.out.println("Inicializando:"+acesso.getIdentificacao());
	selecaoProfessor=Integer.parseInt(acesso.getIdentificacao());
	System.out.println("Ano:"+ano);
	setListaCursos(pdtDao.listaCursos(selecaoProfessor, ano, selecaoSemestre));
	habilidaModulo();
	habilitaAluno();
    habilitaEmail();

	return listaCursos;
	}

	
	

	public void listaTurmaModulo(){
	setListaTurmas(pdtDao.localizaTurmaModulo(selecaoCurso, selecaoProfessor, ano, selecaoModulo, selecaoSemestre));
	habilitaTurma();
    listaDisciplinas();
    listaAlunos();
    habilitaEmail();
	}

	

	public void listaTurmaCurso(){
	setListaTurmas(pdtDao.localizaTurmaCurso(selecaoCurso, selecaoProfessor, ano, selecaoSemestre));
	habilitaTurma();
    listaDisciplinas();
    listaAlunos();
    habilitaEmail();

	}
	
	public void listaDisciplinas(){
	setListaDisciplina(pdtDao.listaDisciplinas(selecaoCurso, ano, selecaoSemestre, selecaoModulo, selecaoProfessor));
    habilitaDisciplina();
    habilitaAluno();
    habilitaEmail();
	}
	
	
	
	public void habilidaModulo(){
		if(selecaoSemestre>0 && selecaoCurso>0)
		{
		desabilitaModulo=false;
		}
		else{
	
		desabilitaModulo=true;
		desabilitaTurma=true;
		desabilitaDisciplina=true;
		desabilitaData=true;
		desabilitaTurno=true;
		selecaoModulo=0;
		selecaoTurma=0;
		selecaoDisciplina=0;
		selecaoTurno=0;
		}
		}
		
		public void habilitaTurma(){
			System.out.println("Habilitando turma");
			
			System.out.println("Seleção semestre:"+selecaoSemestre);
			System.out.println("Seleção curso:"+selecaoCurso);
			System.out.println("Seleção módulo:"+selecaoModulo);
			
			if(selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo>0)
			{
			desabilitaTurma=false;
			System.out.println("Habilitado");
			}
			else{
			System.out.println("Desabilitado");

			desabilitaTurma=true;
			desabilitaDisciplina=true;
			desabilitaData=true;
			desabilitaTurno=true;
			selecaoTurma=0;
			selecaoDisciplina=0;
			selecaoTurno=0;
			}
			}
			
	
		
		public void habilitaDisciplina(){
		if(selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo>0 && selecaoTurma>0){
		desabilitaDisciplina=false;
		desabilitaData=false;
		desabilitaTurno=false;
		}
		else{
		desabilitaDisciplina=true;
		desabilitaData=true;
		desabilitaTurno=true;
		selecaoDisciplina=0;
		}

	    System.out.println("Turma selecionada:"+selecaoTurma);
	    }		

		public void habilitaAluno(){
				if(selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo>0 && selecaoTurma>0 && selecaoDisciplina>0){
				desabilitaAluno=false;
				}
				else{
				desabilitaAluno=true;
				selecaoAluno="";
				}

			    System.out.println("Turma selecionada:"+selecaoTurma);
			    }		
		
		
		public void habilitaEmail(){
			if(selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo>0 && selecaoTurma>0 && selecaoDisciplina>0){
				desabilitaAssunto=false;
				desabilitaConteudo=false;
			    }
				else{
				desabilitaAssunto=true;
				desabilitaConteudo=true;
				assunto="";
				conteudo="";
				
				}
		}


	
public List<Aluno> listaAlunos(){
		setListaAlunos(dDao.listaAlunos(selecaoTurma));
		System.out.println("Tamanho:"+listaAlunos.size());
		if(selecaoSemestre>0 && selecaoCurso>0 && selecaoModulo>0 && selecaoTurma>0 && selecaoDisciplina>0){
			desabilitaAluno=false;
			}
			else{
		    desabilitaAluno=true;
			selecaoAluno="";
			}

		    System.out.println("Turma selecionada:"+selecaoTurma);
		 return listaAlunos;   
         }		


public void enviarEmail(Acesso acessoAtual) throws AddressException, MessagingException, IOException {
	
	if(habilitaGridAluno && habilitaGridArquivo==false){
	enviarAlunoUnicoSemAnexo(acessoAtual);
	}
	else
	if(habilitaGridAluno == false && habilitaGridArquivo==false){
	enviarParaTodosSemAnexo(acessoAtual);
	}
	else
	if(habilitaGridAluno && habilitaGridArquivo==true){
	enviarAlunoUnicoComAnexo(acessoAtual,arquivoSelecionado, arquivoSelecionado.getFile().getInputstream());
	}
	else
	if(habilitaGridAluno==false && habilitaGridArquivo==true){
	enviaParaTodosComAnexo(acessoAtual);
	}
	
}

public void enviarAlunoUnicoSemAnexo(Acesso acessoAtual) throws AddressException, MessagingException {

    String emailProfessor = aDao.localizaEmail(acessoAtual.getIdentificacao());

	String destinatario = emailProfessor + ","+selecaoAluno;
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

	context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Email enviado com sucesso.",""+""));
    limpaCampo();
	}

	else{
	context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
	}
	}
	



public void enviarParaTodosSemAnexo(Acesso acessoAtual) throws AddressException, MessagingException {
boolean enviado=false;

System.out.println("Enviando email");

for (Aluno aluno:listaAlunos){
	String destinatario = aluno.getEmail();

		
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

	enviado=true;
	}
	}
   FacesContext context = FacesContext.getCurrentInstance();
    if(enviado){
    context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Email enviado com sucesso.",""+""));
	enviaCopiaSemAnexo(acessoAtual);
    }
    else{
    context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado.",""+""));
    }

}


public void enviaCopiaSemAnexo(Acesso acessoAtual) throws AddressException, MessagingException {
    String emailProfessor = aDao.localizaEmail(acessoAtual.getIdentificacao());
   
    System.out.println("Email Professor:"+emailProfessor);
    
	String destinatario = emailProfessor;			
		
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
    limpaCampo();
	}
	}


    public void enviarAlunoUnicoComAnexo(Acesso acessoAtual, FileUploadEvent event, InputStream in) throws AddressException, MessagingException, IOException{
    	FacesContext context = FacesContext.getCurrentInstance();  
		
    	String host = null;
		String port = null;
		boolean enviado=false;
	

		final String email = "eteczl2000@gmail.com";
		final String senha = "eteczonaleste2000";
		
		String destinatario = selecaoAluno;

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
		

		
		if(enviado){
	    context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Email enviado com sucesso.",""+""));
	    enviarCopiaProfessorComAnexo(acessoAtual);
	    limpaCampo();
		}

		else{
		context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
		}
		}
		
    
    
    public void enviaParaTodosComAnexo(Acesso acessoAtual) throws AddressException, MessagingException{
FacesContext context = FacesContext.getCurrentInstance();  
		
    	String host = null;
		String port = null;
		boolean enviado=false;
	

		final String email = "eteczl2000@gmail.com";
		final String senha = "eteczonaleste2000";
	    
		for (Aluno aluno:listaAlunos){
		String destinatario = aluno.getEmail();

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
		}
		
		if(enviado){
		enviarCopiaProfessorComAnexo(acessoAtual);
	    limpaCampo();
		}

		else{
		context.addMessage("formContatoProfessor:mensagem", new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email não enviado, todos os campos são de preenchimento obrigatório.",""+""));	
		}
		}
   
    
  
    
    public void enviarCopiaProfessorComAnexo (Acesso acessoAtual) throws MessagingException{
    	String host = null;
		String port = null;

		final String email = "eteczl2000@gmail.com";
		final String senha = "eteczonaleste2000";
		
	    String emailProfessor = aDao.localizaEmail(acessoAtual.getIdentificacao());
		String destinatario = emailProfessor;	

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
			

				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}


			msg.setContent(multipart);
			Transport.send(msg);

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
  
        System.out.println(caminho);
  
                
        FileOutputStream fos = new FileOutputStream(caminho);  
        fos.write(conteudo);  
        fos.close();  
        
        arquivoSelecionado = evento;
       }
    
	
     public void limpaCampo(){
     desabilitaCurso=true;
     desabilitaModulo=true;
     desabilitaTurma=true;
     desabilitaDisciplina=true;
     desabilitaAssunto=true;
     desabilitaConteudo=true;
     desabilitaAssunto=true;
     desabilitaConteudo=true;
     selecaoCurso=0;
     selecaoModulo=0;
     selecaoTurma=0;
     selecaoDisciplina=0;
     selecaoSemestre=1;
     assunto="";
     conteudo="";
      }
     
     
    

public List<Aluno> getPresencaAlunos() {
	return presencaAlunos;
}

public void setPresencaAlunos(List<Aluno> presencaAlunos) {
	this.presencaAlunos = presencaAlunos;
}

public boolean isValue1() {
	return value1;
}

public void setValue1(boolean value1) {
	this.value1 = value1;
}

public boolean isPre() {
	return pre;
}

public void setPre(boolean pre) {
	
	this.pre = pre;
	
	
}


public List<Aluno> getAlunosPresenca() {
	return alunosPresenca;
}

public void setAlunosPresenca(List<Aluno> alunosPresenca) {
	this.alunosPresenca = alunosPresenca;
}



	public List<Boolean> getPresenca() {
		return presenca;
	}

	public void setPresenca(List<Boolean> presenca) {
		this.presenca = presenca;
	}

	public int getSelecaoCurso() {
		return selecaoCurso;
	}

	public void setSelecaoCurso(int selecaoCurso) {
		this.selecaoCurso = selecaoCurso;
	}

	
public AlunoDao getAlunoDao() {
	return alunoDao;
}


public void setAlunoDao(AlunoDao alunoDao) {
	this.alunoDao = alunoDao;
}

public List<Aluno> getListaAlunos() {
	return listaAlunos;
}


public void setListaAlunos(List<Aluno> listaAlunos) {
	this.listaAlunos = listaAlunos;
}


public int getSelecaoDisciplina() {
	return selecaoDisciplina;
}


public void setSelecaoDisciplina(int selecaoDisciplina) {
	this.selecaoDisciplina = selecaoDisciplina;
}


public DiarioDao getdDao() {
	return dDao;
}


public void setdDao(DiarioDao dDao) {
	this.dDao = dDao;
}


public List<Disciplina> getListaDisciplina() {
	return listaDisciplina;
}


public void setListaDisciplina(List<Disciplina> listaDisciplina) {
	this.listaDisciplina = listaDisciplina;
}


public TurmaDao gettDao() {
	return tDao;
}


public void settDao(TurmaDao tDao) {
	this.tDao = tDao;
}




public List<ProfessorDisciplinaTurma> getListaTurma() {
	return listaTurma;
}


public void setListaTurma(List<ProfessorDisciplinaTurma> listaTurma) {
	this.listaTurma = listaTurma;
}


public int getSelecaoTurma() {
	return selecaoTurma;
}

public void setSelecaoTurma(int selecaoTurma) {
	this.selecaoTurma = selecaoTurma;
}

public String getProf() {
return prof;
}


public void setProf(String prof) {
this.prof = prof;
}


public List<Professor> getListaProfessor() {
	return listaProfessor;
}


public void setListaProfessor(List<Professor> listaProfessor) {
	this.listaProfessor = listaProfessor;
}




public ProfessorDao getpDao() {
	return pDao;
}


public void setpDao(ProfessorDao pDao) {
	this.pDao = pDao;
}




public int getSelecaoProf() {
	return selecaoProf;
}


public void setSelecaoProf(int selecaoProf) {
	this.selecaoProf = selecaoProf;
}


public int getSelecaoTurno() {
	return selecaoTurno;
}

public void setSelecaoTurno(int selecaoTurno) {
	this.selecaoTurno = selecaoTurno;
}

public Diario getDiario() {
	return diario;
}

public void setDiario(Diario diario) {
	this.diario = diario;
}



public int getSelecaoSemestre() {
	return selecaoSemestre;
}

public void setSelecaoSemestre(int selecaoSemestre) {
	this.selecaoSemestre = selecaoSemestre;
}

public int getSelecaoAno() {
	return selecaoAno;
}

public void setSelecaoAno(int selecaoAno) {
	this.selecaoAno = selecaoAno;
}

public List<Integer> getAnos() {
	return anos;
}

public void setAnos(List<Integer> anos) {
	this.anos = anos;
}


public Integer getAno() {
	return ano;
}

public void setAno(Integer ano) {
	this.ano = ano;
}

public int getSelecaoModulo() {
	return selecaoModulo;
}

public void setSelecaoModulo(int selecaoModulo) {
	this.selecaoModulo = selecaoModulo;
}

public boolean isDesabilitaModulo() {
	return desabilitaModulo;
}

public void setDesabilitaModulo(boolean desabilitaModulo) {
	this.desabilitaModulo = desabilitaModulo;
}

public boolean isDesabilitaCurso() {
	return desabilitaCurso;
}

public void setDesabilitaCurso(boolean desabilitaCurso) {
	this.desabilitaCurso = desabilitaCurso;
}

public List<Curso> getListaCursos() {
	return listaCursos;
}

public void setListaCursos(List<Curso> listaCursos) {
	this.listaCursos = listaCursos;
}

public AcessoMB getAcesso() {
	return acessoAtual;
}

public void setAcesso(AcessoMB acessoAtual) {
	this.acessoAtual = acessoAtual;
}

public int getSelecaoProfessor() {
	return selecaoProfessor;
}

public void setSelecaoProfessor(int selecaoProfessor) {
	this.selecaoProfessor = selecaoProfessor;
}

public boolean isDesabilitaTurma() {
	return desabilitaTurma;
}

public void setDesabilitaTurma(boolean desabilitaTurma) {
	this.desabilitaTurma = desabilitaTurma;
}

public boolean isDesabilitaDisciplina() {
	return desabilitaDisciplina;
}

public void setDesabilitaDisciplina(boolean desabilitaDisciplina) {
	this.desabilitaDisciplina = desabilitaDisciplina;
}



public boolean isDesabilitaTurno() {
	return desabilitaTurno;
}

public void setDesabilitaTurno(boolean desabilitaTurno) {
	this.desabilitaTurno = desabilitaTurno;
}

public boolean isDesabilitaData() {
	return desabilitaData;
}

public void setDesabilitaData(boolean desabilitaData) {
	this.desabilitaData = desabilitaData;
}

public List<Turma> getListaTurmas() {
	return listaTurmas;
}

public void setListaTurmas(List<Turma> listaTurmas) {
	this.listaTurmas = listaTurmas;
}



//carrega turma de acordo com a selecao do nome do professor
public List<ProfessorDisciplinaTurma> listaTurmaProfessor(){
	
	setListaTurma(tDao.listaTurmaProfessor(selecaoProf));
	listaDisciplinaProfessor();
	return listaTurma;
}


//carrega disciplina de acordo com a seleção do nome do professor
public List<Disciplina> listaDisciplinaProfessor(){
	
	setListaDisciplina(dDao.listaDisciplinaProf(selecaoProf));
	
	return listaDisciplina;
}




public void inicializaCampo(){
selecaoCurso=0;
selecaoTurma=0;
selecaoModulo=0;
selecaoDisciplina=0;
selecaoTurno=0;		
}




public boolean isHabilitaAusenciaParcial() {
	return habilitaAusenciaParcial;
}


public void setHabilitaAusenciaParcial(boolean habilitaAusenciaParcial) {
	this.habilitaAusenciaParcial = habilitaAusenciaParcial;
}


public double getFaltas() {
	return faltas;
}


public void setFaltas(double faltas) {
	this.faltas = faltas;
}


public boolean isDesabilitaFaltas() {
	return desabilitaFaltas;
}


public void setDesabilitaFaltas(boolean desabilitaFaltas) {
	this.desabilitaFaltas = desabilitaFaltas;
}


public List<Double> getOpcoes() {
	return opcoes;
}


public void setOpcoes(List<Double> opcoes) {
	this.opcoes = opcoes;
}


public int getAde() {
	return ade;
}


public void setAde(int ade) {
	this.ade = ade;
}


public String getSelecaoAluno() {
	return selecaoAluno;
}


public void setSelecaoAluno(String selecaoAluno) {
	this.selecaoAluno = selecaoAluno;
}


public boolean isDesabilitaAluno() {
	return desabilitaAluno;
}


public void setDesabilitaAluno(boolean desabilitaAluno) {
	this.desabilitaAluno = desabilitaAluno;
}


public boolean isSelecaoEmail() {
	return selecaoEmail;
}


public void setSelecaoEmail(boolean selecaoEmail) {
	this.selecaoEmail = selecaoEmail;
}


public boolean isHabilitaGridAluno() {
	return habilitaGridAluno;
}


public void setHabilitaGridAluno(boolean habilitaGridAluno) {
	this.habilitaGridAluno = habilitaGridAluno;
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


public boolean isDesabilitaAssunto() {
	return desabilitaAssunto;
}


public void setDesabilitaAssunto(boolean desabilitaAssunto) {
	this.desabilitaAssunto = desabilitaAssunto;
}


public boolean isDesabilitaConteudo() {
	return desabilitaConteudo;
}


public void setDesabilitaConteudo(boolean desabilitaConteudo) {
	this.desabilitaConteudo = desabilitaConteudo;
}


public UploadedFile getArquivo() {
	return arquivo;
}


public void setArquivo(UploadedFile arquivo) {
	this.arquivo = arquivo;
}


public String getCaminho() {
	return caminho;
}


public void setCaminho(String caminho) {
	this.caminho = caminho;
}


public boolean ishabilitaGridArquivo() {
	return habilitaGridArquivo;
}


public void sethabilitaGridArquivo(boolean habilitaGridArquivo) {
	this.habilitaGridArquivo = habilitaGridArquivo;
}


public FileUploadEvent getArquivoSelecionado() {
	return arquivoSelecionado;
}


public void setArquivoSelecionado(FileUploadEvent arquivoSelecionado) {
	this.arquivoSelecionado = arquivoSelecionado;
}

}
