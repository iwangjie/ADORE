package gt.table;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/17.
 * ����--���ڳ��������Ƿ��Ѿ���BOM��
 */
public class UpdateBomWorkflowAction implements Action{
    BaseBean log = new BaseBean();//����д����־�Ķ���

    public String execute(RequestInfo info) {

        //log.writeLog("GTOFT-501-BOM��������UpdateBomWorkflowAction������������");

        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();

        String sql = "";
        String tableName = "";
        String wkFlag = "";

        sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            // ��ѯ����
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                wkFlag = Util.null2String(rs.getString("wkFlag"));//��������id
            }

            // ����ԭ���ı�
            if(!"".equals(wkFlag)) {
                sql = " update formtable_main_61 set rs9 = 0 where requestid=" + wkFlag;
                rs.execute(sql);
                //log.writeLog("sql_update=" + sql);
                if (!rs.executeSql(sql)) {
                    log.writeLog("rs9����ʧ��");
                    return "-1";
                }
            }else{
                log.writeLog("��ȡwkFlagʧ�ܣ�");
            }
        } else {
            log.writeLog("������Ϣ���ȡʧ�ܣ�");
            return "-1";
        }
        return SUCCESS;
    }
}
