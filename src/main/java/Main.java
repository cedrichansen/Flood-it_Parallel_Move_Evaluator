
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ForkJoinPool;

public class Main extends Application {



    private static Board                    b;
    private static int                      numMoves;

    private static Pane                     root;
    private static GridView<Color>          grid;
    private static ObservableList<Color>    colours;
    private static Button                   redButton;
    private static Button                   yellowButton;
    private static Button                   blueButton;
    private static Button                   greenButton;
    private static Button                   purpleButton;
    private static Button                   orangeButton;
    private static Label                    numMovesLabel;
    private static VBox                     vbox;



    public static void main(String[] args) {

        launch(args);

    }

    public void start(Stage primaryStage) throws Exception {

        b = Board.generateRandomBoard(10, 10, 6);
        b.printBoard();

        Board boardToSolve = new Board(b);

        ForkJoinPool childBoardSolver = new ForkJoinPool();
        childBoardSolver.invoke(boardToSolve);

        vbox = new VBox(5);

        numMoves = 0;

        colours = FXCollections.observableArrayList();
        for (int i = 0; i<b.getSpaces().length; i++) {
            for (int j = 0; j<b.getSpaces()[0].length; j++) {
                colours.add(Board.getColour(b.getSpaces()[i][j].getColour()));
            }
        }


        grid = new GridView<Color>(colours);

        grid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
            public GridCell<Color> call(GridView<Color> param) {
                ColorGridCell cell = new ColorGridCell();

                cell.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                return cell;
            }
        });


        grid.setCellHeight(45);
        grid.setCellWidth(45);
        grid.setHorizontalCellSpacing(0);
        grid.setVerticalCellSpacing(0);


        redButton = new Button("   ");
        redButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        redButton.setStyle("-fx-background-color: red;");
        redButton.setLayoutX(100);
        redButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if ((b.getSpaces()[0][0].getColour() != 0  || numMoves == 0)&& !b.isDoneFlooding()) {
                    numMoves++;
                    b.changeColour(0);
                    changeGrid();
                    System.out.println();
                    b.printBoard();
                }
            }
        });


        blueButton = new Button("   ");
        blueButton.setStyle("-fx-background-color: blue;");
        blueButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        blueButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if ((b.getSpaces()[0][0].getColour() != 1  || numMoves == 0)&& !b.isDoneFlooding()) {
                    numMoves++;
                    b.changeColour(1);
                    changeGrid();
                    System.out.println();
                    b.printBoard();
                }
            }
        });
        yellowButton = new Button("   ");
        yellowButton.setStyle("-fx-background-color: yellow;");
        yellowButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        yellowButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if ((b.getSpaces()[0][0].getColour() != 2  || numMoves == 0)&& !b.isDoneFlooding()) {
                    numMoves++;
                    b.changeColour(2);
                    changeGrid();
                    System.out.println();
                    b.printBoard();
                }
            }
        });

        greenButton = new Button("   ");
        greenButton.setStyle("-fx-background-color: green;");
        greenButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        greenButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if ((b.getSpaces()[0][0].getColour() != 3  || numMoves ==0)&& !b.isDoneFlooding()) {
                    numMoves++;
                    b.changeColour(3);
                    changeGrid();
                    System.out.println();
                    b.printBoard();
                }
            }
        });

        purpleButton = new Button("   ");
        purpleButton.setStyle("-fx-background-color: purple;");
        purpleButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        purpleButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if ((b.getSpaces()[0][0].getColour() != 4 || numMoves == 0)&& !b.isDoneFlooding()) {
                    numMoves++;
                    b.changeColour(4);
                    changeGrid();
                    System.out.println();
                    b.printBoard();

                }
            }
        });

        orangeButton = new Button("   ");
        orangeButton.setStyle("-fx-background-color: orange;");
        orangeButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        orangeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if ((b.getSpaces()[0][0].getColour() != 5 || numMoves == 0)&& !b.isDoneFlooding()) {
                    numMoves++;
                    b.changeColour(5);
                    changeGrid();
                    System.out.println();
                    b.printBoard();

                }
            }
        });


        numMovesLabel = new Label("Number of moves: " + numMoves);
        numMovesLabel.setFont(Font.font ("Verdana", FontWeight.BOLD,20));
        vbox.setAlignment( Pos.BOTTOM_CENTER);

        vbox.getChildren().addAll(grid,numMovesLabel, yellowButton, blueButton, redButton, greenButton, purpleButton, orangeButton);

        root = new StackPane();
        StackPane.setMargin(grid, new Insets(8,8,8,8));

        root.getChildren().addAll(vbox);

        Scene scene = new Scene(root, 485, 725);

        primaryStage.setTitle("Flood It - Solver");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


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
                colours.add(Board.getColour(b.getSpaces()[i][j].getColour()));
            }
        }


        grid = new GridView<Color>(colours);

        grid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
            public GridCell<Color> call(GridView<Color> param) {

                ColorGridCell cell = new ColorGridCell();

                cell.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                return cell;
            }
        });

        numMovesLabel.setText("Number of moves: " + numMoves + "/17");
        Label finishedLabel = new Label();
        if (numMoves <=17) {
            finishedLabel.setText("You win!");
        } else {
            finishedLabel.setText("You lose!");
        }



        grid.setCellHeight(45);
        grid.setCellWidth(45);
        grid.setHorizontalCellSpacing(0);
        grid.setVerticalCellSpacing(0);

        //root = new StackPane();
        //root.setAlignment(Pos.BOTTOM_RIGHT);
        StackPane.setMargin(grid, new Insets(8,8,8,8));
        vbox.getChildren().addAll(grid,numMovesLabel, yellowButton, blueButton, redButton, greenButton, purpleButton, orangeButton);
        if (b.isDoneFlooding()) {
            vbox.getChildren().add(finishedLabel);
        }

    }


}
