import java.util.ArrayList;

public class GameRules {
    private boolean enemyMove;
    protected Tile[][] board = new Tile[10][10];
    protected Queen[] enemy;
    protected Queen[] friend;
    protected ArrayList<Arrow> arrows;
    private ArrayList<int[][]> legalArrowMoves;
    private ArrayList<int[][]> legalQueenMoves;
    /**
     * CONSTRUCTOR to start the game
     * @param start: boolean holding whether or not the game is to be started for the first time

     */
    protected GameRules(boolean start) {
        // If the game is being started for the first time, initialize the board
        if(start) {
            board = new Tile[][] {
                { null, null, null, new Queen(0, 3, true), null, null, new Queen(0, 6, true), null, null, null },
					{ null, null, null, null, null, null, null, null, null, null },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ new Queen(3, 0, true), null, null, null, null, null, null, null, null, new Queen(3, 9, true) },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ new Queen(6, 0, false), null, null, null, null, null, null, null, null, new Queen(6, 9, false) },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ null, null, null, null, null, null, null, null, null,	null },
                    { null, null, null, new Queen(9, 3, false), null, null, new Queen(9, 6, false), null, null, null }
            };

            enemy = new Queen[] { (Queen) board[0][3], (Queen) board[0][6], (Queen) board[3][0], (Queen) board[3][9] };
            friend = new Queen[] { (Queen) board[6][0], (Queen) board[6][9], (Queen) board[9][3], (Queen) board[9][6] };
        }
        else {
            board = new GamePiece[][] {
					{ null, null, null, new Queen(0, 3, false), null, null, new Queen(0, 6, false), null, null, null },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ new Queen(3, 0, false), null, null, null, null, null, null, null, null, new Queen(3, 9, false) },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ new Queen(6, 0, true), null, null, null, null, null, null, null, null, new Queen(6, 9, true) },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ null, null, null, null, null, null, null, null, null,	null },
					{ null, null, null, new Queen(9, 3, true), null, null,	new Queen(9, 6, true), null, null, null }
                };
            enemy = new Queen[] { (Queen) board[6][0], (Queen) board[6][9], (Queen) board[9][3], (Queen) board[9][6] };
            friend = new Queen[] { (Queen) board[0][3], (Queen) board[0][6], (Queen) board[3][0], (Queen) board[3][9] };
        }

        /* Instantiate the ArrayLists for legel arrow shots,
           legal queen moves, and update the moves afterwards
         */
        arrows = new ArrayList<>();
        legalArrowMoves = new ArrayList<>();
        legalQueenMoves = new ArrayList<>();
        updateLegalQueenMoves();

        }

        /**
         * CONSTRUCTOR FOR GameRules
         * @param enemy: the opponent's queen positions
         * @param friend: the player's queen positions
         * @param arrow: holds the positions of the "stones"
         */
    protected GameRules(Queen[] enemy, Queen[] friend, ArrayList<Arrow> arrow) {
        this.enemy = enemy;
        this.friend = friend;
        this.arrows = arrow;
        updateAfterMove();
    }


    protected Queen[] getEnemy() {
        return this.enemy;
    }

    protected Queen[] getFriend() {
        return this.friend;
    }

    protected ArrayList<Arrow> getArrows() {
        return this.arrows;
    }

    protected GameRules deepCopy() {
        Queen[] newFriend = new Queen[4];
        Queen[] newEnemy = new Queen[4];
        ArrayList<Arrow> newArrows = new ArrayList<>();
        for(int i = 0; i < newEnemy.length; i++) {
            newFriend[i] = friend[i].clone();
        }

        if(arrows != null) {
            for(Arrow a: arrows) {
                if(a != null) {
                    newArrows.add(new Arrow(a.row, a.col));
                }
            }
        }

        GameRules newRules = GameRules(newEnemy, newFriend, newArrows);
        return newRules;
    }

    /**
     *
     * @param queen: the queen object to check moves for
     * @return: the best legal move a queen can make from it's current position
     */
    protected ArrayList<int[][]> getLegalMoves(Queen queen) {
        ArrayList<int[][]> legalMoves = newArrayList<>();
        int currentRow = queen.getRowPosition();
        int currentCol = queen.getColPosition();

        /*
            Horizontal Movement Checks
         */

        // Legal moves left
        for(int i = i; currentCol - i >= 0; i++) {
            if(board[currentRow][currentCol-i] == null) {
                legalMoves.add(new Queen(currentRow, currentCol-i, queen));
            }
            else {
                break;
            }
        }

        // Legal moves right
        for(int i = 1; currentCol + i <= 9; i++) {
            if(board[currentRow][currentCol+i] == null) {
                legalMoves.add(new Queen(curRow, curCol+i, queen));
            }
            else {
                break;
            }
        }


        /*
            Vertical Movements Checks
         */

        // Legal moves up
        for(int i = 1; currentRow - i >= 0; i++) {
            if(board[currentRow-i][currentCol] == null) {
                legalMoves.add(new Queen(currentRow-i, currentCol, queen));
            }
            else {
                break;
            }
        }

        // Legal moves down
        for(int i = 0; currentRow + i <= 9; i++) {
            if(board[currentRow+i][currentCol-i] == null) {
                legalMoves.add(new Queen(currentRow+i, currentCol, queen));
            }
            else {
                break;
            }
        }


        /*
            Diagonal Movement Checks
         */


        // Legal moves diagonally left/up
        for(int i = 1; currentRow - i >= 0 && currentCol - i >= 0; i++) {
            if(board[currentRow-i][currentCol-i] == null) {
                legalMoves.add(new Queen(currentRow-i, currentCol-i, queen));
            }
            else {
                break;
            }
        }

        // Legal moves diagonally left/down
        for(int i = 1; currentRow + i <= 9 && currentCol - i >= 0; i++) {
            if(board[currentRow+i][currentCol-i] == null) {
                legalMoves.add(new Queen(currentRow+i, currentCol-i, queen));
            }
            else {
                break;
            }
        }

        // Legal moves diagonally right/up
        for(int i = 1; currentRow - i >= 0 && currentCol + i <= 9; i++) {
            if(board[currentRow-i][currentCol+i] == null) {
                legalMoves.add(new Queen(currentRow-i, currentCol+i, queen));
            }
            else {
                break;
            }
        }

        // Legal moves diagonally right/down
        for(int i = 1; currentRow + i <= 9 && currentCol + i <= 9; i++) {
            if(board[currentRow+i][currentCol+i] == null) {
                legalMoves.add(new Queen(currentRow+i, currentCol+i, queen));
            }
            else {
                break;
            }
        }

    return legalMoves;

    } // end getLegalMoves

    /**
     *
     * @param x: int storing the row of the current queen
     * @param y: int storing the column of the current queen
     * @return: an ArrayList<Arrow> containing the possible moves from the current queen
     */
    protected ArrayList<Arrow> getArrowMoves(int x, int y) {
        ArrayList<Arrow> legalArrowMoves = new ArrayList<>();
        int currentRow = x;
        int currentCol = y;

        /*
            Horizontal Movement Checks
         */

        // Legal moves left
        for(int i = 1; currentCol - i >= 0; i++) {
            if(board[currentRow][currentCol-i] == null) {
                legalArrowMoves.add(new Arrow(currentRow, currentCol-i));
            }
            else {
                break;
            }
        }

        // Legal moves right
        for(int i = 1; currentCol + i <= 9; i++) {
            if(board[currentRow][currentCol+i] == null) {
                legalArrowMoves.add(new Arrow(currentRow,currentCol+i));
            }
            else {
                break;
            }
        }

        /*
            Vertical Movement Checks
         */

        // Legal moves up
        for(int i = 1; currentRow - i >= 0; i++) {
            if(board[currentRow-i][currentCol] == null) {
                legalArrowMoves.add(new Arrow(currentRow-i,currentCol));
            }
            else {
                break;
            }
        }

        // Legal moves down
        for(int i = 1; currentRow + i <= 9; i++) {
            if(board[currentRow+i][currentCol] == null) {
                legalArrowMoves.add(new Arrow(currentRow+i,currentCol));
            }
            else {
                break;
            }
        }

        /*
            Diagonal Movement Checks
         */

        // Legal moves diagonal left/up
        for(int i = 1; currentRow - i >= 0 && currentCol - i >= 0; i++) {
            if(board[currentRow-i][currentCol-i] == null) {
                legalArrowMoves.add(new Arrow(currentRow-i,currentCol-i));
            }
            else {
                break;
            }
        }

        // Legal moves diagonal left/down
        for(int i = 1; currentRow + i <= 9 && currentCol - i >= 0; i++) {
            if(board[currentRow+i][currentCol-i] == null) {
                legalArrowMoves.add(new Arrow(currentRow+i,currentCol-i));
            }
            else {
                break;
            }
        }

        // Legal moves diagonal right/up
        for(int i = 1; currentRow - i >= 0 && currentCol + i <= 9; i++) {
            if(board[currentRow-i][currentCol+i] == null) {
                legalArrowMoves.add(new Arrow(currentRow-i,currentCol+i));
            }
            else {
                break;
            }
        }

        // Legal moves diagonal right/down
        for(int i = 1; currentRow + i <= 9 && currentCol + i <= 9; i++) {
            if(board[currentRow+i][currentCol+i] == null) {
                legalArrowMoves.add(new Arrow(currentRow+i,currentCol+i));
            }
            else {
                break;
            }
        }
        return legalArrowMoves;
    } // end of getArrowMoves

    /**
     * Check to see if the enemy has any queens which can move on the board
     */
    public void canEnemyMove() {
        for(Queen q: enemy) {
            int initialRow = q.getRowPosition();
            int initialCol = q.getColPosition();
            
            if(initialRow - 1 >= 0 && board[initialRow - 1][initialCol] == null){
                enemyMove = true;
                break;
            }

            if(initialRow + 1 <= 9 && board[initialRow + 1][initialCol] == null){
                enemyMove = true;
                break;
            }

            if(initialCol - 1 >= 0 && board[initialRow][initialCol - 1] == null){
                enemyMove = true;
                break;
            }
            if(initialCol + 1 <= 9 && board[initialRow][initialCol + 1] == null){
                enemyMove = true;
                break;
            }

            if((initialRow - 1 >= 0 && initialCol - 1 >= 0) && board[initialRow - 1][initialCol - 1] == null){
                enemyMove = true;
                break;
            }

            if((initialRow + 1 <= 9 && initialCol - 1 >= 0) && board[initialRow + 1][initialCol - 1] == null){
                enemyMove = true;
                break;
            }

            if((initialRow + 1 <= 9 && initialCol + 1 <= 9) && board[initialRow + 1][initialCol + 1] == null){
                enemyMove = true;
                break;
            }
            if((initialRow - 1 >= 0 && initialCol + 1 <= 9) && board[initialRow - 1][initialCol + 1] == null){
                enemyMove = true;
                break;
            }
        }
    } // end of canEnemyMove

    /**
     *
     * @return: ArrayList<int[][]> of every possible Arrow move
     */
    protected ArrayList<int[][]> getLegalArrowMoves() {
        return legalArrowMoves;
    }

    /**
     *
     * @return: ArrayList<int[][]> of every possible Queen move
     */
    protected ArrayList<int[][]> getLegalMoves() {
        return legalQueenMoves;
    }

    private void updateLegalQueenMoves() {
        legalQueenMoves.clear();
        for(Queen q: friend) {
            legalQueenMoves.addAll(getLegalMoves(queen));
        }
    } // end of updateLegalQueenMoves

    private void updateLegalArrowMoves() {
        if(legalArrowMoves != null) {
            legalArrowMoves.clear();
            for(Queen q: friend) {
                legalArrowMoves.addAll(getLegalMoves(queen));
            }

        }
    } // end of updateLegalArrowMoves

    /**
     *
     * @param x: the row position of the arrow
     * @param y: the column position of the arrow
     */
    protected void addArrow(int x, int y) {
        arrows.add(new Arrow(x, y));
        updateAfterMove();
    }

    /**
     *
     * @param newArrow: the new Arrow object to add
     */
    protected void addArrow(Arrow newArrow) {
        arrows.add(newArrow);
        updateAfterMove();
    }

    /**
     * Reset each board
     */
    private void clearBoard() {
        for(int i = 0; i <= 9; i++) {
            for(int j = 0; i <= 9; j++) {
                board[i][j] = null;
            }
        }
    } // end of clearBoard

    /**
     * Update the board after each move is made
     */
    protected void updateAfterMove() {
        clearBoard();

        // Reset the position of each friendly queen
        for(Queen q: friend) {
            board[q.getRowPosition()][q.getColumnPosition()] = q;
        }

        // Reset the position of each enemy queen
        for(Queen q: enemy) {
            board[q.getRowPosition()][q.getColPosition()] = q;
        }

        // Reset each arrow on the board
        for(Arrow arrow: arrows) {
            if(arrow != null) {
                board[arrow.getRowPosition()][a.getColPosition()] = arrow;
            }
        }

    } // end of updateAfterMove


    /**
     *
     * @return: boolean indicating if we have won, or lost
     */
    protected boolean goalTest() {
        if (enemyMove == false || legalQueenMoves.size() == 0) {
            if (enemyHasMove == false) {
                System.out.println("We win!");
            } else {
                System.out.println("We lose!");
            }
            return true;
        } else {
            return false;
        }
    }

}
