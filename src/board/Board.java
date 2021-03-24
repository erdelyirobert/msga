package board;

/**
 * The type Board.
 */
public class Board {

    private int sizeOfBoardX = 1000;
    private int sizeOfBoardY = 800;


    /**
     * Instantiates a new Board.
     *
     * @param sizeOfBoardX the size of board x
     * @param sizeOfBoardY the size of board y
     */
    public Board(int sizeOfBoardX, int sizeOfBoardY) {
        this.sizeOfBoardX = sizeOfBoardX;
        this.sizeOfBoardY = sizeOfBoardY;

    }

    /**
     * Get size of board x int.
     *
     * @return the int
     */
    public int getSizeOfBoardX(){ return sizeOfBoardX; }

    /**
     * Get size of board y int.
     *
     * @return the int
     */
    public int getSizeOfBoardY(){ return sizeOfBoardY; }
}
