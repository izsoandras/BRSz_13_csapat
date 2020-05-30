package model.network;

public class Updatesignal {
    Signal_object mySignalObject = new Signal_object();
    boolean was_signalled = false;

    public void doWait(){
        synchronized(mySignalObject){
            while(!was_signalled){
                try{
                    mySignalObject.wait();
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            //clear signal and continue running.
            was_signalled = false;
        }
    }

    public void doNotify(){
        synchronized(mySignalObject){
            was_signalled = true;
            mySignalObject.notify();
        }
    }
}
