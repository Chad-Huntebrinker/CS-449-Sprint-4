package demo.module.test;
import com.example.demo.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class AC1TestCases {
    private Board board = new Board();

    //AC 1.1
    @Test
    public void testStartBoardWithValidSize() {
        //Set Board size to 8x8 and initiate the board.
        board.setBoardSize(8);
        board.initBoard();

        //Because the size is greater than 2, it should be 8.
        assertEquals(8, board.getBoardSize());

        //Should start as blue turns.
        assertEquals(true, board.getIsBlueTurn());

    }

    //AC 1.2
    @Test
    public void testStartBoardWithInvalidSize() {
        //Try to set the Board size to 2x2 and initiate the board.
        board.setBoardSize(2);
        board.initBoard();

        //Because the size is 2, the size shouldn't be 2.
        assertNotEquals(2, board.getBoardSize());

        //Should still start as blue turns.
        assertEquals(true, board.getIsBlueTurn());
    }
}