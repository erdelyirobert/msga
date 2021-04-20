package ThemePark;

public class Trash {
    private int location_X;
    private int location_Y;

    public Trash(int location_X, int location_Y) {
        this.location_X = location_X;
        this.location_Y = location_Y;
    }

    public int getLocation_X() {
        return location_X;
    }

    public void setLocation_X(int location_X) {
        this.location_X = location_X;
    }

    public int getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(int location_Y) {
        this.location_Y = location_Y;
    }
}
