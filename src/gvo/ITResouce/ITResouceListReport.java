package gvo.ITResouce;

import java.util.ArrayList;
import java.util.HashMap;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;

public class ITResouceListReport extends BaseBean {
	private final static int Type_BYMONTH = 2;
	private final static int Type_BYWEEK = 3;
	private final static int Type_BYDAY = 4;

	RecordSet rs = new RecordSet();
	ResourceComInfo rc = null;

	public ITResouceListReport() {
		try {
			rc = new ResourceComInfo();
		} catch (Exception ex) {
			writeLog(ex.getMessage());
		}
	}

	public String getSql(String day, int selectType, String itResouceId) {
		String returnStr = "";
		switch (selectType) {
		case Type_BYMONTH:
			returnStr = "select itform.*, wr.currentnodetype AS statusid from formtable_main_220 itform,workflow_requestbase wr where wr.requestid = itform.requestid AND wr.currentnodetype in (1,2,3) and ('"
					+ day
					+ "'"
					+ " between SUBSTRING(itform.yjjyksrq,1,7) and SUBSTRING(itform.yjjyjsrq,1,7)) "
					+ " and itform.zymc = "
					+ itResouceId
					+ " order by itform.yjjyksrq desc,itform.yjjykssj desc";
			break;
		case Type_BYWEEK:
			returnStr = "select itform.*, wr.currentnodetype AS statusid from formtable_main_220 itform,workflow_requestbase wr where wr.requestid = itform.requestid AND wr.currentnodetype in (1,2,3) and ( ";
			for (int h = 0; h < 7; h++) {
				String newTempDate = TimeUtil.dateAdd(day, h);
				returnStr += "('" + newTempDate
						+ "' between itform.yjjyksrq and itform.yjjyjsrq) or";
			}
			returnStr = returnStr.substring(0, returnStr.length() - 2);
			returnStr += ") and itform.zymc = " + itResouceId
					+ " order by itform.yjjyksrq desc,itform.yjjykssj desc";
			break;
		case Type_BYDAY:
			returnStr = "select itform.*, wr.currentnodetype AS statusid from formtable_main_220 itform,workflow_requestbase wr where wr.requestid = itform.requestid AND wr.currentnodetype in (1,2,3) and ('"
					+ day
					+ "' "
					+ "between itform.yjjyksrq and itform.yjjyjsrq)  "
					+ " and itform.zymc = "
					+ itResouceId
					+ " order by itform.yjjyksrq desc,itform.yjjykssj desc";
			break;
		}

		if ((rs.getDBType()).equals("oracle")) {
			returnStr = Util.StringReplace(returnStr, "SUBSTRING", "substr");
		}
		return returnStr;
	}

	// 0 It设备类 1 试验器材
	public ArrayList<String> getITResouceList(int type) {
		ArrayList<String> itResouceList = new ArrayList<String>();
		// IT 物资表
		String sql = "select id as resouce_id from formtable_main_215 where zylx="
				+ type;
		rs.execute(sql);
		while (rs.next()) {
			String tmp_id = Util.null2String(rs.getString("resouce_id"));
			if (tmp_id.length() > 0) {
				itResouceList.add(tmp_id);
			}
		}
		return itResouceList;
	}

	public boolean isReturn(String requestId) {
		// 归还节点
		String sql = "select count(*) as count_cc from workflow_currentoperator where nodeid=3729 and requestid="
				+ requestId;
		int maxGroup = 0;
		RecordSet recordSet = new RecordSet();
		recordSet.executeSql(sql);
		while (recordSet.next()) {
			maxGroup = recordSet.getInt("count_cc");
		}

		// 第几个节点...
		if (maxGroup > 0)
			return true;

		return false;
	}

	public int isCommit(String requestId) {
		// 获取目前状态
		String sql = "select currentnodetype from workflow_requestbase where requestid="
				+ requestId;
		int group = 0;
		RecordSet recordSet = new RecordSet();
		recordSet.executeSql(sql);
		while (recordSet.next()) {
			group = recordSet.getInt("currentnodetype");
		}

		return group;
	}

