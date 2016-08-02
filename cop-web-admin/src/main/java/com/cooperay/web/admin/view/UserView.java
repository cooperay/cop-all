package com.cooperay.web.admin.view;

import com.cooperay.facade.admin.entity.User;
import com.cooperay.web.admin.component.UserSelecter;
import com.cooperay.web.admin.vo.UserVo;
import com.cooperay.web.vaadin.base.view.BaseView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Slider;
import com.vaadin.ui.renderers.ProgressBarRenderer;
public class UserView extends BaseView<User, UserVo> {
	public UserView() {
		super(new User(), "用户管理", new UserVo(),new Object[]{"id","userName","password"});
		/*panelContent.addComponent(userSelecter);*/
	}
	
	
	@Override
	protected Component createGrid() {
		Grid grid = (Grid)super.createGrid();
		
		ProgressBar progressBar = new ProgressBar();
		Column column = grid.getColumn("age");
		column.setRenderer(new ProgressBarRenderer());
		Slider slider = new Slider();
		slider.setMax(1);
		slider.setSizeFull();
		slider.setResolution(2);
		slider.setMin(0);
		column.setEditorField(slider);
		return grid;
	}
}
