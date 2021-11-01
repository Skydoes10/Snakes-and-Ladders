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
			createCol(i, 1+j, current, rowPrev);
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
	
	public boolean verifyData(int r, int c, int s, int l, int p, String symbolPlayers) {
		Boolean fail = false;
		if((int)((r*c)-2)/2 > s + l) {
			rows = r;
			colums = c;
			setLadders(l);
			setSnakes(s);
			setNumPlayers(p);
			setSymPlayers(symbolPlayers);
			createBoard();
			if(!(p > 9)) {
				addSymbolsToPlayers(first.getFirstPlayer(), symbolPlayers, p, 0);
				setPositionOfPlayer(first.getFirstPlayer(), 1, p);
				addSnakesToBoard(0, snakes);
				addLaddersToBoard(ladders);
			}else {
				fail = true;
			}
		}else {
			fail = true;
		}
		return fail;
	}
	
	private void addSymbolsToPlayers(Player firstPlayer, String symPlayers, int players, int x) {
		if(players == symPlayers.length() && x == 0) {
			first.setFirstPlayer(new Player(symPlayers.charAt(x)));
			if(symPlayers.length() > 1) {
				first.getFirstPlayer().setNextPlayer(new Player(symPlayers.charAt(x+1)));
				addSymbolsToPlayers(first.getFirstPlayer().getNextPlayer(), symPlayers, players, x + 2);
			}
		}else{
			if(firstPlayer.getNextPlayer() == null && players > x) {
				firstPlayer.setNextPlayer(new Player(symPlayers.charAt(x)));
				addSymbolsToPlayers(firstPlayer.getNextPlayer(),symPlayers, players, x + 1);
			}
		}
	}
	
	private void setPositionOfPlayer(Player player, int x, int numPlayers) {
		if(player != null && numPlayers >= x) {
			player.setPosition(x);
			setPositionOfPlayer(player.getNextPlayer(), x+1, numPlayers);
		}
	}
	
	private void addSnakesToBoard(int r, int snakes) {
		int i = (int) Math.floor(Math.random() * rows);
		int j = (int) Math.floor(Math.random() * colums);
		Node node = searchNode(i, j, first);
		if (snakes > 0 && node.getSnake() == ' ' && node.getLadder() == 0 && node.getNum() != size && node.getNum() != 1) {
			char s = (char) ('A' + i);
			node.setSnake(s);
			addSnakesToBoard(i, node);
			addSnakesToBoard(r+1, snakes-1);
		}else if(snakes > 0){
			addSnakesToBoard(r, snakes);
		}
	}
	
	private void addSnakesToBoard(int r, Node node) {
		int i = (int) Math.floor(Math.random() * (rows-r));
		int j = (int) Math.floor(Math.random() * colums);
		Node nodeS = searchNode(i, j, first);
		if(nodeS.getRow() != r && nodeS.getSnake() == ' ' && nodeS.getLadder() == 0 && nodeS.getNum() != size && nodeS.getNum() != 1) {
			nodeS.setSnake(node.getSnake());
		}else {
			addSnakesToBoard(r, node);
		}
	}
	
	private void addLaddersToBoard(int ladders) {
		int i = (int)Math.floor(Math.random() * rows);
		int j = (int)Math.floor(Math.random() * colums);
		Node node = searchNode(i, j, first);
		if(node.getSnake() == ' ' && node.getLadder() == 0 && node.getNum() != 1 && node.getNum() != size && ladders != 0){
			int l = ladders;
			node.setLadder(l);
			addLaddersToBoard(i, node);
			addLaddersToBoard(ladders - 1);
		}else if(ladders > 0){
			addLaddersToBoard(ladders);
		}
	}
	
	private void addLaddersToBoard(int row, Node node) {
		int i = (int)Math.floor(Math.random() * rows);
		int j = (int)Math.floor(Math.random() * colums);
		Node nodeS = searchNode(i, j, first);
		if(nodeS.getSnake() == ' ' && nodeS.getLadder() == 0 && nodeS.getRow() != row && nodeS.getNum() != 1 && nodeS.getNum() != size){
			nodeS.setLadder(node.getLadder());
		}else {
			addLaddersToBoard(row, node);
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
	
	public String getSymPlayers() {
		return SymPlayers;
	}

	public void setSymPlayers(String symPlayers) {
		SymPlayers = symPlayers;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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
	
	public String toString2(){
		String msg = "";
		msg = toStringRow2(first);
		return msg;
	}
	
	private String toStringRow2(Node firstRow) {
		String msg = "";
		if(firstRow != null) {
			msg = toStringCol2(firstRow)+"\n";
			msg += toStringRow2(firstRow.getDown());
		}
		return msg;
	}
	
	private String toStringCol2(Node current) {
		String msg = "";
		if(current != null) {
			msg = current.toStringWithoutNums();
			msg += toStringCol2(current.getNext());
		}
		return msg;
	}
	
	public String toString3(){
		String msg = "";
		msg = toStringRow3(first);
		return msg;
	}
	
	private String toStringRow3(Node firstRow) {
		String msg = "";
		if(firstRow != null) {
			msg = toStringCol3(firstRow)+"\n";
			msg += toStringRow3(firstRow.getDown());
		}
		return msg;
	}
	
	private String toStringCol3(Node current) {
		String msg = "";
		if(current != null) {
			msg = current.toStringWithoutPlayers();
			msg += toStringCol3(current.getNext());
		}
		return msg;
	}
}
