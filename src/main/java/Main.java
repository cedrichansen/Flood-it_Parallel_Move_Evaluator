
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main extends Application {


    static Board b;
    static Pane root;
    static GridView<Color> grid;
    static ObservableList<Color> colours;
    static Button redButton;
    static Button yellowButton;
    static Button blueButton;
    static Button greenButton;
    static Button purpleButton;
    static Button orangeButton;
    static Label numMovesLabel;
    static int numMoves;


    Scanner kb = new Scanner(System.in);
    static VBox vbox;


    public static void main(String[] args) {

        b = Board.generateRandomBoard(10, 10, 6);
        b.printBoard();

        //playGame();


        launch(args);


    }


    public void changeColour(int newColour) {
        b.changeEncapsulatedColour(newColour);
        b.assignNewEncapsulating(newColour, b);

        int numEncapsulated = 1;
        int prevNumEncapsulated = 0;

        while (numEncapsulated != prevNumEncapsulated) {


            b.assignNewEncapsulating(newColour, b);
            prevNumEncapsulated = b.getNumEncapsulatedSpaces();

            b.changeEncapsulatedColour(newColour);

            b.assignNewEncapsulating(newColour, b);

            numEncapsulated = b.getNumEncapsulatedSpaces();

            b.changeEncapsulatedColour(newColour);

            b.assignNewEncapsulating(newColour, b);

            b.changeEncapsulatedColour(newColour);

            b.assignNewEncapsulating(newColour, b);

            b.changeEncapsulatedColour(newColour);

            b.assignNewEncapsulating(newColour, b);


        }
    }

    public static void playGame(){

        //System.out.println("\nEnter new colour");
        Scanner kb = new Scanner(System.in);
        int newColour;

        do {

            System.out.println("\nEnter new colour");
            newColour = Integer.parseInt(kb.nextLine());

            b.changeEncapsulatedColour(newColour);
            b.assignNewEncapsulating(newColour, b);

            int numEncapsulated = 1;
            int prevNumEncapsulated = 0;

            while (numEncapsulated != prevNumEncapsulated) {


                b.assignNewEncapsulating(newColour, b);
                prevNumEncapsulated = b.getNumEncapsulatedSpaces();

                b.changeEncapsulatedColour(newColour);

                b.assignNewEncapsulating(newColour, b);
                numEncapsulated = b.getNumEncapsulatedSpaces();

            }


            b.printBoard();


        } while (!b.isDoneFlooding());


    }


    public javafx.scene.paint.Color getColour(int i) {
        if (i == 0){
            return Color.RED;

        } else if (i == 1) {
            return Color.BLUE;

        } else if (i == 2) {
            return Color.YELLOW;

        } else if (i == 3) {
            return Color.GREEN;

        } else if (i == 4) {
            return Color.PURPLE;

        } else if (i == 5) {
            return Color.ORANGE;

        } else {
            return Color.ANTIQUEWHITE;
        }
    }


    public void changeGrid() {
        vbox.getChildren().remove(grid);
        vbox.getChildren().remove(yellowButton);
        vbox.getChildren().remove(blueButton);
        vbox.getChildren().remove(redButton);
        vbox.getChildren().remove(greenButton);
        vbox.getChildren().remove(purpleButton);
        vbox.getChildren().remove(orangeButton);
        vbox.getChildren().remove(numMovesLabel);

        ObservableList<Color> colours = FXCollections.observableArrayList();
        for (int i = 0; i<b.getSpaces().length; i++) {
            for (int j = 0; j<b.getSpaces()[0].length; j++) {
                colours.add(getColour(b.getSpaces()[i][j].getColour()));
            }
        }


        grid = new GridView<Color>(colours);

        grid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
            public GridCell<Color> call(GridView<Color> param) {

                return new ColorGridCell();
            }
        });

        numMovesLabel = new Label("Number of moves: " + numMoves);


        grid.setCellHeight(45);
        grid.setCellWidth(45);
        grid.setHorizontalCellSpacing(0);
        grid.setVerticalCellSpacing(0);

        //root = new StackPane();
        //root.setAlignment(Pos.BOTTOM_RIGHT);
        StackPane.setMargin(grid, new Insets(8,8,8,8));
        vbox.getChildren().addAll(grid, yellowButton, blueButton, redButton, greenButton, purpleButton, orangeButton, numMovesLabel);

    }

    public void start(Stage primaryStage) throws Exception {

        Task<Void> gameBackGround = new Task<Void>() {

            protected Void call() throws Exception {
                playGame();
                return null;
            }
        };

        vbox = new VBox(5);

        numMoves = 0;



        colours = FXCollections.observableArrayList();
        for (int i = 0; i<b.getSpaces().length; i++) {
            for (int j = 0; j<b.getSpaces()[0].length; j++) {
                colours.add(getColour(b.getSpaces()[i][j].getColour()));
            }
        }


        grid = new GridView<Color>(colours);

        grid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
            public GridCell<Color> call(GridView<Color> param) {

                return new ColorGridCell();
            }
        });


        grid.setCellHeight(45);
        grid.setCellWidth(45);
        grid.setHorizontalCellSpacing(0);
        grid.setVerticalCellSpacing(0);


        redButton = new Button("   ");
        redButton.setStyle("-fx-background-color: red;");
        redButton.setLayoutX(100);
        redButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                numMoves++;
                changeColour(0);
                changeGrid();
                System.out.println();
                b.printBoard();
            }
        });


        blueButton = new Button("   ");
        blueButton.setStyle("-fx-background-color: blue;");
        blueButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                numMoves++;
                changeColour(1);
                changeGrid();
                System.out.println();
                b.printBoard();
            }
        });
        yellowButton = new Button("   ");
        yellowButton.setStyle("-fx-background-color: yellow;");
        yellowButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                numMoves++;
                changeColour(2);
                changeGrid();
                System.out.println();
                b.printBoard();
            }
        });

        greenButton = new Button("   ");
        greenButton.setStyle("-fx-background-color: green;");
        greenButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                numMoves++;
                changeColour(3);
                changeGrid();
                System.out.println();
                b.printBoard();
            }
        });

        purpleButton = new Button("   ");
        purpleButton.setStyle("-fx-background-color: purple;");
        purpleButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                numMoves++;
                changeColour(4);
                changeGrid();
                System.out.println();
                b.printBoard();

            }
        });

        orangeButton = new Button("   ");
        orangeButton.setStyle("-fx-background-color: orange;");
        orangeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                numMoves++;
                changeColour(5);
                changeGrid();
                System.out.println();
                b.printBoard();

            }
        });


        numMovesLabel = new Label("Number of moves: " + numMoves);


        vbox.getChildren().addAll(grid, yellowButton, blueButton, redButton, greenButton, purpleButton, orangeButton,numMovesLabel);

        root = new StackPane();
        StackPane.setMargin(grid, new Insets(8,8,8,8));

        root.getChildren().addAll(vbox);

        Scene scene = new Scene(root, 485, 675);

        primaryStage.setTitle("Flood It - Solver");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();



    }
}
