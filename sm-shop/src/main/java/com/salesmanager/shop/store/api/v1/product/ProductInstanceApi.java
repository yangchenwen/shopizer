package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.store.controller.product.facade.ProductInventoryFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Api to manage ProductInstance
 * <p>
 * Product instance also known as product variant
 * allows to specify product size, sku and options related to this product instance
 *
 * @author carlsamson
 */
@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product instances api"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product instances resource", description = "Manage inventory for a given product")
})
public class ProductInstanceApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInstanceApi.class);
    @Autowired
    private ProductInventoryFacade productInventoryFacade;

    @GetMapping(value = {"/private/product/instance"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")})
    @ApiOperation(httpMethod = "GET", value = "Check if option set code already exists", notes = "", response = EntityExists.class)
    public ResponseEntity<EntityExists> exists(
            @RequestParam(value = "code") String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language) {

        //boolean isOptionExist = productVariationFacade.exists(code, merchantStore);
        //return new ResponseEntity<EntityExists>(new EntityExists(isOptionExist), HttpStatus.OK);
        return null;

    }

}
