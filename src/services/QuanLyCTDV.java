package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import entities.CT_DichVu;

public class QuanLyCTDV {
	public ArrayList<CT_DichVu> dsCTDV;
	
	public QuanLyCTDV() {
		dsCTDV = new ArrayList<>();
	}
	
	public ArrayList<CT_DichVu> getDS(){
		return dsCTDV;
	}
	
	public boolean createCTDV(String maDV, String maHD, String slsd,String thoiGian) {
		System.out.println("21: "+thoiGian);
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
			"insert into CTDichVu(maDV, maHD, soLuongSD,thoiGianSD) values (?,?,?,?)");
			stmt.setInt(1, Integer.parseInt(maDV));
			stmt.setInt(2, Integer.parseInt(maHD));
			stmt.setInt(3,  Integer.parseInt(slsd));
			stmt.setNString(4,  thoiGian);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			if (e.getMessage().contains("Violation of PRIMARY KEY"))
				return false;
		}
		return n > 0;
	}
}
