package model.network;

public class Game_status {
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
