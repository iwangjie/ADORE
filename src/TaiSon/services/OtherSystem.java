package TaiSon.services;

import java.util.HashMap;
import java.util.Map;

import TaiSon.Util.TmcDBUtil;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;

public class OtherSystem extends BaseBean {

    /*
     * dataSource OA的数据源    tableName 异构系统表名
     * sql 异构系统的sql  updateTime 去更新时间的字段 [暂时未处理]
     * uqField 唯一标示字段
     */
    public String loadData(String dataSource, String tableName, String sql,
                           String updateTime, String uqField) {
        RecordSetDataSource rsx = new RecordSetDataSource(dataSource);

        RecordSet rs = new RecordSet();
        // 判断表里  是否存在
        rs.executeSql("select count(*) ct from " + tableName);
        int flag_x = -1;
        if (rs.next()) {
            flag_x = rs.getInt("ct");
        }
        rsx.executeSql(sql);
        String columnName[] = null;
        if (rsx.next()) {
            columnName = rsx.getColumnName();
        }
        if (columnName == null) return "提供的sql异常";
        // 表不存在
        if (flag_x < 0) {
            StringBuffer buff = new StringBuffer();
            buff.append("create table");
            buff.append(tableName);
            buff.append("(");
            for (int index = 0; index < columnName.length; index++) {
                buff.append(columnName[index]);
                buff.append(" varchar2(2000)");
                if (index < columnName.length - 1) {
                    buff.append(",");
                }
            }
            buff.append(")");
        } else {
            // 表存在时，需要判断每个字段是否存在
            for (int index = 0; index < columnName.length; index++) {
                String sqlx = "select count(*) ct from all_table_columns where table_name='"
                        + tableName.toUpperCase()
                        + "' and column_name='" + columnName[index].toUpperCase() + "'";
                rs.executeSql(sqlx);
                int flag = 0;
                if (rs.next()) {
                    flag = rs.getInt("ct");
                }
                if (flag < 0) {
                    sqlx = "alter table " + tableName + " add (" + columnName[index] + " varchar2(2000))";
                    rs.executeSql(sqlx);
                }
            }
        }

        TmcDBUtil tu = new TmcDBUtil();
        // 获取值， 插入
        rsx.executeSql(sql);
        while (rsx.next()) {
            String uqFieldVal = Util.null2String(rsx.getString(uqField));
            Map<String, String> mapStr = new HashMap<String, String>();
            Map<String, String> mapWhere = new HashMap<String, String>();
            mapWhere.put(uqField, uqFieldVal);
            // 获取每个字段
            for (int index = 0; index < columnName.length; index++) {
                String tmpx = columnName[index];
                String tmp_1 = Util.null2String(rsx.getString(tmpx));
                mapStr.put(tmpx, tmp_1);
            }
            tu.addOrUpdate(tableName, mapStr, mapWhere);
        }

        return "";
    }
}
