package com.zhouhao.controller;

import com.zhouhao.entity.DormitoryAdmin;
import com.zhouhao.service.DormitoryAdminService;
import com.zhouhao.service.impl.DormitoryAdminServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dormitoryAdmin")
public class DormitoryAdminServlet extends HttpServlet {
    DormitoryAdminService dormitoryAdminService = new DormitoryAdminServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String method = req.getParameter("method");
        if (method.equals("list")) {
            req.setAttribute("list", this.dormitoryAdminService.list());
            req.getRequestDispatcher("adminmanager.jsp").forward(req, resp);
        }
        if(method.equals("search")){
            String key = req.getParameter("key");
            String value = req.getParameter("value");
            List<DormitoryAdmin> dormitoryAdmins = this.dormitoryAdminService.search(key, value);
            req.setAttribute("list", dormitoryAdmins);
            req.getRequestDispatcher("adminmanager.jsp").forward(req, resp);
        }
        if(method.equals("save")){
            DormitoryAdmin dormitoryAdmin = new DormitoryAdmin();
            dormitoryAdmin.setUsername(req.getParameter("username"));
            dormitoryAdmin.setPassword(req.getParameter("password"));
            dormitoryAdmin.setName(req.getParameter("name"));
            dormitoryAdmin.setGender(req.getParameter("gender"));
            dormitoryAdmin.setTelephone(req.getParameter("telephone"));
            dormitoryAdminService.save(dormitoryAdmin);
            resp.sendRedirect("/dormitoryAdmin?method=list");
        }
        if(method.equals("update")){
            DormitoryAdmin dormitoryAdmin = new DormitoryAdmin();
            dormitoryAdmin.setId(Integer.valueOf(req.getParameter("id")));
            dormitoryAdmin.setUsername(req.getParameter("username"));
            dormitoryAdmin.setPassword(req.getParameter("password"));
            dormitoryAdmin.setName(req.getParameter("name"));
            dormitoryAdmin.setGender(req.getParameter("gender"));
            dormitoryAdmin.setTelephone(req.getParameter("telephone"));
            dormitoryAdminService.update(dormitoryAdmin);
            resp.sendRedirect("/dormitoryAdmin?method=list");
        }
        if(method.equals("delete")){
            String id = req.getParameter("id");
            dormitoryAdminService.delete(Integer.valueOf(id));
            resp.sendRedirect("/dormitoryAdmin?method=list");
        }
    }
}
