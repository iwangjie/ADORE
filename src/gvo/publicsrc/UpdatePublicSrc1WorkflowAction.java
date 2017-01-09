//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gvo.publicsrc;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdatePublicSrc1WorkflowAction implements Action {
    public UpdatePublicSrc1WorkflowAction() {
    }

    public String execute(RequestInfo info) {
        (new BaseBean()).writeLog("进入资源借用申请UpdatePublicSrcWorkflowAction_____");
        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String srcID = "";
        String personID = "";
        String beginborrowdate = "";
        String endborrowdate = "";
        String beginborrowtime = "";
        String endborrowtime = "";
        sql = "Select tablename From Workflow_bill Where id in (Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
        (new BaseBean()).writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if(rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if(!" ".equals(tableName)) {
            String sql_1 = "select * from " + tableName + " where requestid = " + info.getRequestid();
            (new BaseBean()).writeLog("sql1---------" + sql_1);
            res.executeSql(sql_1);
            if(!res.next()) {
                (new BaseBean()).writeLog("流程信息表读取错误");
                return "-1";
            }

            srcID = res.getString("zymc");
            personID = res.getString("sqr");
            beginborrowdate = Util.null2String(res.getString("yjjyksrq"));
            endborrowdate = Util.null2String(res.getString("yjjyjsrq"));
            beginborrowtime = Util.null2String(res.getString("yjjykssj"));
            endborrowtime = Util.null2String(res.getString("yjjyjssj"));
            if("".equals(srcID)) {
                (new BaseBean()).writeLog("资源ID错误");
                return "-1";
            }

            String sql_up = " update formtable_main_215 set zyzt = \'1\',mqjyr=\'" + personID + "\'," + " yjjyksrq = \'" + beginborrowdate + "\',yjjyjsrq = \'" + endborrowdate + "\'," + " yjjykssj = \'" + beginborrowtime + "\',yjjyjssj = \'" + endborrowtime + "\' where id = \'" + srcID + "\'";
            (new BaseBean()).writeLog("sql_up---------" + sql_up);
            if(!rs.executeSql(sql_up)) {
                (new BaseBean()).writeLog("资源状态更新失败");
                return "-1";
            }
        }

        return "1";
    }
}
