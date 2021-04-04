package ThemePark;

/**
 * The type Game.
 */
public class Game extends Building {

    private int waitingTimer;
    private int runningTimer;
    private int usageTime;


    /**
     * Instantiates a new Game.
     *
     * @param type             the type
     * @param inConstruction   the in construction
     * @param constructionTime the construction time
     * @param buildPrice       the build price
     * @param location_X       the location x
     * @param location_Y       the location y
     * @param a                the range a
     * @param b                 the range b
     */
    public Game(Enum type, boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int a, int b) {
        super(type, inConstruction, constructionTime, buildPrice, location_X, location_Y, a,b);
        this.waitingTimer = waitingTimer;
        this.runningTimer = runningTimer;
        this.usageTime = usageTime;
    }

    /**
     * In use.
     */
    public void inUse() {
        //TODO
    }

    /**
     * Make out of order.
     */
    public void makeOutOfOrder() {
        //TODO
    }

    /**
     * Destroy.
     */
    public void destroy() {
        //TODO
    }


    /**
     * Usage.
     */
    public void usage() {
        //TODO
    }

}
