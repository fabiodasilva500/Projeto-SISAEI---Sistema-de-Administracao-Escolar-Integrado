package Dao;

import Pojo.Trancamento;

public interface TrancamentoDao {
public boolean inserir(Trancamento trancamento);
public boolean excluir (String identificacaoAluno);
public Trancamento consultar (Trancamento trancamento);

}
