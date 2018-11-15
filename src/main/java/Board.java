import java.util.ArrayList;
import java.util.Random;

public class Board {


    //no need to keep track of which state the current colour of encapsulated section, because it will always be the
    //colour of spaces[0][0]
    private         Space[][]       spaces;
    private         int             numStepsTaken;
    private         int             numColours;



    //generateInitial board
    public Board(Space[][] spaces, int steps, int numColours) {

        this.spaces = spaces;
        numStepsTaken = steps;
    }

    //Generate a subsequent board to initial one
    public Board(Board b, int newColour) {

        //TODO call a function that appropriatly modifies the spaces
        this.spaces = changeColour(newColour);
        this.numStepsTaken = b.numStepsTaken++;
        this.numColours = b.numColours;
    }



    public Space[][] changeColour(int newColour){
        changeEncapsulatedColour(newColour);
        changeSurroundingColours(newColour);
       return spaces;

    }


    public void changeSurroundingColours(int newColour){
        ArrayList<Space> spaces = getSpacesToBeChanged();
        for (Space s : spaces) {
            s.setColour(newColour);
            s.setEncapsulated(true);
        }
    }


    public ArrayList<Space> getSpacesToBeChanged() {
        ArrayList<Space> surroundingSpaces = new ArrayList<Space>();

        int encapsulatedColour = spaces[0][0].getColour();

        for (int i = 0; i<spaces.length;i++) {
            for (int j=0; j<spaces[0].length; j++) {

                //look at at neighbours and only add it to list to be changed if the neighbour is encapsulated, and
                // is the same colour
                ArrayList<Space> neighbours = getSpaceNeighbours(i,j);
                for (Space s :neighbours) {
                    if (s.isEncapsulated() && (s.getColour()==encapsulatedColour)) {
                        surroundingSpaces.add(s);
                        s.setEncapsulated(true);
                        break;
                    }
                }
            }
        }

        return surroundingSpaces;

    }


        //helper for getSpaces to be changed
    public boolean isValidIndex(int i, int j) {
        return (i<spaces.length && 0<=i) && (j<spaces[0].length && j<spaces[0].length);
    }


    public ArrayList<Space> getSpaceNeighbours(int i, int j){

        ArrayList<Space> neighbours = new ArrayList<Space>();

        if (isValidIndex(i, j-1)) {
            neighbours.add(spaces[i][j-1]);
        }
        if (isValidIndex(i, j+1)) {
            neighbours.add(spaces[i][j+1]);
        }
        //--------------------------------//
        if (isValidIndex(i-1, j-1)) {
            neighbours.add(spaces[i-1][j-1]);

        }
        if (isValidIndex(i-1, j)) {
            neighbours.add(spaces[i-1][j]);

        }
        if (isValidIndex(i-1, j+1)) {
            neighbours.add(spaces[i-1][j+1]);

        }
        //--------------------------------//
        if (isValidIndex(i+1, j-1)) {
            neighbours.add(spaces[i+1][j-1]);
        }
        if (isValidIndex(i+1, j)) {
            neighbours.add(spaces[i+1][j]);
        }
        if (isValidIndex(i+1, j+1)) {
            neighbours.add(spaces[i+1][j+1]);
        }

        return neighbours;
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

        for (int i=0; i<spaces.length; i++) {
            for (int j = 0; j<spaces[0].length; j++) {

                if (spaces[i][j].isEncapsulated()) {
                    encapsulatedSpaces.add(spaces[i][j]);
                }
            }
        }
        return encapsulatedSpaces;
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
