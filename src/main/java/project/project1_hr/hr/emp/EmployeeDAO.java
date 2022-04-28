package project.project1_hr.hr.emp;

import java.util.*;

import project.project1_hr.tools.DbConnection;

import java.sql.*;

public class EmployeeDAO {
	public static List<Employee> findAll() {
		try {
			List<Employee> listEmployees = new ArrayList<>();
			PreparedStatement ps = DbConnection.getConnection().prepareStatement("SELECT * from employee");
			ResultSet rs = ps.executeQuery();
			//System.out.println("rs" + rs);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = (rs.getString("EmployeeName"));
				Double salary = rs.getDouble("salary");
				Employee p = new Employee(id, name, salary);
				listEmployees.add(p);
			}
			return listEmployees;
		} catch (Exception e) {
			return null;
		}
	}

	public Employee find(int id) {
		try {
			Employee p = null;
			PreparedStatement ps = DbConnection.getConnection().prepareStatement("SELECT * from employee WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String name = (rs.getString("EmployeeName"));
				Double salary = rs.getDouble("salary");
				p = new Employee(id, name, salary);
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean create(Employee p) {
		try {
			PreparedStatement ps = DbConnection.getConnection()
					.prepareStatement("INSERT INTO Employee(EmployeeName,salary) values(?,?);");
			ps.setString(1, p.getEmployeeName());
			ps.setDouble(2, p.getSalary());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean edit(Employee p) {
		try {
			PreparedStatement ps = DbConnection.getConnection()
					.prepareStatement("UPDATE Employee set EmployeeName=?,salary=?" + " where id=?");
			ps.setString(1, p.getEmployeeName());
			ps.setDouble(2, p.getSalary());
			ps.setInt(3, p.getId());
			// System.out.println(ps.toString());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean delete(Employee p) {
		try {
			PreparedStatement ps = DbConnection.getConnection().prepareStatement("DELETE FROM Employee where id=?");
			ps.setInt(1, p.getId());
			// System.out.println(ps.toString());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}
}
