package com.esay;

import com.esay.client.Client;
import com.esay.frame.EsayFrame;
import com.esay.server.Server;
import com.esay.utility.EsayFile;

import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        int PORT = 2266;

        GUIstart();

        Client client = new Client();
        Server server = new Server(2266);
        Scanner inputScanner = new Scanner(System.in);

        boolean loopFlag = true;
        String option = null;
        while (loopFlag){
            System.out.println("Client or Server:");
            option = inputScanner.nextLine();


            if (option.equals("client")){
                loopFlag = false;
                client.start();
            }

            else if (option.equals("server")){
                loopFlag = false;
                server.startServer();
            }
        }

        System.out.println(option);

    }

    public static void GUIstart(){
        EsayFrame frame = new EsayFrame();
    }
}
