package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UniDBApp {
	public static void main(String args[]) throws SQLException {
	
	Scanner scan = new Scanner(System.in);

	String query = "SELECT * FROM ";
	
	String url = "jdbc:mysql://localhost:3306/unidb";
	String  user = "root";
	String pass = "Password1";
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	try {
		Connection con = DriverManager.getConnection(url, user, pass);
		Statement stmt = con.createStatement();
	while(true) {
		System.out.println("Welcome to the University Database Administration Application! Options:");
		System.out.println("Would you like to view whole entries (1) or view specific entries (2) or add student (3) or remove student (4) or choose any other number to quit");
		int op1 = scan.nextInt();
		if(op1==1) {
		System.out.println("1:View Course Offerings, 2:View Registrations,3:View Department Affiliations,4:View Courses,5:View Students");
		int op2 = scan.nextInt();
		if(op2==1) {
			ResultSet result = stmt.executeQuery(query+"courseoffering");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=3; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		else if(op2==2) {
			ResultSet result = stmt.executeQuery(query+"registrations");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=3; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		else if(op2==3) {
			ResultSet result = stmt.executeQuery(query+"departmentaffiliations");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=2; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		else if(op2==4) {
			ResultSet result = stmt.executeQuery(query+"course");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=3; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		else {
			ResultSet result = stmt.executeQuery(query+"student");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=4; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		 }
		else if(op1==2) {
		System.out.println("1:Find instructor with most courses,2:Find Courses not offered");
		int op2 = scan.nextInt();
		if(op2==1) {
			ResultSet result = stmt.executeQuery("SELECT Staff_ID AS Mode, COUNT(*) AS Count FROM teaches GROUP BY Staff_ID HAVING COUNT(*) >= ALL (SELECT COUNT(*) FROM teaches GROUP BY Staff_ID)");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=1; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		else if(op2==2) {
			ResultSet result = stmt.executeQuery("SELECT Course_ID FROM course WHERE Course_ID NOT IN (SELECT Course_ID FROM hasoffering)");
			
			while(result.next()) {
				String UniData = "";
				for(int i=1; i<=1; i++) {
					UniData += result.getString(i) + ":";
				}
				System.out.println(UniData);
			}
		}
		}
		else if (op1==3) {
			System.out.println("Enter ID to add");
			int id=scan.nextInt();
			System.out.println("Enter Name");
			String s1=scan.next();
			System.out.println("Enter Major");
			String s2=scan.next();
			System.out.println("Enter Minor");
			String s3=scan.next();
			String indata = ("INSERT INTO student VALUES ('"+ s1+"','"
					+s2+"','"+s3+"',"+id+")");
			stmt.executeUpdate(indata);
			}
		else if (op1==4) {
			System.out.println("Enter ID to remove");
			int id=scan.nextInt();
			stmt.executeUpdate("DELETE FROM STUDENT WHERE Student_ID="+id);
		}
		else {
			break;
		}
	}
	} catch(SQLException s) {
		s.printStackTrace();
	}
}
}