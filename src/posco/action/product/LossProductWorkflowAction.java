package posco.action.product;

import product.PoscoLossProduct;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.ArrayList;

/**
 * Created by adore on 15/11/3.
 * 盘亏流程
 */
public class LossProductWorkflowAction implements Action{
    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {

        log.writeLog("进入盘亏流程LossProductWorkflowAction————————");

        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        EmpGetInfo egi = new EmpGetInfo();//获取员工信息

        String sql = "";
        String tableName = "";
        String tableNamedt = "";
        String mainID = "";

        String txr = "";//操作者id
        String operator = "";//工号
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

            sql = "select * from " + tableName + " where requestid=" + requestid;
            rs.execute(sql);
            if (rs.next()) {
                txr = Util.null2String(rs.getString("txr"));
                operator =egi.getWorkcode(txr);

                txrq = Util.null2String(rs.getString("txrq"));
                bz = Util.null2String(rs.getString("ly"));
                mainID = Util.null2String(rs.getString("ID"));//获取主表中的id，作为明细表中的mainid
            }

            sql = "select * from " + tableNamedt + " where mainid=" + mainID;
            rs.execute(sql);
            while (rs.next()) {
                Double dj = rs.getDouble("dj");//获取明细表中数据
                Double pksl = rs.getDouble("pksl");
                String kw = Util.null2String(rs.getString("kw"));
                String mrodm = Util.null2String(rs.getString("mrodm"));

                cbjlist.add(dj);
                sllist.add(pksl);
                slnamelist.add(kw);
                spidlist.add(mrodm);
            }

            PoscoLossProduct lossProduct = new PoscoLossProduct();
            String str = lossProduct.lossProduct(txrq,operator,bz,cbjlist,sllist,slnamelist,spidlist);
            log.writeLog("接口调用成功！返回结果 = " + str);
            if(!"0".equals(str)){
                return "-1";
            }
        } else {
            log.writeLog("流程信息表获取失败");
            return "-1";
        }
        return SUCCESS;
    }
}
