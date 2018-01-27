package ManagedBeans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.ProfessorDao;
import Dao.ProfessorDaoImplementation;
import Dao.ProfessorDisciplinaTurmaDao;
import Dao.ProfessorDisciplinaTurmaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.Professor;
import Pojo.ProfessorDisciplinaTurma;
import Pojo.Turma;


@ManagedBean(name="professorDisciplinaTurmaMB")
@ViewScoped
public class ProfessorDisciplinaTurmaMB implements Serializable{
	private ProfessorDisciplinaTurma professorTurmaAtual;
	private List<ProfessorDisciplinaTurma> listaPt;
	private List<Professor> listaProfessores;
	private ProfessorDisciplinaTurmaDao professorTurmaDao;
	private List<Curso> listaCursos;
	private List<Turma> listaTurmas;
	private List<Disciplina> listaDisciplinas;
	private CursoDao cursoDao;
	private ProfessorDao professorDao;
    private TurmaDao turmaDao;
    private DisciplinaDao disciplinaDao;
	private int selecaoCurso;
	private int selecaoSemestre;
	private int selecaoAno;
	private int selecaoModulo;
    private List<Integer> anos;
    private Integer ano;
    private boolean desabilitaCurso;
    private boolean desabilitaModulo;
    private boolean desabilitaAno;
    private boolean desabilitaTurma;
    private boolean desabilitaProfessor;    
    private boolean desabilitaDisciplina;

	
	public ProfessorDisciplinaTurmaMB(){
		setProfessorTurmaAtual(new ProfessorDisciplinaTurma());
		setListaPt(new ArrayList<ProfessorDisciplinaTurma>());
		professorTurmaDao=new ProfessorDisciplinaTurmaDaoImplementation();
	    setListaCursos(new ArrayList<Curso>());
        cursoDao=new CursoDaoImplementation();
	    turmaDao=new TurmaDaoImplementation();
	    disciplinaDao=new DisciplinaDaoImplementation();
	    professorDao=new ProfessorDaoImplementation();
	    setListaProfessores(new ArrayList<Professor>());
	    setListaDisciplinas(new ArrayList<Disciplina>());
	    anos=new ArrayList<Integer>();
	    desabilitaAno=true;
		desabilitaCurso=true;
		desabilitaTurma=true;
		desabilitaProfessor=true;
		desabilitaDisciplina=true;
		desabilitaModulo=true;
		
	}


