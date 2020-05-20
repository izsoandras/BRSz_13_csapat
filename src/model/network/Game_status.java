package model.network;

public class Game_status {
    public boolean Ready;
    public boolean Finished;
    public boolean  Paused;
    public boolean Exited;

    public Game_status(){
        Ready = false;
        Finished = false;
        Paused = false;
        Exited = false;
    }
}
