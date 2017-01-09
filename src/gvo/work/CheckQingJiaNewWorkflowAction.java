package gvo.work;

import weaver.interfaces.workflow.action.Action;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import gvo.work.GetWebValue;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 2016/12/17.
 * 请假/公出
 */
public class CheckQingJiaNewWorkflowAction implements Action {
    GetWebValue getWebValue = new GetWebValue();
    BaseBean log = new BaseBean();

    public String execute(RequestInfo info) {
        log.writeLog("进入请假检查申请action_____________________________");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String requestid = info.getRequestid();
        String workflowID = info.getWorkflowid();
        String Main_id = "";

        sql = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";
        log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";
            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            log.writeLog("sql_1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
            }

            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String dtid = Util.null2String(rs.getString("id"));
                String qjlx = Util.null2String(rs.getString("qjlb"));//请假类型
                String qjygbh = Util.null2String(rs.getString("gh"));//请假人员工号
                String jhqjksrq = Util.null2String(rs.getString("yjqjksrq"));//计划请假开始日期
                String jhqjkssj = Util.null2String(rs.getString("yjqjkssj"));//计划请假开始时间
                String jhqjjsrq = Util.null2String(rs.getString("yjqjjsrq"));//计划请假结束日期
                String jhqjjssj = Util.null2String(rs.getString("yjqjjssj"));//计划请假结束时间
                String jhqjxss = Util.null2String(rs.getString("yjqjxss"));//计划请假小时数
                String sjqjksrq = Util.null2String(rs.getString("sjqjksrq"));//实际请假开始日期
                String sjqjkssj = Util.null2String(rs.getString("sjqjkssj"));//实际请假开始时间
                String sjqjjsrq = Util.null2String(rs.getString("sjqjjsrq"));//实际请假结束日期
                String sjqjjssj = Util.null2String(rs.getString("sjqjjssj"));//实际请假结束时间
                String sjqjxsh = Util.null2String(rs.getString("sjqjxss"));//实际请假小时数

                String qjxss = jhqjxss;

                if ("".equals(qjxss) || Double.valueOf(qjxss) == 0) {
                    double temp1 = getWebValue.getValueAction(qjygbh, jhqjksrq, jhqjjsrq, jhqjkssj, jhqjjssj, qjlx);
                    String sql_dt1 = "update " + tableNamedt + " set yjqjxss = " + temp1 + " where id = " + dtid;
                    res.execute(sql_dt1);
                    log.writeLog("sql_dt1---------" + sql_dt1);
                }

                if (!"".equals(sjqjjsrq) && (sjqjxsh.equals("") || Double.valueOf(sjqjxsh) == 0)) {
                    double temp1 = getWebValue.getValueAction(qjygbh, sjqjksrq, sjqjjsrq, sjqjkssj, sjqjjssj, qjlx);
                    String sql_dt2 = "update " + tableNamedt + " set sjqjxss = " + temp1 + " where  id = " + dtid;
                    res.execute(sql_dt2);
                    log.writeLog("sql_dt2---------" + sql_dt2);
                }

            }

        } else {
            log.writeLog("流程取表信息错误");
            return "-1";
        }

        return SUCCESS;
    }
}
