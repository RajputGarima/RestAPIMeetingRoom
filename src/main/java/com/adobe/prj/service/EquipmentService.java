package com.adobe.prj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.EquipmentDao;
import com.adobe.prj.entity.Equipment;
import com.adobe.prj.exception.CustomException;


@Service
public class EquipmentService {
	
	@Autowired
	private EquipmentDao equipmentDao;
	
	public List<Equipment> getEquipments(){
		return equipmentDao.findAll();
	}
	
	public Optional<Equipment> getEquipment(int id) {
		return equipmentDao.findById(id); 
	}
	
	public Equipment addEquipment(Equipment e) {
		Equipment eqp = null;
		try {
			eqp = equipmentDao.save(e);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
		        throw new CustomException("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return eqp;
	}
	
	public void deleteEquipment(Equipment e) {
		equipmentDao.delete(e);
	}

}
