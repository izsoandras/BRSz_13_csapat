package model.network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import model.map.Labyrinth;
import model.map.LabyrinthMemento;

import java.net.Socket;
import java.net.SocketAddress;

public class network_Client extends network_core {
    //Private variables
    private String IP_address;
    private boolean Server_invalid;
    private boolean Opponentlabyrinth_updated;


    //Public variables

    //Private functions

    //Public functions

    public network_Client(String IP_addr)  {
        super();
        set_clientIP(IP_addr);

    }

    public void set_clientIP(String IP_addr){
        Server_invalid = false;
        IP_address = IP_addr;
    }

    public boolean isServerInvalid(){
        return Server_invalid;
    }

    public boolean isOpponentlabUpdated(){
        return Opponentlabyrinth_updated;
    }

    @Override
    public Labyrinth Get_Opponent_labyrinth(){
        Opponentlabyrinth_updated = false;
        return super.Get_Opponent_labyrinth();
    }

//    public void network_Client(String IP_addr, int portnum){
//        GameSocket = new Socket(IP_addr, portnum);
//    }
    public synchronized void UpdateLocallabyrinth(Labyrinth newlabyrinth, Game_status newstatus) {
        Local_labyrinth.Labyrinth_data = new LabyrinthMemento(newlabyrinth);
        Local_labyrinth.Status = newstatus;
        //Locallabyrinth_updated = true;
        //Sync_signal.doNotify();
    }

    //__________________________________________________________________________________________________________________
    public void run(){
        Running = true;
        Server_invalid = false;
        String str;
        try {
            GameSocket = new Socket(IP_address, 22222);
            //STREAMS
            //Outputs
            Obj_outputstream = new ObjectOutputStream(GameSocket.getOutputStream());
            Writer = new PrintWriter(GameSocket.getOutputStream());

            //Inputs
            inputstream = new InputStreamReader(GameSocket.getInputStream());
            Obj_inputstream = new ObjectInputStream(GameSocket.getInputStream());
            Reader = new BufferedReader(inputstream);

            str = Reader.readLine();
            Writer.println("Henlo\n");
            Writer.flush();
            if(!str.equals( "Henlo") ) {
                System.out.println("Server not valid\n");
                Running = false;
                Server_invalid = true;
                return;
            }else{
                System.out.println("Server validated successfully\n");
            }

            Connected = true;
        } catch (IOException e) {
            Running = false;
            Connected = false;
            Server_invalid = true;
            e.printStackTrace();
        }

        //Receive server labyrinth to get maze data
        try {
            while(!Reader.ready()){
                Thread.sleep(100);
            }
            if(Reader.ready()) {
                Opponent_labyrinth = (network_labyrinth) Obj_inputstream.readObject();
                System.out.println("Labyrinth data accuired for settings!\n");
            }
        } catch (Exception e) {
            Running = false;
            Connected = false;
            e.printStackTrace();
        }

        while( Running ){
            try {
                if(inputstream.ready()) {
                    System.out.println(" " + Reader.ready() + "\n");
                    Opponent_labyrinth = (network_labyrinth) Obj_inputstream.readObject();
                    Obj_outputstream.writeObject(Local_labyrinth);
                    //System.out.printf("Labyrinth updated!\n");
                }
            } catch (Exception e) {
                Running = false;
                Connected = false;
                e.printStackTrace();
            }
            if(Opponent_labyrinth.Status.Exited || Local_labyrinth.Status.Exited){
                Running = false;
            }
        }

        Running = false;
        Connected = false;
    }
}
