import java.util.ArrayList;

public class Field {
    private final Cell[][] matrix;
    private final int[] pastHashes = new int[100];
    private byte currentIndex = 0;
    private boolean running = true;
    public final int rows, cols;

    public Field(Cell[][] matrix) {
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

        checkAndStore();
    }
    private void checkAndStore() {
        int hashCode = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                hashCode += ((row * 100 / (col + 1) + row + col + (matrix[row][col].getAlive() ? 1 + row + col : row - col)) * row - 10 * col + row + col) * (matrix[row][col].getAlive() ? -row - col / 2 : 1);
            }
        }
        for (int pastHash : pastHashes) {
            if (hashCode == pastHash) {
                running = false;
                System.out.println("\nThe Game Of Life is ended!");
                return;
            }
        }
        pastHashes[currentIndex] = hashCode;
        currentIndex = (byte) ((currentIndex + 1) % pastHashes.length);
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
