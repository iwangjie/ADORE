package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/8/11.
 * 假期有效期变更&假期合并
 */
public class UpdateHolidayWorkflowAction implements Action {
    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {
        log.writeLog("进入假期修改UpdateHolidayWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String emp_id = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";

        sql = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";

            // 查询主表
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
            }

            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            rs.execute(sql);
            while (rs.next()) {
                String holidayApp = Util.null2String(rs.getString("holidayApp"));//获取明细表中数据
                String leftTime = Util.null2String(rs.getString("leftTime"));
                String changeType = Util.null2String(rs.getString("changeType"));
                String newDate = Util.null2String(rs.getString("newDate"));

                sql = " exec dbo.sh_holiday_update " + holidayApp + "," + leftTime + "," + changeType + ",'" + newDate + "' ";
                res.execute(sql);
                log.writeLog("sql=" + sql);
                //sql = " update "+tableNamedt+" set holidayAppNew="+holidayApp+" where  ";
            }
        } else {
            log.writeLog("流程表信息获取失败!");
            return "-1";
        }
        return SUCCESS;
    }
}
