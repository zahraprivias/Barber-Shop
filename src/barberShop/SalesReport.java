package barberShop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import barberShop.Connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Vector;

public class SalesReport extends JInternalFrame implements ActionListener{

	JPanel mainPnl, topPnl, cenPnl, botPnl;
	JInternalFrame salesReport;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane scroll;
	JLabel titleLbl, totalLbl;
	JTextField totalPrice;
	JButton approveBtn;
	
	public Connect con = new Connect();
	
	private Vector<Object> tableContent;
	
	public SalesReport() {
		salesReport = new JInternalFrame("Sales Report");

		mainPnl = new JPanel(new BorderLayout());
		topPnl = new JPanel();
		cenPnl = new JPanel();
		botPnl = new JPanel();
		
		//main panel
		mainPnl.add(topPnl, BorderLayout.NORTH);
		mainPnl.add(cenPnl, BorderLayout.CENTER);
		mainPnl.add(botPnl, BorderLayout.SOUTH);
		add(mainPnl);
		
		//top panel
		titleLbl = new JLabel("Total Sales Report");
		topPnl.add(titleLbl);
		
		//center panel
		String [] header = {"Order ID", "Member ID", "Customer ID", "Item ID", "Quantity"};
		
		dtm = new DefaultTableModel(header, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
            
		table = new JTable(dtm);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400, 300));
		cenPnl.add(scroll);
		
		getData();
	
		//bottom panel
		totalLbl = new JLabel("Total: ");
		totalPrice = new JTextField();
		botPnl.add(totalLbl);
		botPnl.add(totalPrice);
		getTotal();
		approveBtn = new JButton("Approve");
		botPnl.add(approveBtn);
		approveBtn.addActionListener(this);
		
		// init frame
		setVisible(true);
		setSize(600, 400);
		setClosable(false);
		setMaximizable(false);
		setIconifiable(false);
	}

	protected void getData() {
		dtm.setRowCount(0);
		try {
			
			String query = "SELECT orderitem.orderId, orderitem.memberId, orderitem.customerId, orderitem.itemId, orderitem.quantity FROM orderitem";
			con.rs = con.execQuery(query);
			while(con.rs.next()) {
				tableContent = new Vector<Object>();
				for (int i = 1; i < con.rsm.getColumnCount()+1; i++) {
					tableContent.add(con.rs.getObject(i)+"");
				}
				dtm.addRow(tableContent);
			}
			table.setModel(dtm);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void clearCart() {
		con.approveLaporan();
		getData();
	}
	
	protected void getTotal() {
		String query = "SELECT SUM(orderitem.quantity*item.harga) AS Total FROM orderitem JOIN item ON orderitem.itemId=item.itemId";
		con.rs = con.execQuery(query);
		try {
			con.rs.next();
			int total = con.rs.getInt("Total");
			String total1 = Integer.toString(total);
			totalPrice.setText(total1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == approveBtn) {
			clearCart();
		} else {

		}
		
	}
}
