package gvo.card;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import yeo.util.InsertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2016/9/21.
 * 异常打卡处理
 */
public class UpdateAbnormalCard implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("异常打卡处理UpdateAbnormalCard——————————");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
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

            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                Main_id = Util.null2String(rs.getString("id"));
            }
            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String D_CCODE = Util.null2String(rs.getString("WLDM"));
                String D_COUNT = Util.null2String(rs.getString("sl"));
                String D_UNPRICE = Util.null2String(rs.getString("dj"));
                String D_HUNPRICE = Util.null2String(rs.getString("hsdj"));
                String D_PRICE = Util.null2String(rs.getString("je"));
                String D_RATE = Util.null2String(rs.getString("sll"));
                String D_XPRICE = Util.null2String(rs.getString("se"));
                String D_MONEY = Util.null2String(rs.getString("jshj"));
                String D_REMARK = Util.null2String(rs.getString("bz"));
                String D_DATE = Util.null2String(rs.getString("jhrq"));
                String D_SUPPLIER = Util.null2String(rs.getString("gys"));

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
                mapStr_D.put("D_SUPPLIER", D_SUPPLIER);

                String table_D = "OA_ERP.dbo.Pur_D";
                IU.insert(mapStr_D, table_D);
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
