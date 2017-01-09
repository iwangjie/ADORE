package seahonor.action;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
//��֮�Ŵ�����������action
public class InsertAgentWorkflowAction implements Action {
	
	BaseBean log = new BaseBean();//����д����־�Ķ���
	public String execute(RequestInfo info) {
		log.writeLog("�����������InsertAgentWorkflowAction������������");
		
		String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();//��ȡrequestid��ֵ
		
		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		//test the Intellij IDEA is useful or not
		String sql = "";
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		String authorizer = "";//��Ȩ��
		String createTime = "";//����ʱ��
		String agantFirst = "";//һ��������
		String agantSecond = "";//����������
		String remarkStart = "";//��ע
		String agentWorkflow = "";//��������
		sql = " select createdate,createtime from workflow_requestbase where requestid= "+requestid;
			rs.executeSql(sql);
			log.writeLog("����ʱ�� =---------" + sql);
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
			
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
				authorizer = Util.null2String(rs.getString("authorizer"));
			}
			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
				agantFirst = Util.null2String(rs.getString("agantFirst"));//��ȡ��ϸ��������
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

