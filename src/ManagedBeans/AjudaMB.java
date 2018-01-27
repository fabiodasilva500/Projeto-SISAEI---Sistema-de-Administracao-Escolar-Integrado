package ManagedBeans;

import java.awt.Desktop;
import java.io.File;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;


@ManagedBean(name="ajudaMB")
@ViewScoped
public class AjudaMB {


public String manualDoUsuario(String requisicao){
if(requisicao.equalsIgnoreCase("secretaria")){
return manualDaSecretaria();
}
else
if(requisicao.equalsIgnoreCase("professor")){
return manualDoProfessor();
}
else
if(requisicao.equalsIgnoreCase("diretor")){
return manualDaDiretoria();
}
else
if(requisicao.equalsIgnoreCase("coordenador")){
return manualDoCoordenador();
}
else
if(requisicao.equalsIgnoreCase("aluno")){
return manualDoAluno();
}

return "";
}

public String manualDaSecretaria(){
	    FacesContext facesContext = FacesContext.getCurrentInstance();  
	    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
	   String caminho = scontext.getRealPath("/WEB-INF/LIB/ManuaisDoUsuario/ManualDaSecretaria.pdf"); 
	    
	 try {
		 
         File pdfFile = new File(caminho);
       
         Desktop.getDesktop().open(pdfFile);
              
   } catch (Exception ex) {
         ex.printStackTrace();
   }

return "";	
}

public String manualDoProfessor(){
	    FacesContext facesContext = FacesContext.getCurrentInstance();  
	    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
	   String caminho = scontext.getRealPath("/WEB-INF/LIB/ManuaisDoUsuario/ManualDoProfessor.pdf"); 
	    
	 try {
		 
         File pdfFile = new File(caminho);
       
         Desktop.getDesktop().open(pdfFile);
              
   } catch (Exception ex) {
         ex.printStackTrace();
   }

return "";	
}

public String manualDaDiretoria(){
    FacesContext facesContext = FacesContext.getCurrentInstance();  
    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
   String caminho = scontext.getRealPath("/WEB-INF/LIB/ManuaisDoUsuario/ManualDoDiretor.pdf"); 
    
 try {
	 
     File pdfFile = new File(caminho);
   
     Desktop.getDesktop().open(pdfFile);
          
} catch (Exception ex) {
     ex.printStackTrace();
}

return "";	
}

public String manualDoCoordenador(){
    FacesContext facesContext = FacesContext.getCurrentInstance();  
    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
   String caminho = scontext.getRealPath("/WEB-INF/LIB/ManuaisDoUsuario/ManualDoCoordenador.pdf"); 
    
 try {
	 
     File pdfFile = new File(caminho);
   
     Desktop.getDesktop().open(pdfFile);
          
} catch (Exception ex) {
     ex.printStackTrace();
}

return "";	
}

public String manualDoAluno(){
    FacesContext facesContext = FacesContext.getCurrentInstance();  
    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
   String caminho = scontext.getRealPath("/WEB-INF/LIB/ManuaisDoUsuario/ManualDoAluno.pdf"); 
    
 try {
	 
     File pdfFile = new File(caminho);
   
     Desktop.getDesktop().open(pdfFile);
          
} catch (Exception ex) {
     ex.printStackTrace();
}

return "";	
}

}


