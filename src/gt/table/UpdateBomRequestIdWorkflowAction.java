package gt.table;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/16.
 * GTOFT-501-BOM发布流程
 */
public class UpdateBomRequestIdWorkflowAction implements Action{
    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {

        //log.writeLog("GTOFT-501-BOM发布流程UpdateBomRequestIdWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();

        String sql = "";
        String tableName = "";
        String wkFlag = "";
        String BomFlag = "";

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
                BomFlag = Util.null2String(rs.getString("BomFlag"));//更新字段
            }

            // 更新原来的表
            if (!"".equals(wkFlag) && !"".equals(BomFlag)) {
                sql = " update formtable_main_61 set " + BomFlag + " = " + requestid + " where requestid=" + wkFlag;
                rs.execute(sql);
                //log.writeLog("sql_update=" + sql);

                if (!rs.executeSql(sql)) {
                    log.writeLog("BomFlag更新失败");
                    return "-1";
                }
            }else{
                log.writeLog("wkFlag&BomFlag获取失败！");
            }
        } else {
            log.writeLog("流程信息表获取失败！");
            return "-1";
        }
        return SUCCESS;
    }
}
