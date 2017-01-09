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

import static org.apache.commons.lang3.ObjectUtils.max;
import static org.apache.commons.lang3.ObjectUtils.min;

public class UpdateOutWorkflowAction implements Action {


    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入外出排班UpdateOutWorkflowAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        RecordSet rs2 = new RecordSet();
        RecordSet rs3 = new RecordSet();

        DateApartUtil mu = new DateApartUtil();

        String sql = "";
        String tableName = "";
        String[] ArrangedStaff = new String[100];
        String tmp_ArrangedStaff = "";
        String AttendanceStartDate = "";
        String AttendanceEndDate = "";
        String WorkStartTime = "";
        String WorkEndTime = "";
        String AttendanceStandard = "";
        String departmentid = "";
        String workcode = "";
        String subcompanyid1 = "";
        String wfcreater = "";
        String client = "";
        String address = "";
        String client_lev = "";
        String address_lev = "";
        String requestid = info.getRequestid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            String sql_1 = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql1---------" + sql_1);
            rs.executeSql(sql_1);
            if (rs.next()) {

                AttendanceStartDate = Util.null2String(rs.getString("AttendanceStartDate"));
                AttendanceEndDate = Util.null2String(rs.getString("AttendanceEndDate"));
                WorkStartTime = Util.null2String(rs.getString("WorkStartTime"));
                WorkEndTime = Util.null2String(rs.getString("WorkEndTime"));
                AttendanceStandard = Util.null2String(rs.getString("AttendanceStandard"));
                wfcreater = Util.null2String(rs.getString("creater"));
                client = Util.null2String(rs.getString("client"));
                address = Util.null2String(rs.getString("address"));
                client_lev = Util.null2String(rs.getString("client2"));
                address_lev = Util.null2String(rs.getString("address2"));
                ArrangedStaff = rs.getString("ArrangedStaff").split(",");
                if (!"3".equals(AttendanceStandard)) {
                    for (int i = 0; i < ArrangedStaff.length; i++) {
                        tmp_ArrangedStaff = ArrangedStaff[i];
                        if (!"".equals(tmp_ArrangedStaff)) {
                            String sql_hr = " select workcode,departmentid,subcompanyid1 from HrmResource where id= " + tmp_ArrangedStaff;
                            res.executeSql(sql_hr);

                            if (res.next()) {
                                workcode = Util.null2String(res.getString("workcode"));
                                departmentid = Util.null2String(res.getString("departmentid"));
                                subcompanyid1 = Util.null2String(res.getString("subcompanyid1"));
                            }

                            // 时间拆分
                            for (String nowDay = AttendanceStartDate; nowDay.compareTo(AttendanceEndDate) <= 0; nowDay = mu.nextDay(nowDay)) {
                                sql = " select count(id) as num_exist from uf_Replace_table where EmployeeName=" + tmp_ArrangedStaff + " and" +
                                        " ReplaceStartDate='" + nowDay + "' and isActive=0 ";
                                res.execute(sql);
                                if (res.next()) {
                                    int num_exist = res.getInt("num_exist");
                                    if (num_exist > 0) {
                                        sql = " select AttendanceStandard,ReplaceStartTime,ReplaceEndTime,id from uf_Replace_table where EmployeeName=" + tmp_ArrangedStaff + " and "
                                                + " ReplaceStartDate='" + nowDay + "' and isActive=0 ";
                                        rs2.execute(sql);
                                        if (rs2.next()) {
                                            String tmpstandard = Util.null2String(rs2.getString("AttendanceStandard"));
                                            String tmpStart = Util.null2String(rs2.getString("ReplaceStartTime"));
                                            String tmpEnd = Util.null2String(rs2.getString("ReplaceEndTime"));
                                            String outID = Util.null2String(rs2.getString("id"));

                                            String startTime = min(tmpStart, WorkStartTime);
                                            String endTime = max(tmpEnd, WorkEndTime);

                                            if (AttendanceStandard.equals(tmpstandard)) {
                                                sql = " update uf_Replace_table set ReplaceStartTime='" + WorkStartTime + "',ReplaceEndTime='" + WorkEndTime + "' "
                                                        + " ,client='" + client + "',address='" + address + "',client_leave='" + client_lev + "',address_leave='" + address_lev + "' where id=" + outID;
                                                rs3.execute(sql);

                                                sql = " exec dbo.one_sh_atten_info_clear_up_new '" + nowDay + "'," + tmp_ArrangedStaff + " ";
                                                rs3.executeSql(sql);
                                            } else {
                                                if ("0".equals(tmpstandard)) {
                                                    if ("1".equals(AttendanceStandard)) {
                                                        sql = " update uf_Replace_table set ReplaceStartTime='" + startTime + "',ReplaceEndTime='" + endTime + "'"
                                                                + " ,AttendanceStandard=2,client_leave='" + client_lev + "',address_leave='" + address_lev + "' where id=" + outID;
                                                        rs3.execute(sql);
                                                    } else if ("2".equals(AttendanceStandard)) {
                                                        sql = " update uf_Replace_table set ReplaceStartTime='" + startTime + "',ReplaceEndTime='" + endTime + "'"
                                                                + " ,AttendanceStandard=2,client='" + client + "',address='" + address + "',client_leave='" + client_lev + "' "
                                                                + ",address_leave='" + address_lev + "' where id=" + outID;
                                                        rs3.execute(sql);
                                                    } else {
                                                        sql = " update uf_Replace_table set ReplaceStartTime='" + startTime + "',ReplaceEndTime='" + endTime + "'"
                                                                + " ,AttendanceStandard=3,client='" + client + "',address='" + address + "',client_leave='" + client_lev + "' "
                                                                + ",address_leave='" + address_lev + "' where id=" + outID;
                                                        rs3.execute(sql);
                                                    }
                                                } else if ("1".equals(tmpstandard)) {
                                                    if ("0".equals(AttendanceStandard)) {
                                                        sql = " update uf_Replace_table set ReplaceStartTime='" + startTime + "',ReplaceEndTime='" + endTime + "'"
                                                                + " ,AttendanceStandard=2,client='" + client + "',address='" + address + "' where id=" + outID;
                                                        rs3.execute(sql);
                                                    } else if (AttendanceStandard.equals("2")) {
                                                        sql = " update uf_Replace_table set ReplaceStartTime='" + startTime + "',ReplaceEndTime='" + endTime + "'"
                                                                + " ,AttendanceStandard=2,client='" + client + "',address='" + address + "',client_leave='" + client_lev + "' "
                                                                + ",address_leave='" + address_lev + "' where id=" + outID;
                                                        rs3.execute(sql);
                                                    } else {
                                                        sql = " update uf_Replace_table set ReplaceStartTime='" + startTime + "',ReplaceEndTime='" + endTime + "'"
                                                                + " ,AttendanceStandard=3,client='" + client + "',address='" + address + "',client_leave='" + client_lev + "' "
                                                                + ",address_leave='" + address_lev + "' where id=" + outID;
                                                        rs3.execute(sql);
                                                    }
                                                } else {
                                                    sql = " update uf_Replace_table set ReplaceStartTime='" + WorkStartTime + "',ReplaceEndTime='" + WorkEndTime + "',AttendanceStandard=" + AttendanceStandard + " "
                                                            + " ,client='" + client + "',address='" + address + "',client_leave='" + client_lev + "',address_leave='" + address_lev + "' where id=" + outID;
                                                    rs3.execute(sql);
                                                }
                                                sql = " exec dbo.one_sh_atten_info_clear_up_new '" + nowDay + "'," + tmp_ArrangedStaff + " ";
                                                rs3.executeSql(sql);
                                            }
                                        }

                                    } else {
                                        Map<String, String> mapStr = new HashMap<String, String>();
                                        mapStr.put("requestId", requestid);
                                        mapStr.put("modedatacreater", wfcreater);
                                        mapStr.put("EmployeeName", tmp_ArrangedStaff);
                                        mapStr.put("ReplaceStartDate", nowDay);
                                        mapStr.put("ReplaceEndDate", nowDay);
                                        mapStr.put("ReplaceStartTime", WorkStartTime);
                                        mapStr.put("ReplaceEndTime", WorkEndTime);
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
                                        rs3.executeSql(sql);
                                    }
                                }
                            }
                        } else {
                            log.writeLog("人员ID获取错误");
                            return "-1";
                        }
                    }
                } else {
                    log.writeLog("其它考勤,不变更外出计划!");
                }
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }

}
