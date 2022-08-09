package com.zhouhao.service;

import com.zhouhao.entity.Building;
import java.util.List;

public interface BuildingService {
    List<Building> list();
    List<Building> search(String key, String value);
    void save(String name, String introduction, String adminId);
    void update(Building building);
    void delete(Integer id);
}
