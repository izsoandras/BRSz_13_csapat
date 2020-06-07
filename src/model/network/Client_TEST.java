package model.network;

import model.map.Labyrinth;
import model.util.LabyrinthType;

import java.io.IOException;


public class Client_TEST {
    public static void main(String[] args) {
        Labyrinth client_lab = new Labyrinth(LabyrinthType.WALLESS);

        //___________________________________________________________________________________________________________________

        //___________________________________________________________________________________________________________________
        Game_status client_status = new Game_status();
        network_Client Test_Client;

        Test_Client = new network_Client("192.168.0.27");


        Thread thread = new Thread(Test_Client);
        thread.start();

        //___________________________________________________________________________________________________________________

        //___________________________________________________________________________________________________________________

        while(!Test_Client.isConnected() ||Test_Client.isServerInvalid()){

        }

        //___________________________________________________________________________________________________________________

        //___________________________________________________________________________________________________________________
        //System.out.println("%b",Test_Server.isRunning());
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
