import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.RecursiveAction;

public class Board extends RecursiveAction implements Comparable {


    //no need to keep track of which state the current colour of encapsulated section, because it will always be the
    //colour of spaces[0][0]
    private         Space[][]           spaces;
    private         int                 numStepsTaken;
    private         int                 numColours;
    private         ArrayList<Space>    encapsulatedSpaces;
    private         int                 numEncapsulatedSpaces;
    private         boolean             doneFlooding                = false;

    private         Board               parent;
            static  Board               solution;

    static          int                 numAttempts                 = 0;

    static          ArrayList<String>   cToWin                      = new ArrayList<>();


    //generateInitial board
    public Board(Space[][] spaces, int steps, int numColours, Board p) {

        this.spaces = spaces;
        numStepsTaken = steps;
        this.numColours = numColours;
        parent = p;
    }


    //used to copy a parent board --> this board will become a child of Board b
    public Board(Board b) {
        this.spaces = cloneSpaces(b);
        this.numStepsTaken = b.numStepsTaken + 1;
        this.numColours = b.numColours;

        //the board that is being copied will be the parent
        parent = b;
    }


    public synchronized int increaseNumPathsAttempted() {
        numAttempts++;
        return numAttempts;
    }

    public ArrayList<Color> getStepsToSolveBoard(Board sol) {
        for (;;) {
            if (solution != null) {
                ArrayList <Color> steps = new ArrayList<>();

                Board temp = solution;
                steps.add(getColour(temp.getSpaces()[0][0].getColour()));

                while (temp.parent != null) {
                    temp = temp.parent;
                    steps.add(getColour(temp.getSpaces()[0][0].getColour()));
                }

                //remove last parent because it's technically not a step
                steps.remove(steps.size()-1);
                steps.remove(steps.size()-1);



                Collections.reverse(steps);
                return steps ;

            }
        }
    }


    protected void compute() {

        if (!doneFlooding && solution == null) {

            System.out.println("splitting task");
            List<Board> subtasks = new ArrayList<Board>();

            subtasks.addAll(getNextBoards());

            for (Board b : subtasks) {
                //18 steps is criterion for winning the game
                if (b.getNumStepsTaken() <= 20) {
                    b.fork();
                } else {
                    //System.out.println("path terminated --- did not find solution quickly enough");
                    System.out.println("Failed Boards: " + increaseNumPathsAttempted());
                }
            }


        } else {
            if (solution == null) {
                System.out.println("Solution has been found!");
                solution = this;
                solution.printBoard();

                ArrayList<Color> sColours = getStepsToSolveBoard(solution);

                System.out.println("Steps to solve board\n");
                int count = 0;
                for (Color c : sColours) {
                    System.out.println(count + " : " + printColour(c.toString()));
                    count++;
                }
            }

        }

    }



    public ArrayList<Board> getNextBoards(){

        //save parent board
        final Board parent = this;

        //create copies of the parent to be changed -- each of these correspond to a certain colour change
        Board a =new Board(parent);
        Board b =new Board(parent);
        Board c =new Board(parent);
        Board d =new Board(parent);
        Board e =new Board(parent);
        Board f =new Board(parent);

        Board [] copies = {a,b,c,d,e,f};

        ArrayList<Board> childBoards = new ArrayList<>();

        if (parent.numStepsTaken > 3 && parent.numStepsTaken <12) {

            for (int i=0; i<6; i++) {
                if (3 < getNumAdditionalEncapsulatedSpaces(i, copies[i])) {
                    childBoards.add(copies[i]);
                }
            }

        } else {
            for (int i = 0; i < 6; i++) {
                if (0 < getNumAdditionalEncapsulatedSpaces(i, copies[i])) {

                    childBoards.add(copies[i]);
                }
            }
        }


        Collections.sort(childBoards);
        return childBoards;
    }


    public Space [][] cloneSpaces (Board b) {
        int l= b.spaces.length;
        int w = b.spaces[0].length;
        Space [][] newSpaces = new Space[l][w];
        for (int i =0; i<l; i++) {
            for (int j = 0; j<w;j++) {
                Space old = b.spaces[i][j];
                Space temp = new Space(old.getColour(), old.isEncapsulated(), old.getI(), old.getJ());
                newSpaces[i][j] = temp;
            }
        }

        return newSpaces;
    }


    public int getNumAdditionalEncapsulatedSpaces(int colour, Board child) {

        int beforeChange = child.getEncapsulatedSpaces().size();
        child.changeColour(colour);
        int afterChange = child.getEncapsulatedSpaces().size();

        return afterChange-beforeChange;
    }


    public void changeColour(int newColour) {
        Board b = this;

        changeEncapsulatedColour(newColour);
        assignNewEncapsulating(b);

        int numEncapsulated = 1;
        int prevNumEncapsulated = 0;

        while (numEncapsulated != prevNumEncapsulated) {


            b.assignNewEncapsulating(b);
            prevNumEncapsulated = b.getNumEncapsulatedSpaces();

            b.changeEncapsulatedColour(newColour);

            b.assignNewEncapsulating(b);
            numEncapsulated = b.getNumEncapsulatedSpaces();

        }
    }


    public static String printColour(String hex) {
        if (hex.equals("0xffff00ff")) {
            return "yellow";
        } else if (hex.equals("0x008000ff")) {
            return "green";
        } else if (hex.equals("0xffa500ff")) {
            return "orange";
        } else if (hex.equals("0x0000ffff")) {
            return "blue";
        } else if (hex.equals("0xff0000ff")) {
            return "red";
        } else if (hex.equals("0x800080ff")) {
            return "purple";
        } else {
            return "unknown colour";
        }
    }


    public static javafx.scene.paint.Color getColour(int i) {
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



    public void assignNewEncapsulating(Board b) {
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


        for (int i=0; i<spaces.length; i++) {
            for (int j = 0; j<spaces[0].length; j++) {

                if (spaces[i][j].isEncapsulated()) {
                    encapsulatedSpaces.add(spaces[i][j]);
                }
            }
        }

        numEncapsulatedSpaces = encapsulatedSpaces.size();


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

        Board firstBoard = new Board(firstSpaces, 0, numColours, null);
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


    @Override
    public int compareTo(Object o) {
        return ((Board)o).getNumEncapsulatedSpaces() - this.getNumEncapsulatedSpaces();
    }
}
