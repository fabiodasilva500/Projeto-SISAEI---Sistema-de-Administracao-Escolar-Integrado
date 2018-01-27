package ManagedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
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



@ManagedBean (name="diarioProfessorMB")
@ViewScoped
public class DiarioProfessorMB  implements Serializable{
	
	private Diario diario;
	private int selecaoTurno;
	private int selecaoTurma;
	private int selecaoProf;
	private int selecaoDisciplina;
	private int selecaoCurso;
	private int selecaoSemestre;
	private int selecaoAno;
	private int selecaoModulo;
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
	private boolean desabilitaData;
	private boolean desabilitaTurno;
	private boolean pre;
	private boolean value1;
	private List<Aluno> alunosPresenca;

	private AlunoDao alunoDao;

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
    

    
    
	//Construtor da Classe
	public DiarioProfessorMB(){
		setDiario(new Diario());
		setAcesso(new AcessoMB());
		pDao = new ProfessorDaoImplementation();
		tDao = new TurmaDaoImplementation();
		dDao = new DiarioDaoImplementation();
		pdtDao=new ProfessorDisciplinaTurmaDaoImplementation();
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
		desabilitaData=true;
		desabilitaTurno=true;
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
		  desabilitaData=true;
		  desabilitaTurno=true;
		  selecaoAno=cal.get(Calendar.YEAR); 
	      selecaoCurso=0;
		  }

	    ano = cal.get(Calendar.YEAR); 
	    anos.add(ano);
	    
	    
	    return anos;
		}


	
	
	//carrega combo com o nome do professor
	public List<Professor> localizaProfessor(){
		setListaProfessor(pDao.listaProfessores());
		for (Professor professor : listaProfessor) {
			setProf(professor.getNome());
		}
		return listaProfessor;
	}
	
	

	
	public List<Curso> listaCursos(Acesso acesso){
	System.out.println("Inicializando:"+acesso.getIdentificacao());
	selecaoProfessor=Integer.parseInt(acesso.getIdentificacao());
	System.out.println("Ano:"+ano);
	setListaCursos(pdtDao.listaCursos(selecaoProfessor, ano, selecaoSemestre));
	habilidaModulo();
	return listaCursos;
	}
	
	
	public String validaAcesso(Acesso a){
	    a.setIdentificacao(a.getIdentificacao());	
	    System.out.println("Chegou:"+a.getIdentificacao());
	    AcessoDao ad=new AcessoDaoImplementation();
	    String valida="validado";
	    boolean validado = ad.validaAcessoSecretaria(a.getIdentificacao());
	   
	    if(validado){
	    System.out.println("Autenticado");
	    return "";
	    }
	    else{
	    System.out.println("Não autenticado aqui");
        ade=Integer.parseInt(valida); 
      //Redireciona o usuário para tela de login efetuando o logout.  
        String loginPage = "./acesso.jsf";  
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();  
        HttpServletRequest request = (HttpServletRequest) context.getRequest();  
        try {  
            context.redirect(request.getContextPath() + loginPage);  
        } catch (IOException e) {  
        	e.printStackTrace();
        }  
        HttpSession session = (HttpSession) context.getSession(false);  
        session.invalidate();  
  
	    return "./acesso.jsf";
	    }
	
	}
	

	public void listaTurmaModulo(){
	setListaTurmas(pdtDao.localizaTurmaModulo(selecaoCurso, selecaoProfessor, ano, selecaoModulo, selecaoSemestre));
	habilitaTurma();
    listaDisciplinas();
	}

	

	public void listaTurmaCurso(){
	setListaTurmas(pdtDao.localizaTurmaCurso(selecaoCurso, selecaoProfessor, ano, selecaoSemestre));
	habilitaTurma();
    listaDisciplinas();
	}
	
	public void listaDisciplinas(){
	setListaDisciplina(pdtDao.listaDisciplinas(selecaoCurso, ano, selecaoSemestre, selecaoModulo, selecaoProfessor));
    habilitaDisciplina();
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

	
	
	//inseri diário
	public String inserir() throws SQLException {
		FacesContext context = FacesContext.getCurrentInstance();

		diario.setIdDisciplina(selecaoDisciplina);
		diario.setIdProfessor(selecaoProfessor);
		diario.setIdTurma(selecaoTurma);
		diario.setIdCurso(selecaoCurso);
		System.out.println("ID DIARIO " +  diario.getIdDiario() +" Disciplina " + diario.getIdDisciplina()
				+ " Professor " + diario.getIdProfessor() +
				" Turma " + diario.getIdTurma() + 
				" Data aula " + diario.getDataAula() + 
				" Turno " + diario.getTurno());

		boolean inserido= dDao.inserirDiario(diario, diario.getIdDiario());
		if(inserido){
			context.addMessage("formDiarioProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Diario inserido com sucesso", ""+""));
		}
		else{
			context.addMessage("formDiarioProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Diário não inserido com sucesso", ""+""));
		}
	return "";
	}



	public String pesquisarPorNome() throws SQLException {
		
		return "";
	}

	public String atualizar() throws SQLException{
		
		return "";
	}
	
	

	public String remover() throws SQLException {
		FacesContext context = FacesContext.getCurrentInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
       
		if(selecaoTurma>0 && selecaoDisciplina>0 && selecaoProfessor>0 && diario.getDataAula()!=null){
		boolean excluido= dDao.excluirDiario(selecaoTurma, selecaoDisciplina, selecaoProfessor, sdf.format(diario.getDataAula()));
		if(excluido){
			context.addMessage("formDiarioProfessor:mensagem",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Chamada foi excluída com sucesso.",
					"" + ""));	
		}
		else{
			context.addMessage("formDiarioProfessor:mensagem",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Chamada não excluída, nenhum registro foi encontrado com os dados informados.",
					"" + ""));	
		}
		}
		else{
			context.addMessage("formDiarioProfessor:mensagem",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Chamada não excluída, selecione todos os campos.",
					"" + ""));	
		}
       return "";
	}
	
