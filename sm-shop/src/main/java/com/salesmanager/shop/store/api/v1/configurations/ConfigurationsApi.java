package com.salesmanager.shop.store.api.v1.configurations;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.configuration.ReadableConfiguration;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"Configurations management"})
@SwaggerDefinition(tags = {
        @Tag(name = "Configurations management", description = "Configurations management for modules")})
public class ConfigurationsApi {

    /**
     * Configurations of modules
     */
    @PostMapping("/private/configurations/payment")
    @ApiOperation(
            httpMethod = "POST",
            value = "Manages payment configurations",
            notes = "Requires administration access",
            produces = "application/json",
            response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
    })
    public Void create(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language) {
        //return customerFacade.create(customer, merchantStore, language);
        return null;

    }

    /**
     * Configurations of payment modules
     */
    @GetMapping("/private/configurations/payment")
    @ApiOperation(
            httpMethod = "GET",
            value = "List payment configurations summary",
            notes = "Requires administration access",
            produces = "application/json",
            response = List.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
    })
    public List<ReadableConfiguration> listPaymentConfigurations(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language) {
        //return customerFacade.create(customer, merchantStore, language);
        return null;

    }

    /**
     * Configurations of shipping modules
     */
    @GetMapping("/private/configurations/shipping")
    @ApiOperation(
            httpMethod = "GET",
            value = "List shipping configurations summary",
            notes = "Requires administration access",
            produces = "application/json",
            response = List.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
    })
    public List<ReadableConfiguration> listShippingConfigurations(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language) {
        //return customerFacade.create(customer, merchantStore, language);
        return null;

    }

}
