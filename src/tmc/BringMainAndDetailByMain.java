package tmc;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.weaver.integration.datesource.SAPInterationOutUtil;
import com.weaver.integration.log.LogInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.*;

/**
 * Created by adore on 16/6/17.
 */
public class BringMainAndDetailByMain {
    public static BaseBean basebean = new BaseBean();
    private String dateid;

    public BringMainAndDetailByMain() {
        this.dateid = "1";
    }

    public BringMainAndDetailByMain(String dateid) {
        this.dateid = dateid;
    }

    public static String getFunctionNameByWorkflowID(String id) {
        String result = "";
        String sql = "select distinct SAPFunctionName from TMC_SAP_MAPPING where workflowId =" + id;
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        if(rs.next()) {
            result = rs.getString("SAPFunctionName");
        }

        return result;
    }

    public String getReturn(Map<String, String> oaDatas, String workflowID) {
        String formid = "-1";
        basebean.writeLog("---------------------start SAP OA MAPPING ----------------------------");
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonTable = new JSONObject();
        JSONObject jsonOther = new JSONObject();
        String errorSring = "";
        JCO.Client myConnection = null;

        String var23;
        try {
            myConnection = this.getSAPcon();
            myConnection.connect();
            JCO.Repository e = new JCO.Repository("Repository", myConnection);
            String functionName = getFunctionNameByWorkflowID(workflowID);
            IFunctionTemplate ft = e.getFunctionTemplate(functionName);
            JCO.Function bapi = ft.getFunction();
            JCO.ParameterList paraList = bapi.getImportParameterList();
            JCO.ParameterList inputtable = bapi.getTableParameterList();
            Set keys = oaDatas.keySet();
            Iterator it = keys.iterator();

            SapOaMappingBean mainlist;
            while(it.hasNext()) {
                String outPara = (String)it.next();
                String otherlist = (String)oaDatas.get(outPara);

                try {
                    mainlist = getInputParameter(workflowID, outPara);
                    String dtList = mainlist.getSAPFieldName();
                    if("inPara".equals(mainlist.getSAPParameterType())) {
                        paraList.setValue(otherlist, dtList);
                    } else if("inStr".equals(mainlist.getSAPParameterType())) {
                        JCO.Structure outValue = paraList.getStructure(mainlist.getSAPParameterName());
                        outValue.setValue(otherlist, dtList);
                    } else if("inTab".equals(mainlist.getSAPParameterType())) {
                        JCO.Table outValue1 = inputtable.getTable(mainlist.getSAPParameterName());
                        outValue1.setValue(otherlist, dtList);
                    }
                } catch (Exception var27) {
                    basebean.writeLog("放置SAP过程中出现错误，错误信息为：" + var27.getMessage());
                }
            }

            myConnection.execute(bapi);
            JCO.ParameterList outPara1 = bapi.getExportParameterList();
            List otherlist1 = getOutParameters(workflowID);
            Iterator dtList1 = otherlist1.iterator();

            while(dtList1.hasNext()) {
                mainlist = (SapOaMappingBean)dtList1.next();
                String outValue2 = outPara1.getString(mainlist.getSAPFieldName());
                jsonOther.put(mainlist.getSAPFieldName(), outValue2);
            }

            List mainlist1 = getOutParameters(workflowID, "main");
            List dtList2 = getOutParameters(workflowID, "dt");
            jsonObj.put("table", jsonTable);
            jsonTable.put("main", getReturnValues(bapi, mainlist1, formid));
            jsonTable.put("Detail", getReturnValues(bapi, dtList2, formid));
            jsonObj.put("msg", errorSring);
            jsonObj.put("type", jsonOther);
            var23 = jsonObj.toString();
            return var23;
        } catch (Exception var28) {
            var23 = var28.getMessage();
        } finally {
            releaseClient(myConnection);
            basebean.writeLog("---------------------end SAP OA MAPPING ----------------------------");
        }

        return var23;
    }

    private static JSONArray getReturnValues(JCO.Function bapi, List<SapOaMappingBean> list, String formid) throws JSONException {
        JSONArray arr = new JSONArray();
        JCO.ParameterList outTable = bapi.getTableParameterList();
        Iterator var6 = list.iterator();

        while(true) {
            while(var6.hasNext()) {
                SapOaMappingBean outBean = (SapOaMappingBean)var6.next();
                String outValue = "";
                JSONObject json;
                if("outPara".equals(outBean.getSAPParameterType())) {
                    JCO.ParameterList var14 = bapi.getExportParameterList();
                    outValue = var14.getString(outBean.getSAPFieldName());
                    String var15 = outBean.getOAFieldName();
                    var15 = getPageIDByOAField(formid, var15);
                    json = new JSONObject();
                    json.put(var15, outValue);
                    arr.put(arr.length() == 0?0:arr.length(), json);
                } else {
                    JCO.Table jt = outTable.getTable(outBean.getSAPParameterName());
                    if(jt.getNumRows() > 0) {
                        for(int i = 0; i < jt.getNumRows(); ++i) {
                            json = new JSONObject();
                            if(arr.length() == jt.getNumRows()) {
                                json = (JSONObject)arr.get(i);
                            }

                            jt.setRow(i);
                            outValue = jt.getString(outBean.getSAPFieldName());
                            String oaId;
                            if("dt".equals(outBean.getOATableType().substring(0, 2))) {
                                oaId = outBean.getOATableType();
                                JSONObject jStr;
                                String oaId1;
                                if(!json.has(oaId)) {
                                    jStr = new JSONObject();
                                    oaId1 = outBean.getOAFieldName();
                                    oaId1 = getPageIDByOAFieldDt(formid, oaId1);
                                    jStr.put(oaId1, outValue);
                                    json.put(oaId, jStr);
                                } else {
                                    jStr = (JSONObject)json.get(oaId);
                                    oaId1 = outBean.getOAFieldName();
                                    oaId1 = getPageIDByOAFieldDt(formid, oaId1);
                                    jStr.put(oaId1, outValue);
                                }
                            } else {
                                oaId = outBean.getOAFieldName();
                                oaId = getPageIDByOAField(formid, oaId);
                                json.put(oaId, outValue);
                            }

                            arr.put(i, json);
                        }
                    }
                }
            }

            return arr;
        }
    }

