package TaiSon.budget.action;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/27.
 */
public class CloseBudgetWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        BaseBean log = new BaseBean();

        log.writeLog("进入费用结案申请CloseBudgetWorkflowAction——————");

        String sql = "";
        String tableName = "";
        String requestid = info.getRequestid();
        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        //new BaseBean().writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid = " + requestid;
            //new BaseBean().writeLog("sql1---------" + sql);
            rs.executeSql(sql);
            if (rs.next()) {
                String reqID = Util.null2String(rs.getString("feeapply"));

                if (!"".equals(reqID)) {
                    sql = " select * from formtable_main_13 where requestid= " + reqID;
                    rs.execute(sql);
                    if (rs.next()) {
                        String flowmark = Util.null2String(rs.getString("sqbh"));
                        String tmp_flowmark = flowmark + "-1";
                        if (!"".equals(flowmark)) {
                            sql = " update " + tableName + " set jadh='" + tmp_flowmark + "' where requestid = " + requestid;
                            res.execute(sql);
                            log.writeLog("sql_update=" + sql);
                        } else {
                            log.writeLog("费用单号获取失败!");
                            return "-1";
                        }
                    }
                } else {
                    log.writeLog("费用流程获取失败!");
                    return "-1";
                }
            } else {
                log.writeLog("工作流信息获取错误!");
                return "-1";
            }

        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;

    }
}
