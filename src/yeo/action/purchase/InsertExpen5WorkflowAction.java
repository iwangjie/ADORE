package yeo.action.purchase;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import yeo.util.InsertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/8/30.
 * 付款申请
 */
public class InsertExpen5WorkflowAction implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入付款申请InsertExpen5WorkflowAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String Main_id = "";
        String M_CURR = "";
        String M_RATE = "";
        String M_JSUBJ = "1123";
        String M_ACCOUNT = "";
        String M_TOU = "02";
        String M_ACCOUNTER = "";
        String M_JMONEY = "";
        String M_YDATE = "";
        String M_DATE = "";
        String M_DSUBJ = "";
        String M_CONTNO = "";
        String FSign = "";//1固安；2北京
        String D_ABS = "1";//1固安；2北京
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
                Main_id = Util.null2String(rs.getString("id"));
                M_ACCOUNT = Util.null2String(rs.getString("gys"));
                M_CURR = Util.null2String(rs.getString("bz"));
                M_CONTNO = Util.null2String(rs.getString("bkdh"));
                M_JMONEY = Util.null2String(rs.getString("dxje"));
                M_DSUBJ = Util.null2String(rs.getString("xjkm"));
                D_ABS = Util.null2String(rs.getString("fknr"));
                FSign = Util.null2String(rs.getString("ztsb"));
                M_RATE = Util.null2String(rs.getString("huilv"));

                Map<String, String> mapStr_M = new HashMap<String, String>();
                mapStr_M.put("ID", Main_id);
                mapStr_M.put("M_JSUBJ", M_JSUBJ);
                mapStr_M.put("M_ACCOUNT", M_ACCOUNT);
                mapStr_M.put("M_ACCOUNTER", "");
                mapStr_M.put("M_CURR", M_CURR);
                mapStr_M.put("M_RATE", M_RATE);
                mapStr_M.put("M_JMONEY", M_JMONEY);
                mapStr_M.put("M_DSUBJ", M_DSUBJ);
                mapStr_M.put("M_CONTNO", M_CONTNO);
                mapStr_M.put("FSign", FSign);
                mapStr_M.put("D_ABS", D_ABS);
                mapStr_M.put("M_YDATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                mapStr_M.put("M_DATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                //mapStr_M.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_M.put("M_TOU", M_TOU);

                String table_M = "OA_ERP.dbo.Expen_5_M";
                IU.insert(mapStr_M, table_M);
            }

        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
