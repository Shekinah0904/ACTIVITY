package com.example.activityonline;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class Controller {

    @FXML private TextField txtName;
    @FXML private TextField txtCourse;
    @FXML private ChoiceBox<YearLevel> cbYear;

    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colCourse;
    @FXML private TableColumn<Student, Integer> colYear;

    private ObservableList<Student> list = FXCollections.observableArrayList();
    private int selectedId = -1;

    @FXML
    public void initialize() {

        cbYear.getItems().setAll(YearLevel.values());

        colId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colName.setCellValueFactory(d -> d.getValue().nameProperty());
        colCourse.setCellValueFactory(d -> d.getValue().courseProperty());
        colYear.setCellValueFactory(d -> d.getValue().yearLevelProperty().asObject());

        loadData();

        table.setOnMouseClicked(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                selectedId = s.getId();
                txtName.setText(s.getName());
                txtCourse.setText(s.getCourse());
                cbYear.setValue(YearLevel.values()[s.getYearLevel() - 1]);
            }
        });
    }

    @FXML
    private void addStudent() {

        String sql = "INSERT INTO students(name, course, year_level) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, txtName.getText());
            pst.setString(2, txtCourse.getText());

            // 🔥 FIX HERE (IMPORTANT)
            pst.setInt(3, cbYear.getValue().ordinal() + 1);

            pst.executeUpdate();
            loadData();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateStudent() {

        String sql = "UPDATE students SET name=?, course=?, year_level=? WHERE id=?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, txtName.getText());
            pst.setString(2, txtCourse.getText());
            pst.setInt(3, cbYear.getValue().ordinal() + 1);
            pst.setInt(4, selectedId);

            pst.executeUpdate();
            loadData();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent() {

        String sql = "DELETE FROM students WHERE id=?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, selectedId);

            pst.executeUpdate();
            loadData();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        txtName.clear();
        txtCourse.clear();
        cbYear.setValue(null);
        selectedId = -1;
    }

    private void loadData() {

        list.clear();

        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("year_level")
                ));
            }

            table.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}