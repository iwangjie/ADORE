package gvo.mobile;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class SendMessageLoadJob extends BaseCronJob {

	
	public void execute() {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SendResult sr = new SendResult();
		
		BaseBean bb = new BaseBean();
		bb.writeLog("SendMessageLoadJob Job Start! ");

		String sql=" select count(id) as num_1 from gvo_send_message_info where sendTime is null ";
		rs.executeSql(sql);

		if(rs.next()){
			int num_1=rs.getInt("num_1");

			if(num_1>0){
				sql = "select * from gvo_send_message_info where sendTime is null "
						+" and delaySendtIime<to_char(sysdate,'yyyy-mm-dd HH24:MI:SS')";
				rs.executeSql(sql);
				while(rs.next()){
					String sendID=Util.null2String(rs.getString("id"));
					String mobile=Util.null2String(rs.getString("phoneno"));
					String sendmessage=Util.null2String(rs.getString("sendmessage"));

					String sql_1=" update gvo_send_message_info set sendTime=to_char(sysdate,'yyyy-mm-dd HH24:MI:SS'),active_flag=2 " +
							" where id="+sendID+" ";
					rs1.executeSql(sql_1);
					sr.sendSMS("", mobile, sendmessage);
					//sr.sendSMS("", "18913268242", sendmessage);
				}
			}else{
				bb.writeLog("No delay message to send!");
			}
		}

	}
}
