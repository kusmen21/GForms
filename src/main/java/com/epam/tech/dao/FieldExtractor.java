package com.epam.tech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.epam.tech.bean.Answer;
import com.epam.tech.bean.Field;

public class FieldExtractor implements ResultSetExtractor<List<Field>> {
	private final static String ANSWER_ID = "answer_id";
	private final static String ANSWER = "answer";
	private final static String FIELD_ID = "field_id";
	private final static String ID = "id";
	private final static String QUESTION = "question";
	private final static String FORM_ID = "form_id";

	@Override
	public List<Field> extractData(ResultSet rs) throws SQLException, DataAccessException {
	List<Field> fields = new ArrayList<>();
		Field field = null;
		int currentFieldId = 0;
		List<Answer> currentAnswers = new ArrayList<>();

		while (rs.next()) {
			Answer answer = new Answer();
			answer.setId(rs.getInt(ANSWER_ID));
			answer.setAnswer(rs.getString(ANSWER));
			answer.setFieldID(rs.getInt(FIELD_ID));
			if (currentFieldId != rs.getInt(ID)) {
				if (field != null) {
					field.setAnswers(currentAnswers);
					fields.add(field);	
					currentAnswers = new ArrayList<>();
				}
				field = new Field();
				field.setId(rs.getInt(ID));
				field.setQuestion(rs.getString(QUESTION));	
				field.setFormId(rs.getInt(FORM_ID));
				currentFieldId = field.getId();
			}
			currentAnswers.add(answer);
			if (rs.isLast()) {
				field.setAnswers(currentAnswers);
				fields.add(field);	
			}
		}
		
		return fields;
	}

}
