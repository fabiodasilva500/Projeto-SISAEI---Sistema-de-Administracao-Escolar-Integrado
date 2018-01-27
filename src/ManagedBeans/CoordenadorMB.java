package ManagedBeans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import Dao.CoordenadorDao;
import Dao.CoordenadorDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Pojo.Coordenador;
import Pojo.Curso;


@RequestScoped
@ManagedBean(name="coordenadorMB")
public class CoordenadorMB {
private Coordenador coordenadorAtual;
private CoordenadorDao CoordenadorDao;
private List<String> listaEstados=new ArrayList<String>();
private List<Curso> listaCursos=new ArrayList<Curso>();
private CursoDao cursoDao;


public CoordenadorMB(){
setCoordenadorAtual(new Coordenador());
CoordenadorDao=new CoordenadorDaoImplementation();
setListaCursos(new ArrayList<Curso>());
cursoDao=new CursoDaoImplementation();
}


public String inserir(){
FacesContext context= FacesContext.getCurrentInstance();

retirarMascara();
boolean inserido=CoordenadorDao.inserir(coordenadorAtual);
if(inserido){
limpaCampo();
context.addMessage("formCoordenador:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Coordenador cadastrado com sucesso", ""+""));
}
else{
context.addMessage("formCoordenador:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Coordenador não cadastrado", ""+""));
}
return "";
}

public String atualizar(){
FacesContext context= FacesContext.getCurrentInstance();
retirarMascara();

boolean atualizado=CoordenadorDao.alterar(coordenadorAtual.getCpf(), coordenadorAtual);

if(atualizado){
limpaCampo();
context.addMessage("formCoordenadors:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados atualizados com sucesso", ""+""));
}
else{
context.addMessage("formCoordenador:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados não atualizados", ""+""));
}
return "";
}


public String remover(){
FacesContext context= FacesContext.getCurrentInstance();

boolean excluido=CoordenadorDao.excluir(coordenadorAtual.getCpf());
if(excluido){
limpaCampo();
context.addMessage("formCoordenador:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Coordenador excluído com sucesso", ""+""));
}
else{
context.addMessage("formCoordenador:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Coordenador não excluído", ""+""));
}
return "";
}


public void pesquisar(){
setCoordenadorAtual(CoordenadorDao.pesquisarPorNome(coordenadorAtual.getNome()));
}


public List<Curso> listaCursos(){
setListaCursos(cursoDao.listaCursos());
return listaCursos;
}





public Coordenador getCoordenadorAtual() {
	return coordenadorAtual;
}


public void setCoordenadorAtual(Coordenador Coordenador) {
	this.coordenadorAtual = Coordenador;
}






public void limpaCampo(){
	coordenadorAtual.setNome("");
	coordenadorAtual.setRg("");
	coordenadorAtual.setCpf("");
	coordenadorAtual.setCep("");
	coordenadorAtual.setEndereco("");
	coordenadorAtual.setBairro("");
	coordenadorAtual.setNumero(0);
	coordenadorAtual.setCidade("");
	coordenadorAtual.setEstado("");
	coordenadorAtual.setTelefone("");
	coordenadorAtual.setCelular("");
	coordenadorAtual.setNacionalidade("");
	coordenadorAtual.setDataNascimento(new Date());
	coordenadorAtual.setNaturalidade("");
	coordenadorAtual.setEmail("");
}


public List<String> listaEstados(){
    listaEstados.add("São Paulo");
	listaEstados.add("Acre");
	listaEstados.add("Alagoas");
	listaEstados.add("Amapá");
	listaEstados.add("Amazonas");
	listaEstados.add("Bahia");
	listaEstados.add("Ceará");
	listaEstados.add("Distrito Federal");
	listaEstados.add("Espírito Santo");
	listaEstados.add("Goiás");
	listaEstados.add("Maranhão");
	listaEstados.add("Mato Grosso");
	listaEstados.add("Mato Grosso do Sul");
	listaEstados.add("Minas Gerais");
	listaEstados.add("Pará");
	listaEstados.add("Paraíba");
	listaEstados.add("Paraná");
	listaEstados.add("Pernambuco");
	listaEstados.add("Piauí");
	listaEstados.add("Rio de Janeiro");
	listaEstados.add("Rio Grande do Norte");
	listaEstados.add("Rio Grande do Sul");
	listaEstados.add("Rondônia");
	listaEstados.add("Roraima");
	listaEstados.add("Santa Catarina");
	listaEstados.add("Sergipe");
	listaEstados.add("Tocantins");
	return listaEstados;
	}


public void retirarMascara()
{
	coordenadorAtual.setTelefone(coordenadorAtual.getTelefone().replaceAll("-", ""));
	coordenadorAtual.setCelular(coordenadorAtual.getCelular().replaceAll("-", ""));

	coordenadorAtual.setTelefone(coordenadorAtual.getTelefone().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  
	coordenadorAtual.setCelular(coordenadorAtual.getCelular().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  

	coordenadorAtual.setCep(coordenadorAtual.getCep().replace("-", ""));
	coordenadorAtual.setRg(coordenadorAtual.getRg().replaceAll("[ ./-]", ""));
	coordenadorAtual.setCpf(coordenadorAtual.getCpf().replaceAll("[./-]", ""));
}

public List<String> getListaEstados() {
	return listaEstados;
}


public void setListaEstados(List<String> listaEstados) {
	this.listaEstados = listaEstados;
}


public List<Curso> getListaCursos() {
	return listaCursos;
}


public void setListaCursos(List<Curso> listaCursos) {
	this.listaCursos = listaCursos;
}



}
