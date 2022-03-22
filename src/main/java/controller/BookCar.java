package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.book.BookDAO;
import model.book.D_Book;

/**
 * Servlet implementation class BookCar
 */
@WebServlet("/bookCar")
public class BookCar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ajax로 넘긴  예약 최종 파라미터들 받기
		String c_number = (String) request.getParameter("c_number");
		String c_model = (String) request.getParameter("c_model");
		String c_address = (String) request.getParameter("c_address");
		
		String user_id = (String) request.getParameter("user_id");
		String date_start =  request.getParameter("date_start");
		String date_end = (String) request.getParameter("date_end");
		String total_price = request.getParameter("total_price");
		
		// 날짜로 변
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		try {
//			Date date_start_date = fm.parse(date_start);
//			Date date_end_date = fm.parse(date_end);
		
		int insurance = Integer.parseInt(request.getParameter("insurance"));
		total_price = Integer.toString(Integer.parseInt(total_price) + insurance);
		
		
		System.out.println("ajax 테스트 ");
		System.out.println("c_number :" + c_number);
		System.out.println("date_start : " + date_start);
		System.out.println("date_end : " + date_end);
		System.out.println("total_price : " + total_price);
		
		D_Book book = new D_Book();
		book.setC_number(c_number);
		book.setC_model(c_model);
		book.setC_address(c_address);
		book.setUser_id(user_id);
		book.setDate_start(date_start);
		book.setDate_end(date_end);
//		book.setDate_start(date_start_date);
//		book.setDate_end(date_end_date);
		book.setTotal_price(total_price);
		
		BookDAO bookDAO = new BookDAO();
		boolean flag = bookDAO.insertBook(book);
		PrintWriter out = response.getWriter();
		
		if(flag) {
			out.println("success");
			System.out.println("usable");
		}else {
			out.println("noUsable");
		}
		// try end
		}catch(Exception e) {
			
		}
		
	}
}

