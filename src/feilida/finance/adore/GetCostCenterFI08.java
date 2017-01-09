package feilida.finance.adore;

import org.json.JSONException;
import weaver.general.BaseBean;

/**
 * Created by adore on 2016/12/27.
 * 获取成本中心和部门匹配关系
 */
public class GetCostCenterFI08 {
    BaseBean log = new BaseBean();

    public String getResult(String workflowId, String action, String compCode) {
        java.util.Map<String, String> oaDatas = new java.util.HashMap<String, String>();
        oaDatas.put("I_ACTION", action);
        oaDatas.put("I_BUKRS", compCode);
        tmc.BringMainAndDetailByMain bmb = new tmc.BringMainAndDetailByMain();
        String result = bmb.getReturn(oaDatas, workflowId);
        return result;
    }

    public String getCostCenter(String compCode, String deptName) {
        String result = getResult("17", "B", compCode);
        StringBuffer buffer = new StringBuffer();
        try {
            org.json.JSONObject json = new org.json.JSONObject(result);
            org.json.JSONArray jsonArr = json.getJSONObject("table").getJSONArray("main");
            for (int index = 0; index < jsonArr.length(); index++) {
                org.json.JSONObject jsonx = (org.json.JSONObject) (jsonArr.get(index));
                String KOSTL = jsonx.getString("KOSTL");//部门
                String LTEXT = jsonx.getString("LTEXT");//部门名称

                if (LTEXT.equals(deptName)) {
                    buffer.append(KOSTL);//部门
                } else {
                    buffer.append("");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
