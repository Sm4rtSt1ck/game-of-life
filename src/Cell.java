public class Cell {
    private boolean alive;

    private Cell[] neighbours;
    private byte aliveNeighbours;
    private byte newAliveNeighbours;

    public Cell(boolean alive) {
        this.alive = alive;
    }
    public void setNeighbours(Cell[] neighbours) {
        this.neighbours = neighbours;
        aliveNeighbours = 0;
        for (Cell cell : neighbours) {
            if (cell.getAlive()) aliveNeighbours++;
        }
        newAliveNeighbours = aliveNeighbours;
    }

    public void die() {
        alive = false;
        for (Cell cell : neighbours) {
            cell.newAliveNeighbours--;
        }
    }
    public void resurrect() {
        alive = true;
        for (Cell cell : neighbours) {
            cell.newAliveNeighbours++;
        }
    }
    public boolean getAlive() {
        return alive;
    }

    public void update() {
        if (alive) {
            if (aliveNeighbours != 2 && aliveNeighbours != 3) {
                die();
            }
        } else if (aliveNeighbours == 3) {
            resurrect();
        }
    }
    public void updateNeighboursCount() {
        aliveNeighbours = newAliveNeighbours;
    };
}
