package project.project1_hr.hr.exec;

import project.project1_hr.collection.Measurable;

public class Executive implements Measurable {

    private int id;
    private String executiveName;
    private double salary;
    private String department;
    private double bonus;

    public Executive(int id, String executiveName, double salary, String department, double bonus) {
        this.id = id;
        this.executiveName = executiveName;
        this.salary = salary;
        this.department = department;
        this.bonus = bonus;
    }

    public String getExecutiveName() {
        return executiveName;
    }

    public void setExecutiveName(String executiveName) {
        this.executiveName = executiveName;
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

    public double getBonus() { return bonus;}

    public void setBonus(double bonus) { this.bonus = bonus; }

    @Override
    public String toString() {
        return "Executive [id=" + id + ", executiveName=" + executiveName + ", salary=" +
                salary + " department = " + department + " bonus = " + bonus + "]";
    }

    @Override
    public int getMeasure() {
        return (int) (getSalary() * 100);
    }
}
