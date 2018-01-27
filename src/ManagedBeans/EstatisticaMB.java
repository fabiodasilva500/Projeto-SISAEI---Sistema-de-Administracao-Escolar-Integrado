package ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.AlunoTurmaDao;
import Dao.AlunoTurmaDaoImplementation;
import Dao.AreaConcentracaoDao;
import Dao.AreaConcentracaoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DiarioDao;
import Dao.DiarioDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.EstatisticaDao;
import Dao.EstatisticaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Aluno;
import Pojo.AreaConcentracao;
import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.Estatistica;
import Pojo.Turma;

@ManagedBean(name = "estatisticaMB")
@SessionScoped
public class EstatisticaMB implements Serializable {

	private static final long serialVersionUID = -4281390476508498320L;
	private List<Curso> listaCursos;
	private List<Turma> listaTurmas;
	private List<Aluno> listaAlunos;
	private List<Disciplina> listaDisciplinas;
	private CursoDao cursoDao;
	private TurmaDao turmaDao;
	private AlunoDao alunoDao;
	private EstatisticaDao estatisticaDao;
	private DiarioDao diarioDao;
    private AlunoTurmaDao alunoTurmaDao;
	private DisciplinaDao disciplinaDao;
	private int selecaoCurso;
	private int selecaoTurma;
	private int selecaoModulo;
	private int quantidade=0;

	private int selecaoDisciplina;
	private int selecaoConceito;

	private boolean desabilitaTurma;
	private boolean desabilitaModulo;
	private boolean desabilitaDisciplina;

	private boolean desabilitaConceito;
	private boolean desabilitaCurso;
	private boolean desabilitaArea;
	
	
	private Estatistica boletimAtual;

	private String nomeTurma;
	private String nomeDisciplina;
	private String identificacaoAluno;

	private Turma turma = new Turma();
	
	private Curso curso = new Curso();
    private PieChartModel graficoTurmaFinal;  
    private PieChartModel graficoDisciplinaParcial;  
    private PieChartModel graficoDisciplinaFinal;  
    private PieChartModel graficoConcluintes;  


    private CartesianChartModel graficoCategoriaTurma;  
    
    private CartesianChartModel graficoCategoriaAluno;  

    
    
	
	private boolean desabilitaAno;
	private boolean desabilitaGraficoTurma;
	private boolean desabilitaGraficoDisciplinaFinal;
	private boolean desabilitaGraficoDisciplinaParcial;
	private boolean desabilitaAluno;
	


	private int selecaoSemestre;
	private int selecaoAno;
    private List<Integer> anos;
    private Integer ano;

    private Estatistica estatisticaAtualTurma;
    private Estatistica estatisticaAtualDisciplina;
    private String requisicao;
    private List<Estatistica> estatistica = new ArrayList<Estatistica>();



    private int selecaoAnoInicial;
    private int selecaoAnoFinal;
    private int selecaoSemestreInicial;
    private int selecaoSemestreFinal;
    private int selecaoArea;
    private List<AreaConcentracao> listaAreas;
    private AreaConcentracaoDao areaDao;
    private String requisicaoArea;
    

	public EstatisticaMB() {
		setEstatisticaAtualTurma(new Estatistica());
		setEstatisticaAtualDisciplina(new Estatistica());
		setCurso(new Curso());
		setTurma(new Turma());
		setListaCursos(new ArrayList<Curso>());
		setListaTurmas(new ArrayList<Turma>());
		setListaAlunos(new ArrayList<Aluno>());
		setListaDisciplinas(new ArrayList<Disciplina>());
	
		setAnos(new ArrayList<Integer>());
		cursoDao = new CursoDaoImplementation();
		alunoDao = new AlunoDaoImplementation();
		turmaDao = new TurmaDaoImplementation();
		disciplinaDao = new DisciplinaDaoImplementation();
		estatisticaDao=new EstatisticaDaoImplementation();
		diarioDao = new DiarioDaoImplementation();
		areaDao = new AreaConcentracaoDaoImplementation();
		setGraficoTurmaFinal(new PieChartModel());
		setGraficoConcluintes(new PieChartModel());

		setGraficoDisciplinaParcial(new PieChartModel());
		setGraficoDisciplinaFinal(new PieChartModel());

		setGraficoCategoriaTurma(new CartesianChartModel());
		setGraficoCategoriaAluno(new CartesianChartModel());
		
		localizaCursos();
		desabilitaTurma = true;
		desabilitaDisciplina = true;
		desabilitaAno=true;
		desabilitaConceito=true;
		desabilitaCurso=true;
		desabilitaModulo=true;
		desabilitaGraficoTurma=true;
		desabilitaGraficoDisciplinaParcial=true;
		desabilitaGraficoDisciplinaFinal=true;
		desabilitaAluno=true;
		desabilitaArea=true;
		
	    }
	

