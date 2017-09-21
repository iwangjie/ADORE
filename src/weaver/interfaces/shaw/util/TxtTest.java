package weaver.interfaces.shaw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class TxtTest {

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/adore/Documents/Develop/Develop/CVR100Demo/wz.txt");

        StringBuilder sb = new StringBuilder();
        String s = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((s = br.readLine()) != null) {
            sb.append(s + ",");
        }

        br.close();
        String str = sb.toString();
        String[] arr = str.split(",");
        System.out.println(Arrays.toString(arr));

        //for(int i=0;i<arr.length;i++){
            String name = arr[0];
            String idCard = arr[5];
            System.out.println("name="+name);
            System.out.println("idCard="+idCard);
        //}
    }

}