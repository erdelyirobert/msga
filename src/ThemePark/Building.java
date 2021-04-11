package ThemePark;

import java.util.ArrayList;

/**
 * The type Building.
 */
public class Building {
    /**
     * The Type.
     */
    public Enum type;
    private String buildingsImages;
    private int constructionTime;
    private int buildPrice;
    private int location_X;
    private int location_Y;
    private int buildingsSizesA;
    private int buildingsSizesB;



    public Building(String buildingsImages,  int constructionTime, int buildPrice, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        this.buildingsImages = buildingsImages;
        this.constructionTime = constructionTime;
        this.buildPrice = buildPrice;
        this.location_X = location_X;
        this.location_Y = location_Y;
        this.buildingsSizesA = buildingsSizesA;
        this.buildingsSizesB = buildingsSizesB;
    }

    public Enum getType() {
        return type;
    }

    public String getBuildingsImages() {
        return buildingsImages;
    }



    public int getConstructionTime() {
        return constructionTime;
    }

    public int getBuildPrice() {
        return buildPrice;
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

    public void setType(Enum type) {
        this.type = type;
    }

    public void setBuildingsImages(String buildingsImages) {
        this.buildingsImages = buildingsImages;
    }


    public void setConstructionTime(int constructionTime) {
        this.constructionTime = constructionTime;
    }

    public void setBuildPrice(int buildPrice) {
        this.buildPrice = buildPrice;
    }

    public void setLocation_X(int location_X) {
        this.location_X = location_X;
    }

    public void setLocation_Y(int location_Y) {
        this.location_Y = location_Y;
    }

    public void setBuildingsSizesA(int buildingsSizesA) {
        this.buildingsSizesA = buildingsSizesA;
    }

    public void setBuildingsSizesB(int buildingsSizesB) {
        this.buildingsSizesB = buildingsSizesB;
    }


    /*  Terület kiszámitási logika
    * Kiszámítja az adott területet
    * x-hez hozzáadja az épület A méretét (szélességét)
    * y-hoz hozzáadja az épület B méretét (magasságát)
     */
    public int sumXA(){
      return  location_X+buildingsSizesA;
    }
    public int sumYB(){
        return  location_Y+buildingsSizesB;
    }
}
