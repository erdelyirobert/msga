package board;

public class Board {

    private int sizeOfBoardX = 1000;
    private int sizeOfBoardY = 800;


    public Board(int sizeOfBoardX, int sizeOfBoardY) {
        this.sizeOfBoardX = sizeOfBoardX;
        this.sizeOfBoardY = sizeOfBoardY;

    }
    public int getSizeOfBoardX(){ return sizeOfBoardX; }
    public int getSizeOfBoardY(){ return sizeOfBoardY; }
}
