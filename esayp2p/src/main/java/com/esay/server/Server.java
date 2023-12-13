package com.esay.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Scanner;

import com.esay.frame.EsayFrame;
import com.esay.utility.EsayFile;
import com.simtechdata.waifupnp.UPnP;

import java.net.Socket;

public class Server extends Thread{

    Scanner inputScanner;
    public int port;
    private boolean running;
    public ServerSocket serverSocket;
    private EsayFrame gui;

    public Server (EsayFrame gui) {
        this.port = 2266;
        this.gui = gui;
    }
    public Server (int port, EsayFrame gui) {
        this.port = port;
        this.gui = gui;
    }

    public void startServer() {
        try {
            System.out.println("Server listening to port: " + port);
            this.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void shutServer () {
        this.running = false;
    }

    public void run () {
        try {
            if(!UPnP.openPortTCP(port)){
                System.out.println("Port forwarding failed.");
            } else {
                System.out.println("Port forwarding success.");
            }
            serverSocket = new ServerSocket(port);
            this.running = true;
            while (this.running) {
                System.out.println("Waiting for the client request");
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                EsayFile file = (EsayFile) ois.readObject();
                System.out.println("Message Received.");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                // EsayFile file = new EsayFile();
                // file.readFile("C:\\Users\\bruce\\Documents\\Projects\\EsayP2P\\anya.png");
                file.writeFile("C:\\Users\\bruce\\Documents\\Projects\\EsayP2P\\receivedimg.jpg");
                oos.writeObject(file);
                ois.close();
                oos.close();
                socket.close();
                // if(message.equalsIgnoreCase("exit")) break;
            }
            System.out.println("Shutting down Socket server!!");
            serverSocket.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Unable to open server.");
            e.printStackTrace();
        }
    }

    public boolean isRunning(){
        return this.running;
    }
}
