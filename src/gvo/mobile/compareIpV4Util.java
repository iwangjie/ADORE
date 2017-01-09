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
        // �ж�ip������ַ�Ĵ�С��ϵ
        String ip1 = "10.8.9.116";
        String ip2 = "10.8.9.116";
        System. out.println( (compareIpV4s (ip1, ip2) ==0));

        String ip3= "10.8.8.116";
        String ip4 = "10.10.9.10";

        System. out.println("ip3 ���� ip4�� " + (compareIpV4s (ip3, ip4) > 0));

        // �ж�ip������ַ�Ƿ���ͬһ������
        int mask1 = getIpV4Value( "255.255.255.0");
        int mask2 = getIpV4Value( "255.255.0.0");

        System. out.println("ip1��ip2��ͬһ�������У� " + (checkSameSegment(ip1, ip2, mask1)));

        System. out.println("ip3��ip4��ͬһ�������� ��" + (checkSameSegment(ip3, ip4, mask2)));

        // �ж�ip5�Ƿ���ip1��ip2��Χ��
        String ip5= "10.8.8.8";
        // ����ip1��ip2��ͬһ�������У�����ip1Ϊ��ʼ��ַ��ip2Ϊ������ַ��ip1<=1
        // �Ƚ�ip1��ip5�Ƿ���ͬһ��������
        if(checkSameSegment(ip1, ip5, mask1))
        {
            // �ж�ip5�Ƿ���ip1��ip2��Χ��
            if(((compareIpV4s(ip5, ip1)) >= 0) && (compareIpV4s(ip5, ip2) <= 0))
            {
                System. out.println("ip5 ��ip1-ip2��Χ��" );
            }
            else if ((compareIpV4s(ip5, ip1)) < 0)
            {
                System. out.println("ip5 ����ip1-ip2��Χ�ڣ���Ϊip5С��ip1" );
            }
            else
            {
                System. out.println("ip5 ����ip1-ip2��Χ�ڣ���Ϊip5����ip2" );
            }
        }
        else
        {
            System. out.println("ip5 ����ip1-ip2��Χ�ڣ���Ϊip5����ip1��������" );
        }
    }
}
