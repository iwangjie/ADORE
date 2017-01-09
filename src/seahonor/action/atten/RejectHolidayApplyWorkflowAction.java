package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;


/**
 * Created by adore on 16/8/8.
 */
public class RejectHolidayApplyWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入请假退回RejectHolidayApplyWorkflowAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String requestid = info.getRequestid();
        String applyuser = "";
        String startDate = "";
        String endDate = "";


        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                applyuser = Util.null2String(rs.getString("applyuser"));
                startDate = Util.null2String(rs.getString("startDate"));
                endDate = Util.null2String(rs.getString("endDate"));

                sql = " update uf_all_attend_info set isEffective =1 where emp_id= " + applyuser + " and atten_day between '" + startDate + "' and '" + endDate + "' ";
                res.executeSql(sql);

                res.executeSql("exec dbo.sh_atten_info_period '" + startDate + "','" + endDate + "'," + applyuser + " ");

            } else {
                log.writeLog("人员ID获取错误");
                return "-1";
            }

        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
