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

        Client client;
        Server server;
        Scanner inputScanner = new Scanner(System.in);
        
        boolean loopFlag = true;
        String option = null;
        while (loopFlag){
            System.out.println("Client or Server:");
            option = inputScanner.nextLine();
            

            if (option.equals("client")){
                loopFlag = false;
                
            }

            else if (option.equals("server")){
                loopFlag = false;
                
            }
        }

        System.out.println(option);

    }
}
