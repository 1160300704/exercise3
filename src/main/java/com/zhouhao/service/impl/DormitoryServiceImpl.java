package com.zhouhao.service.impl;

import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.DormitoryDaoImpl;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.Dormitory;
import com.zhouhao.service.DormitoryService;
import java.util.List;

public class DormitoryServiceImpl implements DormitoryService {
    DormitoryDao dormitoryDao = new DormitoryDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    public List<Dormitory> list() {
        return dormitoryDao.list();
    }

    @Override
    public List<Dormitory> availableList() {
        return dormitoryDao.availableList();
    }

    @Override
    public List<Dormitory> search(String key, String value) {
        if(value == null)
            return dormitoryDao.list();
        return dormitoryDao.search(key, value);
    }

    @Override
    public void save(Dormitory dormitory) {
        Integer save = this.dormitoryDao.save(dormitory);
        if(save != 1)
            throw new RuntimeException("添加宿舍信息失败");
    }

    @Override
    public void update(Dormitory dormitory) {
        Integer update = this.dormitoryDao.update(dormitory);
        if(update != 1)
            throw new RuntimeException("宿舍信息修改失败");
    }

    @Override
    public void delete(Integer id) {
        // 1. 学生换宿舍
        List<Integer> studentIds = studentDao.findIdByDormitory(id);
        for (Integer studentId: studentIds) {
            Integer availableId = dormitoryDao.availableId();
            Integer updateDormitory = studentDao.updateDormitory(studentId, availableId);
            Integer subAvailable = dormitoryDao.subAvailable(availableId);
            if(subAvailable != 1 || updateDormitory != 1)
                throw new RuntimeException("学生更换宿舍失败");
        }

        // 2. 删除宿舍
        Integer delete = this.dormitoryDao.deleteById(id);
        if(delete != 1)
            throw new RuntimeException("删除宿舍信息失败");
    }
}
