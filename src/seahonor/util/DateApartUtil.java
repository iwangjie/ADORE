package seahonor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateApartUtil {
	
	public String nextDay(String nowDay){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nextDay = "";
		try {
			Date nowDate = sdf.parse(nowDay);
			Date nextDate = new Date(nowDate.getTime()+24*3600*1000L);
			nextDay = sdf.format(nextDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if("".equals(nextDay))
			nextDay = "9999-01-01";
		return nextDay;
	}

	public static void main(String[] args) {
		DateApartUtil DAU= new DateApartUtil();
		String newDate=DAU.nextDay("2016-03-14");
		System.out.println("newDate="+newDate);
	}
}
