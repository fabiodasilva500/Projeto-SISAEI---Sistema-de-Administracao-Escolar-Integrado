package ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import Dao.AreaConcentracaoDao;
import Dao.AreaConcentracaoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Pojo.AreaConcentracao;
import Pojo.Curso;

@ManagedBean (name="areaConcentracaoMB")
@RequestScoped
public class AreaConcentracaoMB {
private AreaConcentracao areaAtual;
private int selecaoCurso;
private List<AreaConcentracao> listaAreas;
private AreaConcentracaoDao areaDao;
private List<Curso> listaCursos;
private CursoDao cursoDao;

public AreaConcentracaoMB(){
areaAtual= new AreaConcentracao();
listaCursos = new ArrayList<Curso>();
listaAreas = new ArrayList<AreaConcentracao>();
cursoDao = new CursoDaoImplementation();
areaDao = new AreaConcentracaoDaoImplementation(); 
listaCursos();
}

public List<Curso> listaCursos(){
setListaCursos(cursoDao.listaCursos());
return listaCursos;
}

public String inserir(){
FacesContext context = FacesContext.getCurrentInstance();
	   
boolean inserido = areaDao.inserir(selecaoCurso, areaAtual);
if(inserido){
context.addMessage("formAreaConcentracao:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Area inserida com sucesso", ""+""));
}
else{
context.addMessage("formAreaConcentracao:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Area não inserida", ""+""));
}
return "";	
}

public String pesquisarPorNome(){
setAreaAtual(areaDao.pesquisar(selecaoCurso, areaAtual.getNome()));

return "";
}

public String alterar(){
FacesContext context = FacesContext.getCurrentInstance();

boolean alterado = areaDao.alterar(selecaoCurso, areaAtual);
if(alterado){
context.addMessage("formAreaConcentracao:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Area alterada com sucesso", ""+""));
}
else{
context.addMessage("formAreaConcentracao:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Area não alterada", ""+""));
}
areaDao.alterar(selecaoCurso, areaAtual);
return "";
}

public String remover(){
FacesContext context = FacesContext.getCurrentInstance();
boolean excluido = areaDao.excluir(areaAtual.getNome());
if(excluido){
context.addMessage("formAreaConcentracao:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Area excluída com sucesso.", ""+""));
}
else{
context.addMessage("formAreaConcentracao:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Area não excluída", ""+""));
}
return "";
}

public AreaConcentracao getArea() {
	return areaAtual;
}

public void setArea(AreaConcentracao areaAtual) {
	this.areaAtual = areaAtual;
}

public List<AreaConcentracao> getListaAreas() {
	return listaAreas;
}

public void setListaAreas(List<AreaConcentracao> listaAreas) {
	this.listaAreas = listaAreas;
}

public List<Curso> getListaCursos() {
	return listaCursos;
}

public void setListaCursos(List<Curso> listaCursos) {
	this.listaCursos = listaCursos;
}

public AreaConcentracao getAreaAtual() {
	return areaAtual;
}

public void setAreaAtual(AreaConcentracao areaAtual) {
	this.areaAtual = areaAtual;
}

public int getSelecaoCurso() {
	return selecaoCurso;
}

public void setSelecaoCurso(int selecaoCurso) {
	this.selecaoCurso = selecaoCurso;
}






}
