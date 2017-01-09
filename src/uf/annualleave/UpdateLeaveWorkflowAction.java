package uf.annualleave;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateLeaveWorkflowAction implements Action {

	public String execute(RequestInfo info) {
		new BaseBean().writeLog("进入年假计算UpdateLeaveWorkflowAction——————");

		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		String tableName = "";
		String sql = "Select tablename From Workflow_bill Where id in ("
				+ "Select formid From workflow_base Where id="
				+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql---------" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!" ".equals(tableName)) {

			String sql_1 = "select * from " + tableName + " where requestid = "
					+ info.getRequestid();
			new BaseBean().writeLog("sql_1---------" + sql_1);
			res.executeSql(sql_1);
			if (res.next()) {
				String sql_up = "";
				String EmpID = res.getString("sqr");
				String LeaveType = res.getString("sqjq");
				int LeaveDays = res.getInt("qjts");
				int CompDays = res.getInt("fdnj");
				int PersDays = res.getInt("grnj");
				int tmp_days = 0;
				if ("0".equals(LeaveType)) {
					if (LeaveDays > CompDays) {
						tmp_days = CompDays + PersDays - LeaveDays;
						sql_up = " update  uf_annualleave  set ComLeave = '0',PerLeave = '"
								+ tmp_days + "' where id = " + EmpID;
					} else {
						tmp_days = CompDays - LeaveDays;
						sql_up = " update  uf_annualleave  set ComLeave = '"
								+ tmp_days + "'  where id = " + EmpID;
					}

					// new BaseBean().writeLog("sql_up---------" + sql_up);

					if (!rs.executeSql(sql_up)) {
						new BaseBean().writeLog("资源状态更新失败");
						return "-1";
					}
				} else {
					new BaseBean().writeLog("资源ID错误");
					return "-1";
				}
			} else {
				new BaseBean().writeLog("流程信息表读取错误");
				return "-1";
			}
		}
		return SUCCESS;
	}
}
