package shaw.prac;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetList {
    public static void main(String[] args) {

        Map map = new HashMap<String, Object>();
        map.put("1", "fds");
        map.put("2", "valu");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map);
        System.out.println("list="+list);
        for (Map<String, Object> m : list) {
            for (String k : m.keySet()) {
                System.out.println(k + " : " + m.get(k));
            }

        }

    }
}
