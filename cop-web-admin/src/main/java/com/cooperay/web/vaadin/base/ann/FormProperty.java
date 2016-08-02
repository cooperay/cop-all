package com.cooperay.web.vaadin.base.ann;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.Null;

import com.vaadin.ui.TextField;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormProperty {
	
	String text();
	
	boolean readonly() default false;
	
	Class fieldClass() default TextField.class ;	
}