public List<Aluno> visualizarAlunos(){
		
		setAlunosPresenca(dDao.listaAlunos(selecaoTurma));
		
		for (Aluno alu : alunosPresenca) {
			
		}
		
		setListaAlunos(dDao.listaAlunos(selecaoTurma));
		if(listaAlunos.size()>0){
		habilitaAusenciaParcial=true;

		}
		
		return listaAlunos;
	}
	
	
	//Inseri presença do aluno ou exclui presenca do aluno
	public String inserirChamada(String idAluno, String nome, int curso){
		
		Aluno aluno = new Aluno();
		aluno.setIdentificacao(idAluno);
		aluno.setNome(nome);
		aluno.setCurso(curso);
		aluno.setPresencaAtual(pre);
		
		System.out.println(aluno.getIdentificacao());
		System.out.println(aluno.getNome());
		
		System.out.println(aluno.isPresencaAtual());
		presencaAlunos.add(aluno);
		
		System.out.println("Presenca aluno: " + aluno.isPresencaAtual());
		
		if(aluno.isPresencaAtual()==true){
			dDao.inserirPresenca(aluno, diario.getIdDiario());
			faltas=0;
			System.out.println("inserindo presenca aluno");
			desabilitaFaltas=false;
			
			
			
		}else{
			dDao.excluirPresenca(aluno, diario.getIdDiario());
			diario.setQuantidadeAusencia(faltas);
			System.out.println("excluindo presenca aluno");
			
			desabilitaFaltas=true;
			System.out.println(aluno.isPresencaAtual());
			consultaCargaHoraria();
			excluirAusenciaParcial(idAluno);
		}
		return "";
	}
	
	public void inserirAusenciaParcial(String identificacao){
	if(faltas>0){
    Aluno aluno=new Aluno();
    aluno.setIdentificacao(identificacao);
	diario.setQuantidadeAusencia(faltas);
	dDao.inserirAtraso(aluno, diario);
    consultaCargaHoraria(); 
    
	}
	}
	
	public void excluirAusenciaParcial(String identificacao){
		if(pre==false){
	    Aluno aluno=new Aluno();
	    aluno.setIdentificacao(identificacao);
		diario.setQuantidadeAusencia(faltas);
		dDao.excluirAtraso(aluno, diario);
	    consultaCargaHoraria(); 
	    
		}
		}
	
	public List<Double> consultaCargaHoraria (){
	System.out.println("Consultando carga horária");
	int cargaHoraria = disciplinaDao.cargaHoraria(selecaoDisciplina);	
	Aluno aluno=new Aluno();
	opcoes.clear();
	System.out.println(aluno.isPresencaAtual());
	if(pre==false){
	if(cargaHoraria == 50){
    opcoes.add(2.5);
    faltas=2.5;
    System.out.println("Adicionando novamente");
	}
	else{
	opcoes.add(5.0);
	faltas=5.0;
    System.out.println("Adicionando novamente");

	}
	}
	
	if(cargaHoraria==50){
	opcoes.add(0.0);
	opcoes.add(1.25);
	}
	else{
	System.out.println("Adicionando novamente");
	opcoes.add(0.0);
	opcoes.add(1.0);
	opcoes.add(2.0);
	opcoes.add(2.5);
	opcoes.add(3.0);
	opcoes.add(4.0);
	}
	return opcoes;
	
	}
	
	
	//Cria uma nova instãncia do diário e redireciona para a home
public String salvar(){
	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("diarioMB", new DiarioMB());
	return "./indexProfessor.jsf";
	
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



public void gerarListaDePresenca() throws JRException{
FacesContext context = FacesContext.getCurrentInstance();

String dataAula=String.valueOf(diario.getDataAula());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");	


if(selecaoTurma>0 && selecaoDisciplina>0 && selecaoProfessor>0 && diario.getDataAula()!=null){
dDao.gerarPDF(selecaoTurma, selecaoDisciplina, selecaoProfessor, diario.getDataAula());


}
else{
context.addMessage("formDiarioProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na geração da lista de presença, por favor selecione todos os campos.", ""+""));
}
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



}