	// Listando os cursos de acordo com o valor selecionado
	public List<Curso> localizaCursos() {
		setListaCursos(cursoDao.listaCursos());
		return listaCursos;
	}
	
	// Listando os cursos de acordo com o valor selecionado
		public List<Curso> listaCursos() {
			setListaCursos(cursoDao.listaCursos());
			listaTurmaCurso();
			return listaCursos;
		}
		
	

	// Listando as turmas do curso selecionado
	public void listaTurmaCurso() {
		setListaTurmas(turmaDao.listaTurmaCurso(selecaoCurso, selecaoSemestre, selecaoAno));

		
		if (selecaoCurso > 0) {
			desabilitaTurma = false;
			desabilitaDisciplina = false;
			desabilitaAluno=false;
			desabilitaModulo=false;
		} else {
			desabilitaModulo=true;
			desabilitaTurma = true;
			desabilitaDisciplina = true;
			desabilitaAluno=true;
			selecaoTurma=0;
		}
		listaDisciplina();
		localizaAlunos();
	}

	// Listando as turmas de acordo com o módulo selecionado
	public void listaTurmaModulo() {
		setListaTurmas(turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno));
		listaDisciplina();
		localizaAlunos();

	}

	// Listando as disciplinas de acordo com o curso e módulo selecionados
	public void listaDisciplina() {
		setListaDisciplinas(disciplinaDao.listaDisciplina(selecaoCurso,
				selecaoModulo));
	 listaAreas();

	}
	
	
	public List<Aluno> localizaAlunos() {
		setListaAlunos(alunoDao.consultaAluno(selecaoTurma));
		return listaAlunos;
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



	public Estatistica getBoletimAtual() {
		return boletimAtual;
	}

	public void setBoletimAtual(Estatistica boletimAtual) {
		this.boletimAtual = boletimAtual;
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



	public int getSelecaoConceito() {
		return selecaoConceito;
	}

	public void setSelecaoConceito(int selecaoConceito) {
		this.selecaoConceito = selecaoConceito;
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
	
	
	public List<Integer> listaAnosGraficoComparacao(){
		  Calendar cal = GregorianCalendar.getInstance(); 
		 

		  anos.clear();
		  if(selecaoSemestre>0){
			  desabilitaAno=false;
			  }
		     else{
			  desabilitaAno=true;
			  desabilitaDisciplina=true;
			  desabilitaTurma=true;
			  desabilitaCurso=true;
			  desabilitaConceito=true;
			  desabilitaModulo=true;
			  desabilitaAluno=true;
			  desabilitaArea=true;
			  selecaoCurso=0;
			  selecaoDisciplina=0;
			  selecaoTurma=0;
			  }
		  
		  		  
		  if(selecaoAno==0 || selecaoSemestre==0){
		  desabilitaCurso=true;
	      desabilitaConceito=true;
	      desabilitaModulo=true;
	      desabilitaTurma=true;
	      desabilitaDisciplina=true;
	      desabilitaModulo=true;
	      desabilitaAluno=true;
	      desabilitaArea=true;
	      selecaoCurso=0;
	      selecaoDisciplina=0;
	      selecaoTurma=0;
		  }
		  else
		  if(selecaoAno>0 && selecaoSemestre>0){
		  desabilitaConceito=false;
		  desabilitaCurso=false;
		  }
		  
	
		   ano = cal.get(Calendar.YEAR); 

		    for (int i=0;i<=4;i++){
		    anos.add(ano);
		    ano=ano-1;
		    }
		   
		  listaTurmaCurso();
		  listaTurmaModulo();
		  
	
	    
	    return anos;
		}
	
	
	
	public List<Integer> listaAnoAluno(){
		  Calendar cal = GregorianCalendar.getInstance(); 
		 

		  anos.clear();
		  if(selecaoSemestre>0){
			  desabilitaAno=false;
			  }
		     else{
			  desabilitaAno=true;
			  desabilitaDisciplina=true;
			  desabilitaTurma=true;
			  desabilitaCurso=true;
			  desabilitaConceito=true;
			  desabilitaModulo=true;
			  desabilitaArea=true;
			  selecaoCurso=0;
			  selecaoDisciplina=0;
			  selecaoTurma=0;
			  }
		  
		  		  
		  if(selecaoAno==0 || selecaoSemestre==0){
		  desabilitaCurso=true;
	      desabilitaConceito=true;
	      desabilitaModulo=true;
	      desabilitaTurma=true;
	      desabilitaDisciplina=true;
	      desabilitaModulo=true;
	      desabilitaArea=true;
	      selecaoCurso=0;
	      selecaoDisciplina=0;
	      selecaoTurma=0;
		  }
		  else
		  if(selecaoAno>0 && selecaoSemestre>0){
		  desabilitaConceito=false;
		  desabilitaCurso=false;
		  desabilitaModulo=false;
		  }
		  
	
		   ano = cal.get(Calendar.YEAR); 

		    for (int i=0;i<=5;i++){
		    anos.add(ano);
		    ano=ano-1;
		    }
		   
		  listaTurmaCurso();
		
	    return anos;
		}
	
	

	
	public void verificaSemestre(){
	   if(selecaoSemestre==0){
	   desabilitaAno=true;
	   }
	   
	  
	   
	   
		}


	public List<Integer> getAnos() {
		return anos;
	}


	public void setAnos(List<Integer> anos) {
		this.anos = anos;
	}


	public boolean isDesabilitaAno() {
		return desabilitaAno;
	}


	public void setDesabilitaAno(boolean desabilitaAno) {
		this.desabilitaAno = desabilitaAno;
	}


	public Integer getAno() {
		return ano;
	}


	public void setAno(Integer ano) {
		this.ano = ano;
	}


	public boolean isDesabilitaConceito() {
		return desabilitaConceito;
	}


	public void setDesabilitaConceito(boolean desabilitaConceito) {
		this.desabilitaConceito = desabilitaConceito;
	}


	public boolean isDesabilitaCurso() {
		return desabilitaCurso;
	}


	public void setDesabilitaCurso(boolean desabilitaCurso) {
		this.desabilitaCurso = desabilitaCurso;
	}

	
	public String consultaTurmaParcial(){
	return "";
	}
	

	public PieChartModel getGraficoTurmaFinal() {
		return graficoTurmaFinal;
	}


	public void setGraficoTurmaFinal(PieChartModel graficoTurmaFinal) {
		this.graficoTurmaFinal = graficoTurmaFinal;
	}


	public Estatistica getEstatisticaAtualTurma() {
		return estatisticaAtualTurma;
	}


	public void setEstatisticaAtualTurma(Estatistica estatisticaAtualTurma) {
		this.estatisticaAtualTurma = estatisticaAtualTurma;
	}


	public List<Estatistica> getEstatistica() {
		return estatistica;
	}


	public void setEstatistica(List<Estatistica> estatistica) {
		this.estatistica = estatistica;
	}




	public CartesianChartModel getGraficoCategoriaTurma() {
		return graficoCategoriaTurma;
	}


	public void setGraficoCategoriaTurma(CartesianChartModel graficoCategoriaTurma) {
		this.graficoCategoriaTurma = graficoCategoriaTurma;
	}


	public boolean isDesabilitaGraficoTurma() {
		return desabilitaGraficoTurma;
	}


	public void setDesabilitaGraficoTurma(boolean desabilitaGraficoTurma) {
		this.desabilitaGraficoTurma = desabilitaGraficoTurma;
	}


	public boolean isDesabilitaGraficoDisciplinaFinal() {
		return desabilitaGraficoDisciplinaFinal;
	}


	public void setDesabilitaGraficoDisciplinaFinal(boolean desabilitaGraficoDisciplinaFinal) {
		this.desabilitaGraficoDisciplinaFinal = desabilitaGraficoDisciplinaFinal;
	}


	public PieChartModel getGraficoDisciplina() {
		return graficoDisciplinaFinal;
	}


	public void setGraficoDisciplinaFinal(PieChartModel graficoDisciplinaFinal) {
		this.graficoDisciplinaFinal = graficoDisciplinaFinal;
	}


	public Estatistica getEstatisticaAtualDisciplina() {
		return estatisticaAtualDisciplina;
	}


	public void setEstatisticaAtualDisciplina(Estatistica estatisticaAtualDisciplina) {
		this.estatisticaAtualDisciplina = estatisticaAtualDisciplina;
	}


	public boolean isDesabilitaGraficoDisciplinaParcial() {
		return desabilitaGraficoDisciplinaParcial;
	}


	public void setDesabilitaGraficoDisciplinaParcial(
			boolean desabilitaGraficoDisciplinaParcial) {
		this.desabilitaGraficoDisciplinaParcial = desabilitaGraficoDisciplinaParcial;
	}


	public PieChartModel getGraficoDisciplinaParcial() {
		return graficoDisciplinaParcial;
	}


	public void setGraficoDisciplinaParcial(PieChartModel graficoDisciplinaParcial) {
		this.graficoDisciplinaParcial = graficoDisciplinaParcial;
	}


	public PieChartModel getGraficoDisciplinaFinal() {
		return graficoDisciplinaFinal;
	}
	
	

	public String redirecionar(){
	desabilitaAno=true;
	desabilitaAluno=true;
	desabilitaConceito=true;
	desabilitaCurso=true;
	desabilitaDisciplina=true;
	desabilitaModulo=true;
	desabilitaTurma=true;
	desabilitaArea=true;
	
	
	selecaoSemestre=0;
	selecaoAno=0;
	selecaoAnoFinal=0;
	selecaoAnoInicial=0;
	selecaoConceito=0;
	selecaoCurso=0;
	selecaoDisciplina=0;
	selecaoModulo=0;
	selecaoSemestreFinal=0;
	selecaoSemestreInicial=0;
	selecaoTurma=0;
	
	System.out.println("Requisição:"+requisicao);

		
	if(requisicao.equalsIgnoreCase("turma")){
	return "./estatisticaTurma.jsf";
	}
	else
	if(requisicao.equalsIgnoreCase("disciplina")){
	return "./estatisticaDisciplina.jsf";
	}
	else
	if(requisicao.equalsIgnoreCase("comparacaoTurmas")){
	System.out.println("Retornando");
    graficoCategoriaTurma.clear();
	return "./estatisticaComparacaoTurmas.jsf";
	}
	else
	if(requisicao.equalsIgnoreCase("aluno")){
	return "./estatisticaAluno.jsf";		
	}
	return "";
	}
	
	
	public String redirecionaArea(){
	if(requisicaoArea.equalsIgnoreCase("areaConcentracao")){
	return "./estatisticaAreaConcentracao.jsf";
	}
	else
	if(requisicaoArea.equalsIgnoreCase("areaAluno")){
	return "./estatisticaAreaAluno.jsf";
	}
	else
	if(requisicaoArea.equalsIgnoreCase("areaTurma")){
	return "./estatisticaAreaTurma.jsf";
	}
	return "";
	}
	
	
   public String consultaTurmaFinal(){
   desabilitaGraficoTurma=true;
   desabilitaGraficoDisciplinaParcial=false;
   desabilitaGraficoDisciplinaFinal=false;
   graficoTurmaFinal.clear();
   
   
   List<Integer> dadosLocalizados = estatisticaDao.estatisticaTurmaFinal(selecaoTurma);
   
   
   graficoTurmaFinal.set("Aprovados:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
   graficoTurmaFinal.set("Promovidos com progressão parcial:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
   graficoTurmaFinal.set("Retidos por nota:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
   graficoTurmaFinal.set("Retidos por falta:"+dadosLocalizados.get(0), dadosLocalizados.get(0));
   limpaCampo();

   
	return "./graficoDisciplinaTurma.jsf";
	}

   
   public String verificaSelecao(){
   if(selecaoConceito==1){
   return consultaDisciplinaParcial();
   }
   else
   if(selecaoConceito==2){
   return consultaDisciplinaFinal();
   }
   return "";
   }

   public String consultaDisciplinaParcial(){
   desabilitaGraficoTurma=false;
   desabilitaGraficoDisciplinaFinal=false;
   desabilitaGraficoDisciplinaParcial=true;
   graficoDisciplinaParcial.clear();
   
   List<Integer> dadosLocalizados = estatisticaDao.estatisticaDisciplinaParcial(selecaoTurma, selecaoDisciplina);
  
   
   graficoDisciplinaParcial.set("MB:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
   graficoDisciplinaParcial.set("B:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
   graficoDisciplinaParcial.set("R:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
   graficoDisciplinaParcial.set("I:"+dadosLocalizados.get(0), dadosLocalizados.get(0));
   limpaCampo();

	 
	return "./graficoDisciplinaTurma.jsf";
	}
   
   
   
   public String consultaDisciplinaFinal(){
	   desabilitaGraficoTurma=false;
	   desabilitaGraficoDisciplinaParcial=true;
	   desabilitaGraficoDisciplinaFinal=true;
   graficoDisciplinaParcial.clear();
   graficoDisciplinaFinal.clear();
   
   List<Integer> dadosLocalizadosParciais = estatisticaDao.estatisticaDisciplinaParcial(selecaoTurma, selecaoDisciplina);

   List<Integer> dadosLocalizados = estatisticaDao.estatisticaDisciplinaFinal(selecaoTurma, selecaoDisciplina);

   
   graficoDisciplinaParcial.set("MB:"+dadosLocalizadosParciais.get(3), dadosLocalizadosParciais.get(3));
   graficoDisciplinaParcial.set("B:"+dadosLocalizadosParciais.get(2), dadosLocalizadosParciais.get(2));
   graficoDisciplinaParcial.set("R:"+dadosLocalizadosParciais.get(1), dadosLocalizadosParciais.get(1));
   graficoDisciplinaParcial.set("I:"+dadosLocalizadosParciais.get(0), dadosLocalizadosParciais.get(0));
   
   
   graficoDisciplinaFinal.set("MB:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
   graficoDisciplinaFinal.set("B:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
   graficoDisciplinaFinal.set("R:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
   graficoDisciplinaFinal.set("I:"+dadosLocalizados.get(0), dadosLocalizados.get(0));
   limpaCampo();

	 
	return "./graficoDisciplinaTurma.jsf";
	}

   

public String graficoComparativoTurmas(){
	
	
ChartSeries aprovados= new ChartSeries();  
ChartSeries aprovadosComProgressaoParcial= new ChartSeries();  
ChartSeries  retidosPorNota= new ChartSeries();  
ChartSeries  retidosPorFalta= new ChartSeries();  
List<Integer> dadosLocalizados=new ArrayList<Integer>();


graficoCategoriaTurma.clear();

aprovados.setLabel("Promovido");
aprovadosComProgressaoParcial.setLabel("Promovido com progressão parcial");
retidosPorNota.setLabel("Retidos por Nota");
retidosPorFalta.setLabel("Retidos por falta");

System.out.println("Semestre inicial:"+selecaoSemestreInicial);
System.out.println("Semestre final:"+selecaoSemestreFinal);

while((selecaoAnoInicial<selecaoAnoFinal) ||
	(selecaoAnoInicial<=selecaoAnoFinal && selecaoSemestreInicial<=selecaoSemestreFinal)){
	if(selecaoSemestreInicial==3){
	selecaoSemestreInicial=1;
	selecaoAnoInicial=selecaoAnoInicial+1;
	}
	
System.out.println("Carregando gráfico");

dadosLocalizados=estatisticaDao.localizaDadosComparativos(selecaoCurso, selecaoModulo, selecaoAnoInicial, selecaoSemestreInicial);

aprovadosComProgressaoParcial.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(3));  
aprovados.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(2));  
retidosPorNota.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(1));  
retidosPorFalta.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(0));  


selecaoSemestreInicial=selecaoSemestreInicial+1;
}


graficoCategoriaTurma.addSeries(aprovados);
graficoCategoriaTurma.addSeries(aprovadosComProgressaoParcial);
graficoCategoriaTurma.addSeries(retidosPorNota);
graficoCategoriaTurma.addSeries(retidosPorFalta);
limpaCampo();



return "./graficoComparativoTurma.jsf";
}



public String graficoComparativoAreaTurmas(){
ChartSeries aprovados= new ChartSeries();  
ChartSeries aprovadosComProgressaoParcial= new ChartSeries();  
ChartSeries  retidosPorNota= new ChartSeries();  
ChartSeries  retidosPorFalta= new ChartSeries();  
List<Integer> dadosLocalizados=new ArrayList<Integer>();


graficoCategoriaTurma.clear();

aprovados.setLabel("Promovido");
aprovadosComProgressaoParcial.setLabel("Promovido com progressão parcial");
retidosPorNota.setLabel("Retidos por Nota");
retidosPorFalta.setLabel("Retidos por falta");

System.out.println("Semestre inicial:"+selecaoSemestreInicial);
System.out.println("Semestre final:"+selecaoSemestreFinal);

while((selecaoAnoInicial<selecaoAnoFinal) ||
(selecaoAnoInicial<=selecaoAnoFinal && selecaoSemestreInicial<=selecaoSemestreFinal)){
	if(selecaoSemestreInicial==3){
	selecaoSemestreInicial=1;
	selecaoAnoInicial=selecaoAnoInicial+1;
	}
	
System.out.println("Carregando gráfico");

dadosLocalizados=estatisticaDao.localizaDadosAreaTurma(selecaoCurso, selecaoModulo, selecaoAnoInicial, selecaoSemestreInicial, selecaoArea);

aprovadosComProgressaoParcial.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(3));  
aprovados.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(2));  
retidosPorNota.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(1));  
retidosPorFalta.set(selecaoSemestreInicial+"º "+selecaoAnoInicial, dadosLocalizados.get(0));  


selecaoSemestreInicial=selecaoSemestreInicial+1;
}


graficoCategoriaTurma.addSeries(aprovados);
graficoCategoriaTurma.addSeries(aprovadosComProgressaoParcial);
graficoCategoriaTurma.addSeries(retidosPorNota);
graficoCategoriaTurma.addSeries(retidosPorFalta);
limpaCampo();

return "./graficoAreaTurma.jsf";
}




public String graficoAreaConcentracao(){
	   desabilitaGraficoTurma=true;
	   desabilitaGraficoDisciplinaParcial=false;
	   desabilitaGraficoDisciplinaFinal=false;
	   graficoTurmaFinal.clear();
	   

	   List<Integer> dadosLocalizados = estatisticaDao.estatisticaAreaConcentracao(selecaoArea, selecaoTurma);

	   graficoTurmaFinal.set("Aprovados:"+dadosLocalizados.get(0), dadosLocalizados.get(0));
	   graficoTurmaFinal.set("Retidos por nota:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
	   graficoTurmaFinal.set("Retidos por falta:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
	   
	   limpaCampo();
	   
return "./graficoAreaConcentracao.jsf";
}



public String graficoComparativoAlunos(){
	
ChartSeries notaMB= new ChartSeries();  
ChartSeries notaB= new ChartSeries();  
ChartSeries  notaR= new ChartSeries();  
ChartSeries  notaI= new ChartSeries();  
List<Turma> turmaLocalizada=new ArrayList<Turma>();
List<Integer> dadosLocalizados=new ArrayList<Integer>();
graficoCategoriaAluno.clear();



notaMB.setLabel("MB");
notaB.setLabel("B");
notaR.setLabel("R");
notaI.setLabel("I");

	
turmaLocalizada=estatisticaDao.localizaTurmaAluno(identificacaoAluno, selecaoCurso);

for (int i=0; i<turmaLocalizada.size();i++){
dadosLocalizados=estatisticaDao.localizaDadosAluno(turmaLocalizada.get(i).getIDTurma(), identificacaoAluno, selecaoCurso);


System.out.println("Identificação:"+identificacaoAluno+" Turma :"+turmaLocalizada.get(i).getIDTurma());

notaMB.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(3));  
notaB.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(2));  
notaR.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(1));  
notaI.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(0));  
}

graficoCategoriaAluno.addSeries(notaMB);
graficoCategoriaAluno.addSeries(notaB);
graficoCategoriaAluno.addSeries(notaR);
graficoCategoriaAluno.addSeries(notaI);
limpaCampo();


return "./graficoComparativoAluno.jsf";
}



public String graficoComparativoAreaAlunos(){
	
ChartSeries notaMB= new ChartSeries();  
ChartSeries notaB= new ChartSeries();  
ChartSeries  notaR= new ChartSeries();  
ChartSeries  notaI= new ChartSeries();  
List<Turma> turmaLocalizada=new ArrayList<Turma>();
List<Integer> dadosLocalizados=new ArrayList<Integer>();
graficoCategoriaAluno.clear();



notaMB.setLabel("MB");
notaB.setLabel("B");
notaR.setLabel("R");
notaI.setLabel("I");

	
turmaLocalizada=estatisticaDao.localizaTurmaAluno(identificacaoAluno, selecaoCurso);

for (int i=0; i<turmaLocalizada.size();i++){
dadosLocalizados=estatisticaDao.localizaDadosAreaAluno(turmaLocalizada.get(i).getIDTurma(), identificacaoAluno, selecaoCurso, selecaoArea);


System.out.println("Identificação:"+identificacaoAluno+" Turma :"+turmaLocalizada.get(i).getIDTurma());

notaMB.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(3));  
notaB.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(2));  
notaR.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(1));  
notaI.set(String.valueOf(turmaLocalizada.get(i).getModulo())+"º módulo "+"cursado no "+turmaLocalizada.get(i).getSemestre()+"º sem. de "+turmaLocalizada.get(i).getAno(), dadosLocalizados.get(0));  
}

graficoCategoriaAluno.addSeries(notaMB);
graficoCategoriaAluno.addSeries(notaB);
graficoCategoriaAluno.addSeries(notaR);
graficoCategoriaAluno.addSeries(notaI);
limpaCampo();


return "./graficoAreaAluno.jsf";
}


