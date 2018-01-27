package ManagedBeans;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.AcessoDao;
import Dao.AcessoDaoImplementation;
import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.CoordenadorDao;
import Dao.CoordenadorDaoImplementation;
import Dao.DiretoriaDao;
import Dao.DiretoriaDaoImplementation;
import Dao.ProfessorDao;
import Dao.ProfessorDaoImplementation;
import Dao.SecretariaDao;
import Dao.SecretariaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Acesso;

@ManagedBean(name="acessoMB")
@SessionScoped
public class AcessoMB{
	public Acesso acessoAtual;

	private Acesso cadastraAcesso;
	private Acesso recuperaDados;
	private Acesso excluirDados;
	private Acesso alteraPrimeiroAcesso;
	private Acesso alteraAcesso;
	
	
	private AcessoDao aDao;
    private AlunoDao alunoDao;
    private ProfessorDao professorDao;
    private SecretariaDao secretariaDao;
    private CoordenadorDao coordenadorDao;
    private DiretoriaDao diretoriaDao;
    private TurmaDao turmaDao;
    private boolean habilitaCampos;
    private String loginLocalizado;
    private String senhaLocalizada;
	private boolean habilitaLoginSenha;
	private String requisicaoDirecao;
	
	private String emailCadastrado;
	private String rgCadastrado;
	

	public AcessoMB(){
		setAcessoAtual(new Acesso());
		setCadastraAcesso(new Acesso());
		setRecuperaDados(new Acesso());
		setExcluirDados(new Acesso());
		setAlteraPrimeiroAcesso(new Acesso());
	    setAlteraAcesso(new Acesso());
		aDao=new AcessoDaoImplementation();
	    alunoDao=new AlunoDaoImplementation();
	    professorDao=new ProfessorDaoImplementation();
	    turmaDao=new TurmaDaoImplementation();
	    secretariaDao=new SecretariaDaoImplementation();
	    coordenadorDao=new CoordenadorDaoImplementation();
	    diretoriaDao = new DiretoriaDaoImplementation();
	    habilitaLoginSenha=false;
	    System.out.println("Aqui");
	    
	}

	
	public String validaAcessoSecretaria(){
	    AcessoDao a=new AcessoDaoImplementation();
	    boolean validado = a.validaAcessoSecretaria(acessoAtual.getIdentificacao());
	    if(validado){
	    System.out.println("Autenticado");
	    return "";
	    }
	    else{
	    System.out.println("Não autenticado");
	    return "./index.jsf";
	    }
	}
	    
	public String retornar(){
	alteraAcesso.setIdentificacao("");
	alteraAcesso.setNome("");
	alteraAcesso.setRg("");
	alteraAcesso.setEmail("");
	alteraAcesso.setNovoEmail("");
    alteraAcesso.setSenha("");
    alteraAcesso.setNovaSenha("");
	return "./acesso.jsf";
	
	}
	
	public Acesso getAcessoAtual() {
		return acessoAtual;
	}


	public void setAcessoAtual(Acesso acessoAtual) {
		this.acessoAtual = acessoAtual;
	}
	
	
    public String consultarNome(){
    return "";
    
    }


