package Server_Chat;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClientList {
    private Vector<In_Client> clientList;
    
    public ClientList(){
    	clientList = new Vector<In_Client>();
    }
    
    public void add_Client(In_Client newClient){
    	clientList.add(newClient);
    }
    
    public String toPendingMessage(){
    	String temp = "";
    	for (int i = clientList.size() - 1; i >= 0; i--) {  
            temp += (clientList.get(i).getUser().getName() + "/" + clientList  
                    .get(i).getUser().getIp())  
                    + "@";  
        } 
    	return temp;
    }
    
    /*
     * This Method use to send new add client current online clients info list
     */
    public String sentClientListToNewClient(){
        //also sent the clientlist info to this client
    	String temp = "";
        if (clientList.size() > 0) {  
            //convert the whole list to a string
            for (int i = clientList.size() - 1; i >= 0; i--) {  
                temp += (clientList.get(i).getUser().getName() + "/" + clientList  
                        .get(i).getUser().getIp())  
                        + "@";  
            }  
            //sent out the client size and string with a command line at the front
        } 
        return temp;
    }
    
    /*
     * This Method use to send public message
     */
    public void publishPublicMessage(String message){
        for (int i = clientList.size() - 1; i >= 0; i--) {  
            clientList.get(i).getWriter().println("[PUBLIC]@" + message);  
            clientList.get(i).getWriter().flush();  
        } 
    }
    
    /*
     * This Method use to send private message
     */
    public void publishPrivateMessage(String content, String message){
    	StringTokenizer strtoken = new StringTokenizer(content, "/");
    	while(strtoken.hasMoreTokens()){
    		int index = findClient(strtoken.nextToken());
    		if(index != -1){
    			System.out.println("Send private message from CLientList!");
    			clientList.get(index).getWriter().println("[PRIVATE]@" + message);
    			clientList.get(index).getWriter().flush();
    		}
    	}
    }
    
    public int findClient(String clientname){
    	int num = -1;
    	for(int i = clientList.size()-1; i>=0; i--){
    		if(clientList.get(i).getUser().getName().equals(clientname)){
    			num = i;
    		}
    	}
    	return num;
    }
    
    /*
     * This Method use to send all client client online
     */
    public void publish_newUserOnline(User user){
    	//finally sent all the user that a new client added
        for (int i = clientList.size() - 1; i >= 0; i--) {  
        	clientList.get(i).getWriter().println(  
                    "ADD@" + user.getName() + user.getIp());  
        	clientList.get(i).getWriter().flush();  
        } 
    }
    
    /*
     * This Method use to send all client client offline
     */
    public void publish_UserOffline(User user){
	    //sent message"DELETE@username" to all the client
	    for (int i = clientList.size() - 1; i >= 0; i--) {  
	        clientList.get(i).getWriter().println(  
	                "DELETE@" + user.getName());  
	        clientList.get(i).getWriter().flush();  
	    } 
	    

    }
    
    public int getSize(){
    	return clientList.size();
    }
    
    public void remove_Client(User user){
  	  //remove the offline client from the clientlist
        for (int i = clientList.size() - 1; i >= 0; i--) {  
            if (clientList.get(i).getUser() == user) {  
                clientList.remove(i);
                return;  
            }  
        }
    }
    
    public void removeAll(){
        for (int i = clientList.size() - 1; i >= 0; i--) {  
                clientList.get(i).remove();
        }
        clientList.removeAllElements();
        
    }
}
