package com.handler;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class FruitHandler extends GameObject{

	final String FRUITS[] = new String[] {"Apple", "Pineapple", "Lemon", "Banana", "Strawberry"};
	private String fruit;
	private int x;
	private int y;
	
	public FruitHandler(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		Random random = new Random();
		setFruit(FRUITS[random.nextInt(FRUITS.length)]);
		x = random.nextInt((int)(SCREEN_WIDTH/getUNIT_SIZE())) * getUNIT_SIZE();
		y = random.nextInt((int)(SCREEN_HEIGHT/getUNIT_SIZE())) * getUNIT_SIZE();
	}
	
	public void draw(Graphics g) {
		
		Color color = Color.white;
		
		switch(fruit) {
		case "Apple":
			color = Color.red;
			break;
		case "Pineapple":
			color = Color.yellow;
			break;
		case "Lemon":
			color = new Color(102, 255, 102);
			break;
		case "Banana":
			color = new Color(255, 255, 102);
			break;
		case "Strawberry":
			color = new Color(204, 51, 0);
			break;
		}
		
		g.setColor(color);
		g.fillOval(x, y, getUNIT_SIZE(), getUNIT_SIZE());
	}
	
	public int wichFruit() {
		
		switch(fruit) {
		case "Apple":
			return 3;
		case "Pineapple":
			return 7;
		case "Lemon":
			return 6;
		case "Banana":
			return 8;
		case "Strawberry":
			return 2;
		default:
			return 0;
		}
	}
	
	public boolean checkCollision(PlayerHandler player) {
		if((player.getX()[0] == x) && (player.getY()[0] == y)) {
			player.setBodyParts(player.getBodyParts() + 1);
			player.setScore(player.getScore()+ wichFruit());
			return true;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}
	
	
}
