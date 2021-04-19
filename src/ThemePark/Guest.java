package ThemePark;

import java.util.ArrayList;
import java.util.Random;

public class Guest extends Person{
    Random random = new Random();
    int r = random.nextInt(4);
    private int mood = 6;
    public int direction = r;           //actual direction


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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
