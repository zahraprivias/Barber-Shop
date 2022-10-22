package barberShop;

import java.sql.*;

public class Connect {

	private String dbname = "uasbarbershop";
	private String url = "jdbc:mysql://localhost:3306/"+dbname;
	private String user = "root";
	private String password = "";
	
	public Statement stmt;
	public ResultSet rs;
	public ResultSetMetaData rsm;
	public PreparedStatement pStat;
	public Connection con;
	
	public Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong when connecting to the database...");
		}
	}

	public ResultSet execQuery(String query) {
		try {
			rs = stmt.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void validateRegister(String Id, String Name, String Phone, String Email, String Alamat) {
			
			String query = "INSERT INTO member VALUES (?,?,?,?,?)";
			try {
				pStat = con.prepareStatement(query);
				pStat.setString(1, Id);
				pStat.setString(2, Name);
				pStat.setString(3, Phone);
				pStat.setString(4, Email);
				pStat.setString(5, Alamat);
				pStat.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	
	public void updateMember(String memberId, String nama, String phone, String email, String alamat) {
		String query = String.format("UPDATE member SET memberId='%s', nama='%s', phone='%s', email='%s', alamat='%s' WHERE memberId='"+memberId+"'", memberId, nama, phone, email, alamat);
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public void addToCart(String orderId, String memberId, String customerId, String itemId, int qty) {
		String query = "INSERT INTO orderitem (orderId, memberId, customerId, itemId, quantity) VALUES ('"+orderId+"','"+memberId+"','"+customerId+"','"+itemId+"',"+qty+")";
		
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateCart(String orderId, String memberId, String customerId, String itemId, int qty) {
		
		String query = String.format("UPDATE orderitem SET orderId='%s', memberId='%s', customerId='%s', itemId='%s', quantity=%d WHERE orderId='"+orderId+"'", orderId, memberId, customerId, itemId, qty);
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void approveLaporan() {
		String query = "DELETE FROM orderitem";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
