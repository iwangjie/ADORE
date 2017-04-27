package gvo.peixun;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class RejectPeixunWorkflowAction implements Action {

    public String execute(RequestInfo info) {

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        BaseBean log = new BaseBean();
        log.writeLog("进入培训申请退回RejectPeixunWorkflowAction");

        String sql = "";
        String tableName = "";
        String courseID = "";
        String companyID = "";
        sql = "Select tablename From Workflow_bill Where id in ("
                + "Select formid From workflow_base Where id="
                + info.getWorkflowid() + ")";
        log.writeLog("sql----RejectPeixunWorkflowAction-----" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {

            String sql_1 = "select * from " + tableName + " where requestid = "
                    + info.getRequestid();
            log.writeLog("sql1----RejectPeixunWorkflowAction-----"
                    + sql_1);
            res.executeSql(sql_1);
            if (res.next()) {
                courseID = Util.null2String(res.getString("sqcjkc"));
                companyID = Util.null2String(res.getString("ssgs"));
                if ("23".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set jtzbye = nvl(jtzbye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
                if ("25".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set amdxye = nvl(amdxye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
                if ("21".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set pmsybye = nvl(pmsybye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
                if ("101".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set jtyfzxye = nvl(jtyfzxye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
                if ("41".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set bjwxnye = nvl(bjwxnye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
                if ("141".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set zmsybye = nvl(zmsybye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
                if ("42".equals(companyID)) {
                    String sql_up = "update formtable_main_193 set tpsybye = nvl(tpsybye,0) + 1 where id = "
                            + courseID;
                    log.writeLog("sql_up---RejectPeixunWorkflowAction------"
                            + sql_up);
                    res.executeSql(sql_up);
                }
            }
        }
        return SUCCESS;
    }
}
