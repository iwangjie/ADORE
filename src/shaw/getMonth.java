package shaw;


import java.util.Calendar;

/**
 * Created by adore on 16/7/13.
 */
public class getMonth {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(cal.MONTH);
        int dd = cal.get(cal.DATE);
        System.out.println("m="+month+"|date="+dd);
    }

}
