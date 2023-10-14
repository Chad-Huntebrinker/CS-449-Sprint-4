package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerBox {

    //Variables of PlayerBox.
    private RadioButton sButton;
    private RadioButton oButton;
    private RadioButton computerButton;
    private RadioButton humanButton;
    private Label playerLabel;
    private Label playerScore;
    private int score;

    //Constructor.
    public PlayerBox() {
        sButton = new RadioButton("S");
        oButton = new RadioButton("O");
        playerLabel = new Label();
        playerScore = new Label();
        score = 0;
        playerScore.setText("Score: " + Integer.toString(score));
        sButton.setSelected(true);

        ToggleGroup group = new ToggleGroup();
        sButton.setToggleGroup(group);
        oButton.setToggleGroup(group);
    }

    //Constructor.
    public PlayerBox(String playerName, VBox vbox) {
        sButton = new RadioButton("S");
        oButton = new RadioButton("O");
        playerLabel = new Label(playerName);
        computerButton = new RadioButton("Computer");
        humanButton = new RadioButton("Human");
        score = 0;
        playerScore = new Label();
        playerScore.setText("Score: " + Integer.toString(score));
        sButton.setSelected(true);
        humanButton.setSelected(true);

        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();
        sButton.setToggleGroup(group1);
        oButton.setToggleGroup(group1);
        computerButton.setToggleGroup(group2);
        humanButton.setToggleGroup(group2);
        sButton.setTranslateX(25);
        oButton.setTranslateX(25);

        vbox.getChildren().addAll(playerLabel, playerScore, humanButton, sButton, oButton, computerButton);
        vbox.setSpacing(2);


    }

    //Return if the S button is selected.
    public boolean getS () {
        return sButton.isSelected();
    }

    //Return if the O button is selected.
    public boolean getO () {
        return oButton.isSelected();
    }

    public boolean getComputerButton() {return computerButton.isSelected();}
    public boolean getHumanButton() {return humanButton.isSelected();}

    public void updatePlayerScore() {
        score = ++score;
        playerScore.setText("Score: " + Integer.toString(score));
    }
    public void setScore(int temp) {
        score = temp;
        playerScore.setText("Score: " + Integer.toString(score));
    }

    public int getScore() {return score;}
}