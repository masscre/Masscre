package go.service;
 
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import go.service.ActiveAccounts;
import go.service.ActiveAccounts;
 
public class ActiveAccountsClient{
 
	public static void main(String[] args) throws Exception {
 
	URL url = new URL("http://localhost:9999/ws/activeaccounts?wsdl");
 
        QName qname = new QName("http://service.go/", "ActiveAccountsImplService");
 
        Service service = Service.create(url, qname);
 
        ActiveAccounts activeAccounts = service.getPort(ActiveAccounts.class);
 
        System.out.println(activeAccounts.getActiveAccounts());
 
    }
 
}