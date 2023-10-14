//Chad Huntebrinker
//CS 449

package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;


public class HelloApplication extends Application {

    //Create a Board and GUI variable
    Board board = new Board();
    GUI gui = new GUI(board);

    //run the function makeBoard in the GUI.
    //Then, return the main pane that holds the GUI (or square).
    private Parent makeContent() {
        gui.makeBoard();
        return gui.getSquare();

    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(makeContent()));
        stage.show();
    }
}