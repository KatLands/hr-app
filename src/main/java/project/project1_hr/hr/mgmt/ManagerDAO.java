package project.project1_hr.hr.mgmt;

import java.util.*;

import project.project1_hr.tools.DbConnection;

import java.sql.*;

public class ManagerDAO {
    public static List<Manager> findAll() {
        try {
            List<Manager> listManagers = new ArrayList<>();
            PreparedStatement ps = DbConnection.getConnection().prepareStatement("SELECT * from manager");
            ResultSet rs = ps.executeQuery();
            //System.out.println("rs" + rs);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = (rs.getString("managerName"));
                Double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                Manager p = new Manager(id, name, salary, department);
                listManagers.add(p);
            }
            return listManagers;
        } catch (Exception e) {
            return null;
        }
    }

    public Manager find(int id) {
        try {
            Manager p = null;
            PreparedStatement ps = DbConnection.getConnection().prepareStatement("SELECT * from manager WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = (rs.getString("managerName"));
                Double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                p = new Manager(id, name, salary, department);
            }
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean create(Manager p) {
        try {
            PreparedStatement ps = DbConnection.getConnection()
                    .prepareStatement("INSERT INTO manager(managerName,salary,department) values(?,?,?);");
            ps.setString(1, p.getManagerName());
            ps.setDouble(2, p.getSalary());
            ps.setString(3, p.getDepartment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean edit(Manager p) {
        try {
            PreparedStatement ps = DbConnection.getConnection()
                    .prepareStatement("UPDATE manager set managerName=?,salary=?,department=?" + " where id=?");
            ps.setString(1, p.getManagerName());
            ps.setDouble(2, p.getSalary());
            ps.setString(3, p.getDepartment());
            ps.setInt(4, p.getId());
            // System.out.println(ps.toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean delete(Manager p) {
        try {
            PreparedStatement ps = DbConnection.getConnection().prepareStatement("DELETE FROM Manager where id=?");
            ps.setInt(1, p.getId());
            // System.out.println(ps.toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}

