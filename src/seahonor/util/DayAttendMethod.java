package seahonor.util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adore on 16/9/12.
 * 每日考勤统计方法
 */
public class DayAttendMethod {
    //设备考勤时间计算
    public String[] getEmpInfo(String emp_id) {
        RecordSet rs = new RecordSet();
        BaseBean log = new BaseBean();
        String[] arr = new String[8];//排班情况

        String amBeginTime = "";
        String amEndTime = "";
        String pmBeginTime = "";
        String pmEndTime = "";
        String amAttendTime = "";
        String pmAttendTime = "";
        String latetime = "";
        String earlyleavetime = "";

        String sql = " select AmBeginTime,AmEndTime,PmBeginTime,PmEndTime "
                + " from uf_Scheduling_table where isActive = 0 and EmployeeName =  " + emp_id;
        rs.executeSql(sql);
        //log.writeLog("sql___________" + sql);
        if (rs.next()) {
            amBeginTime = Util.null2String(rs.getString("AmBeginTime"));
            amEndTime = Util.null2String(rs.getString("AmEndTime"));
            pmBeginTime = Util.null2String(rs.getString("PmBeginTime"));
            pmEndTime = Util.null2String(rs.getString("PmEndTime"));

        }

        String sql_attend = " select * from dbo.sh_attend_count(" + emp_id + ",'" + amBeginTime + "','" + pmEndTime + "') ";
        rs.executeSql(sql_attend);

        if (rs.next()) {
            amAttendTime = Util.null2String(rs.getString("amattendtime"));
            pmAttendTime = Util.null2String(rs.getString("pmattendtime"));
            latetime = Util.null2String(rs.getString("latetime"));
            earlyleavetime = Util.null2String(rs.getString("earlyleavetime"));

        }
        for (int i = 0; i < arr.length; i++) {
            arr[0] = amBeginTime;//上午开始时间
            arr[1] = amEndTime;//上午结束时间
            arr[2] = pmBeginTime;//下午开始时间
            arr[3] = pmEndTime;//下午结束时间
            arr[4] = amAttendTime;//上班打卡时间
            arr[5] = pmAttendTime;//下班打卡时间
            arr[6] = latetime;//迟到
            arr[7] = earlyleavetime;//早退
        }

        return arr;
    }

    //移动考勤时间计算
    public String[] getOutInfo(String emp_id, String out_date) {
        RecordSet rs = new RecordSet();
        BaseBean log = new BaseBean();
        String[] arr = new String[6];//排班情况

        String amBeginTime = "";
        String pmEndTime = "";
        String amAttendTime = "";
        String pmAttendTime = "";
        String latetime = "";
        String earlyleavetime = "";

        String sql = " select min(ReplaceStartTime),max(ReplaceEndTime) from uf_Replace_table where EmployeeName =  " + emp_id + " "
                + " and ReplaceStartDate = '" + out_date + "' ";
        rs.executeSql(sql);
        //log.writeLog("sql___________" + sql);
        if (rs.next()) {
            amBeginTime = Util.null2String(rs.getString("ReplaceStartTime"));
            pmEndTime = Util.null2String(rs.getString("ReplaceEndTime"));

        }

        String sql_attend = " select * from dbo.sh_out_attend_count(" + emp_id + ",'" + amBeginTime + "','" + pmEndTime + "') ";
        rs.executeSql(sql_attend);

        if (rs.next()) {
            amAttendTime = Util.null2String(rs.getString("amattendtime"));
            pmAttendTime = Util.null2String(rs.getString("pmattendtime"));
            latetime = Util.null2String(rs.getString("latetime"));
            earlyleavetime = Util.null2String(rs.getString("earlyleavetime"));

        }
        for (int i = 0; i < arr.length; i++) {
            arr[0] = amBeginTime;//外出开始时间
            arr[1] = amAttendTime;//外出签到时间
            arr[2] = pmEndTime;//外出结束时间
            arr[3] = pmAttendTime;//外出签退时间
            arr[4] = latetime;//迟到时间
            arr[5] = earlyleavetime;//早退时间
        }

        return arr;
    }

    //String类型转int类型计算
    public String countTime(String latetime, String earlyleavetime) {

        String sum = "";
        //new BaseBean().writeLog("latetime=" + latetime +" earlyleavetime="+earlyleavetime);
        if ("".equals(latetime)) latetime = "0";
        if ("".equals(earlyleavetime)) earlyleavetime = "0";

        int tmp_latetime = Integer.valueOf(latetime).intValue();
        int tmp_earlyleavetime = Integer.valueOf(earlyleavetime).intValue();
        int sumtime = tmp_latetime + tmp_earlyleavetime;
        if (sumtime > 480) {
            sumtime = 480;
        }
        sum = Integer.toString(sumtime);

        return sum;
    }

    //获取当前日期
    public String getNowDate() {
        String temp_str = "";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        temp_str = "2016-09-02";
        return temp_str;
    }
}
