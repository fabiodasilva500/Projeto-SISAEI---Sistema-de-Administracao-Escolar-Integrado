package ManagedBeans;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;

import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.BoletimDao;
import Dao.BoletimDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Dao.DisciplinaDao;
import Dao.DisciplinaDaoImplementation;
import Dao.TurmaDao;
import Dao.TurmaDaoImplementation;
import Pojo.Aluno;
import Pojo.Boletim;
import Pojo.Curso;
import Pojo.Disciplina;
import Pojo.Turma;

@ManagedBean(name = "boletimMB")
@SessionScoped
public class BoletimMB implements Serializable {

	private static final long serialVersionUID = -4281390476508498320L;
	private List<Curso> listaCursos;
	private List<Turma> listaTurmas;
	private List<Aluno> listaAlunos;
	private List<Disciplina> listaDisciplinas;
	private CursoDao cursoDao;
	private TurmaDao turmaDao;
	private AlunoDao alunoDao;
	private BoletimDao boletimDao;
	private DisciplinaDao disciplinaDao;
	private int selecaoCurso;
	private int selecaoTurma;
	private int selecaoModulo;
	private String selecaoNota;
	private double faltas;
	private double frequencia;
	private String status;

	private int selecaoDisciplina;
	private int selecaoConceito;

	private boolean desabilitaTurma;
	private boolean desabilitaModulo;
	private boolean desabilitaDisciplina;
	private boolean desabilitaAulasDadas;
	private boolean desabilitaConceito;
	private boolean desabilitaCurso;
	
	private Boletim boletimAtual;

	private String nomeTurma;
	private String nomeDisciplina;

	private Turma turma = new Turma();
	private Curso curso = new Curso();

	private String insereNotas;
	private double insereFaltas;
	private double insereFrequencia;
	

	private String insereStatus;

	private Boletim boletimCadastrado;
	private String novoCadastroNota;
	private double novoCadastroFaltas;
	private double novoCadastroFrequencia;

	private String nomeAluno;

	private double aulasDadas;
	private boolean desabilitaNovaSelecaoNota;
	private boolean desabilitaAno;

	private List<Boletim> listaBoletim = new ArrayList<Boletim>();

	private int selecaoSemestre;
	private int selecaoAno;
    private List<Integer> anos;
    private Integer ano;


	
	public BoletimMB() {
		setCurso(new Curso());
		setTurma(new Turma());
		setBoletimAtual(new Boletim());
		setBoletimCadastrado(new Boletim());
		setListaCursos(new ArrayList<Curso>());
		setListaTurmas(new ArrayList<Turma>());
		setListaAlunos(new ArrayList<Aluno>());
		setListaDisciplinas(new ArrayList<Disciplina>());
		setAnos(new ArrayList<Integer>());
		cursoDao = new CursoDaoImplementation();
		alunoDao = new AlunoDaoImplementation();
		turmaDao = new TurmaDaoImplementation();
		boletimDao = new BoletimDaoImplementation();
		disciplinaDao = new DisciplinaDaoImplementation();
		localizaCursos();
		desabilitaTurma = true;
		desabilitaDisciplina = true;
		desabilitaAulasDadas = true;
		desabilitaNovaSelecaoNota=true;
		desabilitaAno=true;
		desabilitaConceito=true;
		desabilitaCurso=true;
		desabilitaModulo=true;
	    }
	

	// Listando os cursos de acordo com o valor selecionado
	public List<Curso> localizaCursos() {
		setListaCursos(cursoDao.listaCursos());
		return listaCursos;
	}

	// Listando as turmas do curso selecionado
	public void listaTurmaCurso() {
		setListaTurmas(turmaDao.listaTurmaCurso(selecaoCurso, selecaoSemestre, selecaoAno));

		if (selecaoCurso > 0) {
			desabilitaTurma = false;
			desabilitaDisciplina = false;
		} else {
			desabilitaTurma = true;
			desabilitaDisciplina = true;
			aulasDadas=0;
			selecaoTurma=0;
		}
		listaDisciplina();
		atualizaAulasDadas();
	}

