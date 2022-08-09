package com.zhouhao.dao.impl;

import com.zhouhao.dao.BuildingDao;
import com.zhouhao.entity.Building;
import com.zhouhao.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingDaoImpl implements BuildingDao {

    @Override
    public List<Building> list() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT b.id, b.`name`, b.introduction, b.admin_id, d.`name` FROM building b, dormitory_admin d WHERE b.admin_id = d.id";
        List<Building> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Building building = new Building();
                building.setId(resultSet.getInt(1));
                building.setName(resultSet.getString(2));
                building.setIntroduction(resultSet.getString(3));
                building.setAdminId(resultSet.getInt(4));
                building.setAdminName(resultSet.getString(5));
                list.add(building);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return list;
    }

    @Override
    public List<Building> search(String key, String value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT b.id, b.`name`, b.introduction, b.admin_id, d.`name` FROM building b, dormitory_admin d WHERE b.admin_id = d.id and b." +key+ " LIKE ?";
        List<Building> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + value + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Building building = new Building();
                building.setId(resultSet.getInt(1));
                building.setName(resultSet.getString(2));
                building.setIntroduction(resultSet.getString(3));
                building.setAdminId(resultSet.getInt(4));
                building.setAdminName(resultSet.getString(5));
                list.add(building);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return list;
    }

    @Override
    public Integer save(String name, String introduction, String adminId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "INSERT INTO building VALUES(NULL, ?, ?, ?)";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, name);
            preparedStatement.setObject(2, introduction);
            preparedStatement.setObject(3, adminId);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public Integer update(Integer id, String name, String introduction, Integer adminId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE building SET building.`name` = ?, building.introduction = ?, building.admin_id = ? WHERE building.id = ?";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, name);
            preparedStatement.setObject(2, introduction);
            preparedStatement.setObject(3, adminId);
            preparedStatement.setObject(4, id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }
        return result;
    }

    @Override
    public Integer deleteById(Integer id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "DELETE FROM building WHERE id = ?";

        try{
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
