package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/5/11.
 */
public class InsertAuthorizeModeAction implements Action {
    public String execute(RequestInfo info) {

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        BaseBean log = new BaseBean();//����д����־�Ķ���
        String modeID=info.getRequestid();//��ģ����id
        String sql="";
        String applier="";
        String[] applicant= new String[100];
        if(!"".equals(modeID)) {
            sql = " select * from uf_workflow_table where id= " + modeID;
            rs.executeSql(sql);
            //log.writeLog("sql1=" + sql);
            if (rs.next()) {
                applier = Util.null2String(rs.getString("applier"));
            }
            applicant = applier.split(",");
            if(applicant.length>0){
                sql="delete from uf_workflow_table_dt1 where mainid="+modeID;
                rs.executeSql(sql);
                //log.writeLog("sql_dele="+sql);
                for (int i = 0; i < applicant.length; i++) {
                    String temp_applier = applicant[i];
                    sql = " insert into uf_workflow_table_dt1(mainid,applicant) "
                            + " values(" + modeID + "," + temp_applier + ") ";
                    res.executeSql(sql);
                    //log.writeLog("i=" + i + "|sql_insert=" + sql);

                }
            }
        }else {
            log.writeLog("����id��ȡʧ��!");
            return "-1";
        }

        return SUCCESS;
    }
}
