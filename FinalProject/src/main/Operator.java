package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Operator {

	public void getListOfAllRequests(Request request) {
		System.out.println();
		System.out.println(".------------------------------------------------------------------------------------.");
		System.out.println("|                                List of all requests:                               |");
		printRequests(HandlingDB.getSplitedRequestListFromDB());
	}// END getListOfAllRequests()

// BEGIN Method to find request
	public void findRequest(Request request) {

	// BEGIN What to find
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		//"findRequest" is an array which forms finding pattern where:
		// index 0 = id; index 1 = fullName;
		// index 2 = phoneNumber; index 3 = visitPurpose
		String[] findRequest = new String[4];

		System.out.println();
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("<                         Find menu.                         >");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		System.out.print("Enter request's id to find (or leave field empty to skip): ");
		findRequest[0] = scan.nextLine();
		System.out.print("Enter client's full name to find (or leave field empty to skip): ");
		findRequest[1] = scan.nextLine();
		System.out.print("Enter client's phone to find (or leave field empty to skip): ");
		findRequest[2] = scan.nextLine();
		System.out.print("Enter client's visit purpose to find (or leave field empty to skip): ");
		findRequest[3] = scan.nextLine();
	// END What to find

	// BEGIN Execute searching process
		ArrayList<String[]> matchFind = HandlingDB.findRequetsInDB(findRequest);
	// END Execute searching process
		
	// BEGIN Print out found requests
		if (!matchFind.isEmpty()) {
			System.out.println();
			System.out.println(".------------------------------------------------------------------------------------.");
			System.out.println("|                                Find request results:                               |");
			printRequests(matchFind);
		} else {
			System.out.println();
			System.out.println(".------------------------------------------------------------.");
			System.out.println("|   There is no matching, according to your find request!    |");
			System.out.println("*------------------------------------------------------------*");
		} // END Print out found requests

	}
// END Method to find request

// BEGIN Method to print out a list of requests
	private static void printRequests(ArrayList<String[]> requestList) {
		System.out.println("|------------------------------------------------------------------------------------|");
		System.out.println("| Id  | Client's full name   | Phone Nr | Visit type                                 |");
		System.out.println("|------------------------------------------------------------------------------------|");
		for (String[] req : requestList) {
				System.out.printf("| %-3s | %-20s | %8s | %-42s |", req[0], req[1], req[2], req[3]);
			System.out.println();
		}
		System.out.println("*------------------------------------------------------------------------------------*");
	}
// END Method to print out a list of requests

}// END class Operator
