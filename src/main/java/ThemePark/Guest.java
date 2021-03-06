package ThemePark;

import board.TPBoard;

import java.util.ArrayList;
import java.util.Random;

public class Guest extends Person{
    Random random = new Random();
    int r = random.nextInt(4);
    private int mood = 10;
    public int direction = r;           //actual direction
    private boolean activateTimer = false;
    private int trashTimer = 90;
    private String targetGame = "";
    private boolean inGame = false;
    private boolean wasOnGame = false;
    private int playingTimer = 150;
    private int lastPlayingTimer = 40;

    /**
     * Constructor
     * @param personImages
     * @param location_X
     * @param location_Y
     * @param buildingsSizesA
     * @param buildingsSizesB
     */
    public Guest(String personImages, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        super(personImages, location_X, location_Y, buildingsSizesA, buildingsSizesB);
    }

    /**
     * Timers
     */
    public boolean canIGetTargetGame(){
        if(wasOnGame){
            lastPlayingTimer--;
            if(lastPlayingTimer==0){
                lastPlayingTimer = 40;
                wasOnGame = false;
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isInGame(){
        if(inGame){
            playingTimer--;
            if(playingTimer==0){
                playingTimer = 150;
                inGame = false;
                targetGame="";
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean canLeaveTrash(boolean onBin){  // ha aktiv a timer, akkor csökkenti a trashTimert, ha pedig 0, akkor leteheti a szemetet
        if(activateTimer){
            trashTimer--;
            if(trashTimer==0 || onBin && trashTimer < 20){
                trashTimer = 90;
                activateTimer = false;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Getters and setters
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isWasOnGame() {
        return wasOnGame;
    }

    public void setWasOnGame(boolean wasOnGame) {
        this.wasOnGame = wasOnGame;
    }

    public int getLastPlayingTimer() {
        return lastPlayingTimer;
    }

    public boolean getInGame() {
        return inGame;
    }

    public String getTargetGame() {
        return targetGame;
    }

    public void setTargetGame(String targetGame) {
        this.targetGame = targetGame;
    }

    public void setActivateTimer(boolean activateTimer) {
        this.activateTimer = activateTimer;
    }

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
