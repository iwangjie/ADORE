package gvo.work;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class workWANGDAKAWorkflowAction implements Action {
    public workWANGDAKAWorkflowAction() {
    }

    public String execute(RequestInfo info) {
        (new BaseBean()).writeLog("½øÈëÍü´ò¿¨ÉêÇëaction_____________________________");
        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        RecordSetDataSource rsds = new RecordSetDataSource("local_HR");
        String sql = "";
        String tableName = "";
        String employee_code = "";
        String create_by = "";
        String create_dt = "";
        String att_datetime1 = "";
        String att_datetime2 = "";
        String leixing = "";
        String forgetdate = "";
        String process_id = "";
        sql = "Select tablename From Workflow_bill Where id in (Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        (new BaseBean()).writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {
            String sql_1 = "select * from " + tableName + " where requestid = " + info.getRequestid();
            (new BaseBean()).writeLog("sql1---------" + sql_1);
            res.executeSql(sql_1);
            if (res.next()) {
                process_id = info.getRequestid();
                employee_code = Util.null2String(res.getString("gh"));
                att_datetime1 = Util.null2String(res.getString("sjsbsj"));
                att_datetime2 = Util.null2String(res.getString("sjxbsj"));
                create_by = Util.null2String(res.getString("chjr"));
                create_dt = Util.null2String(res.getString("chjrq"));
                forgetdate = Util.null2String(res.getString("wfkqrq"));
                leixing = Util.null2String(res.getString("wdklc"));
                create_dt = create_dt.replace("-", "/");
                forgetdate = forgetdate.replace("-", "/");
                String tmp_1 = forgetdate + " " + att_datetime1;
                String tmp_2 = forgetdate + " " + att_datetime2;
                String tmp_create = this.getCode(create_by);
                String tmp_createtime = "";
                sql = "select createdate||\' \'||createtime as tmp_createtime from workflow_requestbase where requestid=" + process_id;
                rs.executeSql(sql);
                (new BaseBean()).writeLog("sql---------" + sql);
                if (rs.next()) {
                    tmp_createtime = Util.null2String(rs.getString("tmp_createtime"));
                }

                if ("0".equals(leixing)) {
                    sql = "insert into cus_ats_originality_data(key_id,process_id,employee_code,att_datetime,create_by,create_dt)values (newID(),\'" + process_id + "\',\'" + employee_code + "\',\'" + tmp_1 + "\',\'" + tmp_create + "\',\'" + tmp_createtime + "\')";
                    rsds.execute(sql);
                    (new BaseBean()).writeLog("sql---------" + sql);
                } else if ("1".equals(leixing)) {
                    sql = "insert into cus_ats_originality_data(key_id,process_id,employee_code,att_datetime,create_by,create_dt)values (newID(),\'" + process_id + "\',\'" + employee_code + "\',\'" + tmp_2 + "\',\'" + tmp_create + "\',\'" + tmp_createtime + "\')";
                    rsds.execute(sql);
                    (new BaseBean()).writeLog("sql---------" + sql);
                } else if ("2".equals(leixing)) {
                    sql = "insert into cus_ats_originality_data(key_id,process_id,employee_code,att_datetime,create_by,create_dt)values (newID(),\'" + process_id + "\',\'" + employee_code + "\',\'" + tmp_1 + "\',\'" + tmp_create + "\',\'" + tmp_createtime + "\')";
                    rsds.execute(sql);
                    (new BaseBean()).writeLog("sql---------" + sql);
                    sql = "insert into cus_ats_originality_data(key_id,process_id,employee_code,att_datetime,create_by,create_dt)values (newID(),\'" + process_id + "\',\'" + employee_code + "\',\'" + tmp_2 + "\',\'" + tmp_create + "\',\'" + tmp_createtime + "\')";
                    rsds.execute(sql);
                    (new BaseBean()).writeLog("sql---------" + sql);
                }
            }
        }

        return "1";
    }

    public String getCode(String emp_id) {
        if ("1".equals(emp_id)) {
            return "";
        } else {
            String code = "";
            RecordSet rs = new RecordSet();
            rs.executeSql("select * from hrmresource where id=" + emp_id);
            if (rs.next()) {
                code = Util.null2String(rs.getString("workcode"));
            }

            return code;
        }
    }
}
