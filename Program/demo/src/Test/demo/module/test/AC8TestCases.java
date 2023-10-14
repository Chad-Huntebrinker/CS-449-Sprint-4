package demo.module.test;
import com.example.demo.Board;
import com.example.demo.Box;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AC8TestCases {
    private Board board = new Board();
    private Box box;

    //AC 8.1
    //Test for both general and simple game if the blue computer player can make a move and if
    //the red computer can make a move.
    @Test
    public void computerMakesAMove() {
        board.setBoardSize(8);
        board.initBoard();
        board.setIsSimpleGame(false);
        board.setBluePlayerComputer(true);

        board.getBoxOfBoard(3,2).drawS();
        assertEquals("S", board.getBoxOfBoard(3,2).getValue());
        assertEquals(true, board.getIsBluePlayerComputer());

        board.initBoard();
        board.setIsSimpleGame(true);
        board.setRedPlayerComputer(true);
        board.getBoxOfBoard(4,1);
        board.getBoxOfBoard(2,3).drawO();
        assertEquals("O", board.getBoxOfBoard(2,3).getValue());
        assertEquals(true, board.getIsRedPlayerComputer());
    }
}