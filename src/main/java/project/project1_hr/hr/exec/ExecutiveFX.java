package project.project1_hr.hr.exec;

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

public class ExecutiveFX extends BorderPane {

    private ExecutiveDAO executiveModel;

    private TableView<Executive> tblExecutive;
    private TextField txtId;
    private TextField txtName;
    private TextField txtSalary;
    private TextField txtDepartment;
    private TextField txtBonus;

    private Button btnAddNew;
    private Button btnDelete;
    private Button btnUpdate;

    public ExecutiveFX() {
        executiveModel = new ExecutiveDAO();

        tblExecutive = new TableView<Executive>();
        getTblExecutive().setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                if (getTblExecutive().getSelectionModel().getSelectedItem() != null) {
                    Executive executive = getTblExecutive().getSelectionModel().getSelectedItem();
                    getTxtId().setText(String.valueOf(executive.getId()));
                    getTxtName().setText(executive.getExecutiveName());
                    gettxtSalary().setText(String.valueOf(executive.getSalary()));
                    gettxtDepartment().setText(String.valueOf(executive.getDepartment()));
                    gettxtBonus().setText(String.valueOf(executive.getBonus()));
                }
            }
        });
        ScrollPane pane = new ScrollPane(tblExecutive);

        btnAddNew = new Button("Add New");
        getBtnAddNew().setOnAction(e -> {
            String name = getTxtName().getText();
            double salary = Double.parseDouble(gettxtSalary().getText());
            String department = gettxtDepartment().getText();
            double bonus = Double.parseDouble(gettxtBonus().getText());
            Executive executive = new Executive(0, name, salary, department, bonus);
            System.out.println(executive);
            if (executiveModel.create(executive)) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Add Executive successfull", ButtonType.OK);
                alert.showAndWait();
                getTblExecutive().setItems(FXCollections.observableArrayList(executiveModel.findAll()));
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Add Executive Unsuccessfull", ButtonType.OK);
                alert.showAndWait();
            }
            getTxtId().setText("");
            gettxtSalary().setText("");
            getTxtName().setText("");
            gettxtDepartment().setText("");
            gettxtBonus().setText("");
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
        txtSalary.setAlignment(Pos.CENTER_LEFT);
        txtDepartment = new TextField();
        txtDepartment.setAlignment(Pos.CENTER);
        txtBonus = new TextField();
        txtBonus.setAlignment(Pos.CENTER_RIGHT);

        HBox hBoxMiddle = new HBox(new Label("Name:"), txtName, new Label("Salary:"),
                txtSalary, new Label("Department:"), txtDepartment, new Label("Bonus:"), txtBonus);
        hBoxMiddle.getStyleClass().add("hboxWheat");

        setTop(pane);
        setCenter(hBoxMiddle);
        setBottom(hBoxBottom);
        FormatTable();
        getTblExecutive().setItems(FXCollections.observableArrayList(executiveModel.findAll()));
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

    public TableView<Executive> getTblExecutive() {
        return tblExecutive;
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
    public TextField gettxtBonus() {
        return txtBonus;
    }


    class DeleteHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // System.out.println("delete button clicked");
            int id = Integer.parseInt(getTxtId().getText());
            Executive Executive = executiveModel.find(id);

            String selection = Executive.getExecutiveName();
            Alert alert = new Alert(AlertType.WARNING, "Delete " + selection + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                executiveModel.delete(Executive);
                getTblExecutive().setItems(FXCollections.observableArrayList(executiveModel.findAll()));
                getTxtId().setText("");
                gettxtSalary().setText("");
                getTxtName().setText("");
                gettxtDepartment().setText("");
                gettxtBonus().setText("");
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
            double bonus = Double.parseDouble(gettxtBonus().getText());
            Executive Executive = new Executive(id, name, salary, department, bonus);
            if (executiveModel.edit(Executive)) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Edit Executive sucessfull", ButtonType.OK);
                alert.showAndWait();
                getTblExecutive().setItems(FXCollections.observableArrayList(executiveModel.findAll()));
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Edit product failed", ButtonType.OK);
                alert.showAndWait();
            }
            getTxtId().setText("");
            gettxtSalary().setText("");
            getTxtName().setText("");
            gettxtDepartment().setText("");
            gettxtBonus().setText("");
        }
    }

    public void FormatTable() {

        tblExecutive.setEditable(true);

        TableColumn<Executive, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<Executive, Integer>("id"));
        idCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        TableColumn<Executive, String> nameCol = new TableColumn<>("Executive Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<Executive, String>("ExecutiveName"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Executive, String>>() {
            @Override
            public void handle(CellEditEvent<Executive, String> t) {
                ((Executive) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setExecutiveName(t.getNewValue());
                Executive p = getTblExecutive().getSelectionModel().getSelectedItem();
                ExecutiveDAO model = new ExecutiveDAO();
                model.edit(p);
            }
        });

        TableColumn<Executive, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setMinWidth(200);
        salaryCol.setCellValueFactory(new PropertyValueFactory<Executive, Double>("Salary"));
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        salaryCol.setOnEditCommit(new EventHandler<CellEditEvent<Executive, Double>>() {
            @Override
            public void handle(CellEditEvent<Executive, Double> t) {
                ((Executive) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
                Executive p = getTblExecutive().getSelectionModel().getSelectedItem();
                ExecutiveDAO model = new ExecutiveDAO();
                model.edit(p);
            }
        });

        TableColumn<Executive, String> deptCol = new TableColumn<>("Department");
        deptCol.setMinWidth(200);
        deptCol.setCellValueFactory(new PropertyValueFactory<Executive, String>("Department"));
        deptCol.setCellFactory(TextFieldTableCell.forTableColumn());
        deptCol.setOnEditCommit(new EventHandler<CellEditEvent<Executive, String>>() {
            @Override
            public void handle(CellEditEvent<Executive, String> t) {
                ((Executive) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDepartment(t.getNewValue());
                Executive p = getTblExecutive().getSelectionModel().getSelectedItem();
                ExecutiveDAO model = new ExecutiveDAO();
                model.edit(p);
            }
        });

        TableColumn<Executive, Double> bonusCol = new TableColumn<>("Bonus");
        bonusCol.setMinWidth(200);
        bonusCol.setCellValueFactory(new PropertyValueFactory<Executive, Double>("Bonus"));
        bonusCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        bonusCol.setOnEditCommit(new EventHandler<CellEditEvent<Executive, Double>>() {
            @Override
            public void handle(CellEditEvent<Executive, Double> t) {
                ((Executive) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setBonus(t.getNewValue());
                Executive p = getTblExecutive().getSelectionModel().getSelectedItem();
                ExecutiveDAO model = new ExecutiveDAO();
                model.edit(p);
            }
        });


        tblExecutive.getColumns().add(0, idCol);
        tblExecutive.getColumns().add(1, nameCol);
        tblExecutive.getColumns().add(2, salaryCol);
        tblExecutive.getColumns().add(3, deptCol);
        tblExecutive.getColumns().add(4, bonusCol);
    }
}

