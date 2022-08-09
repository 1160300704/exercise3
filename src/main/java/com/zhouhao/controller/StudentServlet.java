package com.zhouhao.controller;

import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.Dormitory;
import com.zhouhao.entity.Student;
import com.zhouhao.service.DormitoryService;
import com.zhouhao.service.StudentService;
import com.zhouhao.service.impl.DormitoryServiceImpl;
import com.zhouhao.service.impl.StudentServiceImpl;
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

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    StudentService studentService = new StudentServiceImpl();
    DormitoryService dormitoryService = new DormitoryServiceImpl();
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        switch (method) {
            case "list":
                List<Student> list = studentService.list();
                req.setAttribute("list", list);
                List<Dormitory> dormitories = dormitoryService.availableList();
                req.setAttribute("dormitoryList", dormitories);
                req.getRequestDispatcher("studentmanager.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                List<Student> students = studentService.search(key, value);
                req.setAttribute("list", students);
                req.getRequestDispatcher("studentmanager.jsp").forward(req, resp);
                break;
            case "save":
                Integer dormitoryId = Integer.valueOf(req.getParameter("dormitoryId"));
                String number = req.getParameter("number");
                String name = req.getParameter("name");
                String gender = req.getParameter("gender");
                Student student = new Student(null, number, name, gender, dormitoryId, null, null, null);
                studentService.save(student);
                resp.sendRedirect("/student?method=list");
                break;
            case "update":
                String oldDormitoryId = req.getParameter("oldDormitoryId");
                Student student1 = new Student();
                student1.setId(Integer.valueOf(req.getParameter("id")));
                student1.setNumber(req.getParameter("number"));
                student1.setName(req.getParameter("name"));
                student1.setGender(req.getParameter("gender"));
                student1.setDormitoryId(Integer.valueOf(req.getParameter("dormitoryId")));
                studentService.update(student1, Integer.parseInt(oldDormitoryId));
                resp.sendRedirect("/student?method=list");
                break;
            case "delete":
                String id = req.getParameter("id");
                Integer dormitoryId1 = Integer.parseInt(req.getParameter("dormitoryId"));
                studentService.delete(id, dormitoryId1);
                resp.sendRedirect("/student?method=list");
                break;
            case "findByDormitoryId":
                dormitoryId = Integer.valueOf(req.getParameter("dormitoryId"));
                students = this.studentDao.findStudentByDormitory(dormitoryId);
                Map<String, List> map = new HashMap<>();
                map.put("studentList", students);
                JSONArray jsonArray = JSONArray.fromObject(map);
                resp.setContentType("text/json;charset=utf-8");
                resp.getWriter().write(jsonArray.toString());
                break;
        }
    }
}
