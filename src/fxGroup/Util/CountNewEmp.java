package fxGroup.Util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;


/**
 * Created by adore on 16/4/28.
 * 定时任务
 */
public class CountNewEmp extends BaseCronJob {

    public CountNewEmp() {
    }
    public void execute() {
        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        BaseBean log = new BaseBean();
        log.writeLog("AnnualExecuteStart!");
        String sql=" select count(*) as num_cc from hrmresource where SUBSTRING(startdate,0,5)=CONVERT(varchar(4), GETDATE(), 23) "
                +" and id not in(select emp_id from formtable_main_59) ";
        rs.executeSql(sql);
        log.writeLog("sql="+sql);
        if(rs.next()){
            int num_cc=rs.getInt("num_cc");
            if(num_cc>0){
                sql=" select id,startdate from hrmresource where SUBSTRING(startdate,0,5)=CONVERT(varchar(4), GETDATE(), 23) "
                        + " and id not in(select emp_id from formtable_main_59) ";
                rs.executeSql(sql);
                while (rs.next()){
                    String emp_id= Util.null2String(rs.getString("id"));
                    String startdate= Util.null2String(rs.getString("startdate"));
                    String sql_new=" insert into formtable_main_59(emp_id,annual_days,isactive,status,startdate) "
                            +" values("+emp_id+",0,0,1,'"+startdate+"') ";
                    res.executeSql(sql_new);
                    log.writeLog("sql_new="+sql_new);
                }
            }else {
                return;
            }
        }
        log.writeLog("sql="+sql);
        log.writeLog("AnnualExecuteSuccess!");
    }
}
