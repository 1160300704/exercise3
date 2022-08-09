package com.zhouhao.dao;

import com.zhouhao.entity.SystemAdmin;

public interface SystemAdminDao{
    public SystemAdmin findByUsername(String userName);
}
