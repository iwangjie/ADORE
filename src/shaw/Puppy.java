package shaw;

public class Puppy {
	
	int puppyAge;
	
	public Puppy(String name){
		// �������������һ��������name
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
		//��������
		Puppy myPuppy = new Puppy("Tommy");
		//ͨ���������趨age
		myPuppy.setAge(2);
		//������һ��������ȡage
		myPuppy.getAge();
		//Ҳ����������һ�����ʳ�Ա����
		System.out.println("Variable value :" + myPuppy.puppyAge);
		
	}

}
