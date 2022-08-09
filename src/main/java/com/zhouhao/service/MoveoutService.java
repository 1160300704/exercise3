package com.zhouhao.service;

import com.zhouhao.entity.Moveout;

import java.util.List;

public interface MoveoutService {
    public void save(Moveout moveout);
    public List<Moveout> list();
    List<Moveout> search(String key, String value);
}
