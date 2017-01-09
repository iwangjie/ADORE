package gvo.book;

import weaver.conn.RecordSet;//执行数据库操作的类，实现了从数据库链接池中获取链接，执行指定的数据库脚本或者存储过程，并在脚本或者存储过程执行完毕后将链接及时地归还到链接池中。
import weaver.general.BaseBean;//类实现了记录日志和获取属性文件值的方法
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class updateBook1WorkflowAction implements Action {


	public String execute(RequestInfo info) {
		new BaseBean().writeLog("进入借书申请action_____________________________");

		RecordSet rs = new RecordSet();
		RecordSet res = new RecordSet();
		String sql = "";
		String tableName = "";
		String bookID = "";
		String personID = "";
		sql = "Select tablename From Workflow_bill Where id in ("+ "Select formid From workflow_base Where id="+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql---------" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!" ".equals(tableName)) {

			String sql_1 = "select * from " + tableName + " where requestid = "+ info.getRequestid();
			new BaseBean().writeLog("sql1---------" + sql_1);
			res.executeSql(sql_1);
			if (res.next()) {
				bookID = res.getString("tsmc");
				personID = res.getString("sqr");
				if (!"".equals(bookID)) {
					String sql_up = "update formtable_main_91 set tszt = '1',mqjyr='"+ personID + "' where id = '" + bookID + "'";

					new BaseBean().writeLog("sql_up---------" + sql_up);
					String sql_up1 = "update hrmresource set numberfield1=nvl(numberfield1,0)+1 where id = " + personID;
					new BaseBean().writeLog("sql_up1-------" + sql_up1);

					if (!rs.executeSql(sql_up)) {
						new BaseBean().writeLog("图书状态更新失败");
						return "-1";
					}
					if (!rs.executeSql(sql_up1)) {
							new BaseBean().writeLog("人员借阅次数更新失败");
							return "-1";
					}
				} else {
					new BaseBean().writeLog("图书ID错误");
					return "-1";
				}
			} else {
				new BaseBean().writeLog("流程取表信息错误");
				return "-1";
			}
		}
		return SUCCESS;
	}
}