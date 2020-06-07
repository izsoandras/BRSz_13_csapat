package UI.toplist;


import java.io.Serializable;

public class Entry implements Serializable {
    private String name;
    private int points;


    public Entry(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
