package shaw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by adore on 16/3/14.
 */
public class TestDate {
    public int getMonthDay(String source) {
        //String source = "2007年12月";
        int count = 30;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = format.parse(source);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {

        //DepartmentComInfo departmentComInfo=new DepartmentComInfo();
        // weaver.hrm.company.DepartmentComInfo.getDepartmentname();

        TestDate td = new TestDate();
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前一天
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        System.out.println("最大天数为：" + td.getMonthDay("2017-01"));

        System.out.println("前一天的时间是：" + defaultStartDate);
        System.out.println("生成的时间是：" + defaultEndDate);

        String price1 = "9.80";
        String price2 = "10.18";
        String price3 = "8.29";
        int i1 = price1.compareToIgnoreCase(price2);
        int i2 = price1.compareTo(price3);

        System.out.println("结果1：" + i1);
        System.out.println("结果2：" + i2);

        String result = "";
        for (int i = 0; i < 6; i++) {
            //生成97-122的int型的整型
            int intValue = (int) (Math.random() * 25 + 97);
            //将intValue强制转化成char类型后接到result后面
            result = result + (char) intValue;
        }
        //输出字符串
        System.out.println(result);
        System.out.println("Math.random()=" + Math.random());
    }


}