public String getRequisicao() {
	return requisicao;
}


public void setRequisicao(String requisicao) {
	this.requisicao = requisicao;
}


public int getSelecaoAnoInicial() {
	return selecaoAnoInicial;
}


public void setSelecaoAnoInicial(int selecaoAnoInicial) {
	this.selecaoAnoInicial = selecaoAnoInicial;
}


public int getSelecaoAnoFinal() {
	return selecaoAnoFinal;
}


public void setSelecaoAnoFinal(int selecaoAnoFinal) {
	this.selecaoAnoFinal = selecaoAnoFinal;
}


public int getSelecaoSemestreInicial() {
	return selecaoSemestreInicial;
}


public void setSelecaoSemestreInicial(int selecaoSemestreInicial) {
	this.selecaoSemestreInicial = selecaoSemestreInicial;
}


public int getSelecaoSemestreFinal() {
	return selecaoSemestreFinal;
}


public void setSelecaoSemestreFinal(int selecaoSemestreFinal) {
	this.selecaoSemestreFinal = selecaoSemestreFinal;
}


public int getQuantidade() {
	return quantidade;
}


public void setQuantidade(int quantidade) {
	this.quantidade = quantidade;
}





public CartesianChartModel getGraficoCategoriaAluno() {
	return graficoCategoriaAluno;
}


