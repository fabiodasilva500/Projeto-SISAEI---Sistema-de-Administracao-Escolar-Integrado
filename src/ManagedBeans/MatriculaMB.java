package ManagedBeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import Dao.AlunoTurmaDao;
import Dao.AlunoTurmaDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.EstatisticaDao;
import Dao.EstatisticaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Curso;
import Pojo.Matricula;
import Pojo.Turma;

@ManagedBean(name="matriculaMB")
@ViewScoped

public class MatriculaMB implements Serializable{
	private Matricula matriculaAtual;
	private AlunoTurmaDao alunoTurma;
	private List<Matricula> listaAt;
	private int selecaoAno;
	private int selecaoSemestre;
	private boolean desabilitaAno;
	private boolean desabilitaSemestre;
	private boolean desabilitaCurso;
	private boolean desabilitaTurma;
    private List<Integer> anos;
    private List<Curso> listaCursos;
    private List<Turma> listaTurmas;
    private CursoDao cursoDao;
    private TurmaDao turmaDao;
    private Integer ano;
    private int selecaoCurso;
    private boolean habilitaDependencias;
    
 

	public MatriculaMB(){
		setMatriculaAtual(new Matricula());
		alunoTurma=new AlunoTurmaDaoImplementation();
		setListaAt(new ArrayList<Matricula>());
		anos=new ArrayList<Integer>();
		desabilitaAno=true;
		desabilitaCurso=true;
		desabilitaTurma=true;
		cursoDao=new CursoDaoImplementation();
		turmaDao=new TurmaDaoImplementation();
	    setListaCursos(new ArrayList<Curso>());
	    setListaTurmas(new ArrayList<Turma>());
	}

	public String inserir() throws SQLException{
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(habilitaDependencias==false){
		matriculaAtual.setDependencias(0);
		matriculaAtual.setTotalDependencias(0);
		matriculaAtual.setInstituicao("");
		matriculaAtual.setRazao("");
		}
		
	    int trancado = alunoTurma.localizaTrancamento(matriculaAtual.getIdentificacao());
	    if(trancado==1){
		context.addMessage("formAlunoTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "O estudante selecionado encontra-se com o curso trancado.", ""+""));
	    }
	    else{
	    matriculaAtual.setIDCurso(selecaoCurso);
	   boolean inserido = alunoTurma.inserir(matriculaAtual);
	   
		if(inserido){
			limpaCampo();
			context.addMessage("formAlunoTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno cadastrado na turma com sucesso", ""+""));
		
		}
		else{
			context.addMessage("formAlunoTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do aluno na turma", ""+""));
		}
	    }

		return "";
	
	}
	
	public String remover() throws SQLException{
		FacesContext context = FacesContext.getCurrentInstance();

		boolean removido = alunoTurma.remover(matriculaAtual.getIdentificacao(), matriculaAtual.getIDTurma(), selecaoCurso);
		if(removido){
			limpaCampo();
			context.addMessage("formAlunoTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno excluído da turma com sucesso", ""+""));
		}
		else{
			context.addMessage("formAlunoTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do aluno da turma", ""+""));
		}
		return "";
	}



	public String pesquisar() throws SQLException, JRException{
		AlunoTurmaDao aDao = new AlunoTurmaDaoImplementation();
		listaAt=(aDao.pesquisar(matriculaAtual.getIdentificacao()));
		return " ";
	}

	public String gerarLista(int IDTurma) throws JRException{
	
		AlunoTurmaDao aDao = new AlunoTurmaDaoImplementation();
		aDao.preparaPDF(IDTurma);
		return " ";
	}


	public void limpaCampo(){
		matriculaAtual.setIdentificacao("");
		matriculaAtual.setIDTurma(0);
	}

	public Matricula getMatriculaAtual() {
		return matriculaAtual;
	}

	public void setMatriculaAtual(Matricula matriculaAtual) {
		this.matriculaAtual = matriculaAtual;
	}

	public AlunoTurmaDao getTurma() {
		return alunoTurma;
	}

	public void setTurma(AlunoTurmaDao alunoTurma) {
		this.alunoTurma = alunoTurma;
	}

	public AlunoTurmaDao getAlunoTurma() {
		return alunoTurma;
	}

	public void setAlunoTurma(AlunoTurmaDao alunoTurma) {
		this.alunoTurma = alunoTurma;
	}

	public List<Matricula> getListaAt() {
		return listaAt;
	}

	public void setListaAt(List<Matricula> listaAt) {
		this.listaAt = listaAt;
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
      matriculaAtual.setIDTurma(0);
      matriculaAtual.setIdentificacao("");
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
			
			return listaCursos;
			
		}
	  
	  
	  public List<Turma> listaTurmas(){
	  if(selecaoSemestre >0 && selecaoAno>0 && selecaoCurso>0){
	  desabilitaTurma=false;
	  }
	  else{
	  desabilitaTurma=true;
	  
	  }
	  setListaTurmas(turmaDao.listaTurmasAnoCurso(selecaoSemestre, selecaoAno, selecaoCurso));
	  return listaTurmas;
	 
	  }

	
	public String consultarNome(){
	System.out.println("ENTER");
	return "";
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

	public boolean isDesabilitaAno() {
		return desabilitaAno;
	}

	public void setDesabilitaAno(boolean desabilitaAno) {
		this.desabilitaAno = desabilitaAno;
	}

	public boolean isDesabilitaSemestre() {
		return desabilitaSemestre;
	}

	public void setDesabilitaSemestre(boolean desabilitaSemestre) {
		this.desabilitaSemestre = desabilitaSemestre;
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

	public boolean isDesabilitaCurso() {
		return desabilitaCurso;
	}

	public void setDesabilitaCurso(boolean desabilitaCurso) {
		this.desabilitaCurso = desabilitaCurso;
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

	

	public boolean isHabilitaDependencias() {
		return habilitaDependencias;
	}

	public void setHabilitaDependencias(boolean habilitaDependencias) {
		this.habilitaDependencias = habilitaDependencias;
	}
	
	
	public boolean habilitaCampo(){
	System.out.println(habilitaDependencias);
	return habilitaDependencias;
	}
}
