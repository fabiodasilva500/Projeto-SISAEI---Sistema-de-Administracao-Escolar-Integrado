package ManagedBeans;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import Dao.ProfessorDao;
import Dao.ProfessorDaoImplementation;
import Pojo.Professor;


@RequestScoped
@ManagedBean(name="professorMB")
public class ProfessorMB {
private Professor professorAtual;
private List<Professor> professores;
private ProfessorDao professorDao;
private List<String> listaEstados=new ArrayList<String>();


public ProfessorMB(){
setProfessorAtual(new Professor());
setProfessores(new ArrayList<Professor>());
professorDao=new ProfessorDaoImplementation();
}


public String inserir(){
FacesContext context= FacesContext.getCurrentInstance();

retirarMascara();
boolean inserido=professorDao.inserir(professorAtual);
if(inserido){
limpaCampo();
professorAtual.setIDProfessor(0);
context.addMessage("formProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor inserido com sucesso", ""+""));
}
else{
context.addMessage("formProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Professor não inserido", ""+""));
}
return "";
}

public String atualizar(){
FacesContext context= FacesContext.getCurrentInstance();
retirarMascara();

boolean atualizado=professorDao.alterar(professorAtual.getIDProfessor(), professorAtual);

if(atualizado){
limpaCampo();
professorAtual.setIDProfessor(0);
context.addMessage("formProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor atualizado com sucesso", ""+""));
}
else{
context.addMessage("formProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Professor não atualizado", ""+""));
}
return "";
}


public String remover(){
FacesContext context= FacesContext.getCurrentInstance();

boolean excluido=professorDao.excluir(professorAtual.getIDProfessor());
if(excluido){
limpaCampo();
professorAtual.setIDProfessor(0);
context.addMessage("formProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor excluído com sucesso", ""+""));
}
else{
context.addMessage("formProfessor:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Professor não excluído", ""+""));
}
return "";
}


public String pesquisarPorNome(){
setProfessores(professorDao.pesquisarPorNome(professorAtual.getNome()));
return "./listaProfessores.jsf";
}



public String pesquisarPorIdentificacao(){
setProfessorAtual(professorDao.pesquisarIdentificacao(professorAtual.getIDProfessor()));
return "";
}



public String redireciona(){
return "./professorTurma.jsf";
}


public Professor getProfessorAtual() {
	return professorAtual;
}


public void setProfessorAtual(Professor professorAtual) {
	this.professorAtual = professorAtual;
}


public List<Professor> getProfessores() {
	return professores;
}


public void setProfessores(List<Professor> professores) {
	this.professores = professores;
}




public void limpaCampo(){
professorAtual.setNome("");
professorAtual.setRg("");
professorAtual.setCpf("");
professorAtual.setCep("");
professorAtual.setEndereco("");
professorAtual.setBairro("");
professorAtual.setNumero(0);
professorAtual.setCidade("");
professorAtual.setEstado("");
professorAtual.setTelefone("");
professorAtual.setCelular("");
professorAtual.setNacionalidade("");
professorAtual.setDataNascimento(new Date());
professorAtual.setNaturalidade("");
professorAtual.setEmail("");
professorAtual.setStatusAtual("Ativado");
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
professorAtual.setTelefone(professorAtual.getTelefone().replaceAll("-", ""));
professorAtual.setCelular(professorAtual.getCelular().replaceAll("-", ""));

professorAtual.setTelefone(professorAtual.getTelefone().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  
professorAtual.setCelular(professorAtual.getCelular().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  

professorAtual.setCep(professorAtual.getCep().replace("-", ""));
professorAtual.setRg(professorAtual.getRg().replaceAll("[ ./-]", ""));

professorAtual.setCpf(professorAtual.getCpf().replaceAll("[./-]", ""));
}



public List<String> getListaEstados() {
	return listaEstados;
}


public void setListaEstados(List<String> listaEstados) {
	this.listaEstados = listaEstados;
}



}
