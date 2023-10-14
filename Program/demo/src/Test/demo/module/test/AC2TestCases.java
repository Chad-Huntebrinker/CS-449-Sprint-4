package demo.module.test;
import com.example.demo.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AC2TestCases {
    private Board board = new Board();


    //AC 2.1
    @Test
    public void testSimpleGameModeSelected() {
        //Initiate the board, and set the game to a Simple Game.
        board.initBoard();
        board.setIsSimpleGame(true);

        //Make sure the game is a simple game.
        assertEquals(true, board.getIsSimpleGame());

    }

    //AC 2.1
    @Test
    public void testGeneralGameModeSelected() {
        //Initiate the game and the set the game to a General Game.
        board.initBoard();
        board.setIsSimpleGame(false);

        //Make sure the game is a General Game.
        assertEquals(false, board.getIsSimpleGame());
    }
}