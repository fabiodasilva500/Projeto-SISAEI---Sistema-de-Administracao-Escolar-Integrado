package ManagedBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.PieChartModel;

import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DiarioDao;
import Dao.DiarioDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.EstatisticaDao;
import Dao.EstatisticaDaoImplementation;
import Dao.GraficoInstituicaoDao;
import Dao.GraficoInstituicaoDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Aluno;
import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.Estatistica;
import Pojo.Turma;


@ManagedBean(name="graficoMB")
@SessionScoped
public class GraficoInstituicaoMB {
private PieChartModel graficoInstituicao;  

private List<Curso> listaCursos;
private GraficoInstituicaoDao gDao;

private List<Turma> listaTurmas;
private List<Aluno> listaAlunos;
private List<Disciplina> listaDisciplinas;
private List<String> listaQuestoes;

private CursoDao cursoDao;
private TurmaDao turmaDao;
private AlunoDao alunoDao;
private EstatisticaDao estatisticaDao;
private DiarioDao diarioDao;

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

private Estatistica boletimAtual;

private String nomeTurma;
private String nomeDisciplina;
private String identificacaoAluno;


private Turma turma = new Turma();
private Curso curso = new Curso();



private boolean desabilitaAno;
private boolean desabilitaGraficoTurma;
private boolean desabilitaGraficoDisciplinaFinal;
private boolean desabilitaGraficoDisciplinaParcial;
private boolean desabilitaAluno;
private boolean desabilitaQuestoes;



private int selecaoSemestre;
private int selecaoAno;
private String selecaoQuestao;

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


private Integer progress;
private int acao;



public GraficoInstituicaoMB(){
setGraficoInstituicao(new PieChartModel());	
setListaCursos(new ArrayList<Curso>());
gDao=new GraficoInstituicaoDaoImplementation();


setEstatisticaAtualTurma(new Estatistica());
setEstatisticaAtualDisciplina(new Estatistica());
setCurso(new Curso());
setTurma(new Turma());
setListaCursos(new ArrayList<Curso>());
setListaTurmas(new ArrayList<Turma>());
setListaAlunos(new ArrayList<Aluno>());
setListaDisciplinas(new ArrayList<Disciplina>());
setAnos(new ArrayList<Integer>());
setListaQuestoes(new ArrayList<String>());


cursoDao = new CursoDaoImplementation();
alunoDao = new AlunoDaoImplementation();
turmaDao = new TurmaDaoImplementation();
disciplinaDao = new DisciplinaDaoImplementation();
estatisticaDao=new EstatisticaDaoImplementation();
diarioDao = new DiarioDaoImplementation();


localizaCursos();
questoes();
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
desabilitaQuestoes=true;
progress=0;
acao=0;
}


public String gerarGrafico(String requisicao, int IDTurma){
	   graficoInstituicao.clear();
	   
	   

	   if(requisicao.contains("1-")){
	   System.out.println("Turma:"+IDTurma);
	   List<Integer> dadosLocalizados = gDao.questao1(IDTurma);	   
	   
	   graficoInstituicao.set("Sim:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
       graficoInstituicao.set("Não:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
       acao=1;
       limpaCampo();

	   return "./graficoInstituicao.jsf";
	   }
	   else
		if(requisicao.contains("2-")){
		List<Integer> dadosLocalizados = gDao.questao2(IDTurma);
	    graficoInstituicao.set("Sim:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
		graficoInstituicao.set("Não:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
		acao=1;
	    limpaCampo();

		return "./graficoInstituicao.jsf";			   
		}
	    
	   if(requisicao.contains("3-")){
	   List<Integer> dadosLocalizados = gDao.questao3(IDTurma);
	   graficoInstituicao.set("Sim:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
	   graficoInstituicao.set("Não:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
	   acao=1;
       limpaCampo();
       
	   return "./graficoInstituicao.jsf";			   
	  }
	   
	   if(requisicao.contains("4-")){
		   List<Integer> dadosLocalizados = gDao.questao4(IDTurma);
		   graficoInstituicao.set("Sim, todos oferecem:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
		   graficoInstituicao.set("Sim, a maioria oferece:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
     	   graficoInstituicao.set("Sim,  porém poucos professores oferecem:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
     	   graficoInstituicao.set("Não, nenhum professor oferece:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
    	   acao=1;
           limpaCampo();
     	   return "./graficoInstituicao.jsf";			   
	  }
	   else
		  if(requisicao.contains("5-")){
			  List<Integer> dadosLocalizados = gDao.questao5(IDTurma);
			   graficoInstituicao.set("Muito Bom:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
			   graficoInstituicao.set("Bom:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
	     	   graficoInstituicao.set("Regular:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
	     	   graficoInstituicao.set("Ruim:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
	     	   graficoInstituicao.set("Péssimo:"+dadosLocalizados.get(4), dadosLocalizados.get(4));
	    	   acao=1;
	           limpaCampo();
	     	   return "./graficoInstituicao.jsf";			   
		  }
		  else
		   if(requisicao.contains("6-")){
			   List<Integer> dadosLocalizados = gDao.questao6(IDTurma);
			   graficoInstituicao.set("Sim:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
			   graficoInstituicao.set("Não:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
			   acao=1;
		       limpaCampo();
			   return "./graficoInstituicao.jsf";
		  }
		   else
			  if(requisicao.contains("7-")){
			  List<Integer> dadosLocalizados = gDao.questao7(IDTurma);
			  graficoInstituicao.set("Sim, todos:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
			  graficoInstituicao.set("Sim, boa parte são de qualidade:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
			  graficoInstituicao.set("Sim, porém poucos são de qualidade:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
			  graficoInstituicao.set("Não, os equipamentos são de baixa qualidade:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
			  acao=1;
		      limpaCampo();
			  return "./graficoInstituicao.jsf";  	   
			  }
			  else
			  if(requisicao.contains("8-")){
				  List<Integer> dadosLocalizados = gDao.questao8(IDTurma);
				  graficoInstituicao.set("Não gosto da disciplina:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
				  graficoInstituicao.set("Não gosto do professor da matéria:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
				  graficoInstituicao.set("Não gosto do professor e da disciplina:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
				  graficoInstituicao.set("Julgo a disciplina pouco relevante para a minha formação:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
				  graficoInstituicao.set("Trabalho e não tenho tempo para comparecer:"+dadosLocalizados.get(4), dadosLocalizados.get(4));
				  graficoInstituicao.set("Problemas pessoais:"+dadosLocalizados.get(5), dadosLocalizados.get(5));
				  acao=1;
			      limpaCampo();
				  return "./graficoInstituicao.jsf";  	    
			  }
			  else
              if(requisicao.contains("9-")){
			  Turma turma = new Turma();
              turma = (turmaDao.localizaDadosTurma(IDTurma));            
              List<String> listaDisciplinas=disciplinaDao.localizaDisciplinas(turma.getCurso(), turma.getModulo());
           	  System.out.println("Curso:"+turma.getCurso());            		
           	  System.out.println("Módulo:"+turma.getModulo());
              System.out.println("Tamanho da lista:"+listaDisciplinas.size());
              int i=0;

              while(i<listaDisciplinas.size()){
              int resultado = gDao.questao9(listaDisciplinas.get(i), IDTurma);
              System.out.println("Resultado:"+resultado);
			  graficoInstituicao.set(listaDisciplinas.get(i)+":"+resultado, resultado);
              i=i+1;
              }
              acao=1;
              limpaCampo();
			  return "./graficoInstituicao.jsf";  	    

              }
              else  
				 if(requisicao.contains("10-"))
				 {
					  List<Integer> dadosLocalizados = gDao.questao10(IDTurma);
					  graficoInstituicao.set("Plenamente Satisfeito:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
					  graficoInstituicao.set("Satisfeito:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
					  graficoInstituicao.set("Razoavelmente Satisfeito:"+dadosLocalizados.get(2), dadosLocalizados.get(2));
					  graficoInstituicao.set("Insatisfeito:"+dadosLocalizados.get(3), dadosLocalizados.get(3));
					  graficoInstituicao.set("Plenamente Insatisfeito:"+dadosLocalizados.get(4), dadosLocalizados.get(4));
					  acao=1;
				       limpaCampo();
					  return "./graficoInstituicao.jsf";  	    
				 }
				 else
				  if(requisicao.contains("11-")){
					    List<Integer> dadosLocalizados = gDao.questao11(IDTurma);
					    graficoInstituicao.set("Sim:"+ dadosLocalizados.get(0), dadosLocalizados.get(0));
						graficoInstituicao.set("Não:"+dadosLocalizados.get(1), dadosLocalizados.get(1));
						acao=1;
					    limpaCampo();
						return "./graficoInstituicao.jsf";		  
				  }
	    
	   return "";
	}		



private List<String> questoes(){
listaQuestoes.add("1-A escola possuí instalações adequadas a execução das atividades de seu curso?");
listaQuestoes.add("2-Todos os professores apresentam a ementa das disciplinas na 1ª aula?");
listaQuestoes.add("3-Todos os professores apresentam o esquema de avaliação na 1ª aula?");
listaQuestoes.add("4-Os professores oferecem meios de recuperação de aprendizagem para estudantes que não estejam rendendo?");
listaQuestoes.add("5-Como é o relacionamento entre os estudantes em sua sala de aula?");
listaQuestoes.add("6-Há cooperação entre os estudantes de modo que o processo ensino aprendizagem ocorra adequadamente?");
listaQuestoes.add("7-Para você os equipamentos contidos na Etec são de qualidade?");
listaQuestoes.add("8-Qual dos motivos abaixo acarreta mais na sua ausência em dias letivos?");
listaQuestoes.add("9-Qual disciplina no semestre atual você está tendo maior dificuldade?");
listaQuestoes.add("10-Você está satisfeito com o seu curso?");
listaQuestoes.add("11-Você acha que há necessidade de grandes mudanças na forma com que o curso é oferecido pela instituição ?");
return listaQuestoes;
}

public PieChartModel getGraficoInstituicao() {
	return graficoInstituicao;
}

public void setGraficoInstituicao(PieChartModel graficoInstituicao) {
	this.graficoInstituicao = graficoInstituicao;
}


//Listando os cursos de acordo com o valor selecionado
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
			desabilitaQuestoes=false;
			desabilitaDisciplina = false;
			desabilitaAluno=false;
		} else {
			desabilitaTurma = true;
			desabilitaQuestoes=true;
			desabilitaDisciplina = true;
			desabilitaAluno=true;
			selecaoTurma=0;
		}
		listaDisciplina();
		localizaAlunos();
	}

	// Listando as turmas de acordo com o módulo selecionado
	public void listaTurmaModulo() {
		System.out.println("Fazendo besteira");
		setListaTurmas(turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno));
		listaDisciplina();
		localizaAlunos();

	}

	// Listando as disciplinas de acordo com o curso e módulo selecionados
	public void listaDisciplina() {
		setListaDisciplinas(disciplinaDao.listaDisciplina(selecaoCurso,
				selecaoModulo));

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
			  desabilitaQuestoes=true;
			  desabilitaCurso=true;
			  desabilitaConceito=true;
			  desabilitaModulo=true;
			  desabilitaAluno=true;
			  selecaoCurso=0;
			  selecaoDisciplina=0;
			  selecaoTurma=0;
			  }
		  
		  		  
		  if(selecaoAno==0 || selecaoSemestre==0){
		  desabilitaCurso=true;
	      desabilitaConceito=true;
	      desabilitaModulo=true;
	      desabilitaTurma=true;
	      desabilitaQuestoes=true;
	      desabilitaDisciplina=true;
	      desabilitaModulo=true;
	      desabilitaAluno=true;
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
			  desabilitaQuestoes=true;
			  selecaoCurso=0;
			  selecaoDisciplina=0;
			  selecaoTurma=0;
			  }
		  
		  		  
		  if(selecaoAno==0 || selecaoSemestre==0){
		  desabilitaCurso=true;
	      desabilitaConceito=true;
	      desabilitaModulo=true;
	      desabilitaTurma=true;
	      desabilitaQuestoes=true;
	      desabilitaDisciplina=true;
	      desabilitaModulo=true;
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


public List<String> getListaQuestoes() {
	return listaQuestoes;
}


public void setListaQuestoes(List<String> listaQuestoes) {
	this.listaQuestoes = listaQuestoes;
}


public boolean isDesabilitaQuestoes() {
	return desabilitaQuestoes;
}


public void setDesabilitaQuestoes(boolean desabilitaQuestoes) {
	this.desabilitaQuestoes = desabilitaQuestoes;
}


public String getSelecaoQuestao() {
	return selecaoQuestao;
}


public void setSelecaoQuestao(String selecaoQuestao) {
	this.selecaoQuestao = selecaoQuestao;
}






public Integer getProgress() {
	int contador=0;
	
     System.out.println("Carregando");
	if(acao==1){
    progress=0;
    acao=0;
    return progress;
    }
	else{
    progress=100;
    System.out.println("Carregado");
    return progress;
	}
    }

public void setProgress(Integer progress) {
    this.progress = progress;
}
 
public void onComplete() {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Progress Completed"));
}
 
public void cancel() {
    progress = null;
}


public String exportar(){
return "";
}


public void limpaCampo(){
selecaoAno=0;
selecaoAnoFinal=0;
selecaoAnoInicial=0;
selecaoConceito=0;
selecaoCurso=0;
selecaoDisciplina=0;
selecaoModulo=0;
selecaoQuestao="";
selecaoSemestre=0;
selecaoSemestreFinal=0;
selecaoSemestreInicial=0;
selecaoTurma=0;
desabilitaAluno=true;
desabilitaAno=true;
desabilitaConceito=true;
desabilitaCurso=true;
desabilitaDisciplina=true;
desabilitaGraficoDisciplinaFinal=true;
desabilitaGraficoDisciplinaParcial=true;
desabilitaGraficoTurma=true;
desabilitaModulo=true;
desabilitaQuestoes=true;
desabilitaTurma=true;

}

}