public void setGraficoCategoriaAluno(CartesianChartModel graficoCategoriaAluno) {
	this.graficoCategoriaAluno = graficoCategoriaAluno;
}


public String getIdentificacaoAluno() {
	return identificacaoAluno;
}


public void setIdentificacaoAluno(String identificacaoAluno) {
	this.identificacaoAluno = identificacaoAluno;
}


public boolean isDesabilitaAluno() {
	return desabilitaAluno;
}


public void setDesabilitaAluno(boolean desabilitaAluno) {
	this.desabilitaAluno = desabilitaAluno;
}


public int getSelecaoArea() {
	return selecaoArea;
}


public void setSelecaoArea(int selecaoArea) {
	this.selecaoArea = selecaoArea;
}




public List<AreaConcentracao> listaAreas(){	
if(selecaoAno>0 && selecaoSemestre>0 && selecaoCurso>0){
desabilitaArea=false;
}
else{
desabilitaArea=true;
}

setListaAreas(areaDao.listaAreas(selecaoCurso));
return listaAreas;
}



public List<AreaConcentracao> getListaAreas() {
	return listaAreas;
}


public void setListaAreas(List<AreaConcentracao> listaAreas) {
	this.listaAreas = listaAreas;
}


public boolean isDesabilitaArea() {
	return desabilitaArea;
}


