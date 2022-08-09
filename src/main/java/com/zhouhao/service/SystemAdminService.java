package com.zhouhao.service;

import com.zhouhao.dto.SystemAdminDto;

public interface SystemAdminService {
    public SystemAdminDto login(String username, String password);
}
