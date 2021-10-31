package model;

public class Player {
	private char symbol;
	private int movement;
	private int position;
	private Player next;
	
	public Player(char symbol) {
		this.symbol = symbol;
		movement = 0;
		position = 0;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public Player getNext() {
		return next;
	}

	public void setNext(Player next) {
		this.next = next;
	}
	
	
}
