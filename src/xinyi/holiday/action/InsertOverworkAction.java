package xinyi.holiday.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 2017/1/19.
 * 新意假期管理，加班时间汇总
 */
public class InsertOverworkAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入加班申请InsertOverworkAction——————————");

        RecordSet rs = new RecordSet();

        String sql = "";
        String tableName = "";
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            String proc_insert = " exec xy_overWorkAdd_proc " + requestid + " ";
            log.writeLog("proc_insert=" + proc_insert);
            rs.executeSql(proc_insert);
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
