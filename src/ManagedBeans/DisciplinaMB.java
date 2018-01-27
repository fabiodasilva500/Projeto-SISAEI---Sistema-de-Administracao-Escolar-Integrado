package ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import Dao.AreaConcentracaoDao;
import Dao.AreaConcentracaoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Pojo.AreaConcentracao;
import Pojo.Curso;
import Pojo.Disciplina;

@ManagedBean(name="disciplinaMB")
@ViewScoped

public class DisciplinaMB implements Serializable{
	private Disciplina disciplinaAtual;
	private DisciplinaDao disciplina;
	private List<Disciplina> disciplinas = new ArrayList<Disciplina>(); 
    private String selecao;
    private List<Curso> listaCursos=new ArrayList<Curso>();
    private CursoDao cursoDao;
    private AreaConcentracaoDao areaDao;
    private List<AreaConcentracao> listaAreas=new ArrayList<AreaConcentracao>();
    private int selecaoArea;
   

	public DisciplinaMB(){
		setDisciplinaAtual(new Disciplina());
		setDisciplinas( new ArrayList<Disciplina>() );
		disciplina=new DisciplinaDaoImplementation();
	    cursoDao=new CursoDaoImplementation();
	    areaDao=new AreaConcentracaoDaoImplementation();
	    if(disciplinaAtual.getIDDisciplina()==0){
	    disciplinaAtual.setIDDisciplina(disciplina.localizaIDDisciplina()+1);
	}
	}
	
	public void localizaIDDisciplina(){
		if(disciplinaAtual.getIDDisciplina()==0){
	    disciplinaAtual.setIDDisciplina(disciplina.localizaIDDisciplina()+1);
	}
	}

	public String inserir(){
		FacesContext context = FacesContext.getCurrentInstance();
		DisciplinaDao tDao=new DisciplinaDaoImplementation();
		

		boolean inserido = tDao.inserir(disciplinaAtual);
		if(inserido){
			limpaCampo();
			disciplinaAtual.setIDDisciplina(0);
			context.addMessage("formDisciplinas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Disciplina cadastrado com sucesso", ""+""));
		}
		else{
			context.addMessage("formDisciplinas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do disciplina", ""+""));
		}

		return "";
	}

	public String remover(){
		FacesContext context = FacesContext.getCurrentInstance();

		boolean removido = disciplina.remover(disciplinaAtual.getIDDisciplina());
		if(removido){
			disciplinaAtual.setIDDisciplina(0);
			limpaCampo();
			context.addMessage("formDisciplinas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Disciplina excluída com sucesso", ""+""));
		}
		else{
			context.addMessage("formDisciplinas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do disciplina", ""+""));
		}
		return "";
	}


	public String atualizar(){
		FacesContext context = FacesContext.getCurrentInstance();	

		
		boolean atualizado = disciplina.atualizar(disciplinaAtual.getIDDisciplina(), disciplinaAtual);

	
		if(atualizado){
			disciplinaAtual.setIDDisciplina(0);
			limpaCampo();
			context.addMessage("formDisciplinas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Disciplina atualizada com sucesso", ""+""));
		}
		else{
			context.addMessage("formDisciplinas:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do disciplina", ""+""));
		}
		return "";
	}



	public String pesquisarPorNome(){
		DisciplinaDao aDao = new DisciplinaDaoImplementation();
		setDisciplinaAtual(aDao.pesquisar(disciplinaAtual));
		setSelecao(String.valueOf(disciplinaAtual.getModulo()));
		return " ";
	}

	public Disciplina getdisciplinaAtual() {
		return disciplinaAtual;
	}
	public void setDisciplinaAtual(Disciplina disciplinaAtual) {
		this.disciplinaAtual = disciplinaAtual;
	}
	public List<Disciplina> getdisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}



	public void limpaCampo(){
		disciplinaAtual.setNome("");
		disciplinaAtual.setSigla("");
		disciplinaAtual.setDescricao("");
		disciplinaAtual.setAta("");
		disciplinaAtual.setCurso(0);
		disciplinaAtual.setIDAreaConcentracao(0);
	   }
	


	public String getSelecao() {
		return selecao;
	}

	public void setSelecao(String selecao) {
		this.selecao = selecao;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}
	
	public List<Curso> listaCursos() {
		setListaCursos(cursoDao.listaCursos());
		return listaCursos;
	}

	public int getSelecaoArea() {
		return selecaoArea;
	}

	public void setSelecaoArea(int selecaoArea) {
		this.selecaoArea = selecaoArea;
	}
	
	public List<AreaConcentracao> listaAreas(){
	setListaAreas(areaDao.listaAreas(disciplinaAtual.getCurso()));
	return listaAreas;
	}

	public List<AreaConcentracao> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List<AreaConcentracao> listaAreas) {
		this.listaAreas = listaAreas;
	}


}
