package board;

public class Board {

    private int WIDTH = 1000; //width of the park
    private int HEIGHT = 800; //height of the park
    private int segmentSize = 10; //size of one grid

    public int[] x = new int[HEIGHT * WIDTH];
    public int[] y = new int[HEIGHT * WIDTH];

    public Board(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;
    }

    public int getSegmentSize(){return segmentSize;}
    public int getWIDTH(){ return WIDTH; }
    public int getHEIGHT(){ return HEIGHT; }
}
