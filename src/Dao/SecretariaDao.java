package Dao;

import java.util.Date;
import java.util.List;

import Pojo.Secretaria;

public interface SecretariaDao {
	public boolean alterar (String cpf, Secretaria s);
	public Secretaria pesquisarPorNome(String nome);
	public boolean excluir (String cpf);
	public boolean inserir(Secretaria secretariaAtual);
	public String localizaNomeSecretaria(String identificacao);
	public boolean validaNomeRG(String identificacao, String rg, String nome);
	public boolean validaCEP(String identificacao, String cep);  //método retirado do managed bean; porém pode retornar

	  
}
