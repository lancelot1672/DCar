package model.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BookDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public BookDAO(){
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List listAllBook(String user_id) {					// 아이디에 따른 예약 검색 
		List<D_Book> bookList = new ArrayList<D_Book>();
		String sql = "select * from dcar_book where user_id = '" + user_id +"'";
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String c_number = rs.getString("c_number");
				String c_model = rs.getString("c_model");
				String c_address = rs.getString("c_address");
				Date date_start = rs.getDate("date_start");
				Date date_end = rs.getDate("date_end");
			
				String total_price = rs.getString("total_price");
				// 금액 형식 지정
				DecimalFormat formatter = new DecimalFormat("###,###");
			
				D_Book dbook = new D_Book();
				dbook.setC_number(c_number);
				dbook.setC_model(c_model);
				dbook.setC_address(c_address);
				dbook.setDate_start(dateFormat.format(date_start));
				dbook.setDate_end(dateFormat.format(date_end));
				dbook.setTotal_price(formatter.format(Integer.parseInt(total_price)));
				bookList.add(dbook);
				}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return bookList;
	}
	public List SelectBook(String address, String date2_start, String date2_end) {			// 검색시 호출되는 메서드 
		// 예약이 불가능한 차량만 가져오
		List<String> bookList = new ArrayList<String>();
		String sql = "select DISTINCT c_number from dcar_book where c_address='" + address
				+ "' and ('" + date2_start + "' <= TO_CHAR(date_end,'YYYY-MM-DD') and ('" + date2_end + "' >= TO_CHAR(date_start,'YYYY-MM-DD')))";
		
		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String c_number = rs.getString("c_number");
				System.out.println(c_number);
				bookList.add(c_number);
			}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return bookList;
	}
	public List selectBookByPeriod(String period, String date, String user_id) {
		System.out.println(period);
		System.out.println(date.toString());
		System.out.println("user_id :: " + user_id);
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		
		List<D_Book> bookList = new ArrayList<D_Book>();
		String sql = "";
		if(period.equals("before")) {
			sql = "select * from dcar_book where user_id = ? and ? >= TO_CHAR(date_end,'YYYY-MM-DD') ";
			
		}else if(period.equals("after")) {
			sql = "select * from dcar_book where user_id = ? and ? <= TO_CHAR(date_start,'YYYY-MM-DD') ";
		}
		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, date);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String c_number = rs.getString("c_number");
				String c_model = rs.getString("c_model");
				String c_address = rs.getString("c_address");
				Date date_start = rs.getDate("date_start");
				Date date_end = rs.getDate("date_end");
				String total_price = rs.getString("total_price");
				
				D_Book dbook = new D_Book();
				dbook.setC_number(c_number);
				dbook.setC_model(c_model);
				dbook.setC_address(c_address);
				dbook.setDate_start(dateFormat.format(date_start));
				dbook.setDate_end(dateFormat.format(date_end));
				dbook.setTotal_price(total_price);
				bookList.add(dbook);
				}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return bookList;
	}
	public Boolean insertBook(D_Book book) {
		try {
			con = dataFactory.getConnection();	
			String sql = "insert into dcar_book (c_number, c_model, c_address, user_id, date_start, date_end, total_price) VALUES (?,?,?,?, TO_DATE(?,'YYYY-MM-DD') , TO_DATE(?,'YYYY-MM-DD') ,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, book.getC_number());
			pstmt.setString(2, book.getC_model());
			pstmt.setString(3, book.getC_address());
			pstmt.setString(4, book.getUser_id());
			pstmt.setString(5, book.getDate_start());
			pstmt.setString(6, book.getDate_end());
			pstmt.setString(7, book.getTotal_price());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.getMessage();
		}
		return true;
	}
}
