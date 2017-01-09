package shaw.prac;

/**
 * Created by adore on 16/8/1.
 */
public class TestReplaceAllMethod {

    public static void main(String[] args) {
        String s = "111.00000";
        int num_dot = s.indexOf(".");
        System.out.println("小数点位置在"+num_dot);
        if(num_dot > 0){
            //正则表达
            //s = s.replaceAll("0+?$", "");//去掉后面无用的零
            String s1 = s.substring(0,num_dot);//如小数点后面全是零则去掉小数点
            System.out.println(s1);
        }

        String today_date = "张";
        //today_date = today_date.substring(5,7);
        System.out.println("today_date="+today_date+"|"+today_date.length());
        if(today_date.length()>10){
            today_date = today_date.substring(0,10);
            System.out.println("today_date="+today_date+"|"+today_date.length());
        }
    }
}
