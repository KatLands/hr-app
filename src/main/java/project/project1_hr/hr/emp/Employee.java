package project.project1_hr.hr.emp;

import project.project1_hr.collection.Measurable;

public class Employee implements Measurable {

	private int id;
	private String employeeName;
	private double salary;

	public Employee(int id, String employeeName, double salary) {
		this.id = id;
		this.employeeName = employeeName;
		this.salary = salary;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public double getSalary() {
		return salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", salary=" + salary + "]";
	}

	@Override
	public int getMeasure() {
		return (int) (getSalary() * 100);
	}

}
