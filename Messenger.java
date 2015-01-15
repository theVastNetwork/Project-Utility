package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class Messenger implements ActionListener {

	private JFrame frame = new JFrame();
	private JTextPane typeLabel = new JTextPane();
	private static JLabel messengerLabel = new JLabel();
	private JButton button = new JButton("Send");
	public static boolean closed = false;
	public String text = "";
	public String messengerText = "";
	private int firstPass = 0;
	public String name;
	public static boolean sendMessage = false;

	public Messenger() {
		name = JOptionPane.showInputDialog("What is your name?");
		typeLabel.setText("Type here...");
		messengerLabel.setText(messengerText);
		messengerLabel.setVerticalAlignment(JLabel.TOP);
		messengerLabel.setPreferredSize(new Dimension(250, 250));
		button.addActionListener(this);
		frame.setSize(500, 500);
		frame.setTitle("Messenger Version: 0.0      " + "User: " + name);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.add(messengerLabel, BorderLayout.NORTH);
		frame.add(typeLabel, BorderLayout.CENTER);
		frame.add(button, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				closed = true;
			}
		});
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button)) {
			text = "";
			text += typeLabel.getText();
			typeLabel.setText("");
			if (firstPass == 0){
				String withoutHtml = messengerLabel.getText().substring(0,messengerLabel.getText().length());
				messengerText = "<html>" + withoutHtml;
				messengerText += name + ": " + text + "</html>";
				firstPass++;
			}
			else{
				String withoutHtml = messengerLabel.getText().substring(6,messengerLabel.getText().length() - 7);
				messengerText = "<html>" + withoutHtml;
				messengerText += "<br>" + name + ": "+ text + "</html>";
			}
			messengerLabel.setText(messengerText);
			sendMessage = true;
		}

	}
	
	public static String getMessageText(){
		return messengerLabel.getText();
	}
	
	public static void setText(String text){
		messengerLabel.setText(text);
	}

}
