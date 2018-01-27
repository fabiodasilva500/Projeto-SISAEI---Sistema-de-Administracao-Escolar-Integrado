package Dao;

import java.util.List;

import Pojo.Disciplina;

public interface DisciplinaDao {
	public boolean inserir(Disciplina d);
	public boolean atualizar (int IDDisciplina, Disciplina d);
	public boolean remover (int IDDisciplina);
	public Disciplina pesquisar(Disciplina disciplina);
	public List<Disciplina> listaDisciplina(int IDDisciplina, int modulo);
	public String localizaNome(int IDDisciplina);
	public int localizaIDDisciplina();
	public int cargaHoraria (int IDDisciplina);
	public List<String> localizaDisciplinas(int curso, int modulo);
	public List<String> localizaDisciplinaCurso(int curso);
	public List<Disciplina> localizaDisciplinasCurso(int curso);
	public List<Disciplina> localizaDisciplinasCursoModulo(int curso, int modulo);
    public int localizaDisciplinas (int curso);
    

}
