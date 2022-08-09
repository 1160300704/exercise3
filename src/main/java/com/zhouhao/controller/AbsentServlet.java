package com.zhouhao.controller;

import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.DormitoryDaoImpl;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.*;
import com.zhouhao.service.AbsentService;
import com.zhouhao.service.BuildingService;
import com.zhouhao.service.DormitoryService;
import com.zhouhao.service.impl.AbsentServiceImpl;
import com.zhouhao.service.impl.BuildingServiceImpl;
import com.zhouhao.service.impl.DormitoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/absent")
public class AbsentServlet extends HttpServlet {
    AbsentService absentService = new AbsentServiceImpl();
    BuildingService buildingService = new BuildingServiceImpl();
    DormitoryDao dormitoryDao = new DormitoryDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        switch (method){
            case "init":
                List<Building> buildings = this.buildingService.list();
                List<Dormitory> dormitorys = this.dormitoryDao.findDormitoryByBuilding(buildings.get(0).getId());
                List<Student> students = this.studentDao.findStudentByDormitory(dormitorys.get(0).getId());
                req.setAttribute("buildingList", buildings);
                req.setAttribute("dormitoryList", dormitorys);
                req.setAttribute("studentList", students);
                req.getRequestDispatcher("absentregister.jsp").forward(req, resp);
                break;

            case "save":
                Absent absent = new Absent();
                absent.setBuildingId(Integer.valueOf(req.getParameter("buildingId")));
                absent.setDormitoryId(Integer.valueOf(req.getParameter("dormitoryId")));
                absent.setStudentId(Integer.valueOf(req.getParameter("studentId")));
                absent.setReason(req.getParameter("reason"));
                absent.setCreateDate(req.getParameter("date"));
                HttpSession session = req.getSession();
                DormitoryAdmin dormitoryAdmin = (DormitoryAdmin) session.getAttribute("dormitoryAdmin");
                absent.setDormitoryAdminId(dormitoryAdmin.getId());
                absentService.save(absent);
                resp.sendRedirect("/absent?method=init");
                break;
            case "list":
                req.setAttribute("list", this.absentService.list());
                req.getRequestDispatcher("absentrecord.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list", this.absentService.search(key, value));
                req.getRequestDispatcher("absentrecord.jsp").forward(req, resp);
                break;
        }

    }
}
