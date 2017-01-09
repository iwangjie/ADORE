package GoldHongYe.tmc;

import java.util.HashMap;
import java.util.Map;

public class Test1 {

	public static void main(String[] args) {
		BringMainAndDetailByMain bmd = new BringMainAndDetailByMain();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("EMP_CODE", "10001");
		String SID = "10";
		
		String retRes = bmd.getReturn(map, SID);
		
		System.out.println(retRes);
	}
}
