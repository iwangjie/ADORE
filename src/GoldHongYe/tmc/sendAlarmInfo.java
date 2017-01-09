package GoldHongYe.tmc;
import weaver.general.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import javax.mail.*;

/**
 * Created by adore on 16/6/2.
 */


public class sendAlarmInfo extends BaseBean{
    public boolean sendhtmlProxy(int id,String to,String subject,String body,String sendToId){
        char flag=Util.getSeparator();
        String para="";
        para=id+""+flag+to+flag+subject+flag+body+flag+"0"+sendToId;
        weaver.conn.RecordSet recordSet = new  weaver.conn.RecordSet();
        recordSet.executeProc("MailSendRecord_Insert",para);
        return true;
    }

    public static void main(String[] args) {

        Date newdate = new Date() ;
        long datetime = newdate.getTime() ;
        Timestamp timestamp = new Timestamp(datetime) ;

        sendAlarmInfo sAI = new sendAlarmInfo();

        System.out.print("flag="+sAI.sendhtmlProxy(1,"songshawyx@163.com","测试邮件发送","测试邮件主题","1"));

    }
}

