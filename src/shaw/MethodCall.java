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
        System.out.println("����һ����̬������");
    }
    public void sayInstance()
    {
        System.out.println("����һ��ʵ��������");
    }
}