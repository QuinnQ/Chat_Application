package Client_Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;


public class Client {
	private Socket client;
	private BufferedReader input;
	private PrintWriter output;
	private Client_UI ui;
	private String clientName;
	private Map<String, User> map;
	private boolean isConnected;
	
	public Client(Client_UI ui, String hostIP, int port, String name){
		this.ui = ui;
		setUpUI();
		map = new HashMap<String, User>();
		listenSocket(hostIP, port, name);
		clientName = name;
	}
	
	public void setUpUI(){
		ui.chat_w_content.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sendPrivate();
			}
		});
		ui.userText.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				send();  
			}  
		}); 
		ui.button.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						send();
					}
				}
				);
		
		ui.frame.addWindowListener(new WindowListener(){

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
				if (isConnected) {
					closeConnect();
				}
				System.exit(0);
			}
		});
	}
	
	public void listenSocket(String hostIP, int port, String name){
        System.out.println("Establishing connection. Please wait ...");
        try
        {
        	client = new Socket(hostIP, port);
            System.out.println("Connected: " + client);
            input = new BufferedReader (new InputStreamReader (client.getInputStream()));
            output  = new PrintWriter (client.getOutputStream(), true);
			sendMessage(name + "@" + client.getLocalAddress().toString());
			ui.setFramename(name);
			isConnected = true;
			while(isConnected){
				dispatcherMessage();
			}
        }catch(UnknownHostException uhe){
			ui.textarea.append("Port: " + port + "    IP address: " + hostIP
					+ "   Host unknown." + "\r\n");
			isConnected = false;
        }catch(IOException ioe){
			ui.textarea.append("Port: " + port + "    IP address: " + hostIP
					+ "   fails to connect." + "\r\n");
			isConnected = false;
        }            
	}
	
	/*
	 * Close connection
	 */
	public void closeConnect() {
		try {
			sendMessage("CLOSE");
			if (input != null) {
				input.close();
			}
			if (client!= null) {
				client.close();
			}
			isConnected = false;
		} catch (IOException e1) {
			e1.getMessage();
		}
	}
	
	/*
	 * Send public message
	 */
	public void send() {
		if (!isConnected) {
			ui.showErrorMessage("Not connected");
			return;
		}
		String message = ui.userText.getText().trim();
		if (message == null || message.equals("")) {
			ui.showErrorMessage("No empty msg");
			return;
		}
		if (message.equals("Bye.")){
			closeConnect();
		}
		sendMessage(ui.frame.getTitle() + "@" + "ALL" + "@" + message);
//		textarea.append("Me" + " : " + message + "\r\n");
		ui.userText.setText(null);
	}
	
	/*
	 * Send private message
	 */
	public void sendPrivate(){
		if (!isConnected) {
			ui.showErrorMessage("Not connected");
			return;
		}
		//for private chat: choose the people to chat with
		String users = dispatcherPrivateUsers(ui.chat_w_text.getText().trim());
		if(users == null || users.equals("")){
			ui.showErrorMessage("No empty user");
			return;
		}
		
		String message = ui.chat_w_content.getText().trim();
		if (message == null || message.equals("")) {
			ui.showErrorMessage("No empty msg");
			return;
		}
		if (message.equals("Bye.")){
			closeConnect();
		}
		sendMessage(ui.frame.getTitle() + "@" + "Private" + "@" +users + "@" + message);
		ui.private_textarea.append("Me" + " : " + message + "\r\n");
		ui.chat_w_content.setText(null);
	}
	
	private String dispatcherPrivateUsers(String str){
		String t = "";
		if(!str.isEmpty()){
		StringTokenizer stringtoken = new StringTokenizer(str, ", //@");
		while(stringtoken.hasMoreElements()){
			t += stringtoken.nextElement() + "/";
			}
		}
		return t;
	}
	
	private void sendMessage(String message) {
		output.println(message);
		output.flush();
	}
	
    public void dispatcherMessage() {  
		String line;
		try {
			if(input == null) return;
			line = input.readLine();
			if(line == null) return;
			StringTokenizer stringTokenizer = new StringTokenizer(
					line, "/@");
			//command line always sent as the marker character
			String command = stringTokenizer.nextToken();
			if (command.equals("CLOSE"))
			{
				ui.textarea.append("Server shut.\r\n");
				closeConnect();
				return;
			} else if (command.equals("ADD")) {
				String username = "";
				String userIP = "";
				if ((username = stringTokenizer.nextToken()) != null
						&& (userIP = stringTokenizer.nextToken()) != null) {
					User user = new User(username, userIP);
					map.put(username, user);
					ui.listModel.addElement(username);
				}
			} else if (command.equals("DELETE")) {
				String username = stringTokenizer.nextToken();
				User user = (User) map.get(username);
				map.remove(user);
				ui.listModel.removeElement(username);
			} else if (command.equals("USERLIST")) {
				int size = Integer.parseInt(stringTokenizer.nextToken());
				String username = null;
				String userIp = null;
				for (int i = 0; i < size; i++) {
					username = stringTokenizer.nextToken();
					userIp = stringTokenizer.nextToken();
					User user = new User(username, userIp);
					map.put(username, user);
					ui.listModel.addElement(username);
				}
			} else if(command.equals("[PRIVATE]")){
				ui.private_textarea.append(stringTokenizer.nextToken() + "\r\n");
			} else {
				ui.textarea.append(stringTokenizer.nextToken() + "\r\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
    }
}
