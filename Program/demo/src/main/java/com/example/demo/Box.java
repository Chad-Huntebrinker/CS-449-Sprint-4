package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Box extends StackPane {
    //Variables for Box.
    private Text text = new Text();
    private Board board;
    private PlayerBox red;
    private PlayerBox blue;

    //Constructors for Box
    public Box() {

        //Set up the rectangle size.
        Rectangle border = new Rectangle(50,50);

        //Set up the boarder of the box.
        border.setFill(null);
        border.setStroke(Color.BLACK);

        //Set up the font in the box
        text.setFont(Font.font(50));
        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

    }

    //Another constructor for Box.
    public Box(Board board, PlayerBox redPlayer,
               PlayerBox bluePlayer, Label displayTurn, Board SOSBoard, GUI gui,
               SimpleSOSGame sgGame, GeneralSOSGame ggGame) {

        this.board = board;
        red = redPlayer;
        blue = bluePlayer;

        //Set up the rectangle size so that it is proportional to how big the board is.
        Rectangle border = new Rectangle((400 / this.board.getBoardSize()),
                (400 / this.board.getBoardSize()));

        //Set up the boarder of the box.
        border.setFill(null);
        border.setStroke(Color.BLACK);

        //Set up the font in the box so that it is proportional to how big the board is.
        text.setFont(Font.font(400 / this.board.getBoardSize()));
        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        //If someone clicks on the box
        setOnMouseClicked(event -> {

            //If the game is not over and the player has clicked on a square with the left side of the mouse.
            if (!this.board.getGameOver()) {
                if (event.getButton() == MouseButton.PRIMARY) {

                    //If it is a Simple Game, then use the Simple Game rules.
                    if(this.board.getIsSimpleGame()) {
                        sgGame.gameRules(this.board, SOSBoard, blue, red, gui, this, displayTurn);
                    }

                    //If it is a General Game, then use the General Game rules.
                    else {
                        ggGame.gameRules(this.board, SOSBoard, blue, red, gui, this, displayTurn);
                    }

                }
            }
        });
    }

    //If the box is empty, put an S in.
    public void drawS() {
        if(text.getText().isEmpty())
            text.setText("S");
    }

    //If the box is empty, put an O in.
    public void drawO() {
        if(text.getText().isEmpty())
        text.setText("O");
    }

    //Returns the value of the text.
    public String getValue() {
        return text.getText();
    }

}