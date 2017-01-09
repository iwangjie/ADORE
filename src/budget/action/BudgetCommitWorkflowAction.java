package budget.action;

import java.sql.Time;
import java.util.Hashtable;

import weaver.conn.RecordSet;
import weaver.file.Prop;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;

public class BudgetCommitWorkflowAction implements Action {
	private StaticObj staticobj = null;
	public String execute(RequestInfo info) {
		new BaseBean().writeLog("_-----------------进入commit-------------------");
		staticobj = StaticObj.getInstance();
		String errorMsg = "";

		RecordSet rs = new RecordSet();
		RecordSet rsc = new RecordSet();
		RecordSet rec = new RecordSet();
		String sql = "";
		String tableName = "";
		
		String str_fkfs = "";
		
		String str_dep = "szdw";//单位ID
		
		String str_money = "sqje";//申请金额字段名称
		if("2186".equals(info.getWorkflowid())||"2189".equals(info.getWorkflowid())||"2190".equals(info.getWorkflowid())||"2193".equals(info.getWorkflowid())||"2245".equals(info.getWorkflowid())||"2247".equals(info.getWorkflowid())||"2248".equals(info.getWorkflowid())||"2252".equals(info.getWorkflowid())){
			//如果是汇付款类流程，fkfs的字段名字是fkfs
			str_fkfs = "fkfs";
			//如果是汇付款类流程，所在单位的字段名字是fkfs
			str_dep = "sqdw";
		}else if("2185".equals(info.getWorkflowid())||"2187".equals(info.getWorkflowid())){
			//如果是报销类流程，则fkfs的字段名字是zffs
			str_fkfs = "zffs";
			//如果是报销类流程，则申请金额字段的名称是bxzje
			str_money = "bxzje";
		}else{
			//如果是报销类流程，则fkfs的字段名字是zffs
			str_fkfs = "zffs";
			//如果是报销类流程，则申请金额字段的名称是bxzje
			str_money = "bxzje";
		}
		
		sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id="+info.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		String sqrq = "";//流程申请日期
		String fkfs = "";//付款方式
		String sqje = "";//流程申请金额
		String sqdw = "";//申请单位
		
		String xjfs = "";//现金方式
		if(!"".equals(tableName)){
			sql = "select * from "+tableName+" where requestid = '"+info.getRequestid()+"'";
			new BaseBean().writeLog("语句sql"+sql);
			rs.executeSql(sql);
			if(rs.next()){
				 sqrq = Util.null2String(rs.getString("sqrq"));	//申请日期
				 if("2184".equals(info.getWorkflowid())){
					 //如果是承兑汇票流程，付款方式为承兑汇票
					 fkfs = "2";
				 }else{
					 fkfs = Util.null2String(rs.getString(str_fkfs));	//付款方式
				 }
				 sqje = Util.null2String(rs.getString(str_money));	//申请金额
				 sqdw = Util.null2String(rs.getString(str_dep));//申请单位
				 xjfs = Util.null2String(rs.getString("xjfs"));//现金方式
			}
			sqrq = sqrq.substring(0,4);
			
			//如果现金方式不是现金二，则冻结金额
			if(!"1".equals(xjfs)){
				//查看剩余金额是否够用
				String balaceMoney = "";//剩余金额
				String sql_balace = "select * from budgetDetal where conid = '"+sqdw+"' and year = '"+sqrq+"' and type = '"+fkfs+"'";
				new BaseBean().writeLog("查询"+sql_balace);
				rec.executeSql(sql_balace);
				if(rec.next()){
					balaceMoney = rec.getString("availableBudget");
					if(Util.getDoubleValue(balaceMoney)<Util.getDoubleValue(sqje)){
						errorMsg = "剩余余额不足！";
						staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
						return "-1";
					}
				}
				//更新数据：冻结本次申请金额，减少本次申请金额
				String sql_budget = "update budgetDetal set availableBudget = availableBudget-"+Util.getDoubleValue(sqje)+",freeZeBudget=freeZeBudget+"+Util.getDoubleValue(sqje)+" where conid = '"+sqdw+"' and year = '"+sqrq+"' and type = '"+fkfs+"'";
				new BaseBean().writeLog("更新"+sql_budget);
				if(!rsc.executeSql(sql_budget)){
					errorMsg = "金额冻结出错，请联系管理员";
					new BaseBean().writeLog("更新:"+sql_budget);
					staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
					return "-1";
				}
//			}
//			else{
				//更新字段状态为冻结
				sql = "update "+tableName+" set sfdj = '1' where requestid = '"+info.getRequestid()+"'";
				rs.executeSql(sql);
			}
			
		}else{
			errorMsg = "此流程表名称不存在!";
			staticobj.putRecordToObj("budget_upd", "ErrorMsg", "OA ErrorMsg:["+errorMsg+"]");
			return "-1";
		}
			return SUCCESS;
	}
	
}
