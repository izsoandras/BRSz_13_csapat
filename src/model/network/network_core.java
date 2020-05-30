package model.network;
import java.io.*;
import java.net.Socket;
import java.util.Timer;

import model.map.Labyrinth;


public abstract class network_core implements Runnable{
    //Private variables

    protected Updatesignal Sync_signal;
    protected Socket GameSocket;
    protected network_labyrinth Local_labyrinth;
    protected network_labyrinth Opponent_labyrinth;
    protected Boolean Running;
    protected volatile Boolean Connected, Locallabyrinth_updated;
    protected InputStreamReader inputstream;
    protected ObjectInputStream Obj_inputstream;
    protected OutputStream outputstream;
    protected ObjectOutputStream Obj_outputstream;
    protected BufferedReader Reader;
    protected PrintWriter Writer;

    //Public variables


    //Private functions


    //Public functions
    public network_core(){
        Running = false;
        Connected = false;
        Locallabyrinth_updated = false;
        Local_labyrinth = new network_labyrinth();
        Opponent_labyrinth = new network_labyrinth();
        Sync_signal = new Updatesignal();
        //System.out.printf("Parent constructor run\n");
    }

    public synchronized void UpdateLocallabyrinth(Labyrinth newlabyrinth, Game_status newstatus) {
        Local_labyrinth.Labyrinth_data = newlabyrinth;
        Local_labyrinth.Status = newstatus;
        Locallabyrinth_updated = true;
        Sync_signal.doNotify();
    }

    public Labyrinth Get_Opponent_labyrinth(){
        return Opponent_labyrinth.Labyrinth_data;
    }

    public Game_status Get_Opponent_Status(){
        return Opponent_labyrinth.Status;
    }

    public Boolean isRunning(){
        return Running;
    }

    public Boolean isConnected(){
        return Connected;
    }


}