	public String inserir(){
	retirarMascaraCadastro();
	boolean validaNomeRG=false;
	if(cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Aluno")){
	//cadastraAcesso.setNome(alunoDao.localizaNomeAluno(cadastraAcesso.getIdentificacao()));
    validaNomeRG=(alunoDao.validaNomeRG(cadastraAcesso.getIdentificacao(), cadastraAcesso.getRg(), cadastraAcesso.getNome()));
	}
	else
	if(cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Professor")){
	//cadastraAcesso.setNome(professorDao.localizaNomeProfessor(cadastraAcesso.getIdentificacao()));
	int identificacaoConvertida=Integer.parseInt(cadastraAcesso.getIdentificacao());
	validaNomeRG=(professorDao.validaNomeRG(identificacaoConvertida, cadastraAcesso.getRg(), cadastraAcesso.getNome()));
	}
	else
	if(cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Secretaria")){
	System.out.println("Verificando secretária");
	//cadastraAcesso.setNome(secretariaDao.localizaNomeSecretaria(cadastraAcesso.getIdentificacao()));
	validaNomeRG=(secretariaDao.validaNomeRG(cadastraAcesso.getIdentificacao(), cadastraAcesso.getRg(), cadastraAcesso.getNome()));
	}
	else
	if(cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Coordenador")){
	//cadastraAcesso.setNome(coordenadorDao.localizaNomeCoordenador(cadastraAcesso.getIdentificacao()));
	validaNomeRG=(coordenadorDao.validaNomeRG(cadastraAcesso.getIdentificacao(), cadastraAcesso.getRg(), cadastraAcesso.getNome()));		
	}
	else
	if(cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Diretoria")){
	System.out.println("Verificando secretária");
	//cadastraAcesso.setNome(secretariaDao.localizaNomeSecretaria(cadastraAcesso.getIdentificacao()));
	validaNomeRG=(diretoriaDao.validaNomeRG(cadastraAcesso.getIdentificacao(), cadastraAcesso.getRg(), cadastraAcesso.getNome()));
	System.out.println("Validado:"+validaNomeRG);
	}
	

    if(cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Aluno")){
    inserirAluno(validaNomeRG);
    }
    else
    if(!cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Aluno")){
    inserirFuncionario(validaNomeRG);	
    }
    
	return "";	
	}
	
	
	public void inserirAluno(boolean validaNomeRG){
		FacesContext context = FacesContext.getCurrentInstance();
		if(cadastraAcesso.getEmail().length()<=8 || cadastraAcesso.getSenha().length()<=4)
		{
		context.addMessage("formCadastroAcesso:mensagem",new FacesMessage(
		FacesMessage.SEVERITY_ERROR,
		"A senha que você deseja cadastrar deve conter no mínimo 5 caracteres",
		"" + ""));	
		}
		else
		if(cadastraAcesso.getNome()!=null && cadastraAcesso.getNome()!=""){
		    if(validaNomeRG){
		    boolean efetuado = aDao.inserirAcesso(cadastraAcesso);
		    
		    if(efetuado==true){
		    context.addMessage("formCadastroAcesso:mensagem",new FacesMessage(
			FacesMessage.SEVERITY_INFO,
			"Caro (a) : "+cadastraAcesso.getNome()+" seu login foi cadastrado com sucesso.",
			"" + ""));	
		    cadastraAcesso=new Acesso();
		    }
		    }
		    else{
		    context.addMessage("formCadastroAcesso:mensagem", new FacesMessage(
		    FacesMessage.SEVERITY_ERROR,"O nome e/ou RG informados não correspondem aos dados cadastrados no sistema.",""+""));
		    }
		    }
		   
			else
		    if(cadastraAcesso.getNome()==null || cadastraAcesso.getNome()=="" || cadastraAcesso.getEmail().length()>=9 && cadastraAcesso.getSenha().length()>=5){
		    context.addMessage("formCadastroAcesso:mensagem", new FacesMessage(
		    FacesMessage.SEVERITY_ERROR,"Nenhum registro foi localizado com os dados informados.",""+""));
		    } 
	}
	
	
	public void inserirFuncionario(boolean validaNomeRG){
		System.out.println("Aqui");
		FacesContext context = FacesContext.getCurrentInstance();
		if(cadastraAcesso.getNome()!=null && cadastraAcesso.getNome()!=""){
		    if(validaNomeRG){
		    boolean efetuado = aDao.inserirAcesso(cadastraAcesso);
		    
		    if(efetuado==true){
		    context.addMessage("formCadastroAcesso:mensagem",new FacesMessage(
			FacesMessage.SEVERITY_INFO,
			"Caro (a) : "+cadastraAcesso.getNome()+" seu login foi cadastrado com sucesso.",
			"" + ""));	
		    cadastraAcesso=new Acesso();
		    }
		    }
		    else{
		    context.addMessage("formCadastroAcesso:mensagem", new FacesMessage(
		    FacesMessage.SEVERITY_ERROR,"O nome e/ou RG informados não correspondem aos dados cadastrados no sistema.",""+""));
		    }
		    }
		   
			else
		    if(cadastraAcesso.getNome()==null || cadastraAcesso.getNome()==""){
		    context.addMessage("formCadastroAcesso:mensagem", new FacesMessage(
		    FacesMessage.SEVERITY_ERROR,"O nome da pessoa não foi localizado através da identificação informada.",""+""));
		    } 
	
	}
	
	
	

	public String consultar(){
		return "./recuperaSenha.jsf";
	}

