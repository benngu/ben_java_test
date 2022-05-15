package com.ben;

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

    /**
     * Retrieve the passing percentage of every department
     */
    @Override
    public Map<String, Double> genDeptPassPercentage() {
        Map<String, Double> deptPassMap = new HashMap<>();

        // groups the list of students that belongs to the same department
        Map<String, List<Student>> departmentMap = Arrays.stream(Student.values()).sorted(Comparator.comparing(Student::getDepartment)).collect(Collectors.groupingBy(Student::getDepartment));

        // calculates the pass percentage based on the looping of the department map object
        for (Map.Entry<String, List<Student>> department : departmentMap.entrySet()) {
            String departmentName = department.getKey();
            int totalStudents = department.getValue().size();
            int totalStudentPass = department.getValue().stream().mapToInt(student -> student.getMark() >= 40 ? 1 : 0).sum();
            double passPercentage = Double.parseDouble(decimalFormat.format((double) totalStudentPass /(double) totalStudents * 100));
            deptPassMap.put(departmentName, passPercentage);
        }
        return deptPassMap;
    }
}
