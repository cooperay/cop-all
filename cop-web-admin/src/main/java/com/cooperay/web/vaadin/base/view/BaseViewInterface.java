package com.cooperay.web.vaadin.base.view;
import java.util.List;

public interface BaseViewInterface<T> {

	void addListener(BaseViewLinster<T> linster);

	void setPage(List<T> list);
	
}
