import java.util.Random;

public class Board {

    private Space[][] spaces;
    private int numStepsTaken;
    private int numColours;

    //generateInitial board
    public Board(Space[][] spaces, int steps, int numColours) {

        this.spaces = spaces;
        numStepsTaken = steps;
    }

    public Board(Board b) {

        //TODO call a function that appropriatly modifies the spaces
        this.spaces = b.spaces;
        this.numStepsTaken = b.numStepsTaken++;
        this.numColours = b.numColours;
    }




    public void changeEncapsulatedColour(){

    }




    public static Board generateRandomBoard(int row, int col, int numColours) {
        Random r = new Random();
        Space[][] firstSpaces = new Space[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                firstSpaces[i][j] = new Space(r.nextInt(numColours), false);

            }
        }
        firstSpaces[0][0].setEncapsulated(true);   // Space at [0][0] is by default the starting point, so it must be encapsulated

        Board firstBoard = new Board(firstSpaces, 0, numColours);
        return firstBoard;
    }


    public void printBoard() {
        int length = spaces.length;
        int width = spaces[0].length;
        for (int i = 0; i < length; i++) {
            String row = "";
            for (int j = 0; j < width; j++) {
                row += spaces[i][j] + " ";
            }
            System.out.println(row);
        }
    }






    public Space[][] getSpaces() {
        return spaces;
    }

    public void setSpaces(Space[][] spaces) {
        this.spaces = spaces;
    }

    public int getNumStepsTaken() {
        return numStepsTaken;
    }

    public void setNumStepsTaken(int numStepsTaken) {
        this.numStepsTaken = numStepsTaken;
    }

    public int getNumColours() {
        return numColours;
    }

    public void setNumColours(int numColours) {
        this.numColours = numColours;
    }


}
