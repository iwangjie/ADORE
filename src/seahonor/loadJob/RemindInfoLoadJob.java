package seahonor.loadJob;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adore on 16/4/5.
 */

public class RemindInfoLoadJob extends BaseCronJob {
    private int normalHour = 2;
    private int normalMinute = 0;

    public RemindInfoLoadJob() {
    }

    public void execute() {
        RecordSet rs = new RecordSet();
        RecordSet rs_1 = new RecordSet();
        BaseBean log = new BaseBean();
        log.writeLog("RemindInfoLoadJob ... ");
        String sql = "exec tmc_remind_close_from_filter ";
        rs.executeSql(sql);
        sql = "exec tmc_select_remind_now @remindHZ=1";
        rs.executeSql(sql);

        String tmp_id;
        String tmp_title;
        String tmp_titleUrl;
        String tmp_areaType;
        String tmp_areaVal;
        String tmp_level;
        String tmp_LeadType;
        String tmp_creater;
        String tmp_reDate;
        String tmp_reTime;
        String tmp_remarks;
        String tmp_triggertype;
        String tmp_weeks;
        while(rs.next()) {
            tmp_id = Util.null2String(rs.getString("id"));
            tmp_title = Util.null2String(rs.getString("title"));
            tmp_title = "<a href=\'javascript:openNewInfo(1);\'>" + tmp_title + "</a>";
            tmp_title = tmp_title.replace("\'", "\'\'");
            tmp_titleUrl = Util.null2String(rs.getString("titleUrl"));
            tmp_titleUrl = tmp_titleUrl.replace("\'", "\'\'");
            tmp_areaType = Util.null2String(rs.getString("areaType"));
            tmp_areaVal = Util.null2String(rs.getString("areaVal"));
            tmp_level = Util.null2String(rs.getString("level"));
            tmp_LeadType = Util.null2String(rs.getString("LeadType"));
            tmp_creater = Util.null2String(rs.getString("creater"));
            tmp_reDate = Util.null2String(rs.getString("reDate"));
            tmp_reTime = Util.null2String(rs.getString("reTime"));
            tmp_remarks = Util.null2String(rs.getString("remarks"));
            tmp_remarks = tmp_remarks.replace("\'", "\'\'");
            tmp_triggertype = this.getEmps(tmp_areaType, tmp_areaVal, tmp_level, tmp_LeadType);
            StringBuffer tmp_triggercycletime = new StringBuffer();
            tmp_triggercycletime.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
            tmp_triggercycletime.append("waySys,remindEmp,title,titleUrl,remarks)");
            tmp_triggercycletime.append("select ");
            tmp_triggercycletime.append(tmp_id);
            tmp_triggercycletime.append(",");
            tmp_triggercycletime.append(tmp_creater);
            tmp_triggercycletime.append(",CONVERT(varchar(100),GETDATE(),21),\'");
            tmp_triggercycletime.append(tmp_reDate);
            tmp_triggercycletime.append("\',\'");
            tmp_triggercycletime.append(tmp_reTime);
            tmp_triggercycletime.append("\',\'0\',id,\'");
            tmp_triggercycletime.append(tmp_title);
            tmp_triggercycletime.append("\',\'");
            tmp_triggercycletime.append(tmp_titleUrl);
            tmp_triggercycletime.append("\',\'");
            tmp_triggercycletime.append(tmp_remarks);
            tmp_triggercycletime.append("\' from HrmResource where id in(");
            tmp_triggercycletime.append(tmp_triggertype);
            tmp_triggercycletime.append(" ) and id not in(select remindEmp from uf_remindFilter where is_active=0) ");
            log.writeLog("@71@ = " + tmp_triggercycletime.toString());
            rs_1.executeSql(tmp_triggercycletime.toString());
            tmp_weeks = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
            log.writeLog("@76@ = " + tmp_weeks);
            rs_1.executeSql(tmp_weeks);
        }

        sql = "exec tmc_select_remind_now @remindHZ=2";
        rs.executeSql(sql);

        while(true) {
            label85:
            while(rs.next()) {
                tmp_id = Util.null2String(rs.getString("id"));
                tmp_title = Util.null2String(rs.getString("title"));
                tmp_titleUrl = Util.null2String(rs.getString("titleUrl"));
                tmp_areaType = Util.null2String(rs.getString("areaType"));
                tmp_areaVal = Util.null2String(rs.getString("areaVal"));
                tmp_level = Util.null2String(rs.getString("level"));
                tmp_LeadType = Util.null2String(rs.getString("LeadType"));
                tmp_creater = Util.null2String(rs.getString("creater"));
                tmp_reDate = Util.null2String(rs.getString("reDate"));
                tmp_reTime = Util.null2String(rs.getString("reTime"));
                tmp_remarks = Util.null2String(rs.getString("remarks"));
                tmp_triggertype = Util.null2String(rs.getString("triggertype"));
                String tmp_triggercycletime1 = Util.null2String(rs.getString("triggercycletime"));
                tmp_weeks = Util.null2String(rs.getString("weeks"));
                String tmp_months = Util.null2String(rs.getString("months"));
                String tmp_days = Util.null2String(rs.getString("days"));
                String tmp_remindtimetype = Util.null2String(rs.getString("remindtimetype"));
                String tmp_stopID = Util.null2String(rs.getString("stopID"));
                String tmp_stopUqID = Util.null2String(rs.getString("stopUqID"));
                String tmp_selStopInfo = Util.null2String(rs.getString("selStopInfo"));
                String tmp_timeField;
                String tmp_tableName;
                if(!"2".equals(tmp_remindtimetype)) {
                    boolean tmp_dateField1 = this.isGoonRemind(tmp_stopID, tmp_stopUqID);
                    if(!tmp_dateField1) {
                        tmp_timeField = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
                        log.writeLog("@118@ = " + tmp_timeField);
                        rs_1.executeSql(tmp_timeField);
                    } else {
                        boolean tmp_timeField1 = this.isNowTrigger(tmp_reDate, tmp_reTime, tmp_triggertype, tmp_triggercycletime1, tmp_weeks, tmp_months, tmp_days);
                        if(tmp_timeField1) {
                            tmp_tableName = this.getEmps(tmp_areaType, tmp_areaVal, tmp_level, tmp_LeadType);
                            StringBuffer tmp_infowhere1 = new StringBuffer();
                            tmp_infowhere1.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
                            tmp_infowhere1.append("waySys,remindEmp,title,titleUrl,remarks)");
                            tmp_infowhere1.append("select ");
                            tmp_infowhere1.append(tmp_id);
                            tmp_infowhere1.append(",");
                            tmp_infowhere1.append(tmp_creater);
                            tmp_infowhere1.append(",CONVERT(varchar(100),GETDATE(),21),\'");
                            tmp_infowhere1.append(tmp_reDate);
                            tmp_infowhere1.append("\',\'");
                            tmp_infowhere1.append(tmp_reTime);
                            tmp_infowhere1.append("\',\'0\',id,\'");
                            tmp_infowhere1.append(tmp_title);
                            tmp_infowhere1.append("\',\'");
                            tmp_infowhere1.append(tmp_titleUrl);
                            tmp_infowhere1.append("\',\'");
                            tmp_infowhere1.append(tmp_remarks);
                            tmp_infowhere1.append("\' from HrmResource where id in(");
                            tmp_infowhere1.append(tmp_tableName);
                            tmp_infowhere1.append(" ) and id not in(select remindEmp from uf_remindFilter where is_active=0) ");
                            log.writeLog("@141@ = " + tmp_infowhere1.toString());
                            rs_1.executeSql(tmp_infowhere1.toString());
                        }

                        if("6".equals(tmp_triggertype)) {
                            tmp_tableName = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
                            log.writeLog("@148@ = " + tmp_tableName);
                            rs_1.executeSql(tmp_tableName);
                        }
                    }
                } else {
                    String tmp_dateField = Util.null2String(rs.getString("dateField"));
                    tmp_timeField = Util.null2String(rs.getString("timeField"));
                    tmp_tableName = Util.null2String(rs.getString("tableName"));
                    String tmp_infowhere = Util.null2String(rs.getString("infowhere"));
                    String tmp_incrementunit;
                    if("1".equals(tmp_selStopInfo)) {
                        boolean tmp_incrementnum = this.isGoonRemind(tmp_stopID, tmp_stopUqID);
                        if(!tmp_incrementnum) {
                            tmp_incrementunit = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
                            log.writeLog("@170@ = " + tmp_incrementunit);
                            rs_1.executeSql(tmp_incrementunit);
                            continue;
                        }
                    }

                    String tmp_incrementnum1 = Util.null2String(rs.getString("incrementnum")).replace(".00", "");
                    tmp_incrementunit = Util.null2String(rs.getString("incrementunit"));
                    String tmp_incrementway = Util.null2String(rs.getString("incrementway"));
                    String x_sql = "select 1,";
                    if(!"".equals(tmp_dateField)) {
                        x_sql = x_sql + tmp_dateField + " as tmpDate,";
                    }

                    if(!"".equals(tmp_timeField)) {
                        x_sql = x_sql + tmp_timeField + " as tmpTime,";
                    }

                    if(!"".equals(tmp_stopUqID) && !"1".equals(tmp_selStopInfo)) {
                        x_sql = x_sql + tmp_stopUqID + " as tmpID,";
                    }

                    x_sql = x_sql + "2 from " + tmp_tableName + " " + tmp_infowhere;
                    log.writeLog("@191@ = " + x_sql);
                    rs_1.executeSql(x_sql);

                    while(true) {
                        String tmp_date;
                        String tmp_time;
                        boolean tmp_x_reDate;
                        do {
                            do {
                                if(!rs_1.next()) {
                                    continue label85;
                                }

                                tmp_date = Util.null2String(rs_1.getString("tmpDate"));
                            } while("".equals(tmp_date));

                            tmp_time = Util.null2String(rs_1.getString("tmpTime"));
                            if("".equals(tmp_time)) {
                                tmp_time = "00:00:00";
                            }

                            if(tmp_time.length() == 5) {
                                tmp_time = tmp_time + ":00";
                            }

                            if("1".equals(tmp_selStopInfo)) {
                                break;
                            }

                            String tmp_arr = Util.null2String(rs_1.getString("tmpID"));
                            tmp_x_reDate = this.isGoonRemind(tmp_stopID, tmp_arr);
                        } while(!tmp_x_reDate);

                        String[] tmp_arr1 = this.getRes(tmp_date, tmp_time, tmp_incrementnum1, tmp_incrementunit, tmp_incrementway);
                        if(tmp_arr1 != null && tmp_arr1.length == 2) {
                            String tmp_x_reDate1 = tmp_arr1[0];
                            String tmp_x_reTime = tmp_arr1[0];
                            boolean isTrigger_dt = this.isNowTrigger(tmp_x_reDate1, tmp_x_reTime, tmp_triggertype, tmp_triggercycletime1, tmp_weeks, tmp_months, tmp_days);
                            if(isTrigger_dt) {
                                String empids = this.getEmps(tmp_areaType, tmp_areaVal, tmp_level, tmp_LeadType);
                                StringBuffer buff = new StringBuffer();
                                buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
                                buff.append("waySys,remindEmp,title,titleUrl,remarks)");
                                buff.append("select ");
                                buff.append(tmp_id);
                                buff.append(",");
                                buff.append(tmp_creater);
                                buff.append(",CONVERT(varchar(100),GETDATE(),21),\'");
                                buff.append(tmp_x_reDate1);
                                buff.append("\',\'");
                                buff.append(tmp_x_reDate1);
                                buff.append("\',\'0\',id,\'");
                                buff.append(tmp_title);
                                buff.append("\',\'");
                                buff.append(tmp_titleUrl);
                                buff.append("\',\'");
                                buff.append(tmp_remarks);
                                buff.append("\' from HrmResource where id in(");
                                buff.append(empids);
                                buff.append(" ) and id not in(select remindEmp from uf_remindFilter where is_active=0) ");
                                log.writeLog("@231@ = " + buff.toString());
                                rs_1.executeSql(buff.toString());
                            }
                        }
                    }
                }
            }

            return;
        }
    }

