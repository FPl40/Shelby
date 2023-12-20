package shelby;
//Захаров Сергей Борисович 
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Shelby extends JFrame {
//Захаров Сергей Борисович
    private static Shelby shelbyApp;
    private static Image logo;
    private int logoX = 100;  
    private int logoY = 100;  
    private int logoSpeed = 5;  
    private Timer timer;

    private List<Snowflake> snowflakes = new ArrayList<>();

    public Shelby() {
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveLogo();
                moveSnowflakes();
                repaint();
            }
        });
        timer.start();
    }

    private void moveSnowflakes() {
        for (Snowflake snowflake : snowflakes) {
            snowflake.fall();
            if (snowflake.getY() > getHeight()) {
                snowflake.reset();  
            }
        }
    }
//Захаров Сергей Борисович
    public static void main(String[] args) throws IOException {
        logo = ImageIO.read(Shelby.class.getResourceAsStream("logo.png"));
        shelbyApp = new Shelby();
        shelbyApp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        shelbyApp.setLocation(200, 50);
        shelbyApp.setSize(900, 600);
        shelbyApp.setResizable(false);
        ShelbyAppField shelbyAppField = new ShelbyAppField();
        shelbyApp.add(shelbyAppField);
        shelbyApp.generateSnowflakes(50);  
        shelbyApp.setVisible(true);
    }

    private void generateSnowflakes(int count) {
        for (int i = 0; i < count; i++) {
            snowflakes.add(new Snowflake());
        }
    }

    private void moveLogo() {
        logoX += logoSpeed;
        if (logoX > getWidth()) {
            logoX = -logo.getWidth(null);  
        }
    }

    public static void onRepaint(Graphics g) {
        g.drawImage(logo, shelbyApp.logoX, shelbyApp.logoY, null);
        for (Snowflake snowflake : shelbyApp.snowflakes) {
            snowflake.draw(g);
        }
    }

    public static class ShelbyAppField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            
            g.setColor(new Color(0, 0, 50));
            g.fillRect(0, 0, getWidth(), getHeight());
            
            onRepaint(g);
        }
    }

    private static class Snowflake {
        private int x;
        private int y;
        private int speed;

        public Snowflake() {
            reset();
        }

        public int getY() {
            return y;
        }

        public void fall() {
            y += speed;
        }

        public void reset() {
            x = (int) (Math.random() * shelbyApp.getWidth());
            y = (int) (Math.random() * shelbyApp.getHeight());
            speed = 1 + (int) (Math.random() * 3);  
        }

        public void draw(Graphics g) {
            // Set the color to white
            g.setColor(Color.WHITE);
            g.fillOval(x, y, 5, 5);  
        }
    }
}