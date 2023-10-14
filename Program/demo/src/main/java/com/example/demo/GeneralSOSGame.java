package com.example.demo;

import javafx.scene.control.Label;

public class GeneralSOSGame extends SOSGame {

    //Constructor
    GeneralSOSGame() {
    }

    //This is the specific rules for the General Game.
    public void gameRules(Board board, Board SOSBoard, PlayerBox blue, PlayerBox red,
                           GUI guiInput, Box boxInput, Label turnInput) {
        //Boolean variable to keep track of if a letter has been drawn and if an SOS has been formed.
        boolean letterDrawn;
        boolean SOSIsFormed;

        //Set all our variables by the parameters passed in the function.
        this.setBoard(board);
        this.setSOSBoard(SOSBoard);
        this.setBluePlayer(blue);
        this.setRedPlayer(red);
        this.setGui(guiInput);
        this.setBox(boxInput);
        this.setDisplayTurn(turnInput);

        //Run the drawALetter function and store the boolean value.
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

        //Then the game is only over if the board is full. So, check if the board is full and
        //set the gameOver variable in the board class to that boolean value
        this.getBoard().setGameOver(this.getBoard().isBoardFull());

        //If the game is over.
        if (this.getBoard().getGameOver()) {

            //See if the Red Player had the most SOS's formed,
            //if the Blue Player had the most SOS's formed, or if it
            //is a tie.
            if (this.getBluePlayer().getScore() < this.getRedPlayer().getScore()) {
                this.getDisplayTurn().setText("Game Over: Red Player Wins!");
            } else if (this.getRedPlayer().getScore() < this.getBluePlayer().getScore()) {
                this.getDisplayTurn().setText("Game Over: Blue Player Wins!");
            } else {
                this.getDisplayTurn().setText("Game Over: It's a tie!");
            }
        }

        //If it is Blue Player's turn, a SOS has been formed, and a letter has been drawn,
        //then it is still Blue Player's turn.
        else if (this.getBoard().getIsBlueTurn() && SOSIsFormed && letterDrawn &&
                (!this.getBoard().getIsBluePlayerComputer())) {
            this.getBoard().setIsBlueTurn(true);
            this.getDisplayTurn().setText("Blue Player's Turn");
        }

        //If it is Red Player's turn, a SOS has been formed, and a letter has been drawn,
        //then it is still Red Player's turn.
        else if((!this.getBoard().getIsBlueTurn()) && SOSIsFormed && letterDrawn &&
                (!this.getBoard().getIsRedPlayerComputer())) {
            this.getBoard().setIsBlueTurn(false);
            this.getDisplayTurn().setText("Red Player's Turn");
        }

        //If it is blue player's turn, an SOS has been formed, a letter has been drawn, and blue player
        //is a computer, then set it up for blue player and have it automatically go through the
        //game rules (since we don't need to wait on a human's input).
        else if (this.getBoard().getIsBlueTurn() && SOSIsFormed && letterDrawn &&
                this.getBoard().getIsBluePlayerComputer()) {
            this.getBoard().setIsBlueTurn(true);
            this.getDisplayTurn().setText("Blue Player's Turn");
            gameRules(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(), this.getRedPlayer(),
                    this.getGui(), this.getBox(), this.getDisplayTurn());
        }

        //If it is red player's turn, an SOS has been formed, a letter has been drawn, and red player
        //is a computer, then set it up for red player and have it automatically go through the
        //game rules (since we don't need to wait on a human's input).
        else if((!this.getBoard().getIsBlueTurn()) && SOSIsFormed && letterDrawn &&
                this.getBoard().getIsRedPlayerComputer()) {
            this.getBoard().setIsBlueTurn(false);
            this.getDisplayTurn().setText("Red Player's Turn");
            gameRules(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(), this.getRedPlayer(),
                    this.getGui(), this.getBox(), this.getDisplayTurn());
        }

        //If it is Blue Player's turn, a SOS has not been formed, and a letter has been drawn,
        //then it is now Red Player's turn.
        else if((this.getBoard().getIsBlueTurn()) && (!SOSIsFormed) && letterDrawn &&
                (!this.getBoard().getIsRedPlayerComputer())) {
            this.getBoard().setIsBlueTurn(false);
            this.getDisplayTurn().setText("Red Player's Turn");
        }

        //If it is Red Player's turn, a SOS has not been formed, and a letter has been drawn,
        //then it is now Blue Player's turn.
        else if((!this.getBoard().getIsBlueTurn()) && (!SOSIsFormed) && letterDrawn &&
                (!this.getBoard().getIsBluePlayerComputer())) {
            this.getBoard().setIsBlueTurn(true);
            this.getDisplayTurn().setText("Blue Player's Turn");

        }

        //If it is blue player's turn, an SOS has not been formed, a letter has been drawn, and
        //red player is a computer, then set it up for red player and have it automatically go through the
        //game rules (since we don't need to wait on a human's input).
        else if(this.getBoard().getIsBlueTurn() && (!SOSIsFormed) && letterDrawn &&
                this.getBoard().getIsRedPlayerComputer()) {
            this.getBoard().setIsBlueTurn(false);
            this.getDisplayTurn().setText("Red Player's Turn");
            gameRules(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(), this.getRedPlayer(),
                    this.getGui(), this.getBox(), this.getDisplayTurn());
        }

        //If it is red player's turn, an SOS has not been formed, a letter has been drawn, and
        //blue player is a computer, then set it up for blue player and have it automatically go through the
        //game rules (since we don't need to wait on a human's input).
        else if((!this.getBoard().getIsBlueTurn()) && (!SOSIsFormed) && letterDrawn &&
                this.getBoard().getIsBluePlayerComputer()) {
            this.getBoard().setIsBlueTurn(true);
            this.getDisplayTurn().setText("Blue Player's Turn");
            gameRules(this.getBoard(), this.getSOSBoard(), this.getBluePlayer(), this.getRedPlayer(),
                    this.getGui(), this.getBox(), this.getDisplayTurn());
        }

        //If it is Blue Player's turn and a letter has not been drawn,
        //then it is still Blue Player's turn.
        else if((this.getBoard().getIsBlueTurn() && (!letterDrawn))) {
            this.getBoard().setIsBlueTurn(true);
            this.getDisplayTurn().setText("Blue Player's Turn");
        }

        //Else it is Red Player's turn and a letter has not been drawn.
        //So it is still Red Player's turn.
        else {
            this.getBoard().setIsBlueTurn(false);
            this.getDisplayTurn().setText("Red Player's Turn");
        }
    }
}