module com.example.activityonline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.activityonline to javafx.fxml;
    exports com.example.activityonline;
}