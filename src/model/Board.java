package model;

public class Board {
	private int rows;
	private int colums;
	private int size;
	private int snakes;
	private int ladders;
	private int numPlayers;
	private String SymPlayers;
	private String board;
	private Node first;
	private Player player;
	
	public Board(int n, int m) {
		rows = n;
		colums = m;
		size = n*m;
		createBoard();
	}
	
	private void createBoard() {
		first = new Node(0,0);
		createRow(0,0, first);
		assignNumsToNode(rows-1, 0, 0);
	}

	private void createRow(int i, int j, Node currentFirstRow) {
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
	
	private void assignNumsToNode(int row, int col, int num) {
		Node node = searchNode(row, col, first);
		if(node != null) {
			assignNextNum(num+1, node);
		}
	}
	
	private Node searchNode(int row, int col, Node current) {
		Node nodeFound = null;
		if((current.getRow() == row && current.getCol() == col) || current == null) {
			nodeFound = current;
		}else {
			if(current.getRow() < row) {
				if(current.getDown() != null) {
					nodeFound = searchNode(row, col, current.getDown());
				}
			}else if(current.getCol() < col) {

				if(current.getNext() != null) {
					nodeFound = searchNode(row, col, current.getNext());
				}
			}
		}
		return nodeFound;
	}
	
	private void assignNextNum(int num, Node node) {
		node.setNum(num);
		if(node.getNext() != null) {
			assignNextNum(num+1, node.getNext());
		}else if(node.getUp() != null) {
			assignPrevNum(num+1, node.getUp());
		}
	}
		
	private void assignPrevNum(int num, Node node) {
		node.setNum(num);
		if(node.getPrev() != null) {
			assignPrevNum(num+1, node.getPrev());
		}else if(node.getUp() != null) {
			assignNextNum(num+1, node.getUp());
		}
	}
	
	public boolean verifySettings(int r, int c, int s, int l, int p, String symbolPlayers) {
		Boolean fail = false;
		if((int)((r*c)-2)/2 > s + l) {
			rows = r;
			colums = c;
			setLadders(l);
			setSnakes(s);
			setNumPlayers(p);
			createBoard();
//			if(symbolPlayers.length() == p) {
//				assignSymbolsToPlayers(0, p, symbolPlayers);
//			}else {
//				fail = true;
//			}
		}else {
			fail = true;
		}
		return fail;
	}
	
//	private void assignSymbolsToPlayers(int index, int numPlayers, String symbolPlayers) {
//		if(index < numPlayers) {
//			player.setSymbol(symbolPlayers.charAt(index));
//			assignSymbolsToPlayers(index++, numPlayers, symbolPlayers);
//		}else {
//			
//		}
//		
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

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
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
			msg = current.toStringWithNums();
			msg += toStringCol(current.getNext());
		}
		return msg;
	}
}
