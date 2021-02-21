package hw3;


class Employee{
	private long id;
	private String name;
	private int age;
	
	public Employee(long a, String b, int c) {
		id = a;
		name = b;
		age = c;		
	}

	public void show_Employee() {
		System.out.println("[e"+String.format("%03d", id)+", "+name+", "+age+"]");
	}
	public void show_Manager() {
		System.out.print("[m"+String.format("%03d", id)+", "+name+", "+age+", ");
	}
	
}

class Manager extends Employee{
	private String department;
	
	public Manager(long a, String b, int c, String d) {
		super(a,b,c);
		department=d;
	}
	public void show_Manager_2(){
		System.out.println(department+"]");
	}
}

public class s20161566hw3 {
	public static void main(String[] args) {

		Employee a = new Employee(001,"John",27);
		Employee b = new Employee(002,"Eujin",25);
		Employee c = new Employee(003,"Alex",26);
		Employee d = new Employee(004,"Jenny",23);
		Employee e = new Employee(005,"Tom",25);
		
		Manager f = new Manager(001,"Andy",33,"Marketing");
		Manager g = new Manager(002,"Kate",30,"Sales");
		
		System.out.println("===Employee===");
		a.show_Employee();
		b.show_Employee();
		c.show_Employee();
		d.show_Employee();
		e.show_Employee();
		
		System.out.println("===Manager===");
		f.show_Manager();
		f.show_Manager_2();
		g.show_Manager();
		g.show_Manager_2();
	}

}
