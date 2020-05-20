package model.network;

import java.io.IOException;


public class Server_TEST {
    public static void main(String[] args) throws IOException {

        network_Server Test_Server;
        Test_Server = new network_Server();

        Thread thread = new Thread(Test_Server);
        thread.start();

        //System.out.printf("%b",Test_Server.isRunning());
        while(Test_Server.isRunning()){

        }
    }
}
