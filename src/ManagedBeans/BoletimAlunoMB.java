package ManagedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import Dao.BoletimDao;
import Dao.BoletimDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Curso;
import Pojo.Turma;


@SessionScoped
@ManagedBean(name="boletimAlunoMB")
public class BoletimAlunoMB {
private Turma turma;
private Curso curso;
private CursoDao cursoDao;
private TurmaDao tDao;
private BoletimDao bDao;
private int IDTurma;
private String requisicao;
private String identificacaoAluno;


public BoletimAlunoMB(){
tDao=new TurmaDaoImplementation();
turma=new Turma();
curso=new Curso();
cursoDao=new CursoDaoImplementation();
bDao=new BoletimDaoImplementation();
}



public void gerarBoletim(String identificacao) throws JRException{
FacesContext context=FacesContext.getCurrentInstance();
identificacaoAluno=identificacao;
System.out.println("Identificação Informada:");
setTurma(tDao.localizaTurmaAluno(identificacao));
setCurso(cursoDao.localizaCursoAluno(identificacao, turma.getIDTurma()));

System.out.println(turma.getModulo());
if(requisicao.equalsIgnoreCase("parcial")){
System.out.println("Módulo localizado:"+turma.getModulo());
boolean localizado=bDao.verificaMencaoParcial(identificacao, turma.getIDTurma(), turma.getModulo(), curso.getId());
if(localizado){
gerarBoletimParcial();
}
else{
context.addMessage("formVerificaBoletimAluno:mensagem", new FacesMessage(
FacesMessage.SEVERITY_ERROR, "Nem todas as suas menções parciais foram digitadas no sistema, por favor aguarde",
"" + ""));}
}
else
if(requisicao.equalsIgnoreCase("final")){
boolean localizado=bDao.verificaMencaoFinal(identificacao, turma.getIDTurma(), turma.getModulo(), curso.getId());
if(localizado){
gerarBoletimFinal();
}
else{
context.addMessage("formVerificaBoletimAluno:mensagem", new FacesMessage(
FacesMessage.SEVERITY_ERROR, "Nem todas as suas menções finais foram digitadas no sistema, por favor aguarde",
"" + ""));
}
}
else
if(requisicao.equalsIgnoreCase("geral")){
boolean localizado=bDao.verificaMencaoFinal(identificacao, turma.getIDTurma(), turma.getModulo(), curso.getId());
if(localizado){
gerarAcompanhamentoGeral();
}
else{
context.addMessage("formVerificaBoletimAluno:mensagem", new FacesMessage(
FacesMessage.SEVERITY_ERROR, "Nem todas as suas menções finais foram digitadas no sistema, por favor aguarde",
"" + ""));}
}
}


public String gerarBoletimParcial() throws JRException{
FacesContext context=FacesContext.getCurrentInstance();
context.addMessage("formVerificaBoletimAluno:mensagem", new FacesMessage(
FacesMessage.SEVERITY_INFO, "Boletim final gerado com sucesso.",
"" + ""));
bDao.gerarBoletimParcial(identificacaoAluno, turma.getIDTurma(), turma.getModulo());
return "";
}

public String gerarBoletimFinal() throws JRException{
bDao.gerarBoletimFinal(identificacaoAluno, turma.getIDTurma(), turma.getModulo());
return "";
}

public String gerarAcompanhamentoGeral() throws JRException{
bDao.acompanhamentoGeralDoEstudante(identificacaoAluno, turma.getIDTurma());
return "";
}


public Turma getTurma() {
	return turma;
}



public void setTurma(Turma turma) {
	this.turma = turma;
}



public int getIDTurma() {
	return IDTurma;
}



public void setIDTurma(int iDTurma) {
	IDTurma = iDTurma;
}



public String getRequisicao() {
	return requisicao;
}



public void setRequisicao(String requisicao) {
	this.requisicao = requisicao;
}



public String getIdentificacaoAluno() {
	return identificacaoAluno;
}



public void setIdentificacaoAluno(String identificacaoAluno) {
	this.identificacaoAluno = identificacaoAluno;
}



public CursoDao getCursoDao() {
	return cursoDao;
}



public void setCursoDao(CursoDao cursoDao) {
	this.cursoDao = cursoDao;
}



public Curso getCurso() {
	return curso;
}



public void setCurso(Curso curso) {
	this.curso = curso;
}




}
