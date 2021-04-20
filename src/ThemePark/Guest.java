package ThemePark;

import board.TPBoard;

import java.util.ArrayList;
import java.util.Random;

public class Guest extends Person{
    Random random = new Random();
    int r = random.nextInt(4);
    private int mood = 6;
    public int direction = r;           //actual direction
    private boolean activateTimer = false;
    private int trashTimer = 50;
    private String targetGame = "";
    private boolean inGame = false;
    private int playingTimer = 100;




    public boolean isInGame(){
        if(inGame){
            playingTimer--;
            if(playingTimer==0){
                playingTimer = 100;
                inGame = false;
                targetGame="";
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }



    public boolean canLeaveTrash(){              // ha aktiv a timer, akkor csökkenti a trashTimert, ha pedig 0, akkor leteheti a kukát
        if(activateTimer){
            trashTimer--;
            if(trashTimer==0){
                trashTimer = 50;
                activateTimer = false;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getInGame() {
        return inGame;
    }

    public int getPlayingTimer() {
        return playingTimer;
    }

    public void setPlayingTimer(int playingTimer) {
        this.playingTimer = playingTimer;
    }
    public String getTargetGame() {
        return targetGame;
    }

    public void setTargetGame(String targetGame) {
        this.targetGame = targetGame;
    }
    public boolean getActivateTimer() {
        return activateTimer;
    }
    public int getTrashTimer() {
        return trashTimer;
    }

    public void setTrashTimer(int trashTimer) {
        this.trashTimer = trashTimer;
    }
    public void setActivateTimer(boolean activateTimer) {
        this.activateTimer = activateTimer;
    }



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
