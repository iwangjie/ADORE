package posco.action.product;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * Created by adore on 15/11/12.
 * 获取部门名称
 */
public class GetDeptName{
    public String getDept(String dept){
        RecordSet rs = new RecordSet();
        String deptName = "";
        String sql = "select departmentname from HrmDepartment where id ="+dept;
        rs.executeSql(sql);

        if(rs.next()){
            deptName = Util.null2String(rs.getString("departmentname"));
        }

        return deptName;

    }
}
