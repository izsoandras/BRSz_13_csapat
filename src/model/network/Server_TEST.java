package model.network;

import model.map.Labyrinth;
import model.util.LabyrinthType;

import java.io.IOException;


public class Server_TEST {
    public static void main(String[] args) throws IOException {
        Labyrinth server_lab = new Labyrinth(LabyrinthType.WALLESS);
        Game_status server_status = new Game_status();
        network_Server Test_Server;
        Test_Server = new network_Server();

        Thread thread = new Thread(Test_Server);
        thread.start();

        //System.out.printf("%b",Test_Server.isRunning());
        while(Test_Server.isRunning()){
            while(!Test_Server.isConnected()){
            }
            Test_Server.UpdateLocallabyrinth(server_lab, server_status);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
