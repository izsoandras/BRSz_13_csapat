package model.network;

import java.io.IOException;


public class Client_TEST {
    public static void main(String[] args) {

        network_Client Test_Client;
        try {
            Test_Client = new network_Client("192.168.0.27");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Thread thread = new Thread(Test_Client);
        thread.start();

        //System.out.printf("%b",Test_Server.isRunning());
        while (Test_Client.isRunning()) {

        }
    }
}
