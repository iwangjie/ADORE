package gvo.mobile;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.text.SimpleDateFormat;
import java.util.Date;

import static gvo.mobile.IpV4Util.compareIpV4s;

public class LoginExceptionLoadJob extends BaseCronJob {
	
	
	public void execute() {
		SendResult sr = new SendResult();
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String sendMessage = "";
		Boolean check1;
		Boolean check2;
		Boolean check3;
		Boolean check4;
		Boolean check5;
		Boolean check6;
		log.writeLog("LoginExceptionLoadJob Job Start! ");
		//String sql_1 = "select * from formtable_main_332 where uq='MobileLogEx'";
		String sql_1 = "select * from formtable_main_339 where uq='MobileLogEx'";
		rs.executeSql(sql_1);
		if(rs.next()){
			sendMessage = Util.null2String(rs.getString("remark"));
			sendMessage = sendMessage.replace("<br>", " ").replace("&nbsp;", "");
		}
		
		String sql = " select si.id as fid,si.clientaddress,hr.id as empID,hr.lastname,hr.mobile,hr.seclevel,"
					+" operatedate||' '||operatetime as time1 from sysmaintenancelog si "
					+" join hrmresource hr on(si.operateuserid=hr.id) "
					+" where operatedate>'2016-04-27' and operatetype=6 and clientaddress!='mobile' AND si.ID NOT IN "
					+"(SELECT CHECKID FROM gvo_check_data_info WHERE TYPECHECK='LG')order by si.id";
		rs.executeSql(sql);
		while(rs.next()){
			String uid = Util.null2String(rs.getString("fid"));
			
			sql = "insert into gvo_check_data_info(checkid,typecheck) values("+uid+",'LG')";
			rs1.executeSql(sql);
			
			String clientaddress = Util.null2String(rs.getString("clientaddress"));


			// ip 判断  172.16.*  ； 106.120.97.22 ； 58.210.121.68； 10.80.*  222.92.108.194	 58.240.226.123

			String empID = Util.null2String(rs.getString("empID"));
			String lastname = Util.null2String(rs.getString("lastname"));
			String seclevel = Util.null2String(rs.getString("seclevel"));
			String mobile = Util.null2String(rs.getString("mobile"));
			String time1 = Util.null2String(rs.getString("time1"));

			//判断是否需要短信提醒
			int num_cc=0;
			String aqjb="";
			//String sql_x=" select count(*) as num_cc from formtable_main_333 where userid="+empID+" and isremind=0 ";
			String sql_x=" select count(*) as num_cc from formtable_main_340 where userid="+empID+" and isremind=0 ";
			rs1.executeSql(sql_x);
			if(rs1.next()){
				num_cc=rs1.getInt("num_cc");
			}

			//String sql_y=" select aqjb from formtable_main_334 where sfyx=0 ";
			String sql_y=" select aqjb from formtable_main_341 where sfyx=0 ";
			rs1.executeSql(sql_y);
			if(rs1.next()){
				aqjb = Util.null2String(rs1.getString("aqjb"));
				//log.writeLog("安全级别="+aqjb);
			}

			if("".equals(aqjb)) aqjb="30";

			//if(!"吴春艳".equals(lastname)&&!"李争".equals(lastname)&&!"张盼盼".equals(lastname)){
				//continue;
			//}

			if(seclevel.compareTo(aqjb)<0&&num_cc==0){
				continue;
			}

			log.writeLog("姓名="+lastname);
			//log.writeLog("登录IP="+clientaddress);

			String ip1 = "106.120.97.22";
			String ip2 = "58.210.121.68";
			String ip3 = "222.92.108.194";
			String ip4 = "58.240.226.123";
			String ip5_1 = "172.16.0.0";
			String ip5_2 = "172.16.255.255";
			String ip6_1 = "10.80.0.0";
			String ip6_2 = "10.80.255.255";

			if(compareIpV4s(clientaddress, ip1) == 0){
				check1=true;
			}else{
				check1=false;
			}

			//log.writeLog("check1="+check1);

			if(compareIpV4s(clientaddress, ip2) == 0){
				check2=true;
			}else{
				check2=false;
			}

			//log.writeLog("check2="+check2);

			if(compareIpV4s(clientaddress, ip3) == 0){
				check3=true;
			}else{
				check3=false;
			}

			//log.writeLog("check3="+check3);

			if(compareIpV4s(clientaddress, ip4) == 0){
				check4=true;
			}else{
				check4=false;
			}

			//log.writeLog("check4="+check4);

			//判断ip是否在ip1和ip2范围中
			if(((compareIpV4s(clientaddress, ip5_1)) <= 0) && (compareIpV4s(clientaddress, ip5_2) >= 0)) {
				check5=true;
			}
			else {
				check5=false;
			}

			//log.writeLog("check5="+check5);

			//判断ip是否在ip1和ip2范围中
			if(((compareIpV4s(clientaddress, ip6_1)) <= 0) && (compareIpV4s(clientaddress, ip6_2) >= 0)) {
				check6=true;
			}
			else {
				check6=false;
			}

			//log.writeLog("check6="+check6);



			if(check1||check2||check3||check4||check5||check6){
				//log.writeLog("result=正常登录!!!!!!!!!!");
				continue;
			}

			log.writeLog("登录异常!!!!!!!!!!");
			
			String tmp_sql = "select clientaddress,operatedate||' '||operatetime as time2 "
					+" from sysmaintenancelog where id in(select max(id) from sysmaintenancelog "
					+"where operatetype=6 and clientaddress!='mobile' "
					+"AND operateuserid="+empID+" AND ID < "+uid+")";
			rs1.executeSql(tmp_sql);
			String loginTime = "";
			String lastMax_address = "";
			if(rs1.next()){
				lastMax_address = Util.null2String(rs1.getString("clientaddress"));
				loginTime =  Util.null2String(rs1.getString("time2"));
			}

			log.writeLog("last_address="+lastMax_address);
			//log.writeLog("time1="+time1);

			if(!"".equals(lastMax_address)){
				//  和上次信息登录的IP不一致。
				if(!clientaddress.equalsIgnoreCase(lastMax_address)){
					//  如果当前时间是00:00　~ 07:00   延迟发端  统一到早上07:00 以后发

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
					String nowDate=sdf.format(new Date());
					Boolean isbefore7=false;
					try {

						Date date1 = df.parse(nowDate+" 07:00:00");
						Date loginDate=df.parse(time1);
						if(date1.compareTo(loginDate)<=0) {
							isbefore7=true;
						}else{
							isbefore7=false;
						}

					}catch (Exception e){

					}

					//log.writeLog("isbefore7="+isbefore7);

					if(isbefore7){
						// 拿本次登录IP 和  时间
						String tmp_mess = sendMessage.replace("#IP#", clientaddress)
									.replace("#loginTime#", time1).replace("#LIP#",lastMax_address)
								.replace("#lloginTime#",loginTime);
						
						sql  = "insert into gvo_send_message_info(id,phoneno,sendmessage,whosend,systmwho,"
							  +"createtime,sendtime,fromwhere,active_flag,LastloginTime,loginTime) "
							  +"values(gvo_send_message_info_seqno.nextval,'"+mobile+"','"
							  +tmp_mess+"','"+lastname+"',"+empID+",to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),"
							  +"to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'LG',2,'"
							  +loginTime+"','"+time1+"')";
						rs1.executeSql(sql);
						//log.writeLog("sql = " + sql);
						
						sr.sendSMS("", mobile, tmp_mess);
						//sr.sendSMS("", "18913268242", tmp_mess);
						log.writeLog("Message send");
					}else{
						// 拿本次登录IP 和  时间
						String tmp_mess = sendMessage.replace("#IP#", clientaddress)
								.replace("#loginTime#", time1).replace("#LIP#",lastMax_address)
								.replace("#lloginTime#",loginTime);

						sql  = "insert into gvo_send_message_info(id,phoneno,sendmessage,whosend,systmwho,"
								+"createtime,sendtime,fromwhere,active_flag,LastloginTime,loginTime) "
								+"values(gvo_send_message_info_seqno.nextval,'"+mobile+"','"
								+tmp_mess+"','"+lastname+"',"+empID+",to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),"
								+"null,'LG',0,'" +loginTime+"','"+time1+"')";
						rs1.executeSql(sql);
						//log.writeLog("sql = " + sql);

						sql=" update gvo_send_message_info set delaySendtIime=to_char(sysdate,'yyyy-mm-dd')||' 07:10:00' where " +
								"active_flag=0 and systmwho="+empID+" and loginTime='"+time1+"' ";
						rs1.execute(sql);
						log.writeLog("延迟至上午7点以后发送短信");
					}
				}else{
					log.writeLog("和上一次登录IP相同,不需要重复提醒");
				}
			}else{
				log.writeLog("IP地址获取错误!");
			}
		}
	}
}
