package com.zhouhao.controller;

import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.DormitoryDaoImpl;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.Dormitory;
import com.zhouhao.entity.Student;
import com.zhouhao.service.BuildingService;
import com.zhouhao.service.DormitoryService;
import com.zhouhao.service.impl.BuildingServiceImpl;
import com.zhouhao.service.impl.DormitoryServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dormitory")
public class DormitoryServlet extends HttpServlet {
    DormitoryService dormitoryService = new DormitoryServiceImpl();
    BuildingService buildingService = new BuildingServiceImpl();
    DormitoryDao dormitoryDao = new DormitoryDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        switch (method){
            case "list":
                List<Dormitory> dormitories = dormitoryService.list();
                req.setAttribute("list", dormitories);
                req.setAttribute("buildingList", buildingService.list());
                req.getRequestDispatcher("dormitorymanager.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                dormitories = dormitoryService.search(key, value);
                req.setAttribute("list", dormitories);
                req.setAttribute("buildingList", buildingService.list());
                req.getRequestDispatcher("dormitorymanager.jsp").forward(req, resp);
                break;
            case "save":
                Integer buildingId = Integer.parseInt(req.getParameter("buildingId"));
                String name = req.getParameter("name");
                Integer type = Integer.parseInt(req.getParameter("type"));
                String telephone = req.getParameter("telephone");
                Dormitory dormitory = new Dormitory(null, buildingId, null, name, type, type, telephone);
                this.dormitoryService.save(dormitory);
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "update":
                dormitory = new Dormitory();
                dormitory.setId(Integer.valueOf(req.getParameter("id")));
                dormitory.setName(req.getParameter("name"));
                dormitory.setTelephone(req.getParameter("telephone"));
                this.dormitoryService.update(dormitory);
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "delete":
                Integer id = Integer.valueOf(req.getParameter("id"));
                this.dormitoryService.delete(id);
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "findByBuildingId":
                buildingId = Integer.valueOf(req.getParameter("buildingId"));
                List<Dormitory> dormitorys = this.dormitoryDao.findDormitoryByBuilding(buildingId);
                List<Student> students = this.studentDao.findStudentByDormitory(dormitorys.get(0).getId());
                Map<String, List> map = new HashMap<>();
                map.put("dormitoryList", dormitorys);
                map.put("studentList", students);
                JSONArray jsonArray = JSONArray.fromObject(map);
                resp.setContentType("text/json;charset=utf-8");
                resp.getWriter().write(jsonArray.toString());
                break;
        }
    }
}
