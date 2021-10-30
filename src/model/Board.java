package model;

public class Board {
	private int rows;
	private int colums;
	private int size;
	private int snakes;
	private int ladders;
	private String players;
	private String board;
	private Node first;
	
	public Board(int n, int m, int s, int e, String p) {
		rows = n;
		colums = m;
		size = n*m;
		snakes = s;
		ladders = e;
		players = p;
		createBoard();
//		putNumbers(first, size);
	}
	
	private void createBoard() {
		System.out.println("vamos a crear la matrix");
		first = new Node(0,0);
		System.out.println("se crea el first");
		createRow(0,0, first);
	}

	private void createRow(int i, int j, Node currentFirstRow) {
		System.out.println("en createRow con la fila "+i);
		createCol(i, j+1, currentFirstRow, currentFirstRow.getUp());
		if(i+1 < rows) {
			Node downFirstRow = new Node(i+1, j);
			downFirstRow.setUp(currentFirstRow);
			currentFirstRow.setDown(downFirstRow);
			createRow(i+1, j, downFirstRow);
		}
	}

	private void createCol(int i, int j, Node prev, Node rowPrev) {
		if(j < colums) {
			System.out.println("       en createCol con la columna "+j);
			Node current = new Node(i, j);
			current.setPrev(prev);
			prev.setNext(current);
			
			if(rowPrev != null) {
				rowPrev = rowPrev.getNext();
				current.setUp(rowPrev);
				rowPrev.setDown(current);
			}
			createCol(i, j+1, current, rowPrev);
		}
	}
	
//	private void putNumbers(Node current, int size) {
//		if(current == first) {
//			current.setNum(size);
//		}else {
//			current.setNum(size);
//			putNumbers(current.getNext(), size-1);
//		}
//		putNumbers(current, size);
//	}
	
	public void putNumbers(Node first, int rows, int colums) {
		if(rows % 2 != 0) {
			if(first.getCol() == colums) {
				
			}else {
				putNumbers(first.getNext(), rows, colums);
			}
		}else {
			first.setNum(rows*colums);
		}
		
	}
	
//	private void putNumbers(Node current, int num) {
//		if(current.getRow() == rows && current.getCol() == 0) {
//			current.setNum(1);
//		}else {
//			current.setNum(num);
//			putNumbers(current.getNext(), num-1);
//		}
//	}

	public int getSnakes() {
		return snakes;
	}

	public void setSnakes(int snakes) {
		this.snakes = snakes;
	}

	public int getLadders() {
		return ladders;
	}

	public void setLadders(int ladders) {
		this.ladders = ladders;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColums() {
		return colums;
	}

	public void setColums(int colums) {
		this.colums = colums;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}
	
	public String toString(){
		String msg = "";
		msg = toStringRow(first);
		return msg;
	}

	private String toStringRow(Node firstRow) {
		String msg = "";
		if(firstRow != null) {
			msg = toStringCol(firstRow)+"\n";
			msg += toStringRow(firstRow.getDown());
		}
		return msg;
	}

	private String toStringCol(Node current) {
		String msg = "";
		if(current != null) {
			msg = current.toString();
			msg += toStringCol(current.getNext());
		}
		return msg;
	}
}
