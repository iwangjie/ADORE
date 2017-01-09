package seahonor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SerialNumberUtil {

	public String getSerailNO(String nowDay){

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
}