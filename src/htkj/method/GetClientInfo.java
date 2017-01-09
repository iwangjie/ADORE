package htkj.method;

import weaver.conn.RecordSetDataSource;
import weaver.general.Util;

/**
 * Created by adore on 16/4/22.
 */
public class GetClientInfo {

    public String getClientName(String client_id){
        //RecordSet rs = new RecordSet();
        RecordSetDataSource rsds = new RecordSetDataSource("Kingdee_fin");

        String clientName = "";
        String sql = " select FName  from t_Organization where FDeleted=0 and fnumber='"+client_id+"'";
        rsds.executeSql(sql);
        if(rsds.next()){
            clientName = Util.null2String(rsds.getString("FName"));
        }

        return clientName;
    }
}
