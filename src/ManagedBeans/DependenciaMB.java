package ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DependenciaDao;
import Dao.DependenciaDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Aluno;
import Pojo.Curso;
import Pojo.Dependencia;
import Pojo.Disciplina;
import Pojo.Turma;

@ManagedBean(name = "dependenciaMB")
@SessionScoped
public class DependenciaMB implements Serializable {

	private static final long serialVersionUID = -4281390476508498320L;
	private List<Curso> listaCursos;
	private List<Turma> listaTurmas;
	private List<Aluno> listaAlunos;
	private List<Disciplina> listaDisciplinas;
	private CursoDao cursoDao;
	private TurmaDao turmaDao;
	private AlunoDao alunoDao;
	private DependenciaDao dependenciaDao;
	private DisciplinaDao disciplinaDao;
	private int selecaoCurso;
	private int selecaoTurma;
	private int selecaoModulo;
	private String selecaoNota;

	private int selecaoDisciplina;

	private boolean desabilitaTurma;
	private boolean desabilitaModulo;
	private boolean desabilitaDisciplina;
	private boolean desabilitaAulasDadas;
	private boolean desabilitaSemestre;
	private boolean desabilitaAno;
	private boolean desabilitaCurso;
	
	
	private Dependencia dependenciaAtual;

	private String nomeTurma;
	private String nomeDisciplina;

	private Turma turma = new Turma();
	private Curso curso = new Curso();

	private String novoCadastroNota;
	private String nomeAluno;

	private boolean desabilitaNovaSelecaoNota;

    private Dependencia dependenciaLocalizada;
    
    private List<Integer> anos;
    private Integer ano;
    private int selecaoAno;
    private int selecaoSemestre;
	
	public DependenciaMB() {
		setCurso(new Curso());
		setTurma(new Turma());
		setDependenciaAtual(new Dependencia());
		setListaCursos(new ArrayList<Curso>());
		setListaTurmas(new ArrayList<Turma>());
		setListaAlunos(new ArrayList<Aluno>());
		setListaDisciplinas(new ArrayList<Disciplina>());
		cursoDao = new CursoDaoImplementation();
		alunoDao = new AlunoDaoImplementation();
		turmaDao = new TurmaDaoImplementation();
        dependenciaDao=new DependenciaDaoImplementation();
		disciplinaDao = new DisciplinaDaoImplementation();
		localizaCursos();
		desabilitaTurma = true;
		desabilitaDisciplina = true;
		desabilitaAulasDadas = true;
		desabilitaNovaSelecaoNota=true;
		desabilitaSemestre=true;
		desabilitaAno=true;
		desabilitaCurso=true;
		setAnos(new ArrayList<Integer>());
	}

	// Listando os cursos de acordo com o valor selecionado
	public List<Curso> localizaCursos() {
		setListaCursos(cursoDao.listaCursos());
		return listaCursos;
	}

	// Listando as turmas do curso selecionado
	public void listaTurmaCurso() {
		setListaTurmas(turmaDao.listaTurmaCurso(selecaoCurso,selecaoSemestre,selecaoAno));

		if (selecaoCurso > 0) {
			desabilitaTurma = false;
			desabilitaDisciplina = false;
		} else {
			desabilitaTurma = true;
			desabilitaDisciplina = true;
			selecaoTurma=0;
		}
		listaDisciplina();
	}

