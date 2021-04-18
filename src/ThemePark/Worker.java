package ThemePark;

public class Worker extends Person{

    private int salary = 200;
    private int direction = 0;

    public Worker(String personImages, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        super(personImages, location_X, location_Y, buildingsSizesA, buildingsSizesB);
    }

    public int getSalary() {
        return salary;
    }

    public void maintain(){};
    public void clean(){};

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
