package ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean (name="controleDeclaracaoMB")
@ViewScoped
public class ControleDeclaracaoMB {
private String requisicao;


	public String redirecionar(){
	    if(requisicao.equalsIgnoreCase("declaracao")){
	    return "./declaracaoAluno.jsf";
	    }
	    else
	    if(requisicao.equalsIgnoreCase("solicitacaoEspecial")){
	    return "./solicitacaoEspecial.jsf";
	    }
		if(requisicao.equalsIgnoreCase("historicoEscolar")){
		return "./historicoAluno.jsf";
		}
		return "";
		}

	public String getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(String requisicao) {
		this.requisicao = requisicao;
	}

}
