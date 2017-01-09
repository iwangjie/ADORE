package seahonor.action.atten;

import seahonor.util.DateApartUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/8.
 */
public class cancelLeaveWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入销假申请cancelLeaveWorkflowAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        DateApartUtil mu = new DateApartUtil();

        String sql = "";
        String tableName = "";
        String applicant = "";
        String startdate = "";
        String enddate = "";
        String relateReq="";
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
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
                applicant = Util.null2String(rs.getString("applicant"));
                startdate = Util.null2String(rs.getString("startdate2"));
                enddate = Util.null2String(rs.getString("enddate2"));
                relateReq = Util.null2String(rs.getString("aboutOther"));
                if (!"".equals(applicant) && !"".equals(startdate) && !"".equals(enddate)) {
                    sql=" exec dbo.sh_leave_deduction_reject @emp_id="+applicant+",@requestid="+relateReq+" ";
                    res.executeSql(sql);
                    log.writeLog("sql_leave_return---------" + sql);
                    sql = " update uf_all_attend_info set isEffective =1 where emp_id= " + applicant + " and atten_day between '" + startdate + "' and '" + enddate + "' ";
                    res.executeSql(sql);
                    log.writeLog("sql_update---------" + sql);
                    // 时间拆分
                    for (String nowDay = startdate; nowDay.compareTo(enddate) <= 0; nowDay = mu.nextDay(nowDay)) {
                        sql = " exec dbo.one_sh_atten_info_clear_up_new '" + nowDay + "'," + applicant + " ";
                        res.execute(sql);
                        log.writeLog("sql_procedure---------" + sql);
                    }
                } else {
                    log.writeLog("需要取消的信息获取失败!");
                    return "-1";
                }
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
