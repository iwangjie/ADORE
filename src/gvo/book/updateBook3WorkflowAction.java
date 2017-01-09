package gvo.book;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class updateBook3WorkflowAction implements Action {


	public String execute(RequestInfo info) {
		new BaseBean().writeLog("归档action_____________________________");

		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		String sql = "";
		String tableName = "";
		String bookID = "";
		String personID = "";
		String borrowdate = "";
		sql = "Select tablename From Workflow_bill Where id in ("+ "Select formid From workflow_base Where id="+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql---------" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!" ".equals(tableName)) {

			String sql_1 = "select * from " + tableName + " where requestid = "+ info.getRequestid();
			new BaseBean().writeLog("sql1---------" + sql_1);
			res.executeSql(sql_1);
			if (res.next()) {
				bookID = Util.null2String(res.getString("tsmc"));
				personID = Util.null2String(res.getString("sqr"));
				borrowdate = Util.null2String(res.getString("jyksrq"));
				if (!"".equals(bookID)) {
					String sql_up = "update formtable_main_91 set tszt = '2',mqjyr='"+ personID + "',tsjyrq = '"+borrowdate+"' where id = '" + bookID + "'";
					new BaseBean().writeLog("sql_up---------" + sql_up);
					
					if (!rs.executeSql(sql_up)) {
						new BaseBean().writeLog("图书状态更新失败");
						return "-1";
					}
				} else {
					new BaseBean().writeLog("图书ID错误");
					return "-1";
				}
			} else {
				new BaseBean().writeLog("流程取表信息错误");
				return "-1";
			}
		}
		return SUCCESS;
	}
}