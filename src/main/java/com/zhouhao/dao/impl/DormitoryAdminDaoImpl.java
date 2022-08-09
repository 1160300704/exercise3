package com.zhouhao.dao.impl;

import com.zhouhao.dao.DormitoryAdminDao;
import com.zhouhao.entity.DormitoryAdmin;
import com.zhouhao.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DormitoryAdminDaoImpl implements DormitoryAdminDao {
    @Override
    public List<DormitoryAdmin> list() {
        List<DormitoryAdmin> dormitoryAdmins = new ArrayList<>();

        String sql = "SELECT * FROM dormitory_admin";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                DormitoryAdmin dormitoryAdmin = new DormitoryAdmin();
                dormitoryAdmin.setId(resultSet.getInt(1));
                dormitoryAdmin.setUsername(resultSet.getString(2));
                dormitoryAdmin.setPassword(resultSet.getString(3));
                dormitoryAdmin.setName(resultSet.getString(4));
                dormitoryAdmin.setGender(resultSet.getString(5));
                dormitoryAdmin.setTelephone(resultSet.getString(6));
                dormitoryAdmins.add(dormitoryAdmin);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JDBCUtil.release(connection, preparedStatement, resultSet);
        }

        return dormitoryAdmins;
    }

    @Override
    public List<DormitoryAdmin> search(String key, String value) {
        List<DormitoryAdmin> dormitoryAdmins = new ArrayList<>();

        String sql = "SELECT * FROM dormitory_admin where "+ key +" like ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, "%"+value+"%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                DormitoryAdmin dormitoryAdmin = new DormitoryAdmin();
                dormitoryAdmin.setId(resultSet.getInt(1));
                dormitoryAdmin.setUsername(resultSet.getString(2));
                dormitoryAdmin.setPassword(resultSet.getString(3));
                dormitoryAdmin.setName(resultSet.getString(4));
                dormitoryAdmin.setGender(resultSet.getString(5));
                dormitoryAdmin.setTelephone(resultSet.getString(6));
                dormitoryAdmins.add(dormitoryAdmin);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JDBCUtil.release(connection, preparedStatement, resultSet);
        }

        return dormitoryAdmins;
    }

    @Override
    public Integer save(DormitoryAdmin dormitoryAdmin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "INSERT INTO dormitory_admin VALUES(NULL,?,?,?,?,?)";

        try{
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, dormitoryAdmin.getUsername());
            preparedStatement.setObject(2, dormitoryAdmin.getPassword());
            preparedStatement.setObject(3, dormitoryAdmin.getName());
            preparedStatement.setObject(4, dormitoryAdmin.getGender());
            preparedStatement.setObject(5, dormitoryAdmin.getTelephone());
            result = preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(connection, preparedStatement, null);
        }
        return result;
    }

    @Override
    public Integer update(DormitoryAdmin dormitoryAdmin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE dormitory_admin SET username = ?, `password` = ?, `name` = ?, gender = ?, telephone = ? WHERE id = ?";

        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, dormitoryAdmin.getUsername());
            preparedStatement.setObject(2, dormitoryAdmin.getPassword());
            preparedStatement.setObject(3, dormitoryAdmin.getName());
            preparedStatement.setObject(4, dormitoryAdmin.getGender());
            preparedStatement.setObject(5, dormitoryAdmin.getTelephone());
            preparedStatement.setObject(6, dormitoryAdmin.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JDBCUtil.release(connection, preparedStatement, null);
        }
        return result;
    }
    @Override
    public Integer delete(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement =  null;
        Integer result = null;
        String sql = "DELETE FROM dormitory_admin WHERE id = ?";

        try{
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(connection, preparedStatement, null);
        }
        return result;
    }

    @Override
    public DormitoryAdmin login(String username) {
        DormitoryAdmin dormitoryAdmin = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dormitory_admin WHERE `username` = ?";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                dormitoryAdmin = new DormitoryAdmin();
                dormitoryAdmin.setId(resultSet.getInt(1));
                dormitoryAdmin.setUsername(resultSet.getString(2));
                dormitoryAdmin.setPassword(resultSet.getString(3));
                dormitoryAdmin.setName(resultSet.getString(4));
                dormitoryAdmin.setGender(resultSet.getString(5));
                dormitoryAdmin.setTelephone(resultSet.getString(6));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JDBCUtil.release(connection, preparedStatement, resultSet);
        }

        return dormitoryAdmin;
    }
}
