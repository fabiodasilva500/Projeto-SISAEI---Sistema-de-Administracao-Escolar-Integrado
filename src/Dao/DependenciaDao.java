package Dao;

import java.util.List;

import Pojo.Aluno;
import Pojo.Dependencia;

public interface DependenciaDao {
public boolean inserir(String identificacaoAluno, int IDCurso, int IDDaDisciplina);
public boolean remover (String identificacaoAluno, int IDDaDisciplina);
public boolean inserirQuitacaoDependencia(String identificacaoAluno, int IDCurso, int IDDaDisciplina, String atividade, String mencao);
public boolean removerQuitacaoDependencia(String identificacaoAluno, int IDCurso, int IDDaDisciplina);
public boolean decrementaDependencia(String identificacaoAluno);
public boolean incrementaDependencia(String identificacaoAluno);
public Dependencia localizaDependenciaCadastrada(String identificacaoAluno, int IDDaDisciplina);
public Dependencia localizaNovaNotaInformada(String identificacaoAluno, int IDDaDisciplina);
public List<Aluno> consultarDependencia(int selecaoDisciplina);
public List<Aluno> consultarDependenciaQuitada(int selecaoDisciplina);
}
