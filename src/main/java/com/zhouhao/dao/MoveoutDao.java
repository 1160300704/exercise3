package com.zhouhao.dao;

import com.zhouhao.entity.Moveout;
import java.util.List;

public interface MoveoutDao {
    public Integer save(Moveout moveout);
    public List<Moveout> list();
    List<Moveout> search(String key, String value);
}
