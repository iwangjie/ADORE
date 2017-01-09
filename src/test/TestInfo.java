package test;

import java.util.ArrayList;
import java.util.HashMap;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;

public class TestInfo extends BaseBean{
    private final static int Type_BYMONTH = 2; 
    private final static int Type_BYWEEK = 3;
    private final static int Type_BYDAY = 4;
       
    RecordSet rs = new RecordSet() ;
    ResourceComInfo rc = null;
    
    public TestInfo(){
    	try {
            rc = new ResourceComInfo();
        } catch (Exception ex) {
            writeLog(ex.getMessage());
        }
    }
    
    public String getSql(String day,int selectType,String tmp_id){
        String returnStr = "" ;   
        switch (selectType) {
            case Type_BYMONTH :
                returnStr="select id,xm,rq,bfkssj,bfjssj from formtable_main_3  where xm="+tmp_id+" and ('"+day+"'" +
                        " between SUBSTRING(rq,1,7) and SUBSTRING(rq,1,7)) " +
                        "  order by rq desc,bfkssj desc";
                break ;
            case Type_BYWEEK :
                returnStr="select id,xm,rq,bfkssj,bfjssj from formtable_main_3  where xm="+tmp_id+" and ( "   ;   
                for (int h = 0;h<7;h++){                 
                    String newTempDate = TimeUtil.dateAdd(day,h) ;
                    returnStr +="('"+newTempDate+"' between rq and rq) or" ;         
                }
                returnStr = returnStr.substring(0,returnStr.length()-2);
                returnStr += ") and  order by rq desc,bfkssj desc" ;
                break ;                
            case Type_BYDAY : 
                returnStr = "select id,xm,rq,bfkssj,bfjssj from formtable_main_3 where xm="+tmp_id+"  and ('"+day+"' " +
                        "between rq and rq)  " +
                        "  order by rq desc,bfkssj desc" ;
                break ;      
        }
        
        if ((rs.getDBType()).equals("oracle")) {
            returnStr = Util.StringReplace(returnStr,"SUBSTRING","substr");   
        }
        return returnStr;
    }
    
    // 0 It设备类    1 试验器材 
    public ArrayList<String> getITResouceList(){
    	ArrayList<String> itResouceList = new ArrayList<String>();
    	// IT 物资表
    	String sql = "select id,lastname,workcode from HrmResource";
    	rs.execute(sql);
    	while(rs.next()){
    		String tmp_id = Util.null2String(rs.getString("id"));
    		if(tmp_id.length()>0){
    			itResouceList.add(tmp_id);
    		}
    	}
    	return itResouceList;
    }

    
    public HashMap<String,HashMap<String,ArrayList<String>>> getMapping(String day,int selectType,int type) {
        HashMap<String,HashMap<String,ArrayList<String>>> returnMap = new HashMap<String,HashMap<String,ArrayList<String>>>();               
       
        ArrayList<String> itResouceList = getITResouceList();
        String sql = "";
        
        for(int i=0;i<itResouceList.size();i++){
            HashMap<String,ArrayList<String>> tempMap = new HashMap<String,ArrayList<String>>();
            String tmp_id = itResouceList.get(i);
            ArrayList<String> ids = new ArrayList<String>(); 
            ArrayList<String> emp_ids = new ArrayList<String>();
            ArrayList<String> beginDates = new ArrayList<String>();
            ArrayList<String> endDates = new ArrayList<String>();
            ArrayList<String> begintimes = new ArrayList<String>();
            ArrayList<String> endtimes = new ArrayList<String>();
            ArrayList<String> statuss = new ArrayList<String>();
            
            if (selectType != 2 && selectType != 3 && selectType != 4){
                writeLog("the HR query way is not found!");
            } else {
                sql = getSql(day,selectType,tmp_id);
            }      
           
            rs.executeSql(sql);
                       
            while(rs.next()) {
                String id = Util.null2String(rs.getString("id"));
                
                String emp_id = Util.null2String(rs.getString("xm"));
                
                String beginDate = Util.null2String(rs.getString("rq"));
                String endDate = Util.null2String(rs.getString("rq"));  
                
                String begintime = Util.null2String(rs.getString("bfkssj"));
                String endtime = Util.null2String(rs.getString("bfjssj"));
                
                ids.add(id);
                emp_ids.add(emp_id);               
                beginDates.add(beginDate); 
                endDates.add(endDate); 
                begintimes.add(begintime); 
                endtimes.add(endtime);
                statuss.add("1");
           }          
            tempMap.put("ids",ids) ;
            tempMap.put("emp_ids",emp_ids) ;
            tempMap.put("beginDates",beginDates) ;
            tempMap.put("endDates",endDates) ;
            tempMap.put("begintimes",begintimes) ;
            tempMap.put("endtimes",endtimes) ;   
            tempMap.put("statuss", statuss);
            
            returnMap.put(itResouceList.get(i),tempMap);            
        }
        return returnMap;
    } 
    

    public String getITResourceInfo(String emp_id,String beginDate,String endDate,String begintime,String endtime){
    	
		String returnStr = "" ;
		
		returnStr =  "日报填写人:    " + rc.getResourcename(emp_id) + "\n"
		+ " 日期:  " + beginDate + "     日期安排： " + "开始时间:  "+ begintime + "  结束时间:  " + endtime ;
		
		return returnStr ;
    } 
    
}

