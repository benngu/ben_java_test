package com.ben;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Student {

    S1("S1", "Alexander", "Dep 1", 35),
    S2("S2", "Benjamin", "Dep 1", 70),
    S3("S3", "Cassandra", "Dep 1", 60),
    S4("S4", "David", "Dep 1", 90),
    S5("S5", "Eren", "Dep 2", 30),
    S6("S6", "Fred", "Dep 3", 32),
    S7("S7", "Grim", "Dep 3", 70),
    S8("S8", "Hayden", "Dep 3", 20);

    private String studentId;
    private String studentName;
    private String department;
    private int mark;

    Student (String id, String name, String department, int mark) {
        this.studentId = id;
        this.studentName = name;
        this.department = department;
        this.mark = mark;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDepartment() {
        return department;
    }

    public int getMark() {
        return mark;
    }

    public Map<String, Object> getStudentInfo (String studentId) {
        Student student = Arrays.stream(Student.values()).filter(stud -> stud.getStudentId().equals(studentId)).findFirst().orElse(null);
        Map<String, Object> studentDataMap = new HashMap<>();
        studentDataMap.put("studentId", student.studentId);
        studentDataMap.put("studentName", student.studentName);
        studentDataMap.put("department", student.department);
        studentDataMap.put("marks", student.mark);
        return studentDataMap;
    }
}
