package com.esay.client;

import com.esay.transfer.TextMessage;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends Thread{

    Scanner inputScanner;
    boolean running = false;
    private Socket clienSocket;
    private OutputStream output;
 	private InputStream input;
    private static final int BUFFER_SIZE = 1024;
    private static final int DROP_SIZE = 1024 * BUFFER_SIZE;

    public void run() {
        try {
            inputScanner = new Scanner(System.in);
            String addressWithPort;
            
            while(!isRunning()){
                System.out.println("Adress with port:");     
                addressWithPort = inputScanner.nextLine();
                String[] tokens = addressWithPort.split(":");
                if (tokens.length != 2) continue;
                int port = Integer.parseInt(tokens[1]);
                try {
                    System.out.println(tokens[0] + ":" + port);
                    newConnection(tokens[0], port);         
                } catch (Exception e) {
                    ;
                }
            }
            output = clienSocket.getOutputStream();
            input = clienSocket.getInputStream();

            while(isRunning()){
                try {
                    TextMessage latestMsg = receiveMessage();
                    System.out.println(latestMsg.getMsg());
                } catch (Exception e) {
                    if (isRunning()) {
                        System.out.println("Connection lost!!!");
                        try {
                            tearDownConnection();
                        } catch (Exception f) {
                            System.out.println("Unable to close the connection!!!");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Client running error");
        } finally {
            if (inputScanner != null) {
                inputScanner.close();
            }
            if (isRunning()) {
                closeConnection();
            }
        }
    }

    public void newConnection(String hostname, int port) throws Exception{
        try {
            clienSocket = new Socket(hostname, port);
            setRunning(true);
        } catch (Exception e) {
            System.out.println("Cannot connect to it");
        }
    }

    public synchronized void closeConnection() {
        try {
            tearDownConnection();
        } catch (IOException ioe) {
            System.out.println("Connection closing error");
        }
    }

    public void tearDownConnection() throws IOException{
        setRunning(false);
        if (clienSocket != null){
            clienSocket.close();
            clienSocket = null;
        }
    }

    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean value){
        running = value;
    }

    	/**
	 * Method sends a TextMessage using this socket.
	 * @param msg the message that is to be sent.
	 * @throws IOException some I/O error regarding the output stream 
	 */
	public void sendMessage(TextMessage msg) throws IOException {
		byte[] msgBytes = msg.getMsgBytes();
		output.write(msgBytes, 0, msgBytes.length);
		output.flush();
		System.out.println("Send message:\t '" + msg.getMsg() + "'");
    }
	
	
	private TextMessage receiveMessage() throws IOException {
		
		int index = 0;
		byte[] msgBytes = null, tmp = null;
		byte[] bufferBytes = new byte[BUFFER_SIZE];
		
		/* read first char from stream */
		byte read = (byte) input.read();	
		boolean reading = true;
		
		while(read != 13 && reading) {/* carriage return */
			/* if buffer filled, copy to msg array */
			if(index == BUFFER_SIZE) {
				if(msgBytes == null){
					tmp = new byte[BUFFER_SIZE];
					System.arraycopy(bufferBytes, 0, tmp, 0, BUFFER_SIZE);
				} else {
					tmp = new byte[msgBytes.length + BUFFER_SIZE];
					System.arraycopy(msgBytes, 0, tmp, 0, msgBytes.length);
					System.arraycopy(bufferBytes, 0, tmp, msgBytes.length,
							BUFFER_SIZE);
				}

				msgBytes = tmp;
				bufferBytes = new byte[BUFFER_SIZE];
				index = 0;
			} 
			
			/* only read valid characters, i.e. letters and numbers */
			if((read > 31 && read < 127)) {
				bufferBytes[index] = read;
				index++;
			}
			
			/* stop reading is DROP_SIZE is reached */
			if(msgBytes != null && msgBytes.length + index >= DROP_SIZE) {
				reading = false;
			}
			
			/* read next char from stream */
			read = (byte) input.read();
		}
		
		if(msgBytes == null){
			tmp = new byte[index];
			System.arraycopy(bufferBytes, 0, tmp, 0, index);
		} else {
			tmp = new byte[msgBytes.length + index];
			System.arraycopy(msgBytes, 0, tmp, 0, msgBytes.length);
			System.arraycopy(bufferBytes, 0, tmp, msgBytes.length, index);
		}
		
		msgBytes = tmp;
		
		/* build final String */
		TextMessage msg = new TextMessage(msgBytes);
		System.out.println("Receive message:\t '" + msg.getMsg() + "'");
		return msg;
    }
}
