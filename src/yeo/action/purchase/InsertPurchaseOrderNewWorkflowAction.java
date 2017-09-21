package yeo.action.purchase;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.shaw.util.ReplaceBlankUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import yeo.util.GetReqidUtil;
import yeo.util.InsertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/9/6.
 * 采购订单流程(新)
 * UPDATE ON 16/12/19:明细有几个不同的供应商,主表就要几个不同的M_CODE(主表不重复生成),总的明细条数不变
 * <p>
 * UPDATE ON 17/8/20:推送申请人字段的申请人工号到中间表，推送流程发起日期到中间表。
 * 数据库字段对应     OA（sqrgh）→中间表（M_quester）     OA（sqrqerp）→中间表（OAsqsj）
 */
public class InsertPurchaseOrderNewWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入采购订单(新)InsertPurchaseOrderNewWorkflowAction——————————");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        InsertUtil IU = new InsertUtil();
        GetReqidUtil GRU = new GetReqidUtil();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
        String M_DEP = "";
        String M_CODE = "";
        String M_ABS = "";
        String M_CURR = "";
        String M_RATE = "";
        //String M_DATE = "";
        String M_USER = "";
        String M_SUSER = "";
        //String M_TOU = "YG0046";//最初是工号
        String M_TOU = "02";
        String M_quester = "";
        String OAsqsj = "";
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
                M_DEP = Util.null2String(rs.getString("M_DEP"));
                M_CODE = Util.null2String(rs.getString("bh"));
                M_ABS = Util.null2String(rs.getString("zy"));
                M_ABS = ReplaceBlankUtil.replaceBlank(M_ABS);//去空格 换行符
                M_CURR = Util.null2String(rs.getString("bz_new"));
                M_RATE = Util.null2String(rs.getString("hr"));
                M_USER = Util.null2String(rs.getString("M_USER"));
                M_SUSER = Util.null2String(rs.getString("M_SUSER"));
                M_quester = Util.null2String(rs.getString("sqrgh"));
                OAsqsj = Util.null2String(rs.getString("sqrqerp"));
            }
            //查询明细表
            sql = "select gys from " + tableNamedt + " where mainid=" + Main_id + " group by gys ";
            log.writeLog("sql---------" + sql);
            res.execute(sql);
            while (res.next()) {
                String supplier = Util.null2String(res.getString("gys"));
                log.writeLog("supplier---------" + supplier);
                String s_code = supplier.replace(".", "").substring(3);
                if (!"".equals(supplier)) {
                    String reqid = GRU.getReqid();

                    //log.writeLog("tmp_code---------" + reqid);

                    //String tmp_code = M_CODE + reqid;
                    String tmp_code = M_CODE + s_code;
                    log.writeLog("tmp_code---------" + tmp_code);

                    Map<String, String> mapStr_M = new HashMap<String, String>();
                    mapStr_M.put("ID", reqid);
                    mapStr_M.put("M_DEP", M_DEP);
                    mapStr_M.put("M_CODE", tmp_code);
                    mapStr_M.put("M_ABS", M_ABS);
                    mapStr_M.put("M_CURR", M_CURR);
                    mapStr_M.put("M_RATE", M_RATE);
                    mapStr_M.put("M_USER", M_USER);
                    mapStr_M.put("M_SUSER", M_SUSER);
                    mapStr_M.put("M_DATE", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                    //mapStr_M.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                    mapStr_M.put("M_TOU", M_TOU);
                    mapStr_M.put("M_quester", M_quester);
                    mapStr_M.put("OAsqsj", OAsqsj);

                    String table_M = "OA_ERP.dbo.Pur_M";
                    IU.insert(mapStr_M, table_M);

                    sql = "select * from " + tableNamedt + " where mainid=" + Main_id + " and gys='" + supplier + "' ";
                    log.writeLog("sql---------" + sql);
                    rs.execute(sql);
                    while (rs.next()) {
                        String D_CCODE = Util.null2String(rs.getString("WLDM"));
                        String D_COUNT = Util.null2String(rs.getString("sl"));
                        String D_UNPRICE = Util.null2String(rs.getString("D_UNPRICE"));
                        String D_HUNPRICE = Util.null2String(rs.getString("D_HUNPRICE"));
                        String D_PRICE = Util.null2String(rs.getString("je"));
                        String D_RATE = Util.null2String(rs.getString("sll"));
                        String D_XPRICE = Util.null2String(rs.getString("se"));
                        String D_MONEY = Util.null2String(rs.getString("jshj"));
                        String D_REMARK = Util.null2String(rs.getString("bz"));
                        D_REMARK = ReplaceBlankUtil.replaceBlank(D_REMARK);
                        String D_DATE = Util.null2String(rs.getString("jhrq"));


                        Map<String, String> mapStr_D = new HashMap<String, String>();
                        mapStr_D.put("D_MID", reqid);
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
                        mapStr_D.put("D_SUPPLIER", supplier);

                        String table_D = "OA_ERP.dbo.Pur_D";
                        IU.insert(mapStr_D, table_D);
                    }
                } else {
                    log.writeLog("供应商信息获取失败！");
                    return "-1";
                }
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
