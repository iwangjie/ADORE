package posco.action.product;

import product.PoscoOverageProduct;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by adore on 15/11/3.
 * ��ӯ����
 */
public class OverageProductWorkflowAction implements Action{
    BaseBean log = new BaseBean();//����д����־�Ķ���

    public String execute(RequestInfo info) {

        log.writeLog("������ӯ����OverageProductWorkflowAction������������");

        String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
        String requestid = info.getRequestid();//��ȡrequestid��ֵ

        RecordSet rs = new RecordSet();
        EmpGetInfo egi = new EmpGetInfo();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";

        String txr = "";//������id
        String operator = "";//������
        String txrq = "";//��������
        String bz = "";//��ע

        List<Double> cbjlist = new ArrayList<Double>();
        ArrayList<Double> sllist = new ArrayList<Double>();
        ArrayList<String> slnamelist = new ArrayList<String>();
        ArrayList<String> spidlist = new ArrayList<String>();
        sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID + ")";

        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {
            tableNamedt = tableName + "_dt1";
            //tableNamedt = tableName + "_dt2";

            // ��ѯ����
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                txr = Util.null2String(rs.getString("txr"));//������
                operator = egi.getWorkcode(txr);
                txrq = Util.null2String(rs.getString("txrq"));//��������
                bz = Util.null2String(rs.getString("ly"));//��ע
                mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
            }

            //��ѯ��ϸ��
            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            rs.execute(sql);
            log.writeLog("��ѯ��ϸsql������������"+sql);
            while(rs.next()) {
                Double dj = rs.getDouble("dj");//��ȡ��ϸ��������
                Double pysl = rs.getDouble("pysl");
                String kw = Util.null2String(rs.getString("kw"));
                String mrodm = Util.null2String(rs.getString("mrodm"));

                cbjlist.add(dj);
                sllist.add(pysl);
                slnamelist.add(kw);
                spidlist.add(mrodm);
                log.writeLog("cbjlist="+cbjlist.toString()+"|slnamelist="+slnamelist.toString()+"|sllist="+sllist.toString()+"|spidlist="+spidlist.toString());
            }

            PoscoOverageProduct overageProduct = new PoscoOverageProduct();
            String str = overageProduct.overageProduct(txrq,operator,bz,cbjlist,sllist,slnamelist,spidlist);
            log.writeLog("�ӿڵ��óɹ������ؽ�� = " + str);
            if(!"0".equals(str)){
                return "-1";
            }
        } else {
            log.writeLog("������Ϣ���ȡʧ�ܣ�");
            return "-1";
        }
        return SUCCESS;
    }
}