	// Listando as turmas de acordo com o módulo selecionado
	public void listaTurmaModulo() {
		setListaTurmas(turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno));
		listaDisciplina();
	}

	// Listando as disciplinas de acordo com o curso e módulo selecionados
	public void listaDisciplina() {
		setListaDisciplinas(disciplinaDao.listaDisciplina(selecaoCurso,
				selecaoModulo));
		if (selecaoDisciplina > 0 && selecaoCurso > 0) {
			desabilitaAulasDadas = false;
		} else {
			desabilitaAulasDadas = true;
		}

	}
	
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}

	public int getSelecaoCurso() {
		return selecaoCurso;
	}

	public void setSelecaoCurso(int selecaoCurso) {
		this.selecaoCurso = selecaoCurso;
	}

	public int getSelecaoTurma() {
		return selecaoTurma;
	}

	public void setSelecaoTurma(int selecaoTurma) {
		this.selecaoTurma = selecaoTurma;
	}

	public int getSelecaoModulo() {
		return selecaoModulo;
	}

	public void setSelecaoModulo(int selecaoModulo) {
		this.selecaoModulo = selecaoModulo;
	}

	public boolean isDesabilitaTurma() {
		return desabilitaTurma;
	}

	public void setDesabilitaTurma(boolean desabilitaTurma) {
		this.desabilitaTurma = desabilitaTurma;
	}

	public boolean isDesabilitaModulo() {
		return desabilitaModulo;
	}

	public void setDesabilitaModulo(boolean desabilitaModulo) {
		this.desabilitaModulo = desabilitaModulo;
	}

	public List<Aluno> localizaDependencia() {
		System.out.println("Identificação atual:"+dependenciaAtual.getIdentificacaoAluno());
		setListaAlunos(dependenciaDao.consultarDependencia(selecaoDisciplina));
		atualizaStatusNota();
		return listaAlunos;
	}
	
	public List<Aluno> localizaDependenciaQuitada() {
		System.out.println("Identificação atual:"+dependenciaAtual.getIdentificacaoAluno());
		setListaAlunos(dependenciaDao.consultarDependenciaQuitada(selecaoDisciplina));
		atualizaStatusNota();
		return listaAlunos;
	}
	public void atualizaStatusNota() {
		if(dependenciaAtual.getIdentificacaoAluno().equalsIgnoreCase("") || dependenciaAtual.getIdentificacaoAluno()==null){
			desabilitaNovaSelecaoNota=true;
			limpaCampo();
		}
		else{
			desabilitaNovaSelecaoNota=false;
		}
	}


	public boolean isDesabilitaDisciplina() {
		return desabilitaDisciplina;
	}

	public void setDesabilitaDisciplina(boolean desabilitaDisciplina) {
		this.desabilitaDisciplina = desabilitaDisciplina;
	}

	public int getSelecaoDisciplina() {
		return selecaoDisciplina;
	}

	public void setSelecaoDisciplina(int selecaoDisciplina) {
		this.selecaoDisciplina = selecaoDisciplina;
	}

	public List<Disciplina> getListaDisciplinas() {
		return listaDisciplinas;
	}

	public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
	}

	// Passos necessários para o cadatro do boletim
	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public String getSelecaoNota() {
		return selecaoNota;
	}

	public void setSelecaoNota(String selecaoNota) {
		this.selecaoNota = selecaoNota;
	}

	public Dependencia getDependenciaAtual() {
		return dependenciaAtual;
	}

	public void setDependenciaAtual(Dependencia dependenciaAtual) {
		this.dependenciaAtual = dependenciaAtual;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public boolean isDesabilitaAulasDadas() {
		return desabilitaAulasDadas;
	}

	public void setDesabilitaAulasDadas(boolean desabilitaAulasDadas) {
		this.desabilitaAulasDadas = desabilitaAulasDadas;
	}



	public void limpaCampo() {
		novoCadastroNota= "";
		novoCadastroNota="";
		dependenciaAtual.setIdentificacaoAluno("");
	}

	// Consulta, Alteração e Exclusão do boletim
	public String quitaDependencia() {
		setListaAlunos(dependenciaDao.consultarDependencia(selecaoDisciplina));
		novoCadastroNota="";
		desabilitaNovaSelecaoNota=true;
		return "./atualizaDependencia.jsf";
    }

	// Consulta, Alteração e Exclusão do boletim
		public String atualizaDependenciaQuitada() {
			setListaAlunos(dependenciaDao.consultarDependenciaQuitada(selecaoDisciplina));
			novoCadastroNota="";
			desabilitaNovaSelecaoNota=true;
			return "./atualizaDependenciaQuitada.jsf";
	    }
		
	public Dependencia getdependenciaAtual() {
		return dependenciaAtual;
	}

	public void setdependenciaAtual(Dependencia dependenciaAtual) {
		this.dependenciaAtual = dependenciaAtual;
	}

	public String getNovoCadastroNota() {
		return novoCadastroNota;
	}

	public void setNovoCadastroNota(String novoCadastroNota) {
		this.novoCadastroNota = novoCadastroNota;
	}





	// Método responsável por inserir as notas de um único estudante
	// em uma dada disciplina
	public String inserir() {
		FacesContext context = FacesContext.getCurrentInstance();
		dependenciaAtual.setIDDaDisciplina(selecaoDisciplina);
		boolean inserido = dependenciaDao.inserirQuitacaoDependencia(dependenciaAtual.getIdentificacaoAluno(), selecaoCurso, dependenciaAtual.getIDDaDisciplina(),
		dependenciaAtual.getAtividade(), dependenciaAtual.getMencao());
		if(inserido){
			context.addMessage("formAtualizaDependencia:mensagem", new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Dados inseridos com sucesso",
					"" + ""));
			dependenciaAtual.setAtividade("");
			dependenciaAtual.setMencao("");
		}
		 else{
				context.addMessage("formAtualizaDependencia:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Dados não inseridos",
						"" + ""));
		   }
		return "";
	}

	public String consultarDependenciaCadastrada() {
		System.out.println("ID Aluno:"+dependenciaAtual.getIdentificacaoAluno()+" Disciplina:"+dependenciaAtual.getIDDaDisciplina());
		dependenciaAtual.setIDDaDisciplina(selecaoDisciplina);
		dependenciaLocalizada = dependenciaDao.localizaDependenciaCadastrada(dependenciaAtual.getIdentificacaoAluno(), dependenciaAtual.getIDDaDisciplina());
		return "";
	}
	
	
	public String consultarNovaMencao() {
		System.out.println("ID Aluno:"+dependenciaAtual.getIdentificacaoAluno()+" Disciplina:"+dependenciaAtual.getIDDaDisciplina());
		dependenciaAtual.setIDDaDisciplina(selecaoDisciplina);
		dependenciaLocalizada = dependenciaDao.localizaNovaNotaInformada(dependenciaAtual.getIdentificacaoAluno(), dependenciaAtual.getIDDaDisciplina());
		return "";
	}

	
	public String alterar() {
		return "";
	}

	public String remover() {
		FacesContext context = FacesContext.getCurrentInstance();
		dependenciaAtual.setIDDaDisciplina(selecaoDisciplina);
	boolean removido=dependenciaDao.removerQuitacaoDependencia(dependenciaAtual.getIdentificacaoAluno(), selecaoCurso, dependenciaAtual.getIDDaDisciplina());
	   if(removido){	
		context.addMessage("formAtualizaDependenciaQuitada:mensagem", new FacesMessage(
		FacesMessage.SEVERITY_INFO, "Dados removidos com sucesso",
	    "" + ""));  
		dependenciaAtual.setAtividade("");
		dependenciaAtual.setMencao("");
	    }
	   else{
			context.addMessage("formAtualizaDependenciaQuitada:mensagem", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Dados não removidos",
					"" + ""));
	   }
	return "";
	}

	
	public String getNomeAluno() {
		return nomeAluno;
	}

	public String localizaNomeAluno(String identificacaoAluno) {
		System.out.println("Selecionado");
		setNomeAluno(alunoDao.localizaNomeAluno(identificacaoAluno));
		return "";
	}
	
	
	public List<Dependencia> listaDependencia(){
	
	return null;
	
	}


	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	
	

	public boolean getDesabilitaNovaSelecaoNota() {
		return desabilitaNovaSelecaoNota;
	}

	public void setDesabilitaNovaSelecaoNota(boolean desabilitaNovaSelecaoNota) {
		this.desabilitaNovaSelecaoNota = desabilitaNovaSelecaoNota;
	}



	public Dependencia getDependenciaLocalizada() {
		return dependenciaLocalizada;
	}

	public void setDependenciaLocalizada(Dependencia dependenciaLocalizada) {
		this.dependenciaLocalizada = dependenciaLocalizada;
	}
	
	
	public List<Integer> listaAnos(){
		  Calendar cal = GregorianCalendar.getInstance(); 

		  System.out.println("Semestre selecionado:"+selecaoSemestre);
		  anos.clear();
		  if(selecaoSemestre>0){
			  desabilitaAno=false;
			  }
		     else{
			  desabilitaAno=true;
			  desabilitaDisciplina=true;
			  desabilitaTurma=true;
			  desabilitaCurso=true;
			  desabilitaModulo=true;
			  selecaoCurso=0;
			  selecaoDisciplina=0;
			  selecaoTurma=0;
			  }
		  
		 		  
		  if(selecaoAno==0 || selecaoSemestre==0){
		  desabilitaCurso=true;
	      desabilitaModulo=true;
	      desabilitaTurma=true;
	      desabilitaDisciplina=true;
	      desabilitaModulo=true;
	      selecaoCurso=0;
	      selecaoDisciplina=0;
	      selecaoTurma=0;
		  }
		  else
		  if(selecaoAno>0 && selecaoSemestre>0){
		  desabilitaCurso=false;
		  desabilitaModulo=false;
		  }
		  
	
		   ano = cal.get(Calendar.YEAR); 

		    for (int i=0;i<=10;i++){
		    anos.add(ano);
		    ano=ano-1;
		    }
		  
		  listaTurmaCurso();
		  listaTurmaModulo();
	    
	    return anos;
		}

	public boolean isDesabilitaSemestre() {
		return desabilitaSemestre;
	}

	public void setDesabilitaSemestre(boolean desabilitaSemestre) {
		this.desabilitaSemestre = desabilitaSemestre;
	}

	public boolean isDesabilitaAno() {
		return desabilitaAno;
	}

	public void setDesabilitaAno(boolean desabilitaAno) {
		this.desabilitaAno = desabilitaAno;
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

	public boolean isDesabilitaCurso() {
		return desabilitaCurso;
	}

	public void setDesabilitaCurso(boolean desabilitaCurso) {
		this.desabilitaCurso = desabilitaCurso;
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
	

}