	public HashMap<String, HashMap<String, ArrayList<String>>> getMapping(
			String day, int selectType, int type) {
		HashMap<String, HashMap<String, ArrayList<String>>> returnMap = new HashMap<String, HashMap<String, ArrayList<String>>>();

		ArrayList<String> itResouceList = getITResouceList(type);
		String sql = "";

		for (int i = 0; i < itResouceList.size(); i++) {
			HashMap<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();

			ArrayList<String> ids = new ArrayList<String>();
			ArrayList<String> emp_ids = new ArrayList<String>();
			ArrayList<String> beginDates = new ArrayList<String>();
			ArrayList<String> endDates = new ArrayList<String>();
			ArrayList<String> begintimes = new ArrayList<String>();
			ArrayList<String> endtimes = new ArrayList<String>();
			ArrayList<String> addresses = new ArrayList<String>();
			ArrayList<String> statuss = new ArrayList<String>();

			if (selectType != 2 && selectType != 3 && selectType != 4) {
				writeLog("the ITResource query way is not found!");
			} else {
				sql = getSql(day, selectType, itResouceList.get(i));
			}

			rs.executeSql(sql);

			while (rs.next()) {
				String id = Util.null2String(rs.getString("id"));
				String requestid = Util.null2String(rs.getString("requestid"));

				String emp_id = Util.null2String(rs.getString("sqr"));

				String beginDate = Util.null2String(rs.getString("yjjyksrq"));
				String yjjyjsrq = Util.null2String(rs.getString("yjjyjsrq"));
				String yjjyjssj = Util.null2String(rs.getString("yjjyjssj"));
				String sjghrq = Util.null2String(rs.getString("sjghrq"));
				String sjghsj = Util.null2String(rs.getString("sjghsj"));
				String endDate = "";
				String endtime = "";
				if(sjghrq == null || sjghrq.length() <= 0){
				endDate = yjjyjsrq;
				endtime = yjjyjssj;
				}else{
				endDate = sjghrq;
				endtime = sjghsj;
				}
				//String endDate = Util.null2String(rs.getString("sjghrq"));
				String begintime = Util.null2String(rs.getString("yjjykssj"));
				//String endtime = Util.null2String(rs.getString("sjghsj"));

				String address = Util.null2String(rs.getString("zymc"));

				ids.add(id);
				emp_ids.add(emp_id);
				beginDates.add(beginDate);
				endDates.add(endDate);
				begintimes.add(begintime);
				endtimes.add(endtime);
				addresses.add(address);

				if (type == 0) {
					if (isReturn(requestid)) {
						statuss.add("3");
					} else {
						statuss.add("2");
					}
				} else {
					if (isCommit(requestid) == 3) {
						statuss.add("3");
					} else if (isCommit(requestid) == 0) {
						statuss.add("0");
					} else {
						statuss.add("2");
					}
				}

			}
			tempMap.put("ids", ids);
			tempMap.put("emp_ids", emp_ids);
			tempMap.put("beginDates", beginDates);
			tempMap.put("endDates", endDates);
			tempMap.put("begintimes", begintimes);
			tempMap.put("endtimes", endtimes);
			tempMap.put("addresses", addresses);
			tempMap.put("statuss", statuss);

			returnMap.put(itResouceList.get(i), tempMap);
		}
		return returnMap;
	}

	public String getITResourceInfo(String emp_id, String beginDate,
			String endDate, String begintime, String endtime) {

		String returnStr = "";

		returnStr = "借用人:    " + rc.getResourcename(emp_id) + "\n" + "开始日期:  "
				+ beginDate + "     结束日期:  " + endDate + "\n" + "开始时间:  "
				+ begintime + "      结束时间:  " + endtime;

		return returnStr;
	}

}
