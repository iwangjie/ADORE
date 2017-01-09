package ksy.action;

import ksy.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2016/10/14.
 * 题目费用报销
 */
public class UpdateProFeeSpend implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("题目费用报销UpdateProFeeSpend——————————");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        InsertUtil IU = new InsertUtil();

        String sql = "";
        String tableName = "";
        String feeSpend = "";
        String pro_id = "";
        String requestid = info.getRequestid();
        String workflowid = info.getWorkflowid();

        sql = "Select tablename From Workflow_bill Where id in (" + "Select formid From workflow_base Where id=" + workflowid + ")";
        //log.writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            String sql_main = "select * from " + tableName + " where requestid = " + requestid;
            rs.executeSql(sql_main);
            if (rs.next()) {
                pro_id = Util.null2String(rs.getString("lxsq"));
                feeSpend = Util.null2String(rs.getString("yihuafeijine"));
                if(!"".equals(pro_id)&&!"".equals(feeSpend)){
                    String sql_update = " update uf_lixiangshenqing set yhfje="+feeSpend+" where id="+pro_id+" and formmodeid =161 ";
                    res.executeSql(sql_update);
                    log.writeLog("sql_update="+sql_update);
                } else {
                log.writeLog("流程数据获取失败！");
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
