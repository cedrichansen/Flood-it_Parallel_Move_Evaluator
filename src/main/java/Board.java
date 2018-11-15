import java.util.Random;

public class Board {


    public Space[][] spaces;
    public int numStepsTaken;

    public Board(Space[][] spaces, int steps) {

        this.spaces = spaces;
        numStepsTaken = steps;
    }


    public static Space[][] generateRandomBoard(int row, int col, int numColours) {
        Random r = new Random();
        Space[][] firstBoard = new Space[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                firstBoard[i][j] = new Space(r.nextInt(numColours), false);

            }
        }
        firstBoard[0][0].setEncapsulated(true);   // Space at [0][0] is by default the starting point, so it must be encapsulated

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

}
