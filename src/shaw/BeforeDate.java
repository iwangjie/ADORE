package shaw;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        String date1 = "2006-12-10";

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        try {
            Date d1 = df.parse(date1);
            System.out.println("d1=="+df.format(d1));
            Calendar  g = Calendar.getInstance();
            g.setTime(d1);
            g.add(Calendar.MONTH,+1);
            Date d2 = g.getTime();
            System.out.println("d2======="+df.format(d2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String s = "my.test.txt";
        System.out.println(s.replace(".", "#"));
        System.out.println(s.replaceAll("\\.", "#"));
        System.out.println(s.replaceFirst(".", "#"));
    }
}
