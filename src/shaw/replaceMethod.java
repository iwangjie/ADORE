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

        String proj_process="�г�����,��Ʒ��ƿͻ����";
        String proj_processid="NP10,NP09";
        proj_processid=proj_processid.replaceAll("NP09","NP06");
        proj_process=proj_process.replaceAll("��Ʒ��ƿͻ����","��Ʒ���");
        System.out.println(proj_processid);
    }
}
