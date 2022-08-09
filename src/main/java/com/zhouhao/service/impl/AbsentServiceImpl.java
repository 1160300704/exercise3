package com.zhouhao.service.impl;

import com.zhouhao.dao.AbsentDao;
import com.zhouhao.dao.impl.AbsentDaoImpl;
import com.zhouhao.entity.Absent;
import com.zhouhao.service.AbsentService;
import java.util.List;

public class AbsentServiceImpl implements AbsentService {
    AbsentDao absentDao = new AbsentDaoImpl();

    @Override
    public void save(Absent absent) {
        Integer save = absentDao.save(absent);
        if(save != 1)
            throw new RuntimeException("缺勤信息登记失败");
    }

    @Override
    public List<Absent> list() {
        return this.absentDao.list();
    }

    @Override
    public List<Absent> search(String key, String value) {
        if(value == null)
            return this.absentDao.list();
        if(key.equals("buildingName"))
            key = "b";
        if(key.equals("dormitoryName"))
            key = "d";
        return this.absentDao.search(key, value);
    }
}
