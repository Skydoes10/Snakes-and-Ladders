package model;

public class Leaderboard {
	private Score root;
	
	public Leaderboard() {
		
	}
	
	public void addScore(String winPlayer, int score, String size, int s, int e, String players){
		Score newScore = new Score(winPlayer, score, size, s, e, players);
		if(root == null){
			root = newScore;
		}else{
			addScore(root, newScore);
		}
	}

	private void addScore(Score current, Score newScore){
		if(newScore.getScore() <= current.getScore()){
			if(current.getLeft() == null){
				current.setLeft(newScore);
				newScore.setParent(current);
			}else{
				addScore(current.getLeft(), newScore);
			}
		}else{
			if(current.getRight() == null){
				current.setRight(newScore);
				newScore.setParent(current);
			}else{
				addScore(current.getRight(), newScore);
			}
		}
	}
	
	public Score searchScore(int score){
		return searchScore(root, score);
	}
	
	private Score searchScore(Score current, int score){
		if(current == null || current.getScore() == score){
			return current;
		}else{
			if(score <= current.getScore()){
				return searchScore(current.getLeft(), score);
			}else{
				return searchScore(current.getRight(), score);
			}
		}
	}
	
//	public String toString() {
//		return ;
//	}
}
