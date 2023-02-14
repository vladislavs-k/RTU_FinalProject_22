package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HandlingDB {

	private static String pathDB = "jdbc:mysql://localhost:3306/rtuFP22";
	private static String user = "rtu_fp_22";
	private static String password = "rtu@final_project_22";
	
//BEGIN Checking client's phone number in a request
//and if it's a new client, add it to the "clients" table.
	public static void writeClientToDB(Request request) {
		
		HashMap<Integer, Client> clientList = new HashMap<>(); // Integer - key (phone number). Client - value;
		
		try {
			Connection conn = DriverManager.getConnection(pathDB, user, password);

			Statement stmt = conn.createStatement();
			String query;
			ResultSet rs;
			
		//BEGIN Getting client list
			query = "SELECT * FROM clients";
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Client clientInfo = new Client();
				
				clientInfo.setClientId(rs.getInt("cli_id"));
				clientInfo.setFullName(rs.getString("full_name"));
				clientInfo.setPhoneNumber(String.valueOf(rs.getInt("phone_number")));

				clientList.put(clientInfo.getPhoneNumber(), clientInfo);
			}
		//END Getting client list
			
			
		//BEGIN Check if the phone number in the request is unique
			boolean checkPhNum = true;
			do {
				//If phone number already exist in database:
				if (clientList.containsKey(request.getPhoneNumber())) {
					Client client = clientList.get(request.getPhoneNumber());
					//And if request's phone number and full name already exist in database:
					if (request.getFullName().equals(client.getFullName())) {
						//Set current request's client id to the existing client from database:
						request.setClientId(client.getClientId());
						checkPhNum = false;
					}
					//Otherwise if the combination of the request's [phone number and full name] differs from database:
					else {
						System.out.printf("\nYour entry is: [FULL NAME: %s] ", request.getFullName());
						System.out.printf("[PHONE NUMBER: %d]\n", request.getPhoneNumber());
						
						System.out.println("Such phone number is already assigned to another client!");
						System.out.println("Re-check your full name and phone number, or use another phone number.");
						
						@SuppressWarnings("resource")
						Scanner scan = new Scanner(System.in);
						
						System.out.print("Please repeat your full name entry: ");
						request.setFullName(scan.nextLine());
						
						System.out.print("Please repeat your phone number entry: ");
						request.setPhoneNumber(scan.nextLine());
					}
				}
				//If there is no such phone number in the database, add new client to the database:
				else {
					query = "INSERT INTO clients (full_name, phone_number) VALUES (?, ?)";
					
					PreparedStatement  prepstmt = conn.prepareStatement(query);
					prepstmt.setString(1, request.getFullName());
					prepstmt.setInt(2, request.getPhoneNumber());
					prepstmt.execute();
					
				// BEGIN Retrieve new client's id for the current request
					query = "SELECT cli_id FROM clients WHERE phone_number = " + request.getPhoneNumber();
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						request.setClientId(rs.getInt("cli_id"));
					}
				// END Retrieve new client's id for the current request
					
					checkPhNum = false;
				}
					
			}while (checkPhNum);
		//END Check if the phone number in the request is unique
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		
	}
//END Checking the clients's phone number in a request
	
//BEGIN Adding request to database
	public static void writeRequestToDB(Request request) {
		
		try {
			Connection conn = DriverManager.getConnection(pathDB, user, password);

			String query = "INSERT INTO requests (client_id, visit_purpose) VALUES (?, ?)";
			
			PreparedStatement  prepstmt = conn.prepareStatement(query);
			prepstmt.setInt(1, request.getClientId());
			prepstmt.setString(2, request.getVisitPurpose());
			prepstmt.execute();
			
			prepstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}

	}
//END Adding request to database

//BEGIN Retrieve all requests from database
	public static ArrayList<String[]> getSplitedRequestListFromDB() {
		
		ArrayList<String[]> requestList = new ArrayList<>();
		String strSelect;

			strSelect = "SELECT requests.req_id, clients.full_name, clients.phone_number, requests.visit_purpose "
					+ 	"FROM requests " 
					+ 	"INNER JOIN clients ON requests.client_id=clients.cli_id "
					+ 	"ORDER BY requests.req_id";
			
			requestList = getSplitedResultListBySelectStatement(strSelect);

		return requestList;
	}
//END Retrieve all requests from database

//BEGIN Searching requests in database
	public static ArrayList<String[]> findRequetsInDB(String[] findRequest) {
		
		ArrayList<String[]> matchFind = new ArrayList<>();
		String strSelect;
		
	//The request's id
		if(findRequest[0].isBlank())
			findRequest[0] = "requests.req_id";
		//Preventing SQL exception, because of possible non integer value of "request id":
		else if (!findRequest[0].matches("\\d+")) {
			findRequest[0] = "0";
		}
		
	//Client's full name in the request
		if(findRequest[1].isBlank())
			findRequest[1] = "clients.full_name";
		else
			findRequest[1] = "'" + findRequest[1] + "'";
		
	//Client's phone number in the request
		if(findRequest[2].isBlank())
			findRequest[2] = "clients.phone_number";
		//Preventing SQL exception, because of possible non integer value of "request id":
		else if (!findRequest[2].matches("\\d+"))
			findRequest[2] = "0";
		
	//Client's visit purpose in the request
		if(findRequest[3].isBlank())
			findRequest[3] = "requests.visit_purpose";
		else
			findRequest[3] = "'" + findRequest[3] + "'";
		
			strSelect = "SELECT requests.req_id, clients.full_name, clients.phone_number, requests.visit_purpose "
					+ 	"FROM requests " 
					+ 	"INNER JOIN clients ON requests.client_id=clients.cli_id "
					+	"WHERE requests.req_id = " + findRequest[0] + " "
					+	"AND clients.full_name = " + findRequest[1] + " "
					+	"AND clients.phone_number = " + findRequest[2] + " "
					+	"AND requests.visit_purpose = " + findRequest[3] + " "
					+	"ORDER BY requests.req_id";
			
			matchFind = getSplitedResultListBySelectStatement(strSelect);

		return matchFind;
		
	}
//END Searching requests in database
	

	private static ArrayList<String[]> getSplitedResultListBySelectStatement(String strSelect){
		
		ArrayList<String[]> returnResult = new ArrayList<>();
		
		try {
			Connection conn = DriverManager.getConnection(pathDB, user, password);;

			Statement stmt = conn.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery(strSelect);

			while (rs.next()) {
				String[] singleRequest = new String[4];

				singleRequest[0] = String.valueOf(rs.getInt("req_id"));
				singleRequest[1] = rs.getString("full_name");
				singleRequest[2] = String.valueOf(rs.getInt("phone_number"));
				singleRequest[3] = rs.getString("visit_purpose");

				returnResult.add(singleRequest);
			}
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		
		return returnResult;
		
	}//END getSplitedResultListBySelectStatement()
	
}
