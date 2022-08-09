package com.zhouhao.dao;

import com.zhouhao.entity.Student;
import java.util.List;

public interface StudentDao {
    public List<Student> list();
    public List<Student> search(String key, String value);
    public Integer save(Student student);
    public Integer update(Student student);
    public Integer delete(String id);
    List<Integer> findIdByDormitory(Integer dormitoryId);
    List<Student> findStudentByDormitory(Integer dormitoryId);
    Integer updateDormitory(Integer studentId, Integer dormitoryId);
    public List<Student> moveoutList();
    public List<Student> searchForMoveout(String key, String value);
    Integer updateStateById(Integer id);
}
