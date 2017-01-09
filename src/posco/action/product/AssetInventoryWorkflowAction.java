package posco.action.product;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/4.
 * 新增固定资产盘点
 */
public class AssetInventoryWorkflowAction implements Action {

    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {

        log.writeLog("新增盘点AssetInventoryWorkflowAction——————");

        String requestid = info.getRequestid();//获取表单建模id的值

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
        //插入明细表
        sql = " insert into formtable_main_90_dt1(mainid,assid,pdr,plant,pdType) "
                +" select "+requestid+",id,"+empid+","+plant1+","+type+" from formtable_main_88 where formmodeid = 30 and status = 0 ";
        rs.execute(sql);
        log.writeLog("插入明细表sql——————" + sql);

        return SUCCESS;
    }
}
