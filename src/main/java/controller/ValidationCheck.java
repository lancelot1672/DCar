package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDAO;

/**
 * Servlet implementation class ValidationCheck
 */
@WebServlet("/validationCheck")
public class ValidationCheck extends HttpServlet {
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
		
		String id = (String) request.getParameter("id");
		System.out.println("id = " + id);
		
		// id Validation Check
		boolean overlappedID = memberDAO.idCheck(id);
		
		// Return ajax 
		if(overlappedID == true) {
			out.print("not_usable");
		}else {
			out.print("usable");
		}
	}

}

