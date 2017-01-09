package feilida.finance;

import feilida.util.CptBudgetUtil;
import feilida.util.serviceNew.BudgetVO;
import weaver.general.Util;

import java.io.IOException;
import java.util.List;

/**
 * Created by adore on 2016/12/29.
 * 获取预算信息和成本中心
 */
public class FlGetCptInfo {
    //获取预算信息和成本中心
    public StringBuffer getCptInfo(String OADATE, String AMOUNT, String KOSTL, String KSTAR, String GSBER, String CURRENCY, String EXECTYPE
            , String OPTTYPE, int STAFFID, int COMPID, int DEPTID, String ANLKL, String OAKEY) throws IOException {

        StringBuffer buffer = new StringBuffer();
        CptBudgetUtil cbu = new CptBudgetUtil();
        List<BudgetVO> list = cbu.getBudgetResponse(OADATE, AMOUNT, KOSTL, KSTAR, GSBER, CURRENCY, EXECTYPE, OPTTYPE, STAFFID, COMPID, DEPTID, ANLKL, OAKEY);

        buffer.append("{");
        buffer.append("AMOUNT0");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getAMOUNT0());
        buffer.append("'");
        buffer.append(",");
        buffer.append("AMOUNT1");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getAMOUNT1());
        buffer.append("'");
        buffer.append(",");
        buffer.append("AMOUNT2");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getAMOUNT2());
        buffer.append("'");
        buffer.append(",");
        buffer.append("AMOUNT3");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getAMOUNT3());
        buffer.append("'");
        buffer.append("KOSTL");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getKOSTL());
        buffer.append("'");
        buffer.append("MSGTYP");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getMSGTYP());
        buffer.append("'");
        buffer.append("MSGTXT");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getMSGTXT());
        buffer.append("'");
        buffer.append("GPKEY");
        buffer.append(":");
        buffer.append("'");
        buffer.append(list.get(0).getGPKEY());
        buffer.append("'");
        buffer.append("}");
        return buffer;

    }

    public static void main(String[] args) {

        FlGetCptInfo gci = new FlGetCptInfo();
        String OADATE = "2017-12-21";
        String AMOUNT = "10";
        String KOSTL = "1100C00010";
        String KSTAR = "6601501001";
        String GSBER = "1002";
        String CURRENCY = "";
        String EXECTYPE = "1";
        String OPTTYPE = "";
        int STAFFID = 1;
        int COMPID = 950;
        int DEPTID = 7817;
        String ANLKL = "00210020";
        String OAKEY = "";
        //gci.getCptInfo();

        try {
            StringBuffer json = new StringBuffer();
            json = gci.getCptInfo(OADATE, AMOUNT, KOSTL, KSTAR, GSBER, CURRENCY, EXECTYPE, OPTTYPE, STAFFID, COMPID, DEPTID, ANLKL, OAKEY);
            System.out.println(json.toString());
        }
        catch(IOException e) {
            System.out.println("Exception encountered: " + e);
        }

    }
}