	// Listando as turmas de acordo com o módulo selecionado
	public void listaTurmaModulo() {
		setListaTurmas(turmaDao.listaTurmaModulo(selecaoCurso, selecaoModulo, selecaoSemestre, selecaoAno));
		listaDisciplina();
		atualizaAulasDadas();

	}

	// Listando as disciplinas de acordo com o curso e módulo selecionados
	public void listaDisciplina() {
		setListaDisciplinas(disciplinaDao.listaDisciplina(selecaoCurso,
				selecaoModulo));
		if (selecaoDisciplina > 0 && selecaoCurso > 0) {
			desabilitaAulasDadas = false;
		} else {
			desabilitaAulasDadas = true;
		}

	}
	
	
	public void atualizaAulasDadas(){
	aulasDadas=boletimDao.calculaTotalAulas(selecaoCurso, selecaoTurma, selecaoDisciplina);
	System.out.println("Aulas dadas:"+aulasDadas);
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
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

	public int getSelecaoModulo() {
		return selecaoModulo;
	}

	public void setSelecaoModulo(int selecaoModulo) {
		this.selecaoModulo = selecaoModulo;
	}

	public boolean isDesabilitaTurma() {
		return desabilitaTurma;
	}

	public void setDesabilitaTurma(boolean desabilitaTurma) {
		this.desabilitaTurma = desabilitaTurma;
	}

	public boolean isDesabilitaModulo() {
		return desabilitaModulo;
	}

	public void setDesabilitaModulo(boolean desabilitaModulo) {
		this.desabilitaModulo = desabilitaModulo;
	}

	public List<Aluno> localizaAlunos() {
		setListaAlunos(alunoDao.consultaAluno(selecaoTurma));
		atualizaNotaStatusAltera();
		return listaAlunos;
	}
	
	public List<Aluno> localizaAlunosBoletim() {
		setListaAlunos(alunoDao.consultaAluno(selecaoTurma));
		return listaAlunos;
	}

	// Redirecionando o sistema para a realização do cadastro de notas
	// carregando a lista de alunos da turma selecionada
	// A turma selecionada e a disciplina selecionada

	public String redirecionar() {
		setListaAlunos(alunoDao.consultaAluno(selecaoTurma));
		setNomeTurma(turmaDao.localizaNome(selecaoTurma));
		setNomeDisciplina(disciplinaDao.localizaNome(selecaoDisciplina));
		boletimAtual.setFrequenciaParcial(0);
		boletimAtual.setFaltasParciais(0);
		boletimAtual.setFrequenciaParcial(0);
		boletimAtual.setFrequenciaFinal(0);
		faltas = 0;
		frequencia = 0;
		boletimAtual.setStatus("");
		selecaoNota = "";
		listaBoletim.clear();


		return "./insereBoletimDirecao.jsf";
	}

	public boolean isDesabilitaDisciplina() {
		return desabilitaDisciplina;
	}

	public void setDesabilitaDisciplina(boolean desabilitaDisciplina) {
		this.desabilitaDisciplina = desabilitaDisciplina;
	}

	public int getSelecaoDisciplina() {
		return selecaoDisciplina;
	}

	public void setSelecaoDisciplina(int selecaoDisciplina) {
		this.selecaoDisciplina = selecaoDisciplina;
	}

	public List<Disciplina> getListaDisciplinas() {
		return listaDisciplinas;
	}

	public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
	}

	// Passos necessários para o cadatro do boletim
	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public String getSelecaoNota() {
		return selecaoNota;
	}

	public void setSelecaoNota(String selecaoNota) {
		this.selecaoNota = selecaoNota;
	}

	public Boletim getBoletimAtual() {
		return boletimAtual;
	}

