package main;

import java.util.Scanner;

public class Client {
	
	private int clientId;
	private String fullName;
	private int phoneNumber;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = isFullName(fullName);
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = isNumber(phoneNumber);
	}

	private String isFullName(String fullName) {
		
		boolean flag = true;

		while (flag) {
			// Delete spaces before and after entered text - trim; Find that entered expression matches regex - matches.
			if (fullName.trim().matches("[a-zA-Z]+\s[a-zA-Z]+")) { 
				flag = false;
			} else {
				System.out.print("\nIncorrect full name entry. ");
				System.out.println("Please write your first and last name, use only english letters and only one space in between!");
				System.out.print("Repeat your full name entry: ");
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				fullName = scan.nextLine();
			} 
			
		} // END while loop
		
		return fullName;
		
	}//END isFullName()

	private int isNumber(String phoneNumber) {
		
		boolean flag = true;
		int phNum = 0;
		
		while (flag) {
			try {
				phNum = Integer.parseInt(phoneNumber.trim());

				if ((phNum >= 20000000 && phNum <= 29999999) || (phNum >= 60000000 && phNum <= 69999999)) {
					flag = false;
				} else
					throw new NumberFormatException();

			} catch (NumberFormatException e) {
				System.out.println("\nIncorrect phone number format!");
				System.out.println("Phone number should be 2xxxxxxx or 6xxxxxxx, where 'x' is number.");
				System.out.print("Please, repeat your phone number entry: ");
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				phoneNumber = scan.nextLine();
			}

		} // END while (flag)

		return phNum;

	}// END isNumber()

}
