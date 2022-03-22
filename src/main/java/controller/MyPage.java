package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.book.BookDAO;
import model.book.D_Book;

/**
 * Servlet implementation class MyPage
 */
@WebServlet("/myPage")
public class MyPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String period = request.getParameter("period");
		String date = request.getParameter("date");
		String user_id = request.getParameter("user_id");
		// test
		System.out.println("period :: " + period);
		System.out.println("date :: " + date);
		
		List<D_Book> bookList = new ArrayList<D_Book>();
		
		try {
			BookDAO bookDAO = new BookDAO();
			D_Book db = new D_Book();

			bookList = (ArrayList) bookDAO.selectBookByPeriod(period, date, user_id);

			// JSON 생성
			JSONObject obj1 = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			// JSON 에 값 저장
			for(int i =0; i< bookList.size(); i++) {
				JSONObject obj2 = new JSONObject();
				//obj2는 반드시 for문 안에 놓아야 한다. 그래야 중복이 안생긴다.
				
				db = bookList.get(i);
				
				obj2.put("c_number", db.getC_number());
				obj2.put("c_model", db.getC_model());
				obj2.put("c_address", db.getC_address());
				obj2.put("date_start", db.getDate_start().toString());
				obj2.put("date_end", db.getDate_end().toString());
				obj2.put("total_price", db.getTotal_price());
				
				jsonArray.add(obj2);
					
			}
			obj1.put("result", jsonArray);
			String resp = obj1.toString();
			response.getWriter().print(resp);
			System.out.println(resp);
			
		}catch(Exception e) {
			e.getMessage();
		}
	}
}
