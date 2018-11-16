import java.util.ArrayList;
import java.util.Random;

public class Board {


    //no need to keep track of which state the current colour of encapsulated section, because it will always be the
    //colour of spaces[0][0]
    private         Space[][]           spaces;
    private         int                 numStepsTaken;
    private         int                 numColours;
    private         ArrayList<Space>    encapsulatedSpaces;
    private         int                 numEncapsulatedSpaces;
    private         boolean             doneFlooding = false;



    //generateInitial board
    public Board(Space[][] spaces, int steps, int numColours) {

        this.spaces = spaces;
        numStepsTaken = steps;
    }

    //Generate a subsequent board to initial one
    public Board(Board b, int newColour) {

        //TODO call a function that appropriatly modifies the spaces
        this.spaces = b.spaces;
        this.numStepsTaken = b.numStepsTaken++;
        this.numColours = b.numColours;
    }





    public void assignNewEncapsulating(int newColour, Board b) {
        encapsulatedSpaces = getEncapsulatedSpaces();

        for (Space s : encapsulatedSpaces) {
           addSameColourNeighbours(s.getI(), s.getJ(), b);
        }
    }


    public void addSameColourNeighbours(int i, int j, Board b) {
        Space current = b.spaces[i][j];
        if (isValidSpace(i-1, j, b)) {
            if (b.spaces[i-1][j].getColour() == current.getColour()) {
                b.spaces[i-1][j].setEncapsulated(true);
            }
        }
        if (isValidSpace(i+1, j, b)) {
            if (b.spaces[i+1][j].getColour() == current.getColour()) {
                b.spaces[i+1][j].setEncapsulated(true);
            }
        }
        if (isValidSpace(i, j-1, b)) {
            if (b.spaces[i][j-1].getColour() == current.getColour()) {
                b.spaces[i][j-1].setEncapsulated(true);
            }
        }
        if (isValidSpace(i, j+1, b)) {
            if (b.spaces[i][j+1].getColour() == current.getColour()) {
                b.spaces[i][j+1].setEncapsulated(true);
            }
        }
    }


    public static boolean isValidSpace(int i, int j, Board b) {

        return ((i>=0 && i<b.spaces.length) && (j>=0 && j<b.spaces[0].length));


    }

    public void changeEncapsulatedColour(int newColour){
        ArrayList<Space> spaces = getEncapsulatedSpaces();
        for (Space s : spaces) {
            s.setColour(newColour);
            s.setEncapsulated(true);
        }
    }

    //helper for changeEncapsulateColour
    public ArrayList<Space> getEncapsulatedSpaces(){
        ArrayList<Space> encapsulatedSpaces = new ArrayList<Space>();
        numEncapsulatedSpaces = encapsulatedSpaces.size();


        for (int i=0; i<spaces.length; i++) {
            for (int j = 0; j<spaces[0].length; j++) {

                if (spaces[i][j].isEncapsulated()) {
                    encapsulatedSpaces.add(spaces[i][j]);
                }
            }
        }

        if (encapsulatedSpaces.size()== spaces.length * spaces[0].length) {
            doneFlooding = true;
        }
        return encapsulatedSpaces;
    }




    public static Board generateRandomBoard(int row, int col, int numColours) {
        Random r = new Random();
        Space[][] firstSpaces = new Space[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                firstSpaces[i][j] = new Space(r.nextInt(numColours), false, i, j);

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
                row += spaces[i][j].toString() + " ";
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


    public int getNumEncapsulatedSpaces() {
        return numEncapsulatedSpaces;
    }


    public boolean isDoneFlooding() {
        return doneFlooding;
    }




}
