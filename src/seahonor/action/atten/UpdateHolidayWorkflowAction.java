package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/8/11.
 * ������Ч�ڱ��&���ںϲ�
 */
public class UpdateHolidayWorkflowAction implements Action {
    BaseBean log = new BaseBean();//����д����־�Ķ���

    public String execute(RequestInfo info) {
        log.writeLog("��������޸�UpdateHolidayWorkflowAction������������");

        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String emp_id = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";

        sql = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";

            // ��ѯ����
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
            }

            //��ѯ��ϸ��
            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            rs.execute(sql);
            while (rs.next()) {
                String holidayApp = Util.null2String(rs.getString("holidayApp"));//��ȡ��ϸ��������
                String leftTime = Util.null2String(rs.getString("leftTime"));
                String changeType = Util.null2String(rs.getString("changeType"));
                String newDate = Util.null2String(rs.getString("newDate"));

                sql = " exec dbo.sh_holiday_update " + holidayApp + "," + leftTime + "," + changeType + ",'" + newDate + "' ";
                res.execute(sql);
                log.writeLog("sql=" + sql);
                //sql = " update "+tableNamedt+" set holidayAppNew="+holidayApp+" where  ";
            }
        } else {
            log.writeLog("���̱���Ϣ��ȡʧ��!");
            return "-1";
        }
        return SUCCESS;
    }
}
