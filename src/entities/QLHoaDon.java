package entities;

import java.sql.Date;

public class QLHoaDon {
	private int maHD;
	private int maNV;
	private Date ngayDen;
	private Date ngayDi;
	private int tinhTrangHD;
	private double tongTien;
	private int maPhong;
	private String hoTen;
	private String sDT;
	public int getMaHD() {
		return maHD;
	}
	public void setMaHD(int maHD) {
		this.maHD = maHD;
	}
	public int getMaNV() {
		return maNV;
	}
	public void setMaNV(int maNV) {
		this.maNV = maNV;
	}
	public Date getNgayDen() {
		return ngayDen;
	}
	public void setNgayDen(Date ngayDen) {
		this.ngayDen = ngayDen;
	}
	public Date getNgayDi() {
		return ngayDi;
	}
	public void setNgayDi(Date ngayDi) {
		this.ngayDi = ngayDi;
	}
	public int getTinhTrangHD() {
		return tinhTrangHD;
	}
	public void setTinhTrangHD(int tinhTrangHD) {
		this.tinhTrangHD = tinhTrangHD;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	public int getMaPhong() {
		return maPhong + 100;
	}
	public void setMaPhong(int maPhong) {
		this.maPhong = maPhong;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getsDT() {
		return sDT;
	}
	public void setsDT(String sDT) {
		this.sDT = sDT;
	}
	public QLHoaDon(int maHD, int maNV, Date ngayDen, Date ngayDi, int tinhTrangHD, double tongTien, int maPhong,
			String hoTen, String sDT) {
		super();
		this.maHD = maHD;
		this.maNV = maNV;
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
		this.tinhTrangHD = tinhTrangHD;
		this.tongTien = tongTien;
		this.maPhong = maPhong;
		this.hoTen = hoTen;
		this.sDT = sDT;
	}
	public QLHoaDon() {
		super();
	}
	@Override
	public String toString() {
		return "QLHoaDon [maHD=" + maHD + ", maNV=" + maNV + ", ngayDen=" + ngayDen + ", ngayDi=" + ngayDi
				+ ", tinhTrangHD=" + tinhTrangHD + ", tongTien=" + tongTien + ", maPhong=" + maPhong + ", hoTen="
				+ hoTen + ", sDT=" + sDT + "]";
	}
	

}
