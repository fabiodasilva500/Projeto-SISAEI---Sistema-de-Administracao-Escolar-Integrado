package ManagedBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DeclaracaoDao;
import Dao.DeclaracaoDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Aluno;
import Pojo.Curso;
import Pojo.Turma;

@ManagedBean (name="declaracaoMB")
@ViewScoped
public class DeclaracaoMB {
	private AlunoDao alunoDao;
	private TurmaDao turmaDao;
	private CursoDao cursoDao;
	private DeclaracaoDao declaracaoDao;
	private List<Aluno> listaAlunos;
	private List<Turma> listaTurmas;
	private List<Curso> listaCursos;
	private int selecaoCurso;
	private int selecaoTurma;
	private String selecaoAluno;
	private int selecaoSemestre;
	private int selecaoAno;
	private int selecaoModulo;
	private boolean desabilitaCurso;
	private boolean desabilitaTurma;
	private boolean desabilitaAno;
	private boolean desabilitaAluno;
	

	public DeclaracaoMB(){
		setListaAlunos(new ArrayList<Aluno>());
		setListaTurmas(new ArrayList<Turma>());
		setListaCursos(new ArrayList<Curso>());
		desabilitaCurso=true;
		desabilitaTurma=true;
		desabilitaAno=true;
		desabilitaAluno=true;
		localizaCursos();
		listaAnos();
	}

	public void localizaCursos(){
    
		if(selecaoModulo>0){
		desabilitaCurso=false;
		cursoDao = new CursoDaoImplementation();
		setListaCursos(cursoDao.listaCursos());
		localizaTurmas();
		}
		else{
		desabilitaCurso=true;
		desabilitaTurma=true;
		desabilitaAluno=true;
		selecaoCurso = 0;
		selecaoTurma = 0;
		selecaoAluno = "";
		}
		
	}


	public void localizaTurmas (){
				
		if(selecaoModulo>0 && selecaoCurso>0){
		desabilitaTurma=false;
		turmaDao = new TurmaDaoImplementation();
		setListaTurmas(turmaDao.listaTurmasAnoCursoModulo(selecaoSemestre, selecaoAno, selecaoCurso, selecaoModulo));
		
		localizaAlunos();
		}
		else{
		desabilitaTurma=true;
		desabilitaAluno=true;
		selecaoAluno = "";
		
		listaTurmas.clear();
		}
		
		
	}

	public void localizaAlunos(){
		
		if(selecaoModulo>0 && selecaoCurso>0 && selecaoTurma>0){
			desabilitaAluno=false;
			alunoDao = new AlunoDaoImplementation();
			setListaAlunos(alunoDao.consultaAluno(selecaoTurma));
			}
			else{
			desabilitaAluno=true;
			selecaoAluno = "";
			}
	}

	
	public void gerarDeclaracao() throws JRException {
	declaracaoDao=new DeclaracaoDaoImplementation();
	declaracaoDao.gerarDeclacarao(selecaoCurso, selecaoTurma, selecaoSemestre, selecaoModulo, selecaoAno, selecaoAluno);	
	}
	
	public void listaAnos(){
		Calendar cal = GregorianCalendar.getInstance(); 
		selecaoAno = cal.get(Calendar.YEAR); 
	    selecaoSemestre = cal.get(Calendar.MONTH) + 1;
	    if(selecaoSemestre>=7){
	    selecaoSemestre=2;
	    }
	    else{
	    selecaoSemestre=1;
	    }
	    localizaCursos();
	}
	

	public AlunoDao getAlunoDao() {
		return alunoDao;
	}
	public void setAlunoDao(AlunoDao alunoDao) {
		this.alunoDao = alunoDao;
	}
	public TurmaDao getTurmaDao() {
		return turmaDao;
	}
	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}
	public CursoDao getCursoDao() {
		return cursoDao;
	}
	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}
	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}
	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}
	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}
	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}
	public List<Curso> getListaCursos() {
		return listaCursos;
	}
	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}
	public int getSelecaoCurso() {
		return selecaoCurso;
	}
	public void setSelecaoCurso(int selecaoCurso) {
		this.selecaoCurso = selecaoCurso;
	}
	public int getSelecaoTurma() {
		return selecaoTurma;
	}
	public void setSelecaoTurma(int selecaoTurma) {
		this.selecaoTurma = selecaoTurma;
	}
	public String getSelecaoAluno() {
		return selecaoAluno;
	}
	public void setSelecaoAluno(String selecaoAluno) {
		this.selecaoAluno = selecaoAluno;
	}

	public int getSelecaoSemestre() {
		return selecaoSemestre;
	}

	public void setSelecaoSemestre(int selecaoSemestre) {
		this.selecaoSemestre = selecaoSemestre;
	}

	public int getSelecaoAno() {
		return selecaoAno;
	}

	public void setSelecaoAno(int selecaoAno) {
		this.selecaoAno = selecaoAno;
	}


	public boolean isDesabilitaCurso() {
		return desabilitaCurso;
	}

	public void setDesabilitaCurso(boolean desabilitaCurso) {
		this.desabilitaCurso = desabilitaCurso;
	}

	public boolean isDesabilitaTurma() {
		return desabilitaTurma;
	}

	public void setDesabilitaTurma(boolean desabilitaTurma) {
		this.desabilitaTurma = desabilitaTurma;
	}

	public boolean isDesabilitaAno() {
		return desabilitaAno;
	}

	public void setDesabilitaAno(boolean desabilitaAno) {
		this.desabilitaAno = desabilitaAno;
	}

	public boolean isDesabilitaAluno() {
		return desabilitaAluno;
	}

	public void setDesabilitaAluno(boolean desabilitaAluno) {
		this.desabilitaAluno = desabilitaAluno;
	}



	public int getSelecaoModulo() {
		return selecaoModulo;
	}

	public void setSelecaoModulo(int selecaoModulo) {
		this.selecaoModulo = selecaoModulo;
	}

}
