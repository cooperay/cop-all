package com.cooperay.facade.admin.enums;

public enum ResourceType {
	/**菜单资源**/
	MENU("菜单",1),
    /** 按钮资源 */
    BUTTON("按钮",2),
    /**功能资源，在前台页面不显示对应操作*/
    FUNCTION("功能",3);
 
 	private String name;
 	private Integer value;
    private ResourceType(String name,Integer value){
    	this.name = name;
    	this.value = value;
    }
    public Integer getState(){
    	return value;
    }
    public String getName(){
    	return name;
    }
    public String toString() {
    	return getName();
    }
}
