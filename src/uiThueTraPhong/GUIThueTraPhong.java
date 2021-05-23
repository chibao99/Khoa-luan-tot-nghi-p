package uiThueTraPhong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;


import com.toedter.calendar.JDateChooser;

import database.Database;
import entities.Phong;
import services.QuanLyNhanVien;
import services.QuanLyPhong;
import services.QuanLyThueTra;
import uiLogin.GUIMenu;


public class GUIThueTraPhong extends JPanel implements ActionListener, MouseListener {
	JButton btnBack, btnQLPhong, btnQLNV, btnLogin, btnBaoCao, btnDatPhong, btnTraPhong;//, btnQLDon, btnDSKH,btnDichVu;
	private static JButton[] phong;
	JLabel lbDS, lbTongSoPhong, lbSapXep, lblTitle;
	public JTextField txtTongSoPhong, txtTim, txtMaNV, txtTenNV, txtDay;
	
	JPanel pbox, pbox2, pbox1;
	Box b3;
	JLabel lbTim,lbMaNV, lbTenNV, lbDay, lbHieu, lbDang, lbLinh, lbMaHieu, lbMaLinh, lbMaDang;
	JScrollPane scroll;
	JComboBox<String> cmbSapXep, cmbTimKiem;
	GridLayout test;
	public static String maPhong;
	// Quản Lý Phòng 
	QuanLyPhong ql = new QuanLyPhong();
	//Quản Lý Nhân Viên
	QuanLyNhanVien qlNV = new QuanLyNhanVien();
	//Quản Lý Phiếu Đặt Phong
	QuanLyThueTra qltt = new QuanLyThueTra();
	private GUIMenu parent;
	private JLabel lbFindFrom;
	private JDateChooser jdcFrom;
	private JLabel lbFindTo;
	private JDateChooser jdcTo;
	private JButton btnTimNgay;
	
	public GUIThueTraPhong(GUIMenu parent) {
	this.parent = parent;
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	Box bc, b1, b2,b4;
	bc = Box.createVerticalBox();
	add(bc);
	bc.setPreferredSize(new Dimension(1365, 500));	
	bc.add(b1 = Box.createHorizontalBox());
		b1.add(b2 = Box.createVerticalBox());			
			b2.add(Box.createVerticalStrut(10));
			b2.add(b3 = Box.createHorizontalBox());
//			    b3.add(Box.createHorizontalStrut(10));
				b3.add(lbSapXep = new JLabel("Sắp Xếp: "));
				lbSapXep.setFont(font1);
				lbSapXep.setForeground(Color.BLACK);
				b3.add(Box.createHorizontalStrut(10));
				cmbSapXep = new JComboBox<String>();
				cmbSapXep.addItem("");
				cmbSapXep.addItem("Sắp Xếp Theo Giá Phòng Tăng");
				cmbSapXep.addItem("Sắp Xếp Theo Giá Phòng Giảm");
				b3.add(cmbSapXep);
				b3.add(Box.createHorizontalStrut(200));
				b3.add(lbTim = new JLabel("Tình Trạng Phòng: "));
				lbTim.setFont(font1);
				lbTim.setForeground(Color.BLACK);
				b3.add(Box.createHorizontalStrut(10));
				cmbTimKiem = new JComboBox<String>();
				cmbTimKiem.addItem("");
				cmbTimKiem.addItem("---Trống---");
				cmbTimKiem.addItem("---Đang Sử Dụng---");
				cmbTimKiem.addItem("---Đến Hạn Check In---");
				cmbTimKiem.addItem("---Đến Hạn Check Out---");
				b3.add(cmbTimKiem);
				b3.add(Box.createHorizontalStrut(150));
				b3.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon(".\\image\\btn-back.png")));
					btnBack.setBackground(Color.WHITE);
				b3.add(Box.createHorizontalStrut(350));
			b2.add(Box.createVerticalStrut(20));
			b2.add(b3 = Box.createHorizontalBox());
				b3.add(lbTongSoPhong = new JLabel("Tổng Số Phòng: "));
				lbTongSoPhong.setFont(font1);
				lbTongSoPhong.setForeground(Color.BLACK);
				b3.add(Box.createHorizontalStrut(20));
				txtTongSoPhong = new JTextField(50);
				txtTongSoPhong.setText(String.valueOf(ql.tongSoPhong()));
