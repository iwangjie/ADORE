package posco.action.product;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * Created by adore on 15/11/12.
 * 获取供应商名称
 */
public class GetSupplierInfo {
    public String getSupplierName(String supplier){
        RecordSet rs = new RecordSet();
        String supplierName = "";
        String sql = "select custom_name from custom_list_info where is_active=0 and id="+supplier;
        rs.executeSql(sql);

        if(rs.next()){
            supplierName = Util.null2String((rs.getString("custom_name")));
        }

        return supplierName;

    }
}
