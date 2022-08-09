package com.zhouhao.service;

import com.zhouhao.dto.DormitoryAdminDto;
import com.zhouhao.entity.DormitoryAdmin;
import java.util.List;

public interface DormitoryAdminService {
    public List<DormitoryAdmin> list();
    public List<DormitoryAdmin> search(String key, String value);
    public void save(DormitoryAdmin dormitoryAdmin);
    void update(DormitoryAdmin dormitoryAdmin);
    void delete(Integer id);
    DormitoryAdminDto login(String username, String password);
}