	public String inserir(){
		FacesContext context = FacesContext.getCurrentInstance();

		boolean inserido = professorTurmaDao.inserir(professorTurmaAtual);
		if(inserido){
			context.addMessage("formProfessoresDaTurma:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor inserido na turma com sucesso", ""+""));
			limpaCampo();
		}
		else{
			context.addMessage("formProfessoresDaTurma:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Professor não inserido na turma", ""+""));
		}
		return "";
	}

	public String remover(){
		FacesContext context=FacesContext.getCurrentInstance();
		boolean removido = professorTurmaDao.excluir(professorTurmaAtual.getIDDoProfessor(), professorTurmaAtual.getIDDaTurma(), professorTurmaAtual.getIDDaDisciplina());
		if(removido){
			context.addMessage("formProfessoresDaTurma:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor excluído da turma com sucesso", ""+""));
		    limpaCampo();
		}
		else{
			context.addMessage("formProfessoresDaTurma:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor não excluído da turma", ""+""));
		}
		return "";
	}


	public String pesquisar(){
		FacesContext context=FacesContext.getCurrentInstance();
		setListaPt(professorTurmaDao.pesquisar(professorTurmaAtual.getIDDoProfessor()));
		return "";
	}


	public ProfessorDisciplinaTurma getProfessorTurmaAtual() {
		return professorTurmaAtual;
	}


	public void setProfessorTurmaAtual(ProfessorDisciplinaTurma professorTurmaAtual) {
		this.professorTurmaAtual = professorTurmaAtual;
	}

	
	public List<ProfessorDisciplinaTurma> getListaPt() {
		return listaPt;
	}


	public void setListaPt(List<ProfessorDisciplinaTurma> listaPt) {
		this.listaPt = listaPt;
	}



	public void limpaCampo(){
		professorTurmaAtual.setIDDoProfessor(0);
		professorTurmaAtual.setIDDaTurma(0);
		selecaoSemestre=1;
	    selecaoCurso=0;
	    selecaoSemestre=0;
	    
	    

	    
	}
	
	

	  
	  public List<Integer> listaAnos(){
		  Calendar cal = GregorianCalendar.getInstance(); 
		  
		  if(selecaoAno==0)
		  {
		  selecaoCurso=0;
		  }

		  anos.clear();
		  if(selecaoSemestre>0){
		  desabilitaAno=false;
			  }
		  else{
		  desabilitaAno=true;
		  selecaoAno=cal.get(Calendar.YEAR); 
	      selecaoCurso=0;
	      professorTurmaAtual.setIDDaTurma(0);
	      professorTurmaAtual.setIDDoProfessor(0);
		  }
		  
		

		  
	    ano = cal.get(Calendar.YEAR); 

	        
	    if(selecaoSemestre==1){
	    ano=ano+1; 
	    }

	    for (int i=0;i<=2;i++){
	    anos.add(ano);
	    ano=ano-1;
	    }
	    
	    return anos;
		}
	  
	  

	  public List<Curso> listaCursos() {
	  if(selecaoAno>0 && selecaoSemestre>0){
	   desabilitaCurso=false;
	   }
	    else{
		 desabilitaCurso=true;
		 }
			  
	  setListaCursos(cursoDao.listaCursos());
	  habilitaModulo();
	  
	  return listaCursos;
	   }
	  
	  public void habilitaModulo(){
		 if(selecaoCurso>0){
	     desabilitaModulo=false;
	     }
		 else{
		 desabilitaModulo=true;
		 }
	  }
	  
	  public List<Turma> listaTurmas(){
	  System.out.println("Habilitando turma");
	  if(selecaoSemestre >0 && selecaoAno>0 && selecaoCurso>0 && selecaoModulo>0){
	  desabilitaTurma=false;
	  desabilitaProfessor=false;
	  }
	  else{
	  desabilitaTurma=true;
	  desabilitaProfessor=true;
	  
	  }
	  

	  setListaTurmas(turmaDao.listaTurmasAnoCursoModulo(selecaoSemestre, selecaoAno, selecaoCurso, selecaoModulo));
	  listaDisciplinas();
	  
	  return listaTurmas;
	  }
	  
	  

	  
	  public List<Disciplina> listaDisciplinas() {
		  if(selecaoSemestre >0 && selecaoAno>0 && selecaoCurso>0 && professorTurmaAtual.getIDDaTurma()>0 && selecaoModulo>0){
		  desabilitaDisciplina=false;
		  }
		  else{
		   desabilitaDisciplina=true;
		   
		  }
			  
			 setListaDisciplinas(disciplinaDao.listaDisciplina(selecaoCurso,
					selecaoModulo));
			
			return listaDisciplinas;
	  }
	  
			
			
			public List<Professor> listaProfessores(){
			      setListaProfessores(professorDao.listaProfessores());
			      return listaProfessores;
				  }
				  



	public List<Curso> getListaCursos() {
		return listaCursos;
	}


	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}


	public int getSelecaoCurso() {
		return selecaoCurso;
	}


	public void setSelecaoCurso(int selecaoCurso) {
		this.selecaoCurso = selecaoCurso;
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


	public boolean isDesabilitaAno() {
		return desabilitaAno;
	}


	public void setDesabilitaAno(boolean desabilitaAno) {
		this.desabilitaAno = desabilitaAno;
	}


	public boolean isDesabilitaTurma() {
		return desabilitaTurma;
	}


	public void setDesabilitaTurma(boolean desabilitaTurma) {
		this.desabilitaTurma = desabilitaTurma;
	}


	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}


	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}


	public boolean isDesabilitaCurso() {
		return desabilitaCurso;
	}


	public void setDesabilitaCurso(boolean desabilitaCurso) {
		this.desabilitaCurso = desabilitaCurso;
	}


	public List<Professor> getListaProfessores() {
		return listaProfessores;
	}


	public void setListaProfessores(List<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}


	public boolean isDesabilitaProfessor() {
		return desabilitaProfessor;
	}


	public void setDesabilitaProfessor(boolean desabilitaProfessor) {
		this.desabilitaProfessor = desabilitaProfessor;
	}


	public boolean isDesabilitaDisciplina() {
		return desabilitaDisciplina;
	}


	public void setDesabilitaDisciplina(boolean desabilitaDisciplina) {
		this.desabilitaDisciplina = desabilitaDisciplina;
	}


	public int getSelecaoModulo() {
		return selecaoModulo;
	}


	public void setSelecaoModulo(int selecaoModulo) {
		this.selecaoModulo = selecaoModulo;
	}


	public List<Disciplina> getListaDisciplinas() {
		return listaDisciplinas;
	}


	public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
	}


	public boolean isDesabilitaModulo() {
		return desabilitaModulo;
	}


	public void setDesabilitaModulo(boolean desabilitaModulo) {
		this.desabilitaModulo = desabilitaModulo;
	}



}
