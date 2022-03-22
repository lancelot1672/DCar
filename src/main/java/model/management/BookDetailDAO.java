package model.management;

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

public class BookDetailDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public BookDetailDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List selectBookDetail() {
		List<BookDetail> list = new ArrayList<BookDetail>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		
		// 금액 형식 지정
		DecimalFormat formatter = new DecimalFormat("###,###");
		
		try {
			con = dataFactory.getConnection();
			
			String sql = "select * from dcar_book left outer join d_member on dcar_book.user_id = d_member.user_id order by date_start desc";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String c_number = rs.getString("c_number");
				String c_model = rs.getString("c_model");
				String user_name = rs.getString("user_name");
				String phone = rs.getString("phone");
				String c_address = rs.getString("c_address");
				Date date_start = rs.getDate("date_start");
				Date date_end = rs.getDate("date_end");
				String total_price = rs.getString("total_price");
				
				BookDetail db = new BookDetail();
				db.setC_number(c_number);
				db.setC_model(c_model);
				db.setUser_name(user_name);
				db.setPhone(phone);
				db.setC_address(c_address);
				db.setDate_start(dateFormat.format(date_start));
				db.setDate_end(dateFormat.format(date_end));
				db.setTotal_price(formatter.format(Integer.parseInt(total_price)));
				
				list.add(db);
				}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
