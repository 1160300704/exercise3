package com.zhouhao.service;

import com.zhouhao.entity.Student;
import java.util.List;

public interface StudentService {
    public List<Student> list();
    public List<Student> search(String key, String value);
    public void save(Student student);
    public void update(Student student, Integer oldDormitoryId);
    void delete(String id, Integer dormitoryId1);
    public List<Student> moveoutList();
    public List<Student> searchForMoveout(String key, String value);
}
