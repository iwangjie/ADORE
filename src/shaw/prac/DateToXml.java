package shaw.prac;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by adore on 2016/12/28.
 */
public class DateToXml {
    /**
     * Date 转换为XMLGregorianCalendar类型
     */
    public static Date str2Date(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // String转Date
        str = "2007-1-18";
        try {
            date = format.parse(str); // Thu Jan 18 00:00:00 CST 2007
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static XMLGregorianCalendar long2Gregorian(Date date) {
        DatatypeFactory dataTypeFactory;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        return dataTypeFactory.newXMLGregorianCalendar(gc);
    }

    public static void main(String[] args) {
        String code = "11.04.016";
        code = code.replace(".","").substring(3,7);
        System.out.print("日期=" + code);
        //System.out.print("日期=" + long2Gregorian(str2Date("2016-12-18")));

    }
}
