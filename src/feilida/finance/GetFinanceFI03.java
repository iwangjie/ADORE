package feilida.finance;

import org.json.JSONException;

import weaver.general.BaseBean;

public class GetFinanceFI03 {
    BaseBean log = new BaseBean();

    public String getResult(String workflowId, String action, String code, String custom, String date) {
        java.util.Map<String, String> oaDatas = new java.util.HashMap<String, String>();
        oaDatas.put("I_ACTION", action);
        oaDatas.put("I_KUNNR", code);
        oaDatas.put("I_BUKRS", custom);
        oaDatas.put("I_ZFBDT", date);
        tmc.BringMainAndDetailByMain bmb = new tmc.BringMainAndDetailByMain("81");
        String result = bmb.getReturn(oaDatas, workflowId);
        return result;
    }


    public String getDetialinfo(String code, String custom, String date) {
        String result = getResult("11", "J", code, custom, date);
        StringBuffer sb = new StringBuffer();
        try {
            org.json.JSONObject json = new org.json.JSONObject(result);
            org.json.JSONArray jsonArr = json.getJSONObject("table").getJSONArray("main");
            for (int index = 0; index < jsonArr.length(); index++) {
                org.json.JSONObject jsonx = (org.json.JSONObject) (jsonArr.get(index));
                //String BUKRS = jsonx.getString("BUKRS").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//公司代码
                String KUNNR = jsonx.getString("KUNNR").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//客户编号
                //String UMSKS = jsonx.getString("UMSKS").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//特殊总分类帐事务类型
                //String UMSKZ = jsonx.getString("UMSKZ").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//特别总账标识
                //String AUGDT = jsonx.getString("AUGDT").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//清帐日期
                //String AUGBL = jsonx.getString("AUGBL").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//清算单据的单据号码
                //String ZUONR = jsonx.getString("ZUONR").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//分配编号
                // GJAHR = jsonx.getString("GJAHR").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//财年
                String BELNR = jsonx.getString("BELNR").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//会计凭证号码
                //String BUZEI  = jsonx.getString("BUZEI").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//会计凭证中的行项目数

                String BUDAT = jsonx.getString("BUDAT").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//凭证中的过帐日期
                //String BLDAT = jsonx.getString("BLDAT").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//凭证中的发票日期
                //String CPUDT = jsonx.getString("CPUDT").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//会计凭证输入日期
                String WAERS = jsonx.getString("WAERS").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//货币码
                //String XBLNR = jsonx.getString("XBLNR").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//参考凭证编号
                //String BLART = jsonx.getString("BLART").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//凭证类型
                //String MONAT = jsonx.getString("MONAT").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//会计期间
                //String BSCHL = jsonx.getString("BSCHL").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//记帐代码
                //String ZUMSK = jsonx.getString("ZUMSK").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//目标特别总帐标志
                //String SHKZG  = jsonx.getString("SHKZG").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//借方/贷方标识

                //String GSBER = jsonx.getString("GSBER").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//业务范围
                //String MWSKZ = jsonx.getString("MWSKZ").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//销售/购买税代码
                //String DMBTR = jsonx.getString("DMBTR").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//按本位币计的金额
                String WRBTR = jsonx.getString("WRBTR").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//凭证货币金额
                String SGTXT = jsonx.getString("SGTXT").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//项目文本
                String ZFBDT = jsonx.getString("ZFBDT").replace("'", "'||chr(39)||'").replace("&", "'||chr(38)||'");//用于到期日计算的基准日期
                //String ZTERM = jsonx.getString("ZTERM").replace("'","'||chr(39)||'").replace("&","'||chr(38)||'");//收付条件代码
                sb.append(KUNNR);//客户编号
                sb.append("###");
                sb.append(BELNR);    //凭证编号
                sb.append("###");
                sb.append(BUDAT);//凭证日期
                sb.append("###");
                sb.append(ZFBDT);//到期日
                sb.append("###");
                sb.append(WRBTR);//金额
                sb.append("###");
                sb.append(WAERS);//币种
                sb.append("###");
                sb.append(SGTXT);//文本
                sb.append("@@@");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

}
