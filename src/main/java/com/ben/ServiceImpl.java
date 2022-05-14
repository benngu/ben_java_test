package com.ben;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static Map<String, String> userAccountMap = new HashMap<String, String>(){{
        put("ben", "123");
        put("admin", "123");
    }};

    /**
     * Verify user login account
     */
    @Override
    public boolean verifyLogin(String userId, String password) {
        if (!userAccountMap.containsKey(userId)) {
            return false;
        }
        return userAccountMap.get(userId).equals(password);
    }

    /**
     * Retrieve student records
     */
    @Override
    public List<Student> getStudentList() {
        return Arrays.stream(Student.values()).sorted(Comparator.comparing(Student::getDepartment).thenComparing(Student::getStudentId)).collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> genDeptPassPercentage() {
        Map<String, Double> deptPassMap = new HashMap<>();
        List<List<Student>> allDeptStudentList = new ArrayList<>();

        // find and get all department names
        String departments = Arrays.stream(Student.values()).map(Student::getDepartment).distinct().collect(Collectors.joining(";"));

        // find the list of students based on the department name
        for (String department : departments.split(";")) {
            List<Student> deptStudentList = Arrays.stream(Student.values()).filter(student -> student.getDepartment().equals(department)).collect(Collectors.toList());
            // store studentList into a main list
            allDeptStudentList.add(new ArrayList<>(deptStudentList));
        }

        // calculate the pass percentage of every department from the main list
        for (List<Student> studentList : allDeptStudentList) {
            String currentDept = studentList.get(0).getDepartment();
            int totalStudentPass = studentList.stream().mapToInt(student -> student.getMark() >= 40 ? 1 : 0).sum();
            double passPercentage = Double.parseDouble(decimalFormat.format((double) totalStudentPass /(double) studentList.size() * 100));
            deptPassMap.put(currentDept, passPercentage);
        }
        return deptPassMap;
    }
}
