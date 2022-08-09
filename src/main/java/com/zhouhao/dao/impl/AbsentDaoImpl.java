package com.zhouhao.dao.impl;

import com.zhouhao.dao.AbsentDao;
import com.zhouhao.entity.Absent;
import com.zhouhao.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbsentDaoImpl implements AbsentDao {
    @Override
    public Integer save(Absent absent) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "INSERT INTO absent VALUES(NULL,?,?,?,?,?,?)";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, absent.getBuildingId());
            preparedStatement.setObject(2, absent.getDormitoryId());
            preparedStatement.setObject(3, absent.getStudentId());
            preparedStatement.setObject(4, absent.getDormitoryAdminId());
            preparedStatement.setObject(5, absent.getCreateDate());
            preparedStatement.setObject(6, absent.getReason());

            result = preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public List<Absent> list() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT a.id, b.`name`, d.`name`, s.`name`, reason, da.`name`, a.create_date\n" +
                "FROM dormitory d, student s, building b, absent a, dormitory_admin da\n" +
                "WHERE d.id = s.dormitory_id AND b.id = d.building_id AND a.student_id = s.id AND da.id = a.dormitory_admin_id";
        List<Absent> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Absent absent = new Absent();
                absent.setId(resultSet.getInt(1));
                absent.setBuildingName(resultSet.getString(2));
                absent.setDormitoryName(resultSet.getString(3));
                absent.setStudentName(resultSet.getString(4));
                absent.setReason(resultSet.getString(5));
                absent.setDormitoryAdminName(resultSet.getString(6));
                absent.setCreateDate(resultSet.getString(7));
                list.add(absent);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return list;
    }

    @Override
    public List<Absent> search(String key, String value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT a.id, b.`name`, d.`name`, s.`name`, reason, da.`name`, a.create_date\n" +
                "FROM dormitory d, student s, building b, absent a, dormitory_admin da\n" +
                "WHERE d.id = s.dormitory_id AND b.id = d.building_id AND a.student_id = s.id AND da.id = a.dormitory_admin_id " +
                "AND " + key + ".name LIKE ?";
        List<Absent> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + value + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Absent absent = new Absent();
                absent.setId(resultSet.getInt(1));
                absent.setBuildingName(resultSet.getString(2));
                absent.setDormitoryName(resultSet.getString(3));
                absent.setStudentName(resultSet.getString(4));
                absent.setReason(resultSet.getString(5));
                absent.setDormitoryAdminName(resultSet.getString(6));
                absent.setCreateDate(resultSet.getString(7));
                list.add(absent);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return list;
    }
}
