package TaiSon.budget.action;

import TaiSon.Util.FinanceUsq;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/7.
 * update 16/9/30
 */
public class freezeBudgetWorkflowAction extends BaseBean implements Action {
    public String execute(RequestInfo info) {

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        BaseBean log = new BaseBean();

        log.writeLog("����Ԥ�㶳��freezeBudgetWorkflowAction������������");

        String sql = "";
        String tableName = "";
        String requestid = info.getRequestid();
        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        //new BaseBean().writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid = " + requestid;
            //new BaseBean().writeLog("sql1---------" + sql);
            rs.executeSql(sql);
            if (rs.next()) {
                String empID = Util.null2String(rs.getString("sqr"));
                String startDate = Util.null2String(rs.getString("hdqsrq"));
                String endDate = Util.null2String(rs.getString("hkjsrq"));
                String type = Util.null2String(rs.getString("yewuleixing"));
                String amount = Util.null2String(rs.getString("aje"));

                FinanceUsq fu = new FinanceUsq();
                int result = fu.freeze(empID, startDate, endDate, type, amount, requestid);
                log.writeLog("result---------" + result);
                if (result > 0) {
                    String logcontent = "";
                    String sql_err = " select logcontent from uf_budgetErrorLog where logid= " + result;
                    res.executeSql(sql_err);
                    log.writeLog("sql_err!--------" + sql_err);
                    if (res.next()) {
                        logcontent = Util.null2String(res.getString("logcontent"));
                    }
                    //�����쳣 ���ش�����Ϣ
                    info.getRequestManager().setMessageid(System.currentTimeMillis() + "");
                    info.getRequestManager().setMessagecontent(logcontent);
                    //info.getRequestManager().setMessagecontent("�������ԭ������ϵϵͳ����Ա��");
                    log.writeLog("Ԥ�㶳��ʧ��!--------" + logcontent);
                    return "-1";
                }
            } else {
                new BaseBean().writeLog("��������Ϣ��ȡ����!");
                return "-1";
            }

        } else {
            new BaseBean().writeLog("������Ϣ���ȡ����");
            return "-1";
        }
        return SUCCESS;

    }
}
