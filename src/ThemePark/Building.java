package ThemePark;

public  class Building {
    private boolean inConstruction;
    private int constructionTime;
    private int buildPrice;
    private int Location_X;
    private int Location_Y;
    private int Range;



    public Building(boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int range) {
        this.inConstruction = inConstruction;
        this.constructionTime = constructionTime;
        this.buildPrice = buildPrice;
        Location_X = location_X;
        Location_Y = location_Y;
        Range = range;

    }

    public void changeMood(int mood){
        //mood = mood +- n;
    }

    /*public boolean isEmptyArea(){
        //TODO return;
    }*/

    /*public boolean isWaiting(Game g){
      return g.getWaitingTimer();
    }*/

    public void start(){

    }


    //Getter és setter


    public boolean isInConstruction() {
        return inConstruction;
    }

    public int getConstructionTime() {
        return constructionTime;
    }


    public int getBuildPrice() {
        return buildPrice;
    }

    public int getLocation_X() {
        return Location_X;
    }

    public int getLocation_Y() {
        return Location_Y;
    }

    public int getRange() {
        return Range;
    }



    public void setInConstruction(boolean inConstruction) {
        this.inConstruction = inConstruction;
    }

    public void setConstructionTime(int constructionTime) {
        this.constructionTime = constructionTime;
    }



    public void setBuildPrice(int buildPrice) {
        this.buildPrice = buildPrice;
    }

    public void setLocation_X(int location_X) {
        Location_X = location_X;
    }

    public void setLocation_Y(int location_Y) {
        Location_Y = location_Y;
    }

    public void setRange(int range) {
        Range = range;
    }




}
