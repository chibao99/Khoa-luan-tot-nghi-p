package uiQuanLy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import database.Database;
import entities.CT_DichVu;
import entities.CT_HD;
import entities.DichVu;
import entities.HoaDon;
import entities.PhieuDatPhong;
import entities.QLHoaDon;
import services.QuanLyHoaDon;
import services.QuanLyKhachHang;
import services.QuanLyPhieuDatPhong;
import services.QuanLyPhong;
import uiLogin.GUILogin;
import uiLogin.GUIMenu;

public class GUIHoaDon extends JPanel implements ActionListener, MouseListener {
	JLabel lbMa, lbTen, lbGioiTinh, lbCMND, lbSDT, lbBack, lbNgaySinh, lbNgayDen, lbNgayDi;
	JTextField txtMa, txtTen, txtCMND, txtSDT, txtmaKH;
	JButton btnThem, btnXoaRong, btnXoa, btnSua, btnLuu, btnBack, btnThoat, btnHuy, btnTim, btnReset;
	JComboBox<String> cmbDichVu, cmbGioiTinh;
	JDateChooser jdcNgaySinh, jdcNgayDen, jdcNgaydi, jdcND, jdcNDD;
	public static DefaultTableModel tableModel;
	JTable table;
	JPanel pNor;
	JLabel lblTitle;
	private Frame child;
	QuanLyKhachHang qlkh = new QuanLyKhachHang();

	public GUIHoaDon(Frame parent) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		child = parent;
		pNor = new JPanel();
		pNor.add(lblTitle = new JLabel("Danh Sách Hóa Đơn"));
		lblTitle.setFont(new Font("times new roman", Font.BOLD, 50));
		lblTitle.setForeground(new Color(000000));
		Box bc, b1, b2;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(pNor, BorderLayout.NORTH);
		bc.setPreferredSize(new Dimension(width - width * 18 / 100, height - height * 20 / 100));
		bc.add(Box.createVerticalStrut(15));

		bc.add(b2 = Box.createHorizontalBox());
		b2.add(lbNgayDen = new JLabel("Ngày Đến: "));
		lbNgayDen.setFont(new Font("times new roman", Font.BOLD, 20));
		lbNgayDen.setForeground(Color.BLACK);
		b2.add(Box.createHorizontalStrut(20));
		String dateFrom = "yyyy-MM-dd";
		b2.add(jdcND = new JDateChooser());
		jdcND.setDateFormatString(dateFrom);
		b2.add(Box.createHorizontalStrut(30));

		b2.add(lbNgayDi = new JLabel("Ngày Đi: "));
		lbNgayDi.setFont(new Font("times new roman", Font.BOLD, 20));
		lbNgayDi.setForeground(Color.BLACK);
		b2.add(Box.createHorizontalStrut(20));
		jdcND.setPreferredSize(new Dimension(150, 40));
		jdcND.setMaximumSize(new Dimension(150, 40));
		String dateTo = "yyyy-MM-dd";
		b2.add(jdcNDD = new JDateChooser());
		jdcNDD.setDateFormatString(dateTo);
		b2.add(Box.createHorizontalStrut(30));
		jdcNDD.setPreferredSize(new Dimension(150, 40));
		jdcNDD.setMaximumSize(new Dimension(150, 40));
		b2.add(btnTim = new JButton("Tìm Theo Ngày", new ImageIcon(".\\image\\find.png")));
		b2.add(btnReset = new JButton("Làm Mới", new ImageIcon(".\\image\\reload.png")));

