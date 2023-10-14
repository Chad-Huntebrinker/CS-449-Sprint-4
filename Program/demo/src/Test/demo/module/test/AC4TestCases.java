package demo.module.test;
import com.example.demo.Board;
import com.example.demo.Box;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AC4TestCases {
    private Board board = new Board();


    //AC 4.1
    @Test
    public void testSuccessfulMoveInSimpleGame() {
        //Set the board to an 8x8, initiate the board, and make it a Simple Game.
        board.setBoardSize(8);
        board.initBoard();
        board.setIsSimpleGame(true);

        //Place an S in row 1, column 1's box.
        board.getBoxOfBoard(1,1).drawS();

        //Make sure that there is an S at (1,1) and that it is a Simple Game.
        assertEquals("S", board.getBoxOfBoard(1,1).getValue());
        assertEquals(true, board.getIsSimpleGame());

    }

    //4.2
    @Test
    public void testUnsuccessfulMoveInSimpleGame() {
        //Set the board to an 8x8, initiate the board, set it to a Simple Game.
        board.setBoardSize(8);
        board.initBoard();
        board.setIsSimpleGame(true);


        //Draw an S at (1,1), then try to draw an O at the same place.
        board.getBoxOfBoard(1,1).drawS();
        board.getBoxOfBoard(1,1).drawO();

        //Make sure that there is still an S at (1,1) and that it is still a Simple Game.
        assertEquals("S", board.getBoxOfBoard(1,1).getValue());
        assertEquals(true, board.getIsSimpleGame());

    }
}