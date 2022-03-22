package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public MemberDAO(){
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List<D_Member> listMembers(){
		List<D_Member> list = new ArrayList<D_Member>();
		try {
			con = dataFactory.getConnection();
			String sql = "select * from d_member where user_id != 'admin'";
			
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				D_Member member = new D_Member();
				
				member.setUser_id(rs.getString("user_id"));
				member.setUser_name(rs.getString("user_name"));
				member.setEmail(rs.getString("email"));
				member.setBirth(rs.getString("birth"));
				member.setPhone(rs.getString("phone"));
				member.setJoinDate(rs.getDate("joinDate"));
				
				list.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public D_Member searchMember(String id, String pw) {
		D_Member member = new D_Member();
		try {
			con = dataFactory.getConnection();
			String sql = "select * from d_member";
			sql += " where user_id=? and user_pw=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			member.setUser_id(rs.getString("user_id"));
			member.setUser_pw(rs.getString("user_pw"));
			member.setUser_name(rs.getString("user_name"));
			member.setEmail(rs.getString("email"));
			member.setBirth(rs.getString("birth"));
			member.setPhone(rs.getString("phone"));

			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return member;
	}
	public D_Member selectById(String id) {
		D_Member member = new D_Member();
		try {
			con = dataFactory.getConnection();
			String sql = "select * from d_member";
			sql += " where user_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			member.setUser_id(rs.getString("user_id"));
			member.setUser_pw(rs.getString("user_pw"));
			member.setUser_name(rs.getString("user_name"));
			member.setEmail(rs.getString("email"));
			member.setBirth(rs.getString("birth"));
			member.setPhone(rs.getString("phone"));

			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return member;
	}	
	public void insertMember(D_Member dm) {
		try {
			con = dataFactory.getConnection();
			String sql = "insert into d_member (user_id, user_pw, user_name, email, birth, phone) "
					+ "VALUES(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dm.getUser_id());
			pstmt.setString(2, dm.getUser_pw());
			pstmt.setString(3, dm.getUser_name());
			pstmt.setString(4, dm.getEmail());
			pstmt.setString(5, dm.getBirth());
			pstmt.setString(6, dm.getPhone());

			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean loginCheck(String id, String pw) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String sql = "select decode(count(*),1,'true','false') as result from d_member";
			sql += " where user_id=? and user_pw=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			result = Boolean.parseBoolean(rs.getString("result"));
			
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean idCheck(String id) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String sql = "select decode(count(*),1,'true','false') as result from d_member";
			sql += " where user_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			result = Boolean.parseBoolean(rs.getString("result"));
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

