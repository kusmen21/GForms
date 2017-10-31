package com.epam.tech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.tech.bean.Field;
import com.epam.tech.bean.FormModel;

@Repository
public class FormDao {
	
	private static final String INSERT_FORM = "INSERT INTO form (name) VALUES (?)";
	private static final String INSERT_FIELD = "INSERT INTO field (question, form_id) VALUES (?, ?)";
	private static final String GET_FORMS = "SELECT f.id, f.name, e.id AS field_id, e.question, e.form_id FROM form f JOIN field e WHERE f.id = e.form_id";
	private static final String GET_FORM = "SELECT f.id, f.name, e.id AS field_id, e.question, e.form_id FROM form f JOIN field e WHERE f.id = e.form_id AND f.id = ?";
	private static final String INSERT_ANSWER = "INSERT INTO answer (field_id, answer) VALUES (?, ?)";
	private static final String GET_ANSWERS = "SELECT a.id AS answer_id, a.answer, a.field_id, f.id, f.question, f.form_id FROM field f JOIN answer a ON f.id = a.field_id AND f.form_id = ?";
	
	
    @Autowired
    DataSource dataSource; 
 
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	public void addForm(FormModel formModel) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder(); 
		
		jdbcTemplate.update(new PreparedStatementCreator() {			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(INSERT_FORM, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, formModel.getName());
				return statement;
			}
		}, holder);
		
		int primaryKey = holder.getKey().intValue();
        
        List<Field> fields = formModel.getFields();
        for (Field field : fields){
        	if (field != null && field.getQuestion() != null && !field.getQuestion().isEmpty()){
        		jdbcTemplate.update(INSERT_FIELD, new Object[] {field.getQuestion(), primaryKey});
        	}
        }
	}
	
	public List<FormModel> getForms(){		
		List<FormModel> forms = jdbcTemplate.query(GET_FORMS, new FormExtractor());	
		return forms;		
	}
	
	public FormModel getForm(int id){
		FormModel formModel = new FormModel();
		formModel = jdbcTemplate.query(GET_FORM, new Object[] {id}, new FormExtractor()).get(0);
		
		return formModel;
	}
	
	public void updateForm(Map<Integer, String> fieldsAndAswers){
		for (Map.Entry<Integer, String> entry : fieldsAndAswers.entrySet()){
			jdbcTemplate.update(INSERT_ANSWER, new Object[] {entry.getKey(), entry.getValue()});
		}		
	}
	
	public FormModel getFormWithAnswers(int id){
		FormModel formModel = getForm(id);	
		List<Field> fieldsWithAnswers = jdbcTemplate.query(GET_ANSWERS, new Object[] {id}, new FieldExtractor());
		formModel.setFields(fieldsWithAnswers);
		
		return formModel;
	}
}



