package shaw;

public class EmployeeTest {
	
	public static void main(String []args){
		//ʹ�ù�����������������
		
		Employee empOne = new Employee("James Smith");
		Employee empTwo = new Employee("Mary Anne");
		
		
		//��������������ķ���
		empOne.empAge(26);
		empOne.empDesignation("Senior Software Enginner");
		empOne.empSalary(1000.0);
		empOne.printEmployee();
		
		empTwo.empAge(21);
		empTwo.empDesignation("Senior Software Enginner");
		empTwo.empSalary(500.0);
		empTwo.printEmployee();
	}

}
