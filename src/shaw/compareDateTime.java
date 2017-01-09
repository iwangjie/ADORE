package shaw;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adore on 16/4/7.
 */
public class compareDateTime {
    public static void main(String [] args){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String nowDate=sdf.format(new Date());
        String loginTime="2016-04-08 09:22:01";
        Boolean isbefore7=false;
        try {

            Date date1 = df.parse(nowDate+" 07:00:00");
            Date date2=df.parse(loginTime);
            System.out.println("date1="+date1);
            System.out.println("date2="+date2);
            if(date1.compareTo(date2)<=0) {
                isbefore7=true;
            }else{
                isbefore7=false;
            }

        }catch (Exception e){

        }
        System.out.print("isbefore7="+isbefore7);
    }

}
