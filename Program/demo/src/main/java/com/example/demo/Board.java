package com.example.demo;

import java.util.ArrayList;

public class Board {

    //Variables for Board
    private ArrayList<ArrayList <Box>> board;
    private boolean isBluePlayerTurn;
    private boolean isSimpleGame;
    private int boardSize;
    private boolean gameOver;
    private boolean bluePlayerComputer;
    private boolean redPlayerComputer;

    //Constructor.
    public Board() {
        board = new ArrayList<ArrayList<Box>>();
        isBluePlayerTurn = true;
        isSimpleGame = true;
        boardSize = 3;
        initBoard();
        gameOver = false;
        bluePlayerComputer = false;
        redPlayerComputer = false;
    }

    //initBoard builds the ArrayList full of Boxes
    public void initBoard() {
        this.board = new ArrayList<ArrayList<Box>>();
        this.isBluePlayerTurn = true;
        this.gameOver = false;
        this.bluePlayerComputer = false;
        this.redPlayerComputer = false;

        for(int i = 0; i < boardSize; ++i) {
            this.board.add(new ArrayList<Box>());
            for(int j = 0; j < boardSize; ++j) {
                addBox(new Box(), i, j);
            }
        }

    }

    //This function adds the box to the ArrayList.
    public void addBox(Box box, int row, int column) {
        this.board.get(row).add(column, box);
    }

    //Get a specific Box from the ArrayList.
    public Box getBoxOfBoard(int row, int column) {
        if(row >= 0 && row < boardSize && column >= 0 && column < boardSize) {
            return this.board.get(row).get(column);
        }
        return null;

    }

    //Resets the board.
    public void resetBoard() {board.clear();}

    //Sets the board to the size given if it is greater than 2.
    public void setBoardSize(int size) {
        if(size > 2){
            boardSize = size;
        }
        initBoard();
    }

    //This function checks to see if an SOS is formed and returns a boolean value.
    public boolean checkForSOS(PlayerBox player, Board SOSBoard, GUI gui) {
        boolean temp1;
        boolean temp2;

        //Check for the horizontal and vertical SOS.  Those two functions will also call the functions
        //To check the diagonals.
        temp1 = checkHorizontal(player, SOSBoard, gui);
        temp2 = checkVertical(player, SOSBoard, gui);

        //Check if temp1 is true.  If it is not, then return temp 2.
        if(temp1) {
            return temp1;
        }
        else {
            return temp2;
        }
    }

