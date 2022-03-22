package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CarDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public CarDAO(){
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List<D_Car> listCar(String sql){
		List<D_Car> list = new ArrayList<D_Car>();
		// 금액 형식 지정
		DecimalFormat formatter = new DecimalFormat("###,###");
		
		try {
			con = dataFactory.getConnection();
			
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String c_number = rs.getString("c_number");
				String c_model = rs.getString("c_model");
				String address = rs.getString("address");
				String c_price = rs.getString("c_price");
				
			
				D_Car dcar = new D_Car();
				dcar.setC_number(c_number);
				dcar.setC_model(c_model);
				dcar.setAddress(address);
				dcar.setC_price(formatter.format(Integer.parseInt(c_price)));
				
				list.add(dcar);
				}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return list;
	}
	public D_Car selectOneCar(String c_number) {
		D_Car dcar = new D_Car();
		String sql = "select * from d_car where c_number='" + c_number + "'";
		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String c_model = rs.getString("c_model");
			String address = rs.getString("address");
			String c_price = rs.getString("c_price");
				
			dcar.setC_number(c_number);
			dcar.setC_model(c_model);
			dcar.setAddress(address);
			dcar.setC_price(c_price);
			
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dcar;
	}
	public boolean carCheck(String c_number) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String sql = "select decode(count(*),1,'true','false') as result from d_car where c_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, c_number);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			result = Boolean.parseBoolean(rs.getString("result"));
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void insertCar(D_Car dcar) {
		try {
			con = dataFactory.getConnection();
			String sql = "insert into d_car (c_number, c_model, address, c_price) VALUES(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dcar.getC_number());
			pstmt.setString(2, dcar.getC_model());
			pstmt.setString(3, dcar.getAddress());
			pstmt.setString(4, dcar.getC_price());
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
