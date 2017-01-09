package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/8/8.
 */
public class RejectOutPlanWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入外出排班退回RejectOutPlanWorkflowAction");

        RecordSet rs = new RecordSet();

        String sql = "";
        String requestid = info.getRequestid();

        sql = " update uf_Replace_table set isActive =1,modedatacreatedate = CONVERT(varchar(10) ,GETDATE(), 23 ) ,"
                + " modedatacreatetime = CONVERT(varchar(5) ,GETDATE(), 114 ) where requestId= " + requestid + "  ";
        rs.executeSql(sql);

        log.writeLog("sql_reject=" + sql);

        return SUCCESS;
    }

}
