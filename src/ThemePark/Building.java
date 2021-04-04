package ThemePark;

/**
 * The type Building.
 */
public class Building {
    /**
     * The Type.
     */
    public Enum type;
    private boolean inConstruction;
    private int constructionTime;
    private int buildPrice;
    private int Location_X;
    private int Location_Y;
    private int A;
    private int B;


    /**
     * Instantiates a new Building.
     *
     * @param type             the type
     * @param inConstruction   the in construction
     * @param constructionTime the construction time
     * @param buildPrice       the build price
     * @param location_X       the location x
     * @param location_Y       the location y
     * @param a                the range a
     * @param b                the range b
     */
    public Building(Enum type, boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int a, int b) {
        this.inConstruction = inConstruction;
        this.constructionTime = constructionTime;
        this.buildPrice = buildPrice;
        Location_X = location_X;
        Location_Y = location_Y;
        A = a;
        B = b;
        this.type = type;

    }

    /**
     * Change mood.
     *
     * @param mood the mood
     */
    public void changeMood(int mood) {
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
    public void start() {

    }


    //------------------Getter és setter------------------------///


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
     * Gets a.
     *
     * @return the a
     */
    public int getA() {
        return A;
    }

    /**
     * Gets b.
     *
     * @return the b
     */
    public int getB() {
        return B;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Enum getType() {
        return type;
    }
}
