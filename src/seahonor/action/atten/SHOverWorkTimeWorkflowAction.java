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

    BaseBean log = new BaseBean();//定义写入日志的对象
    public String execute(RequestInfo info) {

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

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

            // 查询主表
            sql = "select * from "+tableName + " where requestid="+requestid;
            rs.execute(sql);
            if(rs.next()){
                userid = Util.null2String(rs.getString("applyuser"));
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
            }

            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid="+mainID;
            rs.execute(sql);
            while(rs.next()){
                //userid = Util.null2String(rs.getString("applyuser"));
                dtid=Util.null2String(rs.getString("ID"));
                sql="execute dbo.sh_overtime_info_add_new @empid="+userid+",@mainid="+dtid+",@requestid="+requestid+" ";
                boolean result=res.execute(sql);
                if(!result){
                    log.writeLog("result="+result+"|存储过程未执行。");
                    return "-1";
                }
            }
        }else{
            return "-1";
        }
        return SUCCESS;
    }

}
