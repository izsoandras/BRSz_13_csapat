package model.network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import model.map.Labyrinth;
import model.map.LabyrinthMemento;

import model.map.Labyrinth;

import java.net.Socket;

public class network_Server extends network_core {
    //Private variables

    protected ServerSocket Server_socket;

    //Public variables



    //Private functions



    //Public functions

    public network_Server(){
        super();
        try {
            Server_socket = new ServerSocket(22222);
        }catch(IOException e){
            e.printStackTrace();
        }

        //System.out.println("Child constr run\n");
    }

//    public void network_Server(int portnumber) throws IOException {
//        Server_socket = new ServerSocket(portnumber);
//    }

    public synchronized void UpdateLocallabyrinth(Labyrinth newlabyrinth, Game_status newstatus) {
        Local_labyrinth.Labyrinth_data = new LabyrinthMemento(newlabyrinth);
        Local_labyrinth.Status = newstatus;
        Locallabyrinth_updated = true;
        Sync_signal.doNotify();
    }

    public void run(){
        Running = true;
        System.out.println("Server started\n");
        while(!Connected) {
                try {
                    GameSocket = Server_socket.accept();
                } catch (IOException e) {
                    Running = false;
                    Connected = false;
                    e.printStackTrace();
                    return;
                }
                System.out.println("Server recieved client connection\n");
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
            Locallabyrinth_updated = false;
            System.out.println("Labyrinth data provided for settings!");
        } catch (Exception e) {
            e.printStackTrace();
            Running = false;
            Connected = false;
            return;
        }



        while( Running ){
            //Wait for local labyrinth to get updated
            while(!Locallabyrinth_updated){
                Sync_signal.doWait();
            }
            //send local lab and receive client lab
            try {
                Obj_outputstream.writeObject(Local_labyrinth);
                Opponent_labyrinth = (network_labyrinth) Obj_inputstream.readObject();
                Locallabyrinth_updated = false;
            } catch (Exception e) {
                e.printStackTrace();
                Running = false;
                Connected = false;
                return;
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
