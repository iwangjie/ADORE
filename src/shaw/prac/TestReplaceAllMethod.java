package shaw.prac;

/**
 * Created by adore on 16/8/1.
 */
public class TestReplaceAllMethod {

    public static void main(String[] args) {
        String s = "111.00000";
        int num_dot = s.indexOf(".");
        System.out.println("С����λ����"+num_dot);
        if(num_dot > 0){
            //������
            //s = s.replaceAll("0+?$", "");//ȥ���������õ���
            String s1 = s.substring(0,num_dot);//��С�������ȫ������ȥ��С����
            System.out.println(s1);
        }

        String today_date = "��";
        //today_date = today_date.substring(5,7);
        System.out.println("today_date="+today_date+"|"+today_date.length());
        if(today_date.length()>10){
            today_date = today_date.substring(0,10);
            System.out.println("today_date="+today_date+"|"+today_date.length());
        }
    }
}
