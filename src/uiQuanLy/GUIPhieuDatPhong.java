package uiQuanLy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import database.Database;
import entities.DichVu;
import entities.HoaDon;
import entities.KhachHang;
import entities.PhieuDatPhong;
import services.QuanLyHoaDon;
import services.QuanLyKhachHang;
import services.QuanLyPhieuDatPhong;
import services.QuanLyThueTra;
import uiLogin.GUIMenu;

public class GUIPhieuDatPhong extends JPanel implements ActionListener, MouseListener{
	JLabel lbMa, lbTen, lbGioiTinh, lbCMND, lbSDT, lbBack, lbNgaySinh;
	JTextField txtMa, txtTen, txtCMND, txtSDT, txtmaKH;
	JButton btnThem, btnXoaRong, btnXoa, btnSua, btnLuu, btnBack, btnThoat,btnHuy;
	JComboBox<String> cmbDichVu, cmbGioiTinh;
	JDateChooser jdcNgaySinh, jdcNgayDen, jdcNgaydi;
	public static DefaultTableModel tableModel;
	JTable table;
	JPanel pNor;
	JLabel lblTitle;
	private Frame child;
	QuanLyKhachHang qlkh = new  QuanLyKhachHang(); 
	public GUIPhieuDatPhong(Frame parent) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		child = parent;
		pNor = new JPanel();
		pNor.add(lblTitle = new JLabel("Danh Sách Phiếu Đặt Phòng"));
		lblTitle.setFont(new Font("times new roman", Font.BOLD, 50));
		lblTitle.setForeground(new Color(000000));
		Box bc,b1;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(pNor, BorderLayout.NORTH);
		bc.setPreferredSize(new Dimension(width-width*18/100,height-height*20/100));
		bc.add(Box.createVerticalStrut(15));
		bc.add(b1 = Box.createHorizontalBox());
//			b1.add(Box.createHorizontalStrut(150));
//			b1.add(lbMa = new JLabel("Mã PĐP:"));
			lbMa = new JLabel("Mã PĐP");
			lbTen = new JLabel();
			lbSDT = new JLabel();
			lbGioiTinh = new JLabel();
			lbNgaySinh = new JLabel();
			lbBack = new JLabel();
			lbCMND = new JLabel();
			b1.add(Box.createHorizontalStrut(width - width*60/100));
		bc.add(Box.createVerticalStrut(15));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(150));
			b1.add(lbMa);
			b1.add(Box.createHorizontalStrut(30));
			b1.add(txtMa = new JTextField());
			txtMa.setEnabled(false);
			b1.add(Box.createHorizontalStrut(30));
			String date = "yyyy-MM-dd";
			b1.add(lbSDT = new JLabel("Ngày Đến:"));
			b1.add(Box.createHorizontalStrut(30));
			b1.add(jdcNgayDen = new JDateChooser());
			jdcNgayDen.setDateFormatString(date);
			jdcNgayDen.setEnabled(false);
			b1.add(Box.createHorizontalStrut(10));
			b1.add(lbNgaySinh = new JLabel("Ngày Đi:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(jdcNgaySinh = new JDateChooser());
			jdcNgaySinh.setDateFormatString(date);
			jdcNgaySinh.setEnabled(false);
//			b1.add(Box.createHorizontalStrut(85));
//			b1.add(btnXoaRong=new JButton("Làm Mới",new ImageIcon(".\\image\\reload.png")));
			b1.add(Box.createHorizontalStrut(width - width*80/100));
		bc.add(Box.createVerticalStrut(5));
		bc.add(b1 = Box.createHorizontalBox());
			b1.setBorder(BorderFactory.createTitledBorder("Chọn Tác Vụ"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(btnThem= new JButton("Lưu",new ImageIcon(".\\image\\add.png")));
			b1.add(Box.createHorizontalStrut(5));
			btnThem.setEnabled(false);
			b1.add(btnXoa=new JButton("Xoá",new ImageIcon(".\\image\\remove.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnSua=new JButton("Chỉnh Sửa",new ImageIcon(".\\image\\edit.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnHuy=new JButton("Huỷ",new ImageIcon(".\\image\\cancel.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnThoat=new JButton("Thoát",new ImageIcon(".\\image\\exit.png")));
			b1.add(Box.createHorizontalStrut(5));	

		lbMa.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbTen.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbNgaySinh.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbCMND.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbSDT.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbGioiTinh.setFont(new Font("Times New Roman",Font.BOLD, 15));
		
		JScrollPane scroll;
		bc.add(Box.createVerticalStrut(10));
		int col[]= {5,20,20,15,15,15,15};
		String[] tieuDe = "Mã Phiếu ĐP;Mã Phòng;Mã Nhân Viên;Mã KH;Ngày Đến;Ngày đi;Ngày Lập Phiếu".split(";");
		bc.add(b1 = Box.createHorizontalBox());
			tableModel = new DefaultTableModel(tieuDe,0);
			table = new JTable(tableModel) {
				public boolean isCellEditable(int row, int col) {
						return false;
				}
				public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {
					Component c = super.prepareRenderer(renderer, row, col);
					if ( row % 2 == 0 && !isCellSelected(row, col)) {
						c.setBackground( Color.decode("#F1F1F1") );
					}
					else 
						if(!isCellSelected(row, col)){
							c.setBackground( Color.decode("#D7F1FF") );
						}else {
							c.setBackground(Color.decode("#25C883"));
						}		        
					return c;
				}
			};
			//xét độ dài của cột
			for(int i = 0; i < 6; i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(col[i]*4);
			}
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			for(int i=0;i<6;i++) {
				table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
			}
		
			b1.add(scroll = new JScrollPane(table));
			table.addMouseListener(this);
			btnThem.addActionListener(this);
			btnSua.addActionListener(this);
			btnXoa.addActionListener(this);
			btnThoat.addActionListener(this);
//			btnLuu.addActionListener(this);
			

		//================Load Database==============
		Database.getInstance().connect();
		updatedata();

		
		//================ĐĂNG KÝ SỰ KIỆN==========================
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	

		//===================================================
		setVisible(true);
		
		btnHuy.setVisible(false);
		}
	
	String chucNangHienTai=null;
	private void updatedata() {
		Database.getInstance().connect();
		QuanLyPhieuDatPhong ql = new QuanLyPhieuDatPhong();
		List<PhieuDatPhong> list = ql.dsPDP();
		for(PhieuDatPhong pdp : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String[] rowData = {pdp.getId()+"",pdp.getPhong().getId()+"",pdp.getNhanVien().getId()+"",pdp.getKhachHang().getMa()+"",String.valueOf(pdp.getNgayDen()),String.valueOf(pdp.getNgayDi()),"               "+String.valueOf(pdp.getNgayLapPhieu())};
			
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}
	
	private void hienDataComponent(int row) {
			if(row!=-1) {
			txtMa.setText(table.getValueAt(row,0).toString());
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

	public void enableBtn() {
		btnThem.setEnabled(false);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		//btnLuu.setEnabled(false);
		btnThoat.setEnabled(true);
		List<JTextField> listTxt = Arrays.asList(txtCMND,txtMa,txtSDT,txtTen);
		cmbGioiTinh.setEnabled(false);
		jdcNgaySinh.setEnabled(false);
		txtMa.setEnabled(false);
		cmbGioiTinh.setSelectedItem(null);
	}	
	//=========================HÀNH ĐỘNG==============================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnThem)) {
			actionLuu();
		
		}else if(src.equals(btnThoat)) {
			actionThoat();
		
		}else if(src.equals(btnSua)) {
			actionSua();
		}else if(src.equals(btnHuy)) {

		}else if(src.equals(btnXoa)) {
			actionXoa();

		}else if(src.equals(btnXoaRong)) {

		}else if(src.equals(btnBack)) {
		
			new GUIMenu(1);
			child.setVisible(false);
		}
	}
	private void actionThoat() {
		if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát?", "Cảnh Báo",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	private void actionSua() {
		jdcNgayDen.setEnabled(true);
		jdcNgaySinh.setEnabled(true);
		btnThem.setEnabled(true);
	}
	private void xoaTableData() {
		while(tableModel.getRowCount()>0) {
			tableModel.removeRow(0);
		}
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
		if(n == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa");	
		}
		else {
			
			if(JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không","Thông báo", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				QuanLyHoaDon qlhd = new QuanLyHoaDon();
				int ma = Integer.parseInt(txtMa.getText());
				HoaDon hd = qlhd.timTheoMa(txtMa.getText());
				if(hd.getTinhTrangHD()==0) {
					qlhd.delete(ma);
				}
				
//				boolean kt = ql.delete(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
//				if(kt == true) {
				if(ql.delete(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()))==true) {
					JOptionPane.showMessageDialog(null, "Đã xóa");
				}
				else {
					JOptionPane.showMessageDialog(null, "Phiếu đặt phòng này đã thanh toán, không xóa được");
				}
					
				
//					ql.delPass(ma);
					
					xoaTableData();
					updatedata();
//				}
				
					
				
				
			}
			
			
		}
		
		
	}

	//=======================Đưa Data lên table=========================


	
	//=======================Sự kiện con chuột=========================
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
		if(chucNangHienTai==null && src.equals(table)) {
			int index = table.getSelectedRow();
				hienDataComponent(index);
				btnXoa.setEnabled(true);
				btnSua.setEnabled(true);
		}
		
	}
	


}
