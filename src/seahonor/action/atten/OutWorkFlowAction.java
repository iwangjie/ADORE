package seahonor.action.atten;

import java.util.HashMap;
import java.util.Map;
import seahonor.util.InsertUtil;

public class OutWorkFlowAction {
	
	public boolean remind(String creater,String reDate,String reTime,String title,
			String remarks,String type,String waySys,String wayEmail,String waySms,String reName,
			String other,String notifier,String is_active){
	//   插入中间表  remindRecord
	Map<String, String> mapStr = new HashMap<String, String>();
	mapStr.put("id", "##newId()");
	mapStr.put("creater", creater);
	mapStr.put("created_time", "##getdate()");
	mapStr.put("reDate", reDate);
	mapStr.put("reTime", reTime);
	mapStr.put("title", title);
	mapStr.put("Remarks", remarks);
	mapStr.put("type", type);
	mapStr.put("waySys", waySys);
	mapStr.put("wayEmail", wayEmail);
	mapStr.put("waySms", waySms);
	mapStr.put("reName", reName);
	mapStr.put("other", other);
	mapStr.put("notifier", notifier);
	mapStr.put("is_active", is_active);
	
	String tableName = "remindRecord";
	return new InsertUtil().insert(mapStr, tableName);
}

}
