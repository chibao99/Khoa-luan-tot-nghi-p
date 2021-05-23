package entities;

import java.sql.Date;

public class CT_HD {
	private String tenDV;
	private int soluongSD;
	private String tgSD;
	private double giaDV;

	public String getTenDV() {
		return tenDV;
	}

	public void setTenDV(String tenDV) {
		this.tenDV = tenDV;
	}

	public int getSoluongSD() {
		return soluongSD;
	}

	public void setSoluongSD(int soluongSD) {
		this.soluongSD = soluongSD;
	}

	public String getTgSD() {
		return tgSD;
	}

	public void setTgSD(String tgSD) {
		this.tgSD = tgSD;
	}

	public double getGiaDV() {
		return giaDV;
	}

	public void setGiaDV(double giaDV) {
		this.giaDV = giaDV;
	}

	public CT_HD(String tenDV, int soluongSD, String tgSD, double giaDV) {
		super();
		this.tenDV = tenDV;
		this.soluongSD = soluongSD;
		this.tgSD = tgSD;
		this.giaDV = giaDV;
	}

	public CT_HD() {
		super();
	}

	@Override
	public String toString() {
		return "CT_HD [tenDV=" + tenDV + ", soluongSD=" + soluongSD + ", tgSD=" + tgSD + ", giaDV=" + giaDV + "]";
	}

}
