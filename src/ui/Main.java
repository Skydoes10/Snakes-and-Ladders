package ui;

import java.util.Scanner;
import model.Board;

public class Main {
	private Scanner sc;
	private Board board;
	
	public Main(){
		sc = new Scanner(System.in);
		board = new Board(0,0);
	}

	public static void main(String[] args) {
		Main objMain = new Main();
		objMain.mainMenu();
	}

	public void mainMenu() {
		System.out.println("\n******************************************************" +
				"\nType number of an option:\n" + 
				"\n(1) Play" + 
				"\n(2) See the leaderboard" + 
				"\n(3) Exit" + 
				"\n******************************************************\n"
				);
		int option = Integer.parseInt(sc.nextLine());
		if(option != 3) {
			switch(option) {
				case 1: typeNumbers();
				break;
				case 2: 
				break;
				default:
					System.out.println("\nError, invalid option");
					mainMenu();
			}
		}else {
			System.out.println("Bye ;)");
		}
	}
	
	public void typeNumbers() {
		System.out.println("\nPlease type on a single line 5 positive integers separated with a space, example: 4 5 2 3 3 . Then press ENTER\n"
				+ "The first two numbers are the dimensions of the game board.\n"
				+ "The third number is the number of snakes.\n"
				+ "The fourth number is the number of ladders.\n"
				+ "Finally, the fifth number is the number of players\n"
				+ "NOTE: Only a maximum of 9 players can play");
		String numbers = sc.nextLine();
		String[] arrayNumbers = numbers.split(" ");
		int n = Integer.parseInt(arrayNumbers[0]);
		int m = Integer.parseInt(arrayNumbers[1]);
		int s = Integer.parseInt(arrayNumbers[2]);
		int e = Integer.parseInt(arrayNumbers[3]);
		int p = Integer.parseInt(arrayNumbers[4]);
		System.out.println("Now type the symbol for each player. Example: * !, O, X, %, $, #, +, &\n"
				+ "NOTE: Write all symbols together, do not leave spaces.\n");
		String players = sc.nextLine();
		if(!board.verifySettings(n, m, s, e, p, players)) {
			System.out.println(board.toString());
		}else {
			System.out.println("An error has occurred with the data provided\n");
			typeNumbers();
		}
		
	}
	
	
}
