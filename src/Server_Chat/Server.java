package Server_Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Server {
	private Server_UI serverUI;
	private ServerSocket server;
	private Socket in_C = null;
	private ClientList clients;
	
	public Server(Server_UI ui, int port){
		serverUI = ui;
		setUpUI();
		clients = new ClientList();
		listenSocket(port);
	}
	
	public void setUpUI(){
		serverUI.userText.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                send();  
            }  
        }); 
	    serverUI.button.addActionListener(
		 	         new ActionListener(){
		 	            public void actionPerformed(ActionEvent arg0){
		 	            	send();
		 	            }
		 	         }
		 	      );
		serverUI.frame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				closeServer();
				System.exit(0);
			}
		});
	}
	
	
	public void listenSocket(int port){
		
		server = null; 

	    try { 
	         server = new ServerSocket(port); 
	         System.out.println ("Connection Socket Created");
	         System.out.println ("Socket open on Port: " +
	                             server.getLocalPort());
	         InetAddress addr = InetAddress.getLocalHost();
	         System.out.println("Java InetAddress localHost info: " + addr);
	         System.out.println("Local Host Name: " + addr.getHostName());
	         //TODO server timeout showed some example in EchoServer2d.java
	         try { 
	              while (true)
	                 {
	                  System.out.println ("Waiting for Connection");
	                  in_C = server.accept();    
	                   In_Client client = new In_Client(clients, in_C, serverUI.textarea, serverUI.listModel); 
	                   //send online list to new client
	                   client.start(); 
	                   clients.add_Client(client);
	                   
	                   //send updated online list to all clients
	                   serverUI.listModel.addElement(client.getUser().getName());  
	                   serverUI.textarea.append(client.getUser().getName()  
	                            + client.getUser().getIp() + " is online\r\n");  
	                 }
	             } 
	         catch (IOException e) 
	             { 
	        	  showMessage("Accept failed");
	              System.err.println("Accept failed."); 
	              System.exit(1); 
	             } 
	        } 
	    catch (IOException e) 
	        { 
	    	 showMessage("MSg 1: Could not listen on port");
	         System.err.println("MSg 1: Could not listen on port"); 
	         System.exit(1); 
	        } 
	    finally
	        {
	         try {
	              closeServer(); 
	             }
	         catch (Exception e)
	             { 
	        	  showMessage("MSG 2: Could not close port");
	              System.err.println("MSG 2: Could not close port"); 
	              System.exit(1); 
	             } 
	        }		
	}
	
	public void showMessage(final String mesg){
		SwingUtilities.invokeLater(
	            new Runnable()
	            {
	                public void run()
	                {
	                	serverUI.textarea.append(mesg);
	                }
	            }
	        );
	}
	
	public void send(){
        if (clients.getSize() == 0) {  
        	//Use JOptionPane show Error message
            JOptionPane.showMessageDialog(serverUI.frame, "No Active User!", "ERROR",  
                    JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
        String message = serverUI.userText.getText().trim();  
        if (message == null || message.equals("")) {  
            JOptionPane.showMessageDialog(serverUI.frame, "No empty msg!", "ERROR",  
                    JOptionPane.ERROR_MESSAGE);  
            return;  
        }
        clients.publishPublicMessage("Server says:" + message  + "(Multiple users)");
//        sendServerMessage(message);
        serverUI.textarea.append("Server: " + serverUI.userText.getText() + "\r\n");  
        serverUI.userText.setText(null);  
	}
	
    public void closeServer() {  
        try {  
        	//close all the socket connected with server
            clients.removeAll();   
            server.close(); 
            serverUI.listModel.removeAllElements(); 
        } catch (IOException e) {  
            e.printStackTrace();   
        }  
    }
}
