<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Map,java.util.HashMap" %>
<%@ page import="weaver.hrm.*" %>
<%@ page import="weaver.conn.RecordSet" %>
<%@ page import="weaver.general.*" %>
<%@ page import="weaver.general.BaseBean" %>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="bb" class="weaver.general.BaseBean" scope="page"/>
<jsp:useBean id="billFieldUtil" class="weaver.interfaces.shaw.BillFieldUtil" scope="page"/>
<%

    int formid = Util.getIntValue(request.getParameter("formid"));//表单id
    int requestid = Util.getIntValue(request.getParameter("requestid"));//requestid
    int workflowid = Util.getIntValue(request.getParameter("workflowid"));//requestid
    Map mainTableArray = new HashMap();//主表集合
    mainTableArray = billFieldUtil.getFieldId(formid, "0");// 得到主表的字段集合


%>
<script>
    jQuery(document).ready(function () {
        var name = jQuery("#field" +<%=mainTableArray.get("Name")%>).val();
        alert("name=" + name);
    });
</script>
 