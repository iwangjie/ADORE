package seahonor.util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;


/**
 * Created by adore on 16/7/13.
 */
public class newEmpLeaveUtil extends BaseCronJob {
    public void execute() {
        RecordSet rs = new RecordSet();
        BaseBean log = new BaseBean();
        log.writeLog("newEmpLeaveUtil_Strat!");
        String sql="exec sh_newEmpLeave ";
        rs.executeSql(sql);
        log.writeLog("sql="+sql);
        log.writeLog("newEmpLeaveUtil_Success!");
    }
}
