package com.zhouhao.dao;

import com.zhouhao.entity.DormitoryAdmin;
import java.util.List;

public interface DormitoryAdminDao {
    public List<DormitoryAdmin> list();
    List<DormitoryAdmin> search(String key, String value);
    Integer save(DormitoryAdmin dormitoryAdmin);
    Integer update(DormitoryAdmin dormitoryAdmin);
    Integer delete(Integer id);
    DormitoryAdmin login(String username);
}
