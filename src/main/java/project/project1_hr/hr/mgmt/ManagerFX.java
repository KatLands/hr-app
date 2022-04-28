package project.project1_hr.hr.mgmt;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ManagerFX extends BorderPane {

    private ManagerDAO managerModel;

    private TableView<Manager> tblManager;
    private TextField txtId;
    private TextField txtName;
    private TextField txtSalary;
    private TextField txtDepartment;

    private Button btnAddNew;
    private Button btnDelete;
    private Button btnUpdate;

    public ManagerFX() {
        managerModel = new ManagerDAO();

        tblManager = new TableView<Manager>();
        gettblManager().setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                if (gettblManager().getSelectionModel().getSelectedItem() != null) {
                    Manager manager = gettblManager().getSelectionModel().getSelectedItem();
                    getTxtId().setText(String.valueOf(manager.getId()));
                    getTxtName().setText(manager.getManagerName());
                    gettxtSalary().setText(String.valueOf(manager.getSalary()));
                    gettxtDepartment().setText(String.valueOf(manager.getDepartment()));
                }
            }
        });
        ScrollPane pane = new ScrollPane(tblManager);

        btnAddNew = new Button("Add New");
        getBtnAddNew().setOnAction(e -> {
            String name = getTxtName().getText();
            double salary = Double.parseDouble(gettxtSalary().getText());
            String department = gettxtDepartment().getText();
            Manager manager = new Manager(0, name, salary, department);
            if (managerModel.create(manager)) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Add Manager successfull", ButtonType.OK);
                alert.showAndWait();
                gettblManager().setItems(FXCollections.observableArrayList(managerModel.findAll()));
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Add Manager Unsuccessfull", ButtonType.OK);
                alert.showAndWait();
            }
            getTxtId().setText("");
            gettxtSalary().setText("");
            getTxtName().setText("");
            gettxtDepartment().setText("");
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
        txtSalary.setAlignment(Pos.CENTER);
        txtDepartment = new TextField();
        txtDepartment.setAlignment(Pos.CENTER_RIGHT);


        HBox hBoxMiddle = new HBox(new Label("Name:"), txtName, new Label("Salary:"),
                txtSalary, new Label("Department:"), txtDepartment);
        hBoxMiddle.getStyleClass().add("hboxWheat");

        setTop(pane);
        setCenter(hBoxMiddle);
        setBottom(hBoxBottom);
        FormatTable();
        gettblManager().setItems(FXCollections.observableArrayList(managerModel.findAll()));
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

    public TableView<Manager> gettblManager() {
        return tblManager;
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

    public TextField gettxtDepartment() {
        return txtDepartment;
    }


    class DeleteHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // System.out.println("delete button clicked");
            int id = Integer.parseInt(getTxtId().getText());
            Manager manager = managerModel.find(id);

            String selection = manager.getManagerName();
            Alert alert = new Alert(AlertType.WARNING, "Delete " + selection + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                managerModel.delete(manager);
                gettblManager().setItems(FXCollections.observableArrayList(managerModel.findAll()));
                getTxtId().setText("");
                gettxtSalary().setText("");
                getTxtName().setText("");
                gettxtDepartment().setText("");
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
            String department = gettxtDepartment().getText();
            Manager manager = new Manager(id, name, salary, department);
            if (managerModel.edit(manager)) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Edit Manager successfull", ButtonType.OK);
                alert.showAndWait();
                gettblManager().setItems(FXCollections.observableArrayList(managerModel.findAll()));
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Edit Manager failed", ButtonType.OK);
                alert.showAndWait();
            }
            getTxtId().setText("");
            gettxtSalary().setText("");
            getTxtName().setText("");
            gettxtDepartment().setText("");
        }
    }

    public void FormatTable() {

        tblManager.setEditable(true);

        TableColumn<Manager, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<Manager, Integer>("id"));
        idCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        TableColumn<Manager, String> nameCol = new TableColumn<>("Manager Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<Manager, String>("managerName"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Manager, String>>() {
            @Override
            public void handle(CellEditEvent<Manager, String> t) {
                ((Manager) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setManagerName(t.getNewValue());
                Manager p = gettblManager().getSelectionModel().getSelectedItem();
                ManagerDAO model = new ManagerDAO();
                model.edit(p);
            }
        });

        TableColumn<Manager, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setMinWidth(200);
        salaryCol.setCellValueFactory(new PropertyValueFactory<Manager, Double>("Salary"));
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        salaryCol.setOnEditCommit(new EventHandler<CellEditEvent<Manager, Double>>() {
            @Override
            public void handle(CellEditEvent<Manager, Double> t) {
                ((Manager) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
                Manager p = gettblManager().getSelectionModel().getSelectedItem();
                ManagerDAO model = new ManagerDAO();
                model.edit(p);
            }
        });

        TableColumn<Manager, String> deptCol = new TableColumn<>("Department");
        deptCol.setMinWidth(200);
        deptCol.setCellValueFactory(new PropertyValueFactory<Manager, String>("Department"));
        deptCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deptCol.setOnEditCommit(new EventHandler<CellEditEvent<Manager, String>>() {
            @Override
            public void handle(CellEditEvent<Manager, String> t) {
                ((Manager) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDepartment(t.getNewValue());
                Manager p = gettblManager().getSelectionModel().getSelectedItem();
                ManagerDAO model = new ManagerDAO();
                model.edit(p);
            }
        });


        tblManager.getColumns().add(0, idCol);
        tblManager.getColumns().add(1, nameCol);
        tblManager.getColumns().add(2, salaryCol);
        tblManager.getColumns().add(3, deptCol);
    }
}

