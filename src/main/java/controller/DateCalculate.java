package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCalculate {

	public int DateCalculate(String date1, String date2) {
		long calDateDays = 0;
		try {
			//String Type을 Date Type으로 캐스팅하면서 생기는 예외 때문에
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			
			Date firstDate = format.parse(date1);
			Date secondDate = format.parse(date2);
			
			long calDate = firstDate.getTime() - secondDate.getTime();
			
			calDateDays = calDate / (24*60*60*1000);
			
			calDateDays = Math.abs(calDateDays);
			
		}catch(Exception e) {
			
		}
		return (int) calDateDays;
	}
}
