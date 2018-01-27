package ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.chart.PieChartModel;

import Dao.AlunoTurmaDao;
import Dao.AlunoTurmaDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DiarioDao;
import Dao.DiarioDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Curso;
import Pojo.Diario;
import Pojo.Disciplina;
import Pojo.Turma;

@ManagedBean(name="diarioMB")
@SessionScoped
public class DiarioMB implements Serializable{
	private Diario diarioAtual;
	private DiarioDao diarioDao;
	private DisciplinaDao disciplinaDao;
	private AlunoTurmaDao alunoTurma;
	private List<Diario> listaAt;
	private int selecaoAno;
	private int selecaoSemestre;
	private int selecaoModulo;
	private boolean desabilitaAno;
	private boolean desabilitaSemestre;
	private boolean desabilitaCurso;
	private boolean desabilitaTurma;
	private boolean desabilitaDisciplina;
	private boolean desabilitaModulo;
	private boolean desabilitaDataInicial;
	private boolean desabilitaDataFinal;
    private List<Integer> anos;
    private List<Curso> listaCursos;
    private List<Turma> listaTurmas;
    private List<Disciplina> listaDisciplinas;
    private CursoDao cursoDao;
    private TurmaDao turmaDao;
    private Integer ano;
    private int selecaoCurso;
    private boolean ausenciaDisciplina;
    private Date dataInicial;
    private Date dataFinal;
    
    private PieChartModel graficoDiario;  
    private PieChartModel graficoDiarioDisciplina;
    private boolean desabilitaGraficoDisciplina;

 

	public DiarioMB(){
		setdiarioAtual(new Diario());
		alunoTurma=new AlunoTurmaDaoImplementation();
		disciplinaDao = new DisciplinaDaoImplementation();
		diarioDao=new DiarioDaoImplementation();
		setListaAt(new ArrayList<Diario>());
		anos=new ArrayList<Integer>();
		desabilitaAno=true;
		desabilitaCurso=true;
		desabilitaTurma=true;
		desabilitaDataInicial = true;
		desabilitaDataFinal = true;
		desabilitaGraficoDisciplina = true;
		cursoDao=new CursoDaoImplementation();
		turmaDao=new TurmaDaoImplementation();
	    setListaCursos(new ArrayList<Curso>());
	    setListaTurmas(new ArrayList<Turma>());
	    setListaDisciplinas(new ArrayList<Disciplina>());
		setGraficoDiario(new PieChartModel());
		setGraficoDiarioDisciplina(new PieChartModel());
	}




	public String gerarLista(int IDTurma) throws JRException{
	
		AlunoTurmaDao aDao = new AlunoTurmaDaoImplementation();
		aDao.preparaPDF(IDTurma);
		return " ";
	}


	public void limpaCampo(){
	setDataFinal(null);
	setDataInicial(null);
    selecaoAno=0;
    selecaoCurso=0;
    selecaoModulo=0;
    selecaoSemestre=0;
    desabilitaAno=true;
    desabilitaCurso=true;
    desabilitaDataFinal=true;
    desabilitaDataInicial=true;
    desabilitaDisciplina=true;
    desabilitaGraficoDisciplina=true;
    desabilitaModulo=true;
    desabilitaSemestre=true;
    desabilitaTurma=true;
	}

	public Diario getdiarioAtual() {
		return diarioAtual;
	}

	public void setdiarioAtual(Diario diarioAtual) {
		this.diarioAtual = diarioAtual;
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

	public List<Diario> getListaAt() {
		return listaAt;
	}

	public void setListaAt(List<Diario> listaAt) {
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
		  desabilitaModulo = true;
		  desabilitaDataInicial = true;
		  desabilitaDataFinal   = true;
		  }
		  
			setListaCursos(cursoDao.listaCursos());
			habilitaModulo();
			return listaCursos;
			
		}
	  
	  
	  public void habilitaModulo() {
		  if(selecaoAno>0 && selecaoSemestre>0 && selecaoCurso > 0){
		  desabilitaModulo=false;
		  }
		  else{
		  desabilitaModulo=true;
		  desabilitaTurma = true;
		  desabilitaDataInicial =true;
		  }		
		  listaTurmas();
		  habilitaDataInicial();				  
		}
	  
