package shaw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adore on 16/3/14.
 */
public class BeforeDate {
    public String beforeDay(String nowDay){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beforeDay = "";
        try {
            Date nowDate = sdf.parse(nowDay);
            Date beforeDate = new Date(nowDate.getTime()-24*3600*1000L);
            beforeDay = sdf.format(beforeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if("".equals(beforeDay))
            beforeDay = "9999-01-01";
        return beforeDay;
    }

    public static void main(String[] args) {
        BeforeDate DAU= new BeforeDate();
        String newDate=DAU.beforeDay("2016-03-30");
        System.out.println("newDate="+newDate);
    }
}
