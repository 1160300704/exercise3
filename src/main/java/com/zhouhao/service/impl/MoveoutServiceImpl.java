package com.zhouhao.service.impl;

import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.dao.MoveoutDao;
import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.DormitoryDaoImpl;
import com.zhouhao.dao.impl.MoveoutDaoImpl;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.Moveout;
import com.zhouhao.service.MoveoutService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MoveoutServiceImpl implements MoveoutService {
    MoveoutDao moveoutDao = new MoveoutDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();
    DormitoryDao dormitoryDao = new DormitoryDaoImpl();

    @Override
    public void save(Moveout moveout) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        moveout.setCreateDate(simpleDateFormat.format(date));
        Integer save = moveoutDao.save(moveout);
        Integer updateStateById = this.studentDao.updateStateById(moveout.getStudentId());
        Integer addAvailable = dormitoryDao.addAvailable(moveout.getDormitoryId());

        if(save != 1 || updateStateById != 1 || addAvailable != 1)
            throw new RuntimeException("学生迁出失败");
    }

    @Override
    public List<Moveout> list() {
        return this.moveoutDao.list();
    }

    @Override
    public List<Moveout> search(String key, String value) {
        if(key.equals("studentName"))
            key = "s";
        if(key.equals("dormitoryName"))
            key = "d";
        if(value == null)
            return this.moveoutDao.list();
        return moveoutDao.search(key, value);
    }
}
