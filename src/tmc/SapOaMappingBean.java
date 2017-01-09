package tmc;

/**
 * Created by adore on 16/6/17.
 */
public class SapOaMappingBean {
    public int id;
    public String workflowId;
    public String OAFieldName;
    public String OATableType;
    public String SAPFunctionName;
    public String SAPParameterType;
    public String SAPFieldName;
    public String SAPParameterName;
    public String SAPTableType;

    public SapOaMappingBean() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getOAFieldName() {
        return this.OAFieldName;
    }

    public void setOAFieldName(String oAFieldName) {
        this.OAFieldName = oAFieldName;
    }

    public String getSAPFunctionName() {
        return this.SAPFunctionName;
    }

    public void setSAPFunctionName(String sAPFunctionName) {
        this.SAPFunctionName = sAPFunctionName;
    }

    public String getSAPParameterType() {
        return this.SAPParameterType;
    }

    public void setSAPParameterType(String sAPParameterType) {
        this.SAPParameterType = sAPParameterType;
    }

    public String getSAPFieldName() {
        return this.SAPFieldName;
    }

    public void setSAPFieldName(String sAPFieldName) {
        this.SAPFieldName = sAPFieldName;
    }

    public String getSAPParameterName() {
        return this.SAPParameterName;
    }

    public void setSAPParameterName(String sAPParameterName) {
        this.SAPParameterName = sAPParameterName;
    }

    public String getOATableType() {
        return this.OATableType;
    }

    public void setOATableType(String oATableType) {
        this.OATableType = oATableType;
    }

    public String getSAPTableType() {
        return this.SAPTableType;
    }

    public void setSAPTableType(String sAPTableType) {
        this.SAPTableType = sAPTableType;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("id=");
        buff.append(this.id);
        buff.append(";workflowId=");
        buff.append(this.workflowId);
        buff.append(";OAFieldName=");
        buff.append(this.OAFieldName);
        buff.append(";OATableType=");
        buff.append(this.OATableType);
        buff.append(";SAPFunctionName=");
        buff.append(this.SAPFunctionName);
        buff.append(";SAPParameterType=");
        buff.append(this.SAPParameterType);
        buff.append(";SAPFieldName=");
        buff.append(this.SAPFieldName);
        buff.append(";SAPParameterName=");
        buff.append(this.SAPParameterName);
        buff.append(";SAPTableType=");
        buff.append(this.SAPTableType);
        return buff.toString();
    }

    public static void main(String[] args) {
        SapOaMappingBean smb = new SapOaMappingBean();
        smb.setId(12);
        smb.setOAFieldName("OAFieldName");
        smb.setOATableType("OATableType");
        smb.setSAPFieldName("SAPFieldName");
        smb.setSAPFunctionName("SAPFunctionName");
        smb.setSAPParameterName("SAPParameterName");
        smb.setSAPParameterType("SAPParameterType");
        smb.setSAPTableType("SAPTableType");
        smb.setWorkflowId("100");
        System.out.println(smb.toString());
    }
}
