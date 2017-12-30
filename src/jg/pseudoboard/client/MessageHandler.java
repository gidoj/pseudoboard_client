package jg.pseudoboard.client;

import jg.pseudoboard.common.BoardElement;
import jg.pseudoboard.common.MessageElement;
import jg.pseudoboard.common.MessageTypeConverter;

public class MessageHandler {
	
	private Canvas canvas;
	
	private String server = "localhost";//"www.shelbington.com";
	private int port;
	
	private boolean connected;
	
	private ClientToServer comm;
	
	private String username;
	private int id;
	
	private volatile boolean waitingForResponse;
	private volatile int response;
	
	public MessageHandler(int port) {
		this.port = port;
		connected = false;
	}
	
	public ConnectionStatus connect(String username, int id, boolean newUser) {
		//try connecting to server and sending user
		//info -- if new user and not unique info,
		//disconnect and request new username or id
		comm = new ClientToServer(this, server, port);
		long start = System.nanoTime(); //ns
		while (!comm.isRunning()) {
			long curr = System.nanoTime(); //ns
			double difference = (curr - start) / 1e6; //ms
			//if waiting to connect for more than 10 seconds
			//then return error
			if (difference > 10000) {
				System.out.println("Error connecting to server.");
				return ConnectionStatus.CONNECTION_ERROR;
			}
		}
		String userString = username + ";" + id;
		int response = -1;
		if (newUser) comm.send(new MessageElement(userString, MessageTypeConverter.MessageType.LOGIN_NEW_USER));
		else comm.send(new MessageElement(userString, MessageTypeConverter.MessageType.LOGIN_EXISTING_USER));
		response = checkLogin();
		if (response == 1) {
			connected = true;
			return ConnectionStatus.LOGIN_SUCCESS;
		}
		disconnect(false);
		return ConnectionStatus.LOGIN_FAIL;
	}
	
	private int checkLogin() {
		//wait for server response on whether login is success or not
		waitingForResponse = true;
		while (waitingForResponse) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
	
	public void recieveElement(BoardElement elt) {
		switch (elt.getType()) {
		case MESSAGE_TO_SERVER:
			handleServerMessage(elt);
			break;
		case CANVAS:
			//TODO: update client canvas -- handle if not connected
			if (connected) {
				canvas.updateCanvas(elt.getData());
			}
			break;
		default:
			break;
		}
	}
	
	private void handleServerMessage(BoardElement elt) {
		switch (MessageTypeConverter.getMessageType(elt.getID())) {
		case LOGIN_SUCCESS:
			System.out.println("Login successful.");
			response = 1;
			waitingForResponse = false;
			break;
		case LOGIN_FAIL:
			System.out.println("Login failed.");
			response = 0;
			waitingForResponse = false;
			break;
		default:
			break;
		}
	}
	
	public void disconnect(boolean serverDisconnected) {
		if (!serverDisconnected) {
			//TODO: data is empty string atm -- consider including some relevant info
			//      before disconnect
			comm.send(new MessageElement("", MessageTypeConverter.MessageType.DISCONNECT));
			comm.disconnect();
		}
		connected = false;
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getID() {
		return id;
	}
}
