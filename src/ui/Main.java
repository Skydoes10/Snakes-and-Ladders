package ui;

import java.util.Scanner;
import model.Board;

public class Main {
	private Scanner sc;
	
	public Main(){
		sc = new Scanner(System.in);
	}

	public static void main(String[] args) {
		Main objMain = new Main();
		objMain.mainMenu();
	}

	public void mainMenu() {
		boolean menu = true;
		int option;
		while(menu) {
			System.out.println("\n******************************************************" +
								"\nSelect an option:\n" + 
								"\n(1) Play" + 
								"\n(2) See the leaderboard" + 
								"\n(3) Exit" + 
								"\n******************************************************\n"
								);
		option = Integer.parseInt(sc.nextLine());
		switch(option) {
			case 1: typeNumbers();
				break;
			case 2: 
				break;
			case 3: System.out.println("\nBye!");
					menu = false;
				break;
			default:
					System.out.println("\nError, invalid option");
		}
		}
	}
	
	public void typeNumbers() {
		System.out.println("\nPlease type on a single line 5 positive integers separated with a space, example: 4 5 2 3 3 . Then press ENTER\n"
				+ "The first two numbers are the dimensions of the game board.\n"
				+ "The third number is the number of snakes.\n"
				+ "The fourth number is the number of ladders.\n"
				+ "Finally, the fifth number is the number of players\n");
		String numbers = sc.nextLine();
		String[] arrayNumbers = numbers.split(" ");
		int n = Integer.parseInt(arrayNumbers[0]);
		int m = Integer.parseInt(arrayNumbers[1]);
		int s = Integer.parseInt(arrayNumbers[2]);
		int e = Integer.parseInt(arrayNumbers[3]);
		int p = Integer.parseInt(arrayNumbers[4]);
		System.out.println("Now type the symbol of each player. Example: * ! O X % $ # + &\n");
		String players = sc.nextLine();
		Board board = new Board(n, m, s, e, players);
		System.out.println(board.toString());
	}
	
	
}
