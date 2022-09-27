package com.handler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PlayerHandler extends GameObject{
	
	private KeyAdapter keyAdapter;
	private int[] x = new int[getUNIT_SIZE()];
	private int[] y = new int[getUNIT_SIZE()];
	private int bodyParts = 6;
	private int score = 0;
	private char direction = 'R';
	private Color color;
	
	public PlayerHandler() {
		keyAdapter = new MyKeyAdapter();
		Random rand = new Random();
		color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		for(int i = 0; i < bodyParts; i++) {
			if(i == 0) {
				g.fillRect(x[i], y[i], getUNIT_SIZE(), getUNIT_SIZE());
			} else {
				g.fillRect(x[i], y[i], getUNIT_SIZE(), getUNIT_SIZE());
			}
		}
	}
	
	
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - getUNIT_SIZE();
			break;
		case 'D':
			y[0] = y[0] + getUNIT_SIZE();
			break;
		case 'L':
			x[0] = x[0] - getUNIT_SIZE();
			break;
		case 'R':
			x[0] = x[0] + getUNIT_SIZE();
			break;
		
		}
	}
	
	public boolean checkCollision(int width, int height) {
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				return false;
			}
		}
		
		// checa a colisão com a borda esquerda
		if(x[0] < 0) {
			return false;
		}
		
		// checa a colisão com a borda direita
		if(x[0] > width) {
			return false;
		}
		
		// checa a colisão com a borda de cima
		if(y[0] < 0) {
			return false;
		}
		
		// checa a colisão com a borda de baixo
		if(y[0] > height) {
			return false;
		}
		return true;
	}
	

	public int getBodyParts() {
		return bodyParts;
	}

	public void setBodyParts(int bodyParts) {
		this.bodyParts = bodyParts;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}
	
	public int[] getX() {
		return x;
	}
	
	public void setX(int[] x) {
		this.x = x;
	}
	
	public int[] getY() {
		return y;
	}
	
	public void setY(int[] y) {
		this.y = y;
	}
	

	public KeyAdapter getKeyAdapter() {
		return keyAdapter;
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
		
	}
	
}
