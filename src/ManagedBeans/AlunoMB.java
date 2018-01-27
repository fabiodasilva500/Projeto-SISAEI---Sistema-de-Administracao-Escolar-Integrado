package ManagedBeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import Dao.AlunoDao;
import Dao.AlunoDaoImplementation;
import Dao.CursoDao;
import Dao.CursoDaoImplementation;
import Pojo.Acesso;
import Pojo.Aluno;
import Pojo.Curso;

@ManagedBean(name="alunoMB")
@RequestScoped

public class AlunoMB implements Serializable{
	private Aluno alunoAtual;
	private AlunoDao aluno;
	private List<Aluno> alunos = new ArrayList<Aluno>(); 
    private List<Curso> listaCursos=new ArrayList<Curso>();
	private CursoDao cursoDao;
	private List<String> listaEstados=new ArrayList<String>();
	private boolean habilitaCampos;
    private Acesso acesso;



	public AlunoMB(){
		AcessoMB a = new AcessoMB();
	System.out.println(a.acessoAtual.getIdentificacao());
		setAlunoAtual(new Aluno());
		setAlunos( new ArrayList<Aluno>() );
		aluno=new AlunoDaoImplementation();
		cursoDao =new CursoDaoImplementation();
		
	}
	

	public String inserir(){
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("Campo:"+habilitaCampos);
	    		
		try{
	       retirarMascara();
			boolean inserido = aluno.inserir(alunoAtual);
			if(inserido){
				limpaCampo();
				alunoAtual.setIdentificacao("");
				context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno inserido com sucesso", ""+""));
			}
			else{
				context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do aluno", ""+""));
			}
		}
		catch(SQLException e){
			context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Erro no cadastro do aluno:"+alunoAtual.getNome()+" ,por favor verifique os campos informados"));
			e.printStackTrace();
		}

		return "";
	}

	public String remover(){
		FacesContext context = FacesContext.getCurrentInstance();

		try{
			boolean removido = aluno.remover(alunoAtual.getIdentificacao());
			if(removido){
				limpaCampo();
				context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno removido com sucesso", ""+""));
			}
			else{
				context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na remoção do aluno, verifique se ele está realmente cadastrado no sistema ou em alguma turma.", ""+""));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return "";
	}


	public String atualizar(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		try{
			retirarMascara();
			boolean atualizado = aluno.atualizar(alunoAtual.getIdentificacao(), alunoAtual);
			if(atualizado){
				limpaCampo();
				alunoAtual.setIdentificacao("");
				context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno atualizado com sucesso", ""+""));
			}
			else{
				context.addMessage("formAlunos:mensagem", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do aluno", ""+""));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return "";
	}


	public String pesquisarPorNome() {
		AlunoDao aDao = new AlunoDaoImplementation();
		try {
			alunos = aDao.pesquisarPorNome(alunoAtual.getNome());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return " ";
	}


	public String pesquisarPorIdentificacao(){
		
		AlunoDao aDao = new AlunoDaoImplementation();
		System.out.println("Campo:"+habilitaCampos);


		try {
			setAlunoAtual(aDao.pesquisarPorId(alunoAtual.getIdentificacao()));
			if(alunoAtual.getDataNascimento()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
				alunoAtual.setPreparaData(sdf.format(alunoAtual.getDataNascimento()));
			}
			else
				if(alunoAtual.getDataNascimento()==null){
					limpaCampo();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return " ";
	}



	public Aluno getAlunoAtual() {
		return alunoAtual;
	}
	public void setAlunoAtual(Aluno alunoAtual) {
		this.alunoAtual = alunoAtual;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Curso> localizaCursos() {
		setListaCursos(cursoDao.listaCursos());
		return listaCursos;
	}






	public void limpaCampo(){
		alunoAtual.setNome("");
		alunoAtual.setCep("");
		alunoAtual.setEndereco("");
		alunoAtual.setNumero(0);
		alunoAtual.setBairro("");
		alunoAtual.setCidade("");
		alunoAtual.setEstado("");
		alunoAtual.setTelefone("");
		alunoAtual.setRg("");
		alunoAtual.setOrgao_expeditor("");
		alunoAtual.setDataNascimento(new Date());
		alunoAtual.setCidadeNascimento("");
		alunoAtual.setNacionalidade("");
		alunoAtual.setNaturalidade("");
		alunoAtual.setSexo("");
		alunoAtual.setIdade(0);
		alunoAtual.setEmail("");
		alunoAtual.setCurso(0);
	}
	
	
	public List<Curso> listaCursos(){
	setListaCursos(cursoDao.listaCursos());
	return listaCursos;
	}




	public List<Curso> getListaCursos() {
		return listaCursos;
	}




	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
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
	alunoAtual.setTelefone(alunoAtual.getTelefone().replaceAll("-", ""));
	alunoAtual.setCelular(alunoAtual.getCelular().replaceAll("-", ""));

	alunoAtual.setTelefone(alunoAtual.getTelefone().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  
	alunoAtual.setCelular(alunoAtual.getCelular().replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", ""));  
	
	alunoAtual.setCep(alunoAtual.getCep().replace("-", ""));
	alunoAtual.setRg(alunoAtual.getRg().replaceAll("[ ./-]", ""));
	}


	public List<String> getListaEstados() {
		return listaEstados;
	}




	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}




	public boolean isHabilitaCampos() {
		return habilitaCampos;
	}




	public void setHabilitaCampos(boolean habilitaCampos) {
		this.habilitaCampos = habilitaCampos;
	}
	
	
	public boolean habilitaSelecao(){
	return habilitaCampos;
	}
	
	public String ajuda(){
	try {
		aluno.gerarPDF();
	} catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return "./aluno.jsf";
	}
}
	

      

