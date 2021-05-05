package ThemePark;

import java.util.ArrayList;

/**
 * The type Building.
 */
public class Building {
    private String buildingsImages;
    private double constructionTime;
    private int buildPrice;
    private int location_X;
    private int location_Y;
    private int buildingsSizesA; //szélesség: width
    private int buildingsSizesB; //magasság: height
    private int usagePrice;
    private int closestPoint_X = -20;
    private int closestPoint_Y = -20;
    private boolean isWrong = false;
    private int maxCapacity = 3;
    private ArrayList<Guest> players = new ArrayList<>();
    private ArrayList<Guest> queue = new ArrayList<>();

    public Building(String buildingsImages, double constructionTime, int buildPrice, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        this.buildingsImages = buildingsImages;
        this.constructionTime = constructionTime;
        this.buildPrice = buildPrice;
        this.location_X = location_X;
        this.location_Y = location_Y;
        this.buildingsSizesA = buildingsSizesA;
        this.buildingsSizesB = buildingsSizesB;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Guest> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Guest> players) {
        this.players = players;
    }

    public ArrayList<Guest> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<Guest> queue) {
        this.queue = queue;
    }


    public boolean getIsWrong() { return isWrong; }

    public void setWrong(boolean wrong) { isWrong = wrong; }

    public String getBuildingsImages() {
        return buildingsImages;
    }

    public void setBuildingsImages(String buildingsImages) {
        this.buildingsImages = buildingsImages;
    }

    public double getConstructionTime() {
        return constructionTime;
    }

    public void setConstructionTime(double constructionTime) {
        this.constructionTime = constructionTime;
    }

    public int getBuildPrice() {
        return buildPrice;
    }

    public void setBuildPrice(int buildPrice) {
        this.buildPrice = buildPrice;
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

    public int getBuildingsSizesA() {
        return buildingsSizesA;
    }

    public void setBuildingsSizesA(int buildingsSizesA) {
        this.buildingsSizesA = buildingsSizesA;
    }

    public int getBuildingsSizesB() {
        return buildingsSizesB;
    }

    public void setBuildingsSizesB(int buildingsSizesB) {
        this.buildingsSizesB = buildingsSizesB;
    }



    /**
     * Terület kiszámitási logika
     * Kiszámítja az adott területet
     * x-hez hozzáadja az épület A méretét (szélességét)
     * y-hoz hozzáadja az épület B méretét (magasságát)
     */
    public int sumXA() {
        return (location_X - (location_X % 20)) + buildingsSizesA;
    }

    public int sumYB() {
        return (location_Y - (location_Y % 20)) + buildingsSizesB;
    }

    /**These functions calculete:
    *Closest road from a building
     * Setters set it
     * and you can get it from getters
    * */

    public int getClosestPoint_X() {
        return closestPoint_X;
    }

    public void setClosestPoint_X(int closestPoint_X) {
        this.closestPoint_X = closestPoint_X;
    }

    public int getClosestPoint_Y() {
        return closestPoint_Y;
    }

    public void setClosestPoint_Y(int closestPoint_Y) {
        this.closestPoint_Y = closestPoint_Y;
    }

    public int getUsagePrice() {
        return usagePrice;
    }

    public void setUsagePrice(int usagePrice) {
        this.usagePrice = usagePrice;
    }
}
