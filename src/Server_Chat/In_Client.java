package Server_Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;



public class In_Client extends Thread {  
    private Socket socket;  
    private BufferedReader reader;  
    private ClientList clients;
    private PrintWriter writer;  
    private User user;   
	private JTextArea textarea;
	private DefaultListModel listModel; 
	private boolean isConnected;

    public In_Client(ClientList clients, Socket socket,JTextArea textarea, DefaultListModel listModel) {  
    	this.clients = clients;
    	this.textarea = textarea;
    	this.listModel = listModel;
    	isConnected = true;
        try {  
            this.socket = socket;  
            reader = new BufferedReader(new InputStreamReader(socket  
                    .getInputStream()));  
            writer = new PrintWriter(socket.getOutputStream()); 
            
            //read the first line which provide the name and IP of client to server
            String inf = reader.readLine();  
            StringTokenizer st = new StringTokenizer(inf, "@");  
            user = new User(st.nextToken(), st.nextToken());  
            //sent the message out to the client attempt to do the connection
            writer.println(user.getName() + user.getIp() + "connects with server!");  
            writer.flush();  
           
            String temp = clients.sentClientListToNewClient();
            if(temp.length() != 0){
                writer.println("USERLIST@" + clients.getSize() + "@" + temp);  
                writer.flush();
            }
            clients.publish_newUserOnline(user);
             
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    public BufferedReader getReader() {  
        return reader;  
    }  

    public PrintWriter getWriter() {  
        return writer;  
    }  

    public User getUser() {  
        return user;  
    } 
    
    public void run() {
        String message = null;  
        while (isConnected) {  
            try {  
                message = reader.readLine();
                if (message.equals("CLOSE")){ 
                	System.out.println("Get CLOSE commend! from user.");
                    textarea.append(this.getUser().getName()  
                            + this.getUser().getIp() + " is offline!\r\n");   
                    remove();
                    clients.publish_UserOffline(user);
                    //remove the offline client from the GUI listModel list
                    listModel.removeElement(user.getName()); 
                    clients.remove_Client(user);   
                    isConnected = false;
                } else {  
                    dispatcherMessage(message);
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
    /**
     * this method is used to dispatch the message sent by client
     * @param message
     */
    public synchronized void dispatcherMessage(String message) {  
        StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
        //source--which client sent this message
        String source = stringTokenizer.nextToken();  
        //owner--"ALL" the charater to show the type of chat
        String owner = stringTokenizer.nextToken();  
        //content--messages sent by the client
        String content = stringTokenizer.nextToken();   
        if (owner.equals("ALL")) { 
            message = source + " : " + content;  
            textarea.append(message + "\r\n"); 
            clients.publishPublicMessage(message);
        }else if(owner.equals("Private")){
        	String chat_content = stringTokenizer.nextToken();
        	message = source + " : " + chat_content;
        	
        	textarea.append("[PRIVATE]" + message + "\r\n");
//        	StringTokenizer strtoken = new StringTokenizer(content, "/");
//        	while(strtoken.hasMoreTokens()){
//        		int index = clients.findClient(strtoken.nextToken());
//        		if(index != -1){
//        			clientList.get(index).getWriter().println("[PRIVATE]@" + message);
//        			clientList.get(index).getWriter().flush();
//        		}
//        	}
        	clients.publishPrivateMessage(content, message);
        }
    }
    
    public void remove(){
    	try{
	        reader.close();  
	        writer.close();  
	        socket.close(); 
    	}catch(IOException e){
    		e.getMessage();  
    	}
    }
}
