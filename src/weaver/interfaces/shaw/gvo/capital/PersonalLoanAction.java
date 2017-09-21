package weaver.interfaces.shaw.gvo.capital;

import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.shaw.gvo.bank.BankAccServiceUtil;
import weaver.interfaces.shaw.gvo.util.GetMessageUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 17.9.20
 * ���˽������
 **/

public class PersonalLoanAction implements Action {
    BaseBean log = new BaseBean();

    public String execute(RequestInfo info) {
        log.writeLog("������˽���������� PersonalLoanAction������������");
        String workflowID = info.getWorkflowid();
        String requestid = info.getRequestid();

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String tableNamedt = "";/**��ϸ��**/
        String mainID = "";/**����id,������ϸ��**/
        String SERIAL_NO_ERP = "";/**�����**/
        String REQ_DATE = "";/**��������**/
        String CORP_CODE = "";/**��˾����**/
        String PAYER_ACC_NO = "";/**���������˺�**/
        String CUR = "";/**����ұ�**/
        String ITEM_CODE = "";/**�ʽ�ƻ���Ŀ����**/
        String ZZBS = "";/**�������**/
        String RMK = "";/**�����**/
        String ABS = "";/**�����������**/
        String VOUCHER_TYPE = "";/**���ʽ**/
        String WISH_PAY_DAY = "";/**��������**/
        String ZZKM = "";/**���ݿ�ֵ**/
        String JZDM = "29";/**�̶�ֵ29**/
        String SYSTEM_TYPE = "0";/**�̶�ֵ0**/

        sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            tableNamedt = tableName + "_dt2";

            /**��ѯ����**/

            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                mainID = Util.null2String(rs.getString("ID"));
                SERIAL_NO_ERP = Util.null2String(rs.getString("ID"));
                REQ_DATE = Util.null2String(rs.getString("ID"));
                CORP_CODE = Util.null2String(rs.getString("ID"));
                PAYER_ACC_NO = Util.null2String(rs.getString("ID"));
                CUR = Util.null2String(rs.getString("ID"));
                ITEM_CODE = Util.null2String(rs.getString("ID"));
                ZZBS = Util.null2String(rs.getString("ID"));
                RMK = Util.null2String(rs.getString("ID"));
                ABS = Util.null2String(rs.getString("ID"));
                VOUCHER_TYPE = Util.null2String(rs.getString("ID"));
                WISH_PAY_DAY = Util.null2String(rs.getString("ID"));
            }

            String sqlpara = "";

            try {
                JSONObject head = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                /**��ѯ��ϸ��**/

                sql = "select * from " + tableNamedt + " where mainid=" + mainID;
                rs.execute(sql);
                while (rs.next()) {
                    String AMT = Util.null2String(rs.getString("holidayApp"));/**֧�����**/
                    String GYSDM = Util.null2String(rs.getString("holidayApp"));/**֧����ϢԱ�����**/
                    String PAYEE_NAME = Util.null2String(rs.getString("holidayApp"));/**Ա������**/
                    String PAYEE_BANK = Util.null2String(rs.getString("holidayApp"));/**������**/
                    String PAYEE_ACC_NO = Util.null2String(rs.getString("holidayApp"));/**�����˺�**/
                    String PAYEE_CODE = Util.null2String(rs.getString("holidayApp"));/**���к�**/
                    String FKYYDM = Util.null2String(rs.getString("ID"));/**�ֽ���������**/
                    String PURPOSE = Util.null2String(rs.getString("ID"));/**���и�����;**/
                    String ISFORINDIVIDUAL = Util.null2String(rs.getString("ID"));/**�Թ���˽��־**/
                    String URGENCY_FLAG = Util.null2String(rs.getString("ID"));/**�Ӽ���־**/

                    JSONObject jsonObjSon = new JSONObject();

                    jsonObjSon.put("SERIAL_NO_ERP", SERIAL_NO_ERP);
                    jsonObjSon.put("REQ_DATE", REQ_DATE);
                    jsonObjSon.put("CORP_CODE", CORP_CODE);
                    jsonObjSon.put("PAYER_ACC_NO", PAYER_ACC_NO);
                    jsonObjSon.put("CUR", CUR);
                    jsonObjSon.put("ITEM_CODE", ITEM_CODE);
                    jsonObjSon.put("ZZBS", ZZBS);
                    jsonObjSon.put("RMK", RMK);
                    jsonObjSon.put("ABS", ABS);
                    jsonObjSon.put("VOUCHER_TYPE", VOUCHER_TYPE);
                    jsonObjSon.put("WISH_PAY_DAY", WISH_PAY_DAY);
                    jsonObjSon.put("ZZKM", ZZKM);
                    jsonObjSon.put("JZDM", JZDM);
                    jsonObjSon.put("SYSTEM_TYPE", SYSTEM_TYPE);

                    jsonObjSon.put("FKYYDM", FKYYDM);
                    jsonObjSon.put("PURPOSE", PURPOSE);
                    jsonObjSon.put("AMT", AMT);
                    jsonObjSon.put("GYSDM", GYSDM);
                    jsonObjSon.put("PAYEE_NAME", PAYEE_NAME);
                    jsonObjSon.put("PAYEE_BANK", PAYEE_BANK);
                    jsonObjSon.put("PAYEE_ACC_NO", PAYEE_ACC_NO);
                    jsonObjSon.put("PAYEE_CODE", PAYEE_CODE);
                    jsonObjSon.put("ISFORINDIVIDUAL", ISFORINDIVIDUAL);
                    jsonObjSon.put("URGENCY_FLAG", URGENCY_FLAG);


                    jsonArray.put(jsonObjSon);
                }
                head.put("bean", jsonArray);

                BankAccServiceUtil basu = new BankAccServiceUtil();

                String result = "";
                result = basu.bankAccServiceMethod(head.toString().toLowerCase());

                GetMessageUtil gmu = new GetMessageUtil();
                String status = gmu.getStatus(result);
                String message = gmu.getMessage(result);

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
