package ThemePark;

public class Person {
    private String personImages;
    private int location_X;
    private int location_Y;
    private int buildingsSizesA;
    private int buildingsSizesB;

    public Person(String personImages, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        this.personImages = personImages;
        this.location_X = location_X;
        this.location_Y = location_Y;
        this.buildingsSizesA = buildingsSizesA;
        this.buildingsSizesB = buildingsSizesB;
    }

    public String getPersonImages() {
        return personImages;
    }

    public int getLocation_X() {
        return location_X;
    }

    public int getLocation_Y() {
        return location_Y;
    }

    public int getBuildingsSizesA() {
        return buildingsSizesA;
    }

    public int getBuildingsSizesB() {
        return buildingsSizesB;
    }

    public void setLocation_X(int location_X) {
        this.location_X = location_X;
    }

    public void setLocation_Y(int location_Y) {
        this.location_Y = location_Y;
    }
}
