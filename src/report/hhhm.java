package report;

import java.util.List;

import entities.HoaDon;
import entities.PhieuDatPhong;
import services.QuanLyHoaDon;
import services.QuanLyPhieuDatPhong;

public class hhhm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuanLyPhieuDatPhong ql = new QuanLyPhieuDatPhong();
		List<PhieuDatPhong> list = ql.dsPDP();
		for (PhieuDatPhong phieuDatPhong : list) {
			System.out.println(phieuDatPhong+"\n");
			
		}
		QuanLyHoaDon qlhd = new QuanLyHoaDon();
		List<HoaDon> hds = qlhd.getDSHD();
		for (HoaDon hoaDon : hds) {
			System.out.println(hoaDon);
			
		}

	}

}
