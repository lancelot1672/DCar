package model.book;

import java.util.Date;

public class D_Book {
	private String c_number;
	private String c_model;
	private String date_start;
	private String date_end;
	private String c_address;
	private String user_id;
	private String total_price;
	
	public D_Book() {
		
	}
	public D_Book(String c_number,String c_model) {
		this.c_number = c_number;
		this.c_model = c_model;
		
	}
	
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	public String getC_number() {
		return c_number;
	}

	public void setC_number(String c_number) {
		this.c_number = c_number;
	}

	public String getC_model() {
		return c_model;
	}

	public void setC_model(String c_model) {
		this.c_model = c_model;
	}

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getC_address() {
		return c_address;
	}
	public void setC_address(String c_address) {
		this.c_address = c_address;
	}
}