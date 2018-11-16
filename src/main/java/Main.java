
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {


    static Board b;

    public static void main(String[] args) {

        b = Board.generateRandomBoard(10, 10, 6);
        b.printBoard();

        launch(args);


    }

    public void playGame(){

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

    public void start(Stage primaryStage) throws Exception {

        Task<Void> gameBackGround = new Task<Void>() {

            protected Void call() throws Exception {
                playGame();
                return null;
            }
        };


        ObservableList<Color> colours = FXCollections.observableArrayList();
        for (int i = 0; i<b.getSpaces().length; i++) {
            for (int j = 0; j<b.getSpaces()[0].length; j++) {
                colours.add(getColour(b.getSpaces()[i][j].getColour()));
            }
        }


        GridView<Color> grid = new GridView<Color>(colours);
        grid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
            public GridCell<Color> call(GridView<Color> param) {
                return new ColorGridCell();
            }
        });
        grid.setCellHeight(45);
        grid.setCellWidth(45);
        grid.setHorizontalCellSpacing(0);
        grid.setVerticalCellSpacing(0);

        TextField textField = new TextField();



        StackPane root = new StackPane();
        root.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setMargin(grid, new Insets(8,8,8,8));
        root.getChildren().add(grid);
        root.getChildren().add(textField);
        Scene scene = new Scene(root, 485, 575);

        primaryStage.setTitle("Flood It - Solver");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