    //This function checks for horizontal SOS's.
    public boolean checkHorizontal(PlayerBox player, Board SOSBoard, GUI gui) {
        int firstSRowPosition;
        int firstSColumnPosition;
        int firstORowPosition;
        int firstOColumnPosition;
        int secondSRowPosition;
        int secondSColumnPosition;
        boolean SOSFormed = false;
        boolean temp1 = false;


        //It will be checking in a left-to-right, top-to-bottom way.
        for (int i = 0; i < boardSize; ++i) {
            firstSRowPosition = -1;
            firstSColumnPosition = -1;
            firstORowPosition = -1;
            firstOColumnPosition = -1;
            secondSRowPosition = -1;
            secondSColumnPosition = -1;

            for (int j = 0; j < boardSize; ++j) {

                //The only point of checking is if there is enough columns to form an SOS horizontally.
                //So, check to see if there is still three spaces until we reach the end of the board.
                if (j + 2 < boardSize) {

                    //If the value at row i column j is an S, then keep those i and j values in our variables.
                    if (this.getBoxOfBoard(i, j).getValue().equals("S")) {
                        firstSRowPosition = i;
                        firstSColumnPosition = j;
                    }


                    //If the firstSRowPosition doesn't equal -1 (meaning if we have found our first S) and if
                    //the value at row i column j + 1 is an O, then keep those values.
                    if (firstSRowPosition != -1) {
                        if (this.getBoxOfBoard(i, j + 1).getValue().equals("O")) {
                            firstORowPosition = i;
                            firstOColumnPosition = j + 1;
                        }
                    }

                    //Otherwise, reset the first S positions back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                    }

                    //If the firstSRowPosition and the firstORowPosition doesn't equal -1 (meaning if we have
                    //found the first S and the O) and if the value
                    //at row i column j + 2 is S, then keep those values.
                    if (firstSRowPosition != -1 && firstORowPosition != -1) {
                        if (this.getBoxOfBoard(i, j + 2).getValue().equals("S")) {
                            secondSRowPosition = i;
                            secondSColumnPosition = j + 2;
                        }
                    }

                    //Otherwise, there is no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                    }

                    //If the secondSRowPosition doesn't equal -1 (meaning if we have found our last S) and if either
                    //our SOSBoard (the board that keeps track of all the SOS's) doesn't have an S or an O at
                    //the positions we have kept, that means a new SOS has been formed.
                    //So, update the scores, and then check the left-to-right diagonal.  Then,
                    //make that new SOS in our SOSBoard at those positions,
                    //assign our SOSFormed variable to true, change i and j back to 0 (to make sure we
                    //didn't miss anything), change the position variables back to -1, and if it is a Simple Game,
                    //then change the gameOver variable to true.  Also, make sure to draw the line in the GUI.
                    if (secondSRowPosition != -1 &&
                            (SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).getValue() != "S" ||
                            SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).getValue() != "O" ||
                            SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).getValue() != "S")) {

                        player.updatePlayerScore();

                        temp1 = this.checkLeftToRightDiagonal(player, SOSBoard, gui);

                        SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).drawS();
                        SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).drawO();
                        SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).drawS();

                        gui.drawLine(firstSRowPosition, firstSColumnPosition, firstORowPosition,
                                        firstOColumnPosition, secondSRowPosition, secondSColumnPosition);

                        i = 0;
                        j = 0;
                        SOSFormed = true;
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;

                        if(getIsSimpleGame()) {
                            gameOver = true;
                        }
                    }

                    //Otherwise, (if it is an old SOS that has been already counted), change the values back to -1.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;
                    }
                }
            }
        }

        //If we haven't already checked the left-to-right diagonal, then check it.
        if(!temp1) {
            temp1 = this.checkLeftToRightDiagonal(player, SOSBoard, gui);
        }

        //Return back whether an SOS has been formed.
        if(temp1) {
            return temp1;
        }
        else {
            return SOSFormed;
        }
    }

    //This function checks to see if a vertical SOS has been formed.
    public boolean checkVertical(PlayerBox player, Board SOSBoard, GUI gui) {
        int firstSRowPosition;
        int firstSColumnPosition;
        int firstORowPosition;
        int firstOColumnPosition;
        int secondSRowPosition;
        int secondSColumnPosition;
        boolean SOSFormed = false;
        boolean temp1 = false;

        //It will be checking from a top-to-bottom, left-to-right way.
        for (int i = 0; i < boardSize; ++i) {
            firstSRowPosition = -1;
            firstSColumnPosition = -1;
            firstORowPosition = -1;
            firstOColumnPosition = -1;
            secondSRowPosition = -1;
            secondSColumnPosition = -1;

            for (int j = 0; j < boardSize; ++j) {

                //The only point of checking is if there is enough rows to form an SOS.
                //So, check to see if there is still three spaces until we reach the end of the board.
                if (j + 2 < boardSize) {

                    //If the value at row j column i is an S, then keep those i and j values in our variables.
                    if (this.getBoxOfBoard(j, i).getValue().equals("S")) {
                        firstSRowPosition = j;
                        firstSColumnPosition = i;
                    }

                    //If the firstSRowPosition doesn't equal -1 (meaning if we have found our first S) and if
                    //the value at row j + 1 column i is an O, then keep those values.
                    if (firstSRowPosition != -1) {
                        if (this.getBoxOfBoard(j + 1, i).getValue().equals("O")) {
                            firstORowPosition = j + 1;
                            firstOColumnPosition = i;
                        }
                    }

                    //Otherwise, reset the first S position back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                    }

                    //If the firstSRowPosition and the firstORowPosition doesn't equal -1 (meaning if we have found
                    //our first S and an O) and if the value at row j + 2 column i is S, then keep those values.
                    if (firstSRowPosition != -1 && firstORowPosition != -1) {
                        if (this.getBoxOfBoard(j + 2, i).getValue().equals("S")) {
                            secondSRowPosition = j + 2;
                            secondSColumnPosition = i;
                        }
                    }

                    //Otherwise, reset the first S and O position back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                    }

                    //If the secondSRowPosition doesn't equal -1 (meaning if we have found our last S) and if either
                    //our SOSBoard (the board that keeps track of all the SOS's) doesn't have an S or an O at
                    //the positions we have kept, that means a new SOS has been formed.
                    //So, update the scores, then check the left-to-right diagonal. Then,
                    //make that new SOS in our SOSBoard at those positions,
                    //assign our SOSFormed variable to true, change i and j back to 0 (to make sure we
                    //didn't miss anything), change the position variables back to -1, and if it is a Simple Game,
                    //then change the gameOver variable to true.  Also, make sure to draw the line in the GUI.
                    if (secondSRowPosition != -1 &&
                            (SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).getValue() != "S" ||
                                    SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).getValue() != "O" ||
                                    SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).getValue() != "S")) {

                        player.updatePlayerScore();

                        temp1 = this.checkLeftToRightDiagonal(player, SOSBoard, gui);

                        SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).drawS();
                        SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).drawO();
                        SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).drawS();

                        gui.drawLine(firstSRowPosition, firstSColumnPosition, firstORowPosition,
                                firstOColumnPosition, secondSRowPosition, secondSColumnPosition);

                        i = 0;
                        j = 0;
                        SOSFormed = true;
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;

                        if(getIsSimpleGame()) {
                            gameOver = true;
                        }
                    }

                    //Otherwise, (if it is an old SOS that has been already counted), change the values back to -1.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;
                    }
                }
            }
        }

        //If we haven't already checked the left-to-right diagonal, then check it now.
        if(!temp1) {
            temp1 = this.checkLeftToRightDiagonal(player, SOSBoard, gui);
        }

        //Return back whether an SOS has been formed.
        if(temp1) {
            return temp1;
        }
        else {
            return SOSFormed;
        }
    }

    //This function checks to see if a diagonal SOS is formed from left-to-right
    public boolean checkLeftToRightDiagonal(PlayerBox player, Board SOSBoard, GUI gui) {
        int firstSRowPosition;
        int firstSColumnPosition;
        int firstORowPosition;
        int firstOColumnPosition;
        int secondSRowPosition;
        int secondSColumnPosition;
        boolean SOSFormed = false;
        boolean temp1 = false;

        //It will be checking from right-to-left, top-to-bottom way.
        for (int i = 0; i < boardSize; ++i) {
            firstSRowPosition = -1;
            firstSColumnPosition = -1;
            firstORowPosition = -1;
            firstOColumnPosition = -1;
            secondSRowPosition = -1;
            secondSColumnPosition = -1;

            for (int j = boardSize - 1; j > -1; --j) {

                //The only point of checking is if there is enough spaces to fill an SOS.
                //So, check to see if there is still three spaces diagonally from left to right
                //until we reach the end of the board.
                if (j + 2 < boardSize && i + 2 < boardSize) {

                    //If the value at row i column j is an S, then keep those i and j values in our variables.
                    if (this.getBoxOfBoard(i, j).getValue() == "S") {
                        firstSRowPosition = i;
                        firstSColumnPosition = j;
                    }

                    //If the firstSRowPosition doesn't equal -1 (meaning if we have found our first S) and if
                    //the value at row i + 1 column j + 1 is an O, then keep those values.
                    if (secondSRowPosition == -1) {
                        if (this.getBoxOfBoard(i + 1, j + 1).getValue() == "O") {
                            firstORowPosition = i + 1;
                            firstOColumnPosition = j + 1;
                        }
                    }

                    //Otherwise, reset the first S position back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                    }

                    //If the firstSRowPosition and the firstORowPosition doesn't equal -1
                    //(meaning if we have found our first S and an O) and if
                    //the value at row i + 2 column j + 2 is S, then keep those values.
                    if (firstSRowPosition != -1 && firstORowPosition != -1) {
                        if (this.getBoxOfBoard(i + 2, j + 2).getValue() == "S") {
                            secondSRowPosition = i + 2;
                            secondSColumnPosition = j + 2;
                        }
                    }

                    //Otherwise, reset the first S and O position back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                    }

                    //If the secondSRowPosition doesn't equal -1 (meaning if we have found our last S) and if either
                    //our SOSBoard (the board that keeps track of all the SOS's) doesn't have an S or an O at
                    //the positions we have kept, that means a new SOS has been formed.
                    //So, update the scores, then check to see if the right-to-left diagonal is formed. Then,
                    //make that new SOS in our SOSBoard at those positions,
                    //assign our SOSFormed variable to true, change i and j back to 0 (to make sure we
                    //didn't miss anything), change the position variables back to -1, and if it is a Simple Game,
                    //then change the gameOver variable to true.  Also, make sure to draw the line in the GUI.
                    if (secondSRowPosition != -1 &&
                            (SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).getValue() != "S" ||
                                    SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).getValue() != "O" ||
                                    SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).getValue() != "S")) {

                        player.updatePlayerScore();

                        temp1 = this.checkRightToLeftDiagonal(player, SOSBoard, gui);

                        SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).drawS();
                        SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).drawO();
                        SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).drawS();

                        gui.drawLine(firstSRowPosition, firstSColumnPosition, firstORowPosition,
                                firstOColumnPosition, secondSRowPosition, secondSColumnPosition);


                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;
                        i = 0;
                        j = boardSize - 1;
                        SOSFormed = true;

                        if(getIsSimpleGame()) {
                            gameOver = true;
                        }
                    }

                    //Otherwise, (if it is an old SOS that has been already counted), change the values back to -1.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;
                    }
                }
            }
        }

        //If we haven't checked the right-to-left diagonal, then check it now.
        if(!temp1) {
            temp1 = this.checkRightToLeftDiagonal(player, SOSBoard, gui);
        }
        //Return back whether an SOS has been formed.
        if(temp1) {
            return temp1;
        }
        else {
            return SOSFormed;
        }
    }

    //This function checks to see if a diagonal SOS is formed from right-to-left.
    public boolean checkRightToLeftDiagonal(PlayerBox player, Board SOSBoard, GUI gui) {
        int firstSRowPosition;
        int firstSColumnPosition;
        int firstORowPosition;
        int firstOColumnPosition;
        int secondSRowPosition;
        int secondSColumnPosition;
        boolean SOSFormed = false;

        //We will be checking from a left-to-right, bottom-to-top way.
        for (int i = boardSize - 1; i > -1; --i) {
            firstSRowPosition = -1;
            firstSColumnPosition = -1;
            firstORowPosition = -1;
            firstOColumnPosition = -1;
            secondSRowPosition = -1;
            secondSColumnPosition = -1;

            for (int j = 0; j < boardSize; ++j) {

                //The only point of checking is if there is enough spaces to fill an SOS.
                //So, check to see if there is still three spaces diagonally from right to left
                //until we reach the end of the board.
                if (j - 2 > -1 && i + 2 < boardSize) {

                    //If the value at row i column j is an S, then keep those i and j values in our variables.
                    if (this.getBoxOfBoard(i, j).getValue() == "S") {
                        firstSRowPosition = i;
                        firstSColumnPosition = j;
                    }

                    //If the firstSRowPosition doesn't equal -1 (meaning if we have found our first S) and if
                    //the value at row i + 1 column j - 1 is an O, then keep those values.
                    if (secondSRowPosition == -1) {
                        if (this.getBoxOfBoard(i + 1, j - 1).getValue() == "O") {
                            firstORowPosition = i + 1;
                            firstOColumnPosition = j - 1;
                        }
                    }

                    //Otherwise, reset the first S position back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                    }

                    //If the firstSRowPosition and the firstORowPosition doesn't equal -1
                    //(meaning if we have found our first S and an O) and if
                    //the value at row i + 2 column j - 2 is S, then keep those values.
                    if (firstSRowPosition != -1 && firstORowPosition != -1) {
                        if (this.getBoxOfBoard(i + 2, j - 2).getValue() == "S") {
                            secondSRowPosition = i + 2;
                            secondSColumnPosition = j - 2;
                        }
                    }

                    //Otherwise, reset the first S and O position back to -1.  There's no point in keeping them.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                    }

                    //If the secondSRowPosition doesn't equal -1 (meaning if we have found our last S) and if either
                    //our SOSBoard (the board that keeps track of all the SOS's) doesn't have an S or an O at
                    //the positions we have kept, that means a new SOS has been formed.
                    //So, update the scores, make that new SOS in our SOSBoard at those positions,
                    //assign our SOSFormed variable to true, change i and j back to 0 (to make sure we
                    //didn't miss anything), change the position variables back to -1, and if it is a Simple Game,
                    //then change the gameOver variable to true.  Also, make sure to draw the line in the GUI.
                    if (secondSRowPosition != -1 &&
                            (SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).getValue() != "S" ||
                                    SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).getValue() != "O" ||
                                    SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).getValue() != "S")) {

                        player.updatePlayerScore();
                        SOSBoard.getBoxOfBoard(firstSRowPosition, firstSColumnPosition).drawS();
                        SOSBoard.getBoxOfBoard(firstORowPosition, firstOColumnPosition).drawO();
                        SOSBoard.getBoxOfBoard(secondSRowPosition, secondSColumnPosition).drawS();

                        gui.drawLine(firstSRowPosition, firstSColumnPosition, firstORowPosition,
                                firstOColumnPosition, secondSRowPosition, secondSColumnPosition);

                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;
                        i = boardSize - 1;
                        j = 0;
                        SOSFormed = true;

                        if(getIsSimpleGame()) {
                            gameOver = true;
                        }
                    }

                    //Otherwise, (if it is an old SOS that has been already counted), change the values back to -1.
                    else {
                        firstSRowPosition = -1;
                        firstSColumnPosition = -1;
                        firstORowPosition = -1;
                        firstOColumnPosition = -1;
                        secondSRowPosition = -1;
                        secondSColumnPosition = -1;
                    }
                }
            }
        }

        //Return back whether an SOS has been formed.
        return SOSFormed;
    }

    //This function checks to see if the board is full.
    public boolean isBoardFull() {
        //Assume that the board is full (so set it to true).
        boolean isFull = true;

        for(int i = 0; i < boardSize; ++i) {
            for(int j = 0; j < boardSize; ++j) {

                //If the board has an empty box, then set the boolean variable to false and return it.
                if(this.getBoxOfBoard(i, j).getValue() != "S" &&
                        this.getBoxOfBoard(i, j).getValue() != "O") {
                    isFull = false;
                    return isFull;
                }
            }
        }

        //If we've gone through the whole board and haven't returned anything, then the board is full and return true.
        return isFull;
    }

    //Getters and setters.
    public int getBoardSize() {return boardSize;}
    public void setIsBlueTurn(boolean turn) {isBluePlayerTurn = turn;}
    public boolean getIsBlueTurn() {return isBluePlayerTurn;}
    public void setIsSimpleGame(boolean gameMode) {isSimpleGame = gameMode;}
    public boolean getIsSimpleGame() {return isSimpleGame;}
    public void setGameOver(boolean isGameOver) {gameOver = isGameOver;}
    public boolean getGameOver() {return gameOver;}
    public boolean getIsBluePlayerComputer() {return bluePlayerComputer;}
    public void setBluePlayerComputer(boolean bluePlayerComputer) {this.bluePlayerComputer = bluePlayerComputer;}
    public boolean getIsRedPlayerComputer() {return redPlayerComputer;}
    public void setRedPlayerComputer(boolean redPlayerComputer) {this.redPlayerComputer = redPlayerComputer;}
}