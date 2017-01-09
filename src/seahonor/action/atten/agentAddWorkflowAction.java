package seahonor.action.atten;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/6/27.
 */
public class agentAddWorkflowAction implements Action{
    BaseBean log = new BaseBean();//����д����־�Ķ���
    public String execute(RequestInfo info) {
        log.writeLog("�������ô�����agentAddWorkflowAction������������");

        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();
        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String tableNameAgent="";
        String mainID = "";
        String[] agentors = new String[100];//��������
        String creator = "";//��Ȩ��

        sql  = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if(rs.next()){
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if(!"".equals(tableName)){
            tableNamedt = tableName + "_dt1";
            //tableNamedt = tableName + "_dt2";

            // ��ѯ����
            sql = "select * from "+tableName + " where requestid="+requestid;
            rs.execute(sql);
            if(rs.next()){
                mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
                creator = Util.null2String(rs.getString("authorizer"));
                agentors = Util.null2String(rs.getString("agentors")).split(",");
            }
            for(int i=0;i<agentors.length;i++){
                int num_exist=0;
                String tmp_agentor=agentors[i];//������
                //��ѯ��ϸ��
                sql = "select count(id) as num_exist from " + tableNamedt + " where agentor="+tmp_agentor+" and authorizer="+creator+" ";
                rs.execute(sql);
                if(rs.next()){
                    num_exist=rs.getInt("num_exist");
                    if(num_exist>0){
                        continue;
                    }else{
                        Map<String, String> mapStr = new HashMap<String, String>();
                        mapStr.put("mainid", mainID);
                        mapStr.put("authorizer", creator);
                        mapStr.put("agentor", tmp_agentor);

                        tableNameAgent = tableNamedt;
                        InsertUtil IU = new InsertUtil();
                        IU.insert(mapStr,tableNameAgent);
                    }
                }
            }
        }else{
            log.writeLog("������Ϣ��ȡʧ��!");
            return "-1";
        }
        return SUCCESS;
    }

}
