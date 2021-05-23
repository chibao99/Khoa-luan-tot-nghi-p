package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
	public static Connection con=null;
	public static Database instance=new Database();
	public static Database getInstance() {
		return instance;
	}
	
	public static  void connect() {
		String url="jdbc:sqlserver://103.125.170.20:10032;DatabaseName=QLKS";
		String user="sa";
		String password="Goboi123";
		try {
			con = DriverManager.getConnection(url,user,password);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void disconnect() {
		if(con!=null) {
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static Connection getConnection() {
		if(con == null)
			connect();
		return con;
	}

}
