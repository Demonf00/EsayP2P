package com.esay.client;

import com.esay.frame.EsayFrame;
import com.esay.transfer.TextMessage;
import com.esay.utility.Cipher;
import com.esay.utility.EsayFile;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private EsayFrame gui;

    public Client(EsayFrame gui) {
        this.gui = gui;
    }

    public void run() {
        try {
            inputScanner = new Scanner(System.in);
            String addressWithPort;

            while(!isRunning()){
                System.out.println("Adress with port:");
                addressWithPort = gui.getInputText();
                Cipher crypto = new Cipher(addressWithPort);
                String[] tokens = crypto.getCipheredText().split(":");
                // String[] tokens = new String[2];
                // tokens[0] = "173.33.71.22";
                // tokens[1] = "2266";
                if (tokens.length != 2) continue;
                int port = Integer.parseInt(tokens[1]);
                Socket socket = null;
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;

                System.out.println("Connecting to server address " + tokens[0] + ", port# " + port);
                boolean continue_query = true;
                socket = new Socket(tokens[0], port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                while(continue_query){
                    String sendPath = null;
                    EsayFile file = new EsayFile();
                    String fileName = null;
                    while(gui.getAddingFile() == null){
                        // System.out.println("Input file path: ");
                        // File directory = new File("C:\\");
                        // JFileChooser chooser = new JFileChooser(directory);
                        // chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        // int status = chooser.showOpenDialog(null);
                        // if (status == JFileChooser.APPROVE_OPTION) {
                        //     File afile = chooser.getSelectedFile();
                        //     if (afile == null) {
                        //         continue;
                        //     }

                        //     fileName = chooser.getSelectedFile().getAbsolutePath();
                        //     break;
                        // }
                    }
                    fileName = gui.getAddingFile();
                    file.readFile(fileName);
                    oos.writeObject(file);
                    System.out.println("SEND");
                    file = (EsayFile) ois.readObject();
                    System.out.println("RECV");
                    String receivePath = null;
                    while(gui.getSavingDirectory() == null){
                        // File directory = new File("C:\\");
                        // JFileChooser chooser = new JFileChooser(directory);
                        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        // int status = chooser.showOpenDialog(null);
                        // if (status == JFileChooser.APPROVE_OPTION) {
                        //     File afile = chooser.getSelectedFile();
                        //     if (afile == null) {
                        //         continue;
                        //     }

                        //     fileName = chooser.getSelectedFile().getAbsolutePath();
                        //     break;
                        // }

                        // receivePath = gui.getInputText();
                        // if(new File(receivePath).isDirectory()){break;}
                        // else{
                        //     System.out.println("Invalid path.");
                        // }
                    }
                    fileName = gui.getSavingDirectory();
                    System.out.println(fileName + "/received_file");
                    file.writeFile(fileName + "/received_file");
                    Thread.sleep(100);
                    while(true){
                        System.out.println("Send file again? (y/n)");
                        String response = gui.getInputText();
                        if(response.equals("n")){continue_query = false; break;}
                        else if(response.equals("y")){break;}
                        else{System.out.println("Invalid response: " + response);}
                    }
                }
                ois.close();
                oos.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Client running error");
            e.printStackTrace();
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
