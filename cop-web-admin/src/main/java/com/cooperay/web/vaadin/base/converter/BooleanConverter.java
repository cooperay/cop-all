package com.cooperay.web.vaadin.base.converter;

import java.util.Locale;

import javax.validation.constraints.Null;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontAwesome;

public class BooleanConverter implements Converter<String, Boolean> {

	@Override
	public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		
		return false;
	}

	@Override
	public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		//FontAwesome.CHECK_CIRCLE_O; FontAwesome.CHECK_CIRCLE_O;FontAwesome.TOGGLE_OFF
		String string =FontAwesome.TOGGLE_OFF.getHtml();
		
		if(value!= null && value){
			string = FontAwesome.TOGGLE_ON.getHtml();
		}
		return string;
	}

	@Override
	public Class<Boolean> getModelType() {
		// TODO Auto-generated method stub
		return Boolean.class;
	}

	@Override
	public Class<String> getPresentationType() {
		// TODO Auto-generated method stub
		return String.class;
	}

}
