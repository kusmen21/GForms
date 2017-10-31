package com.epam.tech.controller;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.tech.bean.FormModel;
import com.epam.tech.service.FormService;

@Controller
public class FormController {

	@Autowired
	private FormService formService;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String createForm(Model model) {
		FormModel formModel = new FormModel();
		model.addAttribute("formModel", formModel);
		
		return "/index";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createForm(@ModelAttribute("formModel") FormModel formModel) {
		formService.addForm(formModel);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");
		mav.addObject("result", "Form was created");

		return mav;
	}

	@RequestMapping(value = "/forms", method = RequestMethod.GET)
	public ModelAndView getForms() {
		List<FormModel> formModels = formService.getForms();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/forms");
		mav.addObject("createdForms", formModels);

		return mav;
	}

	@RequestMapping(value = "/fillForm/{id}", method = RequestMethod.GET)
	public ModelAndView fillForm(@PathVariable(value = "id") int idForm) {
		FormModel formModel = formService.getForm(idForm);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/form");
		mav.addObject("form", formModel);

		return mav;
	}

	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public ModelAndView updateForm() {
		Map<Integer, String> fieldsAndAswers = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		List<String> parameters = Collections.list(parameterNames);
		for (String name : parameters) {
			int fieldId = Integer.parseInt(name.split("_")[1]);
			fieldsAndAswers.put(fieldId, request.getParameter(name));
		}
		
		formService.updateForm(fieldsAndAswers);		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");
		mav.addObject("result", "Form was updated");

		return mav;
	}
	
	@RequestMapping(value = "/showForm/{id}", method = RequestMethod.GET)
	public ModelAndView showForm(@PathVariable(value = "id") int idForm){		
		FormModel formModel = formService.getFormWithAnswers(idForm);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/viewForm");
		mav.addObject("form", formModel);
		
		return mav;
	}

}

	
	
	
	
	
	
	