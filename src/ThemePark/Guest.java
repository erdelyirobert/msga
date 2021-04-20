package ThemePark;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class Guest extends Person{
    Random random = new Random();
    int r = random.nextInt(4);
    private int mood = 6;
    public int direction = r;           //actual direction
    private boolean leaveTrash = false;
    public Timer trashTimer;



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

    public boolean getLeaveTrash() {
        return leaveTrash;
    }

    public void setLeaveTrash(boolean leaveTrash) {
        this.leaveTrash = leaveTrash;
    }

    public void startTrashTimer(){
        trashTimer = new Timer(5000, (ActionEvent e) -> {
            leaveTrash = true;
        });
        trashTimer.start();
    }
}
