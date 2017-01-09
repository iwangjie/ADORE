package posco.action.workflow;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/14.
 */
public class updateOperatorWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入流程配置updateOperatorWorkflowAction!");

        RecordSet rs = new RecordSet();
        RecordSet rs2 = new RecordSet();
        RecordSet res = new RecordSet();
        RecordSet res2 = new RecordSet();

        String sql = "";
        String tableName = "";
        String fieldname = "";
        String emp_id = "";
        String splevel1 = "";
        String splevel2 = "";
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            sql = "select * from formtable_main_107  where formtablename  = '" + tableName + "' ";
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql);
            if (rs.next()) {
                fieldname = Util.null2String(rs.getString("fieldname"));
                if (!"".equals(fieldname)) {
                    sql = " select * from " + tableName + " where requestid = " + requestid;
                    res.executeSql(sql);
                    //log.writeLog("sql_leave_return---------" + sql);
                    if (res.next()) {
                        emp_id = Util.null2String(res.getString(fieldname));
                        if (!"".equals(emp_id)) {
                            sql = " select * from emp_sx_view where ID= " + emp_id;
                            res2.execute(sql);
                            if (res2.next()) {
                                splevel1 = Util.null2String(res2.getString("s1"));
                                splevel2 = Util.null2String(res2.getString("s2"));
                                if (!"".equals(splevel1) && !"".equals(splevel2)) {
                                    sql = " update " + tableName + " set splevel1 =" + splevel1 + ",splevel2 =" + splevel2 + " where requestid = " + requestid;
                                    rs2.executeSql(sql);
                                    log.writeLog("sql_update---------" + sql);
                                } else {
                                    log.writeLog("申请人上级信息获取失败!");
                                    return "-1";
                                }
                            }
                        } else {
                            log.writeLog("申请人信息获取失败!");
                            return "-1";
                        }
                    }
                } else {
                    log.writeLog("需要更新的信息获取失败!");
                    return "-1";
                }
            }
        } else {
            log.writeLog("流程信息获取失败!");
            return "-1";
        }
        return SUCCESS;
    }
}
