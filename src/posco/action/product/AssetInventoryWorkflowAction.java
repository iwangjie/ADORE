package posco.action.product;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/4.
 * �����̶��ʲ��̵�
 */
public class AssetInventoryWorkflowAction implements Action {

    BaseBean log = new BaseBean();//����д����־�Ķ���

    public String execute(RequestInfo info) {

        log.writeLog("�����̵�AssetInventoryWorkflowAction������������");

        String requestid = info.getRequestid();//��ȡ����ģid��ֵ

        RecordSet rs = new RecordSet();

        String sql = "";
        String empid = "";
        String plant1 = "";
        String type = "";
        sql = " select applicant,plant,type from formtable_main_90 where id= "+requestid;
        rs.execute(sql);
        if(rs.next()){
            empid = Util.null2String(rs.getString("applicant"));
            plant1 = Util.null2String(rs.getString("plant"));
            type = Util.null2String(rs.getString("type"));
        }
        //������ϸ��
        sql = " insert into formtable_main_90_dt1(mainid,assid,pdr,plant,pdType) "
                +" select "+requestid+",id,"+empid+","+plant1+","+type+" from formtable_main_88 where formmodeid = 30 and status = 0 ";
        rs.execute(sql);
        log.writeLog("������ϸ��sql������������" + sql);

        return SUCCESS;
    }
}
