package Server_Chat;

import javax.swing.JOptionPane;

public class Server_Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String i = JOptionPane.showInputDialog("Please Enter port: ", JOptionPane.OK_CANCEL_OPTION);
		int port = Integer.parseInt(i);
	    Server_UI serverUI = new Server_UI();
		Server server = new Server(serverUI, port);		
	}

}
