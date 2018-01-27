package Dao;

import java.util.List;

import Pojo.Coordenador;

public interface CoordenadorDao {
	public boolean alterar (String cpf, Coordenador s);
	public Coordenador pesquisarPorNome(String nome);
	public boolean excluir (String cpf);
	public boolean inserir(Coordenador coordenadorAtual);
	public String localizaNomeCoordenador(String identificacao);
	public boolean validaNomeRG(String identificacao, String rg, String nome);
	public boolean validaCEP(String identificacao, String cep);  //método retirado do managed bean; porém pode retornar

	
}
