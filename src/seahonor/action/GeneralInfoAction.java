package seahonor.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class GeneralInfoAction implements Action{

	/*
	 *   表单建模每次存储备份
	 */
	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String table_name = "uf_custom";
		// 表单建模寻找表名
		String sql = "Select tablename From Workflow_bill Where id in( "
					+"select formid from modeinfo where id=)"+request.getWorkflowid();
		rs.executeSql(sql);
		if(rs.next()){
			table_name = Util.null2String(rs.getString("tablename"));
		}
		
		// 存放 表的字段
		List<String> list = new ArrayList<String>();
 		sql = "select fieldname from workflow_billfield where billid in(select formid from modeinfo where id="
				+request.getWorkflowid()+") order by dsporder";
		rs.executeSql(sql);
		while(rs.next()){
			String tmp_1 = Util.null2String(rs.getString("fieldname"));
			
			// 关联父类排除
			if(!"".equals(tmp_1)&&!"SuperID".equalsIgnoreCase(tmp_1)){
				list.add(tmp_1);
			}
		}
		System.out.println("list = " + list);
		if(!"".equals(table_name)){
			
			Map<String, String> mapStr = new HashMap<String, String>();
			
			sql = "select * from "+table_name+"  where id="+requestid;
			rs.execute(sql);
			if(rs.next()){
				// 循环获取   不为空值的组合成sql
				for(String field : list){
					String tmp_x = Util.null2String(rs.getString(field));
					if(tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			
			// 最后需要补充关联父id
			if(mapStr.size() > 0){
				mapStr.put("SuperID", requestid);
				iu.insert(mapStr, table_name);
			}
		}
		
		return SUCCESS;
	}

}
