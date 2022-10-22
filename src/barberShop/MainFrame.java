package barberShop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import barberShop.RegisterIframe;

public class MainFrame extends JFrame{

	JMenuBar mainBar;
	JMenu member, order, update, report;
	JMenuItem register, updateMember, makeOrder, makeUpdate, makeReport;
	JDesktopPane registDp, updateMemDp, orderDp, updateDp, reportDp;
	JLabel titleLbl;
	JPanel content;
	
	public MainFrame() {		
		//MenuBar
		mainBar = new JMenuBar();
		//Menu
		member = new JMenu("Member");
		order = new JMenu("Order");
		update = new JMenu("Update");
		report = new JMenu("Report");
		//MenuItem
		register = new JMenuItem("Register");
		updateMember = new JMenuItem("Update Member");
		member.add(register);
		member.add(updateMember);
		makeOrder = new JMenuItem("Order");
		order.add(makeOrder);
		makeUpdate = new JMenuItem("Update Order");
		update.add(makeUpdate);
		makeReport = new JMenuItem("View Order Report");
		report.add(makeReport);
		
		mainBar.add(member);
		mainBar.add(order);
		mainBar.add(update);
		mainBar.add(report);
		setJMenuBar(mainBar);
		
		//panel
		content = new JPanel(new BorderLayout());
		titleLbl = new JLabel("Barber Shop", SwingConstants.CENTER);
		titleLbl.setFont(new Font("Serif", Font.BOLD, 48));
		content.add(titleLbl, BorderLayout.CENTER);
		add(content);
		content.setBackground(Color.orange);
		
		//action
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// desktop pane
				registDp = new JDesktopPane();
				setContentPane(registDp);
				registDp.add(new RegisterIframe());
				registDp.setBackground(Color.orange);

			}
		});
		
		updateMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// desktop pane
				updateMemDp = new JDesktopPane();
				setContentPane(updateMemDp);
				updateMemDp.add(new UpdateMember());
				updateMemDp.setBackground(Color.orange);

			}
		});
		
		makeOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// desktop pane
				orderDp = new JDesktopPane();
				setContentPane(orderDp);
				orderDp.add(new OrderForm());
				orderDp.setBackground(Color.orange);

			}
		});
		
		makeUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Enter Password:");
				if (input.equals("supervisor")) {
					// desktop pane
					updateDp = new JDesktopPane();
					setContentPane(updateDp);
					updateDp.add(new UpdateCart());
					updateDp.setBackground(Color.orange);
				} else {
					
				}

			}
		});
		
		makeReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// desktop pane
				reportDp = new JDesktopPane();
				setContentPane(reportDp);
				reportDp.add(new SalesReport());
				reportDp.setBackground(Color.orange);

			}
		});

		
		// init frame
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Barber Shop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
