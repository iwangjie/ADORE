<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="weaver.conn.*"%>
<%@ page import="weaver.conn.RecordSet"%>
<%@ page import="weaver.general.BaseBean"%>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ include file="/systeminfo/init_wev8.jsp"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
	String year_name = Util.null2String(request.getParameter("year_name"));
	String month_name = Util.null2String(request.getParameter("month_name"));
    String id = Util.fromScreen(request.getParameter("id"),user.getLanguage());
%>
<HTML>
	<HEAD>
		<LINK href="/css/Weaver.css" type=text/css rel=STYLESHEET>
		<script language=javascript src="/js/weaver.js"></script>
		<SCRIPT language="javascript" defer="defer" src="/js/datetime.js"></script>
		<SCRIPT language="javascript" defer="defer"
		src='/js/JSDateTime/WdatePicker.js?rnd="+Math.random()+"'></script>
	</head>
	<%
	String imagefilename = "/images/hdMaintenance_wev8.gif";
	String titlename = SystemEnv.getHtmlLabelName(21039,user.getLanguage())
	+ SystemEnv.getHtmlLabelName(480, user.getLanguage())
	+ SystemEnv.getHtmlLabelName(18599, user.getLanguage())
	+ SystemEnv.getHtmlLabelName(352, user.getLanguage());
	String needfav = "1";
	String needhelp = "";
	%>
	<BODY>
		<%@ include file="/systeminfo/TopTitle_wev8.jsp"%>
		<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp"%>
		<%
		RCMenu += "{" + SystemEnv.getHtmlLabelName(18599, user.getLanguage())+ ",javascript:doEdit(this),_TOP} ";
		RCMenuHeight += RCMenuHeightStep;
		%>
		<%@ include file="/systeminfo/RightClickMenu_wev8.jsp"%>
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
								<FORM id=weaver name=weaver method=post action="" >
									<input class=inputstyle type="hidden" name="authorize" value="">
									<table width=100% class=ViewForm>
										<colgroup>
										<col width="10%"></col>
										<col width="5%"></col>
										<col width="5%"></col>
										<col width="5%"></col>
										<col width="5%"></col>
										<col width="5%"></col>
										<col width="65%"></col>
										</colgroup>
										<tr>
											<td>查询年月</td>
											<td class=field><select id="year_name" name="year_name">
												<option value=""></option>
												<option value="2014" <%if ("2014".equals(year_name)) {%>selected<%}%>>2014</option>
												<option value="2015" <%if ("2015".equals(year_name)) {%>selected<%}%>>2015</option>
												<option value="2016" <%if ("2016".equals(year_name)) {%>selected<%}%>>2016</option>
												<option value="2017" <%if ("2017".equals(year_name)) {%>selected<%}%>>2017</option>
												<option value="2018" <%if ("2018".equals(year_name)) {%>selected<%}%>>2018</option>
												<option value="2019" <%if ("2019".equals(year_name)) {%>selected<%}%>>2019</option>
											</select></td>
											<td>年</td>
											<td class=field><select id="month_name" name="month_name">
												<option value=""></option>
												<option value="1" <%if ("1".equals(month_name)) {%>selected<%}%>>01</option>
												<option value="2" <%if ("2".equals(month_name)) {%>selected<%}%>>02</option>
												<option value="3" <%if ("3".equals(month_name)) {%>selected<%}%>>03</option>
												<option value="4" <%if ("4".equals(month_name)) {%>selected<%}%>>04</option>
												<option value="5" <%if ("5".equals(month_name)) {%>selected<%}%>>05</option>
												<option value="6" <%if ("6".equals(month_name)) {%>selected<%}%>>06</option>
												<option value="7" <%if ("7".equals(month_name)) {%>selected<%}%>>07</option>
												<option value="8" <%if ("8".equals(month_name)) {%>selected<%}%>>08</option>
												<option value="9" <%if ("9".equals(month_name)) {%>selected<%}%>>09</option>
												<option value="10" <%if ("10".equals(month_name)) {%>selected<%}%>>10</option>
												<option value="11" <%if ("11".equals(month_name)) {%>selected<%}%>>11</option>
												<option value="12" <%if ("12".equals(month_name)) {%>selected<%}%>>12</option>
											</select></td>
											<td>月</td>
											<td><input type="submit" value="查询"></td>
											<td>&nbsp;</td>
										</tr>
										<tr style="height: 1px;">
											<td class=Line colspan=11></td>
										</tr>
									</table>
									<TABLE class="altrowstable" style="width: 99.5%;margin-left: 0.2%;margin-top: -1px;font-size: 9pt">
										<colgroup>
										<!--<col width="11%"></col>
										<col width="11%"></col>-->
										<col width="11%"></col>
										<col width="11%"></col>
										<col width="11%"></col>
										<col width="11%"></col>
										<col width="11%"></col>
										<col width="11%"></col>
										<col width="11%"></col>
										</colgroup>
										<TBODY>
											<tr  class="ListStyle" style="height: 2px;background-color: #f7f7f7;border-bottom:2px solid #B7E0FE;">
											    <!--<td>部门</td>
												<td>工号</td>-->
												<td>周一</td>
												<td>周二</td>
												<td>周三</td>
												<td>周四</td>
												<td>周五</td>
												<td>周六</td>
												<td>周日</td>
											</tr>
											<tr>
											<%
											Calendar cal = Calendar.getInstance();
											if (!"".equals(year_name) && !"".equals(month_name)) {
											cal.set(Integer.parseInt(year_name),Integer.parseInt(month_name)-1, 1);
											}else{
											    cal.set(Calendar.DAY_OF_MONTH, 1);
										}
											int maxDay = cal.getActualMaximum(cal.DAY_OF_MONTH);
											int day_week = cal.get(cal.DAY_OF_WEEK) - 1;
											int other_week = day_week;
											int firstday = 1;
											if (other_week == 0){
											other_week = 7;
											}
											out.println("other_week="+day_week);
											//out.println("maxDay="+maxDay);
											%>
											<!--<td rowspan="2">部门</td>
											<td rowspan="2">姓名</td>-->
											<%
											for (int j = 1; j <= 40; j++) {
											    if (j == other_week){
											%>
											        <td><%=firstday%></td>
											<%
											        firstday ++;
											    }

											    if (firstday >1){
											    %>
											        <td><%=firstday%></td>
											<%
                                                    firstday ++;
											    }
										
											if(j%7 == 0){
											%>
										</tr>
										    }
										    %>
											    <td><%=firstday%></td>
											<%
										}
										%>
								<%
								/*String sql = " ";
								rs.executeSql(sql);
								new BaseBean().writeLog("sql___________" + sql);
								while (rs.next()) {
								num ++;
								sid = Util.null2String(rs.getString("id"));
								kzr_name = Util.null2String(rs.getString("kzr_name"));
								gsmc = Util.null2String(rs.getString("gsmc"));
								jyqx = Util.null2String(rs.getString("jyqx"));
								hos_name = Util.null2String(rs.getString("hos_name"));
								nsqqxksrq = Util.null2String(rs.getString("nsqqxksrq"));
								nsqqxjsrq = Util.null2String(rs.getString("nsqqxjsrq"));
								*/%>
								<tr>
									<Td></Td>
									<Td></Td>
									<Td></Td>
									<Td></Td>
									<Td></Td>
									<Td></Td>
									<Td></Td>
								</tr>
							</TBODY>
						</TABLE>
					</FORM>
				</td>
			</tr>
		</TABLE>
	</td>
</tr>
</table>
<Script language=javascript>
	function doEdit(obj) {
		weaver.submit();
		obj.disabled = true;
	}
</script>
</BODY>
</HTML>