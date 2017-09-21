package weaver.interfaces.shaw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * ���ܣ�Java��ȡtxt�ļ�������
 * ���裺1���Ȼ���ļ����
 * 2������ļ��������������һ���ֽ���������Ҫ��������������ж�ȡ
 * 3����ȡ������������Ҫ��ȡ�����ֽ���
 * 4��һ��һ�е������readline()��
 * ��ע����Ҫ���ǵ����쳣���
 *
 * @param filePath
 */

public class ReadTxtUtil {
    public String readTxt() {
        String str = "";
        try {
            File file = new File("/Users/adore/Documents/Develop/Develop/CVR100Demo/wz.txt");

            StringBuilder sb = new StringBuilder();
            String s = "";
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((s = br.readLine()) != null) {
                sb.append(s + ",");
            }

            br.close();
            str = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) {
        ReadTxtUtil rtu = new ReadTxtUtil();
        System.out.println(rtu.readTxt());
    }

}
