package TaiSon.Interface;

import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.conn.RecordSet;
import TaiSon.InsertUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 2016/10/21.
 * 克隆表结构以及表数据old
 */
public class CloneTableUtil {
    public boolean getTableStructure(String dataSource){
        if("".equals(dataSource)){
            return false;
        }

        BaseBean log = new BaseBean();//定义写入日志的对象
        RecordSet rs = new RecordSet();
        RecordSetDataSource rsd = new RecordSetDataSource(dataSource);
        RecordSetDataSource rsds = new RecordSetDataSource(dataSource);
        RecordSetDataSource rsdss = new RecordSetDataSource(dataSource);

        boolean isSuccess = true;
        //rsd.executeSql(dbSql);

        String sql = "";
        sql = " select table_name from user_tables where table_name=uf_sync_test ";
        rsds.executeSql(sql);
        while(rsds.next()){
            String tablename = Util.null2String(rsds.getString("TABLE_NAME"));
            if(!"".equals(tablename)){
                InsertUtil IU = new InsertUtil();

                sql = " exec ts_createtable_pro("+tablename+") ";
                rs.executeSql(sql);
                log.writeLog("sql_rs="+sql);
                sql = " select * from user_tab_columns where Table_Name='"+tablename+"' ";
                rsd.executeSql(sql);
                log.writeLog("sql_"+tablename+"="+sql);
                Map<String, String> mapStr_D = new HashMap<String, String>();
                while(rsd.next()){
                    String columnname = Util.null2String(rsd.getString("column_name"));
                    String data_type = Util.null2String(rsd.getString("data_type"));
                    String data_length = Util.null2String(rsd.getString("data_length"));

                    sql = " alter table "+tablename+" add ("+columnname+" "+data_type+"("+data_length+")) ";
                    log.writeLog("sql_column_add="+sql);
                    //rs.executeSql(sql);

                    sql = " select * from "+tablename+" ";
                    //mapStr_D.put("mainid", pro_id);
                    //mapStr_D.put("wjmc", doc_name);
                    //mapStr_D.put("fj", attach);

                    String table_D = "uf_lixiangshenqing_dt2";
                    //IU.insert(mapStr_D, table_D);
                    log.writeLog("明细2插入成功-----------");
                }
            }
        }

        return isSuccess;
    }
}
