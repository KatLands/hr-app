package project.project1_hr.hr.mgmt;

import project.project1_hr.collection.Measurable;

public class Manager implements Measurable {

    private int id;
    private String managerName;
    private double salary;
    private String department;

    public Manager(int id, String managerName, double salary, String department) {
        this.id = id;
        this.managerName = managerName;
        this.salary = salary;
        this.department = department;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
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

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Manager [id=" + id + ", managerName=" + managerName + ", salary=" +
                salary + " department = " + department + "]";
    }

    @Override
    public int getMeasure() {
        return (int) (getSalary() * 100);
    }
}
