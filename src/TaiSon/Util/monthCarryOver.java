package TaiSon.Util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.util.Calendar;

/**
 * Created by adore on 16/7/13.
 * Update on 16/12/1.
 * 每月预算费用结转
 */
public class monthCarryOver extends BaseCronJob {
    public void execute() {
        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        BaseBean log = new BaseBean();
        log.writeLog("monthCarryOver_Start!");
        Calendar cal = Calendar.getInstance();
        int month = cal.get(cal.MONTH) - 1;//每月1号核算，月份获取为上个月
        String sql = "";
        sql = " select ID,ND,YD,DQB,SYE from uf_yusuan where yd=" + month + " and nvl(SYE,0)>0 ";
        rs.execute(sql);
        log.writeLog("sql_1=" + sql);
        while (rs.next()) {
            int year = rs.getInt("ND");
            int yd = rs.getInt("YD");
            int next_yd = 0;
            int next_nd = 0;
            if (yd == 11) {
                next_yd = 0;
                next_nd = year + 1;
            } else {
                next_yd = yd + 1;
                next_nd = year;
            }
            String dqb = Util.null2String(rs.getString("DQB"));
            String mid = Util.null2String(rs.getString("ID"));
            double sye = rs.getDouble("sye");
            if (sye > 0) {
                sql = " update uf_yusuan set sjkzz=nvl(sjkzz,0)+" + sye + " where yd=" + next_yd + " and ND=" + next_nd + " and DQB=" + dqb + " ";
                res.execute(sql);
                //log.writeLog("sql_2=" + sql);

                sql = " update uf_yusuan set sye=0 where id=" + mid;
                res.execute(sql);
                //log.writeLog("sql_3=" + sql);
            } else {
                log.writeLog("差额更新失败!");
            }
        }

        log.writeLog("monthCarryOver_Success!");
    }
}
