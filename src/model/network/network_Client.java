package model.network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import model.map.Labyrinth;
import model.map.LabyrinthMemento;
import model.util.LabyrinthType;

import java.net.Socket;
import java.net.SocketAddress;

public class network_Client extends network_core {
    //Private variables
    private String IP_address;
    private boolean Server_invalid;
    private boolean Opponentlabyrinth_updated, OpponentStatus_updated;
    private boolean Serverparams_available;


    //Public variables

    //Private functions

    //Public functions

    public network_Client(String IP_addr)  {
        super();
        set_clientIP(IP_addr);
        Server_invalid = false;
        Opponentlabyrinth_updated = false;
        OpponentStatus_updated = false;
        Serverparams_available = false;
    }

    public void set_clientIP(String IP_addr){
        Server_invalid = false;
        IP_address = IP_addr;
    }

    public boolean isServerInvalid(){
        return Server_invalid;
    }

    public boolean isParamsAvailable(){
        return Serverparams_available;
    }

    public boolean isOpponentlabUpdated(){
        return Opponentlabyrinth_updated;
    }

    public LabyrinthType getServerLabType(){
        return LabType;
    }

    public int getGameSpeed(){
        return Gamespeed;
    }

    @Override
    public Labyrinth Get_Opponent_labyrinth(){
        Labyrinth temp = super.Get_Opponent_labyrinth();
        Opponentlabyrinth_updated = false;
        return temp;
    }
    @Override
    public Game_status Get_Opponent_Status(){
        Game_status temp = super.Get_Opponent_Status();
        OpponentStatus_updated = false;
        return temp;

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
        String readymsg = "Initvalue";
        Running = true;
        Local_Ready = false;
        Opponent_Ready = false;
        Server_invalid = false;
        Serverparams_available = false;
        String str;
        try {
            GameSocket = new Socket(IP_address, 22222);
        } catch (IOException e) {
            Running = false;
            Connected = false;
            Server_invalid = true;
            //e.printStackTrace();
            System.out.println("Network exited 1");
            return;
        }
        try{
            //STREAMS
            //Outputs
            Obj_outputstream = new ObjectOutputStream(GameSocket.getOutputStream());
            Writer = new PrintWriter(GameSocket.getOutputStream());

            //Inputs
            inputstream = new InputStreamReader(GameSocket.getInputStream());
            Obj_inputstream = new ObjectInputStream(GameSocket.getInputStream());
            Reader = new BufferedReader(inputstream);

            str = Reader.readLine();
            Writer.println("Henlo");
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
            //e.printStackTrace();
            System.out.println("Network exited 2");
            return;
        }
        //Receive server game parameters
        try {
            while(!Reader.ready()){
                Thread.sleep(100);
            }
            if(Reader.ready()) {
                String msg = Reader.readLine();
                //System.out.println("Received: " + msg);
                String[] messageparts = msg.split("@");
                switch (messageparts[0]) {
                    case "1":
                        LabType = LabyrinthType.WALLED;
                        break;
                    case "2":
                        LabType = LabyrinthType.EXTRA;
                        break;
                    default:
                        LabType = LabyrinthType.WALLESS;
                        break;
                }
                //System.out.println(messageparts[0] + " labtype converted to: " + LabType);

                try {
                    Gamespeed = Integer.parseInt(messageparts[1]);
                }
                catch (NumberFormatException e){
                    Gamespeed = 5;
                }
                //System.out.println(messageparts[1] + " speed converted to: " + Gamespeed);
                Serverparams_available = true;
                System.out.println("Labyrinth data accuired for settings!\n");
            }
        } catch (Exception e) {
            Running = false;
            Connected = false;
            e.printStackTrace();
            return;
        }
        //READY
        try {
            while(!readymsg.equals("Ready")){
                readymsg = Reader.readLine();
            }
            System.out.println("Opponent Ready!");
            Opponent_Ready = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!Local_Ready) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Local Ready!");
        Writer.println("Ready");
        Writer.flush();

        // PROGRAM RUNNING
        while( Running ){
            try {
                if((inputstream.ready() && !Opponentlabyrinth_updated && !OpponentStatus_updated) ) {
                    //System.out.println(" " + Reader.ready() + "\n");

                    Opponent_labyrinth = (network_labyrinth) Obj_inputstream.readObject();
                    if(Opponent_labyrinth.Status.Exited){
                        Local_labyrinth.Status.Exited = true;
                    }
                    Obj_outputstream.writeObject(Local_labyrinth);

                    Opponentlabyrinth_updated = true;
                    OpponentStatus_updated = true;

                    System.out.println(Local_labyrinth.Labyrinth_data.getSnakeMemento().getHead().getX() +
                            " and " + Local_labyrinth.Labyrinth_data.getSnakeMemento().getHead().getY() + " || " +
                            Opponent_labyrinth.Labyrinth_data.getSnakeMemento().getHead().getX() +
                            " and " + Opponent_labyrinth.Labyrinth_data.getSnakeMemento().getHead().getY());
                    //System.out.printf("Labyrinth updated!\n");
                }
                if ( Local_labyrinth.Status.Exited && Opponent_labyrinth.Status.Exited){
                    Running = false;
                    System.out.println("Network thread closed.");
                }
            } catch (Exception e) {
                Running = false;
                Connected = false;
                e.printStackTrace();
            }

        }

        Running = false;
        Connected = false;
    }
}
