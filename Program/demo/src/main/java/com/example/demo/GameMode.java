package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class GameMode {
    //Variables for Game mode.
    private RadioButton gg;
    private RadioButton sg;
    private Label label;

    //Constructor
    public GameMode() {
        gg = new RadioButton("General Game");
        sg = new RadioButton("Simple Game");
        label = new Label("SOS");
    }

    //Constructor.
    public GameMode(HBox hbox, Board board) {
        gg = new RadioButton("General Game");
        sg = new RadioButton("Simple Game");
        label = new Label("SOS");

        if(board.getIsSimpleGame()) {
            sg.setSelected(true);
        }
        else {
            gg.setSelected(true);
        }

        ToggleGroup group = new ToggleGroup();
        gg.setToggleGroup(group);
        sg.setToggleGroup(group);

        hbox.getChildren().addAll(label, sg, gg);
        hbox.setSpacing(10);


    }

    //Return if gg is selected.
    public boolean getGG () {
        return gg.isSelected();
    }

    //Return if sg is selected.
    public boolean getSG () {
        return sg.isSelected();
    }
}