package ThemePark;

import java.util.Random;

public class Guest extends Person{

    Random random = new Random();


    private int mood = 20;
    private int direction = 0;

    public Guest(String personImages, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        super(personImages, location_X, location_Y, buildingsSizesA, buildingsSizesB);
    }

    /* ChangeMood*/

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getMood() {
        return mood;
    }

    public int getDirection(){ return direction; }
    public void setDirection(int direction){ this.direction = direction; }

}
