package board;

/**
 * The type Player.
 */
public class Player {
    private int budget = 10000;
    private boolean isGameOver = false;

    /**
     * Ctor a new Player.
     * new player by default: budget and isGameOver
     */
    public Player() {
    }

    //getters and setters
    /**
     * Gets budget.
     *
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Is game over boolean.
     *
     * @return the boolean
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Sets game over.
     *
     * @param gameOver the game over
     */
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}

