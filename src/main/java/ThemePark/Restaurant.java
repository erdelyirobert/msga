package ThemePark;

/**
 * The type Restaurant.
 */
public class Restaurant extends Building {
    public int usagePrice;

    public Restaurant(String buildingsImages, double constructionTime, int buildPrice, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB, int usagePrice) {
        super(buildingsImages, constructionTime, buildPrice, location_X, location_Y, buildingsSizesA, buildingsSizesB);
        this.usagePrice = usagePrice;
    }

    public int getUsagePrice() {
        return usagePrice;
    }
}
