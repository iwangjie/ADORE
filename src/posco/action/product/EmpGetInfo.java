package posco.action.product;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * Created by adore on 15/11/12.
 * ªÒ»°π§∫≈
 */
public class EmpGetInfo {

    public String getWorkcode(String emp_id){
        RecordSet rs = new RecordSet();

        String workcode = "";
        String sql = " select workcode from hrmresource where id= "+emp_id;
        rs.executeSql(sql);
        if(rs.next()){
            workcode = Util.null2String(rs.getString("workcode"));
        }

        return workcode;
    }
}
