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
        while(!Connected) {
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
                    //Outputs
                    Obj_outputstream = new ObjectOutputStream(GameSocket.getOutputStream());
                    Obj_outputstream.flush();
                    Writer = new PrintWriter(GameSocket.getOutputStream());

                    //Inputs
                    inputstream = new InputStreamReader(GameSocket.getInputStream());
                    Obj_inputstream = new ObjectInputStream(GameSocket.getInputStream());
                    Reader = new BufferedReader(inputstream);

                    System.out.println("Verifying Client...\n");
                    Writer.println("Henlo\n");
                    Writer.flush();
                    String str = Reader.readLine();
                    if (str.equals("Henlo")) {
                        System.out.println("Client verified!\n");
                        Connected = true;
                    }else{
                        System.out.println("Client verifcation failed! Listening to other incoming connections...\n");
                        GameSocket.close();

                    }
                } catch (IOException e) {
                    Running = false;
                    Connected = false;
                    e.printStackTrace();
                    return;
                }
        }
        //TO-DO GAMESETTINGS SENDING

        while(!Locallabyrinth_updated){
            Sync_signal.doWait();
        }

        try {
            //sending local labyrinth with game data to client
            Obj_outputstream.writeObject(Local_labyrinth);
        } catch (Exception e) {
            e.printStackTrace();
        }



        while( Running ){
            //Wait for local labyrinth to get updated
            while(!Locallabyrinth_updated){
                Sync_signal.doWait();
            }
            //send local lab and receive client lab
            try {
                Obj_outputstream.writeObject(Local_labyrinth);
                Opponent_labyrinth.Labyrinth_data = (Labyrinth) Obj_inputstream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //if either guy exits game is over
            if(Opponent_labyrinth.Status.Exited || Local_labyrinth.Status.Exited){
                Running = false;
            }
        }

        Running = false;
        Connected = false;
    }

}
