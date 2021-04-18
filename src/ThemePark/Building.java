package ThemePark;

/**
 * The type Building.
 */
public class Building {
    private String buildingsImages;
    private int constructionTime;
    private int buildPrice;
    private int location_X;
    private int location_Y;
    private int buildingsSizesA; //szélesség: width
    private int buildingsSizesB; //magasság: height
    private int closestRoad_X;
    private int closestRoad_Y;


    public Building(String buildingsImages, int constructionTime, int buildPrice, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        this.buildingsImages = buildingsImages;
        this.constructionTime = constructionTime;
        this.buildPrice = buildPrice;
        this.location_X = location_X;
        this.location_Y = location_Y;
        this.buildingsSizesA = buildingsSizesA;
        this.buildingsSizesB = buildingsSizesB;
    }

    public String getBuildingsImages() {
        return buildingsImages;
    }

    public void setBuildingsImages(String buildingsImages) {
        this.buildingsImages = buildingsImages;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public void setConstructionTime(int constructionTime) {
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

    public int getClosestRoad_X() {
        return closestRoad_X;
    }

    public void setClosestRoad_X(int closestRoad_X) {
        this.closestRoad_X = closestRoad_X;
    }

    public int getClosestRoad_Y() {
        return closestRoad_Y;
    }

    public void setClosestRoad_Y(int closestRoad_Y) {
        this.closestRoad_Y = closestRoad_Y;
    }
}
