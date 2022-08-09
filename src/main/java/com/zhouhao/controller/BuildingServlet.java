package com.zhouhao.controller;

import com.zhouhao.entity.Building;
import com.zhouhao.service.BuildingService;
import com.zhouhao.service.DormitoryAdminService;
import com.zhouhao.service.impl.BuildingServiceImpl;
import com.zhouhao.service.impl.DormitoryAdminServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/building")
public class BuildingServlet extends HttpServlet {
    BuildingService buildingService = new BuildingServiceImpl();
    DormitoryAdminService dormitoryAdminService = new DormitoryAdminServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        List<Building> buildings = null;

        switch (method){
            case "list":
                buildings = this.buildingService.list();
                req.setAttribute("list", buildings);
                req.setAttribute("adminList", this.dormitoryAdminService.list());
                req.getRequestDispatcher("buildingmanager.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                buildings = this.buildingService.search(key, value);
                req.setAttribute("list", buildings);
                req.setAttribute("adminList", this.dormitoryAdminService.list());
                req.getRequestDispatcher("buildingmanager.jsp").forward(req, resp);
                break;
            case "save":
                String name = req.getParameter("name");
                String introduction = req.getParameter("introduction");
                String adminId = req.getParameter("adminId");
                this.buildingService.save(name, introduction, adminId);
                resp.sendRedirect("/building?method=list");
                break;
            case "update":
                Building building = new Building();
                building.setId(Integer.valueOf(req.getParameter("id")));
                building.setName(req.getParameter("name"));
                building.setIntroduction(req.getParameter("introduction"));
                building.setAdminId(Integer.valueOf(req.getParameter("adminId")));
                this.buildingService.update(building);
                resp.sendRedirect("/building?method=list");
                break;
            case "delete":
                String id = req.getParameter("id");
                this.buildingService.delete(Integer.valueOf(id));
                resp.sendRedirect("/building?method=list");
                break;
        }
    }

    public static void main(String[] args) {
        switch (1){
            case 1:
                System.out.println(1);
            case 2:
                System.out.println(2);
                break;
            default:
                System.out.println(3);
        }
    }
}
