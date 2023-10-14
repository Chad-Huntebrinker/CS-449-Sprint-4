package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class GUI {

    //Variables for the GUI.
    public Board board;
    private Board SOSBoard;
    private Board recordGameBoard;
    private Pane square = new Pane();
    private Pane gameBoard = new Pane();
    private VBox rightPlayer;
    private VBox leftPlayer;
    private PlayerBox redPlayer;
    private PlayerBox bluePlayer;
    private GameMode menu;
    private HBox menuHB;
    private HBox textFieldHB;
    private Label displayTurn;
    private TextField textField;
    private Label boardSizelabel;
    private Button btn;
    private Label gameModeLabel;


    //Constructor for the GUI.
    public GUI(Board board) {
        this.board = board;
        this.board.initBoard();
        SOSBoard = new Board();
        SOSBoard.initBoard();
        square = new Pane();
        gameBoard = new Pane();
        rightPlayer = new VBox();
        leftPlayer = new VBox();
        menuHB = new HBox();
        redPlayer = new PlayerBox("Red Player", rightPlayer);
        bluePlayer = new PlayerBox("Blue Player", leftPlayer);
        square.setPrefSize(1000, 600);
        gameBoard.setPrefSize(400, 400);
        menu = new GameMode(menuHB, board);
        textFieldHB = new HBox();
        displayTurn = new Label();
        boardSizelabel = new Label();
        textField = new TextField();
        btn = new Button();
        gameModeLabel = new Label();
    }

    //Getter for square.
    public Pane getSquare() {return square;}

    //This function makes the GUI.
    public void makeBoard() {
        //Create the Simple and General Game variables.
        SimpleSOSGame sgGame = new SimpleSOSGame();
        GeneralSOSGame ggGame = new GeneralSOSGame();

        //Create the Pane that will contain the board.
        gameBoard = new Pane();
        gameBoard.setPrefSize(400, 400);

        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                Box box1 = new Box();
                SOSBoard.addBox(box1, i, j);

            }
        }

        //Create the board itself based on the boardSize.
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                Box box = new Box(board, redPlayer, bluePlayer, displayTurn, SOSBoard, this,
                        sgGame, ggGame);
                box.setLayoutX(j * (400 / board.getBoardSize()) + 250);
                box.setLayoutY(i * (400 / board.getBoardSize()) + 100);

                //Add the box to both the GUI and the board.
                gameBoard.getChildren().add(box);
                board.addBox(box, i, j);

            }
        }

        //Right player contains the Red Player's control.
        rightPlayer.setLayoutX(700);
        rightPlayer.setLayoutY(300);

        //Left player contains the Blue Player's control.
        leftPlayer.setLayoutX(150);
        leftPlayer.setLayoutY(300);

        //menuHB contains the title of the game, Simple, and General game radio buttons.
        menuHB.setLayoutX(100);
        menuHB.setLayoutY(10);

        //This sets up the board size editor for the GUI.
        textField.setText(String.valueOf(board.getBoardSize()));
        boardSizelabel.setText("Board Size");
        textField.setPrefSize(30, 2);
        textFieldHB.getChildren().addAll(boardSizelabel, textField);
        textFieldHB.setSpacing(5);
        textFieldHB.setLayoutX(700);
        textFieldHB.setLayoutY(10);

        btn.setText("New Game");
        btn.setLayoutX(900);
        btn.setLayoutY(550);

        //Shows whose turn it is.
        displayTurn.setText("Blue Player's Turn");
        displayTurn.setFont(Font.font(15));
        displayTurn.setLayoutX(390);
        displayTurn.setLayoutY(75);

        if(board.getIsSimpleGame()) {
            gameModeLabel.setText("Simple Game");
        }
        else {
            gameModeLabel.setText("General Game");
        }
        gameModeLabel.setFont(Font.font(15));
        gameModeLabel.setLayoutX(405);
        gameModeLabel.setLayoutY(55);

        //Add all that stuff to the main part of the GUI
        square.getChildren().addAll(gameBoard, rightPlayer, leftPlayer, menuHB,
                textFieldHB, displayTurn, btn, gameModeLabel);

        //If the blue player is a computer and it is a simple game, then the blue computer
        //should start the simple game.
        if(board.getIsBluePlayerComputer() && board.getIsSimpleGame()) {
            sgGame.gameRules(board, SOSBoard, bluePlayer, redPlayer, this,
                                board.getBoxOfBoard(0,0), displayTurn);
        }
        //If the blue player is a computer and it is a general game, then the blue computer
        //should start the simple game.
        if(board.getIsBluePlayerComputer() && (!board.getIsSimpleGame())) {
            ggGame.gameRules(board, SOSBoard, bluePlayer, redPlayer, this,
                    board.getBoxOfBoard(0,0), displayTurn);
        }


        //If the button next to the textField is pressed, then set the board size to the new value and
        //run the restGUI function.
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                board.setBoardSize(Integer.parseInt(textField.getText()));
                SOSBoard.setBoardSize(board.getBoardSize());
                resetGUI();
            }
        });
    }


    //This function resets the GUI by removing all the children, reseting the board and initiating the board.
    //Then run makeBoard function
    public void resetGUI() {
        boolean isSGSelected = menu.getSG();
        boolean isRedComputer = redPlayer.getComputerButton();
        boolean isBlueComputer = bluePlayer.getComputerButton();
        square.getChildren().removeAll(gameBoard, rightPlayer, leftPlayer, menuHB,
                textFieldHB, displayTurn, btn, gameModeLabel);
        textFieldHB.getChildren().removeAll(boardSizelabel, textField);
        board.resetBoard();
        board.initBoard();
        board.setIsSimpleGame(isSGSelected);
        board.setRedPlayerComputer(isRedComputer);
        board.setBluePlayerComputer(isBlueComputer);
        SOSBoard.resetBoard();
        SOSBoard.initBoard();
        SOSBoard.setIsSimpleGame(isSGSelected);
        SOSBoard.setRedPlayerComputer(isRedComputer);
        SOSBoard.setBluePlayerComputer(isBlueComputer);
        bluePlayer.setScore(0);
        redPlayer.setScore(0);
        makeBoard();

    }

    //This function draws the lines based off of where the box is located and who's turn it is.
    public void drawLine(int firstSRow, int firstSColumn, int firstORow, int firstOColumn,
                         int secondSRow, int secondSColumn) {
        Line line = new Line();

        //Set up where the line will start and end.
        line.setStartX(board.getBoxOfBoard(firstSRow, firstSColumn).getLayoutX() + (200 / board.getBoardSize()));
        line.setStartY(board.getBoxOfBoard(firstSRow, firstSColumn).getLayoutY() + (300 / board.getBoardSize()));
        line.setEndX(board.getBoxOfBoard(secondSRow, secondSColumn).getLayoutX() + (200 / board.getBoardSize()));
        line.setEndY(board.getBoxOfBoard(secondSRow, secondSColumn).getLayoutY() + (300 / board.getBoardSize()));

        //If it is BLue Player's turn, then the line for the SOS should be blue.
        //Otherwise, it should be red.
        if(board.getIsBlueTurn()) {
            line.setStroke(Color.BLUE);
        }
        else {
            line.setStroke(Color.RED);
        }

        //Set up the size of the line and add it into the gameBoard pane.
        line.setStrokeWidth(50 / board.getBoardSize());
        gameBoard.getChildren().add(line);

    }
}