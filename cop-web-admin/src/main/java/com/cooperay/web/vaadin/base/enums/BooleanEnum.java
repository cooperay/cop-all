package com.cooperay.web.vaadin.base.enums;

public enum BooleanEnum {

	  /** 已取消 */
    YES("启用",1),
    /** 待审核 */
    NO("未启用",0);
    
 	private String name;
 	private Integer value;
    private BooleanEnum(String name,Integer value){
    	this.name = name;
    	this.value = value;
    }
    public Integer getBooleanEnum(){
    	return value;
    }
    public String getName(){
    	return name;
    }
    public String toString() {
    	return getName();
    }
	
}
