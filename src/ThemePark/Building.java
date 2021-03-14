package ThemePark;

public class Building {
    Building(){

    }

    //Variables
    private boolean InConstruction;
    private int ConstructionTime;
    private int UsagePrice;
    private int BuildPrice;
    private int Location_X;
    private int Location_Y;
    private int Range;
    private int WaitingTimer; //timer
    private int RunningTimer; //timer
    private int MaxCapacity;
    private int WillToUse;

    //Getter
    public boolean getInConstruction() {
        return InConstruction;
    }

    public void setInConstruction(boolean inConstruction) {
        InConstruction = inConstruction;
    }

    public int getConstructionTime() {
        return ConstructionTime;
    }

    public void setConstructionTime(int constructionTime) {
        ConstructionTime = constructionTime;
    }

    public int getUsagePrice() {
        return UsagePrice;
    }

    public void setUsagePrice(int usagePrice) {
        UsagePrice = usagePrice;
    }

    public int getBuildPrice() {
        return BuildPrice;
    }

    public void setBuildPrice(int buildPrice) {
        BuildPrice = buildPrice;
    }

    public int getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(int location_X) {
        Location_X = location_X;
    }

    public int getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(int location_Y) {
        Location_Y = location_Y;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        Range = range;
    }

    public int getWaitingTimer() {
        return WaitingTimer;
    }

    public void setWaitingTimer(int waitingTimer) {
        WaitingTimer = waitingTimer;
    }

    public int getRunningTimer() {
        return RunningTimer;
    }

    public void setRunningTimer(int runningTimer) {
        RunningTimer = runningTimer;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        MaxCapacity = maxCapacity;
    }

    public int getWillToUse() {
        return WillToUse;
    }

    public void setWillToUse(int willToUse) {
        WillToUse = willToUse;
    }


    /**
     * Changes mood of guest
     */
    public void ChangeMood() {
//todo
    }

    /***
     * Check if the area is empty to build
     */

    public void isEmptyArea() {
//todo
    }

    /***
     *
     * @return
     */

    public void isWaiting() {
        //todo
    }


    public void start(){
        //todo
    }

}
