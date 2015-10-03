package Client_Chat;

import java.io.IOException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Client_Main {

	public static void main(String[] args) throws IOException{
		Client_UI ui = new Client_UI();


		JPanel pop_message = new JPanel();
		JTextField ip = new JTextField(10);
		ip.setText("127.0.0.1");
		JTextField port = new JTextField(6);
		JTextField username = new JTextField(10);

		pop_message.add(new JLabel("IP:"));
		pop_message.add(ip);
		pop_message.add(Box.createVerticalStrut(30)); 
		pop_message.add(new JLabel("Port:"));
		pop_message.add(port);
		pop_message.add(new JLabel("Username: "));
		pop_message.add(username);
		int result = JOptionPane.showConfirmDialog(null, pop_message, 
				"Please Enter: ", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String address = ip.getText();
			int portN = Integer.parseInt(port.getText());
			String name = username.getText();
			Client c = new Client(ui, address, portN, name);
		}else{
			JOptionPane.showMessageDialog(null, "Input invalid!!");
		}


	}

}
