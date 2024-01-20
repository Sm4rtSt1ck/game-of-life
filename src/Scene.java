import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class Scene extends JFrame{
    public final int WIDTH = 1000;
    public final int HEIGHT;

    private final Field field;
    private final int cellSize;

    public Scene(Field field) {
        // setAlwaysOnTop(true);
        setTitle("Game Of Life");
        setIconImage(new ImageIcon("res/cell.png").getImage());
        setLocation(400, 0);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(171, 150, 189));

        this.field = field;
        cellSize = WIDTH / (field.getCols());
        HEIGHT = cellSize * (field.getRows());
        setSize(WIDTH, HEIGHT);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    field.update();
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(126, 87, 157));

        if (!field.getRunning()) {
            // g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            try {
                Image image = ImageIO.read(new File("res/background.png"));
                g.drawImage(image, 0, 0, this);
                Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/TsukimiRounded-Bold.ttf"));
                g.setFont(font.deriveFont(50f));
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
            g.drawString("GAME ENDED", WIDTH / 2 - 200, HEIGHT / 2);
            return;
        }

        for (int x = 0; x < WIDTH; x += cellSize) {
            g.fillRect(x-1, 0, 2, HEIGHT);
        }
        for (int y = 0; y < HEIGHT; y += cellSize) {
            g.fillRect(0, y-1, WIDTH, 2);
        }
        // g.setColor(new Color(106, 62, 152));
        for (int row = 0; row < field.getRows(); row++) {
            for (int col = 0; col < field.getCols(); col++) {
                if (field.getCell(row, col).getAlive()) {
                    g.setColor(new Color(106, 62, 152));
                    g.fillRoundRect(col * cellSize + cellSize/20, row * cellSize + cellSize/20, (int)(cellSize/1.11), (int)(cellSize/1.11), cellSize/3, cellSize/3);
                    g.setColor(new Color(126, 87, 157));
                    g.fillRoundRect(col * cellSize + cellSize / 4, row * cellSize + cellSize / 4, cellSize / 2, cellSize / 2, cellSize/3, cellSize/3);
                }
                // g.setColor(Color.white);
                // g.drawString(String.valueOf(field.getCell(row, col).getAliveNeighbours()), col*(cellSize+1), row*(cellSize+1));
            }
        }
    }
}