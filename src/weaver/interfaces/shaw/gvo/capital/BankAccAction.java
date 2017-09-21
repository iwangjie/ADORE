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
 * 银行账号创建
 *
 * **/

public class BankAccAction implements Action {
    BaseBean log = new BaseBean();//定义写入日志的对象

    public String execute(RequestInfo info) {
        log.writeLog("进银行账户审批BankAccAction——————");
        String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
        String requestid = info.getRequestid();//获取requestid的值

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql = "";
        String tableName = "";
        String gsdm = "";//公司代码
        String yhzh = "";//银行账号
        String zhmc = "";//账户名称
        String lhh = "";//联行号
        String yhlb = "";//银行类别
        String zhxz = "";//账户性质
        String zhlb = "";//账号类别
        String zhsvsx = "";//账户收支属性
        String bb = "";//币别
        String sfsx = "";//账户是否上线
        String sqlx = "";//申请类型
        String sqrq = "";//申请日期
        String jtsy = "";//具体事由

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
                    log.writeLog("错误信息————————" + message);
                } else {
                    log.writeLog("操作成功————————");
                }
            } catch (Exception e) {
                log.writeLog("json拼接错误");
            }
        } else {
            log.writeLog("流程表信息获取失败!");
            return "-1";
        }
        return SUCCESS;
    }
}
