public class Space {

    private int colour;
    private boolean isEncapsulated;

    public Space(int colour, boolean isEncapsulated) {
        this.colour = colour;
        this.isEncapsulated = isEncapsulated;
    }

    public String toString() {
        return Integer.toString(this.colour);
    }







    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public boolean isEncapsulated() {
        return isEncapsulated;
    }

    public void setEncapsulated(boolean encapsulated) {
        isEncapsulated = encapsulated;
    }



}
