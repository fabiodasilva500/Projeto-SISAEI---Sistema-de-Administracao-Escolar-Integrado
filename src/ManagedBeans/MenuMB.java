package ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="menuMB")
@ViewScoped
public class MenuMB {

public MenuMB(){

}

public String aluno(){
return "./aluno.jsf";
}


}