    public String[] getRes(String dates, String times, String num, String unit, String type) {
        boolean num_1 = false;
        int num_11;
        if("1".equals(type)) {
            num_11 = 0 - Integer.parseInt(num);
        } else {
            num_11 = Integer.parseInt(num);
        }

        String[] arr = new String[]{"", ""};
        String tmp_1 = dates + "@" + times;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");

        try {
            Date e = sdf.parse(tmp_1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(e);
            if("1".equals(unit)) {
                calendar.add(12, num_11);
            } else if("2".equals(unit)) {
                calendar.add(10, num_11);
            } else if("3".equals(unit)) {
                calendar.add(5, num_11);
            } else if("4".equals(unit)) {
                calendar.add(2, num_11);
            }

            tmp_1 = sdf.format(calendar.getTime());
            arr = tmp_1.split("@");
            return arr;
        } catch (ParseException var12) {
            return arr;
        }
    }

    public boolean isGoonRemind(String stopID, String stopUqID) {
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        boolean isGoon = true;
        if("".equals(stopID)) {
            return isGoon;
        } else {
            String sql = "select * from uf_remindOtherStop where id in(" + stopID + ")";
            rs.executeSql(sql);

            String stopInfo;
            String res;
            do {
                if(!rs.next()) {
                    return isGoon;
                }

                String stopSql = Util.null2String(rs.getString("stopSql")).replace("$ID$", "\'" + stopUqID + "\'");
                stopInfo = Util.null2String(rs.getString("stopInfo"));
                rs1.executeSql(stopSql);
                res = "";
                if(rs1.next()) {
                    res = Util.null2String(rs1.getString(1));
                }
            } while(!res.equalsIgnoreCase(stopInfo));

            isGoon = false;
            return isGoon;
        }
    }

    public boolean isNowTrigger(String reDate, String reTime, String triggertype, String triggercycletime, String weeks, String months, String days) {
        boolean isTrigger = false;
        if("6".equals(triggertype)) {
            isTrigger = true;
        } else {
            int cal;
            int month;
            if("1".equals(triggertype)) {
                cal = this.getHowMi(reDate, reTime);
                if("".equals(triggercycletime)) {
                    return isTrigger;
                }

                month = Integer.parseInt(triggercycletime);
                if(cal > 0 && month > 0 && cal % month == 0) {
                    isTrigger = true;
                }
            } else if("2".equals(triggertype)) {
                cal = this.getHowMi(reDate, reTime);
                if("".equals(triggercycletime)) {
                    return isTrigger;
                }

                month = Integer.parseInt(triggercycletime) * 60;
                if(cal > 0 && month > 0 && cal % month == 0) {
                    isTrigger = true;
                }
            } else if("3".equals(triggertype)) {
                cal = this.getHowMi(reDate, reTime);
                if("".equals(triggercycletime)) {
                    return isTrigger;
                }

                month = Integer.parseInt(triggercycletime) * 60 * 24;
                if(cal > 0 && month > 0 && cal % month == 0) {
                    isTrigger = true;
                }
            } else {
                int day;
                int hour;
                Calendar cal1;
                if("4".equals(triggertype)) {
                    cal1 = Calendar.getInstance();
                    month = cal1.get(10);
                    day = cal1.get(12);
                    if(month == this.normalHour && day == this.normalMinute) {
                        hour = cal1.get(7) - 1;
                        if(hour < 0) {
                            hour = 0;
                        }

                        if(weeks.contains(String.valueOf(hour))) {
                            isTrigger = true;
                        } else {
                            String[] minute = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
                            String monthArr = minute[hour];
                            weeks = weeks.replace(" ", "");
                            if(weeks.contains(monthArr)) {
                                isTrigger = true;
                            }
                        }
                    }
                } else if("5".equals(triggertype)) {
                    months = "," + months.replace(" ", "") + ",";
                    days = "," + days.replace(" ", "") + ",";
                    cal1 = Calendar.getInstance();
                    month = cal1.get(2) + 1;
                    day = cal1.get(5);
                    hour = cal1.get(10);
                    int minute1 = cal1.get(12);
                    if(hour == this.normalHour && minute1 == this.normalMinute && days.contains("," + String.valueOf(day) + ",")) {
                        if(months.contains("," + String.valueOf(month) + ",")) {
                            isTrigger = true;
                        } else {
                            String[] monthArr1 = new String[]{"", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
                            String month_str = monthArr1[month];
                            if(months.contains("," + month_str + ",")) {
                                isTrigger = true;
                            }
                        }
                    }
                }
            }
        }

        return isTrigger;
    }

    private int getHowMi(String reDate, String reTime) {
        String tmp_1 = reDate + " " + reTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        byte res = 0;

        try {
            Date e = sdf.parse(tmp_1);
            Date now = new Date();
            long ks = now.getTime() - e.getTime();
            int res1 = (int)ks / '\uea60';
            return res1;
        } catch (ParseException var10) {
            return res;
        }
    }

    public String getEmps(String areaType, String areaVal, String level, String leadType) {
        BaseBean log = new BaseBean();
        StringBuffer buff = new StringBuffer();
        StringBuffer trueBuff = new StringBuffer();
        RecordSet rs = new RecordSet();
        if("".equals(level)) {
            level = "10";
        }

        String sql = "";
        log.writeLog("#118# = <areaType>" + areaType + "<areaVal>" + areaVal + "<level>" + level + "<leadType>" + leadType);
        String flag;
        if("1".equals(areaType)) {
            buff.append(areaVal);
        } else if("2".equals(areaType)) {
            sql = "\tselect id from HrmResource where status in(0,1,2,3) and subcompanyid1 in(" + areaVal + ") and seclevel>=" + level;
            log.writeLog("@479@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                buff.append(flag);
                buff.append(Util.null2String(rs.getString("id")));
            }
        } else if("3".equals(areaType)) {
            sql = "\tselect id from HrmResource where status in(0,1,2,3) and departmentid in(" + areaVal + ") and seclevel>=" + level;
            log.writeLog("@491@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                buff.append(flag);
                buff.append(Util.null2String(rs.getString("id")));
            }
        } else if("4".equals(areaType)) {
            sql = "\tselect id from HrmResource where status in(0,1,2,3) and departmentid in(" + areaVal + ") and seclevel>=" + level;
            log.writeLog("@502@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                buff.append(flag);
                buff.append(Util.null2String(rs.getString("id")));
            }
        } else if("5".equals(areaType)) {
            sql = "select resourceid from hrmroleMembers where roleid in(  select id from hrmroles where id in(" + areaVal + ") ) " + " and resourceid in(select id from HrmResource where seclevel>=" + level + " and status in(0,1,2,3))";
            log.writeLog("@517@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                buff.append(flag);
                buff.append(Util.null2String(rs.getString("resourceid")));
            }
        }

        if("2".equals(leadType)) {
            sql = "select id from HrmResource where managerid in(" + buff.toString() + ") and status in(0,1,2,3) ";
            log.writeLog("@529@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                trueBuff.append(flag);
                trueBuff.append(Util.null2String(rs.getString("id")));
            }
        } else if("3".equals(leadType)) {
            sql = "select managerid from HrmResource where id in(" + buff.toString() + ") and status in(0,1,2,3) ";
            log.writeLog("@539@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                trueBuff.append(flag);
                trueBuff.append(Util.null2String(rs.getString("managerid")));
            }
        } else if("4".equals(leadType)) {
            sql = "select managerid from HrmResource where id in(select managerid from HrmResource where id in(" + buff.toString() + ") and status in(0,1,2,3)) and status in(0,1,2,3) ";
            log.writeLog("@550@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                trueBuff.append(flag);
                trueBuff.append(Util.null2String(rs.getString("managerid")));
            }
        } else if("5".equals(leadType)) {
            sql = "select managerid from HrmResource where id in(select managerid from HrmResource where id in(select managerid from HrmResource where id in(" + buff.toString() + ") and status in(0,1,2,3)) and status in(0,1,2,3)) and status in(0,1,2,3) ";
            log.writeLog("@562@ = " + sql);
            rs.execute(sql);

            for(flag = ""; rs.next(); flag = ",") {
                trueBuff.append(flag);
                trueBuff.append(Util.null2String(rs.getString("managerid")));
            }
        } else {
            trueBuff = buff;
        }

        return trueBuff.toString();
    }
}

