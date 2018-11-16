import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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
}
