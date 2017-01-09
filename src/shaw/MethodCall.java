package shaw;

public class MethodCall
{
    public static void main(String[] args)
    {
        Test.sayStatic();
        Test test = new Test();
        test.sayInstance();
    }
}
class Test
{
    public static void sayStatic()
    {
        System.out.println("这是一个静态方法。");
    }
    public void sayInstance()
    {
        System.out.println("这是一个实例方法。");
    }
}