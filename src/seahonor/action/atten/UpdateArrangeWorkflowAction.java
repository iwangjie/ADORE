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
 * Created by adore on 16/8/22.
 * 排班申请流程
 */
public class UpdateArrangeWorkflowAction implements Action {
    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {
        log.writeLog("进入排班UpdateArrangeWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";
        String creator = "";
        String departmentid = "";
        String workcode = "";
        String subcompanyid1 = "";

        sql = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";

            // 查询主表
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
                creator = Util.null2String(rs.getString("cjr"));
            }

            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            rs.execute(sql);
            while (rs.next()) {
                int num_cc = 0;
                String empid = Util.null2String(rs.getString("EmployeeName"));
                String amBeginTime_tmp = Util.null2String(rs.getString("AmBeginTime"));
                String amEndTimes_tmp = Util.null2String(rs.getString("AmEndTime"));
                String pmBeginTime_tmp = Util.null2String(rs.getString("PmBeginTime"));
                String pmEndTime_tmp = Util.null2String(rs.getString("PmEndTime"));
                String effeStartDate_tmp = Util.null2String(rs.getString("StartDate"));
                String effeEndDate_tmp = Util.null2String(rs.getString("EndDate"));
                if (!"".equals(amBeginTime_tmp) || !"".equals(amEndTimes_tmp) || !"".equals(pmBeginTime_tmp) || !"".equals(pmEndTime_tmp) || !"".equals(effeStartDate_tmp) || !"".equals(effeEndDate_tmp)) {
                    String sql_hr = " select workcode,departmentid,subcompanyid1 from HrmResource where id= " + empid;
                    res.executeSql(sql_hr);
                    if (res.next()) {
                        workcode = Util.null2String(res.getString("workcode"));
                        departmentid = Util.null2String(res.getString("departmentid"));
                        subcompanyid1 = Util.null2String(res.getString("subcompanyid1"));
                    }
                    sql = " select count(*) as num_cc from uf_Scheduling_table where isActive = 0 and EmployeeName = " + empid + " "
                            + " and AmBeginTime='" + amBeginTime_tmp + "' and AmEndTime='" + amEndTimes_tmp + "' and PmBeginTime='" + pmBeginTime_tmp + "' and PmEndTime='" + pmEndTime_tmp + "' and EffectiveStartDate='" + effeStartDate_tmp + "' and EffectiveEndDate='" + effeEndDate_tmp + "'";
                    res.executeSql(sql);
                    if (res.next()) {
                        num_cc = res.getInt("num_cc");
                    }
                    if (num_cc < 1) {
                        sql = " update uf_Scheduling_table set isActive =1,modedatacreatedate = CONVERT(varchar(10) ,GETDATE(), 23 ),"
                                + " modedatacreatetime = CONVERT(varchar(5) ,GETDATE(), 114 ),modedatacreater=" + creator + " where EmployeeName= " + empid + " and isActive =0 ";
                        res.executeSql(sql);
                        //new BaseBean().writeLog("sqlupdate___________"+sql);

                        Map<String, String> mapStr = new HashMap<String, String>();
                        mapStr.put("EmployeeName", empid);
                        mapStr.put("AmBeginTime", amBeginTime_tmp);
                        mapStr.put("AmEndTime", amEndTimes_tmp);
                        mapStr.put("PmBeginTime", pmBeginTime_tmp);
                        mapStr.put("PmEndTime", pmEndTime_tmp);
                        mapStr.put("EffectiveStartDate", effeStartDate_tmp);
                        mapStr.put("EffectiveEndDate", effeEndDate_tmp);
                        mapStr.put("modedatacreater", creator);
                        mapStr.put("modedatacreatedate", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                        mapStr.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                        mapStr.put("formmodeid", "2");
                        mapStr.put("workcode", workcode);
                        mapStr.put("department", departmentid);
                        mapStr.put("subcompany", subcompanyid1);
                        mapStr.put("isActive", "0");

                        tableName = "uf_Scheduling_table";
                        InsertUtil IU = new InsertUtil();
                        IU.insert(mapStr, tableName);
                        //sql = " insert into uf_Scheduling_table(EmployeeName,AmBeginTime,AmEndTime,PmBeginTime,PmEndTime,EffectiveStartDate,EffectiveEndDate,formmodeid,Workcode,department,subcompany,modedatacreatedate,modedatacreatetime,isActive,modedatacreater)
                        // values (" + empid + ",'" + amBeginTime_tmp + "','" + amEndTimes_tmp + "','" + pmBeginTime_tmp + "','" + pmEndTime_tmp + "','" + effeStartDate_tmp + "','" + effeEndDate_tmp + "',2,'" + workcode + "','" + departmentid + "','" + subcompanyid1 + "',CONVERT(varchar(10) ,GETDATE(), 23 ),CONVERT(varchar(5) ,GETDATE(), 114 ),0," + creator + ") ";
                    }
                }
            }
        } else {
            log.writeLog("流程表信息获取失败!");
            return "-1";
        }
        return SUCCESS;
    }
}
