package model;

public class Board {
	private int rows;
	private int colums;
	private int size;
	private int snakes;
	private int ladders;
	private int numPlayers;
	private int players;
	private boolean winGame;
	private String symPlayers;
	private Node first;
	private Player player;

	public Board(int n, int m) {
		rows = n;
		colums = m;
		size = n*m;
		createBoard();
	}

	private void createBoard() {
		numPlayers = 0;
		players = 0;
		winGame = false;
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
			assignPreviousNum(num+1, node.getUp());
		}
	}

	private void assignPreviousNum(int num, Node node) {
		node.setNum(num);
		if(node.getPrev() != null) {
			assignPreviousNum(num+1, node.getPrev());
		}else if(node.getUp() != null) {
			assignNextNum(num+1, node.getUp());
		}
	}

	public boolean addData(int r, int c, int s, int l, int p, String symbolPlayers) {
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

	public String movePlayer() {
		String out = "";
		if(numPlayers == players) {
			players = 0;
			out = movePlayer();
		}else {
			players++;
			Player player = searchPlayerByNum(players);
			if(player != null) {
				player.setMoves(player.getMoves()+1);
				out = movePlayer(player);
			}else {
				out = movePlayer();
			}
		}
		return out;
	}

	public String movePlayer(Player p) {
		String out = "";
		int die = (int) Math.floor(Math.random()*6+1);
		boolean win = movePlayer(p, die);
		out = "\nPlayer "+p.getSymbol()+" has rolled the die and obtained the score "+die+"\n";
		if(win) {
			player = p;
			out = "\nPlayer "+p.getSymbol()+" has won the game, with "+p.getMoves()+" moves\n";
			setWinGame(win);
		}
		return out;
	}

	private boolean movePlayer(Player currentPlayer, int die) {
		boolean win = false;
		Node nodeSearch = searchNodeByPlayer(currentPlayer);
		if(nodeSearch != null) {
			if((nodeSearch.getNum() + die) >= size) {
				win = true;
			}else {
				movePlayerByNode(player, nodeSearch.getNum() + die);
			}
		}
		return win;
	}

	private Node searchNodeByPlayer(Player currentPlayer) {
		Node node = getFirst();
		Node nodeSearch = null;
		if(node != null) {
			nodeSearch = nextNode(currentPlayer, node);
		}
		return nodeSearch;
	}

	private Node nextNode(Player currentPlayer, Node node) {
		Player player = searchPlayerByNode(currentPlayer, node);
		Node nodeSearch = null;
		if(player != null) {
			nodeSearch = node;
		}else if(node.getNext() != null && player == null) {
			nodeSearch = nextNode(currentPlayer, node.getNext());
		}else if(node.getUp() != null && player == null){
			nodeSearch = previousNode(currentPlayer, node.getUp());
		}
		return nodeSearch;
	}

	private Node previousNode(Player currentPlayer, Node node) {
		Player player = searchPlayerByNode(currentPlayer, node);
		Node nodeSearch = null;
		if(player != null) {
			nodeSearch = node;
		}else if(node.getPrev() != null && player == null) {
			nodeSearch = previousNode(currentPlayer, node.getPrev());
		}else  if(node.getUp() != null && player == null){
			nodeSearch = previousNode(currentPlayer, node.getUp());
		}
		return nodeSearch;
	}

	private Player searchPlayerByNode(Player currentPlayer, Node node) {
		Player newPlayer = null;
		if(node.getFirstPlayer() != null && node.getFirstPlayer() == currentPlayer) {
			newPlayer = new Player(currentPlayer.getSymbol());
			player = newPlayer;
			player.setMoves(currentPlayer.getMoves());
			player.setPosition(currentPlayer.getPosition());
			if(node.getFirstPlayer().getNextPlayer() == null) {
				node.setFirstPlayer(null);
			}else {
				node.setFirstPlayer(node.getFirstPlayer().getNextPlayer());
			}
		}
		return player;
	}

	private void movePlayerByNode(Player player, int num) {
		movePlayerByNode(getFirst(), player, num);
	}

	private void movePlayerByNode(Node node, Player player, int num) {
		if(node.getNum() == num) {
			if(node.getFirstPlayer() == null) {
				if(node.getSnake() != ' ' || node.getLadder() != 0) {
					searchSnakesAndLaddersOnBoard(player, node);
				}else {
					node.setFirstPlayer(player);
				}
			}else {
				movePlayers(player, node, node.getFirstPlayer());
			}
		}else if(node.getNext() != null){
			movePlayerByNode(node.getNext(), player, num);
		}else if(node.getNext() == null) {
			moveUpPrevious(node, player, num);
		}
	}

	private void searchSnakesAndLaddersOnBoard(Player player, Node node) {
		laddersAndSnakesOnBoard1(getFirst(), node, player);
	}

	private void laddersAndSnakesOnBoard1(Node currentNode, Node node, Player player) {
		if(node.getSnake() != ' ' && node.getSnake() == currentNode.getSnake() && currentNode.getNum() <= node.getNum()) {
			if(currentNode.getFirstPlayer() != null) {
				movePlayers(player, currentNode, currentNode.getFirstPlayer());
			}else {
				currentNode.setFirstPlayer(player);
			}
		}else if(node.getLadder() == currentNode.getLadder() && currentNode.getNum() >= node.getNum()) {
			boolean check = searchLadder1(currentNode, node);
			if(currentNode.getNum() == node.getNum()) {
				if(!check) {
					if(currentNode.getFirstPlayer() != null) {
						movePlayers(player, currentNode, currentNode.getFirstPlayer());
					}else {
						currentNode.setFirstPlayer(player);
					}
				}else {
					if(currentNode.getNext() != null) {
						laddersAndSnakesOnBoard1(currentNode.getNext(), node, player);
					}else if(currentNode.getUp() != null) {
						LaddersAndSnakesOnBoard2(currentNode.getUp(), node, player);
					}
				}
			}else {
				if(currentNode.getFirstPlayer() != null) {
					movePlayers(player, currentNode, currentNode.getFirstPlayer());
				}else {
					currentNode.setFirstPlayer(player);
				}
			}
		}else if(currentNode.getNext() != null){
			laddersAndSnakesOnBoard1(currentNode.getNext(), node, player);
		}else if(currentNode.getUp() != null) {
			LaddersAndSnakesOnBoard2(currentNode.getUp(), node, player);
		}
	}

	private void movePlayers(Player player, Node node, Player currentPlayer) {
		if(player.getPosition() > currentPlayer.getPosition()) {
			if(currentPlayer.getNextPlayer() != null) {
				movePlayers(player, node, currentPlayer.getNextPlayer());
			}else {
				currentPlayer.setNextPlayer(player);
			}
		}else {
			node.setFirstPlayer(player);
			node.getFirstPlayer().setNextPlayer(currentPlayer);
		}
	}

	private boolean searchLadder1(Node current, Node node) {
		boolean check = false;
		if(node.getLadder() != 0 && node.getLadder() == current.getLadder() && current.getNum() > node.getNum()) {
			check = true;
		}else if(current.getNext() != null){
			check = searchLadder1(current.getNext(),node);
		}else if(current.getUp() != null) {
			check = searchLadder2(current.getUp(),node);
		}
		return check;
	}

	private boolean searchLadder2(Node current, Node node) {
		boolean check = false;
		if(node.getLadder() != 0 && node.getLadder() == current.getLadder() && current.getNum() > node.getNum()) {
			check = true;
		}else if(current.getPrev() != null){
			check = searchLadder2(current.getPrev(),node);
		}else if(current.getUp() != null) {
			check = searchLadder1(current.getUp(),node);
		}
		return check;
	}

	private void LaddersAndSnakesOnBoard2(Node currentNode, Node node, Player player) {
		if(node.getLadder() == currentNode.getLadder() && currentNode.getNum() >= node.getNum()) {
			boolean check = searchLadder1(currentNode,node);
			if(currentNode.getNum() == node.getNum()) {
				if(!check) {
					if(currentNode.getFirstPlayer() != null) {
						movePlayers(player, currentNode, currentNode.getFirstPlayer());
					}else {
						currentNode.setFirstPlayer(player);
					}
				}else {
					if(currentNode.getNext() != null) {
						laddersAndSnakesOnBoard1(currentNode.getNext(), node, player);
					}else if(currentNode.getUp() != null) {
						LaddersAndSnakesOnBoard2(currentNode.getUp(), node, player);
					}
				}
			}else {
				if(currentNode.getFirstPlayer() != null) {
					movePlayers(player, currentNode, currentNode.getFirstPlayer());
				}else {
					currentNode.setFirstPlayer(player);
				}
			}
		}else if(node.getSnake() != ' ' && node.getSnake() == currentNode.getSnake() && currentNode.getNum() <= node.getNum()) {
			if(currentNode.getFirstPlayer() != null) {
				movePlayers(player, currentNode, currentNode.getFirstPlayer());
			}else {
				currentNode.setFirstPlayer(player);
			}
		}else if(currentNode.getPrev() != null){
			LaddersAndSnakesOnBoard2(currentNode.getPrev(), node, player);
		}else if(currentNode.getUp() != null) {
			laddersAndSnakesOnBoard1(currentNode.getUp(), node, player);
		}
	}

	private void moveUpPrevious(Node node, Player player, int num) {
		if(node.getUp() != null) {
			movePrevious(node.getUp(), player, num);
		}
	}

	private void movePrevious(Node node, Player player, int num) {
		if(node.getNum() == num) {
			if(node.getFirstPlayer() != null) {
				movePlayers(player, node, node.getFirstPlayer());
			}else {
				if(node.getSnake() != ' ' || node.getLadder() != 0) {
					searchSnakesAndLaddersOnBoard(player, node);
				}else {
					node.setFirstPlayer(player);
				}
			}
		}else if(node.getPrev() != null){
			movePrevious(node.getPrev(), player, num);
		}else if(node.getPrev() == null) {
			moveUp(player, node, num);
		}
	}

	private void moveUp(Player player, Node node, int num) {
		if(node.getUp() != null) {
			movePlayerOnBoard(player, node.getUp(), num);
		}
	}

	private void movePlayerOnBoard(Player player, Node node, int num) {
		if(node.getNum() == num) {
			if(node.getFirstPlayer() == null) {
				if(node.getSnake() != ' ' || node.getLadder() != 0) {
					searchSnakesAndLaddersOnBoard(player, node);
				}else {
					node.setFirstPlayer(player);
				}
			}else {
				movePlayers(player, node, node.getFirstPlayer());
			}

		}else if(node.getNext() != null){
			movePlayerOnBoard(player, node.getNext(), num);
		}else if(node.getNext() == null) {
			moveUpPrevious(node, player, num);
		}
	}

	private Player searchPlayerByNum(int num) {
		Player p = searchPlayerByNum(num, first);;
		return p;
	}

	private Player searchPlayerByNum(int num, Node node) {
		Player p = searchPlayerToMove(node, num);
		if(p == null && node.getNext() != null) {
			p = searchPlayerByNum(num, node.getNext());
		}else if(p == null && node.getUp() != null){
			p = searchPlayerByNum(num, node.getUp());
		}

		return p;
	}

	private Player searchPlayerToMove(Node node, int num) {
		Player p = null;
		if(node.getFirstPlayer() != null) {
			p = searchCurrentPlayer(node.getFirstPlayer(), num);
		}
		return p;
	}

	private Player searchCurrentPlayer(Player currentPlayer, int num) {
		Player p = null;
		if(currentPlayer.getPosition() == num) {
			p = currentPlayer;
		}else if(currentPlayer.getNextPlayer() != null){
			p = searchCurrentPlayer(currentPlayer.getNextPlayer(), num);
		}
		return p;
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

	public boolean getWinGame() {
		return winGame;
	}

	public void setWinGame(boolean winGame) {
		this.winGame = winGame;
	}

	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public String getSymPlayers() {
		return symPlayers;
	}

	public void setSymPlayers(String symPlayers) {
		this.symPlayers = symPlayers;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}



	public String toString(){
		String out = "";
		out = toStringRow(first);
		return out;
	}

	private String toStringRow(Node firstRow) {
		String out = "";
		if(firstRow != null) {
			out = toStringCol(firstRow)+"\n";
			out += toStringRow(firstRow.getDown());
		}
		return out;
	}

	private String toStringCol(Node current) {
		String out = "";
		if(current != null) {
			out = current.toStringWithNums();
			out += toStringCol(current.getNext());
		}
		return out;
	}



	public String toString2(){
		String out = "";
		out = toStringRow2(first);
		return out;
	}

	private String toStringRow2(Node firstRow) {
		String out = "";
		if(firstRow != null) {
			out = toStringCol2(firstRow)+"\n";
			out += toStringRow2(firstRow.getDown());
		}
		return out;
	}

	private String toStringCol2(Node current) {
		String out = "";
		if(current != null) {
			out = current.toStringWithoutNums();
			out += toStringCol2(current.getNext());
		}
		return out;
	}

	public String toString3(){
		String out = "";
		out = toStringRow3(first);
		return out;
	}

	private String toStringRow3(Node firstRow) {
		String out = "";
		if(firstRow != null) {
			out = toStringCol3(firstRow)+"\n";
			out += toStringRow3(firstRow.getDown());
		}
		return out;
	}

	private String toStringCol3(Node current) {
		String out = "";
		if(current != null) {
			out = current.toStringWithoutPlayers();
			out += toStringCol3(current.getNext());
		}
		return out;
	}

}
