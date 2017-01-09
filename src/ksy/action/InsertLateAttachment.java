package ksy.action;

import ksy.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2016/10/12.
 * ���к��ڹ���
 */
public class InsertLateAttachment implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("���к��ڹ���InsertMiddleAttachment��������������������");

        RecordSet rs = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String Main_id = "";
        String pro_id = "";
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

            String sql_main = "select * from " + tableName + " where requestid = " + requestid;
            rs.executeSql(sql_main);
            if (rs.next()) {
                pro_id = Util.null2String(rs.getString("tm"));
                Main_id = Util.null2String(rs.getString("id"));
            }
            //��ѯ��ϸ����Ϣ
            sql = "select * from " + tableNamedt + " where mainid=" + Main_id;
            rs.execute(sql);
            while (rs.next()) {
                String doc_name = Util.null2String(rs.getString("wjmc"));
                String attach = Util.null2String(rs.getString("fj"));

                Map<String, String> mapStr_D = new HashMap<String, String>();
                mapStr_D.put("mainid", pro_id);
                mapStr_D.put("wjmc", doc_name);
                mapStr_D.put("fj", attach);

                String table_D = "uf_lixiangshenqing_dt2";
                IU.insert(mapStr_D, table_D);
                log.writeLog("��ϸ2����ɹ�-----------");
            }
        } else {
            log.writeLog("������Ϣ���ȡ����");
            return "-1";
        }
        return SUCCESS;
    }
}
