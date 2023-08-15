import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private final ArrayList<Point> snake = new ArrayList<>();
    private Point food;
    private boolean isGameOver = false;
    private boolean isMoving = false;
    private char direction = 'R';
    private int score = 0;
    private final Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                changeDirection(e.getKeyCode());
            }
        });

        spawnFood();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        
        timer = new Timer(100, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (!isGameOver) {
            for (Point point : snake) {
                g.setColor(Color.GREEN);
                g.fillRect(point.x, point.y, UNIT_SIZE, UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillRect(food.x, food.y, UNIT_SIZE, UNIT_SIZE);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Score: " + score, 10, 40);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 75));
            g.drawString("Game Over", 75, HEIGHT / 2);
            
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Score: " + score, 225, HEIGHT / 2 + 50);
        }
    }

    private void move() {
        Point newHead = snake.get(0);
        Point newLocation;
        switch (direction) {
            case 'U':
                newLocation = new Point(newHead.x, newHead.y - UNIT_SIZE);
                break;
            case 'D':
                newLocation = new Point(newHead.x, newHead.y + UNIT_SIZE);
                break;
            case 'L':
                newLocation = new Point(newHead.x - UNIT_SIZE, newHead.y);
                break;
            case 'R':
                newLocation = new Point(newHead.x + UNIT_SIZE, newHead.y);
                break;
            default:
                newLocation = new Point(newHead);
                break;
        }

        snake.add(0, newLocation);
        if (newLocation.equals(food)) {
            score++;
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void spawnFood() {
        Random rand = new Random();
        int x = rand.nextInt((WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        int y = rand.nextInt((HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        food = new Point(x, y);
    }

    private void changeDirection(int keyCode) {
        if (!isMoving) {
            isMoving = true;
            if ((keyCode == KeyEvent.VK_LEFT) && (direction != 'R')) {
                direction = 'L';
            }
            if ((keyCode == KeyEvent.VK_RIGHT) && (direction != 'L')) {
                direction = 'R';
            }
            if ((keyCode == KeyEvent.VK_UP) && (direction != 'D')) {
                direction = 'U';
            }
            if ((keyCode == KeyEvent.VK_DOWN) && (direction != 'U')) {
                direction = 'D';
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();
            checkCollision();
            isMoving = false;
        }
        repaint();
    }

    private void checkCollision() {
        Point head = snake.get(0);
        // Check if the head collides with the walls or itself
        if (head.x < 0 || head.y < 0 || head.x >= WIDTH || head.y >= HEIGHT) {
            isGameOver = true;
            timer.stop();
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                isGameOver = true;
                timer.stop();
                break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame snakeGame = new SnakeGame();
        frame.add(snakeGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
