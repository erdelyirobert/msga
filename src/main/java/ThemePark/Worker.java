package ThemePark;

import java.util.ArrayList;

public class Worker extends Person{

    private int salary = 200;
    private int direction = 0;
    private int remainSteps;        //hátrlévő lépések száma
    private int stepNo = 0;
    private boolean inMaintenance = false;           //csak akkor mozog a maintenance ha ez true
    private int workingTimer = 25;

    public ArrayList<Integer> stepForwardOnPathDirection = new ArrayList<>();
    public ArrayList<Integer> stepForwardOnPathStep = new ArrayList<>();
    public ArrayList<Integer> shortestPath = new ArrayList<>();

    public boolean isWorking(){
        if(inMaintenance){
            workingTimer--;
            if(workingTimer==0){
                workingTimer = 25;
                stepNo = 0;
                inMaintenance = false;
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> reverseArrayList()
    {
        // Arraylist for storing reversed elements
        ArrayList<Integer> revArrayList = new ArrayList<Integer>();
        for (int i = shortestPath.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(shortestPath.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    public ArrayList<Integer> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(ArrayList<Integer> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public boolean isInMaintenance() {
        return inMaintenance;
    }

    public void setInMaintenance(boolean inMaintenance) {
        this.inMaintenance = inMaintenance;
    }

    public int getWorkingTimer() {
        return workingTimer;
    }

    public void setWorkingTimer(int workingTimer) {
        this.workingTimer = workingTimer;
    }

    public ArrayList<Integer> getStepForwardOnPathStep() {
        return stepForwardOnPathStep;
    }

    public int getStepNo() {
        return stepNo;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }

    public void setStepForwardOnPathStep(ArrayList<Integer> stepForwardOnPathStep) {
        this.stepForwardOnPathStep = stepForwardOnPathStep;
    }

    public ArrayList<Integer> getStepForwardOnPathDirection() {
        return stepForwardOnPathDirection;
    }

    public void setStepForwardOnPathDirection(ArrayList<Integer> stepForwardOnPathDirection) {
        this.stepForwardOnPathDirection = stepForwardOnPathDirection;
    }

    public Worker(String personImages, int location_X, int location_Y, int buildingsSizesA, int buildingsSizesB) {
        super(personImages, location_X, location_Y, buildingsSizesA, buildingsSizesB);
    }

    public int getSalary() {
        return salary;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getRemainSteps() {
        return remainSteps;
    }

    public void setRemainSteps(int remainSteps) {
        this.remainSteps = remainSteps;
    }
}
