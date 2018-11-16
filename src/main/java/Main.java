
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public static void main(String[] args) {


        launch(args);

        Board b = Board.generateRandomBoard(10, 10, 6);
        b.printBoard();

        //System.out.println("\nEnter new colour");
        Scanner kb = new Scanner(System.in);
        int newColour;





        do {

            System.out.println("\nEnter new colour");
            newColour = Integer.parseInt(kb.nextLine());

            //System.out.println("\n\n\nchanging encapsulated colour\n");
            b.changeEncapsulatedColour(newColour);
            //b.printBoard();




            //System.out.println("\n\n\nadding encapsulated neighbours\n");




            b.assignNewEncapsulating(newColour, b);



            //b.printBoard();

            int numEncapsulated = 1;
            int prevNumEncapsulated = 0;

            while (numEncapsulated != prevNumEncapsulated) {


                //System.out.println("\n\n\nadding encapsulated neighbours\n");
                b.assignNewEncapsulating(newColour, b);
                //b.printBoard();
                prevNumEncapsulated = b.getNumEncapsulatedSpaces();

                //System.out.println("\n\n\nchanging encapsulated colour after reassigning encapsulated\n");
                b.changeEncapsulatedColour(newColour);
                //b.printBoard();

                //System.out.println("\n\n\nadding encapsulated neighbours\n");
                b.assignNewEncapsulating(newColour, b);
                //b.printBoard();
                numEncapsulated = b.getNumEncapsulatedSpaces();


                b.printBoard();

            }


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

        ObservableList<Color> colours = FXCollections.observableArrayList();
        Board b = Board.generateRandomBoard(10, 10, 6);
        b.printBoard();
        for (int i = 0; i<b.getSpaces().length; i++) {
            for (int j = 0; j<b.getSpaces()[0].length; j++) {
                colours.add(getColour(b.getSpaces()[i][j].getColour()));
            }
        }


        GridView<Color> grid = new GridView<Color>(colours);

//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });


        grid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
            public GridCell<Color> call(GridView<Color> param) {
                return new ColorGridCell();
            }
        });
        grid.setCellHeight(45);
        grid.setCellWidth(45);
        grid.setHorizontalCellSpacing(0);
        grid.setVerticalCellSpacing(0);

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        StackPane.setMargin(grid, new Insets(8,8,8,8));
        root.getChildren().add(grid);


        Scene scene = new Scene(root, 485, 575);


        primaryStage.setTitle("Flood It - Solver");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
