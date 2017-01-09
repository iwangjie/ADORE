package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.general.BaseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by adore on 2016/9/22.
 */
public class ScheduleJson {

    public String getScheduleData(String deptid, String subcomid) {

        BaseBean log = new BaseBean();
        RecordSet rs = new RecordSet();
        String sql = "";
        String sql_count = "";
        String id = "";
        int index = 0;
        List<Map> list = new ArrayList<Map>();

        String backfields = " modedatacreater,modedatacreatedate,modedatacreatetime,AmBeginTime,AmEndTime,"
                + "PmBeginTime,PmEndTime,EffectiveStartDate,EffectiveEndDate,EmployeeName  ";
        String fromSql = " uf_Scheduling_table ";
        String sqlWhere = " isActive = 0 ";

        if (!"".equals(deptid)) {
            sqlWhere += " and department  = " + deptid + " ";
        }
        if (!"".equals(subcomid)) {
            sqlWhere += " and subcompany = " + subcomid + " ";
        }
        sql_count = " count(*) as num_count ";
/*
        sql = " select " + sql_count + " from " + fromSql + " where " + sqlWhere;
        rs.executeSql(sql);
        if (rs.next()) {
            index = rs.getInt("num_count");
        }
*/
        //for (int i = 0; i < index; i++) {
        sql = " select " + backfields + " from " + fromSql + " where " + sqlWhere;
        rs.executeSql(sql);
        while (rs.next()) {
            //数据在循环内声明或者循环结束后清空

            String modedatacreater = Util.null2String(rs.getString("modedatacreater"));
            String modedatacreatedate = Util.null2String(rs.getString("modedatacreatedate"));
            String modedatacreatetime = Util.null2String(rs.getString("modedatacreatetime"));
            String AmBeginTime = Util.null2String(rs.getString("AmBeginTime"));
            String AmEndTime = Util.null2String(rs.getString("AmEndTime"));
            String PmBeginTime = Util.null2String(rs.getString("PmBeginTime"));
            String PmEndTime = Util.null2String(rs.getString("PmEndTime"));
            String EffectiveStartDate = Util.null2String(rs.getString("EffectiveStartDate"));
            String EffectiveEndDate = Util.null2String(rs.getString("EffectiveEndDate"));
            String EmployeeName = Util.null2String(rs.getString("EmployeeName"));

            Map<String, String> mapStr = new HashMap<String, String>();
            mapStr.put("modedatacreater", modedatacreater);
            mapStr.put("modedatacreatedate", modedatacreatedate);
            mapStr.put("modedatacreatetime", modedatacreatetime);
            mapStr.put("AmBeginTime", AmBeginTime);
            mapStr.put("AmEndTime", AmEndTime);
            mapStr.put("PmBeginTime", PmBeginTime);
            mapStr.put("PmEndTime", PmEndTime);
            mapStr.put("EffectiveStartDate", EffectiveStartDate);
            mapStr.put("EffectiveEndDate", EffectiveEndDate);
            mapStr.put("EmployeeName", EmployeeName);
            list.add(mapStr);
        }

        JSONArray ja = JSONArray.fromObject(list);
        return ja.toString();
    }
}