package xinyi.holiday.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import xinyi.holiday.util.InsertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2017/1/19.
 * 新意假期管理，加班时间汇总
 */
public class InsertOverworkAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入加班申请InsertOverworkAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String workflowCode = "";//流程编号
        String applicant = "";//申请人
        String applyDate = "";//申请日期
        String applyDept = "";//申请部门
        String overworkType = "";//加班类型
        String overStartDate = "";//申请加班日期
        String overStartTime = "";//申请加班时间
        String overEndDate = "";//加班结束日期
        String overEndTime = "";//加班结束时间
        String overTime = "";//加班小时数
        String reason = "";//加班事由
        //String status = "";//状态
        //String belongto = "";//归属月份
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                workflowCode = Util.null2String(rs.getString("lcbh"));
                applicant = Util.null2String(rs.getString("sqr"));
                applyDate = Util.null2String(rs.getString("sqrq"));
                applyDept = Util.null2String(rs.getString("sqbm"));
                overworkType = Util.null2String(rs.getString("jblx"));
                overStartDate = Util.null2String(rs.getString("sqjbrq"));
                overStartTime = Util.null2String(rs.getString("sqjbsj"));
                overEndDate = Util.null2String(rs.getString("jbjsrq"));
                overEndTime = Util.null2String(rs.getString("jbjssj"));
                overTime = Util.null2String(rs.getString("jbxss"));
                reason = Util.null2String(rs.getString("jbsy"));

                Map<String, String> mapStr_M = new HashMap<String, String>();
                mapStr_M.put("lcbh", workflowCode);
                mapStr_M.put("sqr", applicant);
                mapStr_M.put("sqrq", applyDate);
                mapStr_M.put("sqbm", applyDept);
                mapStr_M.put("jblx", overworkType);
                mapStr_M.put("sqjbrq", overStartDate);
                mapStr_M.put("sqjbsj", overStartTime);
                mapStr_M.put("jbjsrq", overEndDate);
                mapStr_M.put("jbjssj", overEndTime);
                mapStr_M.put("jbxss", overTime);
                mapStr_M.put("jbsy", reason);
                mapStr_M.put("belongto", "##CONVERT(varchar(7) ,GETDATE(), 23 )");
                //mapStr_M.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_M.put("status", "0");

                String table_M = "formtable_main_141";
                IU.insert(mapStr_M, table_M);
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
