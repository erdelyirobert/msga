package ThemePark;

/**
 * The type Game.
 */
public class Game extends Building {

    private int waitingTimer;
    private int runningTimer;
    private int usageTime;


    public Game(Enum type, boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int range) {
        super(type, inConstruction, constructionTime, buildPrice, location_X, location_Y, range);
        this.waitingTimer = waitingTimer;
        this.runningTimer = runningTimer;
        this.usageTime = usageTime;
    }

    public void inUse() {
        //TODO
    }

    public void makeOutOfOrder() {
        //TODO
    }

    public void destroy() {
        //TODO
    }


    public void usage() {
        //TODO
    }

}
