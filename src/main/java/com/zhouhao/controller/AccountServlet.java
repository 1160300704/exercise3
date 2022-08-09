package com.zhouhao.controller;

import com.zhouhao.dto.DormitoryAdminDto;
import com.zhouhao.dto.SystemAdminDto;
import com.zhouhao.service.DormitoryAdminService;
import com.zhouhao.service.SystemAdminService;
import com.zhouhao.service.impl.DormitoryAdminServiceImpl;
import com.zhouhao.service.impl.SystemAdminServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    SystemAdminService systemAdminService = new SystemAdminServiceImpl();
    DormitoryAdminService dormitoryAdminService = new DormitoryAdminServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");

        if ("systemAdmin".equals(type)) {
            String method = req.getParameter("method");
            if (method.equalsIgnoreCase("login")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");

                SystemAdminDto systemAdminDto = systemAdminService.login(username, password);
                switch (systemAdminDto.getCode()) {
                    case -1:
                        req.setAttribute("usernameError", "用户名不存在");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                        break;
                    case 0:
                        req.setAttribute("passwordError", "密码错误");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                        break;
                    case 1:
                    default:
                        HttpSession session = req.getSession();
                        session.setAttribute("systemAdmin", systemAdminDto.getSystemAdmin());
                        resp.sendRedirect("systemadmin.jsp");
                }
            }
        }
        else if("dormitoryAdmin".equals(type)){
            String method = req.getParameter("method");
            if (method.equals("login")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");

                DormitoryAdminDto dormitoryAdminDto = this.dormitoryAdminService.login(username, password);
                switch (dormitoryAdminDto.getCode()) {
                    case -1:
                        req.setAttribute("usernameError", "用户名不存在");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                        break;
                    case 0:
                        req.setAttribute("passwordError", "密码错误");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                        break;
                    case 1:
                    default:
                        HttpSession session = req.getSession();
                        session.setAttribute("dormitoryAdmin", dormitoryAdminDto.getDormitoryAdmin());
                        resp.sendRedirect("dormitoryadmin.jsp");
                }
            }
        }else{
            String method = req.getParameter("method");
            if (method.equalsIgnoreCase("logout")) {
                req.getSession().invalidate();
                resp.sendRedirect("login.jsp");
            }
        }
    }
}
