package yeo.util;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * Created by adore on 16/9/6.
 * 获取自定义流程requestid
 */
public class GetReqidUtil {

    public String getReqid() {
        RecordSet rs = new RecordSet();

        String reqid = "";
        String sql = " select PurReqid  from OA_ERP_Requsetid ";
        rs.executeSql(sql);
        if (rs.next()) {
            reqid = Util.null2String(rs.getString("PurReqid"));
            if ("".equals(reqid)) {
                reqid = "1";
            }
        }
        return reqid;
    }

}
