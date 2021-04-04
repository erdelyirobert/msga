package board;


/**
 * The type Board.
 */
public class Board {

    private int WIDTH = 1000; //width of the park
    private int HEIGHT = 800; //height of the park
    private int segmentSize = 10; //size of one grid


    public int[] x = new int[HEIGHT * WIDTH];
    public int[] y = new int[HEIGHT * WIDTH];

    /**
     * Instantiates a new Board.
     *
     * @param w the w
     * @param h the h
     */
    public Board(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;
    }

    /**
     * Get segment size int.
     *
     * @return the int
     */
    public int getSegmentSize(){return segmentSize;}

    /**
     * Get width int.
     *
     * @return the int
     */
    public int getWIDTH(){ return WIDTH; }

    /**
     * Get height int.
     *
     * @return the int
     */
    public int getHEIGHT(){ return HEIGHT; }
}
