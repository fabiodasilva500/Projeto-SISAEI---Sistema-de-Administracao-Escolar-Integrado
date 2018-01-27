package ManagedBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Pojo.Curso;


@ViewScoped
@ManagedBean(name="cursoMB")
public class CursoMB {

	Curso cursoAtual;
	List<Curso> lista;

	private CursoDao curso;



	public CursoDao getCurso() {
		return curso;
	}

	public void setCurso(CursoDao curso) {
		this.curso = curso;
	}

	public CursoMB() {
		setCursoAtual(new Curso());
		setLista(new ArrayList<Curso>());
		curso = new CursoDaoImplementation();
		if(cursoAtual.getId()==0){
			cursoAtual.setId(curso.localizaIDCurso()+1);
		
	}
	}
	
	
	public void localizaIDCurso(){
		if(cursoAtual.getId()==0){
			cursoAtual.setId(curso.localizaIDCurso()+1);
		
	}
	}
		

	public List<Curso> getLista() {
		return lista;
	}

	public void setLista(List<Curso> lista) {
		this.lista = lista;
	}

	public Curso getCursoAtual() {
		return cursoAtual;
	}

	public void setCursoAtual(Curso cursoAtual) {
		this.cursoAtual = cursoAtual;
	}

	public String inserir() throws SQLException {
		   FacesContext context = FacesContext.getCurrentInstance();
			boolean inserido= curso.inserir(cursoAtual);
			if(inserido){
			context.addMessage("formCurso:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso inserido com sucesso", ""+""));
			cursoAtual.setId(cursoAtual.getId()+1);
			limpaCampo();
			}
			else{
			context.addMessage("formCurso:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso não inserido com sucesso", ""+""));
			}
		return "";
	}
	
	
	public String pesquisarPorNome() throws SQLException {
		setCursoAtual(curso.pesquisarPorNome(cursoAtual.getNome()));
		return "";
	}
	
	public String atualizar() throws SQLException{
    FacesContext context = FacesContext.getCurrentInstance();

	boolean atualizado = curso.atualizar(cursoAtual.getId(), cursoAtual);
	if(atualizado){
    context.addMessage("formCurso:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso atualizado com sucesso", ""+""));
	limpaCampo();
	cursoAtual.setId(curso.localizaIDCurso()+1);
	}
	else{
	context.addMessage("formCurso:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso não atualizado", ""+""));
	}
	return "";
	}
	
	

	public String remover() throws SQLException {
	FacesContext context = FacesContext.getCurrentInstance();
 
    boolean removido =  curso.remover(cursoAtual.getId());
	if(removido){
    context.addMessage("formCurso:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso removido com sucesso", ""+""));
    limpaCampo();
	}
	else{
	context.addMessage("formCurso:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso não removido", ""+""));
	}   
		return "";
	}
	
	
	public String redireciona(){
	return "./areaConcentracao.jsf";
	}
	
	
	
	
	public void limpaCampo(){
	cursoAtual.setId(0);
    cursoAtual.setNome("");
    cursoAtual.setDescricao("");
    cursoAtual.setSigla("");
    cursoAtual.setTipo("");
    cursoAtual.setEixoTecnologico("");
    cursoAtual.setCorArea("");
    cursoAtual.setCorCurso("");
    
	}
}
