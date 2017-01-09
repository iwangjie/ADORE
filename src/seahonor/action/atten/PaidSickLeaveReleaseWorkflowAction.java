package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import yeo.util.InsertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/9/8.
 * 带薪病假发放
 */
public class PaidSickLeaveReleaseWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入带薪病假发放PaidSickLeaveReleaseWorkflowAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
        String wfcreater = "";
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

            sql = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
                wfcreater = Util.null2String(rs.getString("applyuser"));
            }
            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String empid = Util.null2String(rs.getString("empid"));
                String holiname = Util.null2String(rs.getString("holiname"));
                String startDate = Util.null2String(rs.getString("startDate"));
                String endDate = Util.null2String(rs.getString("endDate"));
                String days = Util.null2String(rs.getString("days"));

                Map<String, String> mapStr_D = new HashMap<String, String>();
                mapStr_D.put("applyuser", empid);
                mapStr_D.put("applyholidays", holiname);
                mapStr_D.put("startDate", startDate);
                mapStr_D.put("endDate", endDate);
                mapStr_D.put("applydays", days);
                mapStr_D.put("formmodeid", "116");
                mapStr_D.put("modedatacreater", "1");
                mapStr_D.put("modedatacreatedate", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                mapStr_D.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_D.put("modedatacreatertype", "0");
                mapStr_D.put("creater", wfcreater);
                mapStr_D.put("dayUsed", "0.00");
                mapStr_D.put("IsEffective", "0");
                mapStr_D.put("orderby", "B");
                mapStr_D.put("isSalary", "0");

                String table_D = "uf_holiday_apply";
                IU.insert(mapStr_D, table_D);
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
