package Dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public interface DeclaracaoDao {
public JasperPrint gerarHistorico(String identificacaoAluno, int IDCurso);
public JasperPrint gerarDeclacarao(int selecaoCurso, int selecaoTurma, int selecaoSemestre, int selecaoModulo, int selecaoAno, String rm);
}

	


