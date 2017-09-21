package weaver.interfaces.shaw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 功能：Java读取txt文件的内容
 * 步骤：1：先获得文件句柄
 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
 * 3：读取到输入流后，需要读取生成字节流
 * 4：一行一行的输出。readline()。
 * 备注：需要考虑的是异常情况
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
