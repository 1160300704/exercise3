package com.zhouhao.controller;

import com.zhouhao.entity.Moveout;
import com.zhouhao.entity.Student;
import com.zhouhao.service.MoveoutService;
import com.zhouhao.service.StudentService;
import com.zhouhao.service.impl.MoveoutServiceImpl;
import com.zhouhao.service.impl.StudentServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/moveout")
public class MoveoutServlet extends HttpServlet {
    StudentService studentService = new StudentServiceImpl();
    MoveoutService moveoutService = new MoveoutServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        switch (method){
            case "list":
                List<Student> students = this.studentService.moveoutList();
                req.setAttribute("list", students);
                req.getRequestDispatcher("moveoutregister.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                students = this.studentService.searchForMoveout(key, value);
                req.setAttribute("list", students);
                req.getRequestDispatcher("moveoutregister.jsp").forward(req, resp);
                break;
            case "moveout":
                String studentId = req.getParameter("studentId");
                String dormitoryId = req.getParameter("dormitoryId");
                String reason = req.getParameter("reason");
                Moveout moveout = new Moveout();
                moveout.setStudentId(Integer.valueOf(studentId));
                moveout.setDormitoryId(Integer.valueOf(dormitoryId));
                moveout.setReason(reason);
                moveoutService.save(moveout);
                resp.sendRedirect("/moveout?method=list");
                break;
            case "record":
                List<Moveout> moveouts = this.moveoutService.list();
                req.setAttribute("list", moveouts);
                req.getRequestDispatcher("moveoutrecord.jsp").forward(req, resp);
                break;
            case "recordSearch":
                key = req.getParameter("key");
                value = req.getParameter("value");
                moveouts = this.moveoutService.search(key, value);
                req.setAttribute("list", moveouts);
                req.getRequestDispatcher("moveoutrecord.jsp").forward(req, resp);
                break;
        }
    }
}
