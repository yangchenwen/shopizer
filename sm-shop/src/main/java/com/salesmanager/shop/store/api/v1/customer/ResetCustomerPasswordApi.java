package com.salesmanager.shop.store.api.v1.customer;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.store.api.exception.RestApiException;
import com.salesmanager.shop.store.security.PasswordRequest;
import com.salesmanager.shop.store.security.ResetPasswordRequest;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"Customer password management resource (User password Management Api)"})
@SwaggerDefinition(tags = {
        @Tag(name = "Customer password management resource", description = "Customer password management")})
public class ResetCustomerPasswordApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetCustomerPasswordApi.class);

    @Inject
    private com.salesmanager.shop.store.controller.customer.facade.v1.CustomerFacade customerFacade;

    /**
     * Request a reset password token
     *
     * @param merchantStore
     * @param language
     * @param user
     * @param request
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = {"/customer/password/reset/request"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "POST", value = "Launch customer password reset flow", notes = "", response = Void.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
    public void passwordResetRequest(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                                     @Valid @RequestBody ResetPasswordRequest customer) {

        customerFacade.requestPasswordReset(customer.getUsername(), customer.getReturnUrl(), merchantStore, language);

    }

    /**
     * Verify a password token
     *
     * @param store
     * @param token
     * @param merchantStore
     * @param language
     * @param request
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/customer/{store}/reset/{token}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "GET", value = "Validate customer password reset token", notes = "", response = Void.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
    public void passwordResetVerify(@PathVariable String store, @PathVariable String token,
                                    @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

        /**
         * Receives reset token Needs to validate if user found from token Needs
         * to validate if token has expired
         *
         * If no problem void is returned otherwise throw OperationNotAllowed
         * All of this in UserFacade
         */

        customerFacade.verifyPasswordRequestToken(token, store);

    }

    /**
     * Change password
     *
     * @param passwordRequest
     * @param store
     * @param token
     * @param merchantStore
     * @param language
     * @param request
     */
    @RequestMapping(value = "/customer/{store}/password/{token}", method = RequestMethod.POST, produces = {
            "application/json"})
    @ApiOperation(httpMethod = "POST", value = "Change customer password", response = Void.class)
    public void changePassword(
            @RequestBody @Valid PasswordRequest passwordRequest,
            @PathVariable String store,
            @PathVariable String token, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
            HttpServletRequest request) {

        // validate password
        if (StringUtils.isBlank(passwordRequest.getPassword())
                || StringUtils.isBlank(passwordRequest.getRepeatPassword())) {
            throw new RestApiException("400", "Password don't match");
        }

        if (!passwordRequest.getPassword().equals(passwordRequest.getRepeatPassword())) {
            throw new RestApiException("400", "Password don't match");
        }

        customerFacade.resetPassword(passwordRequest.getPassword(), token, store);

    }

}
