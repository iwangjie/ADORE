package budget.action;

import java.sql.Time;
import java.util.Hashtable;

import weaver.conn.RecordSet;
import weaver.file.Prop;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;

public class BudgetCommitWorkflowAction implements Action {
	private StaticObj staticobj = null;
	public String execute(RequestInfo info) {
		new BaseBean().writeLog("_-----------------����commit-------------------");
		staticobj = StaticObj.getInstance();
		String errorMsg = "";

		RecordSet rs = new RecordSet();
		RecordSet rsc = new RecordSet();
		RecordSet rec = new RecordSet();
		String sql = "";
		String tableName = "";
		
		String str_fkfs = "";
		
		String str_dep = "szdw";//��λID
		
		String str_money = "sqje";//�������ֶ�����
		if("2186".equals(info.getWorkflowid())||"2189".equals(info.getWorkflowid())||"2190".equals(info.getWorkflowid())||"2193".equals(info.getWorkflowid())||"2245".equals(info.getWorkflowid())||"2247".equals(info.getWorkflowid())||"2248".equals(info.getWorkflowid())||"2252".equals(info.getWorkflowid())){
			//����ǻ㸶�������̣�fkfs���ֶ�������fkfs
			str_fkfs = "fkfs";
			//����ǻ㸶�������̣����ڵ�λ���ֶ�������fkfs
			str_dep = "sqdw";
		}else if("2185".equals(info.getWorkflowid())||"2187".equals(info.getWorkflowid())){
			//����Ǳ��������̣���fkfs���ֶ�������zffs
			str_fkfs = "zffs";
			//����Ǳ��������̣����������ֶε�������bxzje
			str_money = "bxzje";
		}else{
			//����Ǳ��������̣���fkfs���ֶ�������zffs
			str_fkfs = "zffs";
			//����Ǳ��������̣����������ֶε�������bxzje
			str_money = "bxzje";
		}
		
		sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id="+info.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		String sqrq = "";//������������
		String fkfs = "";//���ʽ
		String sqje = "";//����������
		String sqdw = "";//���뵥λ
		
		String xjfs = "";//�ֽ�ʽ
		if(!"".equals(tableName)){
			sql = "select * from "+tableName+" where requestid = '"+info.getRequestid()+"'";
			new BaseBean().writeLog("���sql"+sql);
			rs.executeSql(sql);
			if(rs.next()){
				 sqrq = Util.null2String(rs.getString("sqrq"));	//��������
				 if("2184".equals(info.getWorkflowid())){
					 //����ǳжһ�Ʊ���̣����ʽΪ�жһ�Ʊ
					 fkfs = "2";
				 }else{
					 fkfs = Util.null2String(rs.getString(str_fkfs));	//���ʽ
				 }
				 sqje = Util.null2String(rs.getString(str_money));	//������
				 sqdw = Util.null2String(rs.getString(str_dep));//���뵥λ
				 xjfs = Util.null2String(rs.getString("xjfs"));//�ֽ�ʽ
			}
			sqrq = sqrq.substring(0,4);
			
			//����ֽ�ʽ�����ֽ�����򶳽���
			if(!"1".equals(xjfs)){
				//�鿴ʣ�����Ƿ���
				String balaceMoney = "";//ʣ����
				String sql_balace = "select * from budgetDetal where conid = '"+sqdw+"' and year = '"+sqrq+"' and type = '"+fkfs+"'";
				new BaseBean().writeLog("��ѯ"+sql_balace);
				rec.executeSql(sql_balace);
				if(rec.next()){
					balaceMoney = rec.getString("availableBudget");
					if(Util.getDoubleValue(balaceMoney)<Util.getDoubleValue(sqje)){
						errorMsg = "ʣ�����㣡";
						staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
						return "-1";
					}
				}
				//�������ݣ����᱾����������ٱ���������
				String sql_budget = "update budgetDetal set availableBudget = availableBudget-"+Util.getDoubleValue(sqje)+",freeZeBudget=freeZeBudget+"+Util.getDoubleValue(sqje)+" where conid = '"+sqdw+"' and year = '"+sqrq+"' and type = '"+fkfs+"'";
				new BaseBean().writeLog("����"+sql_budget);
				if(!rsc.executeSql(sql_budget)){
					errorMsg = "�����������ϵ����Ա";
					new BaseBean().writeLog("����:"+sql_budget);
					staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
					return "-1";
				}
//			}
//			else{
				//�����ֶ�״̬Ϊ����
				sql = "update "+tableName+" set sfdj = '1' where requestid = '"+info.getRequestid()+"'";
				rs.executeSql(sql);
			}
			
		}else{
			errorMsg = "�����̱����Ʋ�����!";
			staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
			return "-1";
		}
			return SUCCESS;
	}
	
}
