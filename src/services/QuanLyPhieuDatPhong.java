package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Database;
import entities.KhachHang;
import entities.NhanVien;
import entities.PhieuDatPhong;
import entities.Phong;

public class QuanLyPhieuDatPhong {
	private ArrayList<PhieuDatPhong> listPDP;
	public QuanLyPhieuDatPhong() {
		listPDP = new ArrayList<>();
	}
	
	public  ArrayList<PhieuDatPhong> dsPDP(){
		
		try {
				Database.getInstance();
				Connection con = Database.getConnection();
				String sql = "select * from PhieuDatPhong";
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				while(rs.next()) {
					int maPDB = rs.getInt(1);
					int maP = rs.getInt(2);
					int maKH = rs.getInt(3);
					int maNV = rs.getInt(4);
					Date ngayden = rs.getDate(5);
					Date ngaydi = rs.getDate(6);
					Date ngayLapPhieu = rs.getDate(7);
					Phong p = new Phong(maP);
					NhanVien nv = new NhanVien(maNV);
					KhachHang kh = new KhachHang(maKH);
					PhieuDatPhong pdp = new PhieuDatPhong(maPDB, ngayden, ngaydi, ngayLapPhieu, p, kh, nv);
					listPDP.add(pdp);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listPDP;
	}
	
	public static boolean delete(int mPDP) {
		Connection con = database.Database.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete from PhieuDatPhong where maPhieuDatPhong = ?");
			stmt.setInt(1, mPDP);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
//	public static boolean delete(int id) {
//		Connection con = database.Database.getInstance().getConnection();
//		String sql = "delete from PhieuDatPhong where maPhieuDatPhong = ?";
//		int i = 0;
//		try {
//			PreparedStatement pst = con.prepareStatement(sql);
//			pst.setInt(1, id);
//			pst.executeUpdate();
//			if (i != 0)
//				return true;
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
	public void delPass(int maNV) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm = con.prepareStatement("delete from PhieuDatPhong where maPhieuDatPhong = " + maNV+"");
		} catch (SQLException e) {
		}
	}
	public void updatePDP(int maPDP, String ngayDen, String ngayDi) {

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate("update PhieuDatPhong set ngayDen = '"+ngayDen+"'ngayDi = '"+ngayDi+"' where maPhieuDatPhong = "+maPDP+"");

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}
	

	
	public void updateNV(int ma, String ngayDi) {

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate("update PhieuDatPhong set ngayDi = '"+ngayDi+"' where maPhieuDatPhong = "+ma+"");

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}
	public void updateNV2(int ma, String ngayDi) {

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate("update PhieuDatPhong set ngayDen = '"+ngayDi+"' where maPhieuDatPhong = "+ma+"");

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

}
