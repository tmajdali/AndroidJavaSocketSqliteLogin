package h2test;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class H2class
{
	 public static void main(String[] args) throws IOException {
         
	       
	        
	        int portNumber = 3003;
	         while(true){
	        try (
	            ServerSocket serverSocket =
	                new ServerSocket(portNumber);
	            Socket clientSocket = serverSocket.accept();     
	            PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);                   
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
	        ) {
	        	System.out.println("waiting");
	            String inputLine= in.readLine();
	            System.out.println(inputLine);
	            if(isCorrect(inputLine)){
	            	System.out.println("sended correct");
	            	out.println("correct");
	            }
	            else{
	            	System.out.println("sended wrong");
	            	out.println("wrong");
	            }
	            
	                
	            
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	         }
	    }
	 public static boolean isCorrect(String word)
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      
	      
	      
	      System.out.println("name");
	      String s1 = word.substring(0, word.indexOf(";"));
	      
	      System.out.println("age");
	      String p1 = word.substring(word.indexOf(";")+1, word.length());
	      int y = Integer.parseInt(p1);

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	      while ( rs.next() ) {
	         
	         String  name = rs.getString("name");
	         if(name.equals(s1)){
	        	 int age  = rs.getInt("age");
	        	 if(age == y){
	        		 rs.close();
	       	         stmt.close();
	       	         c.close();
	        		 return true;
	        	 }
	        	 else{
	        		 rs.close();
	       	         stmt.close();
	       	         c.close();
	        		 return false;
	        	 }
	         }
	         
	        
	         
	      }
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
		return false;
	  }
	}