package pong;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final Color BACKGROUND_COLOUR = Color.BLACK;
	private static final int TIMER_DELAY = 5;
	private Ball ball;
	private Paddle paddle1;
	private Paddle paddle2;
	private GameState gameState = GameState.INITIALISING;
	private static final int BALL_MOVEMENT_SPEED = 5;
	private static final int PADDLE_MOVEMENT_SPEED = 5;
	private final static int POINTS_TO_WIN = 3;
	int player1Score = 0;
	int player2Score = 0;
	Player gameWinner;
	private static final int SCORE_X_PADDING = 100;
	private static final int SCORE_Y_PADDING = 100;
	private static final int SCORE_FONT_SIZE = 50;
	private static final String SCORE_FONT_FAMILY = "Serif";
	private static final int WINNER_X_PADDING = 150;
	private static final int WINNER_Y_PADDING = 200;
	
	
	public PongPanel() {
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
		
	}
	
	private void update() {
		switch (gameState) {
		case INITIALISING: {
			createObjects();
			gameState = GameState.PLAYING;
			ball.setXVelocity(randomiseDirection() * BALL_MOVEMENT_SPEED);
			ball.setYVelocity(randomiseDirection() * BALL_MOVEMENT_SPEED);
			break;
			}
		case PLAYING: {
			moveObject(paddle1);
			moveObject(paddle2);
			moveObject(ball);
			checkWallBounce();
			checkPaddleBounce();
			checkWin();
			break;
			}
		case GAMEOVER: {
			break;
			}
		}
	}
	
	private void createObjects() {
		ball = new Ball (getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	private void moveObject(Sprite sprite) {
		sprite.setXPosition(sprite.getXPosition() + sprite.getXVelocity(), getWidth());
		sprite.setYPosition(sprite.getYPosition() + sprite.getYVelocity(), getHeight());
	}
	
	private void checkWallBounce() {
		if (ball.getXPosition() <= 0) { // Hit left side of screen
			addScore(Player.Two);
			resetBall();
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) { // Hit right side of screen
			//ball.setXVelocity(-ball.getXVelocity());
			addScore(Player.One);
			resetBall();
		}
		if (ball.getYPosition() <= 0) { // Hit bottom of screen
			ball.setYVelocity(-ball.getYVelocity());
		}
		else if (ball.getYPosition() >= getHeight() - ball.getHeight()) { // Hit top of screen
			ball.setYVelocity(-ball.getYVelocity());
		}
	}
	
	 private void checkPaddleBounce() {
	      if(ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
	          ball.setXVelocity(BALL_MOVEMENT_SPEED);
	      }
	      if(ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
	          ball.setXVelocity(-BALL_MOVEMENT_SPEED);
	      }
	 }
	
	private void resetBall() {
		ball.resetToInitialPosition();
		ball.setXVelocity(randomiseDirection() * BALL_MOVEMENT_SPEED);
		ball.setYVelocity(randomiseDirection() * BALL_MOVEMENT_SPEED);
	}
	
	private int randomiseDirection () {
		Random rand = new Random();
		int direction;
		if (rand.nextInt(2) == 1) {
			direction = 1;
		} else {
			direction = -1;
		}
		return direction;
		
	}
	
	private void addScore(Player player) {
		if (player == Player.One) {
			player1Score += 1;
		}
		else if (player == Player.Two) {
			player2Score += 1;
		}
	}
	
	private void checkWin() {
		if (player1Score >= POINTS_TO_WIN) {
			gameState = GameState.GAMEOVER;
			gameWinner = Player.One;
		}
		else if (player2Score >= POINTS_TO_WIN) {
			gameState = GameState.GAMEOVER;
			gameWinner = Player.Two;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			paddle2.setYVelocity(-PADDLE_MOVEMENT_SPEED );
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(PADDLE_MOVEMENT_SPEED);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_W) {
			paddle1.setYVelocity(-PADDLE_MOVEMENT_SPEED);
		}
		else if (event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(PADDLE_MOVEMENT_SPEED);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(0);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(0);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		update();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if (gameState != GameState.INITIALISING) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
		}
		if (gameState == GameState.GAMEOVER) {
			paintWinner(g, gameWinner);
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0 , new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2,  0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	
	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void paintScores(Graphics g) {
		Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		g.drawString(leftScore, SCORE_X_PADDING, SCORE_Y_PADDING);
		g.drawString(rightScore, getWidth()- SCORE_X_PADDING, SCORE_Y_PADDING);
	}
	
	private void paintWinner(Graphics g, Player winner) {
		Font winnerFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String winnerMessage = "Win!";
		g.setFont(winnerFont);
		if (winner == Player.One) {
			g.drawString(winnerMessage, WINNER_X_PADDING, WINNER_Y_PADDING);
		}
		else if (winner == Player.Two) {
			g.drawString(winnerMessage, getWidth() - WINNER_X_PADDING, WINNER_Y_PADDING);
		}
		
	}
	

}
