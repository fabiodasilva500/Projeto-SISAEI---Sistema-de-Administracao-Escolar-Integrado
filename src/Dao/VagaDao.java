package Dao;

import java.util.Date;
import java.util.List;

import Pojo.Vaga;
public interface VagaDao {
public boolean inserir(Vaga vaga);
public List<Vaga> pesquisarPorEmpresa(String nomeEmpresa, Date dataInicial, Date dataFinal);
public List<Vaga> pesquisarPorData(Date dataInicial, Date dataFinal);
public boolean atualizar (Vaga vaga);
public boolean remover(int IDVaga);
public int localizaIDVaga ();
}


