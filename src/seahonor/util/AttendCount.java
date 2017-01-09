package seahonor.util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by adore on 16/3/9.
 * 定时任务
 */

public class AttendCount extends BaseCronJob {
    public void execute() {
        RecordSet rs = new RecordSet();
        BaseBean log = new BaseBean();
        Date dt = new Date();
        Date beforeDate = new Date(dt.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String DateBefore=sdf.format(beforeDate);
        log.writeLog("AttendExecuteStart!");
        String sql="exec sh_atten_info_clear_up_new @atten_day1 ='"+DateBefore+"' ";
        rs.executeSql(sql);
        log.writeLog("sql="+sql);
        log.writeLog("AttendExecuteSuccess!");
    }
}
