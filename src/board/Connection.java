package board;

public class Connection { // 1 él két road között
    private Pair first; //első pont x és y
    private Pair second; //második pont x és y

    public Connection(Pair first, Pair second) {
        this.first = first;
        this.second = second;
    }

    public Pair getFirst() {
        return first;
    }

    public void setFirst(Pair first) {
        this.first = first;
    }

    public Pair getSecond() {
        return second;
    }

    public void setSecond(Pair second) {
        this.second = second;
    }
}
