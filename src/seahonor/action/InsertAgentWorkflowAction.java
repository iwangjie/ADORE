package seahonor.action;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
//海之信代理审批流程action
public class InsertAgentWorkflowAction implements Action {
	
	BaseBean log = new BaseBean();//定义写入日志的对象
	public String execute(RequestInfo info) {
		log.writeLog("进入代理设置InsertAgentWorkflowAction——————");
		
		String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
		String requestid = info.getRequestid();//获取requestid的值
		
		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		//test the Intellij IDEA is useful or not
		String sql = "";
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		String authorizer = "";//授权人
		String createTime = "";//创建时间
		String agantFirst = "";//一级代理人
		String agantSecond = "";//二级代理人
		String remarkStart = "";//备注
		String agentWorkflow = "";//代理流程
		sql = " select createdate,createtime from workflow_requestbase where requestid= "+requestid;
			rs.executeSql(sql);
			log.writeLog("创建时间 =---------" + sql);
			if (rs.next()) {
				String tmpDate = Util.null2String(rs.getString("createdate"));
				String tmpTime = Util.null2String(rs.getString("createtime"));
				createTime = tmpDate+" "+tmpTime;
		}
		
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
				authorizer = Util.null2String(rs.getString("authorizer"));
			}
			
			//查询明细表
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
				agantFirst = Util.null2String(rs.getString("agantFirst"));//获取明细表中数据
				agantSecond = Util.null2String(rs.getString("agantSecond"));
				remarkStart = Util.null2String(rs.getString("remark"));
				String agentWorkflowNew = Util.null2String(rs.getString("agentWorkflowNew"));

				sql="select workflowName from uf_workflow_table where id="+agentWorkflowNew;
				res.execute(sql);
				if(res.next()){
					agentWorkflow=Util.null2String(res.getString("workflowName"));
				}

				sql = " select count(id) as num_cc from uf_start_Authorize where authorizer = "+authorizer+"  and agentWorkflow="+agentWorkflow+" ";
				res.execute(sql);
				if(res.next()){
					int num_cc = res.getInt("num_cc");

                    if(num_cc>0){
                        sql=" update uf_start_Authorize set isEffective=1 where authorizer = "+authorizer+"  and agentWorkflow="+agentWorkflow+" ";
                        res.execute(sql);
                    }

						Map<String, String> mapStr = new HashMap<String, String>();
						mapStr.put("requestId", requestid);
						mapStr.put("authorizer", authorizer);
						mapStr.put("agentFirst", agantFirst);
						mapStr.put("agentSecond", agantSecond);
						mapStr.put("createTime", createTime);
						mapStr.put("status","0" );
						mapStr.put("remarkStart", remarkStart);
						mapStr.put("agentWorkflow",agentWorkflow);
                        mapStr.put("isEffective","0" );

						tableName = "uf_start_Authorize";
						InsertUtil IU = new InsertUtil();
						IU.insert(mapStr,tableName);
				}
			}
		}else{
			return "-1";
		}
		return SUCCESS;
	}

}

