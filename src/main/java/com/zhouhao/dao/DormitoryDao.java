package com.zhouhao.dao;

import com.zhouhao.entity.Dormitory;
import java.util.List;

public interface DormitoryDao {
    public List<Dormitory> list();
    public List<Dormitory> search(String key, String value);
    public List<Dormitory> availableList();
    public Integer subAvailable(Integer dormitoryId);
    Integer addAvailable(Integer oldDormitoryId);
    List<Integer> findIdByBuilding(Integer buildingId);
    List<Dormitory> findDormitoryByBuilding(Integer buildingId);
    public Integer availableId();
    Integer deleteById(Integer id);
    Integer save(Dormitory dormitory);
    Integer update(Dormitory dormitory);
}
