package TaiSon.Util;

import TaiSon.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2016/9/30.
 * 流程出错返回结果
 */
public class FinanceUsq {

    /*
     * 冻结预算： 按月份冻结,费用平均分摊;目前的月份比开始日期月份大，按目前时间月份计算
     * empID 申请人
     * startDate 开始日期
     * endDate 结束日期
     * type 业务类型
     * amount  总金额
     * requestid 申请流程ID
     */
    public int freeze(String empID, String startDate, String endDate, String type,
                      String amount, String requestid) {
        BaseBean log = new BaseBean();
        // 有值为不符合要求,方法失败
        if (Util.null2String(empID).length() < 1) {
            log.writeLog("人员输入值无效！");
            return 1;
        }
        if (Util.null2String(startDate).length() != 10) {
            log.writeLog("开始日期格式错误！");
            return 2;
        }
        if (Util.null2String(endDate).length() != 10) {
            log.writeLog("结束日期格式错误！");
            return 3;
        }
        if (Util.null2String(type).length() < 1) {
            log.writeLog("业务类型输入有误！");
            return 4;
        }
        // 设置金额需要检查
        if (Util.null2String(amount).length() >= 1) {
            amount = amount.replace(",", "");
            if (!amount.matches("^[0-9\\.]+$")) {
                log.writeLog("金额数据格式错误！");
                return 5;
            }
        } else {
            log.writeLog("金额信息有误！");
            return 6;
        }
        if (Util.null2String(requestid).length() < 1) {
            log.writeLog("流程请求输入有误！");
            return 7;
        }

        // 检查开始日期
        Date realStart = null;
        Date realEnd = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start1 = null;
        try {
            start1 = format.parse(startDate);
            realEnd = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            log.writeLog("日期格式转换异常！");
            return 8;
        }
        Date now = new Date();

        if (now.getTime() > start1.getTime()) {
            realStart = now;
        } else {
            realStart = start1;
        }

        if (realStart.getTime() > realEnd.getTime()) {
            log.writeLog("开始日期不能早于结束日期！");
            //只提醒，允许提交，所以返回0
            return 0;
        }

        Calendar cals = Calendar.getInstance();
        cals.setTime(realStart);

        Calendar cale = Calendar.getInstance();
        cale.setTime(realEnd);

        int s_year = cals.get(Calendar.YEAR);
        int e_year = cale.get(Calendar.YEAR);
        int s_month = cals.get(Calendar.MONTH);
        int e_month = cale.get(Calendar.MONTH);


        // 计算跨几个月
        int month = 0;
        while (true) {
            boolean isBreak = true;
            if (s_year < e_year) {
                isBreak = false;
            }
            if (s_year == e_year) {
                if (s_month <= e_month) {
                    isBreak = false;
                }
            }
            if (isBreak) break;

            month++;

            // 增长
            if (s_month == 11) {
                s_year++;
                s_month = 0;
            } else {
                s_month++;
            }

        }

        if (month < 1) {
            log.writeLog("预算至少 跨一天月!");
            return 10;
        }
        InsertUtil iu = new InsertUtil();
        s_year = cals.get(Calendar.YEAR);
        e_year = cale.get(Calendar.YEAR);
        s_month = cals.get(Calendar.MONTH);
        e_month = cale.get(Calendar.MONTH);

        double s_all_1 = Util.getDoubleValue(amount, 0);
        if (s_all_1 < 0) {
            log.writeLog("申请的金额至少大于0");
            return 11;
        }

        double s_month_1 = s_all_1 / month;
        s_month_1 = ((int) (s_month_1 * 100)) / 100.00;

        RecordSet rs = new RecordSet();

        BudgetSwitchUtil BSU = new BudgetSwitchUtil();
        String deptID = BSU.getBudgetLevel(empID);

        /*
        // 部门  ??
        String deptID = "";
        String dept_Sql = "select htkj_supdepid_dept(departmentid) as supid from hrmresource where id=" + empID;
        rs.executeSql(dept_Sql);
        log.writeLog("dept =" + dept_Sql);
        if (rs.next()) {
            deptID = Util.null2String(rs.getString("supid"));
        }
        */

        if (deptID.length() < 1) {
            log.writeLog("人员存在有误或一级部门不存在！");
            return 12;
        }

        boolean isSucess = true;
        // 单一检查
        while (true) {

            boolean isBreak = true;
            if (s_year < e_year) {
                isBreak = false;
            }
            if (s_year == e_year) {
                if (s_month <= e_month) {
                    isBreak = false;
                }
            }
            if (isBreak) break;

            // 业务处理
            String s_1 = "select sye from uf_yusuan where nd=" + s_year
                    + " and yd=" + s_month + " and ywlx=" + type + " and dqb=" + deptID;
            rs.executeSql(s_1);
            log.writeLog("业务处理 =" + s_1);
            double tmp_s = 0;
            if (rs.next()) {
                tmp_s = Util.getFloatValue(rs.getString("sye"), 0);
            }
            if (tmp_s < s_month_1) {
                // 所有操作失败！
                isSucess = false;
                log.writeLog(s_year + "-" + s_month + " 预算金额不足！剩余预算为" + tmp_s + ",冻结预算为" + s_month_1);
                break;
            }

            // 冻结金额 加上
            s_1 = "update uf_yusuan set dj=nvl(dj,0)+" + s_month_1 + " where nd=" + s_year
                    + " and yd=" + s_month + " and ywlx=" + type + " and dqb=" + deptID;
            log.writeLog("冻结金额  =" + s_1);
            rs.executeSql(s_1);

            // 加上记录
            Map<String, String> mapStr = new HashMap<String, String>();
            mapStr.put("rs_req", requestid);
            mapStr.put("dept", deptID);
            mapStr.put("rs_year", String.valueOf(s_year));
            mapStr.put("rs_month", String.valueOf(s_month));
            mapStr.put("rs_type", type);
            mapStr.put("rs_amount", String.valueOf(s_month_1));
            mapStr.put("rs_active", "0");
            mapStr.put("rs_add", "##to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')");
            iu.insert(mapStr, "uf_fianceSy");

            if (!isSucess) break;
            // 增长
            if (s_month == 11) {
                s_year++;
                s_month = 0;
            } else {
                s_month++;
            }

        }

        // 失败 释放前面内容
        if (!isSucess) {
            unFreeze(requestid);
            return 13;
        }

        return 0;
    }

