package service;

import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * This is main class, responsible for showing menu options and give users' choices to program.
 */
public class Main {
	
	/** The Menu object. */
	static Menu menu;
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 */
	public static void main(String[] args) throws IOException, ParseException, InvalidFormatException {
		long startTime = System.currentTimeMillis() / 1000;
		
		menu = new Menu();
		menu.init();
		
		menu();

		long endTime = System.currentTimeMillis() / 1000;
		System.out.println("Total Running time: " + (endTime - startTime) + "s");

	}
	
	/**
	 * This method will show all options for users to choose and then excute.
	 *
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 */
	public static void menu() throws InvalidFormatException, IOException, ParseException {
		
		Scanner input = new Scanner(System.in);
		String option, keyword;
		int choice;
		boolean loop = true;
		
		
		while(loop) {
			printOptions();
			option = input.nextLine();
			choice = isNumber(option);
			
			if(choice == 0) {
				System.out.println("Invalid choice! Please input again!");
			}else {
				switch(choice) {
					case 1:
						menu.printTopTweets(); // Top Tweets
						break;
					case 2:
						menu.printTopUsers(); // Top Users
						break;
					case 3:
						System.out.println("Give me your key word");
						keyword = input.nextLine();
						
						// Keyword cannot be null
						if(isValid(keyword)) {
							menu.textMatch(keyword);
						}else {
							System.out.println("It is null!");
						}
						
						break;
					case 4:
						System.out.println("Give me your key word");
						keyword = input.nextLine();
						
						// Keyword cannot be null
						if(isValid(keyword)) {
							menu.indexSearch(keyword);
						}else {
							System.out.println("It is null!");
						}
						
						break;
					case 5:
						loop = false;
						input.close();
						break;
				}
						
			}
		}
		
	}
	
	/**
	 * Checks if menu input option is valid.
	 *
	 * @param option
	 *            the option
	 * @return true, if is valid
	 */
	public static boolean isValid(String option) {
		if(option.isEmpty() || option.trim().length() == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Checks if input option is number. If this option is valid, return corresponding integer, else return 0.
	 *
	 * @param str
	 *            the str
	 * @return the int
	 */
	public static int isNumber(String str) {
		String reg = "^[0-9]+(.[0-9]+)?$";
		boolean isNum = str.matches(reg);

		if (!isNum) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}
	
	/**
	 * Prints the menu options.
	 */
	public static void printOptions() {
		System.out.println("\nWhich function you would like to choose?");
		System.out.println("\n1. Print top 10 Tweets and their contents");
		System.out.println("2. Print top 10 Users and their follower numbers");
		System.out.println("3. Search by text matching");
		System.out.println("4. Quick search");
		System.out.println("5. Quit");
	}

}
