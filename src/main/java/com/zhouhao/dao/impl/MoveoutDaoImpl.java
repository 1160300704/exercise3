package com.zhouhao.dao.impl;

import com.zhouhao.dao.MoveoutDao;
import com.zhouhao.entity.Moveout;
import com.zhouhao.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveoutDaoImpl implements MoveoutDao {
    @Override
    public Integer save(Moveout moveout) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "INSERT INTO moveout VALUES(NULL, ?, ?, ?, ?)";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, moveout.getStudentId());
            preparedStatement.setObject(2, moveout.getDormitoryId());
            preparedStatement.setObject(3, moveout.getReason());
            preparedStatement.setObject(4, moveout.getCreateDate());

            result = preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public List<Moveout> list() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT m.id, m.student_id, s.`name`, m.dormitory_id, d.`name`, m.reason, m.create_date FROM moveout m, dormitory d, student s WHERE m.student_id = s.id AND m.dormitory_id = d.id";
        List<Moveout> moveouts = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Moveout moveout = new Moveout();
                moveout.setId(resultSet.getInt(1));
                moveout.setStudentId(resultSet.getInt(2));
                moveout.setStudentName(resultSet.getString(3));
                moveout.setDormitoryId(resultSet.getInt(4));
                moveout.setDormitoryName(resultSet.getString(5));
                moveout.setReason(resultSet.getString(6));
                moveout.setCreateDate(resultSet.getString(7));
                moveouts.add(moveout);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }

        return moveouts;
    }

    @Override
    public List<Moveout> search(String key, String value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT m.id, m.student_id, s.`name`, m.dormitory_id, d.`name`, m.reason, m.create_date FROM moveout m, dormitory d, student s WHERE m.student_id = s.id AND m.dormitory_id = d.id AND " + key + ".name LIKE ?";
        List<Moveout> moveouts = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + value + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Moveout moveout = new Moveout();
                moveout.setId(resultSet.getInt(1));
                moveout.setStudentId(resultSet.getInt(2));
                moveout.setStudentName(resultSet.getString(3));
                moveout.setDormitoryId(resultSet.getInt(4));
                moveout.setDormitoryName(resultSet.getString(5));
                moveout.setReason(resultSet.getString(6));
                moveout.setCreateDate(resultSet.getString(7));
                moveouts.add(moveout);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }

        return moveouts;
    }
}
