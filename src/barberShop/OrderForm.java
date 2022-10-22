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

public class OrderForm extends JInternalFrame implements ActionListener{

	String itemId;
	public static String memberId;
	JPanel mainPnl, leftPnl, cenPnl, rightPnl, leftTopPnl, leftCenPnl, leftBotPnl;
	JInternalFrame orderForm;
	JTable tableItem, tableCart;
	DefaultTableModel dtm, dtmItem;
	JScrollPane scrollItem, scrollCart;
	JLabel memberLbl, totalLbl;
	JTextField inputMember, totalPrice;
	JButton checkoutBtn, addBtn;

	public Connect con = new Connect();
	
	private Vector<Object> tableContent;
	
	public void init() {
		orderForm = new JInternalFrame("Order");
		
		mainPnl = new JPanel(new BorderLayout());
		cenPnl = new JPanel(new GridLayout(1,1));
		leftPnl = new JPanel(new BorderLayout());
		rightPnl = new JPanel(new GridLayout(3,1));
		leftTopPnl = new JPanel(new GridLayout(1,2));
		leftCenPnl = new JPanel();
		leftBotPnl = new JPanel(new GridLayout(1,2));
		
		//main panel
		mainPnl.add(cenPnl, BorderLayout.CENTER);
		leftPnl.add(leftTopPnl, BorderLayout.NORTH);
		leftPnl.add(leftCenPnl, BorderLayout.CENTER);
		leftPnl.add(leftBotPnl, BorderLayout.SOUTH);
		cenPnl.add(leftPnl);
		cenPnl.add(rightPnl);
		add(mainPnl);
		
		//left top panel
		memberLbl = new JLabel("Member ID:");
		inputMember = new JTextField();
		leftTopPnl.add(memberLbl);
		leftTopPnl.add(inputMember);
		
		//left center panel
		String [] header = {"Order ID", "Item ID", "Quantity"};
		
		dtm = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
		tableCart = new JTable(dtm);
		scrollCart = new JScrollPane(tableCart);
		getData();
		leftCenPnl.add(scrollCart);
		scrollCart.setPreferredSize(new Dimension(250, 400));
		
		totalLbl = new JLabel("Total: ");
		totalPrice = new JTextField();
		leftBotPnl.add(totalLbl);
		leftBotPnl.add(totalPrice);
		
		//right panel
		String [] header1 = {"Item ID", "Nama Item", "Harga"};
		
		dtmItem = new DefaultTableModel(header1, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
		tableItem = new JTable(dtmItem);
		scrollItem = new JScrollPane(tableItem);
		tableItem.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				itemId = tableItem.getValueAt(tableItem.getSelectedRow(), 0).toString();
				
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
				
			}
			
		});
		getDataItem();
		rightPnl.add(scrollItem);
		
		checkoutBtn = new JButton("Check Out");
		addBtn = new JButton("Add");
		rightPnl.add(addBtn);
		rightPnl.add(checkoutBtn);
		checkoutBtn.setPreferredSize(new Dimension(200,100));
		
		addBtn.addActionListener(this);
		checkoutBtn.addActionListener(this);
	}

	
	public OrderForm() {
		setVisible(true);
		setSize(500, 400);
		setClosable(false);
		setMaximizable(false);
		setIconifiable(false);
		
		init();
	}
	
	protected void addCart() {
		String query = "SELECT COUNT(orderId) AS 'Rows' FROM orderitem";
		con.rs = con.execQuery(query);
		memberId = inputMember.getText();
		String customerId = "CST001";
		int qty = 1;
		int rows;
		try {
			while (con.rs.next()) {
				rows = Integer.parseInt(con.rs.getString("Rows"));
				String orderId = null;
				String IdNum = null;
				if (rows < 10) {
					IdNum = String.format("%03d",rows+1);
					orderId = String.format("ORD"+IdNum);
				} else if(rows < 100){
					IdNum = String.format("%02d",rows+1);
					orderId = String.format("ORD"+IdNum);
				} else if(rows <1000){
					orderId = String.format("ORD"+IdNum);
				}
				con.addToCart(orderId, memberId, customerId, itemId, qty);
				getData();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void getData() {
		dtm.setRowCount(0);
		try {
			
			String query = "SELECT orderitem.orderId, orderitem.itemId, orderitem.quantity FROM orderitem";
			con.rs = con.execQuery(query);
			while(con.rs.next()) {
				tableContent = new Vector<Object>();
				for (int i = 1; i < con.rsm.getColumnCount()+1; i++) {
					tableContent.add(con.rs.getObject(i)+"");
				}
				dtm.addRow(tableContent);
			}
			tableCart.setModel(dtm);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void getDataItem() {
		dtmItem.setRowCount(0);
		try {
			String query = "SELECT item.itemId, item.namaItem, item.harga FROM item";
			con.rs = con.execQuery(query);
			while(con.rs.next()) {
				tableContent = new Vector<Object>();
				for (int i = 1; i < con.rsm.getColumnCount()+1; i++) {
					tableContent.add(con.rs.getObject(i)+"");
				}
				dtmItem.addRow(tableContent);
			}
			tableItem.setModel(dtmItem);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (e.getSource() == addBtn) {
			addCart();
		} else if (e.getSource() == checkoutBtn){
			getTotal();
		}
		
	}
}
