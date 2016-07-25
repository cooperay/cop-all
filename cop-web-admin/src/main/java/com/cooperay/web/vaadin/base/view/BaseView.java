package com.cooperay.web.vaadin.base.view;

import java.lang.reflect.Field;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.cooperay.common.page.PageBean;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;
import com.cooperay.web.vaadin.base.ann.HideInGrid;
import com.cooperay.web.vaadin.component.ConfimWindow;
import com.cooperay.web.vaadin.component.PageBar;
import com.cooperay.web.vaadin.component.PageBar.PageBarEvent;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public abstract class BaseView<T,V> extends VerticalLayout implements BaseViewInterface<T> {
	protected static final Logger log = LoggerFactory.getLogger(BaseView.class);
	final String STATE_SAVE = "save";
	public static final String STATE_UPDATE = "update";
	private static final long serialVersionUID = 1L;
	private static final Integer FIELD_COL_SIZE = 10;
	private Integer fieldCount;
	private BaseViewLinster<T> linster;
	private T entry;  
	private V vo;
	private List<T> selectedList;  //选中列表
	
	
	/**初示参数**/
	private Integer rows = 15;
	private Integer currentPage = 1;
	private boolean editorEnabled = true;
	private List<T> list;  //初始列表
	private Object[] colOrder; //列表顺序
	
	private Window editWindow;  //编辑窗口
	private Grid grid;
	private PageBar pageBar;
	
	public BaseView(T t) {
		this(t,"编辑区域");
	}
	
	public BaseView(T t,String caption) {
		this(t,caption,null);
	}
	
	/**
	 * 指定实体类的属性名称
	 * @param t
	 * @param caption
	 * @param fieldMap
	 */
	public BaseView(T t,String caption,V vo) {
		this(t,caption,vo,null);
	}
	
	public BaseView(T t,String caption,V vo,Object[] colOrder) {
		entry = t;
		this.vo = vo;
		this.colOrder = colOrder;
		init(caption);
	}
	

	@Override
	public void setList(List<T> list) {
		BeanItemContainer<T> beanItemContainer = (BeanItemContainer)grid.getContainerDataSource();
		beanItemContainer.removeAllContainerFilters();
		beanItemContainer.removeAllItems();
		beanItemContainer.addAll(list);
		Integer row = grid.getContainerDataSource().size();
		if(row>0){
			grid.setHeightByRows(row);
		}
	}
	
	@Override
	public void setPage(PageBean pageBean) {
		BeanItemContainer<T> beanItemContainer = (BeanItemContainer)grid.getContainerDataSource();
		beanItemContainer.removeAllContainerFilters();
		beanItemContainer.removeAllItems();
		beanItemContainer.addAll((List<T>)pageBean.getRecordList());
		Integer row = grid.getContainerDataSource().size();
		if(row>0){
			grid.setHeightByRows(row);
		}
		currentPage = pageBean.getCurrentPage();
		pageBar.reset(pageBean.getTotalCount(), rows);
		selectedList = null;
	}

	protected void init(String caption) {
		log.debug("init base view");
		Panel panel = new Panel(caption);
		panel.addStyleName(ValoTheme.PANEL_WELL);
		VerticalLayout panelContent = new VerticalLayout();
		panel.setContent(panelContent);
		panel.setSizeFull();
		panelContent.setMargin(true);
		panelContent.setSpacing(true);
		panelContent.addComponent(createToolBar());
		panelContent.addComponent(createGrid());
		panelContent.addComponent(createPageBar(11,10));
		addComponent(panel);
	}

	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：创建分页组件
	 * @参数：@return
	 */
	protected Component createPageBar(Integer count,Integer rows){
		pageBar = new PageBar(count, rows);
		//pageBar.setVisible(false);
		pageBar.setPageBarEventLinster(new PageBar.PageBarEventLinster() {
			@Override
			public void pageChangeEvent(PageBarEvent pageBarEvent) {
				Integer page = pageBarEvent.getPageButton().getPage();
				Integer rows = pageBarEvent.getRows();
				linster.page(page, rows);
			}
		});
		return pageBar;
	}
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：创建编辑窗口 用户新建/编辑
	 * @参数：@param type  ‘save’为新建   ‘update’ 为编辑
	 * @参数：@return
	 */
	protected Window createEditWindow(String type){
		editWindow = new Window("新增/编辑窗口");
		editWindow.setContent(createForm(type));
		editWindow.setModal(true);
		editWindow.setWidth(500,Unit.PIXELS);
		editWindow.center();
       return editWindow;
	}
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：创建工具条
	 * @参数：@return
	 */
	protected Component createToolBar(){
		HorizontalLayout layout = new HorizontalLayout();
		/*Label title = new Label("编辑区域：");
		layout.addComponent(title);
		*/
		//创建操作按钮
		Button add  = new Button("新增");
		add.addStyleName(ValoTheme.BUTTON_SMALL);
		add.addStyleName(ValoTheme.BUTTON_PRIMARY);
		add.setIcon(FontAwesome.PLUS);
		add.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Class<T> class1 = (Class<T>)entry.getClass();
				try {
					entry = class1.newInstance();
					editWindow = createEditWindow(STATE_SAVE);
			        getUI().addWindow(editWindow);
				} catch (InstantiationException | IllegalAccessException e) {
					log.error("$==> 初始化实体类失败 "+entry.getClass());
				}
				
				
			}
		});
		
		Button update  = new Button("修改");
		update.setIcon(FontAwesome.EDIT);
		update.addStyleName(ValoTheme.BUTTON_SMALL);
		update.addStyleName(ValoTheme.BUTTON_PRIMARY);
		update.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedList==null || selectedList.size()<=0){
					Notification.show("未选中内容",Type.WARNING_MESSAGE);
					return;
				}
				if(selectedList.size()>1){
					Notification.show("请选中一条记录进行编辑",Type.WARNING_MESSAGE);
					return;
				}
				editWindow = createEditWindow(STATE_UPDATE);
		        getUI().addWindow(editWindow);
			}
		});
		
		Button del  = new Button("删除");
		del.addStyleName(ValoTheme.BUTTON_SMALL);
		del.addStyleName(ValoTheme.BUTTON_DANGER);
		del.setIcon(FontAwesome.REMOVE);
		del.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedList == null || selectedList.size()<=0){
					Notification.show("未选中内容",Type.WARNING_MESSAGE);
					return;
				}
				ConfimWindow confimWindow = new ConfimWindow("警告","删除选中的-"+selectedList.size()+"-条记录？");
				confimWindow.setConfimEventLinster(new ConfimWindow.ConfimEventLinster() {
					@Override
					public void confim(ConfimWindow confimWindow) {
						for(int i=0 ;i<selectedList.size() ;i++){
							linster.delete(selectedList.get(i));
						}
						Notification.show(selectedList.size()+"条记录已删除",Type.TRAY_NOTIFICATION);
						linster.page(currentPage, rows);
						grid.deselectAll();
						confimWindow.close();
					}
					@Override
					public void cancel(ConfimWindow confimWindow) {
						confimWindow.close();
					}
				});
				getUI().addWindow(confimWindow);	
			}
		});
		layout.addComponent(add);
		layout.addComponent(update);
		layout.addComponent(del);
		
		//创建搜索区域
		/*TextField textField = new TextField();
		textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		textField.setInputPrompt("mingcheng");
		layout.addComponent(textField);*/
		
		return layout;
	}
	
	public Component createForm(String type){
		return createForm(type,1);
	}
	
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：创建form用来修改和新增
	 * @参数：@return
	 */
	public Component createForm(String type,Integer cols){
		List<FormLayout> colForms = new ArrayList<>();
		VerticalLayout colsLayout = new VerticalLayout();
		List<com.vaadin.ui.Field> formFields = new ArrayList<>();
		for(int i = 0 ; i < cols ; i++ ){
			FormLayout formLayout = new FormLayout();
			colsLayout.addComponent(formLayout);
			colForms.add(formLayout);
		}
		
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		if(entry==null){
			return null;
		}
		Field[] fields= vo.getClass().getDeclaredFields();
		fieldCount = fields.length;
		final BeanFieldGroup<T> binder =  new BeanFieldGroup(entry.getClass());
		binder.setItemDataSource(entry);
		for (Field voField : fields) {
			//判断是否在表单中隐藏
			HideInForm hideProperty = voField.getAnnotation(HideInForm.class);
			if(hideProperty!=null){
				continue;
			}
			
			//判断是否能得到名称 
			String caption = getFieldDisName(voField);  
			if(caption == null){
				continue;
			}
			
			Field field = null;
			try {
				field = entry.getClass().getDeclaredField(voField.getName());
			} catch (NoSuchFieldException e) {
				log.debug("$==>Vo指定的对象属性不存在 "+voField.getName());
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			if(field ==null){
				continue;
			}
			com.vaadin.ui.Field formField = null;
			if(Enum.class.isAssignableFrom(field.getType())){
				formField = binder.buildAndBind(caption,field.getName(),ComboBox.class);
			}else {
				formField = binder.buildAndBind(caption,field.getName());
			}
			if(!vo.getClass().equals(entry.getClass())){
				formField.addValidator(new BeanValidator(vo.getClass(), voField.getName()));
			}
			if(formField instanceof TextField){
				((TextField)formField).setNullRepresentation("");
				((TextField)formField).setNullSettingAllowed(true);
			}
			
			layout.addComponent(formField);
			formFields.add(formField);
		}
		layout.addComponent(createFormButton(type,binder));
		return layout;
	}
	
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：根据入库单id获取所有的单据明细列表
	 * @参数：@return
	 */
	private String getFieldDisName(Field field){
		String disName = null;
		FormProperty formProperty = field.getAnnotation(FormProperty.class);
		if (formProperty!=null) {
			disName = formProperty.text();
		}
		return disName;
	}
	
	
	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：创建通用表格
	 * @参数：@return
	 */
	private Component createGrid(){
		// Have a container of some type to contain the data
		BeanItemContainer<T> container = new BeanItemContainer(entry.getClass(),list);
		// Create a grid bound to the container
		grid = new Grid(container);
		grid.setSizeFull();
		grid.setEditorEnabled(editorEnabled);
		grid.setEditorSaveCaption("保存");
		grid.setEditorCancelCaption("取消");
		grid.setHeightByRows(rows);
		grid.setHeightMode(HeightMode.ROW);
		grid.setColumnOrder();
		// Handle selection in single-selection mode
		grid.setSelectionMode(SelectionMode.MULTI);
		if(colOrder!=null){
			grid.setColumnOrder(colOrder);
		}
		Field[] fields= vo.getClass().getDeclaredFields();
		for (Field voField : fields) {
			HideInGrid hideProperty = voField.getAnnotation(HideInGrid.class);
			if(hideProperty!=null){
				grid.removeColumn(voField.getName());
				continue;
			}
			String caption = getFieldDisName(voField);
			
			Column column = grid.getColumn(voField.getName());
			//设置表格只读属性
			FormProperty formProperty = voField.getAnnotation(FormProperty.class);
			if(formProperty!=null && formProperty.readonly()){
				column.setEditable(false);
				continue;
			}
			column.setHeaderCaption(caption);
			com.vaadin.ui.Field rowEditField =  column.getEditorField();
			rowEditField.addValidator(new BeanValidator(vo.getClass(), voField.getName()));
			if(rowEditField instanceof TextField){
				((TextField)rowEditField).setNullRepresentation("");
				((TextField)rowEditField).setNullSettingAllowed(true);
				column.setEditorField(rowEditField);
			}
		}
		
		grid.addSelectionListener(new SelectionEvent.SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				selectedList = new ArrayList<>();
				Set<Object> set = event.getSelected();
				Iterator<Object> iterator = set.iterator();
				while (iterator.hasNext()) {
					T object = (T) iterator.next();
					selectedList.add(object);
				}
				if(selectedList.size()>0){
					entry = selectedList.get(0);
				}
			}
		});
		grid.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
			@Override
			public void preCommit(CommitEvent commitEvent) throws CommitException {
				//
			}
			@Override
			public void postCommit(CommitEvent commitEvent) throws CommitException {
				Item item = commitEvent.getFieldBinder().getItemDataSource();
				BeanItem<T> beanItem = (BeanItem)item;
				try{
					linster.update(beanItem.getBean());
				}catch (Exception e) {
					log.error("$==>表格编辑属性失败，原因可能是服务器提交失败！");
					throw new CommitException();
				}
			}
		});
		return grid;
	}

	/**
	 * @作者：李阳
	 * @时间：2016年6月22日 上午8:48:17
	 * @描述：创建form操作按钮
	 * @参数：@return
	 */
	
	private Component createFormButton(String type,BeanFieldGroup beanFieldGroup){
		HorizontalLayout layout = new HorizontalLayout();
		Button submit = null;
		Button canel = new Button("取消");
		if(STATE_SAVE.equals(type)){
			submit = new Button("保存");
			submit.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						beanFieldGroup.commit();
						linster.add(entry);
						editWindow.close();
						linster.page(1, rows);
					} catch (CommitException e) {
						e.printStackTrace();
						Notification.show("输入内容有误");
					}
				}
			});
		}else if(STATE_UPDATE.equals(type)){
			submit = new Button("修改");
			submit.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						beanFieldGroup.commit();
						linster.update(entry);
						editWindow.close();
						linster.page(currentPage, rows);
					} catch (CommitException e) {
						e.printStackTrace();
						Notification.show("输入内容有误");
					}
				}
			});
		}
		canel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2723537829650691217L;

			@Override
			public void buttonClick(ClickEvent event) {
				if(editWindow != null){
					editWindow.close();
				}
			}
		});
		layout.setSpacing(true);
		layout.addComponent(submit);
		layout.addComponent(canel);
		return layout;
	}
	
	@Override
	public void addListener(BaseViewLinster<T> linster) {
		this.linster = linster;
	}
	
	protected void setPageRow(Integer rows){
		this.rows = rows;
	}
	
	
	
}
