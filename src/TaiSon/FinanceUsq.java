package TaiSon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TaiSon.Util.BudgetSwitchUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class FinanceUsq {

    /*
     * ����Ԥ�㣺 ���·ݶ���,����ƽ����̯;Ŀǰ���·ݱȿ�ʼ�����·ݴ󣬰�Ŀǰʱ���·ݼ���
     * empID ������
     * startDate ��ʼ����
     * endDate ��������
     * type ҵ������
     * amount  �ܽ��
     * requestid ��������ID
     */
    public boolean freeze(String empID, String startDate, String endDate, String type,
                          String amount, String requestid) {
        BaseBean log = new BaseBean();
        // ��ֵΪ������Ҫ��,����ʧ��
        if (Util.null2String(empID).length() < 1) {
            log.writeLog("��Ա����ֵ��Ч��");
            return false;
        }
        if (Util.null2String(startDate).length() != 10) {
            log.writeLog("��ʼ���ڸ�ʽ����");
            return false;
        }
        if (Util.null2String(endDate).length() != 10) {
            log.writeLog("�������ڸ�ʽ����");
            return false;
        }
        if (Util.null2String(type).length() < 1) {
            log.writeLog("ҵ��������������");
            return false;
        }
        // ���ý����Ҫ���
        if (Util.null2String(amount).length() >= 1) {
            amount = amount.replace(",", "");
            if (!amount.matches("^[0-9\\.]+$")) {
                log.writeLog("������ݸ�ʽ����");
                return false;
            }
        } else {
            log.writeLog("�����Ϣ����");
            return false;
        }
        if (Util.null2String(requestid).length() < 1) {
            log.writeLog("����������������");
            return false;
        }

        // ��鿪ʼ����
        Date realStart = null;
        Date realEnd = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start1 = null;
        try {
            start1 = format.parse(startDate);
            realEnd = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            log.writeLog("���ڸ�ʽת���쳣��");
            return false;
        }
        Date now = new Date();

        if (now.getTime() > start1.getTime()) {
            realStart = now;
        } else {
            realStart = start1;
        }

        if (realStart.getTime() > realEnd.getTime()) {
            log.writeLog("��ʼ���ڲ������ڽ������ڣ�");
            return false;
        }

        Calendar cals = Calendar.getInstance();
        cals.setTime(realStart);

        Calendar cale = Calendar.getInstance();
        cale.setTime(realEnd);

        int s_year = cals.get(Calendar.YEAR);
        int e_year = cale.get(Calendar.YEAR);
        int s_month = cals.get(Calendar.MONTH);
        int e_month = cale.get(Calendar.MONTH);


        // ����缸����
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

            // ����
            if (s_month == 11) {
                s_year++;
                s_month = 0;
            } else {
                s_month++;
            }

        }

        if (month < 1) {
            log.writeLog("Ԥ������ ��һ����!");
            return false;
        }
        InsertUtil iu = new InsertUtil();
        s_year = cals.get(Calendar.YEAR);
        e_year = cale.get(Calendar.YEAR);
        s_month = cals.get(Calendar.MONTH);
        e_month = cale.get(Calendar.MONTH);

        double s_all_1 = Util.getDoubleValue(amount, 0);
        if (s_all_1 <= 0) {
            log.writeLog("����Ľ�����ٴ���0");
            return false;
        }

        double s_month_1 = s_all_1 / month;
        s_month_1 = ((int) (s_month_1 * 100)) / 100.00;

        RecordSet rs = new RecordSet();

        BudgetSwitchUtil BSU = new BudgetSwitchUtil();
        String deptID = BSU.getBudgetLevel(empID);

        /*
        // ����  ??
        String deptID = "";
        String dept_Sql = "select htkj_supdepid_dept(departmentid) as supid from hrmresource where id=" + empID;
        rs.executeSql(dept_Sql);
        log.writeLog("dept =" + dept_Sql);
        if (rs.next()) {
            deptID = Util.null2String(rs.getString("supid"));
        }
        */

        if (deptID.length() < 1) {
            log.writeLog("��Ա���������һ�����Ų����ڣ�");
            return false;
        }

        boolean isSucess = true;
        // ��һ���
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

            // ҵ����
            String s_1 = "select sye from uf_yusuan where nd=" + s_year
                    + " and yd=" + s_month + " and ywlx=" + type + " and dqb=" + deptID;
            rs.executeSql(s_1);
            log.writeLog("ҵ���� =" + s_1);
            double tmp_s = 0;
            if (rs.next()) {
                tmp_s = Util.getFloatValue(rs.getString("sye"), 0);
            }
            if (tmp_s < s_month_1) {
                // ���в���ʧ�ܣ�
                isSucess = false;
                log.writeLog(s_year + "-" + s_month + " Ԥ�����!ʣ��Ԥ��Ϊ" + tmp_s + ",����Ԥ��Ϊ" + s_month_1);
                break;
            }

            // ������ ����
            s_1 = "update uf_yusuan set dj=nvl(dj,0)+" + s_month_1 + " where nd=" + s_year
                    + " and yd=" + s_month + " and ywlx=" + type + " and dqb=" + deptID;
            log.writeLog("������  =" + s_1);
            rs.executeSql(s_1);

            // ���ϼ�¼
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
            // ����
            if (s_month == 11) {
                s_year++;
                s_month = 0;
            } else {
                s_month++;
            }

        }

        // ʧ�� �ͷ�ǰ������
        if (!isSucess) {
            unFreeze(requestid);
            return false;
        }

        return true;
    }

    /*
     * �ͷŶ���Ԥ��
     * requestid ��������ID
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
     * �ͷŶ���Ԥ�� �� ʵ�ʿۼ�Ԥ��
     * requestid ��������ID
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
