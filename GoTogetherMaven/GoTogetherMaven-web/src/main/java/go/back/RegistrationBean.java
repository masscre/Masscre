package go.back;

import javax.faces.bean.ManagedBean;
import com._4thex.glassfish.security.UserService;
import go.model.User;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RegistrationBean {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;


    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public RegistrationBean() {
        
    }
    
    public void registerUser(String name, char[] password, String firstName, String lastName, String email) throws Exception {
        String[] grouplist = new String[1];
        String usergroup = "user";
        grouplist[0] = usergroup;
        com._4thex.glassfish.security.UserService.getService().addUser(name, password, grouplist);
        User u = new User(name, firstName, lastName, email);
        databaseBean.registerUser(u);
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
    
    public void register() throws Exception {
        System.out.println("RegistrationBean: registering user");
            String username = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("username");
            String firstName = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("firstname");
            String lastName = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("lastname");
            String email = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("email");
	    String password = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("password");
            String passwordcheck = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("passwordcheck");
            if (password.equals(passwordcheck)) {
                registerUser(username, password.toCharArray(), firstName, lastName, email);
            }
	}
    
}
