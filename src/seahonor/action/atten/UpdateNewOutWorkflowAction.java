package seahonor.action.atten;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.DateApartUtil;
import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateNewOutWorkflowAction implements Action {

    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入外出排班修改UpdateNewOutWorkflowAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        RecordSet res1 = new RecordSet();

        DateApartUtil mu = new DateApartUtil();

        String sql = "";
        String tableName = "";
        String[] ArrangedStaff = new String[100];
        //String oldArranged="";//ArrangedStaffs3
        String tmp_ArrangedStaff = "";
        String outStartDate = "";
        String outEndDate = "";
        String outStartTime = "";
        String outEndTime = "";
        String AttendanceStandard = "";
        String departmentid = "";
        String workcode = "";
        String subcompanyid1 = "";
        String relatedapply = "";
        String requestid = info.getRequestid();
        String wfcreater = "";
        String client = "";
        String address = "";
        String client_lev = "";
        String address_lev = "";

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            String sql_1 = "select * from " + tableName + " where requestid = " + info.getRequestid();
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {
                //oldArranged = Util.null2String(rs.getString("ArrangedStaffs3"));
                outStartDate = Util.null2String(rs.getString("AttendanceStartDate"));
                outEndDate = Util.null2String(rs.getString("AttendanceEndDate"));
                outStartTime = Util.null2String(rs.getString("WorkStartTime"));
                outEndTime = Util.null2String(rs.getString("WorkEndTime"));
                AttendanceStandard = Util.null2String(rs.getString("AttendanceStandard"));
                wfcreater = Util.null2String(rs.getString("creater"));
                relatedapply = Util.null2String(rs.getString("aboutOther"));//aboutOther
                client = Util.null2String(rs.getString("client"));
                address = Util.null2String(rs.getString("address"));
                client_lev = Util.null2String(rs.getString("client2"));
                address_lev = Util.null2String(rs.getString("address2"));
                ArrangedStaff = rs.getString("ArrangedStaffs").split(",");

                sql = " update uf_Replace_table set isActive =1,modedatacreatedate = CONVERT(varchar(10) ,GETDATE(), 23 ) ,"
                        + " modedatacreatetime = CONVERT(varchar(5) ,GETDATE(), 114 ) where requestId= " + relatedapply + "  ";
                res1.executeSql(sql);
                // 时间拆分
                for (String nowDate = outStartDate; nowDate.compareTo(outEndDate) <= 0; nowDate = mu.nextDay(nowDate)) {
                    for (int i = 0; i < ArrangedStaff.length; i++) {
                        String empid = ArrangedStaff[i];
                        sql = " exec dbo.one_sh_atten_info_clear_up_new '" + nowDate + "'," + empid + " ";
                        res.executeSql(sql);
                    }
                }

                if (!"3".equals(AttendanceStandard)) {

                    for (int i = 0; i < ArrangedStaff.length; i++) {
                        tmp_ArrangedStaff = ArrangedStaff[i];

                        String sql_hr = " select workcode,departmentid,subcompanyid1 from HrmResource where id= " + tmp_ArrangedStaff;
                        res.executeSql(sql_hr);
                        //log.writeLog("sql_hr="+sql_hr);
                        if (res.next()) {
                            workcode = Util.null2String(res.getString("workcode"));
                            departmentid = Util.null2String(res.getString("departmentid"));
                            subcompanyid1 = Util.null2String(res.getString("subcompanyid1"));
                        }
                        // 时间拆分
                        for (String nowDay = outStartDate; nowDay.compareTo(outEndDate) <= 0; nowDay = mu.nextDay(nowDay)) {

                            Map<String, String> mapStr = new HashMap<String, String>();
                            mapStr.put("requestId", requestid);
                            mapStr.put("modedatacreater", wfcreater);
                            mapStr.put("EmployeeName", tmp_ArrangedStaff);
                            mapStr.put("ReplaceStartDate", nowDay);
                            //mapStr.put("ReplaceEndDate", nowDay);
                            mapStr.put("ReplaceStartTime", outStartTime);
                            mapStr.put("ReplaceEndTime", outEndTime);
                            mapStr.put("AttendanceStandard", AttendanceStandard);
                            mapStr.put("modedatacreatedate", "##CONVERT(varchar(10) ,GETDATE(), 23 )");
                            mapStr.put("modedatacreatetime", "##CONVERT(varchar(5) ,GETDATE(), 114 )");
                            mapStr.put("formmodeid", "3");
                            mapStr.put("workcode", workcode);
                            mapStr.put("department", departmentid);
                            mapStr.put("subcompany", subcompanyid1);
                            mapStr.put("client", client);
                            mapStr.put("address", address);
                            mapStr.put("client_leave", client_lev);
                            mapStr.put("address_leave", address_lev);
                            mapStr.put("isActive", "0");

                            tableName = "uf_Replace_table";
                            InsertUtil IU = new InsertUtil();
                            IU.insert(mapStr, tableName);

                            sql = " exec dbo.one_sh_atten_info_clear_up_new '" + nowDay + "'," + tmp_ArrangedStaff + " ";
                            res.executeSql(sql);
                        }
                    }
                } else {
                    log.writeLog("其它考勤,不变更外出计划!");
                    return "-1";
                }
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }

}