	public void setBoletimAtual(Boletim boletimAtual) {
		this.boletimAtual = boletimAtual;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public boolean isDesabilitaAulasDadas() {
		return desabilitaAulasDadas;
	}

	public void setDesabilitaAulasDadas(boolean desabilitaAulasDadas) {
		this.desabilitaAulasDadas = desabilitaAulasDadas;
	}

	public int getSelecaoConceito() {
		return selecaoConceito;
	}

	public void setSelecaoConceito(int selecaoConceito) {
		this.selecaoConceito = selecaoConceito;
	}

	// Método responsável por adicionar os elementos na lista
	// atualizando todos os itens de acordo com os valores informados
	// de acordo com o número de faltas informado


	// Método responsável por atualizar todos os itens de acordo com a nota
	// informada; porém sem adicionar na lista
	// de acordo com a nota informada
	public void atualizaNotaStatusInsere(String identificacao) {
		frequencia = -1;
		boletimAtual.setStatus("");


		faltas = boletimDao.consultaFaltas(selecaoCurso, selecaoTurma,
				selecaoDisciplina, identificacao);
		
		


		System.out.println("Faltas localizadas:"+faltas);
		
		

		double aulasPresentes = (aulasDadas- faltas);
		if (aulasPresentes >= 0) {

			frequencia = ((aulasPresentes * 100) / aulasDadas);

			if (frequencia >= 0 && selecaoConceito == 2) {

				if (!selecaoNota.equalsIgnoreCase("I")) {
					boletimAtual.setStatus("Promovido");
				}

				if ((selecaoNota.equalsIgnoreCase("I"))) {
					boletimAtual.setStatus("Retido por nota");
				}


			}

			insereNotas = selecaoNota;
			insereFaltas = faltas;
			insereFrequencia = frequencia;
			insereStatus = boletimAtual.getStatus();

			Boletim b = new Boletim();
			b.setNumeroDoConceito(selecaoConceito);
			b.setIdentificacaoAluno(identificacao);
			b.setIDDaTurma(selecaoTurma);
			b.setIDDaDisciplina(selecaoDisciplina);
			b.setModulo(selecaoModulo);
			if (selecaoConceito == 1) {
				b.setAulasDadasParciais(aulasDadas);
				b.setAulasDadasFinais(aulasDadas);
				b.setConceitoParcial(insereNotas);
				b.setFaltasParciais(faltas);
				b.setFrequenciaParcial(frequencia);
				b.setFaltasFinais(0);
				b.setFrequenciaFinal(0);
			} else {
				b.setAulasDadasFinais(aulasDadas);
				b.setConceitoFinal(insereNotas);
				b.setFaltasFinais(faltas);
				b.setFrequenciaFinal(frequencia);
			}
			b.setStatus(insereStatus);

			// Invocando o método que será responsável por atualizar o ArrayList
			// de acordo
			// com as notas informadas
			atualizaArrayList(identificacao);

			listaBoletim.add(b);

		} else {
			frequencia = 0;
		}
	}

	// Fazendo a varredura no array list e atualizando a nota informada
	public void atualizaArrayList(String identificacao) {

		for (int i = 0; i < listaBoletim.size(); i++) {
			if (identificacao == listaBoletim.get(i).getIdentificacaoAluno()) {
				if (selecaoConceito == 1) {
					listaBoletim.get(i).setConceitoParcial(selecaoNota);
					listaBoletim.get(i).setStatus(insereStatus);

				} else {
					listaBoletim.get(i).setConceitoFinal(selecaoNota);
				}
			}
		}
	}

	// Invocando o método responsável por varrer o Array List
	// eliminar as repetições, através de um Loop e quando o limite
	// dele for alcançado a lista é limpada como mecanismo de segurança
	public String inserir() {
		FacesContext context = FacesContext.getCurrentInstance();


		boolean inserido = false;
		int cont = 1;
		while (cont <= 200) {
			for (int i = 0; i < listaBoletim.size(); i++) {
				String aux = listaBoletim.get(i).getIdentificacaoAluno();

				for (int j = i + 1; j < listaBoletim.size(); j++) {
					if (aux == listaBoletim.get(j).getIdentificacaoAluno()) {
						listaBoletim.remove(i);
					}
				}
			}

			cont = cont + 1;

			if (listaBoletim.size() == 201) {
				listaBoletim.clear();
			}
		}

		for (Boletim b : listaBoletim) {
			inserido = boletimDao.inserir(b, selecaoCurso);
		}

		if (inserido) {
			context.addMessage("formInsereBoletim:mensagem", new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Dados inseridos com sucesso",
					"" + ""));
		} else {
			context.addMessage(
					"formInsereBoletim:mensagem",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Erro na realização do cadastro, gere o PDF para verificar as notas inseridas",
							"" + ""));
		}
		listaBoletim.clear();
		limpaCampo();

