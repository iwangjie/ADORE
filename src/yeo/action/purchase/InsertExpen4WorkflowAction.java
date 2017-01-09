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
 * Created by adore on 16/9/1.
 * 非现金报销
 */
public class InsertExpen4WorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入非现金报销 InsertExpen4WorkflowAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String Main_id = "";
        String M_JUDE = "";
        String D_ABS = "";
        String D_JMONEY = "";
        String M_TOU = "02";
        String D_DSUBJ = "";
        String M_YDATE = "";
        String M_DATE = "";
        String D_DEP = "";
        String D_DMONEY = "";
        String D_USER = "";
        String D_PRO = "";
        String D_JSUBJ = "";
        String D_XPRICE = "";
        String FSign = "";//1固安；2北京
        String M_SUPPLIER = "";
        String M_CURR = "";
        String M_DEP = "";
        String M_USER = "";
        String M_RATE = "";
        String M_CODE = "";

        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            String sql_1 = "select *,case sfscpz when 0 then 'TRUE' when 1 then 'FALSE' else '' end as M_JUDE from " + tableName + " where requestid = " + requestid;
            log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
                M_JUDE = Util.null2String(rs.getString("M_JUDE"));
                D_ABS = Util.null2String(rs.getString("sy"));
                D_JMONEY = Util.null2String(rs.getString("dxje"));
                D_DSUBJ = Util.null2String(rs.getString("dfkm"));
                D_DEP = Util.null2String(rs.getString("D_DEP"));
                D_DMONEY = Util.null2String(rs.getString("dxje"));
                D_USER = Util.null2String(rs.getString("gh"));
                D_PRO = Util.null2String(rs.getString("ERPxm"));
                D_JSUBJ = Util.null2String(rs.getString("jfkm"));
                M_SUPPLIER = Util.null2String(rs.getString("gys"));
                M_CURR = Util.null2String(rs.getString("M_CURR"));
                M_DEP = Util.null2String(rs.getString("M_DEP"));
                M_USER = Util.null2String(rs.getString("M_USER"));
                FSign = Util.null2String(rs.getString("ztsb"));
                M_RATE = Util.null2String(rs.getString("huilv"));
                M_CODE = Util.null2String(rs.getString("bxdh"));
                D_XPRICE = Util.null2String(rs.getString("shuie"));

                Map<String, String> mapStr_M = new HashMap<String, String>();
                mapStr_M.put("ID", Main_id);
                mapStr_M.put("M_JUDE", M_JUDE);
                mapStr_M.put("FSign", FSign);
                mapStr_M.put("M_SUPPLIER", M_SUPPLIER);
                mapStr_M.put("M_CURR", M_CURR);
                mapStr_M.put("M_DEP", M_DEP);
                mapStr_M.put("M_USER", M_USER);
                mapStr_M.put("M_RATE", M_RATE);
                mapStr_M.put("M_CODE", M_CODE);
                mapStr_M.put("M_YDATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                mapStr_M.put("M_DATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                //mapStr_M.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_M.put("M_TOU", M_TOU);

                String table_M = "OA_ERP.dbo.Expen_4_M";
                IU.insert(mapStr_M, table_M);

                Map<String, String> mapStr_D = new HashMap<String, String>();
                mapStr_D.put("D_MID", Main_id);
                mapStr_D.put("D_JSUBJ", D_JSUBJ);
                mapStr_D.put("D_USER", D_USER);
                mapStr_D.put("D_JMONEY", D_JMONEY);
                mapStr_D.put("D_DSUBJ", D_DSUBJ);
                mapStr_D.put("D_DMONEY", D_DMONEY);
                mapStr_D.put("D_ABS", D_ABS);
                mapStr_D.put("D_DEP", D_DEP);
                mapStr_D.put("D_PRO", D_PRO);
                mapStr_D.put("D_XPRICE", D_XPRICE);

                String table_D = "OA_ERP.dbo.Expen_4_D";
                IU.insert(mapStr_D, table_D);
            }

        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
