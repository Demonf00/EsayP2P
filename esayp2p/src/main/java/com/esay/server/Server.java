package com.esay.server;
import java.net.ServerSocket;
import java.util.Scanner;
import java.net.Socket;

public class Server {

    Scanner inputScanner;
    public int port;
    public boolean running;
    public ServerSocket serverSocket;

    public Server (int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            // this.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void run() {

    }
}
