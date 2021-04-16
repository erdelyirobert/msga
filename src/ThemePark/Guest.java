package ThemePark;

import java.util.ArrayList;

public class Guest extends Person{
    private int mood = 20;
    public int direction = 0;           //actual direction
    public int previousDirection = 0;   //previous direction


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

    public int getPreviousDirection() {
        return previousDirection;
    }

    public void setPreviousDirection(int previousDirection) {
        this.previousDirection = previousDirection;
    }


}
