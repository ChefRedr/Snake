import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener {

    public class SnakeBody {
        int x = 0;
        int y = 0;
        public void setX(int newX) {x = newX;}
        public void setY(int newY) {y = newY;}
    }

    public class Apple {
        int x = 0;
        int y = 0;
        public void setX(int newX) {x = newX;}
        public void setY(int newY) {y = newY;}
    }

    private final int PANEL_WIDTH = 625;
    private final int PANEL_HEIGHT = 625;
    private final int ITEM_SIZE = 25;
    private final int NUMBER_OF_SPACES = (PANEL_WIDTH/ITEM_SIZE) * (PANEL_HEIGHT/ITEM_SIZE);
    private SnakeBody snake[] = new SnakeBody[NUMBER_OF_SPACES];
    private final int STARTING_LENGTH = 6;
    private int snakeLength = STARTING_LENGTH;
    private Color snakeColor = Color.GREEN;
    private Color snakeHeadColor = new Color(0, 128, 0); //dark green
    private int points = 0;
    private JLabel score = new JLabel("Score: 0");
    private char direction = 'R';
    private int gameSpeed = 75;
    private Timer timer = new Timer(gameSpeed, this);
    private Apple apple = new Apple();
    private Random appleSpawner = new Random();

    public Snake() {
        score.setBounds(PANEL_WIDTH-ITEM_SIZE*5, 0, 200, 50);
        score.setFont(new Font("Ariel", Font.BOLD, 20));
        score.setForeground(Color.WHITE);
        
        this.setLayout(null);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.add(score);
        for(int i=0; i<NUMBER_OF_SPACES; ++i) {snake[i] = new SnakeBody();}
        for(int i=0; i<snakeLength; i++) {
            snake[i].setX(-i * ITEM_SIZE);
            snake[i].setY(0);
        }
        spawnApple();
        timer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //paints background
        super.paint(g);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, ITEM_SIZE, ITEM_SIZE); //To stop from showing snake breifly spawning in the top right corner

        //paints snake
        g2.setColor(Color.GREEN);
        for(int i=snakeLength-1; i>=0; i--) {
            if(i==0) {g2.setColor(snakeHeadColor);}
            else {g2.setColor(snakeColor);}
            g2.fillRect(snake[i].x, snake[i].y, ITEM_SIZE, ITEM_SIZE);
        }

        //paints apple
        g2.setColor(Color.RED);
        g2.fillRect(apple.x, apple.y, ITEM_SIZE, ITEM_SIZE);

    }

    public void move() {
        int tempX = snake[0].x;
        int tempY = snake[0].y;
        int tempX2 = 0;
        int tempY2 = 0;
        switch(direction) {
            case 'U':
                snake[0].setY(snake[0].y -= ITEM_SIZE);
                break;
            case 'L':
                snake[0].setX(snake[0].x -= ITEM_SIZE);
                break;
            case 'D':
                snake[0].setY(snake[0].y += ITEM_SIZE);
                break;
            case 'R':
                snake[0].setX(snake[0].x += ITEM_SIZE);
                break;
        }
        for(int i=1; i<snakeLength; ++i) {
            tempX2 = snake[i].x;
            tempY2 = snake[i].y;
            snake[i].setX(tempX);
            snake[i].setY(tempY);
            tempX = tempX2;
            tempY = tempY2;
            tempX2 = snake[i].x;
            tempY2 = snake[i].y;
        }
        appleEaten();
        snake[snakeLength].setX(tempX2);
        snake[snakeLength].setY(tempY2);
    }

    public void changeDirection(char c) {
        switch(c) {
            case 'U': if(direction != 'D') {direction = 'U';} break;
            case 'L': if(direction != 'R') {direction = 'L';} break;
            case 'D': if(direction != 'U') {direction = 'D';} break;
            case 'R': if(direction != 'L') {direction = 'R';} break;
        }
    }

    public void checkLost() {
        for(int i=1; i<snakeLength; ++i) {
            if(snake[0].x == snake[i].x && snake[0].y == snake[i].y) {gameOver();}
        }
        if(snake[0].x < 0 || snake[0].x > PANEL_WIDTH - ITEM_SIZE) {gameOver();}
        if(snake[0].y < 0 || snake[0].y > PANEL_HEIGHT - ITEM_SIZE) {gameOver();}
    }

    public void spawnApple() {
        apple.setX(appleSpawner.nextInt(PANEL_WIDTH/ITEM_SIZE) * ITEM_SIZE);
        apple.setY(appleSpawner.nextInt(PANEL_HEIGHT/ITEM_SIZE) * ITEM_SIZE);
        if(snakeLength == NUMBER_OF_SPACES) {
            gameOver();
        }
        else {
            for(int i=0; i<snakeLength; ++i) {
                if(apple.x == snake[i].x && apple.y == snake[i].y) {spawnApple();}
            }
        }
    }

    public void appleEaten() {
        if(snake[0].x == apple.x && snake[0].y == apple.y) {
            snakeLength++;
            points++;
            score.setText("Score: " + points);
            spawnApple();
        }
    }

    public void gameOver() {
        timer.stop();
        if(points == NUMBER_OF_SPACES-STARTING_LENGTH) {JOptionPane.showMessageDialog(null, "Outstanding!\nYou scored the maximum of "+points+" points");}
        else if(points == 1) {JOptionPane.showMessageDialog(null, "Game Over!\nYou scored 1 point");}
        else{JOptionPane.showMessageDialog(null, "Game Over!\nYou scored "+points+" points");}
    }

    public void restartGame() {
        snakeLength = STARTING_LENGTH;
        points = 0;
        score.setText("Score: 0");
        direction = 'D';
        for(int i=0; i<NUMBER_OF_SPACES; i++) {snake[i].setX(0); snake[i].setY(0);}
        for(int i=0; i<snakeLength; i++) {
            snake[i].setX(-i * ITEM_SIZE);
            snake[i].setY(0);
        }
        spawnApple();
        timer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        checkLost();
    }
    
}
