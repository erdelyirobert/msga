package ThemePark;

/**
 * The type Game.
 */
public class Game extends Building {
    public int usagePrice;

    public Game(String buildingsImages, double constructionTime, int buildPrice, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB, int usagePrice) {
        super(buildingsImages, constructionTime, buildPrice, location_X, location_Y, buildingsSizesA, buildingsSizesB);
        this.usagePrice = usagePrice;
    }

    public int getUsagePrice() {
        return usagePrice;
    }

    public void setUsagePrice(int usagePrice) {
        this.usagePrice = usagePrice;
    }

}
