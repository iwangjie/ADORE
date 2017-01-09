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
     * dataSource OA������Դ    tableName �칹ϵͳ����
     * sql �칹ϵͳ��sql  updateTime ȥ����ʱ����ֶ� [��ʱδ����]
     * uqField Ψһ��ʾ�ֶ�
     */
    public String loadData(String dataSource, String tableName, String sql,
                           String updateTime, String uqField) {
        RecordSetDataSource rsx = new RecordSetDataSource(dataSource);

        RecordSet rs = new RecordSet();
        // �жϱ���  �Ƿ����
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
        if (columnName == null) return "�ṩ��sql�쳣";
        // ������
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
            // �����ʱ����Ҫ�ж�ÿ���ֶ��Ƿ����
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
        // ��ȡֵ�� ����
        rsx.executeSql(sql);
        while (rsx.next()) {
            String uqFieldVal = Util.null2String(rsx.getString(uqField));
            Map<String, String> mapStr = new HashMap<String, String>();
            Map<String, String> mapWhere = new HashMap<String, String>();
            mapWhere.put(uqField, uqFieldVal);
            // ��ȡÿ���ֶ�
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
