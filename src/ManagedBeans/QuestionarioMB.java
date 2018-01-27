package ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.QuestionarioDao;
import Dao.QuestionarioDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Questionario;
import Pojo.Turma;

@SessionScoped
@ManagedBean(name="questionarioMB")
public class QuestionarioMB {
private String questao;
private String resposta;
private Turma turma;

private List<String> questoes;
private List<String> respostas;
private List<String> listaDisciplinas;
private Questionario questionario;

private QuestionarioDao qDao;
private TurmaDao tDao;
private DisciplinaDao disciplinaDao;


public QuestionarioMB(){
qDao=new QuestionarioDaoImplementation();
tDao=new TurmaDaoImplementation();
disciplinaDao = new DisciplinaDaoImplementation();
setQuestoes(new ArrayList<String>());
setRespostas(new ArrayList<String>());
setQuestionario(new Questionario());
setListaDisciplinas(new ArrayList<String>());
turma=new Turma();
removeItens();
}

public void inicializaQuestoes(String questao){
System.out.println("Questão:"+questao);
System.out.println("Resposta:"+resposta);
boolean encontrado=eliminaRepeticao(questao);
if(encontrado==false){
questoes.add(questao);
respostas.add(resposta);
}
}

public boolean eliminaRepeticao(String questao){
boolean encontrado=false;
for (int i=0;i<questoes.size();i++){
if(encontrado==false){
if(questoes.get(i).equalsIgnoreCase(questao)){
questoes.remove(i);	
questoes.set(i, questao);
respostas.set(i, resposta);
encontrado=true;
System.out.println("Removido");
}
}
}
verificaRespostas();
return encontrado;
}

public void verificaRespostas(){
for (int i=0;i<questoes.size();i++){
System.out.println(questoes.get(i));
System.out.println(respostas.get(i));
}
}

public String inserir(String identificacao){
boolean inserido=false;
FacesContext context= FacesContext.getCurrentInstance();

setTurma(tDao.localizaTurmaAluno(identificacao));

	
for (int i=0;i<questoes.size();i++){
questionario.setQuestao(questoes.get(i));
questionario.setResposta(respostas.get(i));
questionario.setIdentificacaoAluno(identificacao);
questionario.setIDTurma(turma.getIDTurma());
inserido = qDao.inserir(questionario);
resposta="";
questao="";
}

if(inserido){
context.addMessage("formQuestionario:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Caro(a) estudante o questionário foi respondido com sucesso.", ""+""));
questoes.clear();
respostas.clear();
}
else{
context.addMessage("formQuestionario:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Caro(a) estudante o questionário já foi respondido neste semestre letivo.", ""+""));
}
return "";
}

public String getQuestao() {
	return questao;
}

public void setQuestao(String questao) {
	this.questao = questao;
}

public String getResposta() {
	return resposta;
}

public void setResposta(String resposta) {
	this.resposta = resposta;
}

public List<String> getQuestoes() {
	return questoes;
}

public void setQuestoes(List<String> questoes) {
	this.questoes = questoes;
}

public List<String> getRespostas() {
	return respostas;
}

public void setRespostas(List<String> respostas) {
	this.respostas = respostas;
}

public Turma getTurma() {
	return turma;
}

public void setTurma(Turma turma) {
	this.turma = turma;
}

public Questionario getQuestionario() {
	return questionario;
}

public void setQuestionario(Questionario questionario) {
	this.questionario = questionario;
}



   public List<String> listaDisciplinas(String identificacao){
	System.out.println("Chegou:"+identificacao);
	setTurma(tDao.localizaTurmaAluno(identificacao));
	System.out.println("Curso:"+turma.getCurso());
	System.out.println("Módulo:"+turma.getModulo());
	setListaDisciplinas(disciplinaDao.localizaDisciplinas(turma.getCurso(), turma.getModulo()));
	System.out.println("Tamanho da lista:"+listaDisciplinas.size());
	return listaDisciplinas;
   }

public List<String> getListaDisciplinas() {
	return listaDisciplinas;
}

public void setListaDisciplinas(List<String> listaDisciplinas) {
	this.listaDisciplinas = listaDisciplinas;
}


  public void removeItens(){
  respostas.clear();
  listaDisciplinas.clear();
  }



}
