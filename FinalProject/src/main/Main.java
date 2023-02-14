/* Operator and Client query-relationships. [Riga, June 2022]

 This program was created according to "Mācību programmas “Java programmēšanas valoda” IT PROJEKTS"
 and implements a simulator for Operator and Client query-relationships.
 Here you can see functionality for both sides (Client and Operator), 
 their possible optional actions, as well as visual output preview in console.

 This program includes the following features:
 	Main menu: Provides option to select either the Client menu or the Operator menu.
		1)Client menu: Provides opportunity to fill in client's request, checks clients input data,
		  saves requests in the database with the unique id number and as well provides repetitive input of numerous requests.
		2)Operator menu: Provides opportunity for operator to retrieve and to show all the requests from the database and
		  as well to search specific request (or requests) by searching through one or multiple fields in the database.

 You can look up the description of the IT project task in:
 "../FinalProject/TaskAndSQL/uzdevums_IT_projektam.pdf"

 And also you can re-create database and it's tables with the help of:
 "../FinalProject/TaskAndSQL/DB_queries_for_FinalProject.sql"
 
 */

package main;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Request request = new Request();
		Operator operator = new Operator();
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		String mainMenu;
		
		while(true) {
			System.out.println();
			System.out.println("##############################################################");
			System.out.println("#               Main menu. Choose your option.               #");
			System.out.println("##############################################################");
			System.out.print("Enter \"1\" for Client menu, or enter \"2\" for Operator menu: ");
			mainMenu = scan.next();
			
			switch (mainMenu) {
			case "1" : Console.clientMenu(request); break;
			case "2" : Console.operatorMenu(operator, request); break;
			}
			
		}// END while(true)
	}// END main()
}//END class Main
