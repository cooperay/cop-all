package com.cooperay.facade.admin.entity;

import java.util.Date;

import com.cooperay.common.entity.BaseEntity;

public class BillNumber extends BaseEntity {
    private String cname;

    private String expression;

    private Date lastdate;

    private Integer random;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression == null ? null : expression.trim();
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }
}