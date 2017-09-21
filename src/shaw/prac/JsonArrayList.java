package shaw.prac;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArrayList {
    public static void main(String[] args){
        try {
            /**
             * 创建json对象并为该对象设置属性
             */
            JSONObject jsonObj = new JSONObject();
           //jsonObj.put("Int_att", 25);//添加int型属性
//            jsonObj.put("String_att", "str");//添加string型属性
//            jsonObj.put("Double_att", 12.25);//添加double型属性
//            jsonObj.put("Boolean_att", true);//添加boolean型属性
            //添加JSONObject型属性
            JSONObject jsonObjSon = new JSONObject();
            jsonObjSon.put( "CORP_CODE", "1000");
            jsonObjSon.put("BANKACC","1001xxx2001");
            jsonObjSon.put("ACC_NAME","昆山国显光电有限公司");
            jsonObjSon.put("BANK_NAME", "建设银行");
            jsonObjSon.put("BANK_TYPE", "4000");
            jsonObjSon.put("ACC_CATEGORY", "01");
            jsonObjSon.put("ACC_TYPE1", "02");
            jsonObjSon.put("ACC_TYPE", "02");
            jsonObjSon.put("CUR", "USD");
            jsonObjSon.put("IS_ONLINE", "1");
            jsonObjSon.put("ACC_STATE", "1");
            jsonObjSon.put("REGISTER_DATE", "2017-09-18");
            jsonObjSon.put("OP_RESSION", "test");
            //jsonObj.put("JSONObject_att", jsonObjSon);
            //添加JSONArray型属性
            JSONArray jsonArray = new JSONArray();
//            String array0 = "123";
//            String array1 = "456";
//            String array2 = "789";
//            String array3 = "012";
//            jsonArray.put("array0");
//            jsonArray.put("array1");
//            jsonArray.put("array2");
//            jsonArray.put("array3");
            jsonArray.put(jsonObjSon);

            jsonObj.put("bt_bank_acc", jsonArray);
            System.out.println(jsonObj.toString().toLowerCase());
            //System.out.println("Int_att:" + jsonObj.getInt("Int_att"));
//            System.out.println("String_att:" + jsonObj.getString("String_att"));
//            System.out.println("Double_att:" + jsonObj.getDouble("Double_att"));
//            System.out.println("Boolean_att:" + jsonObj.getBoolean("Boolean_att"));
//            System.out.println("JSONObject_att:" + jsonObj.getJSONObject("JSONObject_att"));
//            System.out.println("JSONArray_att:" + jsonObj.getJSONArray("JSONArray_att"));
        }catch (Exception e){
            System.out.println("ERROR");
        }

    }
}
