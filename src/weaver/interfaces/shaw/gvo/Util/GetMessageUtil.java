package weaver.interfaces.shaw.gvo.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by adore on 17/9/19
 * 解析接口返回json
 **/

public class GetMessageUtil {
    public String getStatus(String json) {
        String status = "";
        try {
            JSONArray jsonarray = new JSONArray(json);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobj = jsonarray.getJSONObject(i);
                status = jsonobj.getString("status");
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        return status;
    }

    public String getMessage(String json) {
        String message = "";
        try {
            JSONArray jsonarray = new JSONArray(json);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobj = jsonarray.getJSONObject(i);
                message = jsonobj.getString("message");
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        return message;
    }
}
