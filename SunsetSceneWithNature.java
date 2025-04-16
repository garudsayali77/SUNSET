import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SunsetSceneWithNature extends JPanel implements ActionListener {

    private int sunY = 100;
    private int birdX = 800;
    private final Timer timer;

    public SunsetSceneWithNature() {
        timer = new Timer(50, this); // 20 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // ----------- Sky Gradient -----------
        float ratio = (float) sunY / height;
        Color skyColor = blendColors(new Color(255, 204, 128), new Color(25, 25, 112), ratio);
        g2.setPaint(new GradientPaint(0, 0, skyColor, 0, height, Color.BLACK));
        g2.fillRect(0, 0, width, height);

        // ----------- Sun -----------
        g2.setColor(new Color(255, 100, 0));
        g2.fillOval(width / 2 - 50, sunY, 100, 100);

        // ----------- Ground -----------
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, height - 100, width, 100);

        // ----------- Trees -----------
        drawPalmTree(g2, width / 4, height - 100);
        drawPalmTree(g2, width / 2 + 100, height - 100);
        drawPalmTree(g2, width / 2 - 180, height - 100);

        // ----------- Birds -----------
        drawBird(g2, birdX, 150);
        drawBird(g2, birdX + 60, 180);
        drawBird(g2, birdX + 120, 140);
    }

    private void drawPalmTree(Graphics2D g2, int x, int y) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(x, y, x, y - 60); // trunk

        for (int angle = -60; angle <= 60; angle += 30) {
            int dx = (int) (40 * Math.cos(Math.toRadians(angle)));
            int dy = (int) (40 * Math.sin(Math.toRadians(angle)));
            g2.drawLine(x, y - 60, x + dx, y - 60 + dy);
        }
    }

    private void drawBird(Graphics2D g2, int x, int y) {
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawArc(x, y, 20, 10, 0, 180); // left wing
        g2.drawArc(x + 20, y, 20, 10, 0, 180); // right wing
    }

    private Color blendColors(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (sunY < getHeight() - 100) {
            sunY += 1;
        }
        birdX -= 5;
        if (birdX < -100) {
            birdX = getWidth(); // loop birds back
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sunset with Trees and Birds");
        SunsetSceneWithNature panel = new SunsetSceneWithNature();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

