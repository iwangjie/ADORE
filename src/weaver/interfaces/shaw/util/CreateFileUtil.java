package weaver.interfaces.shaw.util;

import java.io.*;

public class CreateFileUtil {
    public void contentToTxt(String filePath, String content) {
        //String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                if (!f.getParentFile().mkdirs()) {
                    return;
                }
            }
            if (!f.exists()) {
                f.createNewFile();// 不存在则创建
            }

            s1 = content;
            FileOutputStream fos = new FileOutputStream(f);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("/Users/adore/Desktop/1111111.json");
            OutputStream out = new FileOutputStream(file);
            BufferedWriter rd = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
            rd.write("333333333333中国-------");
            rd.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String filePath = "/Users/adore/Desktop/txt.json";
        String filePathWin = "C:\\Weaver2015_oracle\\ecology\\shawtable\\boot-table\\data.json";
        String txt = "哈哈哈哈~~~~~~lol!";
        CreateFileUtil cj = new CreateFileUtil();
        cj.contentToTxt(filePathWin, txt);
    }
}
