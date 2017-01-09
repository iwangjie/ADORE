package feilida.finance;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FinanceExpenseFI08 implements Action {

    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {
        log.writeLog("FinanceExpenseFI08——————");

        String workflowID = info.getWorkflowid();
        String requestid = info.getRequestid();//获取工作流程ID与requestID值

        RecordSet rs = new RecordSet();
        RecordSet rs_dt = new RecordSet();

        String tableName = "";
        String tableNamedt = "";
        String tableNamedt3 = "";
        String mainID = "";
        String expType = "";//报销类型
        String applyDate = "";//过账日期
        String supplName = "";//供应商名称

        //主表
        String PZZY = "";//凭证摘要
        //明细1
        String FYXMMC = "";//费用项目名称
        //明细3
        String XZ = "";//冲销选择

        String sql = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";
            tableNamedt3 = tableName + "_dt3";

            // 查询主表
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                mainID = Util.null2String(rs.getString("ID"));
                expType = Util.null2String(rs.getString("BXLX"));
                applyDate = Util.null2String(rs.getString("GZRQ"));
                supplName = Util.null2String(rs.getString("GYSMC"));
                applyDate = applyDate.substring(5, 7);
            }

            //冲销是否有勾选
            int isExist = 0;
            sql = " select count(1) as isExist from " + tableNamedt3 + " where mainid = " + mainID + " and XZ = 0 ";
            rs.execute(sql);
            log.writeLog("是否冲销:" + sql);
            if (rs.next()) {
                isExist = rs.getInt("isExist");
            }

            if (isExist > 0) {
                //查询明细表3
                String applyNum = "";//OA预付款申请编号
                sql = "select * from " + tableNamedt3 + " where mainid=" + mainID;
                rs_dt.execute(sql);
                log.writeLog("OA预付款申请编号:" + sql);
                String temp = "";//资产描述
                String flag = "";
                String temp1 = "";//费用项目名称
                String flag1 = "";
                while (rs_dt.next()) {
                    applyNum = Util.null2String(rs_dt.getString("YFKBH"));
                }

                //查询明细表1
                sql = "select * from " + tableNamedt + " where mainid=" + mainID;
                rs_dt.execute(sql);
                log.writeLog("查询费用科目:" + sql);
                while (rs_dt.next()) {
                    String zcms = Util.null2String(rs_dt.getString("ZCMS"));
                    String pro_id = Util.null2String(rs_dt.getString("FYXMMC"));
                    temp += flag + zcms;
                    flag = "/";
                    String sql_name = " select fyxmmc from uf_fyxmdzb where id=" + pro_id;
                    rs.executeSql(sql_name);
                    if (rs.next()) {
                        String pro_name = Util.null2String(rs.getString("fyxmmc"));
                        temp1 += flag1 + pro_name;
                        flag1 = "/";
                    }
                }
                if ("1".equals(expType)) {
                    PZZY = applyNum + applyDate + supplName + temp;
                } else {
                    PZZY = applyNum + applyDate + supplName + temp1;
                }
                String sql_update = " update " + tableName + " set PZZY='" + PZZY + "' where requestid= " + requestid;
                rs.executeSql(sql_update);
                log.writeLog("sql_update=" + sql_update);
            } else {
                String temp = "";//资产描述
                String flag = "";
                String temp1 = "";//费用项目名称
                String flag1 = "";
                //查询明细表1
                sql = "select * from " + tableNamedt + " where mainid=" + mainID;
                rs_dt.execute(sql);
                log.writeLog("查询费用科目:" + sql);
                while (rs_dt.next()) {
                    String zcms = Util.null2String(rs_dt.getString("ZCMS"));
                    String pro_id = Util.null2String(rs_dt.getString("FYXMMC"));
                    temp += flag + zcms;
                    flag = "/";
                    String sql_pro = " select fyxmmc from uf_fyxmdzb where id=" + pro_id;
                    log.writeLog("sql_pro=" + sql_pro);
                    rs.executeSql(sql_pro);
                    if (rs.next()) {
                        String proname = Util.null2String(rs.getString("fyxmmc"));
                        temp1 += flag1 + proname;
                        flag1 = "/";
                    }
                }
                if ("1".equals(expType)) {
                    PZZY = applyDate + supplName + temp;
                } else {
                    PZZY = applyDate + supplName + temp1;
                }
                String sql_update = " update " + tableName + " set PZZY='" + PZZY + "' where requestid= " + requestid;
                rs.executeSql(sql_update);
                log.writeLog("sql_update=" + sql_update);
                //}
                //非勾选删除
                sql = " delete from " + tableNamedt3 + " where mainid = " + mainID + " and XZ = 0";
                rs.execute(sql);
                log.writeLog("明细删除:" + sql);

            }
        } else {

            return "-1";
        }
        return SUCCESS;
    }

}

