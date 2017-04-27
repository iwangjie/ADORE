package gt.table;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class ProStausChangeAction implements Action {

    /**
     * 如果一个流程出现2个状态改变的节点，需要单独写Action
     */
    public String execute(RequestInfo request) {

        String workFlowId = request.getWorkflowid();
        String requestId = request.getRequestid();
        RecordSet rs = new RecordSet();

        RecordSet rs_update = new RecordSet();

        String sql = "";
        String tableName = "";

        sql = "Select tablename From Workflow_bill Where id=(";
        sql += "Select formid From workflow_base Where id=" + workFlowId + ")";
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        sql = "select * from formtable_main_123 where workflowInfo=" + workFlowId;
        rs.executeSql(sql);
        while (rs.next()) {
            String proCodeField = Util.null2String(rs.getString("proField1"));

            String proCode = "";
            sql = "select * from " + tableName + " where requestid=" + requestId;
            rs_update.executeSql(sql);
            if (rs_update.next()) {
                proCode = Util.null2String(rs_update.getString(proCodeField));
            }

            // WorkflowForm 修改表单
            // FieldForm  修改字段
            // ProStatus 修改值
            String proCodeField2 = Util.null2String(rs.getString("proField2"));
            String formName = Util.null2String(rs.getString("WorkflowForm"));
            String fieldName = Util.null2String(rs.getString("FieldForm"));
            int status = rs.getInt("ProStatus");

            // 拼凑update SQL语句
            StringBuffer tmp_buff = new StringBuffer();
            tmp_buff.append("update ");
            tmp_buff.append(formName);
            tmp_buff.append(" set ");
            tmp_buff.append(fieldName);
            tmp_buff.append(" = ");
            tmp_buff.append(status);
            tmp_buff.append(" where  ");
            tmp_buff.append(proCodeField2);
            tmp_buff.append(" = '");
            tmp_buff.append(proCode);
            tmp_buff.append("'");

            rs_update.executeSql(tmp_buff.toString());

        }

        return SUCCESS;
    }
}
