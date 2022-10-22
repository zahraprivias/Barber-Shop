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

public class UpdateCart extends JInternalFrame implements ActionListener{

	String itemId;
	JPanel mainPnl, leftPnl, cenPnl, rightPnl;
	JInternalFrame updateCart;
	JTable tableCart;
	DefaultTableModel dtm;
	JScrollPane scrollCart;
	JLabel orderLbl, memberLbl, custLbl, itemLbl, qtyLbl;
	JTextField inputOrder, inputMember, inputCust, inputItem, inputQty;
	JButton updateBtn;
//	String memberId = OrderForm.memberId;
	
	public Connect con = new Connect();
	
	private Vector<Object> tableContent;
	
	public void init() {
		updateCart = new JInternalFrame("Update Cart");
		
		mainPnl = new JPanel(new BorderLayout());
		cenPnl = new JPanel(new GridLayout(1,1));
		leftPnl = new JPanel();
		rightPnl = new JPanel(new GridLayout(6,2));
		
		//main panel
		mainPnl.add(cenPnl, BorderLayout.CENTER);
		cenPnl.add(leftPnl);
		cenPnl.add(rightPnl);
		add(mainPnl);
		
		//left panel
		String [] header = {"Order ID", "Member ID", "Customer ID", "Item ID", "Quantity"};
		
		dtm = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
		tableCart = new JTable(dtm);
		scrollCart = new JScrollPane(tableCart);
		tableCart.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String orderid = tableCart.getValueAt(tableCart.getSelectedRow(), 0).toString();
				inputOrder.setText(orderid);
				
				String memberid = tableCart.getValueAt(tableCart.getSelectedRow(), 1).toString();
				inputMember.setText(memberid);
				
				String custid = tableCart.getValueAt(tableCart.getSelectedRow(), 2).toString();
				inputCust.setText(custid);
				
				String itemid = tableCart.getValueAt(tableCart.getSelectedRow(), 3).toString();
				inputItem.setText(itemid);
				
				String qty = tableCart.getValueAt(tableCart.getSelectedRow(), 4).toString();
				inputQty.setText(qty);
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
		getData();
		leftPnl.add(scrollCart);
		scrollCart.setPreferredSize(new Dimension(250, 400));
		
		//right panel
		orderLbl = new JLabel("Order ID:");
		inputOrder = new JTextField();
		rightPnl.add(orderLbl);
		rightPnl.add(inputOrder);
		
		memberLbl = new JLabel("Member ID:");
		inputMember = new JTextField();
		rightPnl.add(memberLbl);
		rightPnl.add(inputMember);
		
		custLbl = new JLabel("Customer ID:");
		inputCust = new JTextField();
		rightPnl.add(custLbl);
		rightPnl.add(inputCust);
		
		itemLbl = new JLabel("Item ID:");
		inputItem = new JTextField();
		rightPnl.add(itemLbl);
		rightPnl.add(inputItem);
		
		qtyLbl = new JLabel("Quantity:");
		inputQty = new JTextField();
		rightPnl.add(qtyLbl);
		rightPnl.add(inputQty);
		
		updateBtn = new JButton("Update");
		rightPnl.add(updateBtn);
		
		updateBtn.addActionListener(this);
	}
	
	public UpdateCart() {
		setVisible(true);
		setSize(600, 400);
		setClosable(false);
		setMaximizable(false);
		setIconifiable(false);
		
		init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateBtn) {
			updateData();
		} else {

		}
		
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
			tableCart.setModel(dtm);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateData() {
		String orderId = inputOrder.getText();
		String memberId = inputMember.getText();
		String customerId = inputCust.getText();
		String itemId = inputItem.getText();
		String qtyInput = inputQty.getText();
		int qty = Integer.parseInt(qtyInput);
		
		con.updateCart(orderId, memberId, customerId, itemId, qty);
		getData();
	}
}
