package TaiSon.Interface;

import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.conn.RecordSet;
import TaiSon.InsertUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2016/10/21.
 * 克隆表结构以及表数据
 */
public class CloneTableUtilNew extends BaseBean{
    public boolean getTableStructure(String dataSource, String dbSql, String tbname) {
        if ("".equals(dataSource)) {
            return false;
        }

        BaseBean log = new BaseBean();//定义写入日志的对象
        RecordSet rs = new RecordSet();
        RecordSetDataSource rsd = new RecordSetDataSource(dataSource);
        RecordSetDataSource rsds = new RecordSetDataSource(dataSource);
        RecordSetDataSource rsdss = new RecordSetDataSource(dataSource);

        boolean isSuccess = true;
        String sql = "";
        String tname = "NC_" + tbname;
        ArrayList list = new ArrayList();
        list = rsd.getAllColumns(dataSource, tbname);
        Map<String, String> mapStr = new HashMap<String, String>();
        InsertUtil IU = new InsertUtil();
        // 判断表里  是否存在
        rs.executeSql("select count(*) ct from " + tname);
        int flag_x = -1;
        if (rs.next()) {
            flag_x = rs.getInt("ct");
        }
        // 表不存在
        if (flag_x < 0) {
            StringBuffer buff = new StringBuffer();
            buff.append("create table ");
            buff.append(tname);
            buff.append("(");
            for (int index = 0; index < list.size(); index++) {
                String tcolumn = (String) list.get(index);
                buff.append(tcolumn);
                buff.append(" varchar2(4000)");
                if (index < list.size() - 1) {
                    buff.append(",");
                }
            }
            buff.append(")");
            //log.writeLog("buff="+buff.toString());
            rs.executeSql(buff.toString());
        } else {
            // 表存在时，需要判断每个字段是否存在
            for (int index = 0; index < list.size(); index++) {
                String tcolumn = (String) list.get(index);
                String sqlx = "select count(*) ct from user_tab_columns where table_name='" + tname.toUpperCase()
                        + "' and column_name='" + tcolumn.toUpperCase() + "'";
                rs.executeSql(sqlx);
                //log.writeLog("sqlx1="+sqlx);
                int flag = 0;
                if (rs.next()) {
                    flag = rs.getInt("ct");
                }
                if (flag < 1) {
                    sqlx = "alter table " + tname + " add (" + tcolumn + " varchar2(4000))";
                    rs.executeSql(sqlx);
                    log.writeLog("sqlx2="+sqlx);
                }
            }
        }
        //}
        rsd.executeSql(dbSql);
        while (rsd.next()) {
            for (int i = 0; i < list.size(); i++) {
                String tcolumn = (String) list.get(i);
                String column_value = Util.null2String(rsd.getString(tcolumn));
                //log.writeLog("column=" + column_value);
                mapStr.put(tcolumn, column_value);
            }
            isSuccess = IU.insert(mapStr, tname);
            log.writeLog("table数据插入成功-----------" + mapStr.toString());
            log.writeLog("插入成功-----------" + isSuccess);
        }

        return isSuccess;
    }
}
