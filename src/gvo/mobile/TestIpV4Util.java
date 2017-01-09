package gvo.mobile;

/**
 * Created by adore on 16/3/31.
 */

import java.net.UnknownHostException;

import static gvo.mobile.IpV4Util.compareIpV4s;

public class TestIpV4Util {
    public static void main(String[] args) throws UnknownHostException
    {
        // 判断ip两个地址的大小关系
        String ip1 = "10.8.0.0";
        String ip2 = "10.8.0.116";
        Boolean result;
        //result=compareIpV4s(ip1, ip2) > 0;
        //System. out.println("result1=" +result);

        String ip3 = "10.80.0.1";
        String ip4 = "10.80.254.254";
        String ip5 = "10.80.80.16";

        //判断ip5是否在ip1和ip2范围中
        if(((compareIpV4s(ip5, ip3)) <= 0) && (compareIpV4s(ip5, ip4) >= 0)) {
            result=true;
            System. out.println("result2=" +result);
        }
        else {
            result=false;
            System. out.println("result3=" +result);
        }

    }
}
//