package com.salesmanager.shop.store.model.catalog;

import com.salesmanager.shop.model.entity.ShopEntity;

import java.io.Serializable;
import java.util.List;

public class Attribute extends ShopEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name = null;
    private String type = null;
    private String code = null;
    private List<AttributeValue> values = null;
    private AttributeValue readOnlyValue = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AttributeValue> getValues() {
        return values;
    }

    public void setValues(List<AttributeValue> values) {
        this.values = values;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AttributeValue getReadOnlyValue() {
        return readOnlyValue;
    }

    public void setReadOnlyValue(AttributeValue readOnlyValue) {
        this.readOnlyValue = readOnlyValue;
    }

}
