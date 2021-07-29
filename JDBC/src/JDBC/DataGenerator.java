package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataGenerator {
	
	public static void main(String args[]) throws SQLException {
		String query = "SELECT * FROM courseoffering";
		
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
			Random ran = new Random();
			String [] seas = {"Fall", "Winter", "Spring", "Summer"};
			for(int i=0;i<50;i++) {
				int pk1=(1000+ran.nextInt(8999));
				ResultSet result = stmt.executeQuery("SELECT * FROM instructor WHERE Staff_ID="+pk1);
				if(result.next())
					continue;
				String indata = "INSERT INTO instructor VALUES ("+pk1+",'"+ranStr(4 + ran.nextInt(7))+"')";
				stmt.executeUpdate(indata); 
			}
			String [] depts = {"Math","Literature","MechE","ChemE","CS","CompE","Physics","Chem","Biology","Business"};
			for(int i=0;i<depts.length;i++) {
				int pk1=(1000+ran.nextInt(8999));
				ResultSet result = stmt.executeQuery("SELECT * FROM department WHERE Department_ID="+pk1);
				if(result.next())
					continue;
				result = stmt.executeQuery("SELECT Staff_ID FROM instructor ORDER BY RAND() LIMIT 1");
				result.next();
				String pk2s= result.getString(1);
				int pk2= Integer.parseInt(pk2s);
				result = stmt.executeQuery("SELECT * FROM department WHERE DepartmentHead_ID="+pk2);
				if(result.next())
					continue;
				String indata = "INSERT INTO department VALUES ("+pk1+","+pk2+",'"+depts[i]+"')";
				stmt.executeUpdate(indata); 
			}
			ResultSet result = stmt.executeQuery("SELECT Staff_ID FROM instructor");
			ArrayList<Integer> al1 = new ArrayList<Integer>();
			HashSet<Integer> hs1 = new HashSet<Integer>();
			
			while(result.next()) {
				String pk1s= result.getString(1);
				int pk1= Integer.parseInt(pk1s);
				if(hs1.contains(pk1))
					continue;
				al1.add(pk1);
				hs1.add(pk1);}
				
			for(int i=0; i<al1.size();i++) {
				ResultSet result2 = stmt.executeQuery("SELECT Department_ID FROM department ORDER BY RAND() LIMIT 1");
				result2.next(); 
				
				String pk2s= result2.getString(1);
				int pk2= Integer.parseInt(pk2s);
				
				String indata = "INSERT INTO departmentaffiliations VALUES ("+pk2+","+al1.get(i)+")";
				stmt.executeUpdate(indata); 
			}
			for(int i=0;i<50;i++) {
				int pk1=(1000+ran.nextInt(8999));
				result = stmt.executeQuery("SELECT Course_ID FROM course WHERE Course_ID="+pk1);
				if(result.next())
					continue;
				String indata = "INSERT INTO course VALUES ("+pk1+","+(1+ran.nextInt(3))+",'"+ranStr(4 + ran.nextInt(7))+"')";
				stmt.executeUpdate(indata); 
			} 
			for(int i=0;i<50;i++) {
				int pk1=(1000+ran.nextInt(8999));
				result = stmt.executeQuery("SELECT ClassCode FROM courseoffering WHERE ClassCode="+pk1);
				if(result.next())
					continue;
				String indata = "INSERT INTO courseoffering VALUES ("+pk1+",'"+seas[(ran.nextInt(4))]+"',"+(10+ran.nextInt(20))+")";
				stmt.executeUpdate(indata); 
				result = stmt.executeQuery("SELECT Course_ID FROM course ORDER BY RAND() LIMIT 1");
				result.next();
				String pk2s= result.getString(1);
				int pk2= Integer.parseInt(pk2s);
				String indata2 = "INSERT INTO hasoffering VALUES ("+pk1+","+pk2+")";
				stmt.executeUpdate(indata2);
			} 
			result = stmt.executeQuery("SELECT ClassCode FROM courseoffering");
			al1 = new ArrayList<Integer>();
			hs1 = new HashSet<Integer>();
			while(result.next()) {
				String pk1s= result.getString(1);
				int pk1= Integer.parseInt(pk1s);
				if(hs1.contains(pk1))
					continue;
				al1.add(pk1);
				hs1.add(pk1);}
				
			for(int i=0; i<al1.size();i++) {
				ResultSet result2 = stmt.executeQuery("SELECT Staff_ID FROM instructor ORDER BY RAND() LIMIT 1");
				result2.next(); 
				
				String pk2s= result2.getString(1);
				int pk2= Integer.parseInt(pk2s);
				
				String indata = "INSERT INTO teaches VALUES ("+al1.get(i)+","+pk2+")";
				stmt.executeUpdate(indata); 
			} 
			for(int i=0;i<50;i++) {
				int pk1=(1000+ran.nextInt(8999));
				result = stmt.executeQuery("SELECT * FROM instructor WHERE Staff_ID="+pk1);
				if(result.next())
					continue;
				String indata = ("INSERT INTO student VALUES ('"+ranStr(4 + ran.nextInt(7))+"','"
+ ranStr(4 + ran.nextInt(7))+"','"+ranStr(4 + ran.nextInt(7))+"',"+pk1+")");
				stmt.executeUpdate(indata); 
			} 
			for(int j=0; j<5;j++) {
			ResultSet result3 = stmt.executeQuery("SELECT Student_ID FROM student");
			al1 = new ArrayList<Integer>();
			hs1 = new HashSet<Integer>();
			while(result3.next()) {
				String pk1s= result3.getString(1);
				int pk1= Integer.parseInt(pk1s);
				if(hs1.contains(pk1))
					continue;
				al1.add(pk1);
				hs1.add(pk1);}
				
			for(int i=0; i<al1.size();i++) {
				ResultSet result4 = stmt.executeQuery("SELECT ClassCode FROM courseoffering ORDER BY RAND() LIMIT 1");
				result4.next(); 
				String pk2s= result4.getString(1);
				int pk2= Integer.parseInt(pk2s);
				result = stmt.executeQuery("SELECT ClassCode FROM registrations WHERE ClassCode="+pk2+" AND Student_ID="+al1.get(i));
				if(result.next())
					continue;
				String dt = (2000+ran.nextInt(22))+"-"+(1+ran.nextInt(12))+"-"+(1+ran.nextInt(28));
				String indata = "INSERT INTO registrations VALUES ("+pk2+","+al1.get(i)+",'"+dt+"')";
				stmt.executeUpdate(indata); 
			} }
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String ranStr(int len) {
        Random ran = new Random();
		String alp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] alpArr = alp.toCharArray();
        String str = "";
        for(int i=0; i<len;i++)
        	str += alpArr[ran.nextInt(26)];
        return str;
    }
}