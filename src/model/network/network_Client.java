package model.network;
        import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;
        import model.map.Labyrinth;

        import model.map.Labyrinth;

        import java.net.Socket;
        import java.net.SocketAddress;

public class network_Client extends network_core {
    //Private variables
    private String IP_address;

    //Public variables



    //Private functions



    //Public functions

    public network_Client(String IP_addr)  {
        super();
        IP_address = IP_addr;

    }
//    public void network_Client(String IP_addr, int portnum){
//        GameSocket = new Socket(IP_addr, portnum);
//    }

    public void run(){
        Running = true;
        String str="test";
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
                return;
            }else{
                System.out.println("Server validated successfully\n");
            }

            Connected = true;
        } catch (IOException e) {
            Running = false;
            Connected = false;
            e.printStackTrace();
        }
        //TO-DO GAMESETTINGS RECIEVING

        while( Running ){
            try {
                if(Reader.ready()) {
                    Opponent_labyrinth.Labyrinth_data = (Labyrinth) Obj_inputstream.readObject();
                    Obj_outputstream.writeObject(Local_labyrinth);
                }
            } catch (Exception e) {
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
