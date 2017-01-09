package gvo.peixun;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdatePeixunWorkflowAction implements Action {

	public String execute(RequestInfo info) {
		new BaseBean().writeLog("进入培训申请UpdatePeixunWorkflowAction____");

		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		String sql = "";
		String tableName = "";
		String courseID = "";
		String companyID = "";
		sql = "Select tablename From Workflow_bill Where id in ("
				+ "Select formid From workflow_base Where id="
				+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql----UpdatePeixunWorkflowAction-----" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!" ".equals(tableName)) {

			String sql_1 = "select * from " + tableName + " where requestid = "
					+ info.getRequestid();
			new BaseBean().writeLog("sql1----UpdatePeixunWorkflowAction-----"
					+ sql_1);
			res.executeSql(sql_1);
			if (res.next()) {
				courseID = Util.null2String(res.getString("sqcjkc"));
				companyID = Util.null2String(res.getString("ssgs"));
				if ("23".equals(companyID)) {
					String sql_up = "update formtable_main_193 set jtzbye = nvl(jtzbye,jtzb) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
				if ("25".equals(companyID)) {
					String sql_up = "update formtable_main_193 set amdxye = nvl(amdxye,amdx) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
				if ("21".equals(companyID)) {
					String sql_up = "update formtable_main_193 set pmsybye = nvl(pmsybye,pmsyb) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
				if ("101".equals(companyID)) {
					String sql_up = "update formtable_main_193 set jtyfzxye = nvl(jtyfzxye,jtyfzx) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
				if ("41".equals(companyID)) {
					String sql_up = "update formtable_main_193 set bjwxnye = nvl(bjwxnye,bjwxn) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
				if ("141".equals(companyID)) {
					String sql_up = "update formtable_main_193 set zmsybye = nvl(zmsybye,zmsyb) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
				if ("42".equals(companyID)) {
					String sql_up = "update formtable_main_193 set tpsybye = nvl(tpsybye,tpsyb) - 1 where id = "
							+ courseID;
					new BaseBean()
							.writeLog("sql_up---UpdatePeixunWorkflowAction------"
									+ sql_up);
					res.executeSql(sql_up);
				}
			}
		}
		return SUCCESS;
	}
}
