package com.esay.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Scanner;
import java.net.Socket;

public class Server extends Thread{

    Scanner inputScanner;
    public int port;
    private boolean running;
    public ServerSocket serverSocket;

    public Server () {
        this.port = 5968;
    }
    public Server (int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            this.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void shutServer () {
        this.running = false;
    }

    public void run () {
        try {
            serverSocket = new ServerSocket(port);
            this.running = true;
            while (this.running) {
                System.out.println("Waiting for the client request");
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message Received: " + message);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("Hi Client "+message);
                ois.close();
                oos.close();
                socket.close();
                if(message.equalsIgnoreCase("exit")) break;
            }
            System.out.println("Shutting down Socket server!!");
            serverSocket.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
