package demo.module.test;
import com.example.demo.Board;
import com.example.demo.Box;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AC6TestCases {
    private Board board = new Board();
    private Box box;

    //AC 6.1
    @Test
    public void testSuccessfulMoveInGeneralGame() {
        //Set the board to 8x8, initiate the board, and set the game to a General Game.
        board.setBoardSize(8);
        board.initBoard();
        board.setIsSimpleGame(false);

        //Draw an S at (1,1).
        board.getBoxOfBoard(1,1).drawS();

        //Check and make sure there is an S at (1,1) and that the game mode is a General Game.
        assertEquals("S", board.getBoxOfBoard(1,1).getValue());
        assertEquals(false, board.getIsSimpleGame());

    }

    //6.2
    @Test
    public void testUnsuccessfulMoveInGeneralGame() {
        //Set the board to 8x8, initiate the board, and set the game to a General Game.
        board.initBoard();
        board.setIsSimpleGame(false);
        board.setBoardSize(8);
        board.setIsBlueTurn(true);

        //Draw an S at (1,1), then try to draw an O at the same place.
        board.getBoxOfBoard(1,1).drawS();
        board.getBoxOfBoard(1,1).drawO();

        //Make sure that there is still an S at (1,1) and that it is still a General Game.
        assertEquals("S", board.getBoxOfBoard(1,1).getValue());
        assertEquals(false, board.getIsSimpleGame());

    }
}