package ManagedBeans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import Dao.DiretoriaDao;
import Dao.DiretoriaDaoImplementation;
import Pojo.Diretoria;


@RequestScoped
@ManagedBean(name="diretoriaMB")
public class DiretoriaMB {
private Diretoria diretoriaAtual;
private DiretoriaDao diretoriaDao;
private List<String> listaEstados=new ArrayList<String>();


public DiretoriaMB(){
setdiretoriaAtual(new Diretoria());
diretoriaDao=new DiretoriaDaoImplementation();
}


public String inserir(){
FacesContext context= FacesContext.getCurrentInstance();

retirarMascara();
boolean inserido=diretoriaDao.inserir(diretoriaAtual);
if(inserido){
limpaCampo();
context.addMessage("formDiretoria:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso", ""+""));
}
else{
context.addMessage("formDiretoria:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cadastro não realizado", ""+""));
}
return "";
}

public String atualizar(){
FacesContext context= FacesContext.getCurrentInstance();
retirarMascara();

boolean atualizado=diretoriaDao.alterar(diretoriaAtual.getCpf(), diretoriaAtual);

if(atualizado){
limpaCampo();
context.addMessage("formDiretorias:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados atualizados com sucesso", ""+""));
}
else{
context.addMessage("formDiretoria:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados não atualizados", ""+""));
}
return "";
}


public String remover(){
FacesContext context= FacesContext.getCurrentInstance();

retirarMascara();

boolean excluido=diretoriaDao.excluir(diretoriaAtual.getCpf());
if(excluido){
limpaCampo();
context.addMessage("formDiretoria:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados excluídos com sucesso", ""+""));
}
else{
context.addMessage("formDiretoria:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados não excluídos", ""+""));
}
return "";
}


public void pesquisar(){
setdiretoriaAtual(diretoriaDao.pesquisarPorNome(diretoriaAtual.getNome()));
}




public Diretoria getdiretoriaAtual() {
	return diretoriaAtual;
}


public void setdiretoriaAtual(Diretoria Diretoria) {
	this.diretoriaAtual = Diretoria;
}






public void limpaCampo(){
diretoriaAtual.setNome("");
diretoriaAtual.setRg("");
diretoriaAtual.setCpf("");
diretoriaAtual.setCep("");
diretoriaAtual.setEndereco("");
diretoriaAtual.setBairro("");
diretoriaAtual.setNumero(0);
diretoriaAtual.setCidade("");
diretoriaAtual.setEstado("");
diretoriaAtual.setTelefone("");
diretoriaAtual.setCelular("");
diretoriaAtual.setNacionalidade("");
diretoriaAtual.setDataNascimento(new Date());
diretoriaAtual.setNaturalidade("");
diretoriaAtual.setEmail("");
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
diretoriaAtual.setTelefone(diretoriaAtual.getTelefone().replaceAll("-", ""));
diretoriaAtual.setCelular(diretoriaAtual.getCelular().replaceAll("-", ""));

diretoriaAtual.setTelefone(diretoriaAtual.getTelefone().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  
diretoriaAtual.setCelular(diretoriaAtual.getCelular().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  

diretoriaAtual.setCep(diretoriaAtual.getCep().replace("-", ""));
diretoriaAtual.setRg(diretoriaAtual.getRg().replaceAll("[ ./-]", ""));
diretoriaAtual.setCpf(diretoriaAtual.getCpf().replaceAll("[./-]", ""));
}

public List<String> getListaEstados() {
	return listaEstados;
}


public void setListaEstados(List<String> listaEstados) {
	this.listaEstados = listaEstados;
}



}
