package gvo.work;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
//import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;

public class workSHUAKAWorkflowError implements Action {

    public String execute(RequestInfo info) {

        new BaseBean().writeLog("进入刷卡异常action_____________________________");

        weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
        rs.executeSql("select *... where aactive=0 ");
        while (rs.next()) {
            String tmp1 = Util.null2String(rs.getString("titleName"));
            String tmp2 = Util.null2String(rs.getString("titleName"));

        }
        RecordSetDataSource rsd = new RecordSetDataSource("local_HR");
        String sql = "";
        String tableName = "";
        String d_ct = "";
        String d_cb = "";
        sql = "Select tablename From Workflow_bill Where id=(";
        sql += "Select formid From workflow_base Where id="
                + info.getWorkflowid() + ")";
        new BaseBean().writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }


        if (!"".equals(tableName)) {
            String sql_1 = "select * from " + tableName + " where requestid = "
                    + info.getRequestid();
            new BaseBean().writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                d_cb = rs.getString("sqrgh");
                d_ct = rs.getString("sqrq");
                d_ct = d_ct.replace("-", "/");
            }
        } else {
            new BaseBean().writeLog("流程取表信息错误");
            return "-1";
        }
        DetailTable[] detailtable = info.getDetailTableInfo().getDetailTable();

        String d_keyid = "";//原打卡记录ID
        String d_ygbm = "";//员工编码
        String d_ybsw = "";//原标示位
        String d_bianji = "";//编辑
        String d_xbsw = "";//现标示位
        String d_remark = "";//事由
        String d_sksj = "";//刷卡时间
        if (detailtable.length > 0) {
            for (int i = 0; i < detailtable.length; i++) {
                // 指定明细表
                DetailTable dt = detailtable[i];
                // 取出当前明细表中的所有记录，按行存取
                Row[] row = dt.getRow();
                //	int cc = 0;
                for (int j = 0; j < row.length; j++) {
                    //		cc++;
                    // 取出当前数据中的一行记录
                    Row r = row[j];
                    // 取出当前这行数据中每列的数据
                    Cell[] cell = r.getCell();
                    for (int k = 0; k < cell.length; k++) {
                        Cell c = cell[k];
                        // 获取明细表字段名
                        String name = c.getName();

                        if (name.equalsIgnoreCase("keyid")) {// 原打卡记录ID
                            d_keyid = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("gh")) {// 员工编码
                            d_ygbm = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("dcbsw")) {// 原标示位
                            d_ybsw = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("clfs")) {// 编辑
                            d_bianji = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("xghbsw")) {// 现标示位
                            d_xbsw = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("ycyy")) {// 事由
                            d_remark = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("sksj")) {// 刷卡时间
                            d_sksj = c.getValue().trim();
                        }

                    }

                    new BaseBean().writeLog("原打卡记录ID" + d_keyid + "员工编码" + d_ygbm + "原标示位" + d_ybsw + "编辑" + d_bianji + "现标示位" + d_xbsw + "事由" + d_remark + "刷卡时间" + d_sksj);
                    new BaseBean().writeLog("申请人工号" + d_cb + "创建日期" + d_ct + "流程ID" + info.getRequestid());
                    if (!"".equals(d_keyid) && !"".equals(d_ygbm) && !"".equals(d_ybsw) && !"".equals(d_bianji) && !"".equals(d_xbsw) && !"".equals(d_remark) && !"".equals(d_cb) && !"".equals(d_ct) && !"".equals(d_sksj)) {
                        sql = "insert into cus_ats_originality_data_change_info(key_id,process_id,ats_originality_data_id,emp_code,att_datetime,inorout_change_befor,change_type_code,inorout_change_after,remark,create_dt,create_by)"
                                + " values "
                                + "("
                                + "newID()"
                                + ",'"
                                + info.getRequestid()
                                + "','"
                                + d_keyid
                                + "','"
                                + d_ygbm
                                + "','"
                                + d_sksj
                                + "','"
                                + d_ybsw
                                + "','"
                                + d_bianji
                                + "','"
                                + d_xbsw
                                + "','"
                                + d_remark
                                + "','"
                                + d_ct
                                + "','"
                                + d_cb + "')";


                        new BaseBean().writeLog("sql-----------------插入刷卡异常表------------------------" + sql);
                        if (!rsd.executeSql(sql)) {
                            new BaseBean().writeLog("sql-----------------插入刷卡中间表失败-----------------------" + sql);
                            return "-1";
                        } else {
                            new BaseBean().writeLog("sql-----------------插入刷卡异常中间表成功------------" + sql);
                        }
                    } else {
                        new BaseBean().writeLog("写入信息缺失");
                    }

                }

            }
        }
        return SUCCESS;
    }
}
