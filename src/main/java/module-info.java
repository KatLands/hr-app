module project.project1_hr {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires junit;


    opens project.project1_hr to javafx.controls, javafx.graphics, javafx.fxml ;
    opens project.project1_hr.hr.emp to javafx.graphics, javafx.base;
    opens project.project1_hr.hr.mgmt to javafx.graphics, javafx.base;
    opens project.project1_hr.hr.exec to javafx.graphics, javafx.base;

    exports project.project1_hr;



}