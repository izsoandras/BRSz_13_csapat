package model.network;

import model.map.Labyrinth;
import model.util.LabyrinthFactory;

import java.io.IOException;


public class Client_TEST {
    public static void main(String[] args) {
        Labyrinth client_lab = LabyrinthFactory.createWallessLabyrinth();
        Game_status client_status = new Game_status();
        network_Client Test_Client;

        Test_Client = new network_Client("192.168.0.21");


        Thread thread = new Thread(Test_Client);
        thread.start();

        //System.out.printf("%b",Test_Server.isRunning());
        while (Test_Client.isRunning()) {
            while(!Test_Client.isConnected()){
            }
            Test_Client.UpdateLocallabyrinth(client_lab, client_status);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
