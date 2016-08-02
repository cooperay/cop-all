package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class User extends BaseEntity {

    private String userName;

    private String password;

    private Boolean isEnable;
    
    private Double age;
    
    private Long deptId;

    private State state;
    public enum State{
        /** 已取消 */
        CANCEL("已取消",1),
        /** 待审核 */
        WAITCONFIRM("待审核",2),
        /** 等待付款 */
        WAITPAYMENT("等待付款",3),
        /** 正在配货 */
        ADMEASUREPRODUCT ("正在配货",4),
        /** 等待发货 */
        WAITDELIVER("等待发货",5),
        /** 已发货 */
        DELIVERED ("已发货",6),
        /** 已收货 */
        RECEIVED ("已收货",7);
	 
	 	private String name;
	 	private Integer value;
        private State(String name,Integer value){
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }
}