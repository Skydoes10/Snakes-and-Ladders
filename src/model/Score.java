package model;

public class Score {
	private String winPlayer;
	private String players;
	private String size;
	private int score;
	private int s;
	private int e;
	private Score right;
	private Score left;
	private Score parent;
	
	public Score(String winPlayer, int score, String size, int s, int e, String players) {
		this.winPlayer = winPlayer;
		this.size = size;
		this.s = s;
		this.e = e;
		this.players = players;
	}

	public String getWinPlayer() {
		return winPlayer;
	}

	public void setWinPlayer(String winPlayer) {
		this.winPlayer = winPlayer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Score getRight() {
		return right;
	}

	public void setRight(Score right) {
		this.right = right;
	}

	public Score getLeft() {
		return left;
	}

	public void setLeft(Score left) {
		this.left = left;
	}
	
	public Score getParent() {
		return parent;
	}

	public void setParent(Score parent) {
		this.parent = parent;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getE() {
		return e;
	}

	public void setE(int e) {
		this.e = e;
	}

	public String toString() {
		return "Name: "+winPlayer+" Score: "+score;
	}
}
