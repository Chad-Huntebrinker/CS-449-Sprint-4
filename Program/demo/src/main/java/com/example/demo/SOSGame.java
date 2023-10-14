package com.example.demo;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

//Parent Class for the Simple and General Game classes.
public class SOSGame {
    private Board board;
    private Board SOSBoard;
    private PlayerBox bluePlayer;
    private PlayerBox redPlayer;
    private GUI gui;
    private Box box;
    private Label displayTurn;
    private Board recordGameBoard;
    private ArrayList gameMoves;

    //Constructor
    SOSGame() {
        board = new Board();
        SOSBoard = new Board();
        bluePlayer = new PlayerBox();
        redPlayer = new PlayerBox();
        gui = new GUI(new Board());
        box = new Box();
        displayTurn = new Label();
        recordGameBoard = new Board();
        gameMoves = new ArrayList<>();
    }

    //This function is used to draw a letter on a specific box. Both of the Game Modes will need to draw
    //something on the board, so this a good function to put in the parent class.
    public boolean drawALetter(Board board, Board SOSBoard, PlayerBox blue, PlayerBox red,
                               GUI guiInput, Box boxInput, Label turnInput) {
        this.board = board;
        this.SOSBoard = SOSBoard;
        bluePlayer = blue;
        redPlayer = red;
        gui = guiInput;
        box = boxInput;
        displayTurn = turnInput;


            //If the box is not empty, return false (because we didn't draw anything).
            if (!box.getValue().isEmpty() && this.board.getIsBlueTurn() &&
                    (!this.board.getIsBluePlayerComputer())) {
                return false;
            }
            if(!box.getValue().isEmpty() && (!this.board.getIsBlueTurn()) &&
                    (!this.board.getIsRedPlayerComputer())) {
                return false;
            }

            //If it is Blue Player's turn.
            if(this.board.getIsBlueTurn()) {

                //If blue player is not a computer, then it is a human.
                //So, set up the blue human player and check to see whether we need to draw a S or an O.
                if(!this.board.getIsBluePlayerComputer()) {
                    HumanPlayer blueHuman = new HumanPlayer();

                    blueHuman.checkToDrawSOrO(bluePlayer, box);
                }

                //Else, if blue player is a computer, then set up the blue computer.  Also, set up a loop
                //where it will not exit until we have found an open spot on the board.
                else {
                    boolean temp = false;
                    ComputerPlayer blueComputer = new ComputerPlayer();

                    do {
                        Random rand = new Random();
                        int randomRow = rand.nextInt(this.board.getBoardSize());
                        int randomColumn = rand.nextInt(this.board.getBoardSize());
                        if(this.board.getBoxOfBoard(randomRow, randomColumn).getValue().isEmpty()) {
                            temp = true;
                            blueComputer.checkToDrawSOrO(bluePlayer, this.board.getBoxOfBoard(
                                                                            randomRow, randomColumn));
                        }
                    }while(!temp);
                }
            }

            //Else it is Red Player's turn.
            else {

                //If red player is not a computer, then it is a human.
                //So, set up the red human player and check to see whether we need to draw a S or an O.
                if(!this.board.getIsRedPlayerComputer()) {
                    HumanPlayer redHuman = new HumanPlayer();

                    redHuman.checkToDrawSOrO(redPlayer, box);
                }

                //Else, if red player is a computer, then set up the red computer.  Also, set up a loop
                //where it will not exit until we have found an open spot on the board.
                else {
                    boolean temp = false;
                    ComputerPlayer redComputer = new ComputerPlayer();

                    do {
                        Random rand = new Random();
                        int randomRow = rand.nextInt(this.board.getBoardSize());
                        int randomColumn = rand.nextInt(this.board.getBoardSize());
                        if(this.board.getBoxOfBoard(randomRow, randomColumn).getValue().isEmpty()) {
                            temp = true;
                            redComputer.checkToDrawSOrO(redPlayer, this.board.getBoxOfBoard(
                                                                                randomRow, randomColumn));
                        }
                    }while(!temp);
                }
            }
            //If we are here, then we drew something on the board.  So, return true.
            return true;
    }

    //Our getters and setters.
    public Board getBoard() {return board;}
    public Board getSOSBoard() {return SOSBoard;}
    public PlayerBox getBluePlayer() {return bluePlayer;}
    public PlayerBox getRedPlayer() {return redPlayer;}
    public GUI getGui() {return gui;}
    public Box getBox() {return box;}
    public Label getDisplayTurn() {return displayTurn;}

    public void setBoard(Board input) {board = input;}
    public void setSOSBoard(Board input) {SOSBoard = input;}
    public void setBluePlayer(PlayerBox input) {bluePlayer = input;}
    public void setRedPlayer(PlayerBox input) {redPlayer = input;}
    public void setGui(GUI input) {gui = input;}
    public void setBox(Box input) {box = input;}
    public void setDisplayTurn(Label input) {displayTurn = input;}
}