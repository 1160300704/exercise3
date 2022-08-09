package com.zhouhao.dao;

import com.zhouhao.entity.Building;
import java.util.List;

public interface BuildingDao {
    List<Building> list();

    List<Building> search(String key, String value);

    Integer save(String name, String introduction, String adminId);

    Integer update(Integer id, String name, String introduction, Integer adminId);

    Integer deleteById(Integer id);
}
