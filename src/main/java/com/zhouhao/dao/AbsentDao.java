package com.zhouhao.dao;

import com.zhouhao.entity.Absent;
import java.util.List;

public interface AbsentDao {
    public Integer save(Absent absent);
    public List<Absent> list();
    List<Absent> search(String key, String value);
}