	public String criarNovoCadastro(){
	    habilitaCampos=false;
	    cadastraAcesso=new Acesso();
		return "./novoCadastroAcesso.jsf";
	}

	public String alterar(){
		return "";
	}

	public String excluir(){
		return "./exclusaoDeCadastro.jsf";
	}
	
	
	public String logar() throws AddressException, IOException{
	excluiArquivos(); 	
	
		HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
        ses.removeAttribute("usuarioLogado");
        

	FacesContext context = FacesContext.getCurrentInstance();
	Acesso localizado=aDao.consultarAcessoAluno(acessoAtual.getEmail(), acessoAtual.getSenha());

	if(acessoAtual.getEmail().equals(localizado.getEmail())&&
	acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Secretaria")){
	acessoAtual.setIdentificacao(localizado.getIdentificacao());
	String primeiroAcesso=aDao.verificarPrimeiroAcesso(acessoAtual.getEmail(), acessoAtual.getSenha());
	
	if(primeiroAcesso.equalsIgnoreCase("Sim")) {
	return "./primeiroAcesso.jsf";
	}
	else{
	acessoAtual.setEmail("");
	System.out.println("Valor retornado secretaria:"+acessoAtual.getIdentificacao());
    ses.setAttribute("usuarioLogado", "Secretaria");      
    return "./index.jsf";
	}
	
	}
	else
	if(acessoAtual.getEmail().equals(localizado.getEmail())&&
	acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Aluno")){
	acessoAtual.setIdentificacao(localizado.getIdentificacao());
	String primeiroAcesso=aDao.verificarPrimeiroAcesso(acessoAtual.getEmail(), acessoAtual.getSenha());
	
	if(primeiroAcesso.equalsIgnoreCase("Sim")){
	return "./primeiroAcesso.jsf";
	}
	else{
	System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());
    ses.setAttribute("usuarioLogado", "Aluno");  

	return "./indexAluno.jsf";
	}
	}
	
