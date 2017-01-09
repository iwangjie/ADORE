package gvo.work;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class CheckQingJiaWorkflowAction implements Action {
    GetWebValue getWebValue = new GetWebValue();

    public String execute(RequestInfo info) {
        new BaseBean().writeLog("������ټ������action_____________________________");

        RecordSet rs = new RecordSet();
        String sql = "";
        String tableName = "";
        String qjlx = "";//�������
        String qjygbh = "";//���Ա�����
        String qjxss = "";//���Сʱ��

        String jhqjksrq = "";//�ƻ���ٿ�ʼ����
        String jhqjjsrq = "";//�ƻ���ٽ�������
        String jhqjkssj = "";//�ƻ���ٿ�ʼʱ��
        String jhqjjssj = "";//�ƻ���ٽ���ʱ��
        String jhqjxss = "";//�ƻ����Сʱ��

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
                qjlx = rs.getString("jb");//�������
                qjygbh = rs.getString("ygh");//�����Ա����
                jhqjksrq = rs.getString("yjqjksrq");//�ƻ���ٿ�ʼ����
                jhqjkssj = rs.getString("yjqjkssj");//�ƻ���ٿ�ʼʱ��
                jhqjjsrq = rs.getString("yjqjjsrq");//�ƻ���ٽ�������
                jhqjjssj = rs.getString("yjqjjssj");//�ƻ���ٽ���ʱ��
                jhqjxss = Util.null2String(rs.getString("yjgj"));//�ƻ����Сʱ��
                sjqjksrq = Util.null2String(rs.getString("sjqjksrq"));//ʵ����ٿ�ʼ����
                sjqjkssj = Util.null2String(rs.getString("sjqjkssj"));//ʵ����ٿ�ʼʱ��
                sjqjjsrq = Util.null2String(rs.getString("sjqkksrq"));//ʵ����ٽ�������
                sjqjjssj = Util.null2String(rs.getString("sjqjjssj"));//ʵ����ٽ���ʱ��
                sjqjxsh = Util.null2String(rs.getString("jsgjxss"));//�ƻ����Сʱ��
            }

        } else {
            new BaseBean().writeLog("����ȡ����Ϣ����");
            return "-1";
        }
        qjxss = jhqjxss;

        if ("".equals(qjxss) || Double.valueOf(qjxss) == 0) {
            double temp1 = getWebValue.getValueAction(qjygbh, jhqjksrq, jhqjjsrq, jhqjkssj, jhqjjssj, qjlx);
            rs.execute("update " + tableName + " set yjgj = " + temp1 + " where requestid = " + info.getRequestid());
        }

        if (!"".equals(sjqjjsrq) && (sjqjxsh.equals("") || Double.valueOf(sjqjxsh) == 0)) {
            double temp1 = getWebValue.getValueAction(qjygbh, sjqjksrq, sjqjjsrq, sjqjkssj, sjqjjssj, qjlx);
            rs.execute("update " + tableName + " set sjqjxsh = " + temp1 + " where requestid = " + info.getRequestid());
        }


        return SUCCESS;
    }
}
