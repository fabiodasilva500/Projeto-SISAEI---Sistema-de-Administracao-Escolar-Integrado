package ManagedBeans;

import java.io.Serializable;
import java.util.UUID;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
 
@ManagedBean (name="modeloMentalMB")
public class ModeloMentalMB implements Serializable {

	private MindmapNode principal;
	private MindmapNode selecionado;

	public ModeloMentalMB() {
		principal = new DefaultMindmapNode("Sistema Etec", "Sistema de Gest�o Acad�mica", "FFCC00", false);

		MindmapNode diretoria = new DefaultMindmapNode("Diretoria", "Diretoria Acad�mica", "6e9ebf", true);
		MindmapNode secretaria = new DefaultMindmapNode("Secretaria", "Secretaria", "6e9ebf", true);
		MindmapNode professor = new DefaultMindmapNode("Professor", "Professor", "6e9ebf", true);
		MindmapNode aluno = new DefaultMindmapNode("Aluno", "Aluno", "6e9ebf", true);

		principal.addNode(diretoria);
		principal.addNode(secretaria);
		principal.addNode(professor);
		principal.addNode(aluno);
	}
	

	public MindmapNode getPrincipal() {
		return principal;
	}

	public MindmapNode getSelecionado() {
		return selecionado;
	}
	public void setSelectedNode(MindmapNode selecionado) {
		this.selecionado= selecionado;
	}
	

	public void onNodeDblselect(SelectEvent event) {
		this.selecionado = (MindmapNode) event.getObject();        
	}

	public void onNodeSelect(SelectEvent event) {
		MindmapNode node = (MindmapNode) event.getObject();

		//populate if not already loaded
		if(node.getChildren().isEmpty()) {
			Object selecionado= node.getLabel();

			if(selecionado.equals("Professor")) {
				node.addNode(new DefaultMindmapNode("1 " + "Logar", false));
				node.addNode(new DefaultMindmapNode("1.1 " + "Di�rio", false));
				node.addNode(new DefaultMindmapNode("1.2 " + "Notas", false));
				node.addNode(new DefaultMindmapNode("1.3 " + "Reten��o", false));

			}
			else if(selecionado.equals("Secretaria")) {
				node.addNode(new DefaultMindmapNode("2 " + "Logar", false));
				node.addNode(new DefaultMindmapNode("2.1 " + "Alunos", false));
				node.addNode(new DefaultMindmapNode("2.2 " + "Turmas", false));
				node.addNode(new DefaultMindmapNode("2.3 " + "Professores", false));
				node.addNode(new DefaultMindmapNode("2.4 " + "Matr�culas", false));
				node.addNode(new DefaultMindmapNode("2.5 " + "Trancamento", false));
				node.addNode(new DefaultMindmapNode("2.6 " + "Declara��es", false));

			}
			else if(selecionado.equals("Aluno")){
				node.addNode(new DefaultMindmapNode("3 " + "Logar", false));
				node.addNode(new DefaultMindmapNode("3.1 " + "Boletim", false));
				node.addNode(new DefaultMindmapNode("3.2 " + "Avalia��es", false));
				
			}
			else if(selecionado.equals("Coordenador")) {
				node.addNode(new DefaultMindmapNode("4" + "Logar", false));
				node.addNode(new DefaultMindmapNode("4.1" + "Relat�rios", false));
				node.addNode(new DefaultMindmapNode("4.2" + "Gr�ficos", false));
		}
			else if(selecionado.equals("Diretoria")){
				node.addNode(new DefaultMindmapNode("5 " + "Logar", false));
				node.addNode(new DefaultMindmapNode("5.1 " + "Cursos", false));
				node.addNode(new DefaultMindmapNode("5.2 " + "Disciplinas", false));
				node.addNode(new DefaultMindmapNode("5.3 " + "Secret�rios", false));
				node.addNode(new DefaultMindmapNode("5.4 " + "Coordena��o", false));		
				node.addNode(new DefaultMindmapNode("5.5 " + "Dire��o", false));		
				node.addNode(new DefaultMindmapNode("5.6  " + "Acessos", false));	
				node.addNode(new DefaultMindmapNode("5.7  " + "Backup.", false));			


			}
		}   
	}

}