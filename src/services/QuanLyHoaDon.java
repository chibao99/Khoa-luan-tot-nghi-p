package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import database.Database;
import entities.CT_HD;
import entities.HoaDon;
import entities.KhachHang;
import entities.NhanVien;
import entities.PhieuDatPhong;
import entities.Phong;
import entities.QLHoaDon;

public class QuanLyHoaDon {
	public ArrayList<HoaDon> dsHD;

	public QuanLyHoaDon() {
		dsHD = new ArrayList<>();
	}

	public ArrayList<HoaDon> getDS() {
		return dsHD;
	}

	public ArrayList<HoaDon> getDSHD() {

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from HoaDon";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int maHD = rs.getInt(1);
				int maNV = rs.getInt(2);
				int tongTien = rs.getInt(3);
				Date ngayLap = rs.getDate(4); 
				int tinhTrang = rs.getInt(5);
				HoaDon hd = new HoaDon(maHD, maNV, tongTien, ngayLap, tinhTrang);
				dsHD.add(hd);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHD;
	}

	public ArrayList<QLHoaDon> getHD() {
		ArrayList<QLHoaDon> list = new ArrayList<QLHoaDon>();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select hd.maHD, hd.maNV, p.ngayDen, p.ngayDi, hd.tinhTrangHoaDon, hd.tongTien, p.maPhong, k.hoTen, k.sDT\r\n"
					+ "from HoaDon hd join PhieuDatPhong p on hd.maHD = p.maPhieuDatPhong\r\n"
					+ "			   join KhachHang k on p.maKH = k.maKH";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int maHD = rs.getInt(1);
				int maNV = rs.getInt(2);
				Date ngayDen = rs.getDate(4);
				Date ngayDi = rs.getDate(3);
				int tthd = rs.getInt(5);
				int tongtien = rs.getInt(6);
				int maPhong = rs.getInt(7);
				String hoten = rs.getString(8);
				String sdt = rs.getString(9);
				QLHoaDon qlHoaDon = new QLHoaDon(maHD, maNV, ngayDen, ngayDi, tthd, tongtien, maPhong, hoten, sdt);
				list.add(qlHoaDon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * maHD = maDatPhong tongTien = giaPhong * (ngaydi - ngayden) ngayLapHD = null
	 * tinhTrangPhieu = 0
	 */
	public boolean createHD(String maHD, String maNV, double tongTien, String ngayLapHD, int tinhTrangHD) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"insert into HoaDon (maHD, maNV, tongTien, ngayLapHD, tinhTrangHoaDon) values (?,?,?,?,?)");
			stmt.setInt(1, Integer.parseInt(maHD));
			stmt.setInt(2, Integer.parseInt(maNV));
			stmt.setDouble(3, tongTien);
			stmt.setDate(4, java.sql.Date.valueOf(ngayLapHD));
			stmt.setInt(5, tinhTrangHD);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			if (e.getMessage().contains("Violation of PRIMARY KEY"))
				return false;
		}
		return n > 0;
	}

	public boolean update(String tongTien, String maHD) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"update HoaDon set tongTien += ?, ngayLapHD = ?, tinhTrangHoaDon = 1 where maHD = ? ");
			stmt.setDouble(1, Double.parseDouble(tongTien));
			stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stmt.setInt(3, Integer.parseInt(maHD));
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public HoaDon timTheoMa(String maPhieu) {
		HoaDon hd = null;
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = " select * from HoaDon where maHD = " + maPhieu;
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				hd = new HoaDon(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDate(4), rs.getInt(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hd;
	}

	public static boolean delete(int mPDP) {
		Connection con = database.Database.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete from HoaDon where maHD = ?");
			stmt.setInt(1, mPDP);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public ArrayList<QLHoaDon> findOrderByDate(String ngayDen, String ngayDi) {
		ArrayList<QLHoaDon> list = new ArrayList<QLHoaDon>();
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("EXEC QueryHoaDon_FromDateToDate ?,?");
			java.sql.Date date = java.sql.Date.valueOf(ngayDen);
			java.sql.Date date1 = java.sql.Date.valueOf(ngayDi);
			stmt.setDate(1, date);
			stmt.setDate(2, date1);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int maHD = rs.getInt(1);
				int maNV = rs.getInt(2);
				Date ngayDe = rs.getDate(3);
				Date ngayD = rs.getDate(4);
				int tthd = rs.getInt(5);
				int tongtien = rs.getInt(6);
				int maPhong = rs.getInt(7);
				String hoten = rs.getString(8);
				String sdt = rs.getString(9);
				QLHoaDon qlHoaDon = new QLHoaDon(maHD, maNV, ngayDe, ngayD, tthd, tongtien, maPhong, hoten, sdt);
				list.add(qlHoaDon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<CT_HD> getCTHD(int id) {
		ArrayList<CT_HD> list = new ArrayList<CT_HD>();
		CT_HD cthd = new CT_HD();
		Database.getInstance();
		Connection con = Database.getConnection();
		System.out.println("start get CTHD");
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("EXEC QueryCTHoaDon ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String tenDV = rs.getString(1);
				int sl = rs.getInt(2);
				String time = rs.getString(3);
				int gia = rs.getInt(4);
				cthd = new CT_HD(tenDV, sl, time, gia);
				list.add(cthd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
