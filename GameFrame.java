import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame implements ActionListener {

    private Snake gamePanel = new Snake();
    JMenuBar menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenuItem newGame = new JMenuItem("New Game - N");
    ImageIcon gameIcon = new ImageIcon("snake_game_icon.png");
    KeyListener myKeyListener = new KeyListener();

    public GameFrame() {
        newGame.addActionListener(this);
        gameMenu.add(newGame);
        menuBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuBar.add(gameMenu);

        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(gameIcon.getImage());
        this.addKeyListener(myKeyListener);
        this.setJMenuBar(menuBar);
        this.add(gamePanel);
        this.pack();
        this.setVisible(true);
    }

    class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case 87: gamePanel.changeDirection('U'); break; //wasd
                case 65: gamePanel.changeDirection('L'); break;
                case 83: gamePanel.changeDirection('D'); break;
                case 68: gamePanel.changeDirection('R'); break;

                case 38: gamePanel.changeDirection('U'); break; //arrow keys
                case 37: gamePanel.changeDirection('L'); break;
                case 40: gamePanel.changeDirection('D'); break;
                case 39: gamePanel.changeDirection('R'); break;

                case 78: gamePanel.restartGame(); break; //Press N for new game
            }
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGame) { gamePanel.restartGame(); }
    }

}
