package com.zhouhao.dao.impl;

import com.zhouhao.dao.SystemAdminDao;
import com.zhouhao.entity.SystemAdmin;
import com.zhouhao.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemAdminDaoImpl implements SystemAdminDao {

    @Override
    public SystemAdmin findByUsername(String userName) {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM system_admin WHERE `username` = ?";
        SystemAdmin systemAdmin = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, userName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                systemAdmin = new SystemAdmin();
                systemAdmin.setId(resultSet.getInt(1));
                systemAdmin.setUsername(resultSet.getString(2));
                systemAdmin.setPassword(resultSet.getString(3));
                systemAdmin.setName(resultSet.getString(4));
                systemAdmin.setTelephone(resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(connection, preparedStatement, resultSet);
        }
        return systemAdmin;
    }
}
