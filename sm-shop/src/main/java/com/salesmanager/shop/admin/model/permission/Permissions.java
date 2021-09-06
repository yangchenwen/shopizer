package com.salesmanager.shop.admin.model.permission;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "permission"
})
public class Permissions implements Serializable {

    private final static long serialVersionUID = 1L;
    @JsonProperty("permission")
    private List<ShopPermission> shopPermission = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("permission")
    public List<ShopPermission> getShopPermission() {
        return shopPermission;
    }

    @JsonProperty("permission")
    public void setShopPermission(List<ShopPermission> shopPermission) {
        this.shopPermission = shopPermission;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
