package project.project1_hr;

import java.util.Comparator;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project.project1_hr.collection.DataSetGeneric;
import project.project1_hr.hr.exec.Executive;
import project.project1_hr.hr.exec.ExecutiveDAO;
import project.project1_hr.hr.exec.ExecutiveFX;
import project.project1_hr.hr.mgmt.Manager;
import project.project1_hr.hr.mgmt.ManagerDAO;
import project.project1_hr.hr.emp.Employee;
import project.project1_hr.hr.emp.EmployeeDAO;
import project.project1_hr.hr.emp.EmployeeFX;
import project.project1_hr.hr.mgmt.ManagerFX;
import project.project1_hr.tools.DbConnection;


public class PayrollFX extends Application {
	private DataSetGeneric<Employee> employee = new DataSetGeneric<>();
	private EmployeeFX employeeFX;

	private DataSetGeneric<Manager> manager = new DataSetGeneric<>();
	private ManagerFX managerFX;


	private DataSetGeneric<Executive> executive = new DataSetGeneric<>();
	private ExecutiveFX executiveFX;

	public void start(Stage primaryStage) {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		Tab tab1 = new Tab("Employee");
		employeeFX = new EmployeeFX();
		tab1.setContent(employeeFX);

		Tab tab2 = new Tab("Manager");
		managerFX = new ManagerFX();
		tab2.setContent(managerFX);

		Tab tab3 = new Tab("Executive");
		executiveFX = new ExecutiveFX();
		tab3.setContent(executiveFX);

		Tab tab4 = new Tab("Listings");
		TextArea textArea4 = new TextArea();

		Button btListAll = new Button("List All");
		btListAll.getStyleClass().add("grid-buttons");
		Button btListRegularEmployees = new Button("Employees");
		btListRegularEmployees.getStyleClass().add("grid-buttons");
		Button btListManagers = new Button("Managers");
		btListManagers.getStyleClass().add("grid-buttons");
		Button btListExecutives = new Button("Executives");
		btListExecutives.getStyleClass().add("grid-buttons");
		Button btHighestSalary = new Button("Highest Salary");
		btHighestSalary.getStyleClass().add("grid-buttons");
		Button btGeneratePayroll = new Button("Generate Payroll");
		btGeneratePayroll.getStyleClass().add("grid-buttons");
		Button btListByName = new Button("List/Name");
		btListByName.getStyleClass().add("grid-buttons");
		Button btListBySalary = new Button("List/Salary");
		btListBySalary.getStyleClass().add("grid-buttons");

		GridPane gp = new GridPane();
		gp.add(btListAll, 0, 0);
		gp.add(btListRegularEmployees, 1, 0);
		gp.add(btListManagers, 2, 0);
		gp.add(btListExecutives, 3, 0);
		gp.add(btHighestSalary, 0, 1);
		gp.add(btGeneratePayroll, 1, 1);
		gp.add(btListByName, 2, 1);
		gp.add(btListBySalary, 3, 1);
		gp.getStyleClass().add("grid");

		BorderPane borderPane4 = new BorderPane();
		borderPane4.setTop(gp);
		borderPane4.setCenter(textArea4);

		Button btLoadDataSet = new Button("Load Data Set");
		Button btClearAnswers = new Button("Clear");
		Button btnExit = new Button("Exit");
		HBox hBoxBottom = new HBox(btLoadDataSet, btClearAnswers, btnExit);

		hBoxBottom.getStyleClass().add("hboxGreen");
		borderPane4.setBottom(hBoxBottom);
		tab4.setContent(borderPane4);

		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(tabPane);

		btListAll.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "All company members\n\n";
				EmployeeDAO employeeDAO = new EmployeeDAO();
				List<Employee> employees = employeeDAO.findAll();
				for (Employee emp : employees) {
					output += emp + "\n";
				}
				ManagerDAO managerDAO = new ManagerDAO();
				List<Manager> managers = managerDAO.findAll();
				for (Manager mgr : managers) {
					output += mgr + "\n";
				}
				ExecutiveDAO executiveDAO = new ExecutiveDAO();
				List<Executive> executives = executiveDAO.findAll();
				for (Executive exe : executives) {
					output += exe + "\n";
				}
				textArea4.setText(output);
			}
		});

		btListRegularEmployees.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "Regular Employees\n\n";
				EmployeeDAO employeeDAO = new EmployeeDAO();
				List<Employee> employees = employeeDAO.findAll();
				for (Employee emp : employees) {
					output += emp + "\n";
				}
				textArea4.setText(output);
			}
		});

		btListManagers.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "Managers\n\n";
				ManagerDAO managerDAO = new ManagerDAO();
				List<Manager> managers = managerDAO.findAll();
				for (Manager mgr : managers) {
					output += mgr + "\n";
				}
				textArea4.setText(output);
			}
		});

		btListExecutives.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "Executives\n\n";
				ExecutiveDAO executiveDAO = new ExecutiveDAO();
				List<Executive> executives = executiveDAO.findAll();
				for (Executive exe : executives) {
					output += exe + "\n";
				}


				textArea4.setText(output);
			}
		});

		btHighestSalary.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "Highest Salary for Employee, Manager and Executive\n\n";
				employee = DbConnection.ReadFromDataBaseEmployee();
				manager = DbConnection.ReadFromDataBaseManager();
				executive = DbConnection.ReadFromDataBaseExecutive();
				output += employee.getMax() + "\n";
				output += manager.getMax() + "\n";
				output += executive.getMax() + "\n";
				textArea4.setText(output);
			}
		});

		btGeneratePayroll.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "Generate Payroll\n\n";
				employee = DbConnection.ReadFromDataBaseEmployee();
				manager = DbConnection.ReadFromDataBaseManager();
				executive = DbConnection.ReadFromDataBaseExecutive();
				output += "Employee Total: $" + employee.getObjMeasure() + "\n";
				output += "Manager Total: $" + manager.getObjMeasure() + "\n";
				output += "Executive Total: $" + executive.getObjMeasure() + "\n";
				textArea4.setText(output);
			}
		});

		btListByName.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "List by Name: Employees, Managers, Executives \n\n";
				employee = DbConnection.ReadFromDataBaseEmployee();
				manager = DbConnection.ReadFromDataBaseManager();
				executive = DbConnection.ReadFromDataBaseExecutive();
				Comparator<Employee> employeeCompare = (o1, o2) -> (o1.getEmployeeName().compareTo(o2.getEmployeeName()));
				for (Employee emp : employee.sortBy(employeeCompare)) {
					output += emp + "\n";
				}
				output += "\n";
				Comparator<Manager> managerCompare = (o1, o2) -> (o1.getManagerName().compareTo(o2.getManagerName()));
				for (Manager mgr : manager.sortBy(managerCompare)) {
					output += mgr + "\n";
				}
				output += "\n";
				Comparator<Executive> executiveCompare = (o1, o2) -> (o1.getExecutiveName().compareTo(o2.getExecutiveName()));
				for (Executive exec : executive.sortBy(executiveCompare)) {
					output += exec + "\n";
				}
				textArea4.setText(output);
			}
		});

		btListBySalary.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "List by Salary: Employee, Manager, Executive\n\n";
				employee = DbConnection.ReadFromDataBaseEmployee();
				manager = DbConnection.ReadFromDataBaseManager();
				executive = DbConnection.ReadFromDataBaseExecutive();
				Comparator<Employee> employeeCompare = (o1, o2) -> (Double.compare(o1.getSalary(), o2.getSalary()));
				for (Employee emp : employee.sortBy(employeeCompare)) {
					output += emp + "\n";
				}
				output += "\n";
				Comparator<Manager> managerCompare = (o1, o2) -> (Double.compare(o1.getSalary(), o2.getSalary()));
				for (Manager mgr : manager.sortBy(managerCompare)) {
					output += mgr + "\n";
				}
				output += "\n";
				Comparator<Executive> executiveCompare = (o1, o2) -> (Double.compare(o1.getSalary(), o2.getSalary()));
				for (Executive exec : executive.sortBy(executiveCompare)) {
					output += exec + "\n";
				}
				textArea4.setText(output);
			}
		});

		btLoadDataSet.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String output = "Data Set Creation\n\n";

				employee.clear();
				EmployeeDAO employeeDAO = new EmployeeDAO();
				List<Employee> employees = employeeDAO.findAll();
				for (Employee emp : employees) {
					employee.add(emp);
				}
				output += "Size of DataSetGeneric Employee: " + employee.size() + "\n";

				manager.clear();
				ManagerDAO managerDAO = new ManagerDAO();
				List<Manager> managers = managerDAO.findAll();
				for (Manager mgr : managers) {
					manager.add(mgr);
				}
				output += "Size of DataSetGeneric Manager: " + manager.size() + "\n";

				executive.clear();
				ExecutiveDAO executiveDAO = new ExecutiveDAO();
				List<Executive> executives = executiveDAO.findAll();
				for (Executive exec : executives) {
					executive.add(exec);
				}
				output += "Size of DataSetGeneric Executive: " + executive.size() + "\n";

				textArea4.setText(output);
			}
		});

		btClearAnswers.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				textArea4.setText("");
			}
		});

		btnExit.setOnAction(e -> {
			System.exit(0);
		});

		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 800, 600);
		scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		primaryStage.setTitle("Human Resources"); // Set title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}