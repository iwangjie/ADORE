package feilida.util;

import feilida.cpt.ECologyServiceStub;
import feilida.cpt.ECologyServiceStub.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.axis2.AxisFault;

/**
 * Created by adore on 2016/12/30.
 */
public class CptBudgetUtilNew {


    public BudgetVO getBudgetResponse(String OADATE, String AMOUNT, String KOSTL, String KSTAR, String GSBER, String CURRENCY, String EXECTYPE
            , String OPTTYPE, int STAFFID, int COMPID, int DEPTID, String ANLKL, String OAKEY) throws Exception{
        BudgetVO list = null;
        try {
            ECologyServiceStub ess = new ECologyServiceStub();
            BudgetVO bv = new ECologyServiceStub.BudgetVO();
            ArrayOfBudgetVO ab = new ECologyServiceStub.ArrayOfBudgetVO();
            BigDecimal amount = new BigDecimal(Double.valueOf(AMOUNT));
            bv.setAMOUNT(amount);
            bv.setKSTAR(KSTAR);
            bv.setOADATE(str2Calendar(OADATE));
            bv.setKOSTL(KOSTL);
            bv.setGSBER(GSBER);
            bv.setOPTTYPE(OPTTYPE);
            bv.setSTAFFID(STAFFID);
            bv.setCOMPID(COMPID);
            bv.setCURRENCY(CURRENCY);
            bv.setDEPTID(DEPTID);
            bv.setEXECTYPE(EXECTYPE);
            bv.setANLKL(ANLKL);
            bv.setOAKey(OAKEY);
            bv.setGPKEY("");
            bv.setAMOUNT0(new BigDecimal("0"));
            bv.setAMOUNT1(new BigDecimal("0"));
            bv.setAMOUNT2(new BigDecimal("0"));
            bv.setAMOUNT3(new BigDecimal("0"));
            QueryBudget qb = new ECologyServiceStub.QueryBudget();
            ab.addBudgetVO(bv);
            qb.setBuget(ab);
            QueryBudgetResponse qbr = ess.QueryBudget(qb);

            ArrayOfBudgetVO xxx= qbr.getQueryBudgetResult();
            BudgetVO[] aa=xxx.getBudgetVO();
            //BudgetVO aaa = aa[0];
            list = aa[0];

            //result = qbr.getQueryBudgetResult().getBudgetVO().toString();
            //System.out.println("xxx="+aaa.getANLKL());
            return list;
        } catch (AxisFault e) {
            e.printStackTrace();
            return list;
        }
    }

    public static Calendar str2Calendar(String str) {
        //str="2012-5-27";
        Calendar calendar = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
/*
    public static void main(String[] args) throws Exception{

        CptBudgetUtilNew gci = new CptBudgetUtilNew();
        String OADATE = "2016-12-21";
        String AMOUNT = "10";
        String KOSTL = "1100C00010";
        String KSTAR = "6601501001";
        String GSBER = "1002";
        String CURRENCY = "";
        String EXECTYPE = "1";
        String OPTTYPE = "0";
        int STAFFID = 1;
        int COMPID = 1;
        int DEPTID = 1;
        String ANLKL = "00110030";
        String OAKEY = "";
        String AMOUNT0="0";


        BudgetVO json = gci.getBudgetResponse(OADATE, AMOUNT, KOSTL, KSTAR, GSBER, CURRENCY, EXECTYPE, OPTTYPE, STAFFID, COMPID, DEPTID, ANLKL, OAKEY);
        System.out.println("AMOUNT0="+json.getAMOUNT0());
        System.out.println("AMOUNT0="+json.getMSGTYP());
        System.out.println("AMOUNT0="+json.getGPKEY());
        System.out.println("AMOUNT0="+json.getMSGTXT());
        System.out.println("AMOUNT0="+json.getKOSTL());
    }
*/
}
