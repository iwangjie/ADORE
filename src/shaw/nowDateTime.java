package shaw;

/**
 * Created by adore on 16/4/6.
 */

import java.util.Date;
import java.text.SimpleDateFormat;


public class nowDateTime {

    public static void main(String[] args) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String nowDate=df.format(new Date());
        //System.out.println("��ǰʱ��Ϊ"+nowDate);// new Date()Ϊ��ȡ��ǰϵͳʱ��
        try {
            Date date1 = df.parse("2016-04-07 00:00:00");
            Date date2 = df.parse(nowDate);
            if(date1.compareTo(date2)<0){
                System.out.println("���ڵ�ǰʱ��");
            }
        }catch (Exception e){

        }

        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
        String nowDate2=sdf.format(new Date());
        Boolean isbefore7=false;
        try {

            Date date1 = df.parse(nowDate+" 07:00:00");
            Date loginDate=df.parse(nowDate);
            System.out.println("date1="+date1);
            System.out.println("loginDate="+loginDate);
            if(date1.compareTo(loginDate)>0) {
                System.out.print("isbefore7_1="+isbefore7);
                isbefore7=true;
                System.out.print("isbefore7_2="+isbefore7);
            }else{
                System.out.print("isbefore7_3="+isbefore7);
            }

            System.out.print("isbefore7_4="+isbefore7);

        }catch (Exception e){

        }
    }
}
