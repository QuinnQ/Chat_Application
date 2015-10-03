package Client_Chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Client_UI {
	JTextArea textarea;
	JTextArea private_textarea;
	JButton button;
	JTextField userText;
	JTextField chat_w_text;
	JTextField chat_w_content;
	JLabel chat_w_label;
	JLabel chat_w_meglabel;
	DefaultListModel listModel; 
	JList userList;
	JScrollPane left;
	JScrollPane right;
	JPanel south;
	JPanel chat_w_Panel;
	JPanel north;
	JScrollPane private_chat_Panel;
	JSplitPane chatSplit;
	JSplitPane centerSplit; 
	JFrame frame;
	
	public Client_UI(){
        frame = new JFrame("Client");  
        textarea = new JTextArea();  
        textarea.setEditable(false);  
        textarea.setForeground(Color.blue);  
	    textarea.setBackground(Color.PINK);
	    textarea.setLineWrap(true);
        
        private_textarea = new JTextArea();
        private_textarea.setEditable(false);
        private_textarea.setForeground(Color.blue);
	    private_textarea.setBackground(Color.PINK);
	    private_textarea.setLineWrap(true);
        
        chat_w_label = new JLabel("Please List the names: ");
        chat_w_text = new JTextField();
        chat_w_text.setEditable(true);
//        chat_w_text.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e){
//        		String str = chat_w_text.getText();
//        		dispatcherPrivateUsers(str);
//        	}
//        });
        
        chat_w_meglabel = new JLabel("Send message:");
        chat_w_content = new JTextField();
        chat_w_content.setEditable(true);
//        chat_w_content.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e){
//        		sendPrivate();
//        	}
//        });
        
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
	     
	      
	      listModel = new DefaultListModel();  
	      userList = new JList(listModel); 
	      left = new JScrollPane(userList);
	      left.setBorder(new TitledBorder("Online Users")); 

	      right = new JScrollPane(textarea);
	      right.setBorder(new TitledBorder("Public Message Display"));  
	  
	      private_chat_Panel = new JScrollPane(private_textarea);
	      private_chat_Panel.setBorder(new TitledBorder("Private Message Display"));  
	      chatSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, right, private_chat_Panel); 
	      chatSplit.setDividerLocation(250); 
	      centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, chatSplit); 
	      centerSplit.setDividerLocation(100); 
	      
	      south= new JPanel(new BorderLayout());  
	      south.setBorder(new TitledBorder("Write messages to all clients"));  
	      south.add(userText, "Center");  
	      south.add(button, "East"); 
	      
	      chat_w_Panel = new JPanel(new BorderLayout());
	      chat_w_Panel.add(chat_w_meglabel, "West");
	      chat_w_Panel.add(chat_w_content, "Center");
	      
	      north = new JPanel(new BorderLayout());
	      north.setBorder(new TitledBorder("Private Chat"));
	      north.add(chat_w_label, "West");
	      north.add(chat_w_text, "Center");
	      north.add(chat_w_Panel, "South");
	      
	      frame.setLayout(new BorderLayout());
	      frame.add(north, "North");
	      frame.add(centerSplit, "Center");
	      frame.add(south, "South");
	      frame.setSize(600, 400); 
	      //How to show the screen in the middle of the computer screen
	      int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
	      int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;  
	      frame.setLocation((screen_width - frame.getWidth()) / 2,  (screen_height - frame.getHeight()) / 2); 
	      frame.setResizable(false);
	      frame.setVisible(true);
//	      frame.addWindowListener(new WindowListener(){
//
//			@Override
//			public void windowActivated(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void windowClosed(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void windowIconified(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void windowOpened(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void windowClosing(WindowEvent e) {
//				if (isConnected) {
//					closeConnect();
//				}
//				System.exit(0);
//			}
//	      });
	}
	
	public void setFramename(String name){
		frame.setTitle(name);
		frame.repaint();
	}
	
	public void showErrorMessage(String mes){
		JOptionPane.showMessageDialog(frame, mes, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}
}
