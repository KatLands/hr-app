package project.project1_hr.tools;

import project.project1_hr.collection.DataSetGeneric;
import project.project1_hr.hr.emp.Employee;
import project.project1_hr.hr.mgmt.Manager;
import project.project1_hr.hr.exec.Executive;

import java.sql.*;

public class DbConnection {
    public static boolean VerifyDBClassLoaded() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    public static Connection getConnection() {
        try {
    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "username", "password");
            return conn;
        } catch (SQLException e) {
            return null;
        }
    }

    public static DataSetGeneric<Employee> ReadFromDataBaseEmployee() {
        try {
            DataSetGeneric<Employee> employees = new DataSetGeneric<>();
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from employee");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double salary = resultSet.getDouble(3);
                Employee employee = new Employee(id, name, salary);
                employees.add(employee);
            }
            connection.close();
            return employees;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static DataSetGeneric<Manager> ReadFromDataBaseManager() {
        try {
            DataSetGeneric<Manager> managers = new DataSetGeneric<>();
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from manager");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double salary = resultSet.getDouble(3);
                String department = resultSet.getString(4);
                Manager manager = new Manager(id, name, salary, department);
                managers.add(manager);
            }
            connection.close();
            return managers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DataSetGeneric<Executive> ReadFromDataBaseExecutive() {
        try {
            DataSetGeneric<Executive> executives = new DataSetGeneric<>();
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from executive");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double salary = resultSet.getDouble(3);
                String department = resultSet.getString(4);
                double bonus = resultSet.getDouble(5);
                Executive executive = new Executive(id, name, salary, department, bonus);
                executives.add(executive);
            }
            connection.close();
            return executives;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
