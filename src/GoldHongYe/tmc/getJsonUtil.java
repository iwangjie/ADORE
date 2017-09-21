package GoldHongYe.tmc;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by adore on 16/6/1.
 */
public class getJsonUtil {
    public static void jsonArrayTest() {
        try {
            JSONArray jsonarray = new JSONArray("[{'name':'xiazdong','age':20},{'name':'xzdong','age':15}]");
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobj = jsonarray.getJSONObject(i);
                String name = jsonobj.getString("name");
                int age = jsonobj.getInt("age");
                System.out.println("name = " + name + ",age = " + age);
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }


    public static void main(String[] args) {
        getJsonUtil.jsonArrayTest();
    }
}
