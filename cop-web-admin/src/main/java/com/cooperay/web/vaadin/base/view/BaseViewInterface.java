package com.cooperay.web.vaadin.base.view;
import java.util.List;

import com.cooperay.common.page.PageBean;

public interface BaseViewInterface<T> {

	void addListener(BaseViewLinster<T> linster);

	void setPage(PageBean pageBean);
	
	void setList(List<T> list);
	
}
