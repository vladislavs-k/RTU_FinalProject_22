package main;

import java.util.Scanner;

public class Console {

	private static boolean menuFlag;
	private static Scanner scan = new Scanner(System.in);

	public static void clientMenu(Request request) {

		menuFlag = true;

		while (menuFlag) {
			System.out.println("\n");
			System.out.println("**************************************************************");
			System.out.println("*                  Welcome to Client menu.                   *");
			System.out.println("**************************************************************");

			System.out.print("Enter your full name: ");
			request.setFullName(scan.nextLine());
			System.out.print("Phone number: ");
			request.setPhoneNumber(scan.nextLine());
			HandlingDB.writeClientToDB(request);
			System.out.println();

			request.setVisitPurpose();
			HandlingDB.writeRequestToDB(request);

			System.out.println();
			System.out.println("Your name is: " + request.getFullName());
			System.out.println("Your phone is: " + request.getPhoneNumber());
			System.out.println("Your visit purpose is: " + request.getVisitPurpose());
			System.out.println();

			wantToContinue();

		}
	}// END clientMenu()

	public static void operatorMenu(Operator operator, Request request) {

		menuFlag = true;
		String choice;

		while (menuFlag) {

			boolean operatorSubMenuFlag = true;

			System.out.println("\n");
			System.out.println("==============================================================");
			System.out.println("=                 Welcome to Operator menu.                  =");
			System.out.println("==============================================================");

			while (operatorSubMenuFlag) {
				System.out.println("Please select one of the following options:");
				System.out.print("Enter \"S\" to Show all requests, or enter \"F\" to select Find menu: ");
				choice = scan.nextLine().toUpperCase();

				switch (choice) {
				case "S":
					operator.getListOfAllRequests(request); // Show all list
					operatorSubMenuFlag = false;
					break;
				case "F":
					operator.findRequest(request); // Find menu
					operatorSubMenuFlag = false;
					break;
				default:
					System.out.println("\n\nInvalid entry. Please repeat your attempt!");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					
				}// END switch()
				
			} // END while (operatorSubMenuFlag)

			wantToContinue();
			
		}//END while (menuFlag)

	}//END operatorMenu()

	private static void wantToContinue() {
		
		boolean f = true;
		
		while (f) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("Do you want to continue? Enter \"Y\" for YES, or \"N\" for NO : ");

			String flag = scan.nextLine().toUpperCase();
			if (flag.equals("Y")) {
				f = false;
			} else if (flag.equals("N")) {
				f = false;
				menuFlag = false;
			} else
				System.out.println("\nInvalid entry. Please repeat your attempt!");
		}//END while (f)
		
	}//END wantToContinue()

}
