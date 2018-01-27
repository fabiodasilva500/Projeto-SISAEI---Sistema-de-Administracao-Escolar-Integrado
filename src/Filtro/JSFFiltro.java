package Filtro;

import java.io.IOException;

import javax.faces.bean.RequestScoped;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.AcessoDao;
import Dao.AcessoDaoImplementation;
import Pojo.Acesso;

/**
 * Servlet Filter implementation class JSFFiltro
 */
@WebFilter(filterName="JSFFilter")
@RequestScoped
public class JSFFiltro implements Filter {
private AcessoDao acessoDao;
private Acesso acesso;

    /**
     * Default constructor. 
     */
    public JSFFiltro() {
    acessoDao = new AcessoDaoImplementation();
    acesso=new Acesso();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	
		
		try{  
			
            HttpServletRequest httpReq = (HttpServletRequest)request; 
            
            HttpServletResponse httpRes = (HttpServletResponse)response;  
            HttpSession session = httpReq.getSession();  
            String nivel=String.valueOf(session.getAttribute("usuarioLogado"));
            
      	  System.out.println("Logado:"+nivel);
      	  System.out.println(httpReq.getRequestURL().toString());
  

      	if((session.getAttribute("usuarioLogado")==null) &&
        	    httpReq.getRequestURL().toString().contains("acesso.jsf")){
        	    chain.doFilter(request, response);  
        		}
      	 
      	else
      	  if(!httpReq.getRequestURL().toString().contains("acesso.jsf")){
      		  
      		if((session.getAttribute("usuarioLogado")==null) &&
      		(httpReq.getRequestURL().toString().contains("recuperaSenha.jsf")
      		|| httpReq.getRequestURL().toString().contains("alterarEmailSenha.jsf")
      		|| httpReq.getRequestURL().toString().contains("primeiroAcesso"))){
            chain.doFilter(request, response);
      		}
      		else
            if(session.getAttribute("usuarioLogado")==null   
                    && !httpReq.getRequestURL().toString().contains("acesso.jsf")){
            	httpRes.sendRedirect(httpReq.getContextPath()+"/page/acesso.jsf");  
       }
            
            else
            
            	if(nivel.equalsIgnoreCase("Secretaria")){
            	if(httpReq.getRequestURL().toString().contains("index.jsf")
            	|| httpReq.getRequestURL().toString().contains("aluno.jsf") 
            	|| httpReq.getRequestURL().toString().contains("turma.jsf") 
            	|| httpReq.getRequestURL().toString().contains("professor.jsf")
            	|| httpReq.getRequestURL().toString().contains("listaProfessores.jsf")
            	|| httpReq.getRequestURL().toString().contains("professorTurma.jsf")
            	|| httpReq.getRequestURL().toString().contains("boletim.jsf") 
                || httpReq.getRequestURL().toString().contains("matricula.jsf")
                || httpReq.getRequestURL().toString().contains("escolheDeclaracao.jsf")
                || httpReq.getRequestURL().toString().contains("historicoAluno.jsf")
                || httpReq.getRequestURL().toString().contains("declaracaoAluno.jsf")
                || httpReq.getRequestURL().toString().contains("videoInstitucional.jsf")
                || httpReq.getRequestURL().toString().contains("modeloMental.jsf")
                || httpReq.getRequestURL().toString().contains("gerarBoletim.jsf")
                || httpReq.getRequestURL().toString().contains("recuperaSenha.jsf")
                || httpReq.getRequestURL().toString().contains("alterarEmailSenha.jsf")
                || httpReq.getRequestURL().toString().contains("acesso.jsf")
                || httpReq.getRequestURL().toString().contains("trancamento.jsf")
                || httpReq.getRequestURL().toString().contains("invalidaAcesso.jsf")){
            		
               System.out.println("Chegou:"+httpReq.getRequestURL().toString());
                chain.doFilter(request, response);  

            	}
            	else{
                    httpRes.sendRedirect(httpReq.getContextPath()+"/page/invalidaAcesso.jsf");   

                	}
            	}
            
            	else
            		
        	if(nivel.equalsIgnoreCase("Aluno")){
            	if(httpReq.getRequestURL().toString().contains("indexAluno.jsf")
            	|| httpReq.getRequestURL().toString().contains("acessoBoletimAluno.jsf") 
                || httpReq.getRequestURL().toString().contains("questionario.jsf")
                || httpReq.getRequestURL().toString().contains("recuperaSenha.jsf")
                || httpReq.getRequestURL().toString().contains("alterarEmailSenha.jsf")
                || httpReq.getRequestURL().toString().contains("acesso.jsf")
                ||  httpReq.getRequestURL().toString().contains("duvidaAluno.jsf")
                || httpReq.getRequestURL().toString().contains("videoInstitucional.jsf")
                || httpReq.getRequestURL().toString().contains("invalidaAcesso.jsf")){ 


                chain.doFilter(request, response);  

            	}
            	else{
                    httpRes.sendRedirect(httpReq.getContextPath()+"/page/invalidaAcesso.jsf");   
                	}
            	}
            
        	else
        		if(nivel.equalsIgnoreCase("Professor")){
        		if(httpReq.getRequestURL().toString().contains("indexProfessor.jsf")
        		|| httpReq.getRequestURL().toString().contains("diarioProfessor.jsf")
        		||httpReq.getRequestURL().toString().contains("boletimProfessor.jsf")
        		|| httpReq.getRequestURL().toString().contains("dependencia.jsf")
        		|| httpReq.getRequestURL().toString().contains("atualizaDependencia.jsf")
        		|| httpReq.getRequestURL().toString().contains("atualizaDependenciaQuitada.jsf")
        		|| httpReq.getRequestURL().toString().contains("insereBoletimProfessor.jsf")
        		|| httpReq.getRequestURL().toString().contains("modificarBoletimProfessor.jsf")
        		|| httpReq.getRequestURL().toString().contains("contatoProfessor.jsf")
        	    || httpReq.getRequestURL().toString().contains("videoInstitucional.jsf")
                || httpReq.getRequestURL().toString().contains("modeloMental.jsf")
        	    || httpReq.getRequestURL().toString().contains("recuperaSenha.jsf")
                || httpReq.getRequestURL().toString().contains("alterarEmailSenha.jsf")
        		|| httpReq.getRequestURL().toString().contains("acesso.jsf")
                || httpReq.getRequestURL().toString().contains("invalidaAcesso.jsf")){
                chain.doFilter(request, response);  
                }
        		else{
                httpRes.sendRedirect(httpReq.getContextPath()+"/page/invalidaAcesso.jsf");   
        		}
        		}
       	 if(nivel.equalsIgnoreCase("Coordenador")){
    		 if(httpReq.getRequestURL().toString().contains("indexCoordenador.jsf")
    	     || httpReq.getRequestURL().toString().contains("Diario.jsf")
    	     ||httpReq.getRequestURL().toString().contains("escolheEstatistica.jsf")
    	     ||httpReq.getRequestURI().toString().contains("estatisticaInstituicao.jsf")
    	     ||httpReq.getRequestURL().toString().contains("estatisticaAluno.jsf")
    	     ||httpReq.getRequestURL().toString().contains("estatisticaComparacaoTurmas.jsf")
    	     ||httpReq.getRequestURL().toString().contains("estatisticaDisciplina.jsf")
    	     ||httpReq.getRequestURL().toString().contains("estatisticaTurma.jsf")
    	     || httpReq.getRequestURL().toString().contains("graficoComparativoAluno.jsf")
    	     || httpReq.getRequestURL().toString().contains("graficoComparativoTurma.jsf")
    	     || httpReq.getRequestURL().toString().contains("graficoDiario.jsf")
     	     || httpReq.getRequestURL().toString().contains("graficoDisciplinaTurma.jsf")
     	     || httpReq.getRequestURL().toString().contains("videoInstitucional.jsf")
             || httpReq.getRequestURL().toString().contains("modeloMental.jsf")
             || httpReq.getRequestURL().toString().contains("vaga.jsf")
             || httpReq.getRequestURL().toString().contains("localizaVagas.jsf")
     	     || httpReq.getRequestURL().toString().contains("recuperaSenha.jsf")
             || httpReq.getRequestURL().toString().contains("alterarEmailSenha.jsf")
     	     || httpReq.getRequestURL().toString().contains("acesso.jsf")
             || httpReq.getRequestURL().toString().contains("invalidaAcesso.jsf")){
    	     chain.doFilter(request, response);  
    	 }
    		 else{
                 httpRes.sendRedirect(httpReq.getContextPath()+"/page/invalidaAcesso.jsf");   
         		}


      	  }
       	 else 
       		 if(nivel.equalsIgnoreCase("Diretor")){
              chain.doFilter(request, response);  
       		 }
      	  }
      	  else{
           chain.doFilter(request, response);  
      	  }
     
      	  }
		
      	  catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
	


	public String redireciona(FilterChain chain){
	return "./acesso.jsf";
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

}
