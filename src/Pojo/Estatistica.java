package Pojo;

import java.io.Serializable;

public class Estatistica implements Serializable {
private String descricao;
private int totais;

public Estatistica(String string, Integer integer) {
this.descricao=descricao;
this.totais=totais;
}
public Estatistica() {
	// TODO Auto-generated constructor stub
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}
public int getTotais() {
	return totais;
}
public void setTotais(int totais) {
	this.totais = totais;
}





}
