package ksy.book;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class ReturnBookApplyWorkflowAction implements Action {


    public String execute(RequestInfo info) {
        new BaseBean().writeLog("���뻹������action_____________________________");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String bookID = "";
        String personID = "";
        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        new BaseBean().writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {

            String sql_1 = "select * from " + tableName + " where requestid = " + info.getRequestid();
            new BaseBean().writeLog("sql1---------" + sql_1);
            res.executeSql(sql_1);
            if (res.next()) {
                bookID = res.getString("guihuantushu");
                personID = res.getString("sqr");
                if (!"".equals(bookID)) {
                    String sql_up = "update formtable_main_91 set tszt = '0',mqjyr = '',tsjyrq = '' where id = '" + bookID + "'";
                    String sql_up1 = "update hrmresource set numberfield1=numberfield1-1 where numberfield1>0 and id = " + personID;
                    new BaseBean().writeLog("sql_up1-------" + sql_up1);
                    new BaseBean().writeLog("sql_up---------" + sql_up);
                    if (!rs.executeSql(sql_up)) {
                        new BaseBean().writeLog("ͼ��״̬����ʧ��");
                        return "-1";
                    }
                    if (!rs.executeSql(sql_up1)) {
                        new BaseBean().writeLog("��Ա���Ĵ�������ʧ��");
                        return "-1";
                    }
                } else {
                    new BaseBean().writeLog("ͼ��ID����");
                    return "-1";
                }
            } else {
                new BaseBean().writeLog("����ȡ����Ϣ����");
                return "-1";
            }
        }
        return SUCCESS;
    }
}