package seahonor.util;

import weaver.docs.docs.DocViewer;
import weaver.general.Util;
import weaver.conn.RecordSet;
import weaver.workflow.msg.PoppupRemindInfoUtil;

import java.util.ArrayList;

/**
 * Created by adore on 15/11/9.
 * 收回待办事宜和文档权限
 */
public class TakeAgentBackUtil {
    public String setBackAgent(String empid){
        String result = "";
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        RecordSet rs2 = new RecordSet();
        RecordSet rs3 = new RecordSet();
        RecordSet rs6 = new RecordSet();

        PoppupRemindInfoUtil priu = new PoppupRemindInfoUtil();
        DocViewer dv = new DocViewer();

        int usertype = 0;
        rs6.executeSql("select agentuid from workflow_agentConditionSet where bagentuid='"+empid+"' and agenttype=1 ");
        //将所有收回
        while(rs6.next()){
            String aid=Util.getIntValues(rs6.getString("agentuid"));
            String updateSQL = "";
            rs1.executeSql("select * from workflow_currentoperator where isremark in ('0','1','5','7','8','9')  and userid = " + aid + " and agentorbyagentid = " + empid + " and agenttype = '2'");//td2302 xwj
            while(rs1.next()){
                int wfcoid = Util.getIntValue(rs1.getString("id"));
                String tmprequestid=rs1.getString("requestid");
                String tmpisremark=rs1.getString("isremark");
                int tmpgroupid=rs1.getInt("groupid");
                int currentnodeid = rs1.getInt("nodeid");//流程当前所在节点

                int tmpuserid=rs1.getInt("userid");
                String tmpusertype=rs1.getString("usertype");
                int tmppreisremark=Util.getIntValue(rs1.getString("preisremark"),0);
                int upcoid = 0;
                rs2.execute("select id from workflow_currentoperator where requestid = " + tmprequestid + " and isremark = '2' and userid = " + rs1.getString("agentorbyagentid") + " and agenttype = '1' and agentorbyagentid = " + tmpuserid +" and usertype=0 and groupid="+tmpgroupid+" and nodeid="+currentnodeid);
                if(rs2.next()){
                    upcoid = Util.getIntValue(rs2.getString("id"));
                    updateSQL = "update workflow_currentoperator set isremark = '" + tmpisremark + "',preisremark='"+tmppreisremark+"', agenttype ='0', agentorbyagentid = -1  where id="+upcoid;
                    //应该只更新当前节点的代理关系，已经经过的节点不用更新
                    rs2.executeSql(updateSQL);  //被代理人重新获得任务
                    //失效的代理人删除
                    rs2.executeSql("delete workflow_currentoperator where id="+wfcoid);//td2302 xwj
                    rs2.executeSql("update workflow_forward set beforwardid = " + upcoid + " where requestid="+tmprequestid+" and beforwardid="+wfcoid);
                    rs2.executeSql("update workflow_forward set forwardid = " + upcoid + " where requestid="+tmprequestid+" and forwardid="+wfcoid);
                }
                priu.updatePoppupRemindInfo(tmpuserid, 10, tmpusertype, Util.getIntValue(tmprequestid));
                priu.updatePoppupRemindInfo(tmpuserid,0,tmpusertype,Util.getIntValue(tmprequestid));
                //add by fanggsh 20060519 TD4346 begin 流程代理收回导致操作人查不到流程
                rs3.executeSql("select id from workflow_currentoperator where requestid ="+tmprequestid+" and userid="+tmpuserid+" and usertype="+usertype+" order by id desc ");
                if(rs3.next()){
                    rs2.executeSql("update workflow_currentoperator set islasttimes=1 where requestid=" +tmprequestid + " and userid=" + tmpuserid + " and id = " + rs3.getString("id"));
                }
                //add by fanggsh 20060519 TD4346 end

                //回收代理人文档权限

                rs2.executeSql("select distinct docid,sharelevel from Workflow_DocShareInfo where requestid="+tmprequestid+" and userid="+aid+" and beAgentid="+empid);
                boolean hasrow=false;
                ArrayList docslist=new ArrayList();
                ArrayList sharlevellist=new ArrayList();
                while(rs2.next()){
                    hasrow=true;
                    docslist.add(rs2.getString("docid"));
                    sharlevellist.add(rs2.getString("sharelevel"));
                }
                if(hasrow){
                    rs2.executeSql("delete Workflow_DocShareInfo where requestid="+tmprequestid+" and userid="+aid+" and beAgentid="+empid);
                }
                for(int j=0;j<docslist.size();j++){
                    rs3.executeSql("select Max(sharelevel) sharelevel from Workflow_DocShareInfo where docid="+docslist.get(j)+" and userid="+aid);
                    if(rs3.next()){
                        int sharelevel=Util.getIntValue(rs3.getString("sharelevel"),0);
                        if(sharelevel>0){
                            rs.executeSql("update DocShare set sharelevel="+sharelevel+" where sharesource=1 and docid="+docslist.get(j)+" and userid="+aid+" and sharelevel>"+sharelevel);
                        }else{
                            rs.executeSql("delete DocShare where sharesource=1 and docid="+docslist.get(j)+" and userid="+aid);
                        }
                    }else{
                        rs.executeSql("delete DocShare where sharesource=1 and docid="+docslist.get(j)+" and userid="+aid);
                    }
                    //重新赋予被代理人文档权限
                    rs.executeSql("update DocShare set sharelevel="+sharlevellist.get(j)+" where sharesource=1 and docid="+docslist.get(j)+" and userid="+empid);
                   // dv.setDocShareByDoc((String) docslist.get(j));
                }
                //end by mackjoe
            }
        }

        return result;
    }
}
