package weaver.interfaces.shaw.gvo.pay;

import weaver.general.BaseBean;
import weaver.interfaces.shaw.gvo.bank.BankAccServiceUtil;

/***
 * Created by adore on 17/9/18
 * 资金对接付款通用方法
 *
 * ***/
public class HnPayServiceUtil {

    public String hnPayService(String json) throws Exception {

        HnPayWebServiceStub hwss = new HnPayWebServiceStub();
        HnPayWebServiceStub.HnPayWS hpw = new HnPayWebServiceStub.HnPayWS();
        hpw.setXmlPay(json);

        String response = hwss.hnPayWS(hpw).get_return();
        return response;
    }

    public static void main(String[] args){
        HnPayServiceUtil hpsu = new HnPayServiceUtil();
        String json = "{\"bean\":   \n" +
                "\t[\n" +
                "\t\t{\n" +
                "\t\t\t\"serial_no_sap\":\"20170919000001\",\n" +
                "\t\t\t\"wish_payday\":\"2019-09-19\",\n" +
                "\t\t\t\"corp_code\":\"2017\",\n" +
                "\t\t\t\"payer_acc_no\":\"1234567890\",\n" +
                "\t\t\t\"amt\":\"1000.00\",\n" +
                "\t\t\t\"cur\":\"01\",\n" +
                "\t\t\t\"payee_acc_no\":\"0987654321\",\n" +
                "\t\t\t\"payee_name\":\"深圳市拜特科技股份有限公司\",\n" +
                "\t\t\t\"payee_bank\":\"中国农业银行漕溪路支行\",\n" +
                "\t\t\t\"payee_code\":\"2089271\",\n" +
                "\t\t\t\"abs\":\"代发报销款\",\n" +
                "\t\t\t\"urgency_flag\":\"1\",\n" +
                "\t\t\t\"voucher_type\":\"0\",\n" +
                "\t\t\t\"isforindividual\":\"1\",\n" +
                "\t\t\t\"item_code\":\"0987\",\n" +
                "\t\t\t\"gysdm\":\"kkk\",\n" +
                "\t\t\t\"zzbs\":\"jjj\",\n" +
                "\t\t\t\"fkyydm\":\"hhh\",\n" +
                "\t\t\t\"rmk\":\"备注\",\n" +
                "\t\t\t\"purpose\":\"代发报销款\",\n" +
                "\t\t\t\"zzkm\":\"zzzz\",\n" +
                "\t\t\t\"jzdm\":\"ffff\",\n" +
                "\t\t\t\"system_type\":\"OA\",\n" +
                "\t\t\t\"req_date\":\"2017-09-18\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
        String result = "";
        try {
            result = hpsu.hnPayService(json);
            System.out.println("Result="+result);
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }
}
