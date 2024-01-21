import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter a path of a file of the cells' matrix >> ");
        String fileName = in.nextLine();
        System.out.print("Set a start position of the colony (row & col) >> ");
        int row = in.nextInt(), col = in.nextInt();
        System.out.print("Set a size of the field (rows & cols) >> ");
        int rows = in.nextInt(), cols = in.nextInt();
        Cell[][] matrix = createMatrix(fileName, row, col, rows, cols);
        Field field = new Field(matrix);

        Scene scene = new Scene(field);

        System.out.println("\nPress SPACE in the active game window to update the field");

        // while (in.hasNext()) {
        //     in.next();
        //     field.update();
        //     scene.repaint();
        //     // TimeUnit.MILLISECONDS.sleep(200);
        //     // System.out.println(field);
        // }

    }
    public static Cell[][] createMatrix(String fileName, int startRow, int startCol, int rows, int cols) throws IOException {
        ArrayList<String> matrixList = new ArrayList<>();
        FileReader fr = new FileReader(fileName);
        Scanner in = new Scanner(fr);
        while (in.hasNextLine()){
            matrixList.add(in.nextLine());
        }
        fr.close();
        Cell[][] matrix = new Cell[rows][cols];
        for (int row = startRow; row < startRow + matrixList.size(); row++) {
            for (int col = startCol; col < startCol + matrixList.getFirst().length(); col++) {
                matrix[row][col] = new Cell(matrixList.get(row-startRow).charAt(col-startCol) == '1');
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == null) {
                    matrix[row][col] = new Cell(false);
                }
            }
        }
        return matrix;
    }
}