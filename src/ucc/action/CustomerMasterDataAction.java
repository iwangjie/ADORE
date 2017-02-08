package ucc.action;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class CustomerMasterDataAction implements Action {


    public String execute(RequestInfo info) {
        new BaseBean().writeLog("客户主数据OA-->SAPCustomerMasterDataAction");

        RecordSet rs = new RecordSet();
        RecordSet res = new RecordSet();
        //获取外部数据库的方法
        RecordSetDataSource rsd = new RecordSetDataSource("center");
        String sql = "";
        String tableName = "";
        String CardCode = "";//客户代码（主表）
        String Website = "";//省份（主表）
        String CardName = "";//客户名称（主表）
        String Phone1 = "";//电话1（主表）
        String Cellular = "";//移动电话(主表）
        String Addid = "";//身份证号（主表）
        String Notes = "";//地址（主表）
        String ShipPerson = "";//收货人（主表）
        String ShipAddr = "";//收货地址（主表）
        String U_EmpId = "";//业务员（主表）
        String ShipPhone = "";//收货人联系方式（主表）
        String City = "";//卸货地（主表）
        String Workcode = "";

        String lastname = "";//创建人ID的转换
        String sqr = "";//创建人
        int MSGContent_len = 0;//获取的json长度
        String MSGContent = "";

        StringBuffer sBuffer = new StringBuffer();

        String sql_1 = "select * from uf_kehu where id = " + info.getRequestid();
        //表单建模中action，要把requestid改为id即可以
        new BaseBean().writeLog("sql1---------" + sql_1);
        res.executeSql(sql_1);
        if (res.next()) {
            CardCode = Util.null2String(res.getString("khbm"));//获取客户代码（主表）
            Website = Util.null2String(res.getString("sf"));//获取客户代码（主表）
            CardName = Util.null2String(res.getString("fddbr"));//获取法定代表人（主表）
            Phone1 = Util.null2String(res.getString("lxdh"));//获取电话1（主表）
            Cellular = Util.null2String(res.getString("lxrsj"));//获取移动电话(主表）

            Addid = Util.null2String(res.getString("sfzh"));//获取身份证号（主表）
            Notes = Util.null2String(res.getString("dz"));//获取地址（主表）
            U_EmpId = Util.null2String(res.getString("modedatacreater"));//获取业务员工号（主表）
            ShipPerson = Util.null2String(res.getString("shr"));//获取收货人（主表）
            ShipAddr = Util.null2String(res.getString("shdz"));//获取收货地址（主表)
            ShipPhone = Util.null2String(res.getString("shrlxfs"));//获取收货人联系方式（主表）
            City = Util.null2String(res.getString("xhd"));//获取卸货地（主表）

            sBuffer.append("{");
            sBuffer.append('"');
            sBuffer.append("BOM");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append("{");
            sBuffer.append('"');
            sBuffer.append("BO");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append("{");
            sBuffer.append('"');
            sBuffer.append("AdmInfo");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append("{");
            sBuffer.append('"');
            sBuffer.append("Object");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append('"');
            sBuffer.append("2");
            sBuffer.append('"');
            sBuffer.append(",");
            sBuffer.append('"');
            sBuffer.append("Version");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append('"');
            sBuffer.append("2");
            sBuffer.append('"');
            sBuffer.append("}");
            sBuffer.append(",");
            sBuffer.append('"');
            sBuffer.append("BusinessPartners");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append("{");
            sBuffer.append('"');
            sBuffer.append("row");
            sBuffer.append('"');
            sBuffer.append(":");

            sBuffer.append("{\"CardCode\":\"");
            sBuffer.append(CardCode);
            sBuffer.append("\",\"Website\":\"");
            sBuffer.append(Website);
            sBuffer.append("\",\"CardName\":\"");
            sBuffer.append(CardName);
            sBuffer.append("\",\"Phone1\":\"");
            sBuffer.append(Phone1);
            sBuffer.append("\",\"Cellular\":\"");
            sBuffer.append(Cellular);
            sBuffer.append("\",\"AdditionalID\":\"");
            sBuffer.append(Addid);
            sBuffer.append("\",\"Notes\":\"");
            sBuffer.append(Notes);
            sBuffer.append("\",\"U_EmpID\":\"");
            sBuffer.append(U_EmpId);
            sBuffer.append("\",\"CardType\":\"");
            sBuffer.append("cCustomer");
            sBuffer.append('"');
            sBuffer.append("}");
            sBuffer.append("}");
            sBuffer.append(",");

            sBuffer.append('"');
            sBuffer.append("BPAddresses");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append("{");
            sBuffer.append('"');
            sBuffer.append("row");
            sBuffer.append('"');
            sBuffer.append(":");
            sBuffer.append("[");
            sBuffer.append("{");
            sBuffer.append("\"AddressType\":\"");
            sBuffer.append("bo_ShipTo");
            sBuffer.append("\",\"AddressName\":\"");
            sBuffer.append(ShipPerson);
            sBuffer.append("\",\"Street\":\"");
            sBuffer.append(ShipAddr);
            sBuffer.append("\",\"Block\":\"");
            sBuffer.append(ShipPhone);
            sBuffer.append("\",\"City\":\"");
            sBuffer.append(City);
            sBuffer.append('"');
            sBuffer.append("}");
            sBuffer.append("]}}}}");

            MSGContent = sBuffer.toString();
            MSGContent_len = MSGContent.length();

            //记得这里的id后要用+加起来，因为sqr是变量
            String sql_name = "select workcode from hrmresource where id ='" + U_EmpId + "'";
            rs.executeSql(sql_name);
            if (rs.next()) {
                Workcode = Util.null2String(rs.getString("workcode"));
            }
            String sql_insert = "insert into IF_OMSG_OA (CreateDate,MSGObjType,MSGObjDesc,MSGTransType," +
                    "MSGKeyFields,MSGFieldsValue,MSGLength,MSGContent,Status,Creator) values(GETDATE(),'2','客户主数据','U','CardCode','" + CardCode + "','" + MSGContent_len + "','" + MSGContent + "','0','" + Workcode + "')";
            new BaseBean().writeLog("sql_insert---------" + sql_insert);
            //由于插入的是别的库里面，所以用rsd方法
            if (!rsd.executeSql(sql_insert)) {

                new BaseBean().writeLog("资源状态更新失败");
                return "-1";

            }
        }
        //}

        return SUCCESS;
    }
}