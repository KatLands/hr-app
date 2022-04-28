package project.project1_hr.hr.exec;

import java.util.*;

import project.project1_hr.tools.DbConnection;

import java.sql.*;

public class ExecutiveDAO {
    public static List<Executive> findAll() {
        try {
            List<Executive> listExecutives = new ArrayList<>();
            PreparedStatement ps = DbConnection.getConnection().prepareStatement("SELECT * from executive");
            ResultSet rs = ps.executeQuery();
            //System.out.println("rs" + rs);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = (rs.getString("executiveName"));
                double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                double bonus = rs.getDouble("bonus");
                Executive p = new Executive(id, name, salary, department, bonus);
                listExecutives.add(p);
            }
            return listExecutives;
        } catch (Exception e) {
            return null;
        }
    }

    public Executive find(int id) {
        try {
            Executive p = null;
            PreparedStatement ps = DbConnection.getConnection().prepareStatement("SELECT * from executive WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = (rs.getString("executiveName"));
                double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                double bonus = rs.getDouble("bonus");
                p = new Executive(id, name, salary, department, bonus);
            }
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean create(Executive p) {
        try {
            PreparedStatement ps = DbConnection.getConnection()
                    .prepareStatement("INSERT INTO executive(executiveName,salary,department,bonus) values(?,?,?,?);");
            ps.setString(1, p.getExecutiveName());
            ps.setDouble(2, p.getSalary());
            ps.setString(3, p.getDepartment());
            ps.setDouble(4, p.getBonus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean edit(Executive p) {
        try {
            PreparedStatement ps = DbConnection.getConnection()
                    .prepareStatement("UPDATE executive set executiveName=?,salary=?,department=?,bonus=?" + " where id=?");
            ps.setString(1, p.getExecutiveName());
            ps.setDouble(2, p.getSalary());
            ps.setString(3, p.getDepartment());
            ps.setDouble(4, p.getBonus());
            ps.setInt(5, p.getId());
            // System.out.println(ps.toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean delete(Executive p) {
        try {
            PreparedStatement ps = DbConnection.getConnection().prepareStatement("DELETE FROM executive where id=?");
            ps.setInt(1, p.getId());
            // System.out.println(ps.toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}

