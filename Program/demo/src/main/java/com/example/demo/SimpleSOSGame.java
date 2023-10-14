package com.example.demo;

import javafx.scene.control.Label;

public class SimpleSOSGame extends SOSGame {

    //Constructor
    SimpleSOSGame() {

    }

    //This is the specific rules for the Simple Game.
    public void gameRules(Board board, Board SOSBoard, PlayerBox blue, PlayerBox red,
                          GUI guiInput, Box boxInput, Label turnInput) {
        //Boolean variable to keep track if a letter is drawn or if an SOS is formed.
        boolean letterDrawn;
        boolean SOSIsFormed;

        //Set all our variables based off of what is passed in this function.
        this.setBoard(board);
        this.setSOSBoard(SOSBoard);
        this.setBluePlayer(blue);
        this.setRedPlayer(red);
        this.setGui(guiInput);
        this.setBox(boxInput);
        this.setDisplayTurn(turnInput);

        //Run the drawALetter function and store it's boolean value.
        letterDrawn = this.drawALetter(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(),
                this.getRedPlayer(), this.getGui(), this.getBox(), this.getDisplayTurn());

        //If it is Blue Player's turn, then pass the Blue Player in the SOSIsFormed function.
        if(this.getBoard().getIsBlueTurn()) {
            SOSIsFormed = this.getBoard().checkForSOS(this.getBluePlayer(), this.getSOSBoard(), this.getGui());
        }

        //Otherwise, pass the Red Player in the SOSIsFormed function.
        else {
            SOSIsFormed = this.getBoard().checkForSOS(this.getRedPlayer(), this.getSOSBoard(), this.getGui());
        }


        //If an SOS is formed, then the game is over, so set
        //Game Over to true.
        if(SOSIsFormed){
            this.getBoard().setGameOver(SOSIsFormed);
        }

        //If an SOS is not formed, set the gameOver variable to whether the board
        //is full or not.  If it isn't full, then gameOver is set to false and if it
        //is full set the gameOver to true.
        else {
            this.getBoard().setGameOver(this.getBoard().isBoardFull());
        }

        //If the game is over, See if the Red Player had the most SOS's formed,
        //if the Blue Player had the most SOS's formed, or if it is a tie.
        if (this.getBoard().getGameOver()) {
            if (this.getBluePlayer().getScore() < this.getRedPlayer().getScore()) {
                this.getDisplayTurn().setText("Game Over: Red Player Wins!");
            }
            else if (this.getRedPlayer().getScore() < this.getBluePlayer().getScore()) {
                this.getDisplayTurn().setText("Game Over: Blue Player Wins!");
            }
            else {
                this.getDisplayTurn().setText("Game Over: It's a tie!");
            }
        }

        //Else (if the game is not over), then we'll need to check a few things.
        else {

            //If it is blue player's turn, a letter has been drawn on the board, and red player is not a
            //computer, then set it up for the human red player.
            if(this.getBoard().getIsBlueTurn() && letterDrawn &&
                    (!this.getBoard().getIsRedPlayerComputer())) {

                this.getBoard().setIsBlueTurn(false);
                this.getDisplayTurn().setText("Red Player's Turn");
            }

            //Else if it is red player's turn, a letter has been drawn on the board, and blue player is not
            //a computer, then set it up for the human blue player.
            else if((!this.getBoard().getIsBlueTurn()) && letterDrawn &&
                    (!this.getBoard().getIsBluePlayerComputer())) {

                this.getBoard().setIsBlueTurn(true);
                this.getDisplayTurn().setText("Blue Player's Turn");
            }

            //Else if it is blue player's turn, a letter has been drawn on the board, and red player is
            //a computer, then set it up for red player and have it automatically go through the
            //game rules (since we don't need to wait on a human's input).
            else if(this.getBoard().getIsBlueTurn() && letterDrawn &&
                    this.getBoard().getIsRedPlayerComputer()) {

                this.getBoard().setIsBlueTurn(false);
                this.getDisplayTurn().setText("Red Player's Turn");
                gameRules(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(), this.getRedPlayer(),
                            this.getGui(), this.getBox(), this.getDisplayTurn());
            }

            //Else if it is red player's turn, a letter has been drawn on the board, and blue player is
            //a computer, then set it up for blue player and have it automatically go through the
            //game rules (since we don't need to wait on a human's input).
            else if((!this.getBoard().getIsBlueTurn()) && letterDrawn &&
                    this.getBoard().getIsBluePlayerComputer()) {

                this.getBoard().setIsBlueTurn(true);
                this.getDisplayTurn().setText("Blue Player's Turn");
                gameRules(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(), this.getRedPlayer(),
                        this.getGui(), this.getBox(), this.getDisplayTurn());
            }

            //Else if it is blue player's turn, no letter has been drawn, and blue player is not a
            //computer, then set it up again for human blue player's turn.
            else if(this.getBoard().getIsBlueTurn() && !letterDrawn &&
                    (!this.getBoard().getIsBluePlayerComputer())) {
                this.getBoard().setIsBlueTurn(true);
                this.getDisplayTurn().setText("Blue not drawn");
            }

            //Else if it is red player's turn, no letter has been drawn, and red player is not a
            //computer, then set it up again for human blue player's turn.
            else if((!this.getBoard().getIsBlueTurn()) && !letterDrawn &&
                    (!this.getBoard().getIsRedPlayerComputer())) {
                this.getBoard().setIsBlueTurn(false);
                this.getDisplayTurn().setText("Red not drawn");
            }

            //Else, we have a problem in the game.  So output that we have a problem.
            else {
                this.getDisplayTurn().setText("Houston, we have a problem");
            }
        }
    }
}