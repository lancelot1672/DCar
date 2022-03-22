package model.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SummaryDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public SummaryDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int selectTotal_account() {
		int count = 0;
		try {
			
			con = dataFactory.getConnection();	
			
			
			String sql = "select count(*) from d_member";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return count;
	}
	public int selectNew_account() {
		int count = 0;
		try {
			
			con = dataFactory.getConnection();	
			
			
			String sql = "select count(*) from d_member where ";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return count;
	}
	public int selectTotal_book() {			// 전체 예약 수
		int count = 0;
		try {
			
			con = dataFactory.getConnection();	
			
			
			String sql = "select count(*) from dcar_book";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return count;
	}
	public int selectMonth_Book() {			//월별 예약 수
		int total = 0;
		
		Date today = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy");
		SimpleDateFormat m = new SimpleDateFormat("MM");
		String year = y.format(today);
		String month = m.format(today);
		String date1 =  year + "/" + month + "/01";
		String date2 = "";
		
		if(m.format(today).toString().equals("12")) {
			year = Integer.toString(Integer.parseInt(year) + 1);
			month = "01";
			date2 = year + "/" + month + "/01"; 
		}else {
			month = Integer.toString(Integer.parseInt(month) + 1);
			date2 = year + "/" + month + "/01";
		}
		try {	
			con = dataFactory.getConnection();	
			
			String sql = "select count(*) from dcar_book where date_start > ? and date_start < ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt(1);
			
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return total;
	}
	public int selectYear_Book() {			//  예약 수
		int total = 0;
	
		Date today = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy");

		String year = y.format(today);

		String date1 =  year + "01/01";
		String date2 = Integer.toString(Integer.parseInt(year) + 1) + "01/01";
		
		try {	
			con = dataFactory.getConnection();	
			
			String sql = "select count(*) from dcar_book where date_start > ? and date_start < ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt(1);
			
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return total;
	}
	public int selectTotal_sales() {		// 총 매출 내역
		int total = 0;
		try {
			
			con = dataFactory.getConnection();	
			
			
			String sql = "select total_price from dcar_book";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String total_price = rs.getString("total_price");
				total += Integer.parseInt(total_price);
			}
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return total;
	}
	public int selectMonth_sales() {		// 이번 달 매출 내역
		int total = 0;
		Date today = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy");
		SimpleDateFormat m = new SimpleDateFormat("MM");
		String year = y.format(today);
		String month = m.format(today);
		String date1 =  year + "/" + month + "/01";
		String date2 = "";
		if(m.format(today).toString().equals("12")) {
			year = Integer.toString(Integer.parseInt(year) + 1);
			month = "01";
			date2 = year + "/" + month + "/01"; 
		}else {
			month = Integer.toString(Integer.parseInt(month) + 1);
			date2 = year + "/" + month + "/01";
		}
		
		try {
			
			con = dataFactory.getConnection();	
			
			String sql = "select total_price from dcar_book where date_start > ? and date_start < ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String total_price = rs.getString("total_price");
				total += Integer.parseInt(total_price);
			}
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return total;
	}
	public int selectYear_sales() {
		int total = 0;
		Date today = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy");

		String year = y.format(today);

		String date1 =  year + "01/01";
		String date2 = Integer.toString(Integer.parseInt(year) + 1) + "01/01";
		
		try {
			
			con = dataFactory.getConnection();	
			
			String sql = "select total_price from dcar_book where date_start > ? and date_start < ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String total_price = rs.getString("total_price");
				total += Integer.parseInt(total_price);
			}
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			
		}
		return total;
	}
	
}

