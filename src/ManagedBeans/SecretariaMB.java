package ManagedBeans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import Dao.SecretariaDao;
import Dao.SecretariaDaoImplementation;
import Pojo.Secretaria;


@RequestScoped
@ManagedBean(name="secretariaMB")
public class SecretariaMB {
private Secretaria secretariaAtual;
private SecretariaDao secretariaDao;
private List<String> listaEstados=new ArrayList<String>();


public SecretariaMB(){
setSecretariaAtual(new Secretaria());
secretariaDao=new SecretariaDaoImplementation();
}


public String inserir(){
FacesContext context= FacesContext.getCurrentInstance();

retirarMascara();
boolean inserido=secretariaDao.inserir(secretariaAtual);
if(inserido){
limpaCampo();
context.addMessage("formSecretaria:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso", ""+""));
}
else{
context.addMessage("formSecretaria:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cadastro não realizado", ""+""));
}
return "";
}

public String atualizar(){
FacesContext context= FacesContext.getCurrentInstance();
retirarMascara();

boolean atualizado=secretariaDao.alterar(secretariaAtual.getCpf(), secretariaAtual);

if(atualizado){
limpaCampo();
context.addMessage("formSecretarias:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados atualizados com sucesso", ""+""));
}
else{
context.addMessage("formSecretaria:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados não atualizados", ""+""));
}
return "";
}


public String remover(){
FacesContext context= FacesContext.getCurrentInstance();

retirarMascara();

boolean excluido=secretariaDao.excluir(secretariaAtual.getCpf());
if(excluido){
limpaCampo();
context.addMessage("formSecretaria:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados excluídos com sucesso", ""+""));
}
else{
context.addMessage("formSecretaria:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados não excluídos", ""+""));
}
return "";
}


public void pesquisar(){
setSecretariaAtual(secretariaDao.pesquisarPorNome(secretariaAtual.getNome()));
}




public Secretaria getSecretariaAtual() {
	return secretariaAtual;
}


public void setSecretariaAtual(Secretaria secretaria) {
	this.secretariaAtual = secretaria;
}






public void limpaCampo(){
secretariaAtual.setNome("");
secretariaAtual.setRg("");
secretariaAtual.setCpf("");
secretariaAtual.setCep("");
secretariaAtual.setEndereco("");
secretariaAtual.setBairro("");
secretariaAtual.setNumero(0);
secretariaAtual.setCidade("");
secretariaAtual.setEstado("");
secretariaAtual.setTelefone("");
secretariaAtual.setCelular("");
secretariaAtual.setNacionalidade("");
secretariaAtual.setDataNascimento(new Date());
secretariaAtual.setNaturalidade("");
secretariaAtual.setEmail("");
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
secretariaAtual.setTelefone(secretariaAtual.getTelefone().replaceAll("-", ""));
secretariaAtual.setCelular(secretariaAtual.getCelular().replaceAll("-", ""));

secretariaAtual.setTelefone(secretariaAtual.getTelefone().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  
secretariaAtual.setCelular(secretariaAtual.getCelular().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  

secretariaAtual.setCep(secretariaAtual.getCep().replace("-", ""));
secretariaAtual.setRg(secretariaAtual.getRg().replaceAll("[ ./-]", ""));
secretariaAtual.setCpf(secretariaAtual.getCpf().replaceAll("[./-]", ""));
}

public List<String> getListaEstados() {
	return listaEstados;
}


public void setListaEstados(List<String> listaEstados) {
	this.listaEstados = listaEstados;
}



}
