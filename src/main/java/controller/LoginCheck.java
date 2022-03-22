package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.D_Member;
import model.MemberDAO;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/loginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDAO memberDAO = new MemberDAO();
		PrintWriter out = response.getWriter();
		
		String user_id = (String) request.getParameter("user_id");
		String user_pw = (String) request.getParameter("user_pw");
		System.out.println("user_id = " + user_id);
		System.out.println("user_pw = " + user_pw);
		
		HttpSession session = request.getSession();
		
		boolean loginCheck = memberDAO.loginCheck(user_id, user_pw);
		
		
		if(loginCheck == true) {
			out.print("login_O");
			System.out.println("login_O");
			
			D_Member member = memberDAO.searchMember(user_id, user_pw);
			session.setAttribute("login", "login");
			session.setAttribute("member", member);
			
		}else {
			out.print("login_X");
			System.out.println("login_X");
		}
	}

}
