package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 2016/10/9.
 * 加班70%发放退回
 */
public class RejectOverReleaseWorkflowAction implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入加班70%发放退回OverReleaseWorkflowAction——————————");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";

            String sql_main = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_main);
            rs.executeSql(sql_main);
            if (rs.next()) {

                Main_id = Util.null2String(rs.getString("id"));
            }
            //查询明细表信息
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String per = Util.null2String(rs.getString("per"));
                String dataid = Util.null2String(rs.getString("dataid"));
                if(per.compareTo("0")>0&&!"".equals(dataid)){
                    String sql_update = " update uf_holiday_apply set applydays=isnull(applydays,0)-"+per+" where id= "+dataid;
                    log.writeLog("sql_update---------" + sql_update);
                    res.executeSql(sql_update);
                }
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
