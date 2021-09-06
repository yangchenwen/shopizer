package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.inventory.PersistableInventory;
import com.salesmanager.shop.model.catalog.product.inventory.ReadableInventory;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.store.controller.product.facade.ProductInventoryFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product inventory resource (Product Inventory Api)"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product inventory resource", description = "Manage inventory for a given product")
})
public class ProductInventoryApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInventoryApi.class);
    @Autowired
    private ProductInventoryFacade productInventoryFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(
            value = {"/private/product/inventory"},
            method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public @ResponseBody
    ReadableInventory create(
            @Valid @RequestBody PersistableInventory inventory,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language) {
        return productInventoryFacade.add(inventory.getProductId(), inventory, merchantStore, language);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = {"/private/product/{productId}/inventory/{id}"},
            method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public void update(
            @PathVariable Long productId,
            @PathVariable Long id,
            @Valid @RequestBody PersistableInventory inventory,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response) {
        inventory.setId(id);
        inventory.setProductId(productId);
        productInventoryFacade.update(productId, inventory, merchantStore, language);

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = {"/private/product/inventory/{id}"},
            method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public void delete(
            @PathVariable Long id,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response) {

        productInventoryFacade.delete(id, merchantStore);

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = {"/private/product/{id}/inventory"},
            method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public @ResponseBody
    ReadableEntityList<ReadableInventory> get(
            @PathVariable Long id,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @RequestParam(value = "child", required = false) String child,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {

        return productInventoryFacade.getInventory(id, merchantStore, child, language, page, count);

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = {"/private/product/{id}/inventory/{inventoryId}"},
            method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public @ResponseBody
    ReadableInventory get(
            @PathVariable Long id,
            @PathVariable Long inventoryId,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response) {

        return productInventoryFacade.get(id, inventoryId, merchantStore, language);

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = {"/private/product/{id}/inventory/store/{code}"},
            method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public @ResponseBody
    ReadableInventory get(
            @PathVariable Long id,
            @PathVariable String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response) {

        return productInventoryFacade.get(id, code, language);

    }

}
