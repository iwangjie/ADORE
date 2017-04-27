package gt.table;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateSchedulingAction implements Action {

    public String execute(RequestInfo info) {
        new BaseBean().writeLog("进入排班UpdateSchedulingAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String[] ArrangedStaff = new String[100];
        String tmp_ArrangedStaff = "";
        String WorkStartTime = "";
        String WorkEndTime = "";

        sql = " select * from formtable_main_124 ";
        new BaseBean().writeLog("sql1---------" + sql);
        rs.executeSql(sql);
        while (rs.next()) {
            WorkStartTime = Util.null2String(rs.getString("startTime"));
            WorkEndTime = Util.null2String(rs.getString("endTime"));
            ArrangedStaff = Util.null2String(rs.getString("EmployeeName")).split(",");
            for (int i = 0; i < ArrangedStaff.length; i++) {
                tmp_ArrangedStaff = ArrangedStaff[i];
                // new BaseBean().writeLog("tmp_ArrangedStaff = " +
                // tmp_ArrangedStaff);
                if (!"".equals(tmp_ArrangedStaff)) {
                    sql = " select count(*) as emp_num from emp_work_normal_time where emp_id = " + tmp_ArrangedStaff;
                    res.executeSql(sql);
                    if (res.next()) {
                        String emp_num = Util.null2String(res.getString("emp_num"));
                        if ("0".equals(emp_num)) {
                            sql = " insert into emp_work_normal_time values (" + tmp_ArrangedStaff + ",'" + WorkStartTime + "','" + WorkEndTime + "') ";
                        } else {
                            sql = " update emp_work_normal_time set normal_start_time = '" + WorkStartTime + "',normal_end_time = '" + WorkEndTime + "'where emp_id = " + tmp_ArrangedStaff;
                        }
                    }
                    new BaseBean().writeLog("sql_up---------" + sql);
                    if (!rs.executeSql(sql)) {
                        new BaseBean().writeLog("排班信息更新失败");
                        return "-1";
                    }
                } else {
                    new BaseBean().writeLog("人员ID获取错误");
                    return "-1";
                }
            }
        }

        return SUCCESS;
    }

}
