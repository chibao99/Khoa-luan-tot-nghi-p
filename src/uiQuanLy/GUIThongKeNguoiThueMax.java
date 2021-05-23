package uiQuanLy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;

import entities.KhachHang;
import services.ThongKe;

public class GUIThongKeNguoiThueMax extends JFrame implements ActionListener, Printable{
	DefaultTableModel tableModel;
	JTable table;
	JScrollPane scroll;
	ThongKe tk = new ThongKe();
	JComboBox<String> cmbThang, cmbNam;
	private static String thangPDP, namPDP ;
	JButton btnThongKe,btnOK, btInHD;
	JPanel chartPanel = createChartPanel(String.valueOf(LocalDate.now().getMonthValue()),String.valueOf(LocalDate.now().getYear()));
	private JPanel childPanel;

	JLabel lb_tieude;
	Box b;
	public GUIThongKeNguoiThueMax() {
		setTitle("Khách hàng có lượt thuê nhiều nhất");
		setSize(700, 300);
	
		Box bc,b1;
//		bc.add(scroll)
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(chartPanel);		
		bc.add(Box.createVerticalStrut(20));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(20));
		b1.add(new JLabel("Tháng: "));
		cmbThang = new JComboBox();
		final String[] traceMonth = {"1", "2","3","4","5","6","7","8","9","10","11","12"};
		cmbThang.setModel(new DefaultComboBoxModel(traceMonth));

		
		b1.add(cmbThang);
		b1.add(Box.createHorizontalStrut(20));
		b1.add(new JLabel("Năm: "));
		cmbNam = new JComboBox();
		String nam = String.valueOf(LocalDate.now().getYear()-1);
//        final String[] traceYears = {"2018","2019","2020"};
		final String[] traceYears = {String.valueOf(LocalDate.now().getYear()-2),
									String.valueOf(LocalDate.now().getYear()-1),
									String.valueOf(LocalDate.now().getYear())};
        cmbNam.setModel(new DefaultComboBoxModel(traceYears));
        b1.add(cmbNam);
        b1.add(Box.createHorizontalStrut(20));
        b1.add(btnOK = new JButton("OK"));
        b1.add(Box.createHorizontalStrut(30));
        b1.add(btInHD = new JButton("In danh sách"));
        b1.add(Box.createHorizontalStrut(200));

		bc.add(Box.createVerticalStrut(20));
		btnOK.addActionListener(this);
		btInHD.addActionListener(this);
		setLocationRelativeTo(null);
	    setResizable(false);
	    setVisible(true);
	
		
	}
	
	public void showPanel(JPanel panel) {
		childPanel = panel;
		chartPanel.removeAll();
		chartPanel.add(childPanel);
		chartPanel.validate();
	}
	private JPanel createChartPanel(String thang, String nam) {
		String[] cols = "STT;Mã KH;Họ Tên;CMND;SĐT;Số lượt thuê".split(";");
		tableModel = new DefaultTableModel(cols, 0);
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
		table.setAutoCreateRowSorter(true);
		JTableHeader header1 = table.getTableHeader();
		header1.setBackground(Color.CYAN);
		header1.setOpaque(false);
		// xét cứng cột
		table.getTableHeader().setReorderingAllowed(false);
		scroll = new JScrollPane(table);
		ThongKe tk = new ThongKe();
		Map<KhachHang, Integer> map = tk.thongKeNguoiThueNhieuNhat(thang, nam);
		Set<KhachHang> set = map.keySet();
		int i =0;
		for(KhachHang kh : set) {
			i++;
			String[] rowData = {i+"",String.valueOf(kh.getMa()),kh.getHoTen(),kh.getcMND()+"",kh.getsDT()+"",String.valueOf(map.get(kh))};
			tableModel.addRow(rowData);
		}
		JPanel pn = new JPanel();
		table.setSize(500, 200);
//		scroll.setSize(500, 200);
		pn.setLayout(new BorderLayout());

		lb_tieude= new JLabel("3 khách hàng có lượt thuê nhiều nhất trong tháng " +thang+", năm "+nam);
		lb_tieude.setFont(new Font("Times New Roman",15,25));
		b = Box.createVerticalBox();
		
		b.add(Box.createVerticalStrut(20));
		b.add(lb_tieude);
		b.add(Box.createVerticalStrut(25));
		b.add(scroll);
		pn.add(b);

		
		return pn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnOK)) {
			thangPDP = cmbThang.getSelectedItem().toString();
			namPDP = cmbNam.getSelectedItem().toString();
			showPanel(createChartPanel(thangPDP,namPDP));
		}
		if (src.equals(btInHD)) {
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.setJobName(" Bill ");

			pj.setPrintable(new Printable() {
				public int print(Graphics pg, PageFormat pf, int pageNum) {
					if (pageNum > 0) {
						return Printable.NO_SUCH_PAGE;
					}
					Graphics2D g2 = (Graphics2D) pg;
					
					g2.translate(pf.getImageableX(), pf.getImageableY());
//					p.add(table);
//					table.paint(g2);
					b.paint(g2);
					


					return Printable.PAGE_EXISTS;
				}
			});
			if (pj.printDialog() == false)
				return;

			try {
				pj.print();
			} catch (PrinterException ex) {
				// handle exception
			}
		}
		
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

}
