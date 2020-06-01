package model.network;

public class Game_status implements java.io.Serializable{
    public volatile boolean Ready;
    public volatile boolean Finished;
    public volatile boolean  Paused;
    public volatile boolean Exited;

    public Game_status(){
        Ready = false;
        Finished = false;
        Paused = false;
        Exited = false;
    }
}
