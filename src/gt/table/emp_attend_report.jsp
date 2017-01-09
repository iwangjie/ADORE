<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.conn.RecordSet"%>
<%@ page import="weaver.general.BaseBean"%>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ include file="/systeminfo/init.jsp"%>
<jsp:useBean id="DepartmentComInfo"
	class="weaver.hrm.company.DepartmentComInfo" scope="page" />
<jsp:useBean id="ResourceComInfo"
	class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<html>
<head>
<script type="text/javascript" src="/js/weaver.js"></script>
<link rel="stylesheet" type="text/css" href="/css/Weaver.css">
</head>
<%
	int emp_id = user.getUID();
	String sub_com = ResourceComInfo.getSubCompanyID("" + emp_id);
	int pagenum = Util.getIntValue(request.getParameter("pagenum"), 1);
	int perpage = 10;
	String cjkc_name = Util.null2String(request
			.getParameter("cjkc_name"));
	String imagefilename = "/images/hdDOC.gif";
	String titlename = "考勤数据统计";
	String needfav = "1";
	String needhelp = "";
%>
<BODY>
	<%@ include file="/systeminfo/TopTitle.jsp"%>
	<%@ include file="/systeminfo/RightClickMenuConent.jsp"%>
	<%
		RCMenu += "{" + SystemEnv.getHtmlLabelName(197, user.getLanguage())
				+ ",javascript:document.weaver.submit(),_top} ";
		RCMenuHeight += RCMenuHeightStep;
	%>
	<%@ include file="/systeminfo/RightClickMenu.jsp"%>
	<table width=100% height=100% border="0" cellspacing="0"
		cellpadding="0">
		<colgroup>
			<col width="10">
			<col width="">
			<col width="10">
		<tr>
			<td height="10" colspan="3"></td>
		</tr>
		<tr>
			<td></td>
			<td valign="top">
				<TABLE class=Shadow>
					<tr>
						<td valign="top">
							<FORM id=weaver name=weaver STYLE="margin-bottom: 0" action=""
								method="post">
								<input type="hidden" name="multiRequestIds" value=""> <input
									type="hidden" name="operation" value="">
								<table width=100% class=ViewForm>
									<colgroup>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
										<col width="10%"></col>
									</colgroup>
									<tr style="height: 1px;">
										<td class=Line colspan=11></td>
									</tr>
								</table>
								<TABLE width="100%">
									<tr>
										<td valign="top">
											<%
												String backfields = " (select lastname from HrmResource where id = emp_id) as emp_name,atten_day,"
														+ " normal_start_time,normal_end_time,atten_start_time,atten_end_time,"
														+ "case out_type when 0 then '请假' when 1 then '出差' else '正常' end as outname,"
														+ "case card_status when 1 then '上午忘打卡' when 2 then '下午忘打卡' else '正常' end "
														+ " as cardname,case normal_status when 0 then '正常' else '异常' end as normalname  ";
												String fromSql = " from emp_work_all_atten_info ";
												String sqlWhere = " 1=1 ";

												//if(!"".equals(cjkc_name)){
												//cjkc_name = cjkc_name.trim();
												//sqlWhere += "and f.sqcjkc in (select id from formtable_main_193 where kcmc like '%"+cjkc_name+"%')";
												//}
												//if(!"".equals(resourceid)){
												//sqlWhere += "and za.operateuserid ="+resourceid+" ";
												//}
												// out.println("select "+ backfields + fromSql + " where " + sqlWhere);
												out.println(sqlWhere);
												String orderby = " pxrq ";
												String tableString = "";
												tableString = " <table  tabletype=\"none\" pagesize=\""
														+ perpage
														+ "\" >"
														+ "	   <sql backfields=\""
														+ backfields
														+ "\" sqlform=\""
														+ fromSql
														+ "\" sqlwhere=\""
														+ Util.toHtmlForSplitPage(sqlWhere)
														+ "\" sqlorderby=\""
														+ orderby
														+ "\" sqlprimarykey=\"id\" sqlsortway=\"desc\" />"
														+ "	<head>"
														+ " 	<col width=\"29%\" text=\"培训组织方\" column=\"zzf_name\" orderkey=\"zzf_name\"  />"
														+ " 	<col width=\"7%\" text=\"日期\" column=\"pxrq\" orderkey=\"pxrq\"  />"
														+ "   <col width=\"40%\" text=\"课程名称\" column=\"kc_name\" orderkey=\"kc_name\"  />"
														+ "	<col width=\"6%\" text=\"讲师名称\" column=\"js_name\" orderkey=\"js_name\" transmethod=\"weaver.hrm.resource.ResourceComInfo.getLastname\" />"
														+ " 				<col width=\"6%\" text=\"课时(Hr)\" column=\"ks\" orderkey=\"ks\"  />"
														+ " 				<col width=\"6%\" text=\"申请人\" column=\"sqr_name\" orderkey=\"sqr_name\"  />"
														+ " 				<col width=\"6%\" text=\"所在公司\" column=\"gs_name\" orderkey=\"gs_name\"  />"
														+ "			</head>" + "</table>";
											%> <wea:SplitPageTag tableString="<%=tableString%>"
												mode="run" showExpExcel="true" />
										</td>
									</tr>
								</TABLE>
							</FORM>
						</td>
					</tr>
				</TABLE>
			</td>
			<td></td>
		</tr>
		<tr>
			<td height="10" colspan="3"></td>
		</tr>
	</table>
	<script type="text/javascript">
		function onShowResourceID(inputname, spanname) {
			var opts = {
				_dwidth : '550px',
				_dheight : '550px',
				_url : 'about:blank',
				_scroll : "no",
				_dialogArguments : "",
				_displayTemplate : "#b{name}",
				_displaySelector : "",
				_required : "no",
				_displayText : "",
				value : ""
			};
			var iTop = (window.screen.availHeight - 30 - parseInt(opts._dheight))
					/ 2 + "px"; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth - 10 - parseInt(opts._dwidth))
					/ 2 + "px"; //获得窗口的水平位置;
			opts.top = iTop;
			opts.left = iLeft;
			datas = window
					.showModalDialog(
							"/systeminfo/BrowserMain.jsp?url=/hrm/resource/ResourceBrowser.jsp",
							"", "addressbar=no;status=0;scroll=" + opts._scroll
									+ ";dialogHeight=" + opts._dheight
									+ ";dialogWidth=" + opts._dwidth
									+ ";dialogLeft=" + opts.left
									+ ";dialogTop=" + opts.top
									+ ";resizable=0;center=1;");
			if (datas) {
				if (datas.id != "") {
					$("#" + spanname).html(
							"<A href='javascript:openhrm(" + datas.id
									+ ");' onclick='pointerXY(event);'>"
									+ datas.name + "</A>");
					$("input[name=" + inputname + "]").val(datas.id);
				} else {
					$("#" + spanname).html("");
					$("input[name=" + inputname + "]").val("");
				}
			}
		}
	</script>
	<SCRIPT language="javascript" defer="defer" src="/js/datetime.js"></script>
	<SCRIPT language="javascript" defer="defer"
		src="/js/JSDateTime/WdatePicker.js"></script>
</BODY>
</HTML>