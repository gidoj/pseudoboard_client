package jg.pseudoboard.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import jg.pseudoboard.common.BoardElement;

public class ClientToServer implements Runnable {
	
	private MessageHandler mh;
	
	private volatile boolean running;
	
	private String server;
	private int port;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	
	private Thread run, send, listen;
	
	public ClientToServer(MessageHandler mh, String server, int port) {
		this.mh = mh;
		this.server = server;
		this.port = port;
		running = false;
		run = new Thread(this, "run");
		run.start();
	}

	@Override
	public void run() {
		try {
			//get input/output streams and start listening for messages from server
			socket = new Socket(server, port);
			System.out.println("Connected to " + server + " on port " + port + ".");
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			running = true;
			listen();
		} catch (UnknownHostException e) {
			System.out.println("Error connecting to server.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error connecting to server.");
			e.printStackTrace();
		}
	}
	
	public void listen() {
		listen = new Thread("listen") {
			public void run() {
				while (running) {
					try {
						BoardElement elt = (BoardElement) in.readObject();
						mh.recieveElement(elt);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						//if not running, then successfully disconnected from server
						//otherwise error, disconnect client
						if (!running) {
							System.out.println("Socket closed. Disconnected from server.");
							return;
						}
						mh.disconnect(true);
						running = false;
					}
				}
				
			}
		};
		listen.start();
	}
	
	public void send(BoardElement elt) {
		send = new Thread("send") {
			public void run() {
				try {
					out.writeObject(elt);
				} catch (IOException e) {
					System.out.println("Error sending element to server.");
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	public void disconnect() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	

}
