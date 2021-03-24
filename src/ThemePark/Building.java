package ThemePark;

/**
 * The type Building.
 */
public  class Building {
    private boolean inConstruction;
    private int constructionTime;
    private int buildPrice;
    private int Location_X;
    private int Location_Y;
    private int Range;
    /**
     * The Type.
     */
    public Enum type;


    /**
     * Instantiates a new Building.
     *
     * @param type             the type
     * @param inConstruction   the in construction
     * @param constructionTime the construction time
     * @param buildPrice       the build price
     * @param location_X       the location x
     * @param location_Y       the location y
     * @param range            the range
     */
    public Building(Enum type,boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int range) {
        this.inConstruction = inConstruction;
        this.constructionTime = constructionTime;
        this.buildPrice = buildPrice;
        Location_X = location_X;
        Location_Y = location_Y;
        Range = range;
        this.type = type;

    }

    /**
     * Change mood.
     *
     * @param mood the mood
     */
    public void changeMood(int mood){
        //mood = mood +- n;
    }
    /*
    public boolean isEmptyArea(){
        //TODO return;

    }
    */
    /*public boolean isWaiting(Game g){
      return g.getWaitingTimer();
    }*/

    /**
     * Start.
     */
    public void start(){

    }


    //Getter és setter


    /**
     * Is in construction boolean.
     *
     * @return the boolean
     */
    public boolean isInConstruction() {
        return inConstruction;
    }

    /**
     * Gets construction time.
     *
     * @return the construction time
     */
    public int getConstructionTime() {
        return constructionTime;
    }


    /**
     * Gets build price.
     *
     * @return the build price
     */
    public int getBuildPrice() {
        return buildPrice;
    }

    /**
     * Gets location x.
     *
     * @return the location x
     */
    public int getLocation_X() {
        return Location_X;
    }

    /**
     * Gets location y.
     *
     * @return the location y
     */
    public int getLocation_Y() {
        return Location_Y;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return Range;
    }


    /**
     * Sets in construction.
     *
     * @param inConstruction the in construction
     */
    public void setInConstruction(boolean inConstruction) {
        this.inConstruction = inConstruction;
    }

    /**
     * Sets construction time.
     *
     * @param constructionTime the construction time
     */
    public void setConstructionTime(int constructionTime) {
        this.constructionTime = constructionTime;
    }


    /**
     * Sets build price.
     *
     * @param buildPrice the build price
     */
    public void setBuildPrice(int buildPrice) {
        this.buildPrice = buildPrice;
    }

    /**
     * Sets location x.
     *
     * @param location_X the location x
     */
    public void setLocation_X(int location_X) {
        Location_X = location_X;
    }

    /**
     * Sets location y.
     *
     * @param location_Y the location y
     */
    public void setLocation_Y(int location_Y) {
        Location_Y = location_Y;
    }

    /**
     * Sets range.
     *
     * @param range the range
     */
    public void setRange(int range) {
        Range = range;
    }




}
