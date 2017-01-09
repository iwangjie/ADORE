package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;


/**
 * Created by adore on 16/7/8.
 */
public class cancelOutPlanWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入外出计划取消cancelOutPlanWorkflowAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String relatedapply = "";
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
                relatedapply = Util.null2String(rs.getString("aboutOther"));
                if (!"".equals(relatedapply)) {
                    sql = " update uf_Replace_table set isActive =1,modedatacreatedate = CONVERT(varchar(10) ,GETDATE(), 23 ) ,"
                            + " modedatacreatetime = CONVERT(varchar(5) ,GETDATE(), 114 ) where requestId= " + relatedapply + "  ";
                    res.executeSql(sql);
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
