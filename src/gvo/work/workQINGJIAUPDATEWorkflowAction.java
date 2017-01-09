package gvo.work;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class workQINGJIAUPDATEWorkflowAction implements Action {
	private StaticObj staticobj = null;

	public String execute(RequestInfo info) {
		staticobj = StaticObj.getInstance();
		String errorMsg = "";
		new BaseBean().writeLog("������ٲ�������action_____________________________");

		RecordSet rs = new RecordSet();
//		RecordSet res = new RecordSet();
		RecordSetDataSource rsd = new RecordSetDataSource("local_HR");
		String sql = "";
		String tableName = "";
		String  txr = "";//��д��
		String sqrygbm = "";// ����Ա������
		String sqrq = "";// ��������
		String qjsy = "";// �������
		String qjlx = "";//�������
		String qjygbh = "";//���Ա�����
	
		String sjqjksrq = "";//ʵ����ٿ�ʼ����
		String sjqjjsrq = "";//ʵ����ٽ�������
		String sjqjkssj = "";//ʵ����ٿ�ʼʱ��
		String sjqjjssj = "";//ʵ����ٽ���ʱ��
		String sjqjxsh = "";//ʵ�����Сʱ��

		sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id="
				+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql---------" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!"".equals(tableName)) {
			String sql_1 = "select * from " + tableName + " where requestid = "
					+ info.getRequestid();
			new BaseBean().writeLog("sql1---------" + sql_1);
			rs.executeSql(sql_1);
			if (rs.next()) {
				txr = rs.getString("txr");//��д��
				sqrq = rs.getString("txrq");//��д����
				qjsy = rs.getString("qjsy");//�������
				qjlx = rs.getString("jb");//�������
				qjygbh = rs.getString("ygh");//�����Ա����
				
				sjqjksrq = rs.getString("sjqjksrq");//ʵ����ٿ�ʼ����
				sjqjkssj = rs.getString("sjqjkssj");//ʵ����ٿ�ʼʱ��
				sjqjjsrq = rs.getString("sjjsrq");//ʵ����ٽ�������
				sjqjjssj = rs.getString("sjqjjssj");//ʵ����ٽ���ʱ��
				sjqjxsh = rs.getString("jsgjxss");//�ƻ����Сʱ��
				
				qjsy = qjsy.replace("<br>", " ").replace("&nbsp;", " ");
				
				if(qjsy.length() > 250) qjsy = qjsy.substring(0,250);
			}
			sqrq = sqrq.replace("-", "/");
			sql = "select * from hrmresource where id = '"+txr+"'";
			rs.executeSql(sql);
			if(rs.next()){
				sqrygbm = rs.getString("workcode");
			}
			
		} else {
			new BaseBean().writeLog("����ȡ����Ϣ����");
			return "-1";
		}
		
			sjqjksrq = sjqjksrq.replace("-", "/");
			sjqjjsrq = sjqjjsrq.replace("-", "/");
			
			sql = "insert into cus_ats_leave(key_id,process_id,employee_code,leave_begin_date,leave_end_date,leave_begin_time,leave_end_time,leave_value,leave_type_code,create_by,create_dt,remark) values ("+
			          "newID(),'"+info.getRequestid()+"','"+qjygbh+"','"+sjqjksrq+"','"+sjqjjsrq+"','"+sjqjkssj+"','"+sjqjjssj+"','"+sjqjxsh+"','"+qjlx+"','"+sqrygbm+"','"+sqrq+"','"+qjsy+"'"+
					  ")";
			new BaseBean().writeLog("����sqlʵ�ʿ�ʼ���ڼƻ���ʼ----------------"+sql);
			if (!rsd.executeSql(sql)) {
				errorMsg = "��������м��ʧ��";
				new BaseBean().writeLog("sql-----------------��������м��ʧ��------------"+ sql);
				staticobj.putRecordToObj("budget_upd", "ErrorMsg","OA ErrorMsg:[" + errorMsg + "]");
				return "-1";
			}else{
				new BaseBean().writeLog("sql-----------------��������м��ɹ�------------"+ sql);
			}
			return SUCCESS;
	}
}
