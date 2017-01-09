package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateHolidayPersonalWorkflowAction implements Action {

	public String execute(RequestInfo info) {
		new BaseBean().writeLog("进入请假申请UpdateHolidayPersonalWorkflowAction");

		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();

		String sql = "";
		String tableName = "";
		String requestid = info.getRequestid();
		sql = "Select tablename From Workflow_bill Where id in ("+ "Select formid From workflow_base Where id="+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql---------" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!" ".equals(tableName)) {

			sql = "select * from " + tableName + " where requestid = "+ requestid;
			new BaseBean().writeLog("sql1---------" + sql);
			rs.executeSql(sql);
			if (rs.next()) {

				String applyuser = Util.null2String(rs.getString("applyuser"));
				String leavedays = Util.null2String(rs.getString("leavedays"));
				String applyholiday = Util.null2String(rs.getString("applyholiday"));

				String sql_up = " update uf_apply_holiday set remaindays = isnull(remaindays,0)-cast("+leavedays+"/8.0 as decimal(10,1)) where applyuser ="+applyuser+" and applyholiday ="+applyholiday+" ";
				//	res.executeSql(sql_up);
				new BaseBean().writeLog("sql_up---------" + sql_up);
				if (!res.executeSql(sql_up)) {
					new BaseBean().writeLog("排班信息更新失败");
					return "-1";
				}
			} else {
				new BaseBean().writeLog("人员ID获取错误");
				return "-1";
			}

		} else {
			new BaseBean().writeLog("流程信息表读取错误");
			return "-1";
		}
		return SUCCESS;

	}

}
