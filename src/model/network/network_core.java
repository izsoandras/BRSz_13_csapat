package model.network;
import java.io.*;
import java.net.Socket;
import java.util.Timer;

import model.map.Labyrinth;


public abstract class network_core implements Runnable{
    //Private variables

    //protected Timer Networktimer;
    protected Socket GameSocket;
    protected network_labyrinth Local_labyrinth;
    protected network_labyrinth Opponent_labyrinth;
    protected Boolean Running, Connected, Locallabyrinth_updated;
    protected InputStreamReader inputstream;
    protected ObjectInputStream Obj_inputstream;
    protected OutputStream outputstream;
    protected ObjectOutputStream Obj_outputstream;
    protected BufferedReader Reader;
    protected PrintWriter Writer;

    //Public variables


    //Private functions


    //Public functions
    public void UpdateLocallabyrinth(Labyrinth newlabyrinth) {
        Local_labyrinth.Labyrinth_data = newlabyrinth;
        Locallabyrinth_updated = true;
    }

    public Labyrinth Get_Opponent_labyrinth(){
        return Opponent_labyrinth.Labyrinth_data;
    }

    public Boolean isRunning(){
        return Running;
    }

}
