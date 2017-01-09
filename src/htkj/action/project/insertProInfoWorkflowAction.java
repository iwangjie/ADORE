package htkj.action.project;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/6.
 */
public class insertProInfoWorkflowAction implements Action{
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
                String KHMC = Util.null2String(rs.getString("KHMC"));
                String KHLXR = Util.null2String(rs.getString("KHLXR"));
                String KHDM = Util.null2String(rs.getString("KHDM"));
                String LXRDH = Util.null2String(rs.getString("LXRDH"));
                String LXRYX = Util.null2String(rs.getString("LXRYX"));
                String xmgcs = Util.null2String(rs.getString("xmgcs"));
                String xmzz = Util.null2String(rs.getString("xmzz"));
                String scbry = Util.null2String(rs.getString("scbry"));
                String CPLX = Util.null2String(rs.getString("CPLX"));

                String sql_up = " update cus_fielddata set xmzz='"+xmzz+"',xsy='"+scbry+"',proer1='"+xmgcs+"',khmc='"+KHMC+"',khdm='"+KHDM+"'," +
                        "khlxr='"+KHLXR+"',khlxrdh='"+LXRDH+"',khlxryx='"+LXRYX+"',cplx=(select name from mode_selectitempagedetail where mainid=21 and id="+CPLX+")"
                        +"where scope='ProjCustomFieldReal' and id="+project_name+" ";

                res.executeSql(sql_up);
                //new BaseBean().writeLog("sql_up---------" + sql_up);
                if (!res.executeSql(sql_up)) {
                    new BaseBean().writeLog("项目信息更新失败");
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
