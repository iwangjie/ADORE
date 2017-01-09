package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;


/**
 * Created by adore on 16/7/22.
 */
public class EditAttendLateWorkflowAction implements Action{
    BaseBean log = new BaseBean();//定义写入日志的对象
    public String execute(RequestInfo info) {
        log.writeLog("进入考勤迟到EditAttendLateWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String applicant = "";//申请人
        String attendDate = "";//考勤日期
        String editLate = "";//修改后的迟到时间
        String editReason = "";//修改原因

        sql  = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if(rs.next()){
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if(!"".equals(tableName)){
            sql = "select * from "+tableName + " where requestid="+requestid;
            rs.execute(sql);
            if(rs.next()){
                applicant = Util.null2String(rs.getString("sqr"));
                attendDate = Util.null2String(rs.getString("kqrq"));
                editLate = Util.null2String(rs.getString("tzhcdsj"));
                editReason = Util.null2String(rs.getString("tzyy"));

                sql = " update uf_all_attend_info set late_times="+editLate+",late_remark='"+editReason+"' where emp_id="+applicant+" and atten_day='"+attendDate+"' and isEffective=0  ";
                log.writeLog("sql_update="+sql);
                res.execute(sql);
            }

        }else{
            log.writeLog("流程表单获取失败!");
            return "-1";
        }
        return SUCCESS;
    }

}
