package com.salesmanager.shop.store.api.v1.product;

import antlr.collections.List;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductList;
import com.salesmanager.shop.model.catalog.product.group.ProductGroup;
import com.salesmanager.shop.store.controller.items.facade.ProductItemsFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used for product grouping such as featured items
 *
 * @author carlsamson
 */
@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product groups management resource (Product Groups Management Api)"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product groups management resource", description = "Product groups management")})
public class ProductGroupApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGroupApi.class);
    @Inject
    private ProductService productService;
    @Inject
    private ProductItemsFacade productItemsFacade;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/private/products/group")
    @ApiOperation(httpMethod = "POST", value = "Create product group", notes = "", response = ProductGroup.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    @ResponseBody
    public ProductGroup creteGroup(
            @RequestBody ProductGroup group,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response)
            throws Exception {

        return productItemsFacade.createProductGroup(group, merchantStore);

    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/private/products/group/{code}")
    @ApiOperation(httpMethod = "PATCH", value = "Update product group visible flag", notes = "", response = ProductGroup.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public void updateGroup(
            @RequestBody ProductGroup group,
            @PathVariable String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response)
            throws Exception {

        productItemsFacade.updateProductGroup(code, group, merchantStore);

    }

    @GetMapping("/private/products/groups")
    @ApiOperation(httpMethod = "GET", value = "Get products groups for a given merchant", notes = "", response = List.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    @ResponseBody
    public java.util.List<ProductGroup> list(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response)
            throws Exception {

        return productItemsFacade.listProductGroups(merchantStore, language);

    }

    /**
     * Query for a product group public/products/group/{code}?lang=fr|en no lang it will take session
     * lang or default store lang code can be any code used while creating product group, defeult
     * being FEATURED
     *
     * @param store
     * @param language
     * @param groupCode
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/products/group/{code}")
    @ApiOperation(httpMethod = "GET", value = "Get products by group code", notes = "", response = ReadableProductList.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    @ResponseBody
    public ReadableProductList getProductItemsByGroup(
            @PathVariable final String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response)
            throws Exception {
        try {
            ReadableProductList list = productItemsFacade.listItemsByGroup(code, merchantStore, language);

            if (list == null) {
                response.sendError(404, "Group not fount for code " + code);
                return null;
            }

            return list;

        } catch (Exception e) {
            LOGGER.error("Error while getting products", e);
            response.sendError(503, "An error occured while retrieving products " + e.getMessage());
        }

        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/private/products/{productId}/group/{code}", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    @ResponseBody
    public ReadableProductList addProductToGroup(
            @PathVariable Long productId,
            @PathVariable String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response) {

        Product product = null;
        try {
            // get the product
            product = productService.getById(productId);

            if (product == null) {
                response.sendError(404, "Product not fount for id " + productId);
                return null;
            }

        } catch (Exception e) {
            LOGGER.error("Error while adding product to group", e);
            try {
                response.sendError(503, "Error while adding product to group " + e.getMessage());
            } catch (Exception ignore) {
            }

            return null;
        }

        ReadableProductList list =
                productItemsFacade.addItemToGroup(product, code, merchantStore, language);

        return list;

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/private/products/{productId}/group/{code}",
            method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    @ResponseBody
    public ReadableProductList removeProductFromGroup(
            @PathVariable Long productId,
            @PathVariable String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            // get the product
            Product product = productService.getById(productId);

            if (product == null) {
                response.sendError(404, "Product not fount for id " + productId);
                return null;
            }

            ReadableProductList list =
                    productItemsFacade.removeItemFromGroup(product, code, merchantStore, language);

            return list;

        } catch (Exception e) {
            LOGGER.error("Error while removing product from category", e);
            try {
                response.sendError(503, "Error while removing product from category " + e.getMessage());
            } catch (Exception ignore) {
            }

            return null;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/products/group/{code}")
    @ApiOperation(httpMethod = "DELETE", value = "Delete product group by group code", notes = "", response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public void deleteGroup(
            @PathVariable final String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response) {

        productItemsFacade.deleteGroup(code, merchantStore);

    }
}