	else
	if(acessoAtual.getEmail().equals(localizado.getEmail())&&
	acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Professor")){
	acessoAtual.setIdentificacao(localizado.getIdentificacao());
	String primeiroAcesso=aDao.verificarPrimeiroAcesso(acessoAtual.getEmail(), acessoAtual.getSenha());
	
	if(primeiroAcesso.equalsIgnoreCase("Sim")){
	return "./primeiroAcesso.jsf";
	}
	else{
	acessoAtual.setEmail("");
    ses.setAttribute("usuarioLogado", "Professor");  

	System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());

	return "./indexProfessor.jsf";
	}
	}
	
	else
	if(acessoAtual.getEmail().equals(localizado.getEmail())&&
	acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Coordenador")){
	acessoAtual.setIdentificacao(localizado.getIdentificacao());
	acessoAtual.setNome(localizado.getNome());
	String primeiroAcesso=aDao.verificarPrimeiroAcesso(acessoAtual.getEmail(), acessoAtual.getSenha());
	
	if(primeiroAcesso.equalsIgnoreCase("Sim")){
	return "./primeiroAcesso.jsf";
	}
	else{
	acessoAtual.setEmail("");
    ses.setAttribute("usuarioLogado", "Coordenador");  
	System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());

	return "./indexCoordenador.jsf";
	}	
	}
	
	else
		if(acessoAtual.getEmail().equals(localizado.getEmail())&&
		acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Diretoria")){
		acessoAtual.setIdentificacao(localizado.getIdentificacao());
		acessoAtual.setNome(localizado.getNome());
		String primeiroAcesso=aDao.verificarPrimeiroAcesso(acessoAtual.getEmail(), acessoAtual.getSenha());
		
		if(primeiroAcesso.equalsIgnoreCase("Sim")){
		return "./primeiroAcesso.jsf";
		}
		else{
		acessoAtual.setEmail("");
		System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());
	    ses.setAttribute("usuarioLogado", "Diretor");  
	    System.out.println(ses.getId());
		return "./selecaoDirecao.jsf";
		}	
		}
		
		else{
		context.addMessage("formLogin:growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados inválidos", ""+""));
		}
	
	
	return "";
	}
	
	
	    public String logarPrimeiroAcesso(){
		FacesContext context = FacesContext.getCurrentInstance();
		Acesso localizado=aDao.consultarAcessoAluno(acessoAtual.getEmail(), acessoAtual.getSenha());
		 
    	HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
        ses.removeAttribute("usuarioLogado");

		
		if(acessoAtual.getEmail().equals(localizado.getEmail())&&
		acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Secretaria")){
		acessoAtual.setIdentificacao(localizado.getIdentificacao());
		System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());
		acessoAtual.setEmail("");
        ses.setAttribute("usuarioLogado", "Secretaria");  

		return "./index.jsf";
		}
		else
		if(acessoAtual.getEmail().equals(localizado.getEmail())&&
		acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Aluno")){
		acessoAtual.setIdentificacao(localizado.getIdentificacao());
		acessoAtual.setEmail("");
		System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());
        ses.setAttribute("usuarioLogado", "Aluno");  

		return "./indexAluno.jsf";
		}
		else
		if(acessoAtual.getEmail().equals(localizado.getEmail())&&
		acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Professor")){
		acessoAtual.setIdentificacao(localizado.getIdentificacao());
		acessoAtual.setNome(localizado.getNome());
		acessoAtual.setEmail("");
		System.out.println("Valor retornado:"+acessoAtual.getIdentificacao());
        ses.setAttribute("usuarioLogado", "Professor");  

		return "./indexProfessor.jsf";
		}
		else
		if(acessoAtual.getEmail().equals(localizado.getEmail())&&
		acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Coordenador")){
		acessoAtual.setIdentificacao(localizado.getIdentificacao());
		acessoAtual.setNome(localizado.getNome());
		acessoAtual.setEmail("");
        ses.setAttribute("usuarioLogado", "Coordenador");  

   
		return "./indexCoordenador.jsf";
		}	

		if(acessoAtual.getEmail().equals(localizado.getEmail())&&
		acessoAtual.getSenha().equals(localizado.getSenha()) && localizado.getTipoAcesso().equalsIgnoreCase("Diretoria")){
		acessoAtual.setIdentificacao(localizado.getIdentificacao());
		acessoAtual.setNome(localizado.getNome());
		acessoAtual.setEmail("");
		System.out.println("Retornando a tela de acesso");
        ses.setAttribute("usuarioLogado", "Diretor");  

		return "./indexDirecao.jsf";
		}	
		else{
		context.addMessage("formLogin:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados inválidos", ""+""));
		}

		return "";
		}
		
	    
	    public String trataRequisicao(){
	    if(requisicaoDirecao.equalsIgnoreCase("Direcao")){
	    return "./indexDirecao.jsf";
	    }
	    else
	    if(requisicaoDirecao.equalsIgnoreCase("Secretaria")){
	    return "./index.jsf";
	    }
	    else
	    if(requisicaoDirecao.equalsIgnoreCase("Coordenacao")){
	    return "./indexCoordenador.jsf";
	    }
	    return "";
	    }
	
	public void verificaSelecao(){
	if(!cadastraAcesso.getTipoAcesso().equalsIgnoreCase("Aluno")){
    habilitaCampos=true;
	}
	else{
	habilitaCampos=false;
	}
	}


	public boolean isHabilitaCampos() {
		return habilitaCampos;
	}


	public void setHabilitaCampos(boolean habilitaCampos) {
		this.habilitaCampos = habilitaCampos;
	}
	
	public Acesso getCadastraAcesso() {
		return cadastraAcesso;
	}


	public void setCadastraAcesso(Acesso cadastraAcesso) {
		this.cadastraAcesso = cadastraAcesso;
	}


	public Acesso getRecuperaDados() {
		return recuperaDados;
	}


	public void setRecuperaDados(Acesso recuperaDados) {
		this.recuperaDados = recuperaDados;
	}


	public void validaDados(){
    retirarMascaraConsulta();
	habilitaCampos=true;
	FacesContext context = FacesContext.getCurrentInstance();
	boolean encontrado=aDao.validaDados(recuperaDados.getIdentificacao(), recuperaDados.getNome(), recuperaDados.getRg(), recuperaDados.getEmail());
    if(encontrado){
    List<String> dadosRetornados=aDao.localizaLoginSenha(recuperaDados.getIdentificacao());
    loginLocalizado=dadosRetornados.get(0);
    senhaLocalizada = dadosRetornados.get(1);
    habilitaLoginSenha=true;
    habilitaCampos=false;
    }
    else{
    context.addMessage("formRecuperaSenha:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login e senha não localizados, por favor verifique os campos informados.", ""+""));
    habilitaLoginSenha=false;
    habilitaCampos=false;
    }
	}
	
	public String geraNovaSenha()
	{
	setRgCadastrado(rgCadastrado.replaceAll("[ ./-]", ""));

    String emailLocalizado = aDao.localizaEmailUsuario(emailCadastrado, rgCadastrado);
    
    System.out.println("Email cadastrado localizado:"+emailLocalizado);
    
    if (!(emailLocalizado.equals("") && emailLocalizado.equals(null))){

  		// Determia as letras que poderão estar presente nas chaves  
		String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";  
		int numeros=0;
		Random random = new Random();  
		String novaSenha = ""; 
		
		int index = -1;  
		
		for( int i = 0; i <=3; i++ ) {  
		   index = random.nextInt( letras.length() );  
		   novaSenha += letras.substring( index, index + 1 );  
		}  
		numeros=random.nextInt(100000000); 
        novaSenha = novaSenha+""+numeros;
        
    try{
		enviarEmail(emailLocalizado, novaSenha, rgCadastrado);
	} catch (AddressException e) {
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage("formLogin", new FacesMessage (FacesMessage.SEVERITY_ERROR,"Email e/ou RG não encontrado(s).",""));
		    
	} catch (MessagingException e) {
	 FacesContext context= FacesContext.getCurrentInstance();
     context.addMessage("formLogin", new FacesMessage (FacesMessage.SEVERITY_ERROR,"Email e/ou RG não encontrado(s).",""));	}	
    }
    
    else{
    FacesContext context= FacesContext.getCurrentInstance();
    context.addMessage("formLogin", new FacesMessage (FacesMessage.SEVERITY_ERROR,"Email não encontrado.",""));
    }
    
    setRgCadastrado("");
    setEmailCadastrado("");
    
    return "./acesso.jsf";
	}
	
	
	public void enviarEmail(String emailLocalizado, String novaSenha, String rg) throws AddressException, MessagingException {
		String destinatario = emailLocalizado;
		String conteudo="Caro(a) usuário sua senha foi redefinida para:"+novaSenha+" , se você não há requisitou recomendamos que você entre em contato imediatamente com o administrador do sistema.";
			
	    
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("Destinatario Localizado:"+destinatario);
			
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

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(email));
		InternetAddress[] toAddresses = { new InternetAddress(destinatario) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		
	
		msg.setSubject("Recuperação de dados de acesso");
		msg.setSentDate(new Date());
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(conteudo, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		msg.setContent(multipart);
		Transport.send(msg);

		context.addMessage("formRecuperaSenha:mensagem", new FacesMessage (FacesMessage.SEVERITY_INFO, "Solicitação enviada com sucesso.",""+""));

		aDao.atualizaEmailStatus(emailLocalizado, novaSenha, rg);  
		}
	
	   

	public String excluirAcesso(){
		retirarMascaraExclusao();
		FacesContext context = FacesContext.getCurrentInstance();
		boolean encontrado=aDao.excluirAcesso(excluirDados.getIdentificacao(), excluirDados.getNome(), excluirDados.getRg(), excluirDados.getEmail(), excluirDados.getTipoAcesso());
		if(encontrado && acessoAtual.getIdentificacao().equals(excluirDados.getIdentificacao())){
		context.addMessage("formExcluirCadastro:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados excluídos com sucesso.", ""+""));    
		return "./acesso.jsf";
		}
		else   
		if(encontrado && !acessoAtual.getIdentificacao().equals(excluirDados.getIdentificacao())){
        context.addMessage("formExcluirCadastro:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados excluídos com sucesso.", ""+""));
	    return "";
	    }
	    else
	    if(!encontrado){
	    context.addMessage("formExcluirCadastro:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados não excluídos, por favor verifique os campos informados.", ""+""));
	    return "";
	    }
	    return "";
		}
	


	public String getLoginLocalizado() {
		return loginLocalizado;
	}


	public void setLoginLocalizado(String loginLocalizado) {
		this.loginLocalizado = loginLocalizado;
	}


	public String getSenhaLocalizada() {
		return senhaLocalizada;
	}


	public void setSenhaLocalizada(String senhaLocalizada) {
		this.senhaLocalizada = senhaLocalizada;
	}
	
	
    public boolean isHabilitaLoginSenha() {
		return habilitaLoginSenha;
	}


	public void setHabilitaLoginSenha(boolean habilitaLoginSenha) {
		this.habilitaLoginSenha = habilitaLoginSenha;
	}


	public Acesso getExcluirDados() {
		return excluirDados;
	}


	public void setExcluirDados(Acesso excluirDados) {
		this.excluirDados = excluirDados;
	}



	public void retirarMascaraCadastro()
	{
		cadastraAcesso.setRg(cadastraAcesso.getRg().replaceAll("[ ./-]", ""));
	}


	public void retirarMascaraAltera()
	{
		alteraAcesso.setRg(alteraAcesso.getRg().replaceAll("[ ./-]", ""));
	}

	
	public void retirarMascaraExclusao()
	{
		excluirDados.setRg(excluirDados.getRg().replaceAll("[ ./-]", ""));
	}

	public void retirarMascaraConsulta(){
	recuperaDados.setRg(recuperaDados.getRg().replaceAll("[ ./-]", ""));

	}


	public Acesso getAlteraPrimeiroAcesso() {
		return alteraPrimeiroAcesso;
	}


	public void setAlteraPrimeiroAcesso(Acesso alteraPrimeiroAcesso) {
		this.alteraPrimeiroAcesso = alteraPrimeiroAcesso;
	}
	
	public String alteraPrimeiroAcesso(){
	FacesContext context = FacesContext.getCurrentInstance();

	System.out.println("Senha anterior:"+acessoAtual.getSenha());
	System.out.println("Senha anterior informada:"+alteraPrimeiroAcesso.getSenhaAnterior());
	System.out.println("Nova senha:"+alteraPrimeiroAcesso.getNovaSenha());
	
	alteraPrimeiroAcesso.setEmail(acessoAtual.getEmail());
	
	
	if(acessoAtual.getSenha().equals(alteraPrimeiroAcesso.getSenhaAnterior())){
	if(alteraPrimeiroAcesso.getNovaSenha().length()>=5){
		System.out.println("Porra to aqui:"+alteraPrimeiroAcesso.getEmail());
	    System.out.println("De novo:"+alteraPrimeiroAcesso.getNovaSenha());
	    
	    aDao.atualizaNovaSenha(alteraPrimeiroAcesso.getEmail(), alteraPrimeiroAcesso.getNovaSenha());
	    aDao.atualizaPrimeiroAcesso(alteraPrimeiroAcesso.getEmail(), alteraPrimeiroAcesso.getNovaSenha());
	    
	acessoAtual.setSenha(alteraPrimeiroAcesso.getNovaSenha());
	
	context.addMessage("formCadastroAcesso:mensagem",new FacesMessage(
			FacesMessage.SEVERITY_INFO,
			"Caro (a): "+alteraPrimeiroAcesso.getNome()+" sua senha foi cadastrada com sucesso.",
			"" + ""));	
	
	return logarPrimeiroAcesso();
    }
	else{
		context.addMessage("formPrimeiroAcesso:mensagem",new FacesMessage(
				FacesMessage.SEVERITY_ERROR,
				"A senha que você deseja cadastrar deve conter no mínimo 5 caracteres",
				"" + ""));	
	}
	
	}
	else{
		context.addMessage("formPrimeiroAcesso:mensagem",new FacesMessage(
				FacesMessage.SEVERITY_ERROR,
				"A senha anterior informada não corresponde a senha cadastrada",
				"" + ""));	
	}
	return "";
	}


	public Acesso getAlteraAcesso() {
		return alteraAcesso;
	}


	public void setAlteraAcesso(Acesso alteraAcesso) {
		this.alteraAcesso = alteraAcesso;
	}
	
	
	
	public String alterarEmailSenha(){
		setAlteraAcesso(new Acesso());
		alteraAcesso.setIdentificacao("");
		alteraAcesso.setNome("");
		alteraAcesso.setRg("");
		alteraAcesso.setEmail("");
	    alteraAcesso.setNovoEmail("");
		alteraAcesso.setSenha("");
		alteraAcesso.setNovaSenha("");

		return "./alterarEmailSenha.jsf";
	}

	
	public String redefinirSenha(){
	retirarMascaraAltera();
    Acesso localizado=aDao.consultarAcessoAluno(alteraAcesso.getEmail(), alteraAcesso.getSenha());
	FacesContext context = FacesContext.getCurrentInstance();
     
	
   System.out.println("Email:"+alteraAcesso.getEmail());
   System.out.println("Senha:"+alteraAcesso.getSenha());
   
   System.out.println("Email localizado:"+localizado.getEmail());
   System.out.println("Senha localizada:"+localizado.getSenha());
   
   
	
	if(alteraAcesso.getIdentificacao().equals(localizado.getIdentificacao()) && alteraAcesso.getNome().equals(localizado.getNome()) 
	&& alteraAcesso.getRg().equals(localizado.getRg()) 
	&& alteraAcesso.getEmail().equals(localizado.getEmail()) && alteraAcesso.getSenha().equals(localizado.getSenha())){
   	
	System.out.println("Estou aqui");
		
	if(alteraAcesso.getNovoEmail().length()>=9 && alteraAcesso.getNovaSenha().length()>=5){
   	aDao.atualizaEmailSenha(alteraAcesso.getIdentificacao(), alteraAcesso.getNovoEmail(), alteraAcesso.getNovaSenha());
   	context.addMessage("formAlteraAcesso:mensagem",new FacesMessage(
			FacesMessage.SEVERITY_INFO,
			"Caro (a): "+alteraAcesso.getNome()+" seus dados de acesso foram alterados com sucesso.",
			"" + ""));	
    return "./acesso.jsf";
	}
   	else{
	context.addMessage("formAlteraAcesso:mensagem",new FacesMessage(
			FacesMessage.SEVERITY_ERROR,
			"A senha que você deseja cadastrar deve conter no mínimo 5 caracteres",
			"" + ""));	
	}
	}
	else{
		context.addMessage("formAlteraAcesso:mensagem",new FacesMessage(
				FacesMessage.SEVERITY_ERROR,
				"Os dados informados não correspondem aos dados cadastrados",
				"" + ""));	
	}
	  
	alteraAcesso.setIdentificacao("");
	alteraAcesso.setNome("");
	alteraAcesso.setRg("");
	alteraAcesso.setEmail("");
    alteraAcesso.setNovoEmail("");
	alteraAcesso.setSenha("");
	alteraAcesso.setNovaSenha("");

	return "";
	}



	public String getRequisicaoDirecao() {
		return requisicaoDirecao;
	}



	public void setRequisicaoDirecao(String requisicaoDirecao) {
		this.requisicaoDirecao = requisicaoDirecao;
	}

	
	
	public String finalizarSessao(){
		FacesContext context = FacesContext.getCurrentInstance();  
        
		//Remove todos os beans da sessão  
		for (String bean : context.getExternalContext().getSessionMap().keySet()) {  
		    context.getExternalContext().getSessionMap().remove(bean);  
		}  
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);    
		session.invalidate();  
	

	return "./acesso.jsf";
	}


	public String getEmailCadastrado() {
		return emailCadastrado;
	}


	public void setEmailCadastrado(String emailCadastrado) {
		this.emailCadastrado = emailCadastrado;
	}


	public String getRgCadastrado() {
		return rgCadastrado;
	}


	public void setRgCadastrado(String rgCadastrado) {
		this.rgCadastrado = rgCadastrado;
	}
	
	
   public String redirecionaTelaPrincipal(){	   
 
   HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
   String nivel = String.valueOf(session.getAttribute("usuarioLogado"));
   if(nivel.equalsIgnoreCase("Secretaria")){
   return "./index.jsf";
   }
   else
   if(nivel.equalsIgnoreCase("Diretor")){
   return "./indexDirecao.jsf";
   }
   else
   if(nivel.equalsIgnoreCase("Coordenador")){
   return "./indexCoordenador.jsf";
   }
   else
   if(nivel.equalsIgnoreCase("Professor")){
   return "./indexProfessor.jsf";
   }
   else
   if(nivel.equals("Aluno")){
   return "./indexAluno.jsf";
   }
 
   return "";
	
   }
	 public void excluiArquivos() throws IOException, AddressException {  
	    	FacesContext facesContext = FacesContext.getCurrentInstance();  
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
             String caminho = scontext.getRealPath("/WEB-INF/LIB/Envio/"); 
			File folder = new File(caminho);  
			if (folder.isDirectory()) {  
			    File[] sun = folder.listFiles();  
			    for (File excluiItens : sun) {  
			        excluiItens.delete();  
			    }
			}
	    }
	
}



