package com.zhouhao.dao.impl;

import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.entity.Dormitory;
import com.zhouhao.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DormitoryDaoImpl implements DormitoryDao {

    @Override
    public List<Dormitory> list() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT d.id, d.building_id, b.`name`, d.`name`, d.type, d.available, d.telephone FROM dormitory d, building b WHERE d.building_id = b.id";
        List<Dormitory> dormitories = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Dormitory dormitory = new Dormitory();
                dormitory.setId((Integer) resultSet.getObject(1));
                dormitory.setBuildingId((Integer) resultSet.getObject(2));
                dormitory.setBuildingName((String) resultSet.getObject(3));
                dormitory.setName((String) resultSet.getObject(4));
                dormitory.setType((Integer) resultSet.getObject(5));
                dormitory.setAvailable((Integer) resultSet.getObject(6));
                dormitory.setTelephone((String) resultSet.getObject(7));
                dormitories.add(dormitory);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return dormitories;
    }

    @Override
    public List<Dormitory> search(String key, String value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT d.id, d.building_id, b.`name`, d.`name`, d.type, d.available, d.telephone FROM dormitory d, building b WHERE d.building_id = b.id AND d." + key + " LIKE ?";
        List<Dormitory> dormitories = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + value + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Dormitory dormitory = new Dormitory();
                dormitory.setId((Integer) resultSet.getObject(1));
                dormitory.setBuildingId((Integer) resultSet.getObject(2));
                dormitory.setBuildingName((String) resultSet.getObject(3));
                dormitory.setName((String) resultSet.getObject(4));
                dormitory.setType((Integer) resultSet.getObject(5));
                dormitory.setAvailable((Integer) resultSet.getObject(6));
                dormitory.setTelephone((String) resultSet.getObject(7));
                dormitories.add(dormitory);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return dormitories;
    }

    @Override
    public List<Dormitory> availableList() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dormitory WHERE available > 0";
        List<Dormitory> dormitories = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Dormitory dormitory = new Dormitory();
                dormitory.setId((Integer) resultSet.getObject(1));
                dormitory.setBuildingId((Integer) resultSet.getObject(2));
                dormitory.setName((String) resultSet.getObject(3));
                dormitory.setType((Integer) resultSet.getObject(4));
                dormitory.setAvailable((Integer) resultSet.getObject(5));
                dormitory.setTelephone((String) resultSet.getObject(6));
                dormitories.add(dormitory);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return dormitories;
    }

    @Override
    public Integer subAvailable(Integer dormitoryId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE dormitory SET available = available - 1 WHERE id = ?";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, dormitoryId);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }

        return result;
    }

    @Override
    public Integer addAvailable(Integer oldDormitoryId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE dormitory SET available = available + 1 WHERE id = ?";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, oldDormitoryId);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }

        return result;
    }

    @Override
    public List<Integer> findIdByBuilding(Integer buildingId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id FROM dormitory WHERE building_id = ?";
        List<Integer> dormitoryIds = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, buildingId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                dormitoryIds.add((Integer) resultSet.getObject(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return dormitoryIds;
    }

    @Override
    public List<Dormitory> findDormitoryByBuilding(Integer buildingId) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id, name FROM dormitory WHERE building_id = ?";
        List<Dormitory> dormitorys = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, buildingId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Dormitory dormitory = new Dormitory();
                dormitory.setId(resultSet.getInt(1));
                dormitory.setName(resultSet.getString(2));
                dormitorys.add(dormitory);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return dormitorys;
    }

    @Override
    public Integer availableId() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer dormitoriId = null;
        String sql = "SELECT id FROM dormitory WHERE available > 0 LIMIT 1";

        try {
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                dormitoriId = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, resultSet);
        }
        return dormitoriId;
    }

    @Override
    public Integer deleteById(Integer id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "DELETE FROM dormitory WHERE id = ?";

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

    @Override
    public Integer save(Dormitory dormitory) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "INSERT INTO dormitory VALUES(NULL, ?, ?, ?, ?, ?)";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, dormitory.getBuildingId());
            preparedStatement.setObject(2, dormitory.getName());
            preparedStatement.setObject(3, dormitory.getType());
            preparedStatement.setObject(4, dormitory.getAvailable());
            preparedStatement.setObject(5, dormitory.getTelephone());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }

        return result;
    }

    @Override
    public Integer update(Dormitory dormitory) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Integer result = null;
        String sql = "UPDATE dormitory SET `name` = ?, telephone = ? WHERE id = ?";

        try{
            conn = JDBCUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, dormitory.getName());
            preparedStatement.setObject(2, dormitory.getTelephone());
            preparedStatement.setObject(3, dormitory.getId());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(conn, preparedStatement, null);
        }

        return result;
    }
}
