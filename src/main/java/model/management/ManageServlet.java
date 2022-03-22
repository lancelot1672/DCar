package model.management;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarDAO;
import model.D_Car;

/**
 * Servlet implementation class ManageServlet
 */
@WebServlet("/manage.do/*")
public class ManageServlet extends HttpServlet {
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
		
		String action = request.getPathInfo();
		System.out.println("action :: " + action);
		
		String nextPage = null;
		
		if(action == null || action.equals("/mainScreen")){
			nextPage = "/jsp/mainPage.jsp";
		}else if(action.equals("/addCar")) {
			String c_number = request.getParameter("c_number");
			String c_model = request.getParameter("c_model");
			String c_address = request.getParameter("address");
			String c_price = request.getParameter("c_price");
			
			D_Car dcar = new D_Car();
			dcar.setC_number(c_number);
			dcar.setC_model(c_model);
			dcar.setAddress(c_address);
			dcar.setC_price(c_price);
			
			CarDAO carDAO = new CarDAO();
			carDAO.insertCar(dcar);
			
			nextPage = "/member/mainScreen2.jsp";
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}

