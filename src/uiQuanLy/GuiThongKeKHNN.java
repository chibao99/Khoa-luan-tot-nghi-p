package uiQuanLy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import java.awt.EventQueue;


import services.ThongKe;

public class GuiThongKeKHNN  extends JFrame implements ActionListener{
	ThongKe tk = new ThongKe();
	JComboBox<String>  cmbNam;
	private static String  namPDP ;
	JButton btnThongKe,btnOK;
	JPanel chartPanel = createChartPanel(LocalDate.now().getYear()+"");
	private JPanel childPanel;

	public GuiThongKeKHNN() {
		setTitle("Số Lượng Khách Hàng Đặt Phòng");
		setSize(700, 520);
		Box bc,b1;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(chartPanel);		
		bc.add(Box.createVerticalStrut(20));
		bc.add(b1 = Box.createHorizontalBox());
//		b1.add(Box.createHorizontalStrut(20));
		
		
		
		b1.add(Box.createHorizontalStrut(250));
		b1.add(new JLabel("Năm: "));
		cmbNam = new JComboBox();
        final String[] traceYears = {"2019","2020","2021"};
        cmbNam.setModel(new DefaultComboBoxModel(traceYears));
        b1.add(cmbNam);
        b1.add(Box.createHorizontalStrut(20));
        b1.add(btnOK = new JButton("Tìm"));
        b1.add(Box.createHorizontalStrut(200));

		bc.add(Box.createVerticalStrut(20));
		btnOK.addActionListener(this);
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
	
	private JPanel createChartPanel( String namPDP) {
	    String chartTitle = "Số Lượng Đơn Đặt Phòng Trong Năm";
	    String categoryAxisLabel = "Mã Khách Hàng (100)";
	    String valueAxisLabel = "Số Lượng Đơn Đặt Phòng";
	    ThongKe tk = new ThongKe();
	    
	    JFreeChart chart = ChartFactory.createLineChart("Số Lượng Đơn Đặt Phòng Trong "+namPDP+".",
	            categoryAxisLabel, valueAxisLabel, tk.thongkeKHDatPhong( namPDP));
	 
	    return new ChartPanel(chart);
	}
//	public static void main(String[] args) {
//		try {
//			new GuiThongKeKHNN();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnOK)) {
			
			namPDP = cmbNam.getSelectedItem().toString();
			showPanel(createChartPanel(namPDP));
		}
	}
}
