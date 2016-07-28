package com.cooperay.web.vaadin.base.ann;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 20160728 
 * 李阳
 * 设置属性在表单中隐藏，
 * exclude 默认为“” 表示所有表单中隐藏
 * 另外的值可以自定义 默认有create，update
 * @HidinForm(exclude="{'create'}") 表明该属性在新建表单中可见，在其他表单中隐藏
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HideInForm {
	String[] exclude() default "";
}
