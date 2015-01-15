package main;

import gui.Messenger;
import messageClient.ChatMessage;
import messageClient.Message;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Main extends Listener{

	private static Client client;
	private static String ip = "localhost";
	private static int host = 9090, clientPort = 9090;
	private static boolean failed = false;
	private Messenger m = new Messenger();
	
	public static void main(String[] args)throws Exception{
		client = new Client();
		client.getKryo().register(Message.class);
		client.getKryo().register(ChatMessage.class);
		client.start();
		client.connect(5000, ip, clientPort, host);
		
		client.addListener(new Main());
		
		while (!Messenger.closed){
			if (Messenger.sendMessage){
				Message message = new Message();
				message.message = Messenger.getMessageText();
				client.sendTCP(message);
				 Messenger.sendMessage = false;
			}
		}
		System.exit(0);
	}
	
	public void received(Connection c, Object p){
		if (p instanceof Message){
			Message message = (Message) p;
			System.out.println("Client got a message: " + message.message);
		}
		if (p instanceof ChatMessage){
			ChatMessage message = (ChatMessage)p;
			Messenger.setText(message.message);
		}
	}
	
}
