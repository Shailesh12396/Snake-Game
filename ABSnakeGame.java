import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
public class ABSnakeGame extends JPanel implements ActionListener, KeyListener {
 private static final long serialVersionUID = 1L;
 private final int BOARD_WIDTH = 800;
 private final int BOARD_HEIGHT = 600;
 private final int DOT_SIZE = 10;
 private final int ALL_DOTS = 900;
 private final int RAND_POS = 29;
 private final int DELAY = 140;
 private int[] x = new int[ALL_DOTS];
 private int[] y = new int[ALL_DOTS];
 private int dots;
 private int foodX;
 private int foodY;
 private boolean leftDirection = false;
 private boolean rightDirection = true;
 private boolean upDirection = false;
 private boolean downDirection = false;
 private boolean inGame = true;
 private int score = 0;
 private Timer timer;
 private Random random;
 public ABSnakeGame() {
 setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
 setFocusable(true); // Add this line
 addKeyListener(this);
 setBackground(Color.BLACK);
 initGame();
 requestFocusInWindow();
 }
 private void initGame() {
 dots = 3;
 for (int i = 0; i < dots; i++) {
 x[i] = 50 - i * DOT_SIZE;
 y[i] = 50;
 }
 locateFood();
 timer = new Timer(DELAY, this);
 timer.start();
 }
 private void locateFood() {
 random = new Random();
 int r = random.nextInt(RAND_POS);
 foodX = r * DOT_SIZE;
 r = random.nextInt(RAND_POS);
 foodY = r * DOT_SIZE;
 }
 private void checkFood() {
 if (x[0] == foodX && y[0] == foodY) {
 dots++;
 score++;
 locateFood();
 }
 }
 private void gameOver() {
 // Display a dialog box with the score and ask the user if they 
//want to play again
 String message = "Game over!\nYour score: " + score + "\nDo you want to play again?";
 int option = JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION);
 if (option == JOptionPane.YES_OPTION) {
 // Reset the game
 inGame = true;
 leftDirection = false;
 rightDirection = true;
 upDirection = false;
 downDirection = false;
 score = 0;
 initGame();
 } else {
 // Exit the game
 System.exit(0);
 }
 }
 private void checkCollision() {
 for (int i = dots; i > 0; i--) {
 if (x[0] < 0 || x[0] >= BOARD_WIDTH || y[0] < 0 || y[0] >= 
BOARD_HEIGHT) {
 inGame = false;
 }
 // Check if the snake collides with its own body
 if (i > 4 && x[0] == x[i - 1] && y[0] == y[i - 1]) {
 inGame = false;
 }
 }
 if (!inGame) {
 timer.stop();
 gameOver();
 }
 }
 private void move() {
 if (!inGame) {
 return;
 }
 for (int i = dots; i > 0; i--) {
 x[i] = x[i - 1];
 y[i] = y[i - 1];
 }
 if (leftDirection) {
 x[0] -= DOT_SIZE;
 }
 if (rightDirection) {
 x[0] += DOT_SIZE;
 }
 if (upDirection) {
 y[0] -= DOT_SIZE;
 }
 if (downDirection) {
 y[0] += DOT_SIZE;
 }
 }
 @Override
 public void actionPerformed(ActionEvent e) {
 if (inGame) {
 checkFood();
 checkCollision();
 move();
 }
 repaint();
 }
 @Override
 public void paintComponent(Graphics g) {
 super.paintComponent(g);
 drawSnake(g);
 drawFood(g);
 drawScore(g);
 }
 private void drawSnake(Graphics g) {
 for (int i = 0; i < dots; i++) {
 g.setColor(Color.GREEN);
 g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
 }
 }
 private void drawFood(Graphics g) {
 g.setColor(Color.RED);
 g.fillRect(foodX, foodY, DOT_SIZE, DOT_SIZE);
 }
 private void drawScore(Graphics g) {
 g.setColor(Color.WHITE);
 g.drawString("Score: " + score, 10, BOARD_HEIGHT - 10);
 }
 @Override
 public void keyPressed(KeyEvent e) {
 int key = e.getKeyCode();
 if (key == KeyEvent.VK_LEFT && !rightDirection) {
 leftDirection = true;
 upDirection = false;
 downDirection = false;
 }
 if (key == KeyEvent.VK_RIGHT && !leftDirection) {
 rightDirection = true;
 upDirection = false;
 downDirection = false;
 }
 if (key == KeyEvent.VK_UP && !downDirection) {
 upDirection = true;
 leftDirection = false;
 rightDirection = false;
 }
 if (key == KeyEvent.VK_DOWN && !upDirection) {
 downDirection = true;
 leftDirection = false;
 rightDirection = false;
 }
 move();
 }
 @Override
 public void keyReleased(KeyEvent e) {
 }
 @Override
 public void keyTyped(KeyEvent e) {
 }
 public static void main(String[] args) {
 JFrame frame = new JFrame("Snake Game");
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setResizable(false);
 frame.add(new ABSnakeGame());
 frame.pack();
 frame.setLocationRelativeTo(null);
 frame.setVisible(true);
 }
}