    /*
     * 释放冻结预算
     * requestid 申请流程ID
     */
    public boolean unFreeze(String requestid) {
        BaseBean log = new BaseBean();
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        String sql = "select * from uf_fianceSy where rs_req=" + requestid + " and rs_active=0";
        rs.executeSql(sql);
        while (rs.next()) {
            String s_month_1 = Util.null2String(rs.getString("rs_amount"));
            String s_year = Util.null2String(rs.getString("rs_year"));
            String s_month = Util.null2String(rs.getString("rs_month"));
            String type = Util.null2String(rs.getString("rs_type"));
            String deptID = Util.null2String(rs.getString("dept"));

            String s_1 = "update uf_yusuan set dj=nvl(dj,0)-" + s_month_1 + " where nd=" + s_year
                    + " and yd=" + s_month + " and ywlx=" + type + " and dqb=" + deptID;
            rs1.executeSql(s_1);
            log.writeLog("unFreeze = " + s_1);
        }

        sql = "update uf_fianceSy set rs_active=1,"
                + " rs_update=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') "
                + " where rs_req=" + requestid + " and rs_active=0 ";
        log.writeLog("unFreeze = " + sql);

        return rs.executeSql(sql);
    }

    /*
     * 释放冻结预算 ， 实际扣减预算
     * requestid 申请流程ID
     */
    public boolean deduction(String requestid) {
        BaseBean log = new BaseBean();
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        String sql = "select * from uf_fianceSy where rs_req=" + requestid + " and rs_active=0";
        rs.executeSql(sql);
        while (rs.next()) {
            String s_month_1 = Util.null2String(rs.getString("rs_amount"));
            String s_year = Util.null2String(rs.getString("rs_year"));
            String s_month = Util.null2String(rs.getString("rs_month"));
            String type = Util.null2String(rs.getString("rs_type"));
            String deptID = Util.null2String(rs.getString("dept"));

            String s_1 = "update uf_yusuan set dj=nvl(dj,0)-" + s_month_1
                    + ",yy=nvl(yy,0)+" + s_month_1 + " where nd=" + s_year
                    + " and yd=" + s_month + " and ywlx=" + type + " and dqb=" + deptID;
            rs1.executeSql(s_1);
            log.writeLog("deduction = " + s_1);
        }

        sql = "update uf_fianceSy set rs_active=1,"
                + " rs_update=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') "
                + " where rs_req=" + requestid + " and rs_active=0 ";
        log.writeLog("deduction = " + sql);

        return rs.executeSql(sql);
    }
}
