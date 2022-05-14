package com.ben;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {

    private static Service loginService = new ServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String userPass = req.getParameter("password");

        boolean isAuthenticated = loginService.verifyLogin(userId, userPass);
        if (!isAuthenticated) {
            req.setAttribute("result", "Invalid credentials, please try again!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        else {
            req.getSession().setAttribute("user", userId);
            resp.sendRedirect(req.getContextPath()+"/welcome");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
