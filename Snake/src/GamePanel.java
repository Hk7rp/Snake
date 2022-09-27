import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.handler.FruitHandler;
import com.handler.PlayerHandler;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 500;
	static final int SCREEN_HEIGHT = 500;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	
	int lastScore = 0;
	int lastPlayerGetScore = 1;
	
	boolean running = false;
	int winner = 0;
	
	PlayerHandler player;
	FruitHandler fruit;
	
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		player = new PlayerHandler();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(player.getKeyAdapter());
		//this.addKeyListener(new MyKeyAdapterP2());
		startGame();
	}
	
	public void startGame() {
		newFruit();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			// Linhas do mapa
			for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			
			// Fruta
			fruit.draw(g);
			
			// Copitcho da cobra
			
			player.draw(g);
			
			drawGetScore(g);
			
			drawScore(g, "J1", 1, player.getScore());
			//drawScore(g, "J2", 2, j2applesEaten);
		}else {
			gameOver(g);
		}
		
	}
	
	// pos = jogador, aE = appleEaten
	public void drawScore(Graphics g, String jogador, int pos, int aE) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 15));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score " + jogador + ": " + aE, (SCREEN_WIDTH - metrics.stringWidth("Score " + jogador + ": " + aE))/2, g.getFont().getSize()*pos);
	
	}
	
	public void drawGetScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 25));
		g.drawString("J"+ lastPlayerGetScore +": + " + lastScore, 0, 25);
	}
	
	public void newFruit() {
		fruit = new FruitHandler(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	public void checkFruit() {
		if(fruit.checkCollision(player)) {
			lastScore = fruit.wichFruit();
			lastPlayerGetScore = 1;
			newFruit();
		}
	}
	
	public void checkCollisions() {
		running = player.checkCollision(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		if(!running) {
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g) {
		// Texto do Game Over
		g.setColor(Color.white);
		drawScore(g, "J1", 1, player.getScore());
//		drawScore(g, "J2", 2, j2fruitsEaten);
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		g.drawString("Vencedor: Jogador " + winner, (SCREEN_WIDTH - metrics.stringWidth("Vencedor: Jogador " + winner))/2, (SCREEN_HEIGHT/2) + (SCREEN_HEIGHT/3));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			player.move();
			checkFruit();
			checkCollisions();
		}
		repaint();
		
	}

}
