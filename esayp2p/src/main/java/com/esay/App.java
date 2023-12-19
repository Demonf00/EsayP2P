package com.esay;

import com.esay.client.Client;
import com.esay.esayurl.esayurl;
import com.esay.frame.EsayFrame;
import com.esay.server.Server;
import com.esay.utility.Cipher;
import com.esay.utility.EsayFile;

import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 * Hello world!
 */
public final class App {
    private Client client;
    private Server server;
    public boolean loopFlag;
    private EsayFrame gui;
    private final int client_limit = 1;
    private final int server_limit = 1;
    private int client_cnt;
    private int server_cnt;
    private boolean scEnable;
    private final int PORT = 2266;

    private App() {
        this.GUIstart();
        this.client = new Client(gui);
        this.server = new Server(2266, gui);
        loopFlag = true;
        client_cnt = 0;
        server_cnt = 0;
        scEnable = true;
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        App app = new App();

        System.out.println("Client or Server:");
        while (app.loopFlag);
        app.scEnable = false;
    }

    public void GUIstart(){
        this.gui = new EsayFrame(this);
    }

    public void startClient() {
        if(!scEnable) return;
        if(client_cnt >= client_limit) return;
        client.start();
        client_cnt++;
    }

    public void startServer() {
        if(!scEnable) return;
        if(server_cnt >= server_limit) return;
        System.out.println("--------------------------------");
        System.out.println(" Send following code to client.");
        System.out.println("--------------------------------");
        Cipher crypto = new Cipher(getIp() + ":" + PORT);
        System.out.println(crypto.getCipheredText());
        System.out.println("--------------------------------");
        server.start();
        server_cnt++;
    }

    public String getIp() {
        try {
            return esayurl.getIp();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