		return "./boletimDirecao.jsf";
	}



	public void limpaCampo() {
		selecaoNota = "";
		setFaltas(0);
		setFrequencia(0);
		boletimAtual.setStatus("");
		novoCadastroNota= "";
		novoCadastroFaltas = 0;
		novoCadastroFrequencia = 100;
		novoCadastroNota="";
		boletimCadastrado.setIdentificacaoAluno("");
		boletimCadastrado.setStatus("");


	}

	// Consulta, Alteração e Exclusão do boletim
	public String modificarDados() {
		setListaAlunos(alunoDao.consultaAluno(selecaoTurma));
		novoCadastroFrequencia=0;
		novoCadastroFaltas=0;
		novoCadastroNota="";
		boletimCadastrado.setStatus("");
		desabilitaNovaSelecaoNota=true;
		return "./modificarBoletimDirecao.jsf";

	}

	public Boletim getBoletimCadastrado() {
		return boletimCadastrado;
	}

	public void setBoletimCadastrado(Boletim boletimCadastrado) {
		this.boletimCadastrado = boletimCadastrado;
	}

	public String getNovoCadastroNota() {
		return novoCadastroNota;
	}

	public void setNovoCadastroNota(String novoCadastroNota) {
		this.novoCadastroNota = novoCadastroNota;
	}



	// Método responsável por atualizar todos os itens
	// de um único estudante para inserção ou alteração de acordo com a
	// nota informada
	public void atualizaNotaStatusAltera() {

		if(boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase("") || boletimCadastrado.getIdentificacaoAluno()==null){
			desabilitaNovaSelecaoNota=true;
			limpaCampo();
		}
		else{
			desabilitaNovaSelecaoNota=false;
		}

		novoCadastroFaltas = boletimDao.consultaFaltas(selecaoCurso, selecaoTurma,
				selecaoDisciplina, boletimCadastrado.getIdentificacaoAluno());

		novoCadastroFrequencia = 0;
		boletimCadastrado.setStatus("");


		double aulasPresentes = (aulasDadas- novoCadastroFaltas);


		if (aulasPresentes >= 0) {

			novoCadastroFrequencia = ((aulasPresentes * 100) / aulasDadas);
			System.out.println("Nova frequencia:"+novoCadastroFrequencia);

			if (novoCadastroFrequencia >= 0 && selecaoConceito == 2) {

				if (!novoCadastroNota.equalsIgnoreCase("I")) {
					boletimCadastrado.setStatus("Promovido");
				}

				if ((novoCadastroNota.equalsIgnoreCase("I"))) {
					boletimCadastrado.setStatus("Retido por nota");
				}
			}


		} else {
			novoCadastroFrequencia = 0;
		}


	}






	// Método responsável por inserir as notas de um único estudante
	// em uma dada disciplina
	public String inserirDadoUnico() {
		FacesContext context = FacesContext.getCurrentInstance();


		boletimCadastrado.setIDDaTurma(selecaoTurma);
		boletimCadastrado.setIDDaDisciplina(selecaoDisciplina);
		boletimCadastrado.setNumeroDoConceito(selecaoConceito);
		boletimCadastrado.setModulo(selecaoModulo);

		if((!novoCadastroNota.equalsIgnoreCase("") && !novoCadastroNota.equalsIgnoreCase(null))
				&& (!boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase("") && 
						!boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase(null))){

			if (selecaoConceito == 1) {
				boletimCadastrado.setAulasDadasParciais(aulasDadas);
				boletimCadastrado.setAulasDadasFinais(aulasDadas);
				boletimCadastrado.setConceitoParcial(novoCadastroNota);
				boletimCadastrado.setFaltasParciais(novoCadastroFaltas);
				boletimCadastrado.setFrequenciaParcial(novoCadastroFrequencia);
				boletimCadastrado.setConceitoFinal("");
				boletimCadastrado.setFaltasFinais(0);
				boletimCadastrado.setFrequenciaFinal(0);

			} else if (selecaoConceito == 2) {
				boletimCadastrado.setAulasDadasFinais(aulasDadas);
				boletimCadastrado.setConceitoFinal(novoCadastroNota);
				boletimCadastrado.setFaltasFinais(novoCadastroFaltas);
				boletimCadastrado.setFrequenciaFinal(novoCadastroFrequencia);

			}
			boolean inserido = boletimDao.inserir(boletimCadastrado, selecaoCurso);
			if (inserido) {
				context.addMessage("formModificarDados:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Dados inseridos com sucesso",
						"" + ""));
				limpaCampo();
				desabilitaNovaSelecaoNota=true;
				
				if(selecaoConceito==1){
			    boletimDao.atualizarFrequenciaParcial(boletimAtual.getIdentificacaoAluno(), boletimAtual.getIDDaTurma(),
			    selecaoModulo, selecaoCurso, selecaoConceito);
				}
				else
				if(selecaoConceito==2){
				boletimDao.atualizarFrequenciaParcial(boletimAtual.getIdentificacaoAluno(), boletimAtual.getIDDaTurma(),
				selecaoModulo, selecaoCurso, selecaoConceito);
				}

			} else {
				context.addMessage("formModificarDados:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erro na inserção dos dados",
						"" + ""));
				limpaCampo();
				desabilitaNovaSelecaoNota=true;
			}
		}
		else{
			context.addMessage("formModificarDados:mensagem", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Algum dos dados não foi informado corretamente.",
					"" + ""));
		}


		return "";
	}

	// Método responsável por consultar as notas de um único estudante
	// em uma dada disciplina
	public String consultar() {
		boletimCadastrado.setIDDaTurma(selecaoTurma);
		boletimCadastrado.setIDDaDisciplina(selecaoDisciplina);
		setBoletimCadastrado(boletimDao.consultar(boletimCadastrado, selecaoConceito));
		if (selecaoConceito == 1) {
			novoCadastroNota = boletimCadastrado.getConceitoParcial();
			novoCadastroFaltas=boletimCadastrado.getFaltasParciais();
			novoCadastroFrequencia=boletimCadastrado.getFrequenciaParcial();
		} else {
			novoCadastroNota = boletimCadastrado.getConceitoFinal();
			novoCadastroFaltas=boletimCadastrado.getFaltasFinais();
			novoCadastroFrequencia=boletimCadastrado.getFrequenciaFinal();
		}
		return "";
	}

	// Método responsável por alterar as notas de um único estudante
	// em uma dada disciplina
	public String alterar() {
		FacesContext context = FacesContext.getCurrentInstance();

		boletimCadastrado.setIDDaTurma(selecaoTurma);
		boletimCadastrado.setIDDaDisciplina(selecaoDisciplina);
		boletimCadastrado.setNumeroDoConceito(selecaoConceito);
		boletimCadastrado.setModulo(selecaoModulo);

		if((!novoCadastroNota.equalsIgnoreCase("") && !novoCadastroNota.equalsIgnoreCase(null))
				&& (!boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase("") && 
						!boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase(null))){

			if (selecaoConceito == 1) {
				boletimCadastrado.setAulasDadasParciais(aulasDadas);
				boletimCadastrado.setConceitoParcial(novoCadastroNota);
				boletimCadastrado.setFaltasParciais(novoCadastroFaltas);
				boletimCadastrado.setFrequenciaParcial(novoCadastroFrequencia);
				boletimCadastrado.setFaltasFinais(0);
				boletimCadastrado.setFrequenciaFinal(0);
			} else if (selecaoConceito == 2) {
				boletimCadastrado.setAulasDadasFinais(aulasDadas);
				boletimCadastrado.setCurso(selecaoCurso);
				boletimCadastrado.setModulo(selecaoModulo);
				boletimCadastrado.setConceitoFinal(novoCadastroNota);
				boletimCadastrado.setFaltasFinais(novoCadastroFaltas);
				boletimCadastrado.setFrequenciaFinal(novoCadastroFrequencia);
			}
			boolean alterado = boletimDao.alterar(boletimCadastrado);
			if (alterado) {
				context.addMessage("formModificarDados:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Dados alterados com sucesso",
						"" + ""));
				limpaCampo();
				
				if(selecaoConceito==1){
				    boletimDao.atualizarFrequenciaParcial(boletimAtual.getIdentificacaoAluno(), boletimAtual.getIDDaTurma(),
				    selecaoModulo, selecaoCurso, selecaoConceito);
					}
					else
					if(selecaoConceito==2){
					boletimDao.atualizarFrequenciaParcial(boletimAtual.getIdentificacaoAluno(), boletimAtual.getIDDaTurma(),
					selecaoModulo, selecaoCurso, selecaoConceito);
					}
				
			} else {
				context.addMessage("formModificarDados:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erro na alteração dos dados",
						"" + ""));
			}
		}
		else{
			context.addMessage("formModificarDados:mensagem", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Algum dos dados não foi informado corretamente.",
					"" + ""));
		}
		return "";
	}

	// Método responsável por remover as notas de um único estudante
	// em uma dada disciplina
	public String remover(String identificacao) {
		FacesContext context = FacesContext.getCurrentInstance();

		if(!boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase("") && 
				!boletimCadastrado.getIdentificacaoAluno().equalsIgnoreCase(null)){

			boletimCadastrado.setIDDaTurma(selecaoTurma);
			boletimCadastrado.setIDDaDisciplina(selecaoDisciplina);
			boletimCadastrado.setNumeroDoConceito(selecaoConceito);
			boletimCadastrado.setModulo(selecaoModulo);
			boolean removido = boletimDao.remover(boletimCadastrado, selecaoCurso, selecaoConceito);
			if (removido) {
				context.addMessage("formModificarDados:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Dados removidos com sucesso",
						"" + ""));
				limpaCampo();
				if(selecaoConceito==1){
				    boletimDao.atualizarFrequenciaParcial(boletimAtual.getIdentificacaoAluno(), boletimAtual.getIDDaTurma(),
				    selecaoModulo, selecaoCurso, selecaoConceito);
					}
					else
					if(selecaoConceito==2){
					boletimDao.atualizarFrequenciaParcial(boletimAtual.getIdentificacaoAluno(), boletimAtual.getIDDaTurma(),
					selecaoModulo, selecaoCurso, selecaoConceito);
					}
			} else {
				context.addMessage("formModificarDados:mensagem", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erro na remoção dos dados",
						"" + ""));
			}
		}
		else{
			context.addMessage("formModificarDados:mensagem", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Para remover as notas do estudante da disciplina por favor selecione o nome do aluno desejado.",
					"" + ""));
		}
		return "";
	}

	// Método responsável por gerar um relatório pdf com as notas de um
	// estudante
	// em uma dada disciplina
	public String gerarPDF() throws JRException {
		boletimAtual.setIdentificacaoAluno("");

		boletimDao.gerarPDF(selecaoTurma, selecaoDisciplina);
		return "";
	}

	// Método responsável por redirecionar o usuário a tela de geração do
	// boletim

	public String telaBoletimDoAluno() {
		boletimAtual.setIdentificacaoAluno("");
		return "./gerarBoletim.jsf";
	}
	
	
	
	public String verificaTipoAcesso(){
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
    String nivel = String.valueOf(session.getAttribute("usuarioLogado"));
    if(nivel.equalsIgnoreCase("Secretaria")){
    return "./index.jsf";
    }
    else
    if(nivel.equalsIgnoreCase("Diretor")){
    return "./indexDirecao.jsf";
	}
    
	return "";
		
	}

	public void verificarTeclaTab() {
		System.out.println("TAB");
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public String localizaNomeAluno(String identificacaoAluno) {
		System.out.println("Selecionado");
		setNomeAluno(alunoDao.localizaNomeAluno(identificacaoAluno));
		return "";
	}

	public String gerarBoletim() throws JRException {

		if (selecaoConceito == 1) {
			boletimDao.gerarBoletimParcial(
					boletimAtual.getIdentificacaoAluno(), selecaoTurma,
					selecaoModulo);
		} else {
			boletimDao.gerarBoletimFinal(boletimAtual.getIdentificacaoAluno(),
					selecaoTurma, selecaoModulo);

		}

		return "";

	}

	public String gerarAcompanhamento() throws JRException {
		boletimDao.acompanhamentoGeralDoEstudante(
				boletimAtual.getIdentificacaoAluno(), selecaoTurma);
		return "";
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public double getFaltas() {
		return faltas;
	}

	public void setFaltas(double faltas) {
		this.faltas = faltas;
	}

	public double getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(double frequencia) {
		this.frequencia = frequencia;
	}

	public double getNovoCadastroFaltas() {
		return novoCadastroFaltas;
	}

	public void setNovoCadastroFaltas(double novoCadastroFaltas) {
		this.novoCadastroFaltas = novoCadastroFaltas;
	}

	public double getNovoCadastroFrequencia() {
		return novoCadastroFrequencia;
	}

	public void setNovoCadastroFrequencia(double novoCadastroFrequencia) {
		this.novoCadastroFrequencia = novoCadastroFrequencia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAulasDadas() {
		return aulasDadas;
	}

	public void setAulasDadas(double aulasDadas) {
		this.aulasDadas = aulasDadas;
	}

	public boolean getDesabilitaNovaSelecaoNota() {
		return desabilitaNovaSelecaoNota;
	}

	public void setDesabilitaNovaSelecaoNota(boolean desabilitaNovaSelecaoNota) {
		this.desabilitaNovaSelecaoNota = desabilitaNovaSelecaoNota;
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
	
	
	public List<Integer> listaAnos(){
		  Calendar cal = GregorianCalendar.getInstance(); 
		 

		  anos.clear();
		  if(selecaoSemestre>0){
			  desabilitaAno=false;
			  }
		     else{
			  desabilitaAno=true;
			  desabilitaDisciplina=true;
			  desabilitaTurma=true;
			  desabilitaCurso=true;
			  desabilitaConceito=true;
			  desabilitaModulo=true;
			  selecaoCurso=0;
			  selecaoDisciplina=0;
			  selecaoTurma=0;
			  }
		  
		  
		  System.out.println("Ano selecionado:"+selecaoAno);
		  
		  if(selecaoAno==0 || selecaoSemestre==0){
		  desabilitaCurso=true;
	      desabilitaConceito=true;
	      desabilitaModulo=true;
	      desabilitaTurma=true;
	      desabilitaDisciplina=true;
	      desabilitaModulo=true;
	      selecaoCurso=0;
	      selecaoDisciplina=0;
	      selecaoTurma=0;
		  }
		  else
		  if(selecaoAno>0 && selecaoSemestre>0){
		  desabilitaConceito=false;
		  desabilitaCurso=false;
		  desabilitaModulo=false;
		  }
		  
	
		  ano = cal.get(Calendar.YEAR); 

	        
		    if(selecaoSemestre==2){

		    for (int i=0;i<=2;i++){
		    anos.add(ano);
		    ano=ano-1;
		    }
		    }
		    else{
		    ano=cal.get(Calendar.YEAR);
		    anos.add(ano);
		    anos.add(ano-1);
		    }
		    
		  listaTurmaCurso();
		  listaTurmaModulo();
	    
	    return anos;
		}
	
	
	public void verificaSemestre(){
	   if(selecaoSemestre==0){
	   desabilitaAno=true;
	   }
	   
	  
	   
	   
		}


	public List<Integer> getAnos() {
		return anos;
	}


	public void setAnos(List<Integer> anos) {
		this.anos = anos;
	}


	public boolean isDesabilitaAno() {
		return desabilitaAno;
	}


	public void setDesabilitaAno(boolean desabilitaAno) {
		this.desabilitaAno = desabilitaAno;
	}


	public Integer getAno() {
		return ano;
	}


	public void setAno(Integer ano) {
		this.ano = ano;
	}


	public boolean isDesabilitaConceito() {
		return desabilitaConceito;
	}


	public void setDesabilitaConceito(boolean desabilitaConceito) {
		this.desabilitaConceito = desabilitaConceito;
	}


	public boolean isDesabilitaCurso() {
		return desabilitaCurso;
	}


	public void setDesabilitaCurso(boolean desabilitaCurso) {
		this.desabilitaCurso = desabilitaCurso;
	}

}