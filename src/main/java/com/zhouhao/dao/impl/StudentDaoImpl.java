package com.zhouhao.dao.impl;

import com.zhouhao.dao.StudentDao;
import com.zhouhao.entity.Student;
import com.zhouhao.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> list() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT s.id, d.id, d.`name`, s.number, s.`name`, s.gender, s.state, s.create_date FROM student s, dormitory d WHERE s.dormitory_id = d.id";
        List<Student> students = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student =new Student();
                student.setId((Integer) resultSet.getObject(1));
                student.setDormitoryId((Integer) resultSet.getObject(2));
                student.setDormitoryName((String) resultSet.getObject(3));
                student.setNumber((String) resultSet.getObject(4));
                student.setName((String) resultSet.getObject(5));
                student.setGender((String) resultSet.getObject(6));
                student.setState((String) resultSet.getObject(7));
                student.setCreateDate((String) resultSet.getObject(8));
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return students;
    }

    @Override
    public List<Student> search(String key, String value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT s.id, d.id, d.`name`, s.number, s.`name`, s.gender, s.state, s.create_date FROM student s, dormitory d WHERE s.dormitory_id = d.id AND s."+key+" LIKE ?";
        List<Student> students = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, "%" +value + "%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student =new Student();
                student.setId((Integer) resultSet.getObject(1));
                student.setDormitoryId((Integer) resultSet.getObject(2));
                student.setDormitoryName((String) resultSet.getObject(3));
                student.setNumber((String) resultSet.getObject(4));
                student.setName((String) resultSet.getObject(5));
                student.setGender((String) resultSet.getObject(6));
                student.setState((String) resultSet.getObject(7));
                student.setCreateDate((String) resultSet.getObject(8));
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return students;
    }

    @Override
    public Integer save(Student student) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "INSERT INTO student VALUES(NULL,?,?,?,?,?,?)";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, student.getNumber());
            preparedStatement.setObject(2, student.getName());
            preparedStatement.setObject(3, student.getGender());
            preparedStatement.setObject(4, student.getDormitoryId());
            preparedStatement.setObject(5, student.getState());
            preparedStatement.setObject(6, student.getCreateDate());

            result = preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public Integer update(Student student) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE student SET number = ?, `name` = ?, gender = ?, dormitory_id = ? WHERE id = ?";
        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, student.getNumber());
            preparedStatement.setObject(2, student.getName());
            preparedStatement.setObject(3, student.getGender());
            preparedStatement.setObject(4, student.getDormitoryId());
            preparedStatement.setObject(5, student.getId());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public Integer delete(String id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "DELETE FROM student WHERE id = ?";
        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public List<Integer> findIdByDormitory(Integer dormitoryId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id FROM student WHERE dormitory_id = ?";
        List<Integer> studentIds = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, dormitoryId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                studentIds.add((Integer) resultSet.getObject(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return studentIds;
    }

    @Override
    public List<Student> findStudentByDormitory(Integer dormitoryId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id, name FROM student WHERE dormitory_id = ?";
        List<Student> students = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, dormitoryId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return students;
    }

    @Override
    public Integer updateDormitory(Integer studentId, Integer dormitoryId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE student SET dormitory_id = ? WHERE id = ?";
        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, dormitoryId);
            preparedStatement.setObject(2, studentId);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public List<Student> moveoutList() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT s.id, d.id, d.`name`, s.number, s.`name`, s.gender, s.state FROM student s, dormitory d WHERE s.dormitory_id = d.id AND s.state = '入住'";
        List<Student> students = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student =new Student();
                student.setId((Integer) resultSet.getObject(1));
                student.setDormitoryId((Integer) resultSet.getObject(2));
                student.setDormitoryName((String) resultSet.getObject(3));
                student.setNumber((String) resultSet.getObject(4));
                student.setName((String) resultSet.getObject(5));
                student.setGender((String) resultSet.getObject(6));
                student.setState((String) resultSet.getObject(7));
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return students;
    }

    @Override
    public List<Student> searchForMoveout(String key, String value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT s.id, d.id, d.`name`, s.number, s.`name`, s.gender, s.state FROM student s, dormitory d WHERE s.dormitory_id = d.id AND s.state = '入住' AND s." + key + " LIKE ?";
        List<Student> students = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + value + "%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student =new Student();
                student.setId((Integer) resultSet.getObject(1));
                student.setDormitoryId((Integer) resultSet.getObject(2));
                student.setDormitoryName((String) resultSet.getObject(3));
                student.setNumber((String) resultSet.getObject(4));
                student.setName((String) resultSet.getObject(5));
                student.setGender((String) resultSet.getObject(6));
                student.setState((String) resultSet.getObject(7));
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return students;
    }

    @Override
    public Integer updateStateById(Integer id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE student SET state = '迁出' WHERE id = ?";
        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }
}
