package ksy.book;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/10/19.
 * ��������
 */

public class BorrowBookApplyWorkflowAction implements Action {


    public String execute(RequestInfo info) {
        new BaseBean().writeLog("�����������BorrowBookApplyWorkflowAction_____________________________");

        BaseBean log = new BaseBean();

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String bookID = "";
        String personID = "";
        sql = " select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ") ";
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid = " + requestid;
            rs.executeSql(sql);
            if (rs.next()) {
                bookID = Util.null2String(rs.getString("tsmc"));
                personID = Util.null2String(rs.getString("sqr"));
                if (!"".equals(bookID)) {
                    sql = " update formtable_main_91 set tszt = '1',mqjyr='" + personID + "' where id = '" + bookID + "' ";
                    log.writeLog("sql_up1---------����ͼ��״̬" + sql);
                    res.executeSql(sql);

                    sql = " update hrmresource set numberfield1=nvl(numberfield1,0)+1 where id = " + personID;
                    res.executeSql(sql);
                    log.writeLog("sql_up2-------������Ա���Ĵ���" + sql);
                } else {
                    log.writeLog("ͼ��ID����");
                    return "-1";
                }
            }
        } else {
            log.writeLog("����ȡ����Ϣ����");
            return "-1";
        }
        return SUCCESS;
    }
}