package gvo.work;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class workQINGJIAUPDATEWorkflowAction implements Action {
	private StaticObj staticobj = null;

	public String execute(RequestInfo info) {
		staticobj = StaticObj.getInstance();
		String errorMsg = "";
		new BaseBean().writeLog("进入请假差异申请action_____________________________");

		RecordSet rs = new RecordSet();
//		RecordSet res = new RecordSet();
		RecordSetDataSource rsd = new RecordSetDataSource("local_HR");
		String sql = "";
		String tableName = "";
		String  txr = "";//填写人
		String sqrygbm = "";// 创建员工编码
		String sqrq = "";// 申请日期
		String qjsy = "";// 请假事由
		String qjlx = "";//请假类型
		String qjygbh = "";//请假员工编号
	
		String sjqjksrq = "";//实际请假开始日期
		String sjqjjsrq = "";//实际请假结束日期
		String sjqjkssj = "";//实际请假开始时间
		String sjqjjssj = "";//实际请假结束时间
		String sjqjxsh = "";//实际请假小时数

		sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id="
				+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql---------" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!"".equals(tableName)) {
			String sql_1 = "select * from " + tableName + " where requestid = "
					+ info.getRequestid();
			new BaseBean().writeLog("sql1---------" + sql_1);
			rs.executeSql(sql_1);
			if (rs.next()) {
				txr = rs.getString("txr");//填写人
				sqrq = rs.getString("txrq");//填写日期
				qjsy = rs.getString("qjsy");//请假事由
				qjlx = rs.getString("jb");//请假类型
				qjygbh = rs.getString("ygh");//请假人员工号
				
				sjqjksrq = rs.getString("sjqjksrq");//实际请假开始日期
				sjqjkssj = rs.getString("sjqjkssj");//实际请假开始时间
				sjqjjsrq = rs.getString("sjjsrq");//实际请假结束日期
				sjqjjssj = rs.getString("sjqjjssj");//实际请假结束时间
				sjqjxsh = rs.getString("jsgjxss");//计划请假小时数
				
				qjsy = qjsy.replace("<br>", " ").replace("&nbsp;", " ");
				
				if(qjsy.length() > 250) qjsy = qjsy.substring(0,250);
			}
			sqrq = sqrq.replace("-", "/");
			sql = "select * from hrmresource where id = '"+txr+"'";
			rs.executeSql(sql);
			if(rs.next()){
				sqrygbm = rs.getString("workcode");
			}
			
		} else {
			new BaseBean().writeLog("流程取表信息错误");
			return "-1";
		}
		
			sjqjksrq = sjqjksrq.replace("-", "/");
			sjqjjsrq = sjqjjsrq.replace("-", "/");
			
			sql = "insert into cus_ats_leave(key_id,process_id,employee_code,leave_begin_date,leave_end_date,leave_begin_time,leave_end_time,leave_value,leave_type_code,create_by,create_dt,remark) values ("+
			          "newID(),'"+info.getRequestid()+"','"+qjygbh+"','"+sjqjksrq+"','"+sjqjjsrq+"','"+sjqjkssj+"','"+sjqjjssj+"','"+sjqjxsh+"','"+qjlx+"','"+sqrygbm+"','"+sqrq+"','"+qjsy+"'"+
					  ")";
			new BaseBean().writeLog("插入sql实际开始大于计划开始----------------"+sql);
			if (!rsd.executeSql(sql)) {
				errorMsg = "插入请假中间表失败";
				new BaseBean().writeLog("sql-----------------插入请假中间表失败------------"+ sql);
				staticobj.putRecordToObj("budget_upd", "ErrorMsg","OA ErrorMsg:[" + errorMsg + "]");
				return "-1";
			}else{
				new BaseBean().writeLog("sql-----------------插入请假中间表成功------------"+ sql);
			}
			return SUCCESS;
	}
}
