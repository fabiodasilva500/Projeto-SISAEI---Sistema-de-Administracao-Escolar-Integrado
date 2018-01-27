package Dao;

import java.util.List;

import Pojo.AreaConcentracao;

public interface AreaConcentracaoDao {
	public boolean inserir(int IDCurso, AreaConcentracao area);
	public boolean alterar(int IDCurso, AreaConcentracao area);
	public boolean excluir (String nome);
	public AreaConcentracao pesquisar(int IDCurso, String nome);
	public List<AreaConcentracao> listaAreas(int selecaoCurso);
	
}