		JScrollPane scroll;
		bc.add(Box.createVerticalStrut(10));
		int col[] = { 5, 20, 20, 15, 15, 15, 15 };
		String[] tieuDe = "Mã Hóa Đơn;Tên Khách Hàng;Số Điện Thoại;Mã Phòng;Ngày Đến;Ngày Đi;Tổng TIền;Tình Trạng"
				.split(";");
		bc.add(b1 = Box.createHorizontalBox());
		tableModel = new DefaultTableModel(tieuDe, 0);
		table = new JTable(tableModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component c = super.prepareRenderer(renderer, row, col);
				if (row % 2 == 0 && !isCellSelected(row, col)) {
					c.setBackground(Color.decode("#F1F1F1"));
				} else if (!isCellSelected(row, col)) {
					c.setBackground(Color.decode("#D7F1FF"));
				} else {
					c.setBackground(Color.decode("#25C883"));
				}
				return c;
			}
		};
		// xét độ dài của cột
		for (int i = 0; i < 5; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(col[i] * 4);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		b1.add(scroll = new JScrollPane(table));

		// ================Load Database==============
		Database.getInstance().connect();
		updatedata();

		// ================ĐĂNG KÝ SỰ KIỆN==========================
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// ===================================================
		setVisible(true);
		btnTim.addActionListener(this);
		btnReset.addActionListener(this);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				actionCTHD(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
			}
		});

	}

	String chucNangHienTai = null;

	private void updatedata() {
		Database.getInstance().connect();
		QuanLyHoaDon qlhd = new QuanLyHoaDon();
		List<QLHoaDon> list = qlhd.getHD();
		System.out.println(list);
		tableModel.setRowCount(0);
		jdcND.setCalendar(null);
		jdcNDD.setCalendar(null);
		for (QLHoaDon hd : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String ngayDen = sdf.format(hd.getNgayDen());
			String ngayDi = sdf.format(hd.getNgayDi());
			String ttTemp = "";
			if (hd.getTinhTrangHD() == 1) {
				ttTemp = "Đã Thanh Toán";
			} else {
				ttTemp = "Chưa Thanh Toán";
			}
			NumberFormat formater = new DecimalFormat("###,###.## VNĐ");
			String[] rowData = { hd.getMaHD() + "", hd.getHoTen() + "", hd.getsDT() + "", hd.getMaPhong() + "",
					String.valueOf(hd.getNgayDen()), String.valueOf(hd.getNgayDi()),
					String.valueOf(formater.format(hd.getTongTien())), ttTemp + "" };

			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}

	private void hienDataComponent(int row) {
		if (row != -1) {
			txtMa.setText(table.getValueAt(row, 0).toString());
			java.util.Date ngaySinh = null;
			Date ngaydi = null;
			try {
				ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 5).toString());
				ngaydi = new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 4).toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdcNgaySinh.setDate(ngaySinh);
			jdcNgayDen.setDate(ngaydi);
		}
	}

	public void tim(ArrayList<DichVu> listDv) {

	}

	// =========================HÀNH ĐỘNG==============================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src.equals(btnThem)) {
			actionLuu();

		} else if (src.equals(btnThoat)) {
			actionThoat();

		} else if (src.equals(btnSua)) {
			actionSua();
		} else if (src.equals(btnHuy)) {

		} else if (src.equals(btnXoa)) {
			actionXoa();

		} else if (src.equals(btnXoaRong)) {

		} else if (src.equals(btnBack)) {

			new GUILogin();
			child.setVisible(false);
		} else if (src.equals(btnTim)) {
			actionTim();
		} else if (src.equals(btnReset)) {
			updatedata();
		}
	}

	private void actionCTHD(int id) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		QuanLyHoaDon don = new QuanLyHoaDon();
		ArrayList<CT_HD> list = new ArrayList<CT_HD>();
		list = don.getCTHD(id);
		JPanel panel = new JPanel(new GridLayout(0, 1));
//		panel.setPreferredSize();
		Box bc, b1;
		bc = Box.createVerticalBox();
		panel.add(bc);
		bc.add(b1 = Box.createVerticalBox());
		NumberFormat formater = new DecimalFormat("###,###.## VNĐ");
