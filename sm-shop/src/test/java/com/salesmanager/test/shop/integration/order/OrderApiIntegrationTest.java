package com.salesmanager.test.shop.integration.order;

import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCart;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@Ignore
public class OrderApiIntegrationTest extends ServicesTestSupport {

    @Autowired
    private TestRestTemplate testRestTemplate;

    public void createOrder() throws Exception {

        //create cart
        ReadableShoppingCart cart = super.sampleCart();

        //create order
    }

}
