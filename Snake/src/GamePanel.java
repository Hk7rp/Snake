import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 1000;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int j1x[] = new int[GAME_UNITS];
	final int j1y[] = new int[GAME_UNITS];
	final int j2x[] = new int[GAME_UNITS];
	final int j2y[] = new int[GAME_UNITS];
	int j1bodyParts = 6;
	int j2bodyParts = 6;
	int j1applesEaten;
	int j2applesEaten;
	int appleX;
	int appleY;
	char j1Direction = 'R';
	char j2Direction = 'R';
	boolean running = false;
	int winner = 0;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapterP1());
		this.addKeyListener(new MyKeyAdapterP2());
		startGame();
	}
	
	public void startGame() {
		newApple();
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
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// Copitcho da cobra
			
			
			drawPlayer(g, Color.green, j1bodyParts, j1x, j1y); // Jogador 1
			drawPlayer(g, Color.red, j2bodyParts, j2x, j2y); // Jogador 2
			
			drawScore(g, "J1", 1, j1applesEaten);
			drawScore(g, "J2", 2, j2applesEaten);
		}else {
			gameOver(g);
		}
		
	}
	
	// bp == BodyParts
	public void drawPlayer(Graphics g, Color color, int bP, int[] x, int[] y) {
		for(int i = 0; i < bP; i++) {
			if(i == 0) {
				g.setColor(color);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			} else {
				g.setColor(color);
				//g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
	}
	
	// pos = jogador, aE = appleEaten
	public void drawScore(Graphics g, String jogador, int pos, int aE) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 15));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score " + jogador + ": " + aE, (SCREEN_WIDTH - metrics.stringWidth("Score " + jogador + ": " + aE))/2, g.getFont().getSize()*pos);
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
		
	}
	
	public void move() {
		movePlayer(j1bodyParts, j1Direction, j1x, j1y); // Jogador 1
		movePlayer(j2bodyParts, j2Direction, j2x, j2y); // Jogador 2
		
	}
	
	// bp == BodyParts
	public void movePlayer(int bP, char direction, int[] x, int[] y) {
		for(int i = bP; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		
		}
	}
	
	public void checkApple() {
		if((j1x[0] == appleX) && (j1y[0] == appleY)) {
			j1bodyParts++;
			j1applesEaten++;
			newApple();
		}
		if((j2x[0] == appleX) && (j2y[0] == appleY)) {
			j2bodyParts++;
			j2applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// Sim... a última variável é pra declarar o vencedor caso ele perca.
		checkCollisionPlayer(j1bodyParts, j1x, j1y, 2); // Jogador 1
		checkCollisionPlayer(j2bodyParts, j2x, j2y, 1); // Jogador 2
		
	}
	
	public void checkCollisionPlayer(int bP, int[] x, int [] y, int w) {
		for(int i = bP; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// checa a colisão com a borda esquerda
		if(x[0] < 0) {
			running = false;
			winner = w;
		}
		
		// checa a colisão com a borda direita
		if(x[0] > SCREEN_WIDTH) {
			running = false;
			winner = w;
		}
		
		// checa a colisão com a borda de cima
		if(y[0] < 0) {
			running = false;
			winner = w;
		}
		
		// checa a colisão com a borda de baixo
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
			winner = w;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		// Texto do Game Over
		g.setColor(Color.white);
		drawScore(g, "J1", 1, j1applesEaten);
		drawScore(g, "J2", 2, j2applesEaten);
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		g.drawString("Vencedor: Jogador " + winner, (SCREEN_WIDTH - metrics.stringWidth("Vencedor: Jogador " + winner))/2, (SCREEN_HEIGHT/2) + (SCREEN_HEIGHT/3));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapterP1 extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(j1Direction != 'R') {
					j1Direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(j1Direction != 'L') {
					j1Direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(j1Direction != 'D') {
					j1Direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(j1Direction != 'U') {
					j1Direction = 'D';
				}
				break;
			}
		}
		
	}
	
	public class MyKeyAdapterP2 extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				if(j2Direction != 'R') {
					j2Direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(j2Direction != 'L') {
					j2Direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if(j2Direction != 'D') {
					j2Direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if(j2Direction != 'U') {
					j2Direction = 'D';
				}
				break;
			}
		}
		
	}

}