//		JScrollPane talkPane = new JScrollPane(b1,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		if (list.size() == 0) {
			JOptionPane.showMessageDialog(this, "Không có sử dụng dịch vụ thêm");
		} else {
			for (CT_HD cthd : list) {
				b1.add(new JLabel("Tên Dịch Vụ:" + cthd.getTenDV()));
				b1.add(new JLabel("Số Lượng Sử Dụng:" + cthd.getSoluongSD()));
				b1.add(new JLabel("Thời Gian Sử Dụng:" + cthd.getTgSD()));
				b1.add(new JLabel("Giá Dịch Vụ:" + formater.format(cthd.getGiaDV())));
				b1.add(new JLabel("-----------------------------------------"));
			}

			JOptionPane.showConfirmDialog(null, panel, "Danh Sách dịch vụ", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		}

	}

	private void actionThoat() {
		if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát?", "Cảnh Báo",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	private void actionSua() {
		jdcNgayDen.setEnabled(true);
		jdcNgaySinh.setEnabled(true);
		btnThem.setEnabled(true);
	}

	private void xoaTableData() {
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
	}

	private void actionTim() {
		if (jdcND.getDate() == null || jdcNDD.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Chưa chọn ngày đến hoặc ngày đi");
			return;
		} else {
			System.out.println("Loi");

		}
		SimpleDateFormat ngay = new SimpleDateFormat("yyyy-MM-dd");
		QuanLyPhong ql = new QuanLyPhong();
		String ngayden = ngay.format(jdcND.getDate());
		String ngaydi = ngay.format(jdcNDD.getDate());

		if (ngayden.compareTo(ngaydi) >= 0) {
			JOptionPane.showMessageDialog(this, "Ngày Đi Phải Lớn Hơn Ngày Đến: " + ngayden);
			return;
		}

		Database.getInstance().connect();
		QuanLyHoaDon quanLyHoaDon = new QuanLyHoaDon();
		ArrayList<QLHoaDon> list = new ArrayList<QLHoaDon>();
		list = quanLyHoaDon.findOrderByDate(ngayden, ngaydi);
		tableModel.setRowCount(0);
		for (QLHoaDon hd : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String ngayDen = sdf.format(hd.getNgayDen());
			String ngayDi = sdf.format(hd.getNgayDi());
			String ttTemp = "";
			if (hd.getTinhTrangHD() == 1) {
				ttTemp = "Đã Thanh Toán";
			} else {
				ttTemp = "Chưa Thanh Toán";
			}
			NumberFormat formater = new DecimalFormat("###,###.## VNĐ");
			String[] rowData = { hd.getMaHD() + "", hd.getHoTen() + "", hd.getsDT() + "", hd.getMaPhong() + "",
					String.valueOf(hd.getNgayDen()), String.valueOf(hd.getNgayDi()),
					String.valueOf(formater.format(hd.getTongTien())), ttTemp + "" };

			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}

	private void actionLuu() {
		QuanLyPhieuDatPhong ql = new QuanLyPhieuDatPhong();
//		Date dateNS;
//		dateNS = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinh);
		SimpleDateFormat ngay = new SimpleDateFormat("yyyy-MM-dd");
		String ngayDen = ngay.format(jdcNgayDen.getDate());
		String ngayDi = ngay.format(jdcNgaySinh.getDate());
		int ma = Integer.parseInt(txtMa.getText());
//		ql.updatePDP(ma, ngayDen, ngayDi);

		try {
			ql.updateNV(ma, ngayDi);
			ql.updateNV2(ma, ngayDen);
			JOptionPane.showMessageDialog(null, "Luu thanh cong");
			xoaTableData();
			updatedata();

		} catch (Exception e) {
			// TODO: handle exception
		}
		btnThem.setEnabled(false);
	}

	public void actionXoa() {
		QuanLyPhieuDatPhong ql = new QuanLyPhieuDatPhong();

		int n = table.getSelectedRow();
		if (n == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa");
		} else {

			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không", "Thông báo",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				QuanLyHoaDon qlhd = new QuanLyHoaDon();
				int ma = Integer.parseInt(txtMa.getText());
				HoaDon hd = qlhd.timTheoMa(txtMa.getText());
				if (hd.getTinhTrangHD() == 0) {
					qlhd.delete(ma);
				}

//				boolean kt = ql.delete(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
//				if(kt == true) {
				if (ql.delete(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString())) == true) {
					JOptionPane.showMessageDialog(null, "Đã xóa");
				} else {
					JOptionPane.showMessageDialog(null, "Phiếu đặt phòng này đã thanh toán, không xóa được");
				}

//					ql.delPass(ma);

				xoaTableData();
				updatedata();
//				}

			}

		}

	}

	// =======================Đưa Data lên table=========================

	// =======================Sự kiện con chuột=========================
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (chucNangHienTai == null && src.equals(table)) {
			int index = table.getSelectedRow();
			hienDataComponent(index);
			btnXoa.setEnabled(true);
			btnSua.setEnabled(true);
		}

	}

}
