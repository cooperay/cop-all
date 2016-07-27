package com.cooperay.web.vaadin.base.view;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooperay.common.page.PageBean;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;
import com.cooperay.web.vaadin.base.ann.HideInGrid;
import com.cooperay.web.vaadin.base.ann.SeachProperty;
import com.cooperay.web.vaadin.base.converter.BooleanConverter;
import com.cooperay.web.vaadin.base.enums.BooleanEnum;
import com.cooperay.web.vaadin.component.ConfimWindow;
import com.cooperay.web.vaadin.component.PageBar;
import com.cooperay.web.vaadin.component.PageBar.PageBarEvent;
import com.cooperay.web.vaadin.component.PageBar.PageBarExprotEvent;
import com.vaadin.client.ui.VOptionGroup;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
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
	private V seachEntry;
	private List<T> selectedList;  //选中列表
	
	
	/**初示参数**/
	private Integer rows = 100;
	private Integer currentPage = 1;
	private PageBean pageBean;
	private boolean editorEnabled = true;
	private List<T> list;  //初始列表
	private Object[] colOrder; //列表顺序
	
	private Window editWindow;  //编辑窗口
	private Grid grid;
	private PageBar pageBar;
	private VerticalLayout panelContent;
	
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
		this.seachEntry = vo;
		this.colOrder = colOrder;
		init(caption);
	}
	

	@Override
	public void setList(List<T> list) {
		BeanItemContainer<T> beanItemContainer = (BeanItemContainer)grid.getContainerDataSource();
		beanItemContainer.removeAllItems();
		beanItemContainer.addAll(list);
		Integer row = grid.getContainerDataSource().size();
		if(row>0){
			grid.setHeightByRows(row);
		}
	}
	
	@Override
	public void setPage(PageBean pageBean) {
		this.pageBean = pageBean;
		BeanItemContainer<T> beanItemContainer = (BeanItemContainer)grid.getContainerDataSource();
		beanItemContainer.removeAllContainerFilters();
		beanItemContainer.removeAllItems();
		beanItemContainer.addAll((List<T>)pageBean.getRecordList());
		Integer row = grid.getContainerDataSource().size();
		if(row>0){
			grid.setHeightByRows(row);
		}
		currentPage = pageBean.getCurrentPage();
		if(pageBar == null){
			panelContent.addComponent(createPageBar(pageBean.getTotalCount(),rows));
		}
		selectedList = null;
	}

	protected void init(String caption) {
		log.debug("init base view");
		Panel panel = new Panel(caption);
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panelContent = new VerticalLayout();
		panel.setContent(panelContent);
		panel.setSizeFull();
		panelContent.setMargin(true);
		panelContent.setSpacing(true);
		panelContent.addComponent(createToolBar());
		panelContent.addComponent(createGrid());
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
				BaseView.this.rows = pageBarEvent.getRows();
				linster.page(page, BaseView.this.rows);
			}
		});
		pageBar.setPageBarExprotLinster(new PageBar.PageBarExprotLinster() {
			@Override
			public void exprotAll(PageBarExprotEvent event) {
				
				Notification.show("导出所有记录",Type.WARNING_MESSAGE);
				
			}
			
			@Override
			public void exprot(PageBarExprotEvent event) {
				System.out.println(pageBean.getRecordList());
				Notification.show("导出记录 "+event.getPage()+" "+event.getRows(),Type.WARNING_MESSAGE);
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
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setSizeFull();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeUndefined();
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
		del.setIcon(FontAwesome.TRASH);
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
						resetPageBar();
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
		
		Button refresh  = new Button("刷新");
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addStyleName(ValoTheme.BUTTON_SMALL);
		refresh.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				linster.page(currentPage, rows);
			}
		});
		layout.addComponent(add);
		layout.addComponent(update);
		layout.addComponent(del);
		layout.addComponent(refresh);
		toolbar.addComponent(layout);

		Component seachForm = createSeachForm();
		toolbar.addComponent(seachForm);
		toolbar.setComponentAlignment(seachForm, Alignment.TOP_RIGHT);
		
		
		//创建搜索区域
		/*TextField textField = new TextField();
		textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		textField.setInputPrompt("mingcheng");
		layout.addComponent(textField);*/
		
		return toolbar;
	}
	
	/**
	 * 创建搜索条
	 * @return
	 */
	protected Component createSeachForm() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidthUndefined();
		//layout.setSpacing(true);
		
		final BeanFieldGroup<V> binder =  new BeanFieldGroup(seachEntry.getClass());
		binder.setItemDataSource(seachEntry);
		Field[] fields= vo.getClass().getDeclaredFields();
		fieldCount = fields.length;
		//final FieldGroup binder =  new FieldGroup();
		for (Field voField : fields) {
			//判断是否在表单中隐藏
			SeachProperty seachProperty = voField.getAnnotation(SeachProperty.class);
			if(seachProperty==null){
				continue;
			}
			com.vaadin.ui.Field formField = null;
			if(Enum.class.isAssignableFrom(voField.getType())){
				formField = binder.buildAndBind(null, voField.getName(), ComboBox.class);
			}else {
				formField = binder.buildAndBind(null,voField.getName());
			}
			
			if(formField instanceof TextField){
				formField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
				((TextField)formField).setInputPrompt(seachProperty.text());
				((TextField)formField).setNullRepresentation("");
				((TextField)formField).setNullSettingAllowed(false);
			}else if(formField instanceof ComboBox){
				formField.addStyleName(ValoTheme.COMBOBOX_SMALL);
				((ComboBox) formField).setInputPrompt(seachProperty.text());
			}else if(formField instanceof DateField){
				formField.addStyleName(ValoTheme.DATEFIELD_SMALL);
				((PopupDateField) formField).setInputPrompt(seachProperty.text());
			}
			
			formField.removeAllValidators();
			layout.addComponent(formField);
		}
		
		Button button = new Button("搜索",FontAwesome.SEARCH);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
				} catch (CommitException e1) {
					log.error("搜索条件提交失败");
				}
				Field[] fields= seachEntry.getClass().getDeclaredFields();
				Map<String, Object> param = new HashMap<>();
				//final FieldGroup binder =  new FieldGroup();
				for (Field voField : fields) {
					//判断是否在表单中隐藏
					SeachProperty seachProperty = voField.getAnnotation(SeachProperty.class);
					if(seachProperty==null){
						continue;
					}
					Object value = null;
					try {
						PropertyDescriptor pd = new PropertyDescriptor(voField.getName(),seachEntry.getClass());
						value = pd.getReadMethod().invoke(seachEntry);
					} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.debug("获取搜索实体属性值失败");
						e.printStackTrace();
					}
					if(value!=null){
						if(value instanceof BooleanEnum){
							BooleanEnum enum1 = (BooleanEnum)value;
							value = enum1.getBooleanEnum();
						}else if(value instanceof Enum){ 
							Enum enum1 = (Enum)value;
							value = enum1.name();
						}
						param.put(voField.getName(), value);
					}
				}
			}
		});
		layout.addComponent(button);
		Button reset = new Button("重置");
		reset.addStyleName(ValoTheme.BUTTON_SMALL);
		reset.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Class<V> class1 = (Class<V>)seachEntry.getClass();
				try {
					seachEntry = class1.newInstance();
					binder.setItemDataSource(seachEntry);
				} catch (InstantiationException | IllegalAccessException e) {
					log.error("$==> 初始化搜索类失败 "+entry.getClass());
				}
			}
		});
		layout.addComponent(reset);
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
					try {
						field = entry.getClass().getSuperclass().getDeclaredField(voField.getName());
					} catch (NoSuchFieldException | SecurityException e1) {
						log.debug("$==>Vo指定的对象属性不存在 "+voField.getName());
					}
			} catch (SecurityException e) {
				log.error(e.toString());
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
			//设置只读属性
			FormProperty formProperty = voField.getAnnotation(FormProperty.class);
			if(formProperty!=null && formProperty.readonly()){
				formField.setReadOnly(true);
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
		//添加操作列
		GeneratedPropertyContainer gpc =
			    new GeneratedPropertyContainer(container);
		gpc.addGeneratedProperty("delete",
		    new PropertyValueGenerator<String>() {

		    @Override
		    public String getValue(Item item, Object itemId,
		                           Object propertyId) {
		        return "Delete"; // The caption
		    }

		    @Override
		    public Class<String> getType() {
		        return String.class;
		    }
		});
		// Create a grid bound to the container
		grid = new Grid(container);
		grid.setSizeFull();
		//设置列重新排序
		grid.setColumnReorderingAllowed(true);
		//设置列折叠
		for (Column c : grid.getColumns()) {
        	c.setHidable(true);
        }
		grid.setEditorEnabled(editorEnabled);
		grid.setEditorSaveCaption("保存");
		grid.setEditorCancelCaption("取消");
		//grid.setHeightByRows(rows);
		//grid.setHeightMode(HeightMode.ROW);
		grid.setHeight(Page.getCurrent().getBrowserWindowHeight()-290, Unit.PIXELS);
		// Handle selection in single-selection mode
		grid.setSelectionMode(SelectionMode.MULTI);
		if(colOrder!=null){
			grid.setColumnOrder(colOrder);
		}
		Field[] fields= vo.getClass().getDeclaredFields();
		for (Field voField : fields) {
			
			//设置隐藏列
			HideInGrid hideProperty = voField.getAnnotation(HideInGrid.class);
			if(hideProperty!=null){
				try{
					grid.removeColumn(voField.getName());
				}catch (IllegalArgumentException e) {
					log.debug("Vo属性在Grid中不存在");
				}
				continue;
			}
			//设置表头
			String caption = getFieldDisName(voField);
			Column column = grid.getColumn(voField.getName());
			column.setHeaderCaption(caption);
			//设置表格只读属性
			FormProperty formProperty = voField.getAnnotation(FormProperty.class);
			if(formProperty!=null && formProperty.readonly()){
				column.setEditable(false);
				continue;
			}
			//设置render
			if(Boolean.class.equals(voField.getType())){
				/*column.setRenderer(new HtmlRenderer(),new StringToBooleanConverter(FontAwesome.CHECK_CIRCLE_O.getHtml(),
				        FontAwesome.CIRCLE_O.getHtml()));*/
				column.setRenderer(new HtmlRenderer(),new BooleanConverter());
				
			}
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
		
		//添加操作列表
		//grid.addColumn("id", Long.class);
		
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
						resetPageBar();
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
						grid.deselectAll();
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
	
	private void resetPageBar(){
		pageBar.reset(pageBean.getTotalCount(), rows);
	}
	
	@Override
	public void addListener(BaseViewLinster<T> linster) {
		this.linster = linster;
	}
	
	protected void setPageRow(Integer rows){
		this.rows = rows;
	}
	
	
	
}
