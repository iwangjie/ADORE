package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;


/**
 * Created by adore on 15/12/30.
 */
public class SHOverWorkTimeWorkflowAction implements Action{

    BaseBean log = new BaseBean();//����д����־�Ķ���
    public String execute(RequestInfo info) {

        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";
        String userid="";
        String dtid="";

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
                userid = Util.null2String(rs.getString("applyuser"));
                mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
            }

            //��ѯ��ϸ��
            sql = "select * from " + tableNamedt + " where mainid="+mainID;
            rs.execute(sql);
            while(rs.next()){
                //userid = Util.null2String(rs.getString("applyuser"));
                dtid=Util.null2String(rs.getString("ID"));
                sql="execute dbo.sh_overtime_info_add_new @empid="+userid+",@mainid="+dtid+",@requestid="+requestid+" ";
                boolean result=res.execute(sql);
                if(!result){
                    log.writeLog("result="+result+"|�洢����δִ�С�");
                    return "-1";
                }
            }
        }else{
            return "-1";
        }
        return SUCCESS;
    }

}
