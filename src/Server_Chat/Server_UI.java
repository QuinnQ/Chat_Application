package Server_Chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Server_UI {
	//UI elements
	JTextArea textarea;
	JButton button;
	JTextField userText;
	DefaultListModel listModel; 
	JList userList;
	JScrollPane left;
	JScrollPane right;
	JPanel south;
    JSplitPane centerSplit; 
    JFrame frame;
//    private ClientList clients;
//    private ArrayList<In_Client> clientList;
    
    public Server_UI(){
		//GUI
//    	clients = list;
//    	clientList = list;
    	
        frame = new JFrame("Server");  
        textarea = new JTextArea();  
        textarea.setEditable(false);  
        textarea.setForeground(Color.blue);  
        
	      userText = new JTextField();
	      userText.setEditable(true);
//	      userText.addActionListener(new ActionListener() {  
//	            public void actionPerformed(ActionEvent e) {  
//	                send();  
//	            }  
//	        }); 
	      
	      button = new JButton("Send");
//	      button.addActionListener(
//	 	         new ActionListener(){
//	 	            public void actionPerformed(ActionEvent arg0){
//	 	            	send();
//	 	            }
//	 	         }
//	 	      );
	      
	      textarea = new JTextArea();
	      textarea.setBackground(Color.PINK);
	      textarea.setLineWrap(true);
	      textarea.setEditable(false);
	      
	      //User JList show the active clients
	      listModel = new DefaultListModel();  
	      userList = new JList(listModel); 
	      left = new JScrollPane(userList);
	      left.setBorder(new TitledBorder("Online Users")); 

	      right = new JScrollPane(textarea);
	      right.setBorder(new TitledBorder("Message display"));  
	  
	      //JSplitPane to separate JList and JTextArea
	      centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left,right);  
	      centerSplit.setDividerLocation(100); 
	      
	      south= new JPanel(new BorderLayout());  
	      south.setBorder(new TitledBorder("Write messages to all clients"));  
	      south.add(userText, "Center");  
	      south.add(button, "East"); 
	      frame.setLayout(new BorderLayout()); 
	      frame.add(centerSplit, "Center");
	      frame.add(south, "South");
	      frame.setSize(600, 400); 
	      //How to show the screen in the middle of the computer screen
	      int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
	      int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;  
	      frame.setLocation((screen_width - frame.getWidth()) / 2,  (screen_height - frame.getHeight()) / 2); 
	      frame.setResizable(false);
	      frame.setVisible(true);
    }
    
	public void showErrorMessage(String mes){
		JOptionPane.showMessageDialog(frame, mes, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}
}
