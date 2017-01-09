package shaw;

/**
 * Created by adore on 16/7/13.
 */
public class getTEL {
    //DepartmentComInfo dc = new DepartmentComInfo();

    public static void main(String[] args){
        int[] arr = new int[]{8,2,1,0,3};//1,2,3,4,6,8,9
        int[] index = new int[]{2,0,3,2,4,0,1,3,2,3,3};//1891326242
        String tel = "";
            for(int i:index){
                tel+=arr[i];
            }
        System.out.println("联系方式:"+tel);
    }
}
