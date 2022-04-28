package project.project1_hr.hr.emp;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EmployeeFX extends BorderPane {

	private EmployeeDAO employeeModel;

	private TableView<Employee> tblEmployee;
	private TextField txtId;
	private TextField txtName;
	private TextField txtSalary;

	private Button btnAddNew;
	private Button btnDelete;
	private Button btnUpdate;

	public EmployeeFX() {
		employeeModel = new EmployeeDAO();

		tblEmployee = new TableView<Employee>();
		getTblEmployee().setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 0) {
				if (getTblEmployee().getSelectionModel().getSelectedItem() != null) {
					Employee employee = getTblEmployee().getSelectionModel().getSelectedItem();
					getTxtId().setText(String.valueOf(employee.getId()));
					getTxtName().setText(employee.getEmployeeName());
					gettxtSalary().setText(String.valueOf(employee.getSalary()));
				}
			}
		});
		ScrollPane pane = new ScrollPane(tblEmployee);

		btnAddNew = new Button("Add New");
		getBtnAddNew().setOnAction(e -> {
			String name = getTxtName().getText();
			double salary = Double.parseDouble(gettxtSalary().getText());
			Employee employee = new Employee(0, name, salary);
			if (employeeModel.create(employee)) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Add Employee successfull", ButtonType.OK);
				alert.showAndWait();
				getTblEmployee().setItems(FXCollections.observableArrayList(employeeModel.findAll()));
			} else {
				Alert alert = new Alert(AlertType.WARNING, "Add Employee Unsuccessfull", ButtonType.OK);
				alert.showAndWait();
			}
			getTxtId().setText("");
			gettxtSalary().setText("");
			getTxtName().setText("");
		});

		btnDelete = new Button("Delete");
		DeleteHandlerClass deleteHandler = new DeleteHandlerClass();
		btnDelete.setOnAction(deleteHandler);

		btnUpdate = new Button("Update");
		UpdateHandlerClass updateHandler = new UpdateHandlerClass();
		btnUpdate.setOnAction(updateHandler);

		HBox hBoxBottom = new HBox(btnAddNew, btnDelete, btnUpdate);
		hBoxBottom.getStyleClass().add("hboxGreen");

		txtId = new TextField();
		txtId.setEditable(false);
		txtId.setVisible(false);
		txtName = new TextField();
		txtSalary = new TextField();
		txtSalary.setAlignment(Pos.CENTER_RIGHT);

		HBox hBoxMiddle = new HBox(new Label("Name:"), txtName, new Label("Salary:"), txtSalary);
		hBoxMiddle.getStyleClass().add("hboxWheat");

		setTop(pane);
		setCenter(hBoxMiddle);
		setBottom(hBoxBottom);
		FormatTable();
		getTblEmployee().setItems(FXCollections.observableArrayList(employeeModel.findAll()));
	}

	public Button getBtnDelete() {
		return btnDelete;
	}

	public Button getBtnUpdate() {
		return btnUpdate;
	}

	public Button getBtnAddNew() {
		return btnAddNew;
	}

	public TableView<Employee> getTblEmployee() {
		return tblEmployee;
	}

	public TextField getTxtId() {
		return txtId;
	}

	public TextField getTxtName() {
		return txtName;
	}

	public TextField gettxtSalary() {
		return txtSalary;
	}

	class DeleteHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			// System.out.println("delete button clicked");
			int id = Integer.parseInt(getTxtId().getText());
			Employee employee = employeeModel.find(id);

			String selection = employee.getEmployeeName();
			Alert alert = new Alert(AlertType.WARNING, "Delete " + selection + " ?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				employeeModel.delete(employee);
				getTblEmployee().setItems(FXCollections.observableArrayList(employeeModel.findAll()));
				getTxtId().setText("");
				gettxtSalary().setText("");
				getTxtName().setText("");
			}
		}
	}
	class UpdateHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			// System.out.println("Update button clicked");
			int id = Integer.parseInt(getTxtId().getText());
			String name = getTxtName().getText();
			double salary = Double.parseDouble(gettxtSalary().getText());
			Employee employee = new Employee(id, name, salary);
			if (employeeModel.edit(employee)) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Edit employee successfull", ButtonType.OK);
				alert.showAndWait();
				getTblEmployee().setItems(FXCollections.observableArrayList(employeeModel.findAll()));
			} else {
				Alert alert = new Alert(AlertType.WARNING, "Edit product failed", ButtonType.OK);
				alert.showAndWait();
			}
			getTxtId().setText("");
			gettxtSalary().setText("");
			getTxtName().setText("");
		}
	}

	public void FormatTable() {

		tblEmployee.setEditable(true);

		TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
		idCol.setMinWidth(100);
		idCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		idCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		TableColumn<Employee, String> nameCol = new TableColumn<>("Employee Name");
		nameCol.setMinWidth(400);
		nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Employee, String>>() {
			@Override
			public void handle(CellEditEvent<Employee, String> t) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setEmployeeName(t.getNewValue());
				Employee p = getTblEmployee().getSelectionModel().getSelectedItem();
				EmployeeDAO model = new EmployeeDAO();
				model.edit(p);
			}
		});

		TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
		salaryCol.setMinWidth(200);
		salaryCol.setCellValueFactory(new PropertyValueFactory<Employee, Double>("Salary"));
		salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		salaryCol.setOnEditCommit(new EventHandler<CellEditEvent<Employee, Double>>() {
			@Override
			public void handle(CellEditEvent<Employee, Double> t) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
				Employee p = getTblEmployee().getSelectionModel().getSelectedItem();
				EmployeeDAO model = new EmployeeDAO();
				model.edit(p);
			}
		});
		tblEmployee.getColumns().add(0, idCol);
		tblEmployee.getColumns().add(1, nameCol);
		tblEmployee.getColumns().add(2, salaryCol);
	}
}