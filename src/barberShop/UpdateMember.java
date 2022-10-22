package barberShop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import barberShop.Connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class UpdateMember extends JInternalFrame implements ActionListener{

	JPanel mainPnl, topPnl, cenPnl, botPnl;
	JInternalFrame updateMemberForm;
	JLabel titleLbl, idLbl, nameLbl, phoneLbl, emailLbl, alamatLbl;
	JTextField inputId, inputNama, inputEmail, inputPhone;
	JTextArea inputAddress;
	JButton updateBtn;
	
	public Connect con = new Connect();
	
	public void init() {
		updateMemberForm = new JInternalFrame("Update Member");
		
		mainPnl = new JPanel(new BorderLayout());
		topPnl = new JPanel();
		cenPnl = new JPanel(new GridLayout(5,2));
		botPnl = new JPanel();
		
		//main panel
		mainPnl.add(topPnl, BorderLayout.NORTH);
		mainPnl.add(cenPnl, BorderLayout.CENTER);
		mainPnl.add(botPnl, BorderLayout.SOUTH);
		add(mainPnl);
		
		//top panel
		titleLbl = new JLabel("Update Member");
		topPnl.add(titleLbl);
		
		//center panel
		idLbl = new JLabel("ID:");
		inputId = new JTextField();
		nameLbl = new JLabel("Nama:");
		inputNama = new JTextField();
		phoneLbl = new JLabel("Nomor Handphone:");
		inputPhone = new JTextField();
		emailLbl = new JLabel("Email:");
		inputEmail = new JTextField();
		alamatLbl = new JLabel("Alamat:");
		inputAddress = new JTextArea();
		cenPnl.add(idLbl);
		cenPnl.add(inputId);
		cenPnl.add(nameLbl);
		cenPnl.add(inputNama);
		cenPnl.add(phoneLbl);
		cenPnl.add(inputPhone);
		cenPnl.add(emailLbl);
		cenPnl.add(inputEmail);
		cenPnl.add(alamatLbl);
		cenPnl.add(inputAddress);
		
		//bottom panel
		updateBtn = new JButton("Update Member");
		updateBtn.setPreferredSize(new Dimension(180, 30));
		botPnl.add(updateBtn);
		
		//action listener
		updateBtn.addActionListener(this);
	}
	
	public UpdateMember() {
		setVisible(true);
		setSize(300, 250);
		setClosable(false);
		setMaximizable(false);
		setIconifiable(false);
		
		init();
	}

	protected void updtData() {
		String memberId = inputId.getText();
		String nama = inputNama.getText();
		String phone = inputPhone.getText();
		String email = inputEmail.getText();
		String alamat = inputAddress.getText();
		
		con.updateMember(memberId, nama, phone, email, alamat);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateBtn) {
			if (!inputId.getText().isEmpty() && !inputNama.getText().isEmpty() && !inputPhone.getText().isEmpty() && !inputEmail.getText().isEmpty() && !inputAddress.getText().isEmpty()) {
				updtData();
	            JOptionPane.showMessageDialog(this, "Update berhasil!");
			} else {

			}
			
		}
		
	}

}
