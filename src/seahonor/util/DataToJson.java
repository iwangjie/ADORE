package seahonor.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by adore on 16/9/12.
 */
public class DataToJson {
    public static String aa() throws Exception {
        JSONObject header = new JSONObject();
        JSONArray detail = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("HEADER", header);
        json.put("DETAIL", detail);
        header.put("IA", 11);
        header.put("haha", "cc");
        for (int i = 0; i < 2; i++) {
            JSONObject node = new JSONObject();
            node.put("thumb_path", "./Image/" + i + ".gif");
            node.put("flash_path", "./Image/" + i + ".gif");
            node.put("desc1", "可疑车辆" + i);
            node.put("desc2", "");
            node.put("desc3", "");
            node.put("desc4", "");
            node.put("title", "hello");
            node.put("upload_time", (new java.util.Date()).toString());
            node.put("uploader", "王二");
            detail.put(node);
        }

        return json.toString();
    }

    public static void main(String[] args) {
        DataToJson DTJ = new DataToJson();
        try {
            System.out.println("SUCCCESS="+DTJ.aa());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FAILED");
        }
    }

}
