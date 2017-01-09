package GoldHongYe.tmc;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * Created by adore on 16/6/2.
 */
public class emailAlarmInfo {
    public String getAlarmInfo(String is_send){
        StringBuffer buff = new StringBuffer();
        RecordSet recordSet = new RecordSet();
        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        String sql_group=" SELECT RECEIPT_NO,count(RECEIPT_NO) as num_rec FROM UF_ALARM_INFO GROUP BY RECEIPT_NO ";
        rs.executeSql(sql_group);
        while(rs.next()) {
            String RECEIPT=Util.null2String(rs.getString("RECEIPT_NO"));
            String num_rec=Util.null2String(rs.getString("num_rec"));

            if(!"".equals(RECEIPT)) {

                String sql = " SELECT  Channel,Area,Province,Receipt_NO,KFNAME,Actual_VAL,Target_val,Acheive,A_level,Duration,R_Level  "
                        +" FROM UF_ALARM_INFO where RECEIPT_NO='"+RECEIPT+"' ";
                String Channel = "";
                String Area = "";
                String Province = "";
                String Receipt_NO = "";
                String Duration = "";
                String R_Level = "";
                recordSet.executeSql(sql);
                if(recordSet.next()) {
                    Channel = Util.null2String(recordSet.getString("Channel"));
                    Area = Util.null2String(recordSet.getString("Area"));
                    Province = Util.null2String(recordSet.getString("Province"));
                    Receipt_NO = Util.null2String(recordSet.getString("Receipt_NO"));
                    Duration = Util.null2String(recordSet.getString("Duration"));
                    R_Level = Util.null2String(recordSet.getString("R_Level"));

                    buff.append("期间:"+Duration);
                    buff.append("责任层级:"+R_Level);
                    buff.append("责任单位:"+Province);
                    buff.append("<table id=\"alarm-table\" border=\"3\" style=\"width: 910px;border-collapse: collapse;font-size:12px;\"> ");
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

                    buff.append("<tr>");
                    buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:top;width:100px;padding: 5px;\">");
                    buff.append(Channel);
                    buff.append("</td>");

                    buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:top;width:100px;padding: 5px;\">");
                    buff.append(Area);
                    buff.append("</td>");

                    buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:top;width:100px;padding: 5px;\">");
                    buff.append(Province);
                    buff.append("</td>");

                    buff.append("<td rowspan=\"" + num_rec + "\" style=\"vertical-align:top;width:100px;padding: 5px;\">");
                    buff.append(Receipt_NO);
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
}
