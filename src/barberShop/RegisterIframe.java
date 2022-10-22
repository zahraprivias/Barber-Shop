package barberShop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import barberShop.Connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class RegisterIframe extends JInternalFrame implements ActionListener{

	JPanel regMainPnl, regTopPnl, regCenPnl, regBotPnl;
	JInternalFrame registerForm;
	JLabel titleLbl, nameLbl, phoneLbl, emailLbl, alamatLbl;
	JTextField inputNama, inputEmail, inputPhone;
	JTextArea inputAddress;
	JButton regBtn;
	
	public Connect con = new Connect();
	
	public void init() {
		registerForm = new JInternalFrame("Register");
		
		regMainPnl = new JPanel(new BorderLayout());
		regTopPnl = new JPanel();
		regCenPnl = new JPanel(new GridLayout(4,2));
		regBotPnl = new JPanel();
		
		//main panel
		regMainPnl.add(regTopPnl, BorderLayout.NORTH);
		regMainPnl.add(regCenPnl, BorderLayout.CENTER);
		regMainPnl.add(regBotPnl, BorderLayout.SOUTH);
		add(regMainPnl);
		
		//top panel
		titleLbl = new JLabel("Register Member");
		regTopPnl.add(titleLbl);
		
		//center panel
		nameLbl = new JLabel("Nama:");
		inputNama = new JTextField();
		phoneLbl = new JLabel("Nomor Handphone:");
		inputPhone = new JTextField();
		emailLbl = new JLabel("Email:");
		inputEmail = new JTextField();
		alamatLbl = new JLabel("Alamat:");
		inputAddress = new JTextArea();
		regCenPnl.add(nameLbl);
		regCenPnl.add(inputNama);
		regCenPnl.add(phoneLbl);
		regCenPnl.add(inputPhone);
		regCenPnl.add(emailLbl);
		regCenPnl.add(inputEmail);
		regCenPnl.add(alamatLbl);
		regCenPnl.add(inputAddress);
		
		//bottom panel
		regBtn = new JButton("Register");
		regBtn.setPreferredSize(new Dimension(180, 30));
		regBotPnl.add(regBtn);
		
		//action listener
		regBtn.addActionListener(this);
	}
	
	public RegisterIframe() {
		setVisible(true);
		setSize(300, 200);
		setClosable(false);
		setMaximizable(false);
		setIconifiable(false);
		
		init();
	}

	protected void insertData() {
		String query1 = "SELECT COUNT(memberId) AS 'Rows' FROM member";
		con.rs = con.execQuery(query1);
		try {
			while (con.rs.next()) {
				int rows;
				try {
					rows = Integer.parseInt(con.rs.getString("Rows"));
					String Id = null;
					String IdNum = null;
					if (rows < 10) {
						IdNum = String.format("%03d",rows+1);
						Id = String.format("MEM"+IdNum);
					} else if(rows < 100){
						IdNum = String.format("%02d",rows+1);
						Id = String.format("MEM"+IdNum);
					} else if(rows <1000){
						Id = String.format("MEM"+IdNum);
					}
					String Name = inputNama.getText();
					String Phone = inputPhone.getText();
					String Email = inputEmail.getText();
					String Alamat = inputAddress.getText();
					
					con.validateRegister(Id, Name, Phone, Email, Alamat);
				
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == regBtn) {
			if (!inputNama.getText().isEmpty() && !inputPhone.getText().isEmpty() && !inputEmail.getText().isEmpty() && !inputAddress.getText().isEmpty()) {
				insertData();
				JOptionPane.showMessageDialog(this, "Member berhasil diregistrasi!");
			} else {

			}

		} else {

		}
		
	}

}
