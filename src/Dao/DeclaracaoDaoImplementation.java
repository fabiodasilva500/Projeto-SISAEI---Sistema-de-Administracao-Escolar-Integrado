package Dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class DeclaracaoDaoImplementation implements DeclaracaoDao{
public JasperPrint gerarHistorico (String identificacaoAluno, int IDCurso){
    JasperPrint rel = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

		try {
			Connection con = BD.getConnection();
			Map map = new HashMap();
			map.put("identificacaoAluno",identificacaoAluno);
			map.put("IDCurso", IDCurso);
			String arquivoJasper = caminho+File.separator+"historicoAluno.jasper";
			rel = JasperFillManager.fillReport(arquivoJasper, map, con);
		    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=Histórico do Estudante de RM:"+ identificacaoAluno +".pdf");
	           // Exporta o relatório
	            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
	            // Salva o estado da aplicação no contexto do JSF
	            fc.getApplication().getStateManager().saveView(fc);
	            // Fecha o stream do response
	            fc.responseComplete();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		
		return rel;
	}

@Override
public JasperPrint gerarDeclacarao(int selecaoCurso, int selecaoTurma,
		int selecaoSemestre, int selecaoModulo, int selecaoAno, String rm) {
	    
	    JasperPrint rel = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");
		
		
		if(selecaoModulo==1 && selecaoSemestre==1){
        selecaoAno = selecaoAno +1;
		}
		else
		if(selecaoModulo == 2 && selecaoSemestre==1){
		selecaoSemestre = 2;

		}
		else
		if(selecaoModulo==1 && selecaoSemestre ==2){
		selecaoAno = selecaoAno+1;

		}
		else
		if(selecaoModulo ==2 && selecaoSemestre ==2){
		selecaoSemestre=1;
		selecaoAno = selecaoAno+1;
		}
	
		
		try {
			Connection con = BD.getConnection();
			Map map = new HashMap();
			map.put("IDCurso", selecaoCurso);
			map.put("IDTurma", selecaoTurma);
			map.put("modulo", selecaoModulo);
			map.put("semestre", selecaoSemestre);
			map.put("ano", selecaoAno);
			map.put("rm", rm);
        
			String arquivoJasper = caminho+File.separator+"declaracaoSimples.jasper";
			rel = JasperFillManager.fillReport(arquivoJasper, map, con);
		    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=Declaração de matrícula para o Estudante de RM:"+ rm +".pdf");
	           // Exporta o relatório
	            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
	            // Salva o estado da aplicação no contexto do JSF
	            fc.getApplication().getStateManager().saveView(fc);
	            // Fecha o stream do response
	            fc.responseComplete();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	
	return rel;
}

}
