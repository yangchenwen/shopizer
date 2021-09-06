package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.order.history.PersistableOrderStatusHistory;
import com.salesmanager.shop.model.order.history.ReadableOrderStatusHistory;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.utils.AuthorizationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Order status history api"})
@SwaggerDefinition(tags = {
        @Tag(name = "Order status history resource", description = "Related to OrderManagement api")})
public class OrderStatusHistoryApi {

    @Inject
    private OrderFacade orderFacade;

    @Inject
    private AuthorizationUtils authorizationUtils;

    @RequestMapping(value = {"private/orders/{id}/history"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ReadableOrderStatusHistory> list(@PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
                                                 @ApiIgnore Language language) {

        String user = authorizationUtils.authenticatedUser();
        authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
                Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

        return orderFacade.getReadableOrderHistory(id, merchantStore, language);

    }

    @RequestMapping(value = {"private/orders/{id}/history"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(httpMethod = "POST", value = "Add order history", notes = "Adds a new status to an order", produces = "application/json", response = Void.class)
    @ResponseBody
    public void create(@PathVariable final Long id, @RequestBody PersistableOrderStatusHistory history,
                       @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

        String user = authorizationUtils.authenticatedUser();
        authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
                Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

        // TODO validate date format

        orderFacade.createOrderStatus(history, id, merchantStore);

    }

}
