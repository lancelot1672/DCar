package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CarDAO;
import model.D_Car;
import model.D_Member;
import model.MemberDAO;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/")
public class MainServlet extends HttpServlet {
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
		
		CarDAO carDAO = new CarDAO();
		MemberDAO memberDAO = new MemberDAO();
		
		String action = request.getPathInfo();
		System.out.println("action :: " + action);
		
		String nextPage = null;
		
		if(action == null || action.equals("/mainScreen")){
			String sql = "select * from d_car";
			List<D_Car> carList = carDAO.listCar(sql);
			System.out.println("abc");
			request.setAttribute("carList", carList);
			
			nextPage = "/jsp/mainPage.jsp";
		}
		else if(action.equals("/login")) {
			nextPage = "/jsp/loginForm.jsp";
			
		}else if(action.equals("/logout")) {
			HttpSession session = request.getSession();
			session.removeAttribute("member");
			session.removeAttribute("logOn");
			
			nextPage = "/jsp/mainPage.jsp";
		}
		else if(action.equals("/addMember")) {
			String id = request.getParameter("user_id");
			String pw = request.getParameter("user_pw");
			String name = request.getParameter("user_name");
			String phone = request.getParameter("phone");
			String birth = request.getParameter("birth");
			String email = request.getParameter("email");
			
			D_Member dm = new D_Member();
			dm.setUser_id(id);
			dm.setUser_pw(pw);
			dm.setUser_name(name);
			dm.setPhone(phone);
			dm.setBirth(birth);
			dm.setEmail(email);
			
			memberDAO.insertMember(dm);
			nextPage = "/jsp/mainPage.jsp";
			
		}else if(action.equals("/addMemberForm")) {
			nextPage = "/jsp/jspForm2.jsp";
		
		}else if(action.equals("/check")) {
			String do_ = request.getParameter("do_");
			String si = request.getParameter("si");
			String gu = request.getParameter("gu");
			String dong = request.getParameter("dong");
			
			String address = do_ + " " + si + " " + gu + " " + dong;
			String sql = "select * from d_car where address='" + address + "'";
			List<D_Car> carList = carDAO.listCar(sql);
			
			String date_start = request.getParameter("start");
			String date_end = request.getParameter("end");
			
			request.setAttribute("carList", carList);
			System.out.println(date_start);
			
			nextPage = "/jsp/mainPage.jsp";
			
		}else if(action.equals("/book")) {
			String c_number = request.getParameter("c_number");
			String date_start = request.getParameter("date_start");
			String date_end = request.getParameter("date_end");
			
			System.out.println(c_number);
			System.out.println(date_start);
			System.out.println(date_end);
			
			HttpSession session = request.getSession();
//			String user_id = (String) session.getAttribute("member");
//			
//			D_Member dm = memberDAO.selectById(user_id);	// 예약시 필요한 회원정보
			D_Car dcar = carDAO.selectOneCar(c_number);		// 예약시 필요한 차량 정보
			
	//		request.setAttribute("member", dm);
			request.setAttribute("car", dcar);
			request.setAttribute("date_start", date_start);
			request.setAttribute("date_end", date_end);
			
			nextPage = "/jsp/bookPage.jsp";
			
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
