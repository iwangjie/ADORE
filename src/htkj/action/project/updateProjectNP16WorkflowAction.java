package htkj.action.project;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/4/21.
 */
public class updateProjectNP16WorkflowAction implements Action{

    public String execute(RequestInfo info) {

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();

        String sql = "";
        String tableName = "";
        String requestid = info.getRequestid();
        sql = "Select tablename From Workflow_bill Where id in ("+ "Select formid From workflow_base Where id="+ info.getWorkflowid() + ")";
        //new BaseBean().writeLog("sql---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!" ".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid = "+ requestid;
            //new BaseBean().writeLog("sql1---------" + sql);
            rs.executeSql(sql);
            if (rs.next()) {
                String project_name = Util.null2String(rs.getString("project_name"));
                //String proj_process = Util.null2String(rs.getString("proj_process"));
                //String proj_processid = Util.null2String(rs.getString("proj_processid"));

                String sql_up = " update cus_fielddata set process='量产导入',processid='NP16'  where  id= "+project_name+" "
                        +" and scope='ProjCustomFieldReal' and scopeid= 21 ";

                res.executeSql(sql_up);
                //new BaseBean().writeLog("sql_up---------" + sql_up);
                if (!res.executeSql(sql_up)) {
                    new BaseBean().writeLog("项目过程信息更新失败");
                    return "-1";
                }
            } else {
                new BaseBean().writeLog("项目ID获取错误");
                return "-1";
            }

        } else {
            new BaseBean().writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;

    }
}
