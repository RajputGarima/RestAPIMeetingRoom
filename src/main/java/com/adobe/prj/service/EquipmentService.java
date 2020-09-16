package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.EquipmentDao;
import com.adobe.prj.entity.Equipment;

@Service
public class EquipmentService {
	
	@Autowired
	private EquipmentDao equipmentDao;
	
	public List<Equipment> getEquipments(){
		return equipmentDao.findAll();
	}
	
	public Equipment getEquipment(int id) {
		return equipmentDao.findById(id).get(); 
	}
	
	public Equipment addEquipment(Equipment e) {
		return equipmentDao.save(e);
	}
	
	public void deleteEquipment(Equipment e) {
		equipmentDao.delete(e);
	}

}
