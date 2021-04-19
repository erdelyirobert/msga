package board;

public class Connections {
    private Pair connection1;    //kapcsolat
    private Pair connection2;


    public Connections(Pair connection1, Pair connection2) {
        this.connection1 = connection1;
        this.connection2 = connection2;
    }

    public Pair getConnection1() {
        return connection1;
    }

    public void setConnection1(Pair connection1) {
        this.connection1 = connection1;
    }

    public Pair getConnection2() {
        return connection2;
    }

    public void setConnection2(Pair connection2) {
        this.connection2 = connection2;
    }
}
