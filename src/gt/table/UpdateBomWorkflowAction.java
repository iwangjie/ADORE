package gt.table;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/17.
 * 更新--交期长的物料是否已经出BOM？
 */
public class UpdateBomWorkflowAction implements Action{
    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {

        //log.writeLog("GTOFT-501-BOM发布流程UpdateBomWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();

        String sql = "";
        String tableName = "";
        String wkFlag = "";

        sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            // 查询主表
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                wkFlag = Util.null2String(rs.getString("wkFlag"));//流程请求id
            }

            // 更新原来的表
            if(!"".equals(wkFlag)) {
                sql = " update formtable_main_61 set rs9 = 0 where requestid=" + wkFlag;
                rs.execute(sql);
                //log.writeLog("sql_update=" + sql);
                if (!rs.executeSql(sql)) {
                    log.writeLog("rs9更新失败");
                    return "-1";
                }
            }else{
                log.writeLog("获取wkFlag失败！");
            }
        } else {
            log.writeLog("流程信息表获取失败！");
            return "-1";
        }
        return SUCCESS;
    }
}
