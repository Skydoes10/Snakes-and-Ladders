package model;

public class Node {
	private int row;
	private int col;
	private int num;
	private int ladder;
	private char snake;
	private Node next;
	private Node prev;
	private Node up;
	private Node down;
	private Player first;
	private String symPlayers;
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}
	
	public int getLadder() {
		return ladder;
	}

	public void setLadder(int ladder) {
		this.ladder = ladder;
	}

	public char getSnake() {
		return snake;
	}

	public void setSnake(char snake) {
		this.snake = snake;
	}

	public Player getFirst() {
		return first;
	}

	public void setFirst(Player first) {
		this.first = first;
	}

	public String getSymPlayers() {
		return symPlayers;
	}

	public void setSymPlayers(String symPlayers) {
		this.symPlayers = symPlayers;
	}

	public String toString() {
		return "["+row+", "+col+"]";
	}
	
	public String toString2() {
		return "["+num+"]";
	}
}
