package gvo.baoxiao;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class RejectBaoxiaoWorkflowAction implements Action {

    public String execute(RequestInfo info) {
        new BaseBean().writeLog("������ֽ𸶿���˻�RejectBaoxiaoWorkflowAction������������");

        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        RecordSet res = new RecordSet();
        String sql1 = "";
        String sql2 = "";
        String tableName = "";
        String tableNamedt = "";
        String htbhID = "";
        String bcfkje = "";
        sql1 = " Select tablename From Workflow_billdetailtable  Where billid in ("
                + "Select formid From workflow_base Where id="
                + info.getWorkflowid() + ")";
        //new BaseBean().writeLog("sql1---RejectBaoxiaoWorkflowAction------" + sql1);
        rs.executeSql(sql1);
        if (rs.next()) {
            tableNamedt = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableNamedt)) {

            sql2 = " Select tablename From Workflow_bill Where id in ("
                    + " Select formid From workflow_base Where id= "
                    + info.getWorkflowid() + ")";
            //new BaseBean().writeLog("sql2----RejectBaoxiaoWorkflowAction-----" + sql2);
            rs1.executeSql(sql2);
            if (rs1.next()) {
                tableName = Util.null2String(rs1.getString("tablename"));
            }
            String sql_1 = " select * from " + tableNamedt
                    + " where mainid in (" + " Select id From " + tableName
                    + " where requestid = " + info.getRequestid() + ")";
            //new BaseBean().writeLog("sql_1----RejectBaoxiaoWorkflowAction-----" + sql_1);
            res.executeSql(sql_1);
            while (res.next()) {
                htbhID = Util.null2String(res.getString("htbh"));
                bcfkje = Util.null2String(res.getString("bcfkeoc"));
                if (!"".equals(htbhID)) {
                    String sql_up = " update formtable_main_253 set ljyfk = nvl(ljyfk,0)  -  (" + bcfkje + ") where id = " + htbhID;
                    new BaseBean().writeLog("sql_up---RejectBaoxiaoWorkflowAction------" + sql_up);

                    if (!rs.executeSql(sql_up)) {
                        new BaseBean().writeLog("״̬����ʧ��");
                        return "-1";
                    }
                } else {
                    new BaseBean().writeLog("��ͬ��Ż�ȡʧ��---RejectBaoxiaoWorkflowAction------");
                }
            }
        }
        return SUCCESS;
    }

}
