package model.network;

import model.map.Labyrinth;
import model.util.LabyrinthType;

import java.io.IOException;

import static model.util.LabyrinthType.WALLED;


public class Server_TEST {
    public static void main(String[] args) throws IOException {
        Labyrinth server_lab = new Labyrinth(LabyrinthType.WALLESS);

        //___________________________________________________________________________________________________________________

        //___________________________________________________________________________________________________________________
        Game_status server_status = new Game_status();
        network_Server Test_Server = new network_Server();

        Thread thread = new Thread(Test_Server);
        thread.start();

        while(!Test_Server.isRunning()){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //___________________________________________________________________________________________________________________

        //___________________________________________________________________________________________________________________
        //System.out.printf("Server running!\n");
        while(!Test_Server.isConnected()){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.printf("Pushing labyrinth for data...\n");
        Test_Server.UpdateServerParameters(LabyrinthType.EXTRA, 10);


        //___________________________________________________________________________________________________________________

        //___________________________________________________________________________________________________________________
        //System.out.printf("%b",Test_Server.isRunning());
        while(Test_Server.isRunning()){
            try {
                //System.out.printf("Waiting...\n");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Test_lab = Test_Server.Get_Opponent_labyrinth();
            //Test_lab.getOpponentStatus();
            Test_Server.UpdateLocallabyrinth(server_lab, server_status);
            System.out.println("Local_labyrinth sent!\n");

        }
    }
}
