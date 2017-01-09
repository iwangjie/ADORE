package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/19.
 * 调休假有效期修改action（old）
 */
public class UpdateHolidayDateWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String holidayApp = "";
        String newDate = "";
        String leftTime = "";
        String requestid = info.getRequestid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        //log.writeLog("sql1---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid = " + requestid;
            //log.writeLog("sql2---------" + sql);
            rs.executeSql(sql);
            if (rs.next()) {
                holidayApp = Util.null2String(rs.getString("holidayApp"));
                newDate = Util.null2String(rs.getString("newDate"));
                leftTime = Util.null2String(rs.getString("leftTime"));
            }

            if (!"".equals(holidayApp) && !"".equals(newDate)) {
                sql = " update uf_holiday_apply set IsEffective=1 where id= " + holidayApp;

                rs.executeSql(sql);

                sql = " select COUNT(id) as num_exist from uf_holiday_apply where enddate = '" + newDate + "' ";
                rs.execute(sql);
                if (rs.next()) {
                    String num_exist = Util.null2String(rs.getString("num_exist"));

                    if (!"0".equals(num_exist)) {
                        sql = " update uf_holiday_apply set applydays +=" + leftTime + " where id= " + holidayApp;
                        res.executeSql(sql);
                    }
                }
            } else {
                log.writeLog("holidayApp&&newDate获取失败");
                return "-1";
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}