//				b3.add(txtTongSoPhong = new JTextField("" + ql.tongSoPhong()));
				b3.add(txtTongSoPhong);
				txtTongSoPhong.setEnabled(false);
				txtTongSoPhong.setFont(font1);
				pbox = new JPanel();
				b3.add(Box.createHorizontalStrut(200));
				pbox.setLayout(new GridLayout(1, 3));
				pbox.add(pbox1 = new JPanel());
				pbox1.setBackground(Color.blue);
				pbox.add(new JLabel("Đang Sử Dụng"));//3
				// pbox1.add(Box.createHorizontalStrut(5));
				pbox.add(pbox1 = new JPanel());
				pbox1.setBackground(Color.red);//2
				pbox.add(new JLabel("Đến Hạn Check In"));
				// pbox1.add(Box.createHorizontalStrut(5));		
				pbox.add(pbox1 = new JPanel());
				pbox1.setBackground(Color.CYAN);
				pbox.add(new JLabel("Đến Hạn Check Out"));//4
//				 pbox1.add(Box.createHorizontalStrut(10));		
				pbox.add(pbox1 = new JPanel());
				pbox1.setBackground(Color.gray);
				pbox.add(new JLabel("Phòng Trống")); //0
				// pbox.add(Box.createHorizontalStrut(150));
				b3.add(pbox);
				b3.add(Box.createHorizontalStrut(150));
			b2.add(Box.createVerticalStrut(20));
			b2.add(b3 = Box.createHorizontalBox());
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED),
					"Danh Sách Phòng");
			title.setTitleFont(font1);
			title.setTitleColor(Color.BLACK);
			b3.setBorder(title);
			callListPhong(pbox2, b3, listPhong());
			bc.add(Box.createVerticalStrut(10));
            bc.add(b4 = Box.createHorizontalBox());
			b4.add(lbFindFrom = new JLabel("Ngày Đến: "));
			lbFindFrom.setFont(font1);
			lbFindFrom.setForeground(Color.BLACK);
			b4.add(Box.createHorizontalStrut(20));
			String dateFrom="yyyy-MM-dd";
			b4.add(jdcFrom = new JDateChooser());
			jdcFrom.setDateFormatString(dateFrom);
			b4.add(Box.createHorizontalStrut(30));
			
			b4.add(lbFindTo = new JLabel("Ngày Đi: "));
			lbFindTo.setFont(font1);
			lbFindTo.setForeground(Color.BLACK);
			b4.add(Box.createHorizontalStrut(20));
			jdcFrom.setPreferredSize(new Dimension(150, 40));
			jdcFrom.setMaximumSize(new Dimension(150, 40));
			String dateTo="yyyy-MM-dd";
			b4.add(jdcTo = new JDateChooser());
			jdcTo.setDateFormatString(dateTo);
			b4.add(Box.createHorizontalStrut(30));
			jdcTo.setPreferredSize(new Dimension(150, 40));
			jdcTo.setMaximumSize(new Dimension(150, 40));
			b4.add(btnTimNgay= new JButton("Tìm Theo Ngày",new ImageIcon(".\\image\\find.png")));
			//processing();
		
		// Đăng Ký Sự Kiện
		btnBack.addActionListener(this);
		btnTimNgay.addActionListener(this);
		cmbTimKiem.addActionListener(this);
		cmbSapXep.addActionListener(this);
		// ================Load Database==============
		//database.Database.getInstance().connect();
		database.Database.getInstance().connect();
	}

	private void createButton(JButton button, Dimension size) {
		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
	}

	public void setFontLabel(List<JLabel> listLabel) {
		listLabel.forEach(x -> {
			x.setFont(new Font("Times New Roman", Font.BOLD, 15));
		});

	}

	public void setTextisFalse(List<JTextField> listText) {
		listText.forEach(x -> {
			x.setEnabled(false);
		});
	}
	private ArrayList<Phong> listPhong() {
		QuanLyPhong ql = new QuanLyPhong();
		ArrayList<Phong> list = new ArrayList<Phong>(); 
		list = ql.docTuBang();
		return list;
	}
	
	public void callListPhong(JPanel pbox2, Box b3, ArrayList<Phong> list) {
		b3.removeAll();
		b3.revalidate();
		b3.repaint();
		int x = 4;
		int y = ql.tongSoPhong() / 4 + 1;
		List<String> dsPhong = new ArrayList<String>();
		for (Phong p : list) {
			DecimalFormat df = new DecimalFormat("###,000");
			String mauSac = "";
			if (p.getTinhTrangPhong() == 0) {//trống
				mauSac = "#808080";
			} else if (p.getTinhTrangPhong() == 1) {//màu vàng
				mauSac = "#fdff00";
			} else if (p.getTinhTrangPhong() == 2) {// màu đỏ
				mauSac = "#FF0000";
			} else if(p.getTinhTrangPhong() == 3) {//màu xanh
				mauSac = "#001eff";
			}else if(p.getTinhTrangPhong() == 4) {//màu xanh lơ
				mauSac = "#00FFFF";
			}
			dsPhong.add("<html><center>Phòng " + (p.getId() + 100) + " <br/>" + "Ghi Chú: " + p.getGhiChu() + " <br/>"
					+ "Giá Tiền: " + df.format(p.getGiaPhong()) + "<br />" + "Loại Phòng: " + p.getLoaiPhong() + 
					"<!--color"	+ mauSac + "color-->"
					+"<!--id"	+ p.getId() + "id-->"
							+ "</html>");
			// 0 là phòng trống, 1 là Đã Đặt, 2 là Đang sử dụng
		}
		String[] tenDsPhong;
		tenDsPhong = dsPhong.toArray(new String[0]);
		phong = new JButton[dsPhong.size()];
		pbox2 = new JPanel();
//		Component[] components = pbox2.getComponents();
//		for (Component component : components) {
//			pbox2.remove(component);  
//		}
		pbox2.revalidate();
		pbox2.repaint();
		pbox2.setOpaque(false);
		pbox2.setLayout(test = new GridLayout(y, x, 20, 30));

		try {
			for (int i = 0; i < tenDsPhong.length; i++) {
				 //System.out.println("271 "+tenDsPhong[i]);
				phong[i] = new JButton();
				phong[i].setText(tenDsPhong[i]);
				// System.out.println(tenDsPhong[i]);
				int startString = tenDsPhong[i].indexOf("<!--color");
				int finishString = tenDsPhong[i].indexOf("color-->");
				String mauSac = tenDsPhong[i].substring(startString + 9, finishString);
				// phong[i].setBackground(Color.getColor(mauSac));
				phong[i].setBackground(Color.decode(mauSac));
				// System.out.println(mauSac);
				pbox2.add(phong[i]);
				phong[i].addActionListener(this);
				createButton(phong[i], new Dimension(20, 100));
			}
			
		} catch (Exception e2) {
			//System.out.println("heheh");
			JOptionPane.showMessageDialog(null, e2);
		}
		b3.add(new JScrollPane(pbox2));
		pbox2.setBackground(new Color(213, 134, 145, 123));
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();// src instanceof JButton
		for (int i = 0; i < phong.length; i++) {
			if (src.equals(phong[i])) {
				int startString = phong[i].getText().indexOf("<!--id");
				int finishString = phong[i].getText().indexOf("id-->");
				String id_str = phong[i].getText().substring(startString + 6, finishString);
				try {
					com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme("Black", "Le Chi Bao", "My Hotel");    

				       UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
					maPhong = id_str;
					GUIThuePhong gui = new GUIThuePhong(parent);
				} catch (Exception e12) {
					e12.printStackTrace();
				}
					//neesu co 3,4,2,1 => 4 (sap checkout) thi chuyen phong gia han hay ko?
			}
		}
		if (src.equals(btnBack)) {
			new GUIMenu(GUIMenu.ktrLogin);
			parent.setVisible(false);
		}
		if(src.equals(btnTimNgay)) {
			if(jdcFrom.getDate() == null||jdcTo.getDate() == null) {
				JOptionPane.showMessageDialog(null, "Chưa chọn ngày đến hoặc ngày đi");
				return;
			}
			else {
				System.out.println("Loi");
				
			}
			SimpleDateFormat ngay = new SimpleDateFormat("yyyy-MM-dd");
			Date dateNow = new Date();
			String ngayHienTai = ngay.format(dateNow);
			QuanLyPhong ql = new QuanLyPhong();
			String ngayden = ngay.format(jdcFrom.getDate());
			String ngaydi = ngay.format(jdcTo.getDate());
		
			if(ngayden.compareTo(ngaydi)>= 0) {
				JOptionPane.showMessageDialog(this, "Ngày Đi Phải Lớn Hơn Ngày Hiện Đến: "+ngayden);
				return;
			}
			if(ngayden.compareTo(ngayHienTai)<0) {
				JOptionPane.showMessageDialog(this, "Ngày Đến Không Được Nhỏ Hơn Ngày Hiện tại");
				return;
			}
		ArrayList<Phong> list = ql.timkiemtheongay(ngayden, ngaydi);
//			ArrayList<Phong> list = timkiemtheongay(String.valueOf(jdcFrom.getDate()), String.valueOf(jdcTo.getDate()));
			callListPhong(pbox2, b3, list);
		}
		if(src.equals(cmbSapXep)) {
			//System.out.println(cmbSapXep.getSelectedItem().toString());
			String s = cmbSapXep.getSelectedItem().toString();
			//pbox2.revalidate();
			//pbox2.repaint();
			callListPhong(pbox2, b3, listPhongSapXep(s));
		}
		
		
		if(src.equals(cmbTimKiem)) {
			String loaiTim = cmbTimKiem.getSelectedItem().toString();
			int tinhTrang = 0;
			QuanLyPhong ql = new QuanLyPhong();
			ArrayList<Phong> list = new ArrayList<Phong>(); 
			list = ql.docTuBang();
			if(!loaiTim.equals("")) {
				if(loaiTim.equals("---Trống---"))
					tinhTrang = 0;
				else if(loaiTim.equals("---Đã Đặt---"))
					tinhTrang = 1;
				else if(loaiTim.equals("---Đang Sử Dụng---"))
					tinhTrang = 3;
				else if(loaiTim.equals("---Đến Hạn Check In---"))
					tinhTrang = 2;
				else if(loaiTim.equals("---Đến Hạn Check Out---"))
					tinhTrang = 4;
				
				ArrayList<Phong> listResult = new ArrayList<Phong>(); 
				for(Phong p : list) {
					if(p.getTinhTrangPhong()==tinhTrang){
						listResult.add(p);
					}
				};
				callListPhong(pbox2, b3, listResult);
			}else callListPhong(pbox2, b3, list);
		}
	}
	private ArrayList<Phong> listPhongSapXep(String loai) {
		QuanLyPhong ql = new QuanLyPhong();
		ArrayList<Phong> list = new ArrayList<Phong>(); 
			list = ql.sapXepPhongTheoGia(loai);
		return list;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
//	public ArrayList<Phong> timkiemtheongay(String ngayFrom,String ngayTo){
//		ArrayList<Phong> dsPhong = new ArrayList<Phong>();
//		Database.getInstance();
//		Connection con = Database.getConnection();
//		try {
////			Database.getInstance();
////			Connection con = (Connection) Database.getConnection();
//			String sql ="select * from Phong\r\n" + 
//					"except\r\n" + 
//					"select p.* from  PhieuDatPhong as pdp join Phong as p on pdp.maPhong = p.maPhong\r\n" + 
//					"where (pdp.ngayDen  = '"+ngayFrom+"' and pdp.ngayDi  ='"+ngayTo+"')";
//			Statement stm = con.createStatement();
//			ResultSet rs = stm.executeQuery(sql);
//			
//			while(rs.next()) {
//				String maPhong = rs.getString(1);
//				String loaiPhong = rs.getNString(2);
//				String ghiChu = rs.getNString(3);
//				String giaPhong = rs.getString(4);
//				String tinhTrang = rs.getString(5);
//				// System.out.println("53: "+tinhTrang);
//				Phong t = new Phong(Integer.parseInt(maPhong), loaiPhong, ghiChu, Integer.parseInt(giaPhong),
//						Integer.parseInt(tinhTrang));
//				dsPhong.add(t);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		
//		}
//		return dsPhong;
//	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
