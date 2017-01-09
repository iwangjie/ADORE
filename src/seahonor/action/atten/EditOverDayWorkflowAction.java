package seahonor.action.atten;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by adore on 16/7/26.
 */
public class EditOverDayWorkflowAction implements Action{
    BaseBean log = new BaseBean();//定义写入日志的对象
    public String execute(RequestInfo info) {
        log.writeLog("进入跨天数据修改EditOverDayWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";

        sql  = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if(rs.next()){
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if(!"".equals(tableName)){
            tableNamedt = tableName + "_dt1";

            // 查询主表
            sql = "select * from "+tableName + " where requestid="+requestid;
            rs.execute(sql);
            if(rs.next()){
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
            }

            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid="+mainID;
            rs.execute(sql);
            while(rs.next()){
                String empid = Util.null2String(rs.getString("name"));//获取明细表中数据
                String changeDate = Util.null2String(rs.getString("changeDate"));
                String changeData = Util.null2String(rs.getString("changeData"));
                //String dataID = changeData.substring(1);

                sql=" select  detail_time from sh_atten_view  where id="+changeData+" ";
                res.execute(sql);
                if(res.next()) {
                    String detail_time = Util.null2String(res.getString("detail_time"));
                    String attend_time = detail_time.substring(11);

                    if (!"".equals(attend_time)) {
                        sql = " update uf_all_attend_info set atten_end_time='"+attend_time+"',is_over_day=1,early_leave_times=0 "
                        +" where emp_id="+empid+" and atten_day='"+changeDate+"' and isEffective=0 ";
                        log.writeLog(" sql_update_1= "+sql);
                        res.execute(sql);

                        sql = " update uf_tempAttendDetail set effective=1   where id="+changeData+" ";
                        log.writeLog(" sql_update_2= "+sql);
                        res.execute(sql);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String nextDay = "";
                        try {
                            Date nowDate = sdf.parse(changeDate);
                            Date beforeDate = new Date(nowDate.getTime()+24*3600*1000L);
                            nextDay = sdf.format(beforeDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        sql = " exec dbo.one_sh_atten_info_clear_up_new '"+nextDay+"',"+empid+" ";
                        log.writeLog(" sql_update_3= "+sql);
                        res.execute(sql);
                    }
                }
            }
        }else{
            log.writeLog("流程表信息获取失败!");
            return "-1";
        }
        return SUCCESS;
    }
}
