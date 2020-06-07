package UI.toplist;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class TopList {
    //private static final int LIST_LENGTH = 10;
    private static final String FILE_NAME = "toplist.ser";
    private List<Entry> entries;

    public TopList(){
        reload();
    }

    public boolean insert(Entry newEntry){
        if(entries.isEmpty()){
            entries.add(newEntry);
            saveTopList();
            return true;
        }

        int i = 0;
        while(newEntry.getPoints() < entries.get(i).getPoints() && i < entries.size()){
            i++;
        }

        if(i < entries.size()){
            entries.add(i,newEntry);
//            if(entries.size() > LIST_LENGTH) {
//                entries.remove(entries.size() - 1);
//            }
            saveTopList();
            return true;
        }else{
            return false;
        }
    }

    public void reload(){
        readToplist();
    }

    private void readToplist(){
        try {
            File topListFile = new File(FILE_NAME);
            FileInputStream topListFIS = new FileInputStream(topListFile);
            ObjectInputStream topListOIS = new ObjectInputStream(topListFIS);
            entries = (List<Entry>) topListOIS.readObject();
            topListOIS.close();
            topListFIS.close();
        }catch (FileNotFoundException fnf){
            entries = new LinkedList<>();
        }catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            entries = null;
        }
    }

    private void saveTopList(){
        try{
            File topListFile = new File(FILE_NAME);
            FileOutputStream topListFOS = new FileOutputStream(topListFile);
            ObjectOutputStream topListOOS = new ObjectOutputStream(topListFOS);
            topListOOS.writeObject(entries);
            topListOOS.close();
            topListFOS.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
