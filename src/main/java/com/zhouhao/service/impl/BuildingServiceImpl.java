package com.zhouhao.service.impl;

import com.zhouhao.dao.BuildingDao;
import com.zhouhao.dao.DormitoryDao;
import com.zhouhao.dao.StudentDao;
import com.zhouhao.dao.impl.BuildingDaoImpl;
import com.zhouhao.dao.impl.DormitoryDaoImpl;
import com.zhouhao.dao.impl.StudentDaoImpl;
import com.zhouhao.entity.Building;
import com.zhouhao.service.BuildingService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class BuildingServiceImpl implements BuildingService {
    BuildingDao buildingDao = new BuildingDaoImpl();
    DormitoryDao dormitoryDao = new DormitoryDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    public List<Building> list() {
        return this.buildingDao.list();
    }

    @Override
    public List<Building> search(String key, String value) {
        List<Building> search = this.buildingDao.search(key, value);
        if(search == null)
            return this.buildingDao.list();
        return search;
    }

    @Override
    public void save(String name, String introduction, String adminId) {
        Integer integer = this.buildingDao.save(name, introduction, adminId);
        if(integer != 1)
            throw new RuntimeException("添加楼宇信息失败");
    }

    @Override
    public void update(Building building) {
        Integer integer = this.buildingDao.update(building.getId(), building.getName(), building.getIntroduction(), building.getAdminId());
        if(integer != 1)
            throw new RuntimeException("楼宇信息更新失败");
    }

    @Override
    public void delete(Integer id) {
        // 1. 学生换宿舍
        List<Integer> dormitoryIds = this.dormitoryDao.findIdByBuilding(id);
        for(Integer dormitoryId: dormitoryIds){
            List<Integer> studentIds = this.studentDao.findIdByDormitory(dormitoryId);
            for(Integer student: studentIds){
                Integer availableId = this.dormitoryDao.availableId();
                Integer updateDormitory = this.studentDao.updateDormitory(student, availableId);
                Integer subAvailable = this.dormitoryDao.subAvailable(availableId);
                if(updateDormitory != 1 || subAvailable != 1)
                    throw new RuntimeException("学生换宿舍失败");
            }
        }
        // 2. 删除宿舍
        for (Integer dormitoryId:dormitoryIds){
            Integer delete = this.dormitoryDao.deleteById(dormitoryId);
            if(delete != 1)
                throw new RuntimeException("删除宿舍信息失败");
        }
        // 3. 删除楼宇
        Integer delete = this.buildingDao.deleteById(id);
        if(delete != 1)
            throw new RuntimeException("删除楼宇信息失败");
    }
}
