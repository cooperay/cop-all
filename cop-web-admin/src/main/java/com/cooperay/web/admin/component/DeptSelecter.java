package com.cooperay.web.admin.component;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cooperay.common.page.PageBean;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.web.admin.service.DeptService;
import com.cooperay.web.vaadin.base.converter.ConvertEnableIntface;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

public class DeptSelecter extends ComboBox implements ConvertEnableIntface {

	private DeptService deptService;
	
	private List list;
	public DeptSelecter() {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		deptService = wac.getBean(DeptService.class);
		init();
	}
	
	@Override
	public Converter<String, Long> getCustomConverter() {
		Converter<String,Long> c = new Converter<String, Long>(){
			@Override
			public Long convertToModel(String value, Class<? extends Long> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String convertToPresentation(Long value, Class<? extends String> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				for (Object object : list) {
					Department department = (Department)object;
					if(department.getId().equals(value)){
						Long id = department.getId();
						String string = String.format("%04d", id);
						return string+"-"+department.getDeptName(); 
					}
				}
				return value==null ? "" : value+"";
			}

			@Override
			public Class<Long> getModelType() {
				// TODO Auto-generated method stub
				return Long.class;
			}

			@Override
			public Class<String> getPresentationType() {
				// TODO Auto-generated method stub
				return String.class;
			}

		};
		return c;
	}
	
	public void init(){
		PageBean pageBean = deptService.page(1, 5000);
		list = pageBean.getRecordList();
		setNullSelectionAllowed(false);
		setFilteringMode(FilteringMode.CONTAINS);
		setInputPrompt("--选择部门--");
		for (Object object : list) {
			Department department = (Department)object;
			addItem(department.getId());
			Long id = department.getId();
			String string = String.format("%04d", id);
			setItemCaption(department.getId(), string+"-"+department.getDeptName());
		}
	}

}
