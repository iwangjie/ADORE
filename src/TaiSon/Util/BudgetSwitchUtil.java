package TaiSon.Util;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * Created by adore on 2016/9/27.
 * 预算控制开关
 */
public class BudgetSwitchUtil {
    public String getBudgetLevel(String empid) {
        RecordSet rs = new RecordSet();

        String deptID = "";
        String supid = "";
        String ssupid = "";
        String clevel = "";//控制级别：0大区、1省区、2经营部
        String department = "";//empid所在部门
        String dlevel = "";//部门级别：0一级部门、1二级部门、2三级部门
        String sql = " select ctrllevel from uf_budgetswitch where isActive=0 ";
        rs.executeSql(sql);
        if (rs.next()) {
            clevel = Util.null2String(rs.getString("ctrllevel"));
        }

        sql = " select departmentid,id,TS_EMP_LEVEL(id) as dlevel from hrmresource where id="+empid+" ";
        rs.executeSql(sql);
        if (rs.next()) {
            department = Util.null2String(rs.getString("departmentid"));
            dlevel = Util.null2String(rs.getString("dlevel"));
        }

        sql = " select id,LEVEL0,SUPID,LEVEL1,SSUPID,LEVEL2 from TS_DEPT_LEVEL_VIEW where id="+department+" ";
        rs.executeSql(sql);
        if (rs.next()) {
            supid = Util.null2String(rs.getString("SUPID"));
            ssupid = Util.null2String(rs.getString("SSUPID"));
        }

        if(clevel.compareTo(dlevel)>=0){
            deptID = department;
        }else {
            if ("2".equals(clevel)) {
                deptID = department;
            } else if ("1".equals(clevel)) {
                deptID = supid;
            } else {
                deptID = ssupid;
            }
        }
        return deptID;
    }
}
