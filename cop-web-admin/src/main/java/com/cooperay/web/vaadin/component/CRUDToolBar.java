package com.cooperay.web.vaadin.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CRUDToolBar extends HorizontalLayout{
	private CRUDLinster linster;

	private CRUDButton createButton;
	private CRUDButton updateButton;
	private CRUDButton delButton;
	private CRUDButton refreshButton;
	
	public CRUDToolBar(CRUDLinster linster) {
		this.linster = linster;
		init();
	}
	
	public void init(){
		setSizeUndefined();
		createButton= new CRUDButton("新增");
		createButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		createButton.setIcon(FontAwesome.PLUS);
		createButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				linster.create();
			}
		});
		
		updateButton  = new CRUDButton("修改");
		updateButton.setIcon(FontAwesome.EDIT);
		updateButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		updateButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				linster.update();
			}
		});
		
		delButton  = new CRUDButton("删除");
		delButton.addStyleName(ValoTheme.BUTTON_DANGER);
		delButton.setIcon(FontAwesome.TRASH);
		delButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				linster.delete();
			}
		});
		
		refreshButton  = new CRUDButton("刷新");
		refreshButton.setIcon(FontAwesome.REFRESH);
		refreshButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				linster.refresh();
			}
		});
		addComponents(createButton,updateButton,delButton,refreshButton);
		
	}
	
	public interface CRUDLinster{
		
		void create();
		
		void update();
		
		void delete();
		
		void refresh();
		
	}
	
	public static class CRUDButton extends Button{
		private static final long serialVersionUID = 1L;

		public CRUDButton(String cap) {
			super(cap);
			addStyleName(ValoTheme.BUTTON_SMALL);
		}
	}

	public CRUDButton getCreateButton() {
		return createButton;
	}

	public void setCreateButton(CRUDButton createButton) {
		this.createButton = createButton;
	}

	public CRUDButton getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(CRUDButton updateButton) {
		this.updateButton = updateButton;
	}

	public CRUDButton getDelButton() {
		return delButton;
	}

	public void setDelButton(CRUDButton delButton) {
		this.delButton = delButton;
	}

	public CRUDButton getRefreshButton() {
		return refreshButton;
	}

	public void setRefreshButton(CRUDButton refreshButton) {
		this.refreshButton = refreshButton;
	}
	
	
}
