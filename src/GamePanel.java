import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HIGHT / UNIT_SIZE);
    static final int InitialDELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyparts = 1;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;
    //https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();

    }

    public void StartGame() {
        NewApple();
        running = true;
        timer = new Timer(InitialDELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //The lines drawn appear as squares which have a certain value in which items can be placed or an entity can move
    public void draw(Graphics g) {
        // if running - execute all the code bellow, once the game is done the code is no more executing (a black screen shows)
        if (running) {
            for (int i = 0; i < SCREEN_HIGHT / UNIT_SIZE; i++) {
                // g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HIGHT);
                //g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                if (applesEaten >= 35) {
                    g.drawLine(SCREEN_WIDTH, i * UNIT_SIZE, UNIT_SIZE * i, 0);
                    g.drawLine(0, i * UNIT_SIZE, UNIT_SIZE * i, SCREEN_WIDTH);
                }
            }
            g.setColor(Color.GREEN);
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            if (applesEaten >= 10) {
                g.setColor(new Color(29, 103, 226));
                g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            }
            if (applesEaten >= 20) {
                g.setColor(new Color(253, 135, 2));
                g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            }
            if (applesEaten >= 35) {
                g.setColor(new Color(255, 148, 235));
                g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            }

            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    if (applesEaten >= 10) {
                        g.setColor(new Color(29, 103, 226));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    if (applesEaten >= 20) {
                        g.setColor(new Color(253, 135, 2));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    if (applesEaten >= 35) {
                        g.setColor(new Color(255, 148, 235));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }

                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    if (applesEaten >= 10) {
                        g.setColor(Color.blue);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    if (applesEaten >= 20) {
                        g.setColor(Color.red);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    if (applesEaten >= 35) {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }
            g.setColor(Color.green);
            g.setFont(new Font("", Font.BOLD, 40));
            //Lining up text in the middle of the screen
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
            if (applesEaten >= 10) {
                g.setColor(Color.blue);
                g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
            }
            if (applesEaten >= 20) {
                g.setColor(Color.red);
                g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
            }
            if (applesEaten >= 35) {
                g.setColor(Color.magenta);
                g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
            }
        } else {
            gameOver(g);
            //g - Graphics
        }
    }

    public void NewApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void Move() {
        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple() {
        //x[0] and y[0] - head of the snake
        if ((x[0] == appleX && y[0] == appleY)) {
            //Once the head "meets" an apple the count of the snake's body parts and the apples eaten increases
            bodyparts++;
            applesEaten++;
            NewApple();
            //sets the pace of the game(with every apple eaten the timer get access to the delay and lower the value with 1)
            int currentDelay = timer.getDelay();
            timer.setDelay(currentDelay-=1);
        }
    }

    public void checkCollisions() {
        //checks if head collides with body
        for (int i = bodyparts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if (y[0] > SCREEN_HIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Game Over text
        g.setColor(Color.green);
        g.setFont(new Font("", Font.BOLD, 75));
        //Lining up text in the middle of the screen
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HIGHT / 2);
        //Displays score
        g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        if (applesEaten >= 10) {
            g.setColor(Color.blue);
            g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HIGHT / 2);
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        }
        if (applesEaten >= 20) {
            g.setColor(Color.red);
            g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HIGHT / 2);
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        }
        if (applesEaten >= 35) {
            g.setColor(Color.magenta);
            g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HIGHT / 2);
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            Move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                //Pause the game
                case KeyEvent.VK_SPACE:
                    timer.stop();
                    break;
                //Resume the game
                case KeyEvent.VK_ENTER:
                    timer.start();
                    break;
            }

        }

    }
}
