package main;

import java.util.Scanner;

public class Request extends Client{
	
	private int requestId = 0;
	private String visitPurpose;

	private final String[] VISIT_PURPOSES = {   "Consultation", 
												"Obtaining/issuing certificates",
												"Specialist visit by an appointment", 
												"Specialist visit without prior appointment", 
												"Other" };
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getVisitPurpose() {
		return visitPurpose;
	}


	public void setVisitPurpose() {
		System.out.println(".------------------------------------------------------------.");
		System.out.println("|              What is your purpose of visit?                |");

		for (int i = 0; i < VISIT_PURPOSES.length; i++) {
			System.out.printf("| %1$d - %2$-54s |\n", (i + 1), VISIT_PURPOSES[i]);
		}
		
		System.out.println("*------------------------------------------------------------*");
		System.out.print("Choose your visit purpose by selecting appropriate number: ");
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		int select = 0;
		String inputCheck = scan.nextLine();
		
		while(flag) {
			try {
				select = Integer.parseInt(inputCheck.trim());

				if (select < 1 || select > 5) {
					throw new NumberFormatException();
				}
				
				flag = false;

			} catch (NumberFormatException e) {
				System.out.println("\nIncorrectly selected option.");
				System.out.print("Please enter appropriate number: ");
				inputCheck = scan.nextLine();
			}
			
		}//END while(flag)
		
		this.visitPurpose = VISIT_PURPOSES[select - 1];
		
	}//END setVisitPurpose()
	
}