package com.esay.client;
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
                newConnection(tokens[0], port);
            }

            output = clienSocket.getOutputStream();
            input = clienSocket.getInputStream();

            while(isRunning()){
                
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
        clienSocket = new Socket(hostname, port);
        setRunning(true);
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
}
