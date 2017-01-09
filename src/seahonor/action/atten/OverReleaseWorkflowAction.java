package seahonor.action.atten;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/9/8.
 * 加班70%发放
 */
public class OverReleaseWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入加班70%发放OverReleaseWorkflowAction——————————");

        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();
        InsertUtil IU = new InsertUtil();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";

            String sql_main = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_main);
            rs.executeSql(sql_main);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
            }

            //查询明细表信息
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String per = Util.null2String(rs.getString("per"));
                String empid = Util.null2String(rs.getString("empid"));
                String overDate = Util.null2String(rs.getString("overDate"));
                String overall = Util.null2String(rs.getString("overall"));

                Map<String, String> holidayStr = new HashMap<String, String>();
                holidayStr.put("applyuser", empid);
                holidayStr.put("applyholidays", "19");
                holidayStr.put("enddate", overDate);
                holidayStr.put("applydays", per);
                holidayStr.put("source", "3");
                holidayStr.put("reqID", requestid);
                holidayStr.put("overWorkAll", overall);

                String insertTable = "uf_holidayAddRecord";
                IU.insert(holidayStr, insertTable);

                sql = " select count(id) as num_count from uf_holiday_apply where  applyuser=" + empid + " and applyholidays=19 and enddate='" + overDate + "' and IsEffective = 0 ";
                res.executeSql(sql);
                if (res.next()) {
                    int num_count = res.getInt("num_count");
                    if (num_count < 1) {
                        Map<String, String> mapStr = new HashMap<String, String>();
                        mapStr.put("applyuser", empid);
                        mapStr.put("applyholidays", "19");
                        mapStr.put("enddate", overDate);
                        mapStr.put("applydays", per);
                        mapStr.put("source", "3");
                        mapStr.put("IsEffective", "0");
                        mapStr.put("orderby", "A");
                        mapStr.put("overWorkAll", overall);
                        mapStr.put("isSalary", "0");

                        String tableNameInsert = "uf_holiday_apply";
                        IU.insert(mapStr, tableNameInsert);
                    } else {
                        sql = " update uf_holiday_apply set  applydays+=" + per + ",overWorkAll=isnull(overWorkAll,0)+" + overall + " where  applyuser=" + empid + " and applyholidays=19 and enddate='" + overDate + "' and IsEffective = 0  ";
                        rs1.executeSql(sql);
                        log.writeLog("sql_update=" + sql);
                    }
                }
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
