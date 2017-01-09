package shaw;

public class Puppy {
	
	int puppyAge;
	
	public Puppy(String name){
		// 这个构造器仅有一个参数：name
		System.out.println("Passed name is :" +name);
	}
	
	public void setAge( int age ){
		
		puppyAge = age;
	}
	
	public int getAge( ){
		
		System.out.println("shaw.Puppy's age is:" + puppyAge);
		return puppyAge;
	}
	public static void main(String []args){
		//创建对象
		Puppy myPuppy = new Puppy("Tommy");
		//通过方法来设定age
		myPuppy.setAge(2);
		//调用另一个方法获取age
		myPuppy.getAge();
		//也可以像下面一样访问成员变量
		System.out.println("Variable value :" + myPuppy.puppyAge);
		
	}

}
