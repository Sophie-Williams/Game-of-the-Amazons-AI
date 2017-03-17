package ygraphs.ai.smart_fox.games;

public class Queen extends Tile implements Cloneable{
	private int previousRow;
    private int previousCol;

	public Queen clone() throws CloneNotSupportedException{
        return (Queen) super.clone();
    }
	
    protected boolean isOpponent;

    /**
     *
     * @param x: an integer storing the row location
     * @param y: an integer storing the column location
     */
    public Queen(int x, int y) {
        super(x,y);
    }
    
    public Queen(int x, int y,boolean isOpponent) {
    	super(x,y);
        this.previousRow = x;
        this.previousCol = y;
    	this.isOpponent = isOpponent;
    }

    /**
     *
     * @return: the row position of the current Queen
     */
    public int getRowPosition() {
        return super.row;
    }

    /**
     *
     * @return: the column position of the current Queen
     */
    public int getColPosition() {

        return super.col;
    }

    public void setPreviousRowPosition(int row) {
        this.previousRow = row;
    }

    public void setPreviousColPosition(int col) {
        this.previousCol = col;
    }

    public int getPreviousRowPosition() {
        return this.previousRow;
    }

    public int getPreviousColPosition() {
        return this.previousCol;
    }

    public void moveQueen(int row, int col) {
        setPreviousRowPosition(super.row);
        setPreviousColPosition(super.col);
        super.row = row;
        super.col = col;
    }

    public int[] combinedMove(int row, int col) {
        int[] move = new int[2];
        move[0] = row;
        move[1] = col;
        return move;
    }

}