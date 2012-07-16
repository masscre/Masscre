package go.service;
 
import go.back.DatabaseBean;
import go.logic.DatabaseConnector;
import javax.faces.bean.ManagedProperty;
import javax.jws.WebService;

@WebService(endpointInterface = "go.service.ActiveAccounts")
public class ActiveAccountsImpl implements ActiveAccounts{
    
        DatabaseConnector dc = new DatabaseConnector();
    
	@Override
	public String getActiveAccounts() {
		return "Number of accounts: "+dc.getNumberOfAccounts();
	}
        
        //http://localhost:9998/ws/hello?wsdl
 
}