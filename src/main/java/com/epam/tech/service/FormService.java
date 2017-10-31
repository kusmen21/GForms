package com.epam.tech.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.tech.bean.FormModel;
import com.epam.tech.dao.FormDao;

@Service
public class FormService {
	
	@Autowired
	private FormDao formDao;
	
	@Transactional
	public void addForm(FormModel formModel){
		formDao.addForm(formModel);
	}
	
	public List<FormModel> getForms(){
		return formDao.getForms();
	}
	
	public FormModel getForm(int id){
		return formDao.getForm(id);
	}
	
	public void updateForm(Map<Integer, String> fieldsAndAswers){
		formDao.updateForm(fieldsAndAswers);
	}
	
	public FormModel getFormWithAnswers(int id){
		return formDao.getFormWithAnswers(id);
	}

}
