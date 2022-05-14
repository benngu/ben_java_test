package com.ben;

import java.util.List;
import java.util.Map;

public interface Service {

    boolean verifyLogin(String userId, String password);

    List<Student> getStudentList();

    Map<String, Double> genDeptPassPercentage();
}
