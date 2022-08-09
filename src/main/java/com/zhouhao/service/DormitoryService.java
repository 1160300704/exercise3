package com.zhouhao.service;

import com.zhouhao.entity.Dormitory;
import java.util.List;

public interface DormitoryService {
    public List<Dormitory> list();
    public List<Dormitory> availableList();
    public List<Dormitory> search(String key, String value);
    void save(Dormitory dormitory);
    void update(Dormitory dormitory);
    void delete(Integer id);
}
