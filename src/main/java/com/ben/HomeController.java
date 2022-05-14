package com.ben;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/Welcome")
public class HomeController extends HttpServlet {

    private static String studentDataMap = JSON.toJSONString(Arrays.stream(Student.values())
                .collect(Collectors.toMap(Student::getStudentId, s -> s.getStudentInfo(s.getStudentId()))));

    private static Service homeService = new ServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // verify if there is user in the session
        if (req.getSession(false).getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath()+"/login");
        }
        else {
            String currentSessionUserId = String.valueOf(req.getSession().getAttribute("user"));
            List<Student> studentList = homeService.getStudentList();

            if (!studentList.isEmpty()) {
                Map<String, Double> deptPassPercentageMap = homeService.genDeptPassPercentage();
                req.setAttribute("studentDataMap", studentDataMap);
                req.setAttribute("studentList", studentList);
                req.setAttribute("deptPassPercentMap", deptPassPercentageMap);
            }
            req.setAttribute("userId", currentSessionUserId);
            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String method = req.getParameter("action");
        switch (method) {
            case "logout":
                userLogout(req, resp);
                break;
            case "getStudentName":
                getStudentName(req, resp);
                break;
        }
    }

    private void userLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }

    private void getStudentName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String studentName = Arrays.stream(Student.values()).filter(student -> student.getStudentId().equals(req.getParameter("studentId"))).map(Student::getStudentName).findFirst().get();
        JSONObject studentJSON = new JSONObject();
        studentJSON.put("studentName", studentName);
        resp.getWriter().write(studentJSON.toString());
    }
}
