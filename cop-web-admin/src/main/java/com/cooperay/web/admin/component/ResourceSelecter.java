package com.cooperay.web.admin.component;

import java.util.List;
import java.util.Locale;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cooperay.common.page.PageBean;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.web.admin.service.ResourceService;
import com.cooperay.web.admin.service.UserService;
import com.cooperay.web.vaadin.base.converter.ConvertEnableIntface;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.Renderer;

public class ResourceSelecter extends ComboBox implements ConvertEnableIntface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceService resourceService;
	
	private Double d;
	
	List list = null;
	public ResourceSelecter() {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		resourceService = wac.getBean(ResourceService.class);
		init();
	}
	
	public void init(){
		PageBean pageBean = resourceService.page(1, 5000);
		list = pageBean.getRecordList();
		setNullSelectionAllowed(false);
		setFilteringMode(FilteringMode.CONTAINS);
		setInputPrompt("--选择资源--");
		for (Object object : list) {
			Resource resource = (Resource)object;
			addItem(resource.getId());
			setItemCaption(resource.getId(), resource.getId()+"-"+resource.getResourceName());
		}
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
					Resource resource = (Resource)object;
					if(resource.getId().equals(value)){
						return resource.getId()+"-"+resource.getResourceName(); 
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
	
		
		
		
		/*BeanItemContainer<User> container = new BeanItemContainer(User.class,list);
		setItemCaptionPropertyId("userName");*/
		//comboBox.setItemIconPropertyId("id");
		//comboBox.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		//setContainerDataSource(container);
		/*this.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				User user = (User)event.getProperty().getValue(); 
				if(user!=null){
					d = user.getId().doubleValue();
				}
			}
		});*/

	/*@Override
	public Object getValue() {
		//User user = (User)super.getValue(); 
		System.out.println(d);
		return d;
	}
	
	@Override
	protected void setValue(Object newFieldValue, boolean repaintIsNotNeeded) {
		// TODO Auto-generated method stub
		User user = (User)newFieldValue; 
		if(user!=null){
			d=user.getId().doubleValue();
			System.out.println(d);
			//super.setValue(d);
		}
	}*/
	
	/*@Override
	protected void setValue(Object newFieldValue, boolean repaintIsNotNeeded) {
		// TODO Auto-generated method stub
		Container container = getContainerDataSource();
		Iterator iterator = container.getItemIds().iterator();
		while (iterator.hasNext()) {
			Item item = container.getItem(iterator.next());
		}
		if(list==null ){
			return ;
		}
		for (Object object : list) {
			User user= (User)object;
			if(user!=null && newFieldValue!=null&&newFieldValue.equals(user)){
				d = user.getId().doubleValue();
				System.out.println("=================>"+d);
				break;
			}
		}
	}*/
}
