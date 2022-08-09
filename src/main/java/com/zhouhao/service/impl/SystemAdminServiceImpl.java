package com.zhouhao.service.impl;

import com.zhouhao.dao.SystemAdminDao;
import com.zhouhao.dao.impl.SystemAdminDaoImpl;
import com.zhouhao.dto.SystemAdminDto;
import com.zhouhao.entity.SystemAdmin;
import com.zhouhao.service.SystemAdminService;

public class SystemAdminServiceImpl implements SystemAdminService {
    SystemAdminDao systemAdminDao = new SystemAdminDaoImpl();

    @Override
    public SystemAdminDto login(String username, String password) {
        SystemAdminDto systemAdminDto = new SystemAdminDto();

        SystemAdmin systemAdmin = this.systemAdminDao.findByUsername(username);
        if(systemAdmin ==  null)
            systemAdminDto.setCode(-1);
        else{
            if(!password.equals(systemAdmin.getPassword()))
                systemAdminDto.setCode(0);
            else{
                systemAdminDto.setCode(1);
                systemAdminDto.setSystemAdmin(systemAdmin);
            }
        }

        return systemAdminDto;
    }

    public static void main(String[] args) {
        SystemAdminDto systemAdminDto = new SystemAdminDto();
        System.out.println(systemAdminDto.getSystemAdmin());
    }
}
