package com.example.activityonline;

import javafx.beans.property.*;

public class Student {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty course;
    private IntegerProperty yearLevel;

    public Student(int id, String name, String course, int yearLevel) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.course = new SimpleStringProperty(course);
        this.yearLevel = new SimpleIntegerProperty(yearLevel);
    }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getCourse() { return course.get(); }
    public int getYearLevel() { return yearLevel.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty courseProperty() { return course; }
    public IntegerProperty yearLevelProperty() { return yearLevel; }
}