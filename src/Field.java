import java.util.ArrayList;

public class Field {
    private final Cell[][] matrix;
    private final ArrayList<boolean[][]> pastMatrices;
    private boolean running = true;
    public final int rows, cols;

    public Field(Cell[][] matrix) {
        pastMatrices = new ArrayList<>();
        rows = matrix.length;
        cols = matrix[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell[] neighbours = new Cell[8];
                byte cellIndex = 0;

                // Loop throw all neighbours
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i != row || j != col) {
                            neighbours[cellIndex] = matrix[(rows + i) % rows][(cols + j) % cols];
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
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
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
        boolean[][] lastCells = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                lastCells[row][col] = matrix[row][col].getAlive();
            }
        }
        pastMatrices.add(lastCells);
    }

    public Cell getCell(int row, int col) {
        return matrix[row][col];
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
