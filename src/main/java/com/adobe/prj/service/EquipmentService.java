package com.adobe.prj.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.EquipmentDao;
import com.adobe.prj.entity.Equipment;
import com.adobe.prj.exception.ExceptionNotFound;

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
		Equipment eqp = null;
		try {
			eqp = equipmentDao.save(e);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
//			 Throwable cause = exp.getCause();
//		        if (cause instanceof SQLException) {
//		            throw new ExceptionNotFound("constraint violatoin " + cause.getMessage());
//		        }	
		        throw new ExceptionNotFound("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new ExceptionNotFound("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return eqp;
	}
	
	public void deleteEquipment(Equipment e) {
		equipmentDao.delete(e);
	}

}
