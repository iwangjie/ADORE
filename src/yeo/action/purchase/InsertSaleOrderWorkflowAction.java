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
 * Created by adore on 16/8/29.
 * 销售订单（固安）
 */
public class InsertSaleOrderWorkflowAction implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入销售订单 InsertSaleOrderWorkflowAction——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
        String M_DEP = "";
        String M_CUSTOMER = "";
        String M_ABS = "";
        String M_CURR = "";
        String M_RATE = "";
        //String M_DATE = "";
        String M_USER = "";
        String M_SUSER = "";
        //String M_TOU = "YG0046";
        String M_TOU = "02";
        String M_CONTNO = "";
        String M_RANGE = "";
        String M_PRO = "";
        //String FSign = "";//1固安；2北京
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

            String sql_1 = "select *,case M_RANGE when 0 then '购销' when 1 then '调拨' else '' end as M_RANGE_new from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
                M_DEP = Util.null2String(rs.getString("M_DEP"));
                M_CUSTOMER = Util.null2String(rs.getString("GHDW"));
                M_ABS = Util.null2String(rs.getString("zy"));
                M_CURR = Util.null2String(rs.getString("bz_new"));
                M_RATE = Util.null2String(rs.getString("hl"));
                M_USER = Util.null2String(rs.getString("M_USER"));
                M_SUSER = Util.null2String(rs.getString("M_SUSER"));
                M_PRO = Util.null2String(rs.getString("XMMC"));
                M_CONTNO = Util.null2String(rs.getString("HTH"));
                M_RANGE = Util.null2String(rs.getString("M_RANGE_new"));

                Map<String, String> mapStr_M = new HashMap<String, String>();
                mapStr_M.put("ID", Main_id);
                mapStr_M.put("M_DEP", M_DEP);
                mapStr_M.put("M_CUSTOMER", M_CUSTOMER);
                mapStr_M.put("M_ABS", M_ABS);
                mapStr_M.put("M_CURR", M_CURR);
                mapStr_M.put("M_RATE", M_RATE);
                mapStr_M.put("M_USER", M_USER);
                mapStr_M.put("M_SUSER", M_SUSER);
                //mapStr_M.put("FSign", FSign);
                mapStr_M.put("M_DATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                //mapStr_M.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_M.put("M_TOU", M_TOU);
                mapStr_M.put("M_PRO", M_PRO);
                mapStr_M.put("M_CONTNO", M_CONTNO);
                mapStr_M.put("M_RANGE", M_RANGE);

                String table_M = "OA_ERP.dbo.Sale_M";
                IU.insert(mapStr_M, table_M);
            }
            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String D_CCODE = Util.null2String(rs.getString("CPDM"));
                String D_COUNT = Util.null2String(rs.getString("sl"));
                String D_UNPRICE = Util.null2String(rs.getString("dj"));
                String D_HUNPRICE = Util.null2String(rs.getString("HSJE"));
                String D_PRICE = Util.null2String(rs.getString("je"));
                String D_RATE = Util.null2String(rs.getString("SHL"));
                String D_XPRICE = Util.null2String(rs.getString("XXSE"));
                String D_MONEY = Util.null2String(rs.getString("jshj"));
                String D_REMARK = Util.null2String(rs.getString("bz"));
                String D_DATE = Util.null2String(rs.getString("jhrq1"));
                //String D_SUPPLIER = Util.null2String(rs.getString("gys"));

                Map<String, String> mapStr_D = new HashMap<String, String>();
                mapStr_D.put("D_MID", Main_id);
                mapStr_D.put("D_CCODE", D_CCODE);
                mapStr_D.put("D_COUNT", D_COUNT);
                mapStr_D.put("D_HUNPRICE", D_HUNPRICE);
                mapStr_D.put("D_PRICE", D_PRICE);
                mapStr_D.put("D_RATE", D_RATE);
                mapStr_D.put("D_XPRICE", D_XPRICE);
                mapStr_D.put("D_MONEY", D_MONEY);
                //mapStr_D.put("modedatacreatedate", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                //mapStr_D.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                mapStr_D.put("D_REMARK", D_REMARK);
                mapStr_D.put("D_DATE", D_DATE);
                mapStr_D.put("D_UNPRICE", D_UNPRICE);
                //mapStr_D.put("D_SUPPLIER", D_SUPPLIER);

                String table_D = "OA_ERP.dbo.Sale_D";
                IU.insert(mapStr_D, table_D);
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
