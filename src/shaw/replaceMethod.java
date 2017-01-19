package shaw;

/**
 * Created by adore on 16/4/21.
 */
public class replaceMethod {

    public static void main(String[] args) {
       // TODO Auto-generated method stub
        String str = "111.3.22.11";
        str=str.replaceAll("(^|\\.)(\\d)(\\.|$)","$100$2$3");
        str=str.replaceAll("(^|\\.)(\\d{2})(\\.|$)","$10$2$3");
        str=str.replaceAll("(^|\\.)(\\d{2})(\\.|$)","$10$2$3");
        str=str.replaceAll("(^|\\.)(\\d{1})(\\.|$)","$100$2$3");
        str=str.substring(0,6);

        String subStr = "11.02.094";
        subStr = subStr.replace(".", "").substring(3,7);
        //String proj_process="市场报价,产品设计客户审核";
        //String proj_processid="NP10,NP09";
        //proj_processid=proj_processid.replaceAll("NP09","NP06");
        //proj_process=proj_process.replaceAll("产品设计客户审核","产品设计");
        System.out.println(subStr);
    }
}
