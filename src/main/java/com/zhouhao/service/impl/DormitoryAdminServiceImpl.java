package com.zhouhao.service.impl;

import com.zhouhao.dao.DormitoryAdminDao;
import com.zhouhao.dao.impl.DormitoryAdminDaoImpl;
import com.zhouhao.dto.DormitoryAdminDto;
import com.zhouhao.entity.DormitoryAdmin;
import com.zhouhao.service.DormitoryAdminService;
import java.util.List;

public class DormitoryAdminServiceImpl implements DormitoryAdminService {
    DormitoryAdminDao dormitoryAdminDao = new DormitoryAdminDaoImpl();

    @Override
    public List<DormitoryAdmin> list() {
        return this.dormitoryAdminDao.list();
    }

    @Override
    public List<DormitoryAdmin> search(String key, String value) {
        if(value.equals(""))
            return this.dormitoryAdminDao.list();
        return this.dormitoryAdminDao.search(key, value);
    }

    @Override
    public void save(DormitoryAdmin dormitoryAdmin) {
        Integer save = dormitoryAdminDao.save(dormitoryAdmin);
        if(save != 1)
            throw new RuntimeException("添加失败");
    }

    @Override
    public void update(DormitoryAdmin dormitoryAdmin) {
        Integer update = dormitoryAdminDao.update(dormitoryAdmin);
        if(update != 1)
            throw new RuntimeException("宿管信息更新失败!");
    }

    @Override
    public void delete(Integer id) {
        Integer delete = dormitoryAdminDao.delete(id);
        if(delete != 1)
            throw new RuntimeException("宿管信息删除失败!");
    }

    @Override
    public DormitoryAdminDto login(String username, String password) {
        DormitoryAdminDto dormitoryAdminDto = new DormitoryAdminDto();
        DormitoryAdmin dormitoryAdmin = dormitoryAdminDao.login(username);
        if(dormitoryAdmin == null){
            dormitoryAdminDto.setCode(-1);
        }else {
            if(!password.equals(dormitoryAdmin.getPassword()))
                dormitoryAdminDto.setCode(0);
            else{
                dormitoryAdminDto.setCode(1);
                dormitoryAdminDto.setDormitoryAdmin(dormitoryAdmin);
            }
        }

        return dormitoryAdminDto;
    }
}
