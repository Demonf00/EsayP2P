package com.esay;

import com.esay.client.Client;
import com.esay.server.Server;
import java.util.Scanner;


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
}
