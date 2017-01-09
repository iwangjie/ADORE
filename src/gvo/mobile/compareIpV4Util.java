package gvo.mobile;

import java.net.UnknownHostException;

import static gvo.mobile.IpV4Util.checkSameSegment;
import static gvo.mobile.IpV4Util.compareIpV4s;
import static gvo.mobile.IpV4Util.getIpV4Value;

/**
 * Created by adore on 16/3/31.
 */
public class compareIpV4Util {
    public static void main(String[] args) throws UnknownHostException
    {
        // 判断ip两个地址的大小关系
        String ip1 = "10.8.9.116";
        String ip2 = "10.8.9.116";
        System. out.println( (compareIpV4s (ip1, ip2) ==0));

        String ip3= "10.8.8.116";
        String ip4 = "10.10.9.10";

        System. out.println("ip3 大于 ip4？ " + (compareIpV4s (ip3, ip4) > 0));

        // 判断ip两个地址是否是同一个网段
        int mask1 = getIpV4Value( "255.255.255.0");
        int mask2 = getIpV4Value( "255.255.0.0");

        System. out.println("ip1和ip2在同一个网段中？ " + (checkSameSegment(ip1, ip2, mask1)));

        System. out.println("ip3和ip4在同一个网段中 ？" + (checkSameSegment(ip3, ip4, mask2)));

        // 判断ip5是否在ip1和ip2范围中
        String ip5= "10.8.8.8";
        // 假设ip1和ip2在同一个网段中，并且ip1为起始地址，ip2为结束地址，ip1<=1
        // 比较ip1与ip5是否在同一个网段中
        if(checkSameSegment(ip1, ip5, mask1))
        {
            // 判断ip5是否在ip1和ip2范围中
            if(((compareIpV4s(ip5, ip1)) >= 0) && (compareIpV4s(ip5, ip2) <= 0))
            {
                System. out.println("ip5 在ip1-ip2范围内" );
            }
            else if ((compareIpV4s(ip5, ip1)) < 0)
            {
                System. out.println("ip5 不在ip1-ip2范围内，因为ip5小于ip1" );
            }
            else
            {
                System. out.println("ip5 不在ip1-ip2范围内，因为ip5大于ip2" );
            }
        }
        else
        {
            System. out.println("ip5 不在ip1-ip2范围内，因为ip5不在ip1的网段中" );
        }
    }
}
