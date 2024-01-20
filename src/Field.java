import java.util.ArrayList;

public class Field {
    private final Cell[][] matrix;
    private final ArrayList<boolean[][]> pastMatrices = new ArrayList<>();
    private boolean running = true;

    public Field(Cell[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                Cell[] neighbours = new Cell[8];
                byte cellIndex = 0;

                // Loop throw all neighbours
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i != row || j != col) {

                            // Bounds handling
                            int r, c;

                            if (i == -1) r = matrix.length-1;
                            else if (i == matrix.length) r = 0;
                            else r = i;

                            if (j == -1) c = matrix[0].length-1;
                            else if (j == matrix[0].length) c = 0;
                            else c = j;

                            neighbours[cellIndex] = matrix[r][c];
                            cellIndex++;
                        }
                    }
                }
                matrix[row][col].setNeighbours(neighbours);
            }
        }
        this.matrix = matrix;
    }

    public void update() {
        for (Cell[] cells : matrix)
            for (Cell cell : cells)
                cell.update();
        for (Cell[] cells : matrix)
            for (Cell cell : cells)
                cell.updateNeighboursCount();

        checkSame();
        store();
    }
    private void checkSame() {
        // Check similarity with all previous matrices
        for (boolean[][] pastCells : pastMatrices) {
            boolean same = true;
            for (int row = 0; row < pastCells.length; row++) {
                for (int col = 0; col < pastCells[0].length; col++) {
                    if (pastCells[row][col] != matrix[row][col].getAlive()) {
                        same = false;
                        break;
                    }
                }
                if (!same) break;
            }
            if (same) {
                running = false;
                System.out.println("\nThe Game Of Life is ended!");
                break;
            }
        }
    }
    private void store() {
        // Store matrix
        boolean[][] lastCells = new boolean[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                lastCells[row][col] = matrix[row][col].getAlive();
            }
        }
        pastMatrices.add(lastCells);
    }

    public Cell getCell(int row, int col) {
        return matrix[row][col];
    }
    public int getRows() {
        return matrix.length;
    }
    public int getCols() {
        return matrix[0].length;
    }
    public boolean getRunning() {
        return running;
    }

    public String toString() {
        StringBuilder strMatrix = new StringBuilder();
        for (Cell[] cells : matrix) {
            for (Cell cell : cells) {
                strMatrix.append(cell.getAlive() ? 1 : 0);
            }
            strMatrix.append("\n");
        }
        return strMatrix.toString();
    }
}
