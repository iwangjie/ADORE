package TaiSon.budget.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/27.
 * Updated on 16/10/26
 * Updated v2.0 on 16/12/20
 */
public class FeeApplyWorkflowAction implements Action {

    BaseBean log = new BaseBean();//����д����־�Ķ���

    public String execute(RequestInfo info) {
        log.writeLog("���������������FeeApplyWorkflowAction������������");

        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";
        Boolean isUpdate1 = false;
        Boolean isUpdate2 = false;

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
            log.writeLog("��ѯ������Ϣ=" + sql);
            rs.execute(sql);
            if (rs.next()) {
                mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
            }

            //��ѯ��ϸ��
            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            log.writeLog("��ѯ��ϸ��Ϣ=" + sql);
            rs.execute(sql);
            while (rs.next()) {
                //int groupPrice = rs.getInt("cxzj");
                double groupPrice = rs.getDouble("cxzj");
                double permission1 = rs.getDouble("qx1");
                double permission2 = rs.getDouble("qx2");
                //int permission1 = rs.getInt("qx1");
                //int permission2 = rs.getInt("qx2");
                log.writeLog("groupPrice=" + groupPrice);
                log.writeLog("permission1=" + permission1);
                log.writeLog("permission2=" + permission2);

                if (groupPrice < permission1) {
                    isUpdate1 = true;
                    //String sql_up1 = " update " + tableName + " set qx1=0 where requestid=" + requestid;
                    //log.writeLog("sql_up1=" + sql_up1);
                    //res.execute(sql_up1);
                } else {
                    log.writeLog("����Ҫ����Ȩ��1------------------isUpdate1=" + isUpdate1);
                }
                if (groupPrice < permission2) {
                    isUpdate2 = true;
                    //String sql_up2 = " update " + tableName + " set qx2=0 where requestid=" + requestid;
                    //log.writeLog("sql_up2=" + sql_up2);
                    //res.execute(sql_up2);
                } else {
                    log.writeLog("����Ҫ����Ȩ��2--------------------isUpdate2=" + isUpdate2);
                }

            }
            if (isUpdate1) {
                String sql_up1 = " update " + tableName + " set qx1=0 where requestid=" + requestid;
                log.writeLog("sql_up1=" + sql_up1 + "|" + isUpdate1);
                res.execute(sql_up1);
            } else {
                String sql_up1_null = " update " + tableName + " set qx1='' where requestid=" + requestid;
                log.writeLog("sql_up1_null=" + sql_up1_null + "|" + isUpdate1);
                res.execute(sql_up1_null);
            }
            if (isUpdate2) {
                String sql_up2 = " update " + tableName + " set qx2=0 where requestid=" + requestid;
                log.writeLog("sql_up2=" + sql_up2 + "|" + isUpdate2);
                res.execute(sql_up2);
            } else {
                String sql_up2_null = " update " + tableName + " set qx2='' where requestid=" + requestid;
                log.writeLog("sql_up2_null=" + sql_up2_null + "|" + isUpdate2);
                res.execute(sql_up2_null);
            }
        } else {
            log.writeLog("���̱���Ϣ��ȡʧ��!");
            return "-1";
        }
        return SUCCESS;
    }
}
