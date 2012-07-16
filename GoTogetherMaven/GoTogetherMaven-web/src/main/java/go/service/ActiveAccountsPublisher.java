package go.service;
 
import javax.xml.ws.Endpoint;

public class ActiveAccountsPublisher{
 
	public static void main(String[] args) {            
	   try {
               Endpoint.publish("http://localhost:9999/ws/activeaccounts", new ActiveAccountsImpl());
           } catch (Exception e) {}
    }
 
}
