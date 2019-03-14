package project2;

class Employee{
	private String name;
	private String identity;
	
	public Employee(String name,String identity){
		this.name = name;
		this.identity = identity;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setIdentity(String identity){
		this.identity = identity;
	}
	public String getName(){
		return this.name;
	}
	public String getIdentity(){
		return this.identity;
	}
}
class SalariedEmplyee extends Employee{
	private int monthlySalary;
	
	public SalariedEmplyee(String name, String identity, int monthlySalary) {
		super(name, identity);
		this.monthlySalary = monthlySalary;
	}
	public void setMonthlySalary(int monthlySalary){
		this.monthlySalary = monthlySalary;
	}
	public int getMonthlySalary(){
		return monthlySalary;
	}
}
class HourlyEmployee extends Employee{
	private int hourlyWage;
	private float workTime;
	
	public HourlyEmployee(String name, String identity, int hourlyWage, float workTime) {
		super(name, identity);
		this.hourlyWage = hourlyWage;
		this.workTime = workTime;
	}
	public void setHourlyWage(int hourlyWage){
		this.hourlyWage = hourlyWage;
	}
	public void setWorkTime(float workTime){
		this.workTime = workTime;
	}
	public int getHourlyWage(){
		return this.hourlyWage;
	}
	public float getWorkTime(){
		return this.workTime;
	}
}
class CommissionEmployee extends Employee{
	private float proportionOfCommission;
	private int totalSales;
	
	public CommissionEmployee(
			String name, String identity, float proportionOfCommission, int totalSales) {
		super(name, identity);
		this.proportionOfCommission = proportionOfCommission;
		this.totalSales = totalSales;
	}
	public void setProportionOfCommission(float proportionOfCommission){
		this.proportionOfCommission = proportionOfCommission;
	}
	public void setTotalSales(int totalSales){
		this.totalSales = totalSales;
	}
	public float getProportionOfCommission(){
		return this.proportionOfCommission;
	}
	public int getTotalSales(){
		return this.totalSales;
	}
}
public class Main {
	public static void main(String[] args){
		Employee employee = new Employee("小明", "123456");
		System.out.println("Name: " + employee.getName());
		System.out.println("Identity: " + employee.getIdentity());
		employee.setName("小红");
		employee.setIdentity("654321");
		System.out.println("Name: " + employee.getName());
		System.out.println("Identity: " + employee.getIdentity());
		System.out.println("\n");
		
		SalariedEmplyee salariedEmplyee = new SalariedEmplyee("小明", "123456", 200);
		System.out.println("MonthlySalary: " + salariedEmplyee.getMonthlySalary());
		salariedEmplyee.setMonthlySalary(300);
		System.out.println("MonthlySalary: " + salariedEmplyee.getMonthlySalary());
		System.out.println("\n");
		
		HourlyEmployee hourlyEmployee = new HourlyEmployee("小明", "123456", 20, 5);
		System.out.println("HourlyWage: " + hourlyEmployee.getHourlyWage());
		System.out.println("WorkTime: " + hourlyEmployee.getWorkTime());
		hourlyEmployee.setHourlyWage(30);
		hourlyEmployee.setWorkTime(10);
		System.out.println("HourlyWage: " + hourlyEmployee.getHourlyWage());
		System.out.println("WorkTime: " + hourlyEmployee.getWorkTime());
		System.out.println("\n");
		
		CommissionEmployee commissionEmployee = 
				new CommissionEmployee("小明", "123456", (float) 0.05, 100);
		System.out.println("ProportionOfCommission: " +
				commissionEmployee.getProportionOfCommission());
		System.out.println("TotalSales: " + commissionEmployee.getTotalSales());
		commissionEmployee.setProportionOfCommission((float) 0.1);
		commissionEmployee.setTotalSales(300);
		System.out.println("ProportionOfCommission: " +
				commissionEmployee.getProportionOfCommission());
		System.out.println("TotalSales: " + commissionEmployee.getTotalSales());
	}	
}
