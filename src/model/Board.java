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
//		System.out.println("vamos a crear la matrix");
		first = new Node(0,0, size);
//		System.out.println("se crea el first");
		createRow(0,0, first);
	}

	private void createRow(int i, int j, Node currentFirstRow) {
//		System.out.println("en createRow con la fila "+i);
		createCol(i, j+1, currentFirstRow, currentFirstRow.getUp());
		if(i+1 < rows) {
			Node downFirstRow = new Node(i+1, j, size-1);
			downFirstRow.setUp(currentFirstRow);
			currentFirstRow.setDown(downFirstRow);
			createRow(i+1, j, downFirstRow);
		}
//		evenOrOddRows();
	}

	private void createCol(int i, int j, Node prev, Node rowPrev) {
		if(j < colums) {
//			System.out.println("       en createCol con la columna "+j);
			Node current = new Node(i, j, size-1);
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
	

	
	private void evenOrOddRows() {
		if(rows % 2 == 0) {
			first.setNum(size);
			putNumbersOnRows(first.getNext(), size-1);
		}else {
			putNumbers(first);
		}
	}
	
	private void putNumbersOnRows(Node current, int size) {
		if(current.getNext() != null && current.getPrev().getNum() != 0) {
			current.setNum(size);
			putNumbersOnRows(current.getNext(), size-1);
		}else if(current.getNext() == null && current.getDown() != null){
			current.setNum(size);
			putNumbersOnRows(current.getDown(), size-1);
		}else if(current.getPrev() != null) {
			current.setNum(size);
			putNumbersOnRows(current.getPrev(), size-1);
		}else if(current.getPrev() == null && current.getDown() != null) {
			current.setNum(size);
			putNumbersOnRows(current.getDown(), size-1);
		}
	}
	
	private void putNumbersOnColums(Node current, int size) {
		System.out.println("CURRENT 2: "+current.toString());
		System.out.println("SIZE 2: "+size);
		if(current.getDown() != null && current.getNext() == null) {
			current.setNum(size);
			putNumbersOnColums(current.getPrev(), size-1);
		}else if(current.getDown() != null && current.getUp() != null && current.getNext() != null && current.getPrev() != null) {
			putNumbersOnRows(current, size);
		}else if(current.getDown() != null && current.getUp() != null && current.getNext() != null && current.getPrev() == null) {
			current.setNum(size);
			putNumbersOnRows(current.getDown(), size-1);
		}
	}
	
	
	
	private void putNumbers(Node current) {
		if(current.getCol() == colums-1 && current.getRow() == 0) {
			current.setNum(size);
			
		}else {
			putNumbers(current.getNext());
		}
	}

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
			msg = current.toString2();
			msg += toStringCol(current.getNext());
		}
		return msg;
	}
}
