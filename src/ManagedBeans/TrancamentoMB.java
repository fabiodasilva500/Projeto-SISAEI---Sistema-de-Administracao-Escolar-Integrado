package ManagedBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.TrancamentoDao;
import Dao.TrancamentoDaoImplementation;
import Pojo.Curso;
import Pojo.Trancamento;

@ManagedBean(name="trancamentoMB")
@ViewScoped
public class TrancamentoMB {
private Trancamento trancamentoAtual;
private TrancamentoDao trancamentoDao;
private List<Curso> listaCursos;
private CursoDao cursoDao;
private int ano;
private List<Integer> anos;


public TrancamentoMB(){
trancamentoAtual = new Trancamento();
trancamentoDao=new TrancamentoDaoImplementation();
setListaCursos(new ArrayList<Curso>());
cursoDao=new CursoDaoImplementation();
anos=new ArrayList<Integer>();
listaCursos();
}


public String inserir(){
	   FacesContext context = FacesContext.getCurrentInstance();
			boolean inserido= trancamentoDao.inserir(trancamentoAtual);
			if(inserido){
			context.addMessage("formTrancamento:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Trancamento inserido com sucesso.", ""+""));
			limpaCampo();
			}
			else{
			context.addMessage("formTrancamento:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Trancamento não inserido.", ""+""));
			}
return "";	
}



public String excluir(){
	FacesContext context = FacesContext.getCurrentInstance();
	boolean excluido= trancamentoDao.excluir(trancamentoAtual.getIdentificacao());
	if(excluido){
	context.addMessage("formTrancamento:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Trancamento excluído com sucesso", ""+""));
	limpaCampo();
	}
	else{
	context.addMessage("formTrancamento:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Trancamento não excluído.", ""+""));
	}
	return "";
	}

public List<Integer> listaAnos(){
	  Calendar cal = GregorianCalendar.getInstance(); 
	   anos.clear();
	   ano = cal.get(Calendar.YEAR); 

	    for (int i=0;i<=4;i++){
	    anos.add(ano);
	    ano=ano-1;
	    }
	  
  return anos;
	}

public Trancamento consultar(){
setTrancamentoAtual(trancamentoDao.consultar(trancamentoAtual));
return trancamentoAtual;
}


		public List<Curso> listaCursos() {
			setListaCursos(cursoDao.listaCursos());
			return listaCursos;
		}
		
public void limpaCampo(){
trancamentoAtual = new Trancamento();
}


public Trancamento getTrancamentoAtual() {
	return trancamentoAtual;
}


public void setTrancamentoAtual(Trancamento trancamentoAtual) {
	this.trancamentoAtual = trancamentoAtual;
}


public List<Curso> getListaCursos() {
	return listaCursos;
}


public void setListaCursos(List<Curso> listaCursos) {
	this.listaCursos = listaCursos;
}


public int getAno() {
	return ano;
}


public void setAno(int ano) {
	this.ano = ano;
}


public List<Integer> getAnos() {
	return anos;
}


public void setAnos(List<Integer> anos) {
	this.anos = anos;
}
}
