package com.epam.tech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.epam.tech.bean.Field;
import com.epam.tech.bean.FormModel;

public class FormExtractor implements ResultSetExtractor<List<FormModel>> {	
	private final static String NAME = "name";
	private final static String FIELD_ID = "field_id";
	private final static String ID = "id";
	private final static String QUESTION = "question";
	private final static String FORM_ID = "form_id";

	@Override
	public List<FormModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
	List<FormModel> formModels = new ArrayList<>();
		FormModel formModel = null;
		int currentFormModelId = 0;
		List<Field> currentFields = new ArrayList<>();

		while (rs.next()) {
			Field field = new Field();
			field.setId(rs.getInt(FIELD_ID));
			field.setQuestion(rs.getString(QUESTION));
			field.setFormId(rs.getInt(FORM_ID));
			if (currentFormModelId != rs.getInt(ID)) {
				if (formModel != null) {
					formModel.setFields(currentFields);
					formModels.add(formModel);					
				}
				formModel = new FormModel();
				formModel.setId(rs.getInt(ID));
				formModel.setName(rs.getString(NAME));				
				currentFormModelId = formModel.getId();
			}
			currentFields.add(field);
			if (rs.isLast()) {
				formModel.setFields(currentFields);
				formModels.add(formModel);	
			}
		}
		
		return formModels;
	}

}
