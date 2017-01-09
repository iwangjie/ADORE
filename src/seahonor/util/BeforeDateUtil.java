package seahonor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adore on 2016/11/11.
 */
public class BeforeDateUtil {
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
            beforeDay = "9000-01-01";
        return beforeDay;
    }
}
