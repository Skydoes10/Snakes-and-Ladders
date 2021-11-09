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
	private Player firstPlayer;
	private String symPlayers;
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
		ladder = 0;
		snake = ' ';
	}
	
	public String getSymPlayers(Player p) {
		if(p != null) {
			symPlayers += p.getSymbol();
			getSymPlayers(p.getNextPlayer());
		}
		return symPlayers;
	}
	
	public String toStringWithoutNums() {
		String out = "";
		if(snake != ' ') {
			if(snake != ' ' && firstPlayer != null) {
				out = "["+snake+getSymPlayers(firstPlayer)+"]";
			}else {
				out = "["+snake+"]";
			}
		}else if(ladder != 0) {
			if(ladder != 0 && firstPlayer != null) {
				out = "["+ladder+getSymPlayers(firstPlayer)+"]";
			}else {
				out = "["+ladder+"]";
			}
		}else if(firstPlayer != null){
			out = "["+getSymPlayers(firstPlayer)+"]";
		}else {
			out = "[ ]";
		}
		return out;
	}
	
	public String toStringWithNums() {
		String out = "";
		if(ladder != 0) {
			if(ladder != 0 && firstPlayer != null) {
				out = "["+num+ladder+getSymPlayers(firstPlayer)+"]";
			}else {
				out = "["+num+ladder+"]";
			}
		}else if(snake != ' ') {
			if(snake != ' ' && firstPlayer != null) {
				out = "["+num+snake+getSymPlayers(firstPlayer)+"]";
			}else {
				out = "["+num+snake+"]";
			}
		}else  if(firstPlayer != null){
			out = "["+num+getSymPlayers(firstPlayer)+"]";
		}else {
			out = "["+num+"]";
		}
		return out;
	}
	
	public String toStringWithoutPlayers() {
		String out = "";
		if(ladder != 0) {
			out = "["+num+ladder+"]";
		}else if(snake != ' ') {
			out = "["+num+snake+"]";
		}else {
			out = "["+num+"]";
		}
		return out;
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

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

}
