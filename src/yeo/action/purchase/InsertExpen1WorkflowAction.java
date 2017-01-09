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
 * 一般性费用报销（固安）
 */
public class InsertExpen1WorkflowAction implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入一般性费用报销InsertExpen1WorkflowAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String tableNamedt2 = "";
        String Main_id = "";
        String M_YDATE = "";
        String M_DATE = "";
        String M_TOU = "02";
        String FSign = "";//1固安；2北京
        String D_PRO = "";
        String D_ABS2 = "";//明细表2
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";

            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
                FSign = Util.null2String(rs.getString("ztsb"));
                D_PRO = Util.null2String(rs.getString("xmmc"));
                D_ABS2 = Util.null2String(rs.getString("bxsm"));

                Map<String, String> mapStr_M = new HashMap<String, String>();
                mapStr_M.put("ID", Main_id);
                mapStr_M.put("FSign", FSign);
                mapStr_M.put("M_YDATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                mapStr_M.put("M_DATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                //mapStr_M.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_M.put("M_TOU", M_TOU);

                String table_M = "OA_ERP.dbo.Expen_1_M";
                IU.insert(mapStr_M, table_M);
            }
            //查询明细表1
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            //log.writeLog("sqldt1---------" + sql);
            rs.execute(sql);
            while (rs.next()) {
                String D_ABS = Util.null2String(rs.getString("sy"));
                String D_JSUBJ = Util.null2String(rs.getString("bxkm"));
                String D_JMONEY = Util.null2String(rs.getString("je"));
                String D_DEP = Util.null2String(rs.getString("D_DEP"));
                String D_USER = Util.null2String(rs.getString("D_USER"));
                //String D_PRO = Util.null2String(rs.getString("xm"));
                String D_ABSD = Util.null2String(rs.getString("bxsm"));

                Map<String, String> mapStr_D = new HashMap<String, String>();
                mapStr_D.put("D_MID", Main_id);
                mapStr_D.put("D_ABS", D_ABS);
                mapStr_D.put("D_JSUBJ", D_JSUBJ);
                mapStr_D.put("D_JMONEY", D_JMONEY);
                mapStr_D.put("D_DEP", D_DEP);
                mapStr_D.put("D_USER", D_USER);
                mapStr_D.put("D_ABSD", D_ABSD);
                //mapStr_D.put("modedatacreatedate", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                //mapStr_D.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_D.put("D_PRO", D_PRO);

                String table_D = "OA_ERP.dbo.Expen_1_D";
                IU.insert(mapStr_D, table_D);
                log.writeLog("明细1插入成功-----------");
            }
            tableNamedt2 =  tableName + "_dt2";

            //查询明细表2
            sql = "select * from " + tableNamedt2 + " where mainid=" + Main_id;
            //log.writeLog("sqldt2---------" + sql);
            rs.execute(sql);
            while (rs.next()) {
                String D_DSUBJ = Util.null2String(rs.getString("dfkm"));
                String D_DMONEY = Util.null2String(rs.getString("dfje"));

                Map<String, String> mapStr_D = new HashMap<String, String>();
                mapStr_D.put("D_MID", Main_id);
                mapStr_D.put("D_ABS", D_ABS2);
                mapStr_D.put("D_DSUBJ", D_DSUBJ);
                mapStr_D.put("D_DMONEY", D_DMONEY);

                String table_D = "OA_ERP.dbo.Expen_1_D";
                IU.insert(mapStr_D, table_D);
                log.writeLog("明细2插入成功----------");
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