public void setDesabilitaArea(boolean desabilitaArea) {
	this.desabilitaArea = desabilitaArea;
}


public String getRequisicaoArea() {
	return requisicaoArea;
}


public void setRequisicaoArea(String requisicaoArea) {
	this.requisicaoArea = requisicaoArea;
}


public String graficoEstudantesConcluintes(){
	   graficoConcluintes.clear();
	   estatisticaDao = new EstatisticaDaoImplementation();
	   alunoTurmaDao = new AlunoTurmaDaoImplementation();
	   disciplinaDao = new DisciplinaDaoImplementation();
	   
	   int matriculados= alunoTurmaDao.localizaMatriculados(selecaoSemestreInicial, selecaoAnoInicial, selecaoCurso);
	   int disciplinasCurso = disciplinaDao.localizaDisciplinas(selecaoCurso);
	   List<String> listaIdentificacoes = alunoTurmaDao.localizaIdentificacoes(selecaoSemestreInicial, selecaoAnoInicial, selecaoCurso);
			   
	   List<Integer> dadosLocalizados = estatisticaDao.estatisticaConcluintes(listaIdentificacoes, selecaoCurso, disciplinasCurso, matriculados);
	   
	   graficoConcluintes.set("Formandos:" +dadosLocalizados.get(0), dadosLocalizados.get(0));
	   graficoConcluintes.set("Alunos matricualdos não concluintes:" +dadosLocalizados.get(1), dadosLocalizados.get(1));
	   graficoConcluintes.set("Alunos evadidos:" +dadosLocalizados.get(2), dadosLocalizados.get(2));
	   
	   limpaCampo();
	   
return "./graficoConcluintes.jsf";
}



public void limpaCampo(){
selecaoAno=0;
selecaoAnoFinal=0;
selecaoAnoInicial=0;
selecaoArea=0;
selecaoConceito=0;
selecaoCurso=0;
selecaoDisciplina=0;
selecaoModulo=0;
selecaoSemestre=0;
selecaoSemestreFinal=0;
selecaoSemestreInicial=0;
selecaoTurma=0;
desabilitaAluno=true;
desabilitaAno=true;
desabilitaArea=true;
desabilitaConceito=true;
desabilitaCurso=true;
desabilitaDisciplina=true;
desabilitaModulo=true;
desabilitaTurma=true;

}


public PieChartModel getGraficoConcluintes() {
	return graficoConcluintes;
}


public void setGraficoConcluintes(PieChartModel graficoConcluintes) {
	this.graficoConcluintes = graficoConcluintes;
}
}