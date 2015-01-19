/*
	USE ECLIPSE TO COMPILE AND RUN
	THIS AS A RUNNABLE JAR FILE.
	GOOGLE THE INSTRUCTIONS, BUT IT
	IS SUPER EASY, FAST, AND EFFICIENT.
*/


package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Main implements Runnable, ActionListener{

	private JFrame frame = new JFrame("Editor: Beta Version 0.00");
	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	private JTextPane editorArea = new JTextPane();
	private Thread mainThread = new Thread(this, "RUNNING LOOP");
	private Style defaultStyle = editorArea.addStyle("Defualt", null);
	private JMenuBar bar = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenuItem open = new JMenuItem("Open");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem save = new JMenuItem("Save");
	private String fileContents = "";
	private String fileName;
	
	public Main(){
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponents();
		frame.setVisible(true);
		mainThread.start();
	}
	
	public void addComponents(){
		
		exit.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		file.add(exit);
		file.add(open);
		file.add(save);
		bar.add(file);
		
		editorArea.setBackground(new Color(64,64,64));
		editorArea.setForeground(new Color(255,255,255).brighter());
		frame.add(editorArea, BorderLayout.CENTER);
		frame.add(bar, BorderLayout.NORTH);
	}
	
	public void addSyntaxHighlighting(){
		findKeywords();
	}
	
	private void findKeywords(){
		StyledDocument doc = editorArea.getStyledDocument();
		Style keywordStyle = editorArea.addStyle("Keywords", null);
		StyleConstants.setForeground(keywordStyle, new Color(0,100,255).brighter());
		
		Style brackets = editorArea.addStyle("Brackets", null);
		StyleConstants.setForeground(brackets, new Color(0,255,100).brighter());
		
		Style textS = editorArea.addStyle("Text", null);
		StyleConstants.setForeground(textS, new Color(255,0,0).brighter());

		doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
		
		String regx = "attribute|value|_value";
		String bracketsReg = "\\{|\\}|\\<|\\>";
		String textReg = " \" ";
		
		String text = editorArea.getText();
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(text);
		while (m.find()){
			doc.setCharacterAttributes(m.start(), (m.end() - m.start()), keywordStyle, true);
		}
		
		Pattern p1 = Pattern.compile(bracketsReg);
		Matcher m1 = p1.matcher(text);
		while (m1.find()){
			doc.setCharacterAttributes(m1.start(), (m1.end() - m1.start()), brackets, true);
		}
		
		Pattern p2 = Pattern.compile(textReg);
		Matcher m2 = p2.matcher(text);
		while (m2.find()){
			doc.setCharacterAttributes(m2.start(), (m2.end() - m2.start()), textS, true);
		}
	}
	
	public static void main(String[] args) {
		Main m = new Main();
	}

	@Override
	public void run() {
		while (true){
			try{
				Thread.sleep(500);
				addSyntaxHighlighting();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exit)){
			System.exit(0);
		}
		
		if (e.getSource().equals(open)){
			loadFile();
		}
		
		if (e.getSource().equals(save)){
			saveFile();
		}
	}

	public void loadFile(){
		try{
			String name = JOptionPane.showInputDialog("Enter a file name");
			fileName = name;
			BufferedReader reader = new BufferedReader(new FileReader(new File(name)));
			String line;
			while ((line = reader.readLine()) != null){
				fileContents += line + "\n";
			}
			editorArea.setText(fileContents);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void saveFile(){
		if (fileName == null){
			fileName = JOptionPane.showInputDialog("Save Where?");
		}
		try{
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.write(editorArea.getText());
			writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
