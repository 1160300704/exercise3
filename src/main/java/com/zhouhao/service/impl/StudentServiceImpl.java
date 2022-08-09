package com.zhouhao.service.impl;

import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.DormitoryDaoImpl;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.Student;
import com.zhouhao.service.StudentService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    StudentDao studentDao = new StudentDaoImpl();
    DormitoryDao dormitoryDao = new DormitoryDaoImpl();

    @Override
    public List<Student> list() {
        return this.studentDao.list();
    }

    @Override
    public List<Student> search(String key, String value) {
        if(value != null)
            return this.studentDao.search(key, value);
        else
            return this.studentDao.list();
    }

    @Override
    public void save(Student student) {
        student.setState("入住");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setCreateDate(simpleDateFormat.format(new Date()));
        Integer subAvailable = dormitoryDao.subAvailable(student.getDormitoryId());
        Integer save = this.studentDao.save(student);
        if(save != 1 || subAvailable != 1)
            throw new RuntimeException("添加学生信息失败!");
    }

    @Override
    public void update(Student student, Integer oldDormitoryId) {
        Integer update = studentDao.update(student);
        Integer addAvailable = dormitoryDao.addAvailable(oldDormitoryId);
        Integer subAvailable = dormitoryDao.subAvailable(student.getDormitoryId());
        if(update != 1 || subAvailable != 1 || addAvailable != 1)
            throw new RuntimeException("学生信息修改失败！");
    }

    @Override
    public void delete(String id, Integer dormitoryId1) {
        Integer delete = studentDao.delete(id);
        Integer addAvailable = dormitoryDao.addAvailable(dormitoryId1);
        if(delete != 1 || addAvailable != 1)
            throw new RuntimeException("学生信息删除失败");
    }

    @Override
    public List<Student> moveoutList() {
        return this.studentDao.moveoutList();
    }

    @Override
    public List<Student> searchForMoveout(String key, String value) {
        if (value == null)
            return this.studentDao.moveoutList();
        return this.studentDao.searchForMoveout(key, value);
    }
}
