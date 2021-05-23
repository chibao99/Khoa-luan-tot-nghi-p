package uiQuanLy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

public class GUIQuanLyHoaDon extends JFrame implements ActionListener, MouseListener {

	JTable dataTable;
	JDateChooser dateOrder;
	static DefaultTableModel tableModel;
	JButton btn_search;
	JPanel panel;
	JLabel lb_title;
	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	private Frame child;

	public GUIQuanLyHoaDon(Frame parent) {
		child  = parent;
		panel = new JPanel();
		panel.add(lb_title = new JLabel("Quản Lý Hóa Đơn"));
		lb_title.setFont(new Font("times new roman", Font.BOLD, 50));
		lb_title.setForeground(new Color(000000));
		Box b1,b2;
		Box b = Box.createVerticalBox();
		add(b);
		b.add(panel,BorderLayout.NORTH);
//		b.add(Box.createVerticalStrut(20));
		String[] cols = "Mã Nhân Viên;Tên Nhân Viên;Giới Tính;Số Điện Thoại;CMND;Ngày Sinh;Địa Chỉ".split(";");
		tableModel = new DefaultTableModel(cols,0);
		dataTable = new JTable(tableModel) {
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
		dataTable.setAutoCreateRowSorter(true);
		int col[] = {10,30,10,10,10,10,120};
		for(int i=0;i<7;i++) {
			dataTable.getColumnModel().getColumn(i).setPreferredWidth(col[i]*4);

		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<7;i++) {
			dataTable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
		}
		
		JTableHeader header = dataTable.getTableHeader();
		header.setBackground(Color.WHITE);
		header.setOpaque(false);
		// xét cứng cột
		dataTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scroll = new JScrollPane(dataTable);
		b.add(scroll);
		b.add(Box.createVerticalStrut(10));
		
		String date = "yyyy-MM-dd";
		dateOrder = new JDateChooser();
		dateOrder.setDateFormatString(date);
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

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
