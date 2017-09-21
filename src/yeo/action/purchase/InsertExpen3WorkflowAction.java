package yeo.action.purchase;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.shaw.util.ReplaceBlankUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import yeo.util.InsertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/9/1.
 * 个人现金还款（固安）
 * Updated on 17/8/24 字段去空格
 * 个人还款	还款事由	hksy	Expen_3_D	D_ABS
 */
public class InsertExpen3WorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入个人现金还款 InsertExpen3WorkflowAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String Main_id = "";
        String D_ABS = "";
        String M_YDATE = "";
        String D_JSUBJ = "";
        String M_DATE = "";
        String D_JMONEY = "";
        String D_DSUBJ = "";
        String M_TOU = "02";
        String D_DMONEY = "";
        String D_USER = "";
        String D_DEP = "";
        String D_PRO = "";
        String FSign = "1";//1固安；2北京
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
                D_USER = Util.null2String(rs.getString("gh"));
                D_JMONEY = Util.null2String(rs.getString("hkje"));
                D_DSUBJ = Util.null2String(rs.getString("dfkm"));
                D_JSUBJ = Util.null2String(rs.getString("jfkm"));
                D_DMONEY = Util.null2String(rs.getString("hkje"));
                D_ABS = Util.null2String(rs.getString("hksy"));
                D_ABS = ReplaceBlankUtil.replaceBlank(D_ABS);
                D_DEP = Util.null2String(rs.getString("D_DEP"));
                D_PRO = Util.null2String(rs.getString("xm"));
                FSign = Util.null2String(rs.getString("ztsb"));

                Map<String, String> mapStr_M = new HashMap<String, String>();
                mapStr_M.put("ID", Main_id);
                mapStr_M.put("FSign", FSign);
                mapStr_M.put("M_DATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                mapStr_M.put("M_YDATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                mapStr_M.put("M_TOU", M_TOU);

                String table_M = "OA_ERP.dbo.Expen_3_M";
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

                String table_D = "OA_ERP.dbo.Expen_3_D";
                IU.insert(mapStr_D, table_D);
            }

        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
