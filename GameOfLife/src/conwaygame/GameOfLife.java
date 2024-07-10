package conwaygame;
import java.util.ArrayList;

import javax.xml.namespace.QName;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        StdIn.setFile(file);

        int r = StdIn.readInt(); 
        int c = StdIn.readInt(); 

        grid = new boolean[r][c]; 

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
               grid[i][j] = StdIn.readBoolean();
            }
        }
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {
        
        return getGrid()[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        boolean Alive = false;

        for (int i = 0; i < getGrid().length; i++) {
            for (int j = 0; j < getGrid()[0].length; j++) {
                if (getGrid()[i][j] == true) {
                    Alive = true;
                }
            }
        }

        
        return Alive; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        int AliveNeighborsNum = 0;

        //target cell left neighbor 
        if (col == 0) { 
            if (getGrid()[row][getGrid()[0].length - 1] == true) {
                AliveNeighborsNum++;
            }
        }
        else {
            if (getGrid()[row][col -1] == true) {
                AliveNeighborsNum++;
            }
        }

        //target cell right neighbor
        if (col == getGrid()[0].length - 1) { 
            if (getGrid()[row][0] == true ) {
                AliveNeighborsNum++;
            }
        }
        else {
            if (getGrid()[row][col + 1] == true) {
                AliveNeighborsNum++;
            }
        }
       
        //target cell top neighbor 
        if (row == 0) {
            if (getGrid()[getGrid().length-1][col] == true) {
                AliveNeighborsNum++;
            }
        }
        else {
            if (getGrid()[row - 1 ][col] == true) {
                AliveNeighborsNum++;
            }
        }

        //target cell bottom neighbor 
        if (row == getGrid().length - 1) {
            if (getGrid()[0][col] == true) {
                AliveNeighborsNum++;
            }
        }
        else {
            if (getGrid()[row + 1][col] == true) {
                AliveNeighborsNum++;
            }
        }

        //target cell top right neighbor 
        if (row == 0 && col == getGrid()[0].length - 1 ) {
            if (getGrid()[getGrid().length-1][0] == true){
                AliveNeighborsNum++;
            }
        }
        else { 
            if (row == 0) {
                if (getGrid()[getGrid().length - 1 ][col + 1] == true) {
                    AliveNeighborsNum++;
                }
            }
            else { 
                if (col == getGrid()[0].length - 1) {
                    if (getGrid()[row - 1][0] == true) {
                        AliveNeighborsNum++;
                    }
                }
                else{
                    if (getGrid()[row - 1][col + 1] == true) { 
                        AliveNeighborsNum++;
                    }
                }
            }
        }

        //target cell top left neighbor

        if (row == 0 && col == 0) {
            if (getGrid()[getGrid().length-1][getGrid()[0].length - 1] == true){
                AliveNeighborsNum++;
            }
        }
        else { 
            if (row == 0) {
                if (getGrid()[getGrid().length - 1][col - 1] == true) {
                    AliveNeighborsNum++;
                }
            }
            else { 
                if (col == 0) {
                    if (getGrid()[row - 1][getGrid()[0].length - 1] == true) {
                        AliveNeighborsNum++;
                    }
                }
                else{
                    if (getGrid()[row - 1][col - 1] == true) { 
                        AliveNeighborsNum++;
                    }
                }
            }
        }

        //target cell bottom left neighbor 

        if (row == getGrid().length - 1 && col == 0 ) {
            if (getGrid()[0][getGrid()[0].length -1] == true){
                AliveNeighborsNum++;
            }
        }
        else { 
            if (row == getGrid().length -1 ) {
                if (getGrid()[0][col - 1] == true) {
                    AliveNeighborsNum++;
                }
            }
            else { 
                if (col == 0) {
                    if (getGrid()[row + 1][getGrid()[0].length -1] == true) {
                        AliveNeighborsNum++;
                    }
                }
                else{
                    if (getGrid()[row + 1][col - 1] == true) { 
                        AliveNeighborsNum++;
                    }
                }
            }
        }
        // target cell bottom right
        if (row == getGrid().length - 1 && col == getGrid()[0].length - 1 ) {
            if (getGrid()[0][0] == true){
                AliveNeighborsNum++;
            }
        }
        else { 
            if (row == getGrid().length -1 ) {
                if (getGrid()[0][col + 1] == true) {
                    AliveNeighborsNum++;
                }
            }
            else { 
                if (col == getGrid()[0].length - 1) {
                    if (getGrid()[row + 1][0] == true) {
                        AliveNeighborsNum++;
                    }
                }
                else{
                    if (getGrid()[row + 1][col + 1] == true) { 
                        AliveNeighborsNum++;
                    }
                }
            }
        }
        return AliveNeighborsNum;
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        boolean[][] NewGrid = new boolean[getGrid().length][getGrid()[0].length];

        for (int i = 0; i < getGrid().length; i++) {
            for (int j = 0; j < getGrid()[0].length; j++) {
                if (getGrid()[i][j] == true && (numOfAliveNeighbors(i, j) == 1 || numOfAliveNeighbors(i, j) == 0)) { 
                    NewGrid[i][j] = false; 
                }
                if (getGrid()[i][j] == false && numOfAliveNeighbors(i, j) == 3) {
                    NewGrid[i][j] = true;
                }
                if (getGrid()[i][j] == true && numOfAliveNeighbors(i, j) == 2 || numOfAliveNeighbors(i, j) == 3) {
                    NewGrid[i][j] = true; 
                }
                if (getGrid()[i][j] == true && numOfAliveNeighbors(i, j) >= 4) {
                    NewGrid[i][j] = false; 
                }
            }
        }

        return NewGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        grid = computeNewGrid();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        for (int i = 0; i < n; i++) {
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
            boolean[][] grid = getGrid();
            int rows = grid.length;
            int cols = grid[0].length;
            WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(rows, cols);
        
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (grid[row][col] == true) { 
    
                        if (grid[(row - 1 + rows) % rows][col]) {
                            wqu.union(row, col, (row - 1 + rows) % rows, col);
                        }

                        if (grid[row][(col - 1 + cols) % cols]) {
                            wqu.union(row, col, row, (col - 1 + cols) % cols);
                        }

                        if (grid[(row - 1 + rows) % rows][(col - 1 + cols) % cols]) {
                            wqu.union(row, col, (row - 1 + rows) % rows, (col - 1 + cols) % cols);
                        }

                        if (grid[(row - 1 + rows) % rows][(col + 1) % cols]) {
                            wqu.union(row, col, (row - 1 + rows) % rows, (col + 1) % cols);
                        }
                    }
                }
            }
        
            int[] roots = new int[rows * cols]; 
            int count = 0;
        
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (grid[row][col]) { // If cell is alive
                        int root = wqu.find(row, col);
                        boolean found = false;
                        for (int i = 0; i < count; i++) {
                            if (roots[i] == root) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            roots[count++] = root;
                        }
                    }
                }
            }
        
            return count;
        }
        
}

