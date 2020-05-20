package model.network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import model.map.Labyrinth;

import model.map.Labyrinth;

import java.net.Socket;

public class network_Server extends network_core {
    //Private variables

    protected ServerSocket Server_socket;

    //Public variables



    //Private functions



    //Public functions

    public network_Server() throws IOException {
        super();
        Server_socket = new ServerSocket( 22222 );
        //System.out.printf("Child constr run\n");
    }

//    public void network_Server(int portnumber) throws IOException {
//        Server_socket = new ServerSocket(portnumber);
//    }

    public void run(){
        Running = true;
        System.out.printf("Server started\n");
        try {
            GameSocket = Server_socket.accept();
        } catch (IOException e) {
            Running = false;
            Connected = false;
            e.printStackTrace();
            return;
        }
        System.out.printf("Server recieved client connection\n");
        try {
            //STREAMS
            //Inputs
            inputstream = new InputStreamReader(GameSocket.getInputStream());
            Obj_inputstream = new ObjectInputStream(GameSocket.getInputStream());
            Reader = new BufferedReader(inputstream);
            //Outputs
            Obj_outputstream = new ObjectOutputStream(GameSocket.getOutputStream());
            Writer = new PrintWriter(GameSocket.getOutputStream());


            Writer.println("Henlo\n");
            String str = Reader.readLine();
            if(str.equals( "Henlo") ) {
                System.out.println("Client connected");
            }
            Connected = true;
        } catch (IOException e) {
            Running = false;
            Connected = false;
            e.printStackTrace();
            return;
        }

        //TO-DO GAMESETTINGS SENDING


        while( Running ){
            if(Locallabyrinth_updated) {
                try {
                    Obj_outputstream.writeObject(Local_labyrinth);

                    Opponent_labyrinth.Labyrinth_data = (Labyrinth) Obj_inputstream.readObject();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(Opponent_labyrinth.Status.Exited || Local_labyrinth.Status.Exited){
                Running = false;
            }
        }

        Running = false;
        Connected = false;
    }

}