package weaver.interfaces.shaw.gvo.capital;

import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.shaw.gvo.util.GetMessageUtil;
import weaver.interfaces.shaw.gvo.bank.BankAccServiceUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * Created by adore on 17.9.19
 * �����˺Ŵ���
 *
 * **/

public class BankAccAction implements Action {
    BaseBean log = new BaseBean();//����д����־�Ķ���

    public String execute(RequestInfo info) {
        log.writeLog("�������˻�����BankAccAction������������");
        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String gsdm = "";//��˾����
        String yhzh = "";//�����˺�
        String zhmc = "";//�˻�����
        String lhh = "";//���к�
        String yhlb = "";//�������
        String zhxz = "";//�˻�����
        String zhlb = "";//�˺����
        String zhsvsx = "";//�˻���֧����
        String bb = "";//�ұ�
        String sfsx = "";//�˻��Ƿ�����
        String sqlx = "";//��������
        String sqrq = "";//��������
        String jtsy = "";//��������

        sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                gsdm = Util.null2String(rs.getString("gsdm"));
                yhzh = Util.null2String(rs.getString("yhzh"));
                zhmc = Util.null2String(rs.getString("zhmc"));
                lhh = Util.null2String(rs.getString("lhh"));
                yhlb = Util.null2String(rs.getString("yhlb"));
                zhxz = Util.null2String(rs.getString("zhxz"));
                zhlb = Util.null2String(rs.getString("zhlb"));
                zhsvsx = Util.null2String(rs.getString("zhsvsx"));
                bb = Util.null2String(rs.getString("bb"));
                sfsx = Util.null2String(rs.getString("sfsx"));
                sqlx = Util.null2String(rs.getString("sqlx"));
                sqrq = Util.null2String(rs.getString("sqrq"));
                jtsy = Util.null2String(rs.getString("jtsy"));
            }

            String sqlpara = "";

            try {
                JSONObject head = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObjSon = new JSONObject();
                jsonObjSon.put("CORP_CODE", gsdm);
                jsonObjSon.put("BANKACC", yhzh);
                jsonObjSon.put("ACC_NAME", zhmc);
                jsonObjSon.put("BANK_NAME", lhh);
                jsonObjSon.put("BANK_TYPE", yhlb);
                jsonObjSon.put("ACC_CATEGORY", zhxz);
                jsonObjSon.put("ACC_TYPE1", zhlb);
                jsonObjSon.put("ACC_TYPE", zhsvsx);
                jsonObjSon.put("CUR", bb);
                jsonObjSon.put("IS_ONLINE", sfsx);
                jsonObjSon.put("ACC_STATE", sqlx);
                jsonObjSon.put("REGISTER_DATE", sqrq);
                jsonObjSon.put("OP_RESSION", jtsy);

                jsonArray.put(jsonObjSon);
                head.put("bean", jsonArray);

                BankAccServiceUtil basu = new BankAccServiceUtil();

                String result = "";
                result = basu.bankAccServiceMethod(head.toString().toLowerCase());

                GetMessageUtil gmu = new GetMessageUtil();
                String status = gmu.getStatus(result);
                String message = "";
                if (status.equalsIgnoreCase("F")) {
                    message = gmu.getMessage(result);
                    log.writeLog("������Ϣ����������������" + message);
                } else {
                    log.writeLog("�����ɹ�����������������");
                }
            } catch (Exception e) {
                log.writeLog("jsonƴ�Ӵ���");
            }
        } else {
            log.writeLog("���̱���Ϣ��ȡʧ��!");
            return "-1";
        }
        return SUCCESS;
    }
}
