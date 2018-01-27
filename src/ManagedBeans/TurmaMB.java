package ManagedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Curso;
import Pojo.Turma;

@ManagedBean(name="turmaMB")
@ViewScoped

public class TurmaMB implements Serializable{
	private Turma turmaAtual;
	private TurmaDao turma;
	private List<Turma> turmas = new ArrayList<Turma>(); 
	private List<Integer> anos=new ArrayList<Integer>();
	private int selecaoAno;
	private Integer ano;
    private List<Curso> listaCursos=new ArrayList<Curso>();
    private CursoDao cursoDao;


	public TurmaMB(){
		setTurmaAtual(new Turma());
		setturmas( new ArrayList<Turma>() );
		turma=new TurmaDaoImplementation();
	    cursoDao=new CursoDaoImplementation();
	    if(turmaAtual.getIDTurma()==0){
			turmaAtual.setIDTurma(turma.localizaIDTurma() + 1);
		}
	}

	public String inserir(){
		FacesContext context = FacesContext.getCurrentInstance();
		TurmaDao tDao=new TurmaDaoImplementation();

		boolean inserido = tDao.inserir(turmaAtual);
		if(inserido){
			limpaCampo();
			turmaAtual.setIDTurma(0);
			context.addMessage("formTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Turma cadastrada com sucesso", ""+""));
			turmaAtual.setIDTurma(turma.localizaIDTurma() + 1);

		}
		else{
			context.addMessage("formTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do turma", ""+""));
		}

		return "";
	}

	
	public void localizaIDTurma(){
		   if(turmaAtual.getIDTurma()==0){
				turmaAtual.setIDTurma(turma.localizaIDTurma() + 1);
			}
	}
	
	public String remover(){
		FacesContext context = FacesContext.getCurrentInstance();

		boolean removido = turma.remover(turmaAtual.getIDTurma());
		if(removido){
			limpaCampo();
			context.addMessage("formTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Turma excluída com sucesso", ""+""));
		}
		else{
			context.addMessage("formTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do turma", ""+""));
		}
		return "";
	}


	public String atualizar(){
		FacesContext context = FacesContext.getCurrentInstance();
		boolean atualizado = turma.atualizar(turmaAtual.getIDTurma(), turmaAtual);
		if(atualizado){
			limpaCampo();
			turmaAtual.setIDTurma(0);
			context.addMessage("formTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Turma atualizada com sucesso", ""+""));
		}
		else{
			context.addMessage("formTurmas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do turma", ""+""));
		}
		return "";
	}




	public String pesquisar(){
		TurmaDao aDao = new TurmaDaoImplementation();


		setTurmaAtual(aDao.pesquisar(turmaAtual));
		if(turmaAtual.getData_conselho()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
			turmaAtual.setPreparaData(sdf.format(turmaAtual.getData_conselho()));
			turmaAtual.setPreparaIDTurma(turmaAtual.getIDTurma());
		}
		else
			if(turmaAtual.getIDTurma()==0){
				limpaCampo();
			}

		return " ";
	}


	public String gerar() throws JRException{
		TurmaDao aDao = new TurmaDaoImplementation();
		aDao.preparaPDF();
		return " ";
	}


	public String correioEletronico(){
		return "./correioEletronico.jsf";
	}

	public Turma getturmaAtual() {
		return turmaAtual;
	}
	public void setTurmaAtual(Turma turmaAtual) {
		this.turmaAtual = turmaAtual;
	}
	public List<Turma> getturmas() {
		return turmas;
	}
	public void setturmas(List<Turma> turmas) {
		this.turmas = turmas;
	}




	public List<Integer> listaAnos(){
		Calendar cal = GregorianCalendar.getInstance(); 
		anos.clear();
		selecaoAno=cal.get(Calendar.YEAR); 

		ano = cal.get(Calendar.YEAR); 

		System.out.println("AQUI"+turmaAtual.getSemestre());
          
		if(turmaAtual.getSemestre()==1){
			ano=ano+1; 
		}

		for (int i=0;i<=2;i++){
			anos.add(ano);
			ano=ano-1;
		}

		return anos;
	}

	
	public List<Curso> listaCursos(){
		setListaCursos(cursoDao.listaCursos());
		return listaCursos;
	}



	public void limpaCampo(){
		turmaAtual.setNome("");
		turmaAtual.setAno(2013);
		turmaAtual.setPeriodo("");
		turmaAtual.setAulas_totais(0);
		turmaAtual.setCurso(0);
		turmaAtual.setSala("");
		turmaAtual.setDescricao("");
		turmaAtual.setData_conselho(new Date());
		turmaAtual.setPerfil("");

	}

	public List<Integer> getAnos() {
		return anos;
	}

	public void setAnos(List<Integer> anos) {
		this.anos = anos;
	}

	public int getSelecaoAno() {
		return selecaoAno;
	}

	public void setSelecaoAno(int selecaoAno) {
		this.selecaoAno = selecaoAno;
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



}
