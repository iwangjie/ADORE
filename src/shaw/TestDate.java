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
        //String source = "2007��12��";
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
        Date dNow = new Date();   //��ǰʱ��
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //�õ�����
        calendar.setTime(dNow);//�ѵ�ǰʱ�丳������
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //����Ϊǰһ��
        dBefore = calendar.getTime();   //�õ�ǰһ���ʱ��

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ
        String defaultStartDate = sdf.format(dBefore);    //��ʽ��ǰһ��
        String defaultEndDate = sdf.format(dNow); //��ʽ����ǰʱ��

        System.out.println("�������Ϊ��" + td.getMonthDay("2017-01"));

        System.out.println("ǰһ���ʱ���ǣ�" + defaultStartDate);
        System.out.println("���ɵ�ʱ���ǣ�" + defaultEndDate);

        String price1 = "9.80";
        String price2 = "10.18";
        String price3 = "8.29";
        int i1 = price1.compareToIgnoreCase(price2);
        int i2 = price1.compareTo(price3);

        System.out.println("���1��" + i1);
        System.out.println("���2��" + i2);

        String result = "";
        for (int i = 0; i < 6; i++) {
            //����97-122��int�͵�����
            int intValue = (int) (Math.random() * 25 + 97);
            //��intValueǿ��ת����char���ͺ�ӵ�result����
            result = result + (char) intValue;
        }
        //����ַ���
        System.out.println(result);
        System.out.println("Math.random()=" + Math.random());
    }


}