    public static String getPageIDByOAField(String formid, String fieldName) {
        return fieldName;
    }

    public static String getPageIDByOAFieldDt(String formid, String fieldName) {
        return fieldName;
    }

    public static List<SapOaMappingBean> getOutParameters(String workflowId, String OAFieldType) {
        ArrayList list = new ArrayList();
        String sql = "select *  from TMC_SAP_MAPPING where workflowid = " + workflowId + " and OATableType like \'" + OAFieldType + "%\'and  SAPParameterType like \'out%\'";
        RecordSet rs = new RecordSet();
        rs.execute(sql);

        while(rs.next()) {
            SapOaMappingBean bean = rowMapping(rs);
            list.add(bean);
        }

        return list;
    }

    public static List<SapOaMappingBean> getOutParameters(String workflowId) {
        ArrayList list = new ArrayList();
        String sql = "select *  from TMC_SAP_MAPPING where workflowid = " + workflowId + " and SAPParameterType = \'outPara\' and (OATableType<>\'main\' or OATableType is null)";
        RecordSet rs = new RecordSet();
        rs.execute(sql);

        while(rs.next()) {
            SapOaMappingBean bean = rowMapping(rs);
            list.add(bean);
        }

        return list;
    }

    public static SapOaMappingBean getInputParameter(String workflowId, String OaFieldName) {
        SapOaMappingBean bean = new SapOaMappingBean();
        String sql = "select *  from TMC_SAP_MAPPING where OAFieldName =\'" + OaFieldName + "\' and workflowid = " + workflowId + " and  SAPParameterType like \'in%\'";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        if(rs.next()) {
            bean = rowMapping(rs);
        }

        return bean;
    }

    public static List<String> getOAParaName(String workflowId) {
        ArrayList list = new ArrayList();
        String sql = "select OAFieldName from TMC_SAP_MAPPING where workflowId = " + workflowId + " and SAPParameterType in (\'inPara\',\'inStr\')";
        RecordSet rs = new RecordSet();
        rs.execute(sql);

        while(rs.next()) {
            list.add(rs.getString("OAFieldName"));
        }

        return list;
    }

    public static List<SapOaMappingBean> getRelation(String workflowId) {
        (new StringBuilder("select OAFieldName, SAPParameterType,SAPParameterName,SAPFieldName from TMC_SAP_MAPPING where workflowId = ")).append(workflowId).toString();
        String sql = "select * from TMC_SAP_MAPPING where workflowId = " + workflowId;
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        ArrayList list = new ArrayList();

        while(rs.next()) {
            list.add(rowMapping(rs));
        }

        return list;
    }

    private static SapOaMappingBean rowMapping(RecordSet rs) {
        SapOaMappingBean bean = new SapOaMappingBean();
        bean.setId(Util.getIntValue(String.valueOf(rs.getInt("id")), 0));
        bean.setOATableType(Util.null2String(rs.getString("oATableType")));
        bean.setSAPTableType(Util.null2String(rs.getString("sAPTableType")));
        bean.setOAFieldName(Util.null2String(rs.getString("OAFieldName")));
        bean.setSAPFieldName(Util.null2String(rs.getString("SAPFieldName")));
        bean.setSAPParameterType(Util.null2String(rs.getString("SAPParameterType")));
        bean.setSAPParameterName(Util.null2String(rs.getString("SAPParameterName")));
        bean.setSAPFunctionName(Util.null2String(rs.getString("sAPFunctionName")));
        bean.setWorkflowId(Util.null2String(rs.getString("WorkflowId")));
        return bean;
    }

    private JCO.Client getSAPcon() {
        BaseBean bean = new BaseBean();
        String datasourceid = bean.getPropValue("datasource", "datasourceid");
        datasourceid = this.dateid;
        basebean.writeLog("SAP数据源ID：" + datasourceid);
        SAPInterationOutUtil sapUtil = new SAPInterationOutUtil();
        JCO.Client myConnection = (JCO.Client)sapUtil.getConnection(datasourceid, new LogInfo());
        return myConnection;
    }

    public static void releaseClient(JCO.Client myConnection) {
        if(myConnection != null) {
            JCO.releaseClient(myConnection);
        }

    }
}
