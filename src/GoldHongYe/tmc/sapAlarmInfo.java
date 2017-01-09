package GoldHongYe.tmc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seahonor.util.InsertUtil;
import weaver.general.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adore on 16/6/3.
 */
public class sapAlarmInfo {

        public String getSapAlarmInfo(String alermID){
            String workflowId = "2";
            java.util.Map<String, String> oaDatas = new java.util.HashMap<String, String>();
            oaDatas.put("ID", alermID);

            GoldHongYe.tmc.BringMainAndDetailByMain bmb = new GoldHongYe.tmc.BringMainAndDetailByMain();
            String result = bmb.getReturn(oaDatas,workflowId);
            return result;
        }

        public String getAlarmInfo(String is_send){
            StringBuffer buff = new StringBuffer();
            weaver.conn.RecordSet recordSet = new  weaver.conn.RecordSet();
            weaver.conn. RecordSet rs = new  weaver.conn.RecordSet();
            weaver.conn.RecordSet res = new  weaver.conn.RecordSet();
            String sql_group=" SELECT RECEIPT_NO,count(RECEIPT_NO) as num_rec FROM UF_ALARM_INFO GROUP BY RECEIPT_NO ";
            rs.executeSql(sql_group);
            while(rs.next()) {
                String RECEIPT= Util.null2String(rs.getString("RECEIPT_NO"));
                String num_rec= Util.null2String(rs.getString("num_rec"));

                if(!"".equals(RECEIPT)) {
                    buff.append("<table id=\"alarm-table\" border=\"2\"  style=\"width: 910px;border-collapse: collapse;font-size:12px;\"> ");
                    buff.append("<tr style=\"background: blue;\">\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">渠道</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">销售大区</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">销售省办</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">省办经理</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">预警KPI</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">实际</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">目标</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">达成率</th>\n" +
                            "                     <th  style=\"color: white;width:100px;padding: 5px;\">预警层级</th> " +
                            "    </tr>");

                    String sql = " SELECT  Channel,Area,Province,Receipt_NO,RPTDESC,KFNAME,Actual_VAL,Target_val,Acheive,A_level FROM UF_ALARM_INFO"
                            +" where RECEIPT_NO='"+RECEIPT+"' ";
                    String Channel = "";
                    String Area = "";
                    String Province = "";
                    String Receipt_NO = "";
                    String RPTDESC="";
                    recordSet.executeSql(sql);
                    if(recordSet.next()) {
                        Channel = Util.null2String(recordSet.getString("Channel"));
                        Area = Util.null2String(recordSet.getString("Area"));
                        Province = Util.null2String(recordSet.getString("Province"));
                        Receipt_NO = Util.null2String(recordSet.getString("Receipt_NO"));
                        RPTDESC = Util.null2String(recordSet.getString("RPTDESC"));

                        buff.append("<tr>");
                        buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:middle;width:100px;padding: 5px;\">");
                        buff.append(Channel);
                        buff.append("</td>");

                        buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:middle;width:100px;padding: 5px;\">");
                        buff.append(Area);
                        buff.append("</td>");

                        buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:middle;width:100px;padding: 5px;\">");
                        buff.append(Province);
                        buff.append("</td>");

                        buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:middle;width:100px;padding: 5px;\">");
                        buff.append(RPTDESC);
                        buff.append("</td>");
                    }

                    res.executeSql(sql);
                    while (res.next()) {
                        String KFNAME = Util.null2String(res.getString("KFNAME"));
                        String Actual_VAL = Util.null2String(res.getString("Actual_VAL"));
                        String Target_val = Util.null2String(res.getString("Target_val"));
                        String Acheive = Util.null2String(res.getString("Acheive"));
                        String A_level = Util.null2String(res.getString("A_level"));

                        buff.append("<td style=\"vertical-align:top;width:100px;padding: 5px;\">");
                        buff.append(KFNAME);
                        buff.append("</td>");

                        buff.append("<td style=\"vertical-align:top;width:100px;padding: 5px;\">");
                        buff.append(Actual_VAL);
                        buff.append("</td>");

                        buff.append("<td style=\"vertical-align:top;width:100px;padding: 5px;\">");
                        buff.append(Target_val);
                        buff.append("</td>");

                        buff.append("<td style=\"vertical-align:top;width:100px;padding: 5px;\">");
                        buff.append(Acheive);
                        buff.append("</td>");

                        buff.append("<td style=\"vertical-align:top;width:100px;padding: 5px;\">");
                        buff.append(A_level);
                        buff.append("</td>");

                        buff.append("</tr>");
                    }
                    buff.append("</table>");
                }
            }
            return buff.toString();
        }
    public String addOaAlarmInfo(String str){
        String result="";
        try {
            JSONObject json = new JSONObject(str);
            JSONArray jsonArr = json.getJSONObject("table").getJSONArray("main");
            weaver.conn.RecordSet rs = new weaver.conn.RecordSet();


            for(int index=0;index<jsonArr.length();index++){
                org.json.JSONObject jsonx = (org.json.JSONObject)(jsonArr.get(index));

                // json的值是 tmc_sap_mapping 的OA字段
                String KFNAME = jsonx.getString("KFNAME");
                String ACHEIVE = jsonx.getString("ACHEIVE");
                String S_LEVEL = jsonx.getString("S_LEVEL");
                String AREA = jsonx.getString("AREA");
                String RPTDESC = jsonx.getString("RPTDESC");
                String SENT_IND = jsonx.getString("SENT_IND");
                String ALARM_TIME = jsonx.getString("ALARM_TIME").substring(0,5);
                String APPROVAL_NO = jsonx.getString("APPROVAL_NO");
                String RS_MAIL = jsonx.getString("RS_MAIL");
                String ALARM_DAT = jsonx.getString("ALARM_DAT");
                String A_LEVEL = jsonx.getString("A_LEVEL");
                String CHANNEL = jsonx.getString("CHANNEL");
                String SALES_GRP = jsonx.getString("SALES_GRP");
                String R_LEVEL = jsonx.getString("R_LEVEL");
                String TARGET_VAL = jsonx.getString("TARGET_VAL");
                String CATEGORY = jsonx.getString("CATEGORY");
                String PROVINCE = jsonx.getString("PROVINCE");
                String CREAT_IND = jsonx.getString("CREAT_IND");
                String ALARM_ID = jsonx.getString("ALARM_ID");
                String KFCODE = jsonx.getString("KFCODE");
                String ACTUAL_VAL = jsonx.getString("ACTUAL_VAL");
                String RECEIPT_NO = jsonx.getString("RECEIPT_NO");
                String DURATION = jsonx.getString("DURATION");

                Map<String, String> mapStr = new HashMap<String, String>();
                mapStr.put("ALARM_ID", ALARM_ID);
                mapStr.put("KFNAME", KFNAME);
                mapStr.put("ACHEIVE", ACHEIVE);
                mapStr.put("S_LEVEL", S_LEVEL);
                mapStr.put("AREA", AREA);
                mapStr.put("RPTDESC", RPTDESC);
                mapStr.put("SENT_IND",SENT_IND );
                mapStr.put("ALARM_TIME", ALARM_TIME);
                mapStr.put("APPROVAL_NO",APPROVAL_NO);
                mapStr.put("ALARM_DAT",ALARM_DAT);
                mapStr.put("A_LEVEL", A_LEVEL);
                mapStr.put("RS_MAIL", RS_MAIL);
                mapStr.put("CHANNEL", CHANNEL);
                mapStr.put("SALES_GRP", SALES_GRP);
                mapStr.put("R_LEVEL", R_LEVEL);
                mapStr.put("TARGET_VAL",TARGET_VAL );
                mapStr.put("CATEGORY", CATEGORY);
                mapStr.put("PROVINCE",PROVINCE);
                mapStr.put("CREAT_IND",CREAT_IND);
                mapStr.put("KFCODE", KFCODE);
                mapStr.put("ACTUAL_VAL", ACTUAL_VAL);
                mapStr.put("RECEIPT_NO", RECEIPT_NO);
                mapStr.put("DURATION", DURATION);
                mapStr.put("is_plan", "0");
                mapStr.put("approveStatus", "0");
                mapStr.put("is_approve", "0");

                String tableName = "UF_ALARM_INFO";
                InsertUtil IU = new InsertUtil();
                IU.insert(mapStr,tableName);
                result="SUCCESS";
                //result=ALARM_TIME;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