	  public List<Turma> listaTurmaModulo(){
	 if(selecaoAno>0 && selecaoSemestre>0 && selecaoCurso > 0 && selecaoModulo > 0){
	 desabilitaTurma=false;
	 }
	 else{
	  desabilitaTurma=true;
	 }
	   
	  setListaTurmas(turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno));
	  return listaTurmas;
	  }
	  
	  
	  public void habilitaDataInicial(){
	  if(selecaoAno>0 && selecaoSemestre > 0 && selecaoCurso > 0 && selecaoModulo > 0  && diarioAtual.getIdTurma() > 0){
	  desabilitaDataInicial = false;
	  }
	  else{
	  desabilitaDataInicial = true;
	  }
	  habilitaDataFinal();
	  }
	  
	  public void habilitaDataFinal(){
	  if(selecaoAno>0 && selecaoSemestre > 0 && selecaoCurso > 0 && selecaoModulo > 0 && diarioAtual.getIdTurma() > 0 &&  desabilitaDataInicial == false){
       desabilitaDataFinal = false;			  
	  }
	else{
	desabilitaDataFinal = true;
	}
	
	}
	  
	  
	  public List<Turma> listaTurmas(){
	  if(selecaoSemestre >0 && selecaoAno>0 && selecaoCurso>0){
	  desabilitaTurma=false;
	  }
	  else{
	  desabilitaTurma=true;
	  desabilitaModulo = true;
	  desabilitaDataInicial = true;
	  desabilitaDataFinal = true;
	  
	  }
	  if(selecaoModulo>0){
		  setListaTurmas(turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno));
		  System.out.println("Lista de turmas:"+listaTurmas.size());
	  }
		  
	  else{
	  setListaTurmas(turmaDao.listaTurmasAnoCurso(selecaoSemestre, selecaoAno, selecaoCurso));
	  System.out.println("Lista de turmas:"+listaTurmas.size());

	  }
	  habilitaDataInicial();
	  habilitaDataFinal();

	  return listaTurmas;	 
	  }

	  
	  public List<Disciplina> listaDisciplinas(){
	  if(selecaoSemestre >0 && selecaoAno>0 && selecaoCurso>0){
	  desabilitaDisciplina=false;
	  }
	  else{
	  desabilitaDisciplina=true;
	  desabilitaModulo = true;
	  desabilitaDataInicial = true;
	  desabilitaDataFinal = true;
	  }
	  setListaDisciplinas(disciplinaDao.listaDisciplina(selecaoCurso, selecaoModulo));
	  System.out.println("Curso:"+selecaoCurso);
	  System.out.println("Módulo:"+selecaoModulo);
	  return listaDisciplinas;	 
	  }

	
	  public void gerarLista(){
	  
	  if(ausenciaDisciplina==true){
	  diarioDao.verificarAlunosAusentesDisciplina(diarioAtual.getIdTurma(), diarioAtual.getIdDisciplina(), dataInicial, dataFinal);
	  System.out.println("Estou aqui!!!");
	  }
	  else{
	  diarioDao.verificarAlunosAusentesTurma(diarioAtual.getIdTurma(), dataInicial, dataFinal);
	  }
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

	

	public boolean getAusenciaDisciplina() {
		return ausenciaDisciplina;
	}

	public void setAusenciaDisciplina(boolean ausenciaDisciplina) {
		this.ausenciaDisciplina = ausenciaDisciplina;
	}
	
	
	public boolean habilitaCampo(){
	System.out.println(ausenciaDisciplina);
	return ausenciaDisciplina;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}




	public boolean isDesabilitaDisciplina() {
		return desabilitaDisciplina;
	}




	public void setDesabilitaDisciplina(boolean desabilitaDisciplina) {
		this.desabilitaDisciplina = desabilitaDisciplina;
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




	public int getSelecaoModulo() {
		return selecaoModulo;
	}




	public void setSelecaoModulo(int selecaoModulo) {
		this.selecaoModulo = selecaoModulo;
	}




	public boolean isDesabilitaDataInicial() {
		return desabilitaDataInicial;
	}




	public void setDesabilitaDataInicial(boolean desabilitaDataInicial) {
		this.desabilitaDataInicial = desabilitaDataInicial;
	}




	public boolean isDesabilitaDataFinal() {
		return desabilitaDataFinal;
	}




	public void setDesabilitaDataFinal(boolean desabilitaDataFinal) {
		this.desabilitaDataFinal = desabilitaDataFinal;
	}




	public PieChartModel getGraficoDiario() {
		return graficoDiario;
	}




	public void setGraficoDiario(PieChartModel graficoDiario) {
		this.graficoDiario = graficoDiario;
	}







	public String graficoComparativoDiarioTurma(){
		   graficoDiario.clear();
		   
		   List<Integer> dadosLocalizados = diarioDao.graficoChamada(diarioAtual.getIdTurma(), dataInicial, dataFinal);
		  
		   if(dadosLocalizados.size()==0){
		   dadosLocalizados.add(0);
		   dadosLocalizados.add(0);
		   }
		   if(dadosLocalizados.size()==1){
		   dadosLocalizados.add(0);
		   }
		   
		   graficoDiario.set("Compareceram:"+dadosLocalizados.get(0), dadosLocalizados.get(0));
		   graficoDiario.set("Não compareceram:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
		   
			 		   
		  
		   if(ausenciaDisciplina==true){
		   graficoComparativoDiarioDisciplina();
		   }
			return "./graficoDiario.jsf";
			}
	

	  public String graficoComparativoDiarioDisciplina(){
		   graficoDiarioDisciplina.clear();
		   
		   List<Integer> dadosLocalizados = diarioDao.graficoChamadaDisciplina(diarioAtual.getIdTurma(), diarioAtual.getIdDisciplina(), dataInicial, dataFinal);
		  
		   System.out.println(dadosLocalizados.size());
		   
		   if(dadosLocalizados.size()==0){
			   dadosLocalizados.add(0);
			   dadosLocalizados.add(0);
			   }
			   if(dadosLocalizados.size()==1){
			   dadosLocalizados.add(0);
			   }
			   
		   graficoDiarioDisciplina.set("Compareceram:"+dadosLocalizados.get(0), dadosLocalizados.get(0));
		   graficoDiarioDisciplina.set("Não compareceram:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
		   limpaCampo();
			 
			return "./graficoDiario.jsf";
			}




	public PieChartModel getGraficoDiarioDisciplina() {
		return graficoDiarioDisciplina;
	}




	public void setGraficoDiarioDisciplina(PieChartModel graficoDiarioDisciplina) {
		this.graficoDiarioDisciplina = graficoDiarioDisciplina;
	}

}

