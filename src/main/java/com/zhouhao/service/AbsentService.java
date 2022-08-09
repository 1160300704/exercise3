package com.zhouhao.service;

import com.zhouhao.entity.Absent;

import java.util.List;

public interface AbsentService {
    public void save(Absent absent);
    public List<Absent> list();
    List<Absent> search(String key, String value);
}
