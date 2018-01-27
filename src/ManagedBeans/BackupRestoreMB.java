package ManagedBeans;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import Dao.BD;
import Dao.BackupRestoreDao;
import Dao.BackupRestoreDaoImplementation;

@ManagedBean(name="backupRestoreMB")
@ViewScoped
public class BackupRestoreMB {
private String diretorio;
private String database;
private String resposta;
private String caminho;
private boolean habilitaRestore;
private BackupRestoreDao br;
private boolean habilitaDiretorio;
private String caminhoSelecionado;
private UploadedFile arquivo;  


public BackupRestoreMB(){
database = "projetoLabEng";
br=new BackupRestoreDaoImplementation();
}

public String verificaRequisicao() throws IOException{
if(habilitaRestore){
efetuarRestore();
return "./acesso.jsf";
}
else{
efetuarBackup();
return "./backupRestore.jsf";
}
}


public void efetuarBackup(){
FacesContext context=FacesContext.getCurrentInstance();
diretorio = "C:/Program Files (x86)/Microsoft SQL Server/MSSQL10.SQLEXPRESS/MSSQL/Backup/";
 
boolean backup = br.efetuarBackupSimples(database, diretorio);
if(backup){
context.addMessage("formBackupRestore:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Backup efetuado com sucesso no diretório:\n"+diretorio, ""+""));
}
else{
context.addMessage("formBackupRestore:mensagem", new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro na realização do backup.", ""+""));
}
	
}



public void uploadListener(FileUploadEvent event) throws IOException {  
	  try {
           database = event.getFile().getFileName();  
   }
	  catch(Exception e){
		 e.printStackTrace();
	  }
}

public void efetuarRestore() throws IOException{
AcessoMB a=new AcessoMB();
a.finalizarSessao();

FacesContext context=FacesContext.getCurrentInstance();

diretorio = "C:/Program Files (x86)/Microsoft SQL Server/MSSQL10.SQLEXPRESS/MSSQL/Backup/";

boolean efetuado = br.efetuarRestoreSimples(database, diretorio);
if(efetuado){
context.addMessage("formBackupRestore:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Restauração do banco de dados efetuada com sucesso.", ""+""));
}
else{
context.addMessage("formBackupRestore:mensagem", new FacesMessage(FacesMessage.SEVERITY_WARN, "Restauração não efetuada arquivo", ""+""));
}
}


public String getDiretorio() {
	return diretorio;
}

public void setDiretorio(String diretorio) {
	this.diretorio = diretorio;
}

public String getDatabase() {
	return database;
}

public void setDatabase(String database) {
	this.database = database;
}

public String getResposta() {
	return resposta;
}

public void setResposta(String resposta) {
	this.resposta = resposta;
}


public String getCaminho() {
	return caminho;
}


public void setCaminho(String caminho) {
	this.caminho = caminho;
}




public boolean isHabilitaDiretorio() {
	return habilitaDiretorio;
}


public void setHabilitaDiretorio(boolean habilitaDiretorio) {
	this.habilitaDiretorio = habilitaDiretorio;
}

public boolean isHabilitaRestore() {
	return habilitaRestore;
}

public void setHabilitaRestore(boolean habilitaRestore) {
	this.habilitaRestore = habilitaRestore;
}

public String getCaminhoSelecionado() {
	return caminhoSelecionado;
}

public void setCaminhoSelecionado(String caminhoSelecionado) {
	this.caminhoSelecionado = caminhoSelecionado;
}

public UploadedFile getArquivo() {
	return arquivo;
}

public void setArquivo(UploadedFile arquivo) {
	this.arquivo = arquivo;
}

}
