package weaver.interfaces.shaw.gvo.bank;

public class BankAccServiceUtil {

    public String bankAccServiceMethod(String json) throws Exception{


        BankAccServiceWebServiceStub bswss = new BankAccServiceWebServiceStub();

        //BankAccServiceWebServiceStub.BankAccJKResponse bajkr = new BankAccServiceWebServiceStub.BankAccJKResponse();

        BankAccServiceWebServiceStub.BankAccJK bajk = new BankAccServiceWebServiceStub.BankAccJK();

        bajk.setJson(json);

        String result = bswss.bankAccJK(bajk).get_return();

        return result;

    }

    public static void main(String[] args){
        BankAccServiceUtil basu = new BankAccServiceUtil();
        String json = "{\"bean\":[{\"register_date\":\"2017-09-18\",\"acc_type\":\"02\",\"acc_name\":\"昆山国显光电有限公司\",\"corp_code\":\"1000\",\"bank_name\":\"建设银行\",\"is_online\":\"1\",\"bankacc\":\"1001xxx2001\",\"acc_type1\":\"02\",\"acc_state\":\"1\",\"acc_category\":\"01\",\"bank_type\":\"4000\",\"op_ression\":\"test\",\"cur\":\"usd\"}]}\n";
        String result = "";
        try {
            result = basu.bankAccServiceMethod(json);
            System.out.println("Result="+result);
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }
}
