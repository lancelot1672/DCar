package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarDAO;
import model.D_Car;

@WebServlet("/searchCar")
public class SearchCar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CarDAO carDAO = new CarDAO();
		PrintWriter out = response.getWriter();
		
		String address = (String) request.getParameter("address");
		System.out.println("address = " + address);
		
		String sql = "select * from d_car where address='" + address + "'";
		List<D_Car> list = carDAO.listCar(sql);
		
	}
}
