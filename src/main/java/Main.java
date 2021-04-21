import board.TPBoard;
import gui.MainMenu;
import gui.ThemeParkFrame;

import java.io.IOException;
import java.sql.SQLOutput;


/**
 * The  Main program
 */
public class Main {

    /**
     * The entry point of application.
     *
     */
    public static void main(String[] args) throws IOException {
        MainMenu mainMenu = new MainMenu();

        TPBoard board = new TPBoard();
        double  distance = board.coordinateDistance(1,4,0,4);
        System.out.println("FONTOS: " + distance);
    }
}
