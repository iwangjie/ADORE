package gvo.work;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class CheckQingJiaWorkflowAction implements Action {
    GetWebValue getWebValue = new GetWebValue();

    public String execute(RequestInfo info) {
        new BaseBean().writeLog("进入请假检查申请action_____________________________");

        RecordSet rs = new RecordSet();
        String sql = "";
        String tableName = "";
        String qjlx = "";//请假类型
        String qjygbh = "";//请假员工编号
        String qjxss = "";//请假小时数

        String jhqjksrq = "";//计划请假开始日期
        String jhqjjsrq = "";//计划请假结束日期
        String jhqjkssj = "";//计划请假开始时间
        String jhqjjssj = "";//计划请假结束时间
        String jhqjxss = "";//计划请假小时数

        String sjqjksrq = "";//实际请假开始日期
        String sjqjjsrq = "";//实际请假结束日期
        String sjqjkssj = "";//实际请假开始时间
        String sjqjjssj = "";//实际请假结束时间
        String sjqjxsh = "";//实际请假小时数

        sql = "Select tablename From Workflow_bill Where id=(";
        sql += "Select formid From workflow_base Where id="
                + info.getWorkflowid() + ")";
        new BaseBean().writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            String sql_1 = "select * from " + tableName + " where requestid = "
                    + info.getRequestid();
            new BaseBean().writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                qjlx = rs.getString("jb");//请假类型
                qjygbh = rs.getString("ygh");//请假人员工号
                jhqjksrq = rs.getString("yjqjksrq");//计划请假开始日期
                jhqjkssj = rs.getString("yjqjkssj");//计划请假开始时间
                jhqjjsrq = rs.getString("yjqjjsrq");//计划请假结束日期
                jhqjjssj = rs.getString("yjqjjssj");//计划请假结束时间
                jhqjxss = Util.null2String(rs.getString("yjgj"));//计划请假小时数
                sjqjksrq = Util.null2String(rs.getString("sjqjksrq"));//实际请假开始日期
                sjqjkssj = Util.null2String(rs.getString("sjqjkssj"));//实际请假开始时间
                sjqjjsrq = Util.null2String(rs.getString("sjqkksrq"));//实际请假结束日期
                sjqjjssj = Util.null2String(rs.getString("sjqjjssj"));//实际请假结束时间
                sjqjxsh = Util.null2String(rs.getString("jsgjxss"));//计划请假小时数
            }

        } else {
            new BaseBean().writeLog("流程取表信息错误");
            return "-1";
        }
        qjxss = jhqjxss;

        if ("".equals(qjxss) || Double.valueOf(qjxss) == 0) {
            double temp1 = getWebValue.getValueAction(qjygbh, jhqjksrq, jhqjjsrq, jhqjkssj, jhqjjssj, qjlx);
            rs.execute("update " + tableName + " set yjgj = " + temp1 + " where requestid = " + info.getRequestid());
        }

        if (!"".equals(sjqjjsrq) && (sjqjxsh.equals("") || Double.valueOf(sjqjxsh) == 0)) {
            double temp1 = getWebValue.getValueAction(qjygbh, sjqjksrq, sjqjjsrq, sjqjkssj, sjqjjssj, qjlx);
            rs.execute("update " + tableName + " set sjqjxsh = " + temp1 + " where requestid = " + info.getRequestid());
        }


        return SUCCESS;
    }
}
