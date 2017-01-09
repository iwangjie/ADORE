package gvo.work;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 2016/12/17.
 */
public class WorkQingJiaUpdateNewWorkflowAction implements Action {
    private StaticObj staticobj = null;
    BaseBean log = new BaseBean();

    public String execute(RequestInfo info) {
        staticobj = StaticObj.getInstance();
        String errorMsg = "";
        log.writeLog("进入请假差异申请action_____________________________");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        RecordSetDataSource rsd = new RecordSetDataSource("local_HR");
        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String txr = "";
        String sqrygbm = "";
        String sqrq = "";
        String qjsy = "";
        String requestid = info.getRequestid();
        String workflowID = info.getWorkflowid();
        String Main_id = "";

        sql = "Select tablename From Workflow_bill Where id=("
                + "Select formid From workflow_base Where id=" + workflowID + ")";
        log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
            txr = Util.null2String(rs.getString("txr"));//填写人
            sqrq = Util.null2String(rs.getString("txrq"));//填写日期
            qjsy = Util.null2String(rs.getString("qjsy"));//请假事由

            qjsy = qjsy.replace("<br>", " ").replace("&nbsp;", " ");

            if (qjsy.length() > 250) qjsy = qjsy.substring(0, 250);

            sqrq = sqrq.replace("-", "/");

            String sql_txr = "select * from hrmresource where id = '" + txr + "'";
            res.executeSql(sql_txr);
            if (res.next()) {
                sqrygbm = Util.null2String(res.getString("workcode"));
            }
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";
            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            log.writeLog("sql1---------" + sql_1);
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

                String sjqjksrq = Util.null2String(rs.getString("sjqjksrq"));//实际请假开始日期
                String sjqjkssj = Util.null2String(rs.getString("sjqjkssj"));//实际请假开始时间
                String sjqjjsrq = Util.null2String(rs.getString("sjqjjsrq"));//实际请假结束日期
                String sjqjjssj = Util.null2String(rs.getString("sjqjjssj"));//实际请假结束时间
                String sjqjxsh = Util.null2String(rs.getString("sjqjxss"));//实际请假小时数

                sjqjksrq = sjqjksrq.replace("-", "/");
                sjqjjsrq = sjqjjsrq.replace("-", "/");

                String process_id = requestid + "_" + dtid;
                String sql_insert = "insert into cus_ats_leave(key_id,process_id,employee_code,leave_begin_date,leave_end_date,leave_begin_time,leave_end_time,leave_value,leave_type_code,create_by,create_dt,remark) values (" +
                        "newID(),'" + process_id + "','" + qjygbh + "','" + sjqjksrq + "','" + sjqjjsrq + "','" + sjqjkssj + "','" + sjqjjssj + "','" + sjqjxsh + "','" + qjlx + "','" + sqrygbm + "','" + sqrq + "','" + qjsy + "'" +
                        ")";
                log.writeLog("插入sql实际开始大于计划开始----------------" + sql_insert);
                if (!rsd.executeSql(sql_insert)) {
                    errorMsg = "插入请假中间表失败";
                    log.writeLog("sql-----------------插入请假中间表失败------------" + sql_insert);
                    staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:[" + errorMsg + "]");
                    return "-1";
                } else {
                    log.writeLog("sql-----------------插入请假中间表成功------------" + sql_insert);
                }
            }
        } else {
            log.writeLog("流程取表信息错误");
            return "-1";
        }


        return SUCCESS;
    }
}
