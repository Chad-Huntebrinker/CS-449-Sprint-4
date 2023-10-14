package demo.module.test;
import com.example.demo.Board;
import com.example.demo.Box;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AC3TestCases {
    private Board board = new Board();


    //AC 3.1
    @Test
    public void testStartANewGameWithValidBoardSizeAndGameMode() {
        //Set the board to an 8x8, initiate the board, and make it a Simple Game.
        board.setBoardSize(8);
        board.initBoard();
        board.setIsSimpleGame(true);

        //Make sure the initial settings are true and the game isn't over.
        assertEquals(true, board.getIsSimpleGame());
        assertEquals(8, board.getBoardSize());
        assertEquals(false, board.getGameOver());

        //Now, reset the board to a new
        board.setBoardSize(3);
        board.initBoard();
        board.setIsSimpleGame(false);

        //Check to see that the new settings are true and the game isn't over.
        assertEquals(false, board.getIsSimpleGame());
        assertEquals(3, board.getBoardSize());
        assertEquals(false, board.getGameOver());

    }

    //AC 3.2
    @Test
    public void testStartANewGameWithInvalidBoardSizeAndGameMode() {
        //Set the board to an 8x8, initiate the board, and make it a General Game.
        board.setBoardSize(8);
        board.initBoard();
        board.setIsSimpleGame(true);

        //Make sure the initial settings are true and the game isn't over.
        assertEquals(true, board.getIsSimpleGame());
        assertEquals(8, board.getBoardSize());
        assertEquals(false, board.getGameOver());

        //Now, reset the board to a new
        board.setBoardSize(1);
        board.initBoard();

        //Check to see that the board size is not the invalid size 1
        assertNotEquals(1, board.getBoardSize());

    }
}