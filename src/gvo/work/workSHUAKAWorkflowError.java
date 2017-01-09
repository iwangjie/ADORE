package gvo.work;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
//import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;

public class workSHUAKAWorkflowError implements Action {

    public String execute(RequestInfo info) {

        new BaseBean().writeLog("����ˢ���쳣action_____________________________");

        weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
        rs.executeSql("select *... where aactive=0 ");
        while (rs.next()) {
            String tmp1 = Util.null2String(rs.getString("titleName"));
            String tmp2 = Util.null2String(rs.getString("titleName"));

        }
        RecordSetDataSource rsd = new RecordSetDataSource("local_HR");
        String sql = "";
        String tableName = "";
        String d_ct = "";
        String d_cb = "";
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
                d_cb = rs.getString("sqrgh");
                d_ct = rs.getString("sqrq");
                d_ct = d_ct.replace("-", "/");
            }
        } else {
            new BaseBean().writeLog("����ȡ����Ϣ����");
            return "-1";
        }
        DetailTable[] detailtable = info.getDetailTableInfo().getDetailTable();

        String d_keyid = "";//ԭ�򿨼�¼ID
        String d_ygbm = "";//Ա������
        String d_ybsw = "";//ԭ��ʾλ
        String d_bianji = "";//�༭
        String d_xbsw = "";//�ֱ�ʾλ
        String d_remark = "";//����
        String d_sksj = "";//ˢ��ʱ��
        if (detailtable.length > 0) {
            for (int i = 0; i < detailtable.length; i++) {
                // ָ����ϸ��
                DetailTable dt = detailtable[i];
                // ȡ����ǰ��ϸ���е����м�¼�����д�ȡ
                Row[] row = dt.getRow();
                //	int cc = 0;
                for (int j = 0; j < row.length; j++) {
                    //		cc++;
                    // ȡ����ǰ�����е�һ�м�¼
                    Row r = row[j];
                    // ȡ����ǰ����������ÿ�е�����
                    Cell[] cell = r.getCell();
                    for (int k = 0; k < cell.length; k++) {
                        Cell c = cell[k];
                        // ��ȡ��ϸ���ֶ���
                        String name = c.getName();

                        if (name.equalsIgnoreCase("keyid")) {// ԭ�򿨼�¼ID
                            d_keyid = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("gh")) {// Ա������
                            d_ygbm = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("dcbsw")) {// ԭ��ʾλ
                            d_ybsw = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("clfs")) {// �༭
                            d_bianji = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("xghbsw")) {// �ֱ�ʾλ
                            d_xbsw = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("ycyy")) {// ����
                            d_remark = c.getValue().trim();
                        }
                        if (name.equalsIgnoreCase("sksj")) {// ˢ��ʱ��
                            d_sksj = c.getValue().trim();
                        }

                    }

                    new BaseBean().writeLog("ԭ�򿨼�¼ID" + d_keyid + "Ա������" + d_ygbm + "ԭ��ʾλ" + d_ybsw + "�༭" + d_bianji + "�ֱ�ʾλ" + d_xbsw + "����" + d_remark + "ˢ��ʱ��" + d_sksj);
                    new BaseBean().writeLog("�����˹���" + d_cb + "��������" + d_ct + "����ID" + info.getRequestid());
                    if (!"".equals(d_keyid) && !"".equals(d_ygbm) && !"".equals(d_ybsw) && !"".equals(d_bianji) && !"".equals(d_xbsw) && !"".equals(d_remark) && !"".equals(d_cb) && !"".equals(d_ct) && !"".equals(d_sksj)) {
                        sql = "insert into cus_ats_originality_data_change_info(key_id,process_id,ats_originality_data_id,emp_code,att_datetime,inorout_change_befor,change_type_code,inorout_change_after,remark,create_dt,create_by)"
                                + " values "
                                + "("
                                + "newID()"
                                + ",'"
                                + info.getRequestid()
                                + "','"
                                + d_keyid
                                + "','"
                                + d_ygbm
                                + "','"
                                + d_sksj
                                + "','"
                                + d_ybsw
                                + "','"
                                + d_bianji
                                + "','"
                                + d_xbsw
                                + "','"
                                + d_remark
                                + "','"
                                + d_ct
                                + "','"
                                + d_cb + "')";


                        new BaseBean().writeLog("sql-----------------����ˢ���쳣��------------------------" + sql);
                        if (!rsd.executeSql(sql)) {
                            new BaseBean().writeLog("sql-----------------����ˢ���м��ʧ��-----------------------" + sql);
                            return "-1";
                        } else {
                            new BaseBean().writeLog("sql-----------------����ˢ���쳣�м��ɹ�------------" + sql);
                        }
                    } else {
                        new BaseBean().writeLog("д����Ϣȱʧ");
                    }

                }

            }
        }
        return SUCCESS;
    }
}
