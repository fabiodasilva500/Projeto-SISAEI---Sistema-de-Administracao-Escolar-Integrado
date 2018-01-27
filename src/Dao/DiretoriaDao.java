package Dao;

import Pojo.Diretoria;

public interface DiretoriaDao {
	public boolean alterar (String cpf, Diretoria s);
	public Diretoria pesquisarPorNome(String nome);
	public boolean excluir (String cpf);
	public boolean inserir(Diretoria DiretoriaAtual);
	public String localizaNomeDiretoria(String identificacao);
	public boolean validaNomeRG(String identificacao, String rg, String nome);
	public boolean validaCEP(String identificacao, String cep);  //m�todo retirado do managed bean; por�m pode retornar

}
