package yeo.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adore on 16/9/2.
 * ������ˮ��
 */
public class PrimaryGenerater {
    private static final String SERIAL_NUMBER = "XXXX"; // ��ˮ�Ÿ�ʽ
    private static PrimaryGenerater primaryGenerater = null;

    private PrimaryGenerater() {
    }

    /**
     * ȡ��PrimaryGenerater�ĵ���ʵ��
     *
     * @return
     */
    public static PrimaryGenerater getInstance() {
        if (primaryGenerater == null) {
            synchronized (PrimaryGenerater.class) {
                if (primaryGenerater == null) {
                    primaryGenerater = new PrimaryGenerater();
                }
            }
        }
        return primaryGenerater;
    }

    /**
     * ������һ�����
     */
    public synchronized String generaterNextNumber(String sno) {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        if (sno == null) {
            id = formatter.format(date) + "0001";
        } else {
            int count = SERIAL_NUMBER.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append("0");
            }
            DecimalFormat df = new DecimalFormat("0000");
            id = formatter.format(date)
                    + df.format(1 + Integer.parseInt(sno.substring(8, 10)));
        }
        return id;
    }

    public static void main(String[] args) {
        PrimaryGenerater gnn = new PrimaryGenerater();
        String newDate = gnn.generaterNextNumber("1609020001");
        System.out.println("newDate="+newDate);
    }
}
