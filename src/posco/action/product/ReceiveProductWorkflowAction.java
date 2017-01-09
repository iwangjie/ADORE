package posco.action.product;

import product.PoscoReceiveProduct;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.ArrayList;

/**
 * Created by adore on 15/11/3.
 * 领料流程
 */

public class ReceiveProductWorkflowAction implements Action {

    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {

        log.writeLog("进入领料申请ReceiveProductWorkflowAction——————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        EmpGetInfo egi = new EmpGetInfo();
        GetDeptName gdn = new GetDeptName();

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";

        String txr = "";//操作者
        String operator = "";//操作者工号
        String departmentname = "";//领用部门
        String txrbm = "";//领用部门id
        String txrq = "";//领用日期
        String bz = "";//备注

        ArrayList<Double> cbjlist = new ArrayList<Double>();
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

            // 查询主表
            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                txr = Util.null2String(rs.getString("txr"));//操作者
                operator = egi.getWorkcode(txr);
                txrbm = Util.null2String(rs.getString("txrbm"));//领用部门
                departmentname = gdn.getDept(txrbm);
                txrq = Util.null2String(rs.getString("txrq"));//领用日期
                bz = Util.null2String(rs.getString("ly"));//备注
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
                log.writeLog("txr="+txr+"|工号Workcode="+operator+"|departmentname="+departmentname+"|txrq="+txrq+"|bz="+bz);
            }

            //查询明细表
            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            rs.execute(sql);
            log.writeLog("查询明细sql——————"+sql);
            while (rs.next()) {
                Double dj = rs.getDouble("dj");//获取明细表中数据
                Double sl = rs.getDouble("sl");
                String kw = Util.null2String(rs.getString("kw"));
                String mrodm = Util.null2String(rs.getString("mrodm"));

                cbjlist.add(dj);
                slnamelist.add(kw);
                sllist.add(sl);
                spidlist.add(mrodm);
                log.writeLog("cbjlist="+cbjlist.toString()+"|slnamelist="+slnamelist.toString()+"|sllist="+sllist.toString()+"|spidlist="+spidlist.toString());
            }

            PoscoReceiveProduct receiveProduct = new PoscoReceiveProduct();
            String str = receiveProduct.receiveProduct(departmentname, 0.0, 0.0, txrq, operator, bz, cbjlist, sllist, slnamelist, spidlist);
            log.writeLog("接口调用成功！返回结果 = " + str);
            if(!"0".equals(str)){
               return "-1";
            }

        } else {
            log.writeLog("流程信息表获取失败！");
            return "-1";
        }
        return SUCCESS;
    }

}
