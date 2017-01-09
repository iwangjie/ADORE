package sh.attendance;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateOutWorkflowAction implements Action {

	public String execute(RequestInfo info) {
		new BaseBean().writeLog("进入外出排班UpdateOutWorkflowAction");

		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		String sql = "";
		String tableName = "";
		String[] ArrangedStaff = new String[100];
		String tmp_ArrangedStaff = "";
		String AttendanceStartDate = "";
		String AttendanceEndDate = "";
		String WorkStartTime = "";
		String WorkEndTime = "";
		String AttendanceStandard = "";
		String departmentid="";
		String workcode="";
		String subcompanyid1="";
		sql = "Select tablename From Workflow_bill Where id in ("
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
			new BaseBean().writeLog("sql1---------" + sql_1);
			rs.executeSql(sql_1);
			if (rs.next()) {
				
				AttendanceStartDate = Util.null2String(rs.getString("AttendanceStartDate"));
				AttendanceEndDate = Util.null2String(rs.getString("AttendanceEndDate"));
				WorkStartTime = Util.null2String(rs.getString("WorkStartTime"));
				WorkEndTime = Util.null2String(rs.getString("WorkEndTime"));
				AttendanceStandard = Util.null2String(rs.getString("AttendanceStandard"));
				ArrangedStaff = rs.getString("ArrangedStaff").split(",");
				for (int i = 0; i < ArrangedStaff.length; i++) {
					tmp_ArrangedStaff = ArrangedStaff[i];
					//new BaseBean().writeLog("tmp_ArrangedStaff = "+ tmp_ArrangedStaff);
					// }
					if (!"".equals(tmp_ArrangedStaff)) {
						String sql_hr = " select workcode,departmentid,subcompanyid1 from HrmResource where id= "+tmp_ArrangedStaff;
			            res.executeSql(sql_hr);
			            new BaseBean().writeLog("sql_hr="+sql_hr);

			            if(res.next()){
			                workcode = Util.null2String(res.getString("workcode"));
			                departmentid = Util.null2String(res.getString("departmentid"));
			                subcompanyid1 = Util.null2String(res.getString("subcompanyid1"));
			            }
			            //时间拆分
						String sql_up = " insert into uf_Replace_table(EmployeeName,ReplaceStartDate,ReplaceEndDate,ReplaceStartTime,ReplaceEndTime,AttendanceStandard,modedatacreatedate,modedatacreatetime,formmodeid,workcode,department,subcompany) "
								+ " values ("+ tmp_ArrangedStaff+ ",'"+ AttendanceStartDate+ "','"+ AttendanceEndDate+ "','"+ WorkStartTime+ "','"+ WorkEndTime+ "','"+ AttendanceStandard
								+ "',CONVERT(varchar(10) ,GETDATE(), 23 ),CONVERT(varchar(5) ,GETDATE(), 114 ),3,'"+workcode+"','"+departmentid+"','"+subcompanyid1+"')";
						new BaseBean().writeLog("sql_up---------" + sql_up);
						if (!rs.executeSql(sql_up)) {
							new BaseBean().writeLog("排班信息更新失败");
							return "-1";
						}
			            //}
						sql = " select id from uf_Replace_table where EmployeeName ="+ tmp_ArrangedStaff;
						rs.executeSql(sql);
						while (rs.next()) {
							String sourceid = Util.null2String(rs.getString("id"));
							sql = "exec sh_replace_right @sourceid ="+ sourceid;
							res.executeSql(sql );
							//new BaseBean().writeLog("sh_replace_right="+ sql);
						}
					} else {
						new BaseBean().writeLog("人员ID获取错误");
						return "-1";
					}
				}
			}
		} else {
			new BaseBean().writeLog("流程信息表读取错误");
			return "-1";
		}
		return SUCCESS;
	}

}
