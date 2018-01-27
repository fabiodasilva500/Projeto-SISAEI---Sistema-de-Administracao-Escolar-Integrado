package Dao;

import java.util.List;

import Pojo.Acesso;

public interface AcessoDao {
public boolean inserirAcesso(Acesso a);
public Acesso consultarAcessoAluno(String identificacao, String senha);
public boolean alterarAcesso();
public boolean validaDados(String identificacao, String nome, String rg,
		String email);
public List<String> localizaLoginSenha(String identificacao);
public boolean excluirAcesso(String identificacao, String nome,
		String rg, String email, String tipoAcesso);
public String verificarPrimeiroAcesso(String identificacao, String senha);
public boolean atualizaPrimeiroAcesso(String email, String senha);
public boolean atualizaNovaSenha(String email, String novaSenha);
public boolean atualizaEmailSenha(String identificacao, String novoEmail, String novaSenha);
public boolean validaAcessoSecretaria(String identificacao);
public String localizaEmailUsuario(String emailCadastrado, String rgCadastrado);
public boolean atualizaEmailStatus(String email, String novaSenha, String rg); 
public String localizaEmail(String identificacao);
}



