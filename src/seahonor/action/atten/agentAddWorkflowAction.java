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
 * Created by adore on 16/6/27.
 */
public class agentAddWorkflowAction implements Action{
    BaseBean log = new BaseBean();//定义写入日志的对象
    public String execute(RequestInfo info) {
        log.writeLog("进入设置代理人agentAddWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String tableNameAgent="";
        String mainID = "";
        String[] agentors = new String[100];//代理人组
        String creator = "";//授权人

        sql  = " Select tablename From Workflow_bill Where id in ("
                + " Select formid From workflow_base Where id= "
                + workflowID + ")";

        rs.execute(sql);
        if(rs.next()){
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if(!"".equals(tableName)){
            tableNamedt = tableName + "_dt1";
            //tableNamedt = tableName + "_dt2";

            // 查询主表
            sql = "select * from "+tableName + " where requestid="+requestid;
            rs.execute(sql);
            if(rs.next()){
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
                creator = Util.null2String(rs.getString("authorizer"));
                agentors = Util.null2String(rs.getString("agentors")).split(",");
            }
            for(int i=0;i<agentors.length;i++){
                int num_exist=0;
                String tmp_agentor=agentors[i];//代理人
                //查询明细表
                sql = "select count(id) as num_exist from " + tableNamedt + " where agentor="+tmp_agentor+" and authorizer="+creator+" ";
                rs.execute(sql);
                if(rs.next()){
                    num_exist=rs.getInt("num_exist");
                    if(num_exist>0){
                        continue;
                    }else{
                        Map<String, String> mapStr = new HashMap<String, String>();
                        mapStr.put("mainid", mainID);
                        mapStr.put("authorizer", creator);
                        mapStr.put("agentor", tmp_agentor);

                        tableNameAgent = tableNamedt;
                        InsertUtil IU = new InsertUtil();
                        IU.insert(mapStr,tableNameAgent);
                    }
                }
            }
        }else{
            log.writeLog("流程信息获取失败!");
            return "-1";
        }
        return SUCCESS;
    }

}
