package ManagedBeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.VagaDao;
import Dao.VagaDaoImplementation;
import Pojo.Curso;
import Pojo.Vaga;

@ManagedBean(name="vagaMB")
@SessionScoped
public class VagaMB {
private Vaga vagaAtual;
private List<Vaga> listaVagas;
private List<Curso> listaCursos;
private VagaDao vagaDao;
private CursoDao cursoDao;
private String requisicao;
private boolean habilitaInsercao;
private boolean habilitaEmpresa;
private boolean habilitaData;
private Vaga vagaSelecionada;



public VagaMB(){
setVagaAtual(new Vaga());
setListaVagas(new ArrayList<Vaga>());
setListaCursos(new ArrayList<Curso>());
habilitaEmpresa=false;
habilitaData=false;
habilitaInsercao=true;
requisicao="novaOportunidade";
if(vagaAtual.getIDOportunidade()==0){
vagaAtual.setIDOportunidade(localizaIDDisciplina());
}
}


public String inserir(){
FacesContext context = FacesContext.getCurrentInstance();
vagaDao =new VagaDaoImplementation();
boolean inserido = vagaDao.inserir(vagaAtual);
if(inserido){
context.addMessage("formVagas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaga cadastrada com sucesso.", ""+""));
limpaCampo();
}
else{
context.addMessage("formVagas:mensagem", new FacesMessage(FacesMessage.SEVERITY_WARN, "Vaga não cadastrada.", ""+""));
}

return "";
}


public String remover(int IDVaga){
FacesContext context = FacesContext.getCurrentInstance();
vagaDao =new VagaDaoImplementation();
System.out.println("ID:"+IDVaga);
boolean excluido = vagaDao.remover(IDVaga);
setVagaAtual(new Vaga());
if(excluido){
context.addMessage("formVagas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaga excluída com sucesso.", ""+""));
limpaCampo();
}
else{
context.addMessage("formVagas:mensagem", new FacesMessage(FacesMessage.SEVERITY_WARN, "Vaga não excluída.", ""+""));
}

return "";
}


public String atualizar(){
FacesContext context = FacesContext.getCurrentInstance();
vagaDao =new VagaDaoImplementation();
boolean inserido = vagaDao.atualizar(vagaAtual);
if(inserido){
context.addMessage("formVagas:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaga atualizada com sucesso.", ""+""));
limpaCampo();
}
else{
context.addMessage("formVagas:mensagem", new FacesMessage(FacesMessage.SEVERITY_WARN, "Vaga não atualizada.", ""+""));
}

return "";
}






public String redirecionaPesquisaEmpresa() throws ParseException{
pesquisarEmpresa();
return "./localizaVagas.jsf";
}

public List<Vaga> pesquisarEmpresa() throws ParseException{
setListaVagas(vagaDao.pesquisarPorEmpresa(vagaAtual.getNomeEmpresa(), vagaAtual.getDataInicial(), vagaAtual.getDataFinal()));
System.out.println("Total:"+listaVagas.size());

return listaVagas;
}

public List<Vaga> pesquisarData() throws ParseException{
setListaVagas(vagaDao.pesquisarPorData(vagaAtual.getDataInicial(), vagaAtual.getDataFinal()));
System.out.println("Total:"+listaVagas.size());

return listaVagas;
}

public List<Curso> listaCursos() {
	cursoDao = new CursoDaoImplementation();
	setListaCursos(cursoDao.listaCursos());
	return listaCursos;
}



public int localizaIDDisciplina(){
vagaDao=new VagaDaoImplementation();
vagaAtual.setIDOportunidade(vagaDao.localizaIDVaga()+1);
return vagaAtual.getIDOportunidade();
}

public Vaga getVagaAtual() {
	return vagaAtual;
}


public void setVagaAtual(Vaga vagaAtual) {
	this.vagaAtual = vagaAtual;
}


public List<Vaga> getListaVagas() {
	return listaVagas;
}


public void setListaVagas(List<Vaga> listaVagas) {
	this.listaVagas = listaVagas;
}




public VagaDao getVagaDao() {
	return vagaDao;
}


public void setVagaDao(VagaDao vagaDao) {
	this.vagaDao = vagaDao;
}


public List<Curso> getListaCursos() {
	return listaCursos;
}


public void setListaCursos(List<Curso> listaCursos) {
	this.listaCursos = listaCursos;
}


public String redireciona(){
return "./localizaVagas.jsf";
}


public void verificaSelecao(){
if(requisicao.equalsIgnoreCase("novaOportunidade")){
habilitaInsercao=true;
habilitaEmpresa=false;
habilitaData=false;
}
else
if(requisicao.equalsIgnoreCase("empresa")){
habilitaEmpresa=true;
habilitaInsercao=false;
habilitaData=false;
}
else{
habilitaData=true;
habilitaInsercao=false;
habilitaEmpresa=false;
}
}


public void limpaCampo(){
vagaAtual.setNomeEmpresa("");
vagaAtual.setDescricao("");
vagaAtual.setRequisitos("");
vagaAtual.setDiferenciais("");
vagaAtual.setEmail("");
vagaAtual.setDataInicial(new Date());
vagaAtual.setDataFinal(new Date());

}


public String getRequisicao() {
	return requisicao;
}


public void setRequisicao(String requisicao) {
	this.requisicao = requisicao;
}


public boolean isHabilitaEmpresa() {
	return habilitaEmpresa;
}


public void setHabilitaEmpresa(boolean habilitaEmpresa) {
	this.habilitaEmpresa = habilitaEmpresa;
}


public boolean isHabilitaData() {
	return habilitaData;
}


public void setHabilitaData(boolean habilitaData) {
	this.habilitaData = habilitaData;
}


public boolean isHabilitaInsercao() {
	return habilitaInsercao;
}


public void setHabilitaInsercao(boolean habilitaInsercao) {
	this.habilitaInsercao = habilitaInsercao;
}


public Vaga getVagaSelecionada() {
	return vagaSelecionada;
}


public void setVagaSelecionada(Vaga vagaSelecionada) {
	this.vagaSelecionada = vagaSelecionada;
}


public Vaga atualizaSelecionado(){
setVagaAtual(vagaSelecionada);
return vagaAtual;
}


}
