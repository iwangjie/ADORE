package budget.action;

import java.sql.Time;
import java.util.Hashtable;

import weaver.conn.RecordSet;
import weaver.file.Prop;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;

public class BudgetFinalWorkflowAction implements Action {
	private StaticObj staticobj = null;
	public String execute(RequestInfo info) {
		new BaseBean().writeLog("_-----------------����Final-------------------");
		staticobj = StaticObj.getInstance();
		String errorMsg = "";

		RecordSet rs = new RecordSet();
		RecordSet rsc = new RecordSet();
		RecordSet rec = new RecordSet();
		User user = new User();
		String sql = "";
		String tableName = "";
		
		String str_fkfs = "";//���ʽ�ֶ�����
		String str_lastMoney = "";//���ۼ�����ֶ�����
		
		String str_dep = "szdw";//��λID
		String str_money = "sqje";//�������ֶ�����
		if("2186".equals(info.getWorkflowid())||"2189".equals(info.getWorkflowid())||"2190".equals(info.getWorkflowid())||"2193".equals(info.getWorkflowid())||"2245".equals(info.getWorkflowid())||"2247".equals(info.getWorkflowid())||"2248".equals(info.getWorkflowid())||"2252".equals(info.getWorkflowid())){
			//����ǻ㸶�������̣�fkfs���ֶ�������fkfs
			str_fkfs = "fkfs";
			//����ǻ㸶�������̣����ۼ��Ľ���ֶ�����
			str_lastMoney = "pzje";
			//����ǻ㸶�������̣����ڵ�λ���ֶ�������fkfs
			str_dep = "sqdw";
		}else if("2185".equals(info.getWorkflowid())||"2187".equals(info.getWorkflowid())){
			//����Ǳ��������̣���fkfs���ֶ�������zffs
			str_fkfs = "zffs";
			str_lastMoney = "zfje";
			//����Ǳ��������̣����������ֶε�������bxzje
			str_money = "bxzje";
		}else if("2184".equals(info.getWorkflowid())){
			//����ǳжһ�Ʊ���̣����ۼ��Ľ���ֶ�����
			str_lastMoney = "hzje";
		}else{
			//����Ǳ��������̣���fkfs���ֶ�������zffs
			str_fkfs = "zffs";
			str_lastMoney = "zfje";
			//����Ǳ��������̣����������ֶε�������bxzje
			str_money = "bxzje";
		}
		
		sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id="+info.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		String nowData = TimeUtil.getCurrentDateString();
		String nowTime = TimeUtil.getCurrentTimeString();
		
		String sqrq = "";//������������
		String fkfs = "";//���ʽ
		String sqje = "";//����������
		String sqdw = "";//���뵥λ
		String lastMoney = "";//���̹鵵��ۼ��Ľ��
		String xjfs = "";//�ֽ�ʽ
		String sqr = "";//������
		if(!"".equals(tableName)){
			sql = "select * from "+tableName+" where requestid = '"+info.getRequestid()+"'";
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
				 sqdw = Util.null2String(rs.getString(str_dep));	//���뵥λ
				 lastMoney = Util.null2String(rs.getString(str_lastMoney));	//���̹鵵���ۼ������ֶ�����
				 xjfs = Util.null2String(rs.getString("xjfs"));	//�ֽ�ʽ
				 sqr = Util.null2String(rs.getString("sqr"));	//������
			}
			sqrq = sqrq.substring(0,4);
			double balance = Util.getDoubleValue(sqje)-Util.getDoubleValue(lastMoney);
			
			//������ʽΪ�ֽ𣬲����ֽ�֧��֧����ʽΪ�ֽ�����򲻽���Ԥ��ۼ�
			if("0".equals(fkfs) && "1".equals(xjfs)){
				new BaseBean().writeLog("���ۼ�����Ϊ֧����ʽΪ�ֽ𣬲����ֽ�ʽΪС��");
			}else{
				String sql_budget = "update budgetDetal set availableBudget = availableBudget+"+balance+",freeZeBudget=freeZeBudget-"+Util.getDoubleValue(sqje)+",UseBudget = UseBudget+"+Util.getDoubleValue(lastMoney)+" where conid = '"+sqdw+"' and year = '"+sqrq+"' and type = '"+fkfs+"'";
				new BaseBean().writeLog("����λ��1:"+sql_budget);
				if(!rsc.executeSql(sql_budget)){
					errorMsg = "���ۼ���������ϵ����Ա";
					new BaseBean().writeLog("����λ��2:"+sql_budget);
					staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
					return "-1";
				}else{
					//������־
					String sql_log = "insert into budgetLogTable(Year,conType,Type,Operating,OperatingDate,OperatingTime,OldBudget,NewBudget,OperatingType) values ('"+sqrq+"','"+sqdw+"','"+fkfs+"','"+sqr+"','"+nowData+"','"+nowTime+"','-1','"+lastMoney+"','1')";
					rec.execute(sql_log);
				}
			}
		}else{
			errorMsg = "�����̱����Ʋ�����!";
			staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
			return "-1";
		}
			return SUCCESS;
	}
	
}
