package com.cooperay.web.vaadin.component;

import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;
import javax.xml.bind.helpers.ValidationEventImpl;

import org.springframework.web.servlet.config.VelocityConfigurerBeanDefinitionParser;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.themes.ValoTheme;

public class PageBar extends HorizontalLayout {
	private HorizontalLayout pagesLayout = new HorizontalLayout();
	private List<PageButton> pages = new ArrayList<>();
	
	private PageButton curentPage;
	private PageBarEventLinster pageBarEventLinster;
	private PageBarExprotLinster pageBarExprotLinster;
	private PageButton preButton;
	private PageButton nextButton;
	private Integer allCount;
	private Integer rows;
	private Integer pageCount;
	
	private PageButton infoButton;  //页面信息显示
	
	public PageBar(Integer allCount,Integer rows){
		this.allCount = allCount;
		this.rows = rows;
		init();
	}
	
	/**
	 * s刷新bar
	 * @param allCount
	 * @param rows
	 */
	public void reset(Integer allCount,Integer rows){
		removeAllComponents();
		this.allCount = allCount;
		this.rows = rows;
		curentPage =null;
		init();
	}
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：上一页点击事件
	 * @参数：@param pageButton
	 */
	private Button.ClickListener preButtonLinster = new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			PageButton eventButton = (PageButton)event.getButton();
			for(PageButton pageButton : pages){
				if(curentPage.equals(pageButton)){
					Integer index = pages.indexOf(pageButton) < 1 ? 1 : pages.indexOf(pageButton) ;  //当前选中页面在数组中的索引 如果是第一页不再向前移动
					PageButton preButton = pages.get(index-1);
					dispatchPageChangeEvent(preButton);
					break;
				}
			}
		}
	}; 
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：下一页点击事件
	 * @参数：@param pageButton
	 */
	private Button.ClickListener nextButtonLinster = new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			PageButton eventButton = (PageButton)event.getButton();
			for(PageButton pageButton : pages){
				if(curentPage.equals(pageButton)){
					Integer index = pages.indexOf(pageButton) > pages.size()-2 ? pages.size()-2 : pages.indexOf(pageButton) ;  //当前选中页面在数组中的索引 如果是第一页不再向前移动
					PageButton nextButton = pages.get(index+1);
					dispatchPageChangeEvent(nextButton);
					break;
				}
			}
		}
	}; 
	
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：分页按钮点击事件
	 * @参数：@param pageButton
	 */
	private Button.ClickListener pageButtonClickLinstener = new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			PageButton pageButton = (PageButton)event.getButton();
			dispatchPageChangeEvent(pageButton);
		}
	}; 
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：触发页面修改事件，并更新分页工具条样式
	 * @参数：@param pageButton
	 */
	private void dispatchPageChangeEvent(PageButton pageButton){
		if(pageButton==null){
			return;
		}
		curentPage = pageButton;
		Integer index = pageButton.getIndex();
		Integer startPage = 0;
		if(index == 5 && pageButton.getPage() < pageCount){  //
			startPage = pageButton.getPage();
		}else if(index == 1 ){
			startPage = pageButton.getPage() >=5 ? pageButton.getPage() -4 : 1;  //
		}
		if(startPage!=0){
			createPageButton(startPage, pageCount);
		}
		refreshSelected();
		pageBarEventLinster.pageChangeEvent(new PageBarEvent(pageButton, rows));
	}
	
	private void init(){
		/*if(allCount <= rows || allCount <= 0){
			addComponent(createInfoLabel());
			addComponent(createSlider());
			return;
		}*/
		pageCount = allCount%rows > 0 ? allCount / rows +1 : allCount /rows ;
		infoButton = new PageButton(getPageInfoString());
		addComponent(infoButton);
		if(allCount > rows){
			preButton = new PageButton("上一页");
			preButton.addClickListener(preButtonLinster);
			nextButton = new PageButton("下一页");
			nextButton.addClickListener(nextButtonLinster);
			addComponent(preButton);
			addComponent(pagesLayout);
			addComponent(nextButton);
			createPageButton(1, pageCount);
			refreshSelected();
		}
		addComponent(createSlider());
		addComponent(createExprot());
		addComponent(createExprotAll());
	}
	
	private List<PageButton> createPageButton(Integer startPage,Integer pageCount){
		pages = new ArrayList<>();
		pagesLayout.removeAllComponents();
		
		HorizontalLayout h = new HorizontalLayout();
		pagesLayout.addComponent(h);
		
		for(int i=startPage , j=1; i <= pageCount && j<= 5 ; i++ , j++){
			PageButton pb = new PageButton(i,j);
			pb.addClickListener(pageButtonClickLinstener);
			//初始设置第一个坐标为当前页面
			if(curentPage==null && j==1){
				curentPage = pb;
			}else if(i==curentPage.getPage()){  //如果重新生成了pagebar 将生成前的当前页面设置为生成后的页面  
				curentPage = pb;
			}
			pagesLayout.addComponent(pb);
			pages.add(pb);
		}
		if(pages.size()<=0){
			return pages;
		}
		PageButton firstPage = pages.get(0);
		//curentPage = firstPage;
		if(firstPage.getPage() >= 2){
			firstPage = new PageButton(1,5);
			firstPage.addClickListener(pageButtonClickLinstener);
			Label b = new Label("....");
			b.setEnabled(false);
			h.addComponent(firstPage);
			h.addComponent(b);
		}
		
		
		PageButton lastPage = pages.get(pages.size()-1);
		if(pageCount > 5 &&  lastPage.getPage() != pageCount){
			lastPage = new PageButton(pageCount,1);
			lastPage.addClickListener(pageButtonClickLinstener);
			Label b = new Label("....");
			b.setEnabled(false);
			pagesLayout.addComponent(b);
			pagesLayout.addComponent(lastPage);
		}
		
		return pages;
	}
	
	/**
	 * 刷新当前页面选中状态 以及页面信息libel
	 * */
	private void refreshSelected(){
		for(PageButton pb : pages){
			if(curentPage.equals(pb)){
				curentPage.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			}else {
				pb.removeStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			}
		};
		infoButton.setCaption(getPageInfoString());
	}
	
	/**
	 * 创建更改rows的滑动条
	 * @return
	 */
	private Component createSlider(){
		Slider slider = new Slider(100, 1000);
		slider.setWidth(150,Unit.PIXELS);
		slider.setValue(rows.doubleValue());
		slider.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Double value = (Double)event.getProperty().getValue();
				rows = value.intValue();
				reset(allCount, rows);
				dispatchPageChangeEvent(curentPage);
			}
		});
		return slider;
	}

	/**
	 * 创建信息显示标签
	 * @return
	 */
	private String getPageInfoString(){
		String infoStr = "记录:"+rows+"/"+allCount+"  页数:";
		if(curentPage!=null){
			infoStr += curentPage.getPage();
		}else{
			infoStr+=1;
		}
		infoStr+="/"+pageCount;
		return infoStr;
	}
	
	private Component createExprot(){
		Button exp  = new Button("导出当前页");
		exp.setIcon(FontAwesome.DOWNLOAD);
		exp.addStyleName(ValoTheme.BUTTON_SMALL);
		exp.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(pageBarExprotLinster!=null){
					pageBarExprotLinster.exprot(new PageBarExprotEvent());
				}
			}
		});
		return exp;
	}
	
	private Component createExprotAll(){
		Button exp  = new Button("导出所有页");
		exp.setIcon(FontAwesome.DOWNLOAD);
		exp.addStyleName(ValoTheme.BUTTON_SMALL);
		exp.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(pageBarExprotLinster!=null){
					pageBarExprotLinster.exprotAll(new PageBarExprotEvent());
				}
			}
		});
		return exp;
	}
	
	public PageBarEventLinster getPageBarEventLinster() {
		return pageBarEventLinster;
	}

	public void setPageBarEventLinster(PageBarEventLinster pageBarEventLinster) {
		this.pageBarEventLinster = pageBarEventLinster;
	}

	public void setPageBarExprotLinster(PageBarExprotLinster pageBarExprotLinster){
		this.pageBarExprotLinster = pageBarExprotLinster;
	}


	/**
	 * 页面切换事件
	 * @author cooperay
	 *
	 */
	public interface PageBarEventLinster{
		void pageChangeEvent(PageBarEvent pageBarEvent);
	}
	
	/**
	 * 导出事件监听
	 * @author cooperay
	 *
	 */
	public interface PageBarExprotLinster{
		void exprot(PageBarExprotEvent event);
		
		void exprotAll(PageBarExprotEvent event);
	}
	
	public class PageBarExprotEvent{
		public Integer getPage() {
			return curentPage.getPage();
		}
		public Integer getRows() {
			return rows;
		}
	}
	
	
	public class PageBarEvent{
		private PageButton pageButton;
		private Integer rows;
		public PageBarEvent(PageButton pageButton , Integer rows) {
			this.pageButton = pageButton;
			this.rows = rows;
		}
		public PageButton getPageButton() {
			return pageButton;
		}
		public void setPageButton(PageButton pageButton) {
			this.pageButton = pageButton;
		}
		public Integer getRows() {
			return rows;
		}
		public void setRows(Integer rows) {
			this.rows = rows;
		}
	}
	
	
	public class PageButton extends Button {
		private static final long serialVersionUID = 4682481300029559176L;
		private Integer index;
		private Integer page;
		public PageButton(Integer page,Integer index) {
			super(page+"");
			this.page = page;
			this.index = index;
			init();
		}
		
		public PageButton(String cap){
			super(cap);
			init();
		}
		
		private void init(){
			addStyleName(ValoTheme.BUTTON_SMALL);
			addStyleName(ValoTheme.BUTTON_BORDERLESS);
		}
		
		public Integer getIndex() {
			return index;
		}
		public void setIndex(Integer index) {
			this.index = index;
		}
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		
	}
	
}